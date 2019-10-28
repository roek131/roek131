package com.example.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.example.domain.comentVO;
import com.example.domain.memberVO;
import com.example.domain.postVO;

public interface postmapper {
	public ArrayList<postVO> postList();

	public memberVO login(@Param("id") String id, @Param("pw") String pw);

	public ArrayList<memberVO> loginCheck(@Param("id") String id, @Param("pw") String pw);

	public ArrayList<memberVO> idcheck(@Param("id") String id);

	public Integer newmember(@Param("id") String id, @Param("pw") String pw,@Param("name") String name);

	public ArrayList<postVO> postdetail(@Param("pnum") String pnum);

	public ArrayList<comentVO> coment(@Param("pnum") String pnum);

	public int newreply(comentVO comentVO);

	public int newpost(postVO postVO);

	public int postdel(@Param("pnum") String pnum, @Param("name") String name);


}
