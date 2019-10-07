package com.example.why;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.memberVO;
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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(memberVO userInfo, HttpSession session, Model model, HttpServletResponse response)
			throws IOException {
		System.out.println("여기옴?");
		System.out.println(userInfo.getId());
		System.out.println(userInfo.getPw());
		memberVO user= post.login(userInfo.getId(),userInfo.getPw());
		session.setAttribute("user", user);
		return "home";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginForm(HttpSession session) {
		session.invalidate();
		return "home";
	}
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String board() {
	
		
		return "board";
	}
}
