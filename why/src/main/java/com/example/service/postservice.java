package com.example.service;

import java.util.ArrayList;

import com.example.domain.comentVO;
import com.example.domain.memberVO;
import com.example.domain.postVO;


public interface postservice {
	
	public ArrayList<postVO> postList();

	public memberVO login(String id, String pw);

	public ArrayList<memberVO> loginCheck(String id, String pw);

	public ArrayList<memberVO> idcheck(String id);

	public Integer newmember(String id, String pw, String name);

	public ArrayList<postVO> postdetail(String pnum);

	public ArrayList<comentVO> coment(String pnum);

	public int newreply(comentVO comentVO);

	public int newpost(postVO postVO);

	public int postdel(String pnum, String name);

	public Integer postup(String title, String context, String pnum);

	

}
