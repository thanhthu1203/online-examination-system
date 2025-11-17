package com.yourexam.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin {
    @Id
    @Column(length=11)
    private String Admin_ID;

    private String Password;

    @ManyToOne
    @JoinColumn(name = "OnlineWeb_W_ID")
    private OnlineWeb onlineWeb;

    public String getAdmin_ID(){return Admin_ID;}
    public void setAdmin_ID(String id){this.Admin_ID = id;}
    public String getPassword(){return Password;}
    public void setPassword(String p){this.Password = p;}
    public OnlineWeb getOnlineWeb(){return onlineWeb;}
    public void setOnlineWeb(OnlineWeb o){this.onlineWeb = o;}
}