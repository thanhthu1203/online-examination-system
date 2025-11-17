package com.yourexam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OnlineWeb")
public class OnlineWeb {
    @Id
    private String W_ID;
    private String WName;

    public String getW_ID() { return W_ID; }
    public void setW_ID(String w_ID) { W_ID = w_ID; }
    public String getWName() { return WName; }
    public void setWName(String wName) { WName = wName; }
}