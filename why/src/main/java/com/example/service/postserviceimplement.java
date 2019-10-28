package com.example.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.domain.comentVO;
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

	@Override
	public ArrayList<postVO> postdetail(String pnum) {
		// TODO Auto-generated method stub
		return mapper.postdetail(pnum);
	}

	@Override
	public ArrayList<comentVO> coment(String pnum) {
		// TODO Auto-generated method stub
		return mapper.coment(pnum);
	}

	@Override
	public int newreply(comentVO comentVO) {
		// TODO Auto-generated method stub
		return mapper.newreply(comentVO);
	}

	@Override
	public int newpost(postVO postVO) {
		// TODO Auto-generated method stub
		return mapper.newpost(postVO);
	}

	@Override
	public int postdel(String pnum, String name) {
		// TODO Auto-generated method stub
		return mapper.postdel(pnum , name);
	}

	
	

}
