package com.example.why;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.postVO;
import com.example.service.postservice;


import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class HomeController {

	private postservice post;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home( Model model) {
		System.out.println("아");
		ArrayList<postVO> postlist = new ArrayList<postVO>();
			postlist = post.postList();
			model.addAttribute("post", postlist);
		
		return "home";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
	
		
		return "login";
	}
	
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String board() {
	
		
		return "board";
	}
}
