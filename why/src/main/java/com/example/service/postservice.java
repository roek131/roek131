package com.example.service;

import java.util.ArrayList;

import com.example.domain.comentVO;
import com.example.domain.memberVO;
import com.example.domain.postVO;
import com.example.domain.postfile;


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

	public int lookup(String pnum);

	public Integer fileup(String fileFullPath, String originalFilename);

	public ArrayList<postfile> file(String pnum);

	public Integer totalcount(postVO postVO);

	public int totalcount();

	public ArrayList<postVO> page(int anm, int pnm);

	

}
