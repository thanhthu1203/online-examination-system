export const API_BASE = (location.hostname === 'localhost' || location.hostname === '127.0.0.1')
  ? 'http://localhost:8080'
  : (location.origin.replace(/:\d+$/, '') + ':8080');

export function getToken(){ return localStorage.getItem('token'); }
export function setToken(t){ localStorage.setItem('token', t); }
export function clearToken(){ localStorage.removeItem('token'); localStorage.removeItem('role'); localStorage.removeItem('userId'); }

async function apiRequest(path, opts = {}){
  const headers = opts.headers || {};
  headers['Content-Type'] = headers['Content-Type'] || 'application/json';
  const token = getToken();
  if (token) headers['Authorization'] = token.startsWith('Bearer ') ? token : ('Bearer ' + token);
  opts.headers = headers;
  const res = await fetch(API_BASE + path, opts);
  if (res.status === 401) {
    clearToken();
    throw new Error('Unauthorized');
  }
  const text = await res.text();
  try { return JSON.parse(text || '{}'); } catch(e){ return text; }
}

export async function login(username, password){
  return apiRequest('/auth/login', { method:'POST', body: JSON.stringify({ username, password }) });
}
export async function registerStudent(payload){
  const body = { Student_ID: payload.studentId, SName: payload.sName, Phone: payload.phone, Password: payload.password };
  return apiRequest('/auth/register', { method:'POST', body: JSON.stringify(body) });
}
export async function listCourses(){ return apiRequest('/courses'); }
export async function createCourse(course){ return apiRequest('/courses', { method:'POST', body: JSON.stringify(course) }); }
export async function listExams(){ return apiRequest('/exams'); }
export async function createExam(exam){ return apiRequest('/exams', { method:'POST', body: JSON.stringify(exam) }); }
export async function enrollCourse(courseId){ return apiRequest(`/courses/${encodeURIComponent(courseId)}/enroll`, { method:'POST' }); }
export async function registerExam(examId){ return apiRequest(`/exams/${encodeURIComponent(examId)}/register`, { method:'POST' }); }
export async function uploadResultCsv(file){
  const form = new FormData();
  form.append('file', file, file.name);
  const token = getToken();
  const headers = token ? { 'Authorization': token.startsWith('Bearer ') ? token : ('Bearer ' + token) } : {};
  const res = await fetch(API_BASE + '/results/upload-csv', { method:'POST', body: form, headers });
  if (!res.ok) throw new Error('Upload failed: ' + res.statusText);
  const text = await res.text();
  try { return JSON.parse(text || '{}'); } catch(e){ return text; }
}
export async function getResultsByStudent(studentId){ return apiRequest(`/results/student/${encodeURIComponent(studentId)}`); }
export async function getResultsByExam(examId){ return apiRequest(`/results/exam/${encodeURIComponent(examId)}`); }