package com.example.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.domain.memberVO;
import com.example.domain.postVO;
import com.example.mapper.postmapper;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class postserviceimplement implements postservice {
	
	private postmapper mapper;
	
	@Override
	public ArrayList<postVO> postList() {
		// TODO Auto-generated method stub
		return mapper.postList();
	}

	@Override
	public memberVO login(String id, String pw) {
		// TODO Auto-generated method stub
		return mapper.login(id,pw);
	}

	@Override
	public ArrayList<memberVO> loginCheck(String id, String pw) {
		// TODO Auto-generated method stub
		return mapper.loginCheck(id,pw);
	}

	@Override
	public ArrayList<memberVO> idcheck(String id) {
		// TODO Auto-generated method stub
		return mapper.idcheck(id);
	}

	@Override
	public Integer newmember(String id, String pw, String name) {
		// TODO Auto-generated method stub
		return mapper.newmember(id,pw,name);
	}

	
	

}
