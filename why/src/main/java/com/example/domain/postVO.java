package com.example.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class postVO {
 private String pnum;
 private String id;
 private String title;
 private String context;
 private String good;
 private String look;
 private Timestamp postdate;
 
}
