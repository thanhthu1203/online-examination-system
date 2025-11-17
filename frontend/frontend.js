import * as api from './api.js';

const dlgLogin = document.getElementById('dlg-login');
const dlgRegister = document.getElementById('dlg-register');

function showToast(msg, timeout=3000){
  const t = document.getElementById('toast');
  t.hidden = false;
  t.textContent = msg;
  setTimeout(()=> t.hidden = true, timeout);
}

function showView(viewId){
  document.querySelectorAll('.view').forEach(v => v.hidden = true);
  const view = document.getElementById(viewId);
  if (view) view.hidden = false;
}

async function loadCourses(){
  try {
    const courses = await api.listCourses();
    const grid = document.getElementById('courses-grid');
    grid.innerHTML = '';
    (courses || []).forEach(c => {
      const card = document.createElement('div');
      card.className = 'card course-card';
      card.innerHTML = `
        <div class="course-head">
          <div class="course-badge">${c.course_ID}</div>
          <div>
            <div style="font-weight:700">${c.cName}</div>
            <div style="font-size:12px;color:var(--muted)">${c.onlineWeb ? (c.onlineWeb.WName || c.onlineWeb.W_ID) : 'main'}</div>
          </div>
        </div>
        <div style="margin-top:auto">
          <button class="btn btn-primary btn-enroll" data-id="${c.course_ID}">Enroll</button>
        </div>`;
      grid.appendChild(card);
    });
    document.querySelectorAll('.btn-enroll').forEach(b => {
      b.addEventListener('click', async (e) => {
        const id = e.currentTarget.dataset.id;
        try {
          await api.enrollCourse(id);
          showToast('Enrolled in ' + id);
        } catch(err){ showToast('Enroll failed: ' + err.message); }
      });
    });
  } catch(err){
    console.error(err);
    showToast('Failed loading courses');
  }
}

function renderCalendar(exams){
  const byDate = {};
  (exams || []).forEach(ex => {
    const dateStr = (ex.testDateDATETIME || ex.TestDateDATETIME || '').split('T')[0] || (ex.TestDateDATETIME ? ex.TestDateDATETIME.split(' ')[0] : '');
    if (!dateStr) return;
    byDate[dateStr] = byDate[dateStr] || [];
    byDate[dateStr].push(ex);
  });
  const calendar = document.getElementById('exam-calendar');
  calendar.innerHTML = '';
  const dates = Object.keys(byDate).sort();
  if (dates.length === 0) { calendar.innerHTML = '<div>No upcoming exams</div>'; return; }
  dates.forEach(d => {
    const group = document.createElement('div');
    group.className = 'date-group';
    group.innerHTML = `<div class="date-title">${d}</div>`;
    byDate[d].forEach(ex => {
      const item = document.createElement('div');
      item.className = 'exam-item';
      item.innerHTML = `<div><strong>${ex.exam_ID}</strong> — ${ex.course ? (ex.course.CName || ex.course.Course_ID) : ex.course_Course_ID}<div style="font-size:12px;color:var(--muted)">${ex.testDateDATETIME || ex.TestDateDATETIME}</div></div>
                        <div><button class="btn btn-primary btn-register" data-id="${ex.exam_ID}">Register</button></div>`;
      group.appendChild(item);
    });
    calendar.appendChild(group);
  });
  document.querySelectorAll('.btn-register').forEach(b => {
    b.addEventListener('click', async e => {
      const id = e.currentTarget.dataset.id;
      try {
        await api.registerExam(id);
        showToast('Registered for exam ' + id);
      } catch(err){ showToast('Register failed: ' + err.message); }
    });
  });
}

