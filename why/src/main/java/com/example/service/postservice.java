package com.example.service;

import java.util.ArrayList;

import com.example.domain.memberVO;
import com.example.domain.postVO;


public interface postservice {
	
	public ArrayList<postVO> postList();

	public memberVO login(String id, String pw);
	
	

}
