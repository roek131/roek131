package com.example.why;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.domain.comentVO;
import com.example.domain.memberVO;
import com.example.domain.postVO;
import com.example.domain.postfile;
import com.example.service.postservice;
import com.google.common.io.ByteStreams;

import lombok.AllArgsConstructor;
import net.sf.json.JSONObject;


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
		 return "redirect:/";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value = "/newmember", method = RequestMethod.GET)
	public String newmember(HttpSession session) {
		
		return "newmember";
	}
	@RequestMapping(value = "/upid", method = RequestMethod.POST)
	@ResponseBody
	public String upid(Model model,@RequestParam String id,@RequestParam String pw,@RequestParam String name) {
		System.out.println(id + pw + name );
		ArrayList<memberVO> idup = new ArrayList<memberVO>();
	   Integer upid = post.newmember(id,pw,name);
			return "success";
	} 
	
	@RequestMapping(value = "/idcheck", method = RequestMethod.POST)
	@ResponseBody
	public String idcheck(Model model,@RequestParam String id) {
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
	@RequestMapping(value = "/newboard", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject newboard(Model model,postVO postVO,HttpSession session, HttpServletRequest request) {
		System.out.println("글쓰기");
		String name = ((memberVO) request.getSession().getAttribute("user")).getName();
		Map<String, Integer> map = new HashMap<String, Integer>();
		postVO.setName(name);
		int newboard = post.newpost(postVO);
		System.out.println(postVO);
		map.put("result", newboard);
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		return json;
	}
	@RequestMapping(value = "/delpost", method = RequestMethod.POST)
	@ResponseBody
	public String delpost(Model model,@RequestParam String pnum,HttpSession session, HttpServletRequest request) {
		System.out.println(pnum);
		String name = ((memberVO) request.getSession().getAttribute("user")).getName();
		Integer del = post.postdel(pnum,name);
		System.out.println(del);
		if(del == 0) {
			return "no";
		}
			return "success";
	}

	@RequestMapping(value = "/post/{pnum}", method = RequestMethod.GET)
	public String post(@PathVariable String pnum,Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		System.out.println("ㅎ이"+pnum);
		ArrayList<postVO> postdetail = new ArrayList<postVO>();
		ArrayList<comentVO> comnets = new ArrayList<comentVO>();
		ArrayList<postfile> file = new ArrayList<postfile>();
		comnets = post.coment(pnum);
		file = post.file(pnum);
		System.out.println(comnets);
		
		Cookie[] cookies = request.getCookies();     
        Cookie viewCookie = null;
        if (cookies != null && cookies.length > 0) 
        {
            for (int i = 0; i < cookies.length; i++)
            {
                // Cookie의 name이 cookie + reviewNo와 일치하는 쿠키를 viewCookie에 넣어줌 
                if (cookies[i].getName().equals("cookie"+pnum))
                { 
                    System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
                    viewCookie = cookies[i];
                }
            }
        }
        if (postdetail != null) {
            System.out.println("System - 해당 상세 리뷰페이지로 넘어감");
            
            // 만일 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리함.
            if (viewCookie == null) {    
                System.out.println("cookie 없음");
                
                // 쿠키 생성(이름, 값)
                Cookie newCookie = new Cookie("cookie"+pnum, "|" + pnum + "|");
                                
                // 쿠키 추가
                response.addCookie(newCookie);
 
                // 쿠키를 추가 시키고 조회수 증가시킴
                int result = post.lookup(pnum);
                
                if(result>0) {
                    System.out.println("조회수 증가");
                }else {
                    System.out.println("조회수 증가 에러");
                }
            }
            // viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
            else {
                System.out.println("cookie 있음");
                
                // 쿠키 값 받아옴.
                String value = viewCookie.getValue();
                
                System.out.println("cookie 값 : " + value);
        
            }
            
        } 
        postdetail = post.postdetail(pnum);
		model.addAttribute("post", postdetail);
		model.addAttribute("coment", comnets);
		model.addAttribute("file", file);
		return "post";
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject reply(Model model,comentVO comentVO,HttpSession session, HttpServletRequest request) {
		System.out.println("댓글쓰기");
	
		String name = ((memberVO) request.getSession().getAttribute("user")).getName();
			
		Map<String, Integer> map = new HashMap<String, Integer>();
		comentVO.setName(name);
		System.out.println(comentVO);
	
		int newreply = post.newreply(comentVO);
		System.out.println(newreply);
		map.put("result", newreply);
		
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		
			return json;
	}
//	@RequestMapping(value = "/uppost/{pnum}", method = RequestMethod.GET)
//	public String uppost( Model model,@PathVariable String pnum) {
//		System.out.println("수정페이지");
//		System.out.println(pnum);
//		ArrayList<postVO> postlist = new ArrayList<postVO>();
//			postlist = post.postList();
//			model.addAttribute("post", postlist);
////		
//		return "uppost";
//	}

	@RequestMapping(value = "/uppost/{pnum}", method = RequestMethod.GET)
	public String upposts( Model model,@PathVariable String pnum) {
		System.out.println("수정페이지"+pnum);
		
		ArrayList<postVO> postlist = new ArrayList<postVO>();
			postlist = post.postdetail(pnum);
			model.addAttribute("post", postlist);
//		
		return "uppost";
	}
	
	@RequestMapping(value = "/upposts", method = RequestMethod.POST)
	@ResponseBody
	public String upposts(Model model,@RequestParam String pnum,HttpSession session, HttpServletRequest request,@RequestParam String title,@RequestParam String context) {
		System.out.println("호이"+pnum);
		System.out.println(title + context);
		
		Integer postup = post.postup(title , context,pnum);
		System.out.println(postup);
		
		return "success";
	}
//파일 업로드
	@RequestMapping(value = "/fileUpload") //ajax에서 호출하는 부분
    @ResponseBody
    public JSONObject uploads(MultipartHttpServletRequest multipartRequest) { //Multipart로 받는다.
          
        Iterator<String> itr =  multipartRequest.getFileNames();
         System.out.println(multipartRequest);
        String filePath = "D:/test"; //설정파일로 뺀다.
        Map<String, String> map = new HashMap<String, String>();
		Map<String, Integer> maps = new HashMap<String, Integer>();
        while (itr.hasNext()) { //받은 파일들을 모두 돌린다.
             
            /* 기존 주석처리
            MultipartFile mpf = multipartRequest.getFile(itr.next());
            String originFileName = mpf.getOriginalFilename();
            System.out.println("FILE_INFO: "+originFileName); //받은 파일 리스트 출력'
            */
             
            MultipartFile mpf = multipartRequest.getFile(itr.next());
      
            String originalFilename = mpf.getOriginalFilename(); //파일명
           
            String fileFullPath = filePath+"/"+originalFilename; //파일 전체 경로
            map.put("name",originalFilename);
            
            try {
                //파일 저장
            	 long filesize = mpf.getSize();
                mpf.transferTo(new File(fileFullPath)); //파일저장 실제로는 service에서 처리
                Integer fileup = post.fileup(fileFullPath,originalFilename);
                System.out.println("originalFilename => "+originalFilename);
                System.out.println("fileFullPath => "+fileFullPath);
                System.out.println(filesize);
            } catch (Exception e) {
                System.out.println("postTempFile_ERROR======>"+fileFullPath);
                e.printStackTrace();
            }
                          
       }
        JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
        return json;
    }
	
	@RequestMapping(value = "/fileUpload/post") //ajax에서 호출하는 부분
    @ResponseBody
    public String upload(MultipartHttpServletRequest multipartRequest,@RequestParam String pnum) { //Multipart로 받는다.
          
        Iterator<String> itr =  multipartRequest.getFileNames();
         
        String filePath = "D:/test"; //설정파일로 뺀다.
         
        while (itr.hasNext()) { //받은 파일들을 모두 돌린다.
             
            /* 기존 주석처리
            MultipartFile mpf = multipartRequest.getFile(itr.next());
            String originFileName = mpf.getOriginalFilename();
            System.out.println("FILE_INFO: "+originFileName); //받은 파일 리스트 출력'
            */
             
            MultipartFile mpf = multipartRequest.getFile(itr.next());
      
            String originalFilename = mpf.getOriginalFilename(); //파일명
           
            String fileFullPath = filePath+"/"+originalFilename; //파일 전체 경로
            
            
            try {
                //파일 저장
            	 byte[] filesize = mpf.getBytes();
                mpf.transferTo(new File(fileFullPath)); //파일저장 실제로는 service에서 처리
                System.out.println("originalFilename => "+originalFilename);
                System.out.println("fileFullPath => "+fileFullPath);
      
            } catch (Exception e) {
                System.out.println("postTempFile_ERROR======>"+fileFullPath);
                e.printStackTrace();
            }
                          
       }
          
        return "success";
    }
	//다운로드
	@RequestMapping("/filedown")//URL호출
	public void getFile(@RequestParam Map<String,Object> map, HttpServletResponse response) throws Exception {
	     
	    String filePath = (String) map.get("filePath"); //파일 전체경로(파일명도 포함)
	    String oriFileName = (String) map.get("oriFileName"); //파일 원본 경로
	     
	    String docName = URLEncoder.encode(oriFileName,"UTF-8").replaceAll("\\+", "%20"); //한글파일명 깨지지 않도록
	    response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
	    response.setContentType("text/plain");
	 
	    File down_file = new File(filePath); //파일 생성
	    FileInputStream fileIn = new FileInputStream(down_file); //파일 읽어오기
	    ByteStreams.copy(fileIn, response.getOutputStream());
	    response.flushBuffer();
	 
	}
}