async function loadStudentDashboard(){
  const userId = localStorage.getItem('userId');
  if (!userId) { document.getElementById('student-profile').innerText = 'Not signed in'; return; }
  document.getElementById('student-profile').innerHTML = `<div><strong>ID:</strong> ${userId}</div>`;

  try {
    const exams = await api.listExams();
    renderCalendar(exams);
  } catch(err){
    document.getElementById('exam-calendar').innerText = 'Failed loading exams';
  }

  try {
    const results = await api.getResultsByStudent(userId);
    const tbody = document.getElementById('student-results').querySelector('tbody');
    tbody.innerHTML = '';
    (results || []).forEach(r => {
      const courseName = r.exam && r.exam.course ? (r.exam.course.CName || r.exam.course.Course_ID) : (r.exam ? (r.exam.Course_Course_ID || '') : '');
      const tr = document.createElement('tr');
      tr.innerHTML = `<td>${courseName}</td><td>${r.exam ? (r.exam.exam_ID || r.exam.Exam_ID) : r.Exam_ID}</td><td>${r.score || r.Score}</td>`;
      tbody.appendChild(tr);
    });
  } catch(e){}
}

async function wireUp(){
  document.getElementById('nav-courses').addEventListener('click', ()=> { showView('view-courses'); loadCourses(); });
  document.getElementById('nav-student').addEventListener('click', ()=> { showView('view-student'); loadStudentDashboard(); });
  document.getElementById('nav-admin').addEventListener('click', ()=> { showView('view-admin'); });

  document.getElementById('btn-open-login').addEventListener('click', ()=> dlgLogin.showModal());
  document.getElementById('btn-open-register').addEventListener('click', ()=> dlgRegister.showModal());
  document.getElementById('btn-login-cancel').addEventListener('click', ()=> dlgLogin.close());
  document.getElementById('btn-register-cancel').addEventListener('click', ()=> dlgRegister.close());

  document.getElementById('form-login').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fm = e.currentTarget;
    const username = fm.username.value.trim();
    const password = fm.password.value;
    try {
      const r = await api.login(username, password);
      if (r.token) {
        api.setToken(r.token);
        localStorage.setItem('role', r.role || 'STUDENT');
        localStorage.setItem('userId', username);
        showToast('Login successful');
        dlgLogin.close();
        loadCourses();
        showView('view-student');
        loadStudentDashboard();
      } else {
        showToast('Login failed: invalid response');
      }
    } catch(err){
      showToast('Login failed: ' + err.message);
    }
  });

  document.getElementById('form-register').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fm = e.currentTarget;
    const payload = {
      studentId: fm.studentId.value.trim(),
      sName: fm.sName.value.trim(),
      phone: fm.phone.value.trim(),
      password: fm.password.value
    };
    try {
      await api.registerStudent(payload);
      showToast('Registered successfully, please login');
      dlgRegister.close();
      dlgLogin.showModal();
    } catch(err){
      showToast('Register failed: ' + err.message);
    }
  });

  document.getElementById('form-create-course').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fm = e.currentTarget;
    try {
      const course = { course_ID: fm.courseId.value.trim(), cName: fm.courseName.value.trim(), onlineWeb_W_ID: 'web_main' };
      await api.createCourse(course);
      showToast('Course created');
      fm.reset();
      loadCourses();
    } catch(err){ showToast('Create course failed: ' + err.message); }
  });

  document.getElementById('form-create-exam').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fm = e.currentTarget;
    try {
      const exam = {
        exam_ID: fm.examId.value.trim(),
        eType: fm.eType.value.trim(),
        roomNumber: fm.room.value.trim(),
        testDateDATETIME: fm.testDate.value,
        durationMinutes: parseInt(fm.duration.value,10),
        course_Course_ID: fm.courseId.value.trim()
      };
      await api.createExam(exam);
      showToast('Exam created');
      fm.reset();
    } catch(err){ showToast('Create exam failed: ' + err.message); }
  });

  document.getElementById('form-upload-result').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fileInput = document.getElementById('csv-file');
    const file = fileInput.files[0];
    if (!file) { showToast('Select a CSV file'); return; }
    try {
      const res = await api.uploadResultCsv(file);
      showToast('CSV upload completed');
      fileInput.value = '';
    } catch(err){ showToast('Upload failed: ' + err.message); }
  });

  loadCourses();
  showView('view-courses');
}

document.addEventListener('DOMContentLoaded', ()=> {
  try { if (!('showModal' in HTMLDialogElement.prototype)) { document.querySelectorAll('dialog').forEach(d => d.style.display = 'block'); } } catch(e){}
  wireUp();
});