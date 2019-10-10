package com.example.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.example.domain.memberVO;
import com.example.domain.postVO;

public interface postmapper {
	public ArrayList<postVO> postList();

	public memberVO login(@Param("id") String id, @Param("pw") String pw);

	public ArrayList<memberVO> loginCheck(@Param("id") String id, @Param("pw") String pw);



}
