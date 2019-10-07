package com.example.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

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

}
