package com.example.why;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		ArrayList<memberVO> u = post.loginCheck(userInfo.getId(), userInfo.getPw());
		System.out.println(u);
		if (u.size() == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.'); </script>");
			out.flush();
			return "/login";
		}
		session.setAttribute("user", user);
		return "home";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
	@RequestMapping(value = "/newmember", method = RequestMethod.GET)
	public String newmember(HttpSession session) {
		
		return "newmember";
	}
	@RequestMapping(value = "/idcheck", method = RequestMethod.POST)
	@ResponseBody
	public String pet_delete(Model model,@RequestParam String id) {
		System.out.println(id);
		ArrayList<memberVO> idch = new ArrayList<memberVO>();
		idch = post.idcheck(id);
		System.out.println(idch);
		if(idch.size() == 0) {
			return "ok";
		}
			return "success";
	}
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String board() {
	
		
		return "board";
	}
}
