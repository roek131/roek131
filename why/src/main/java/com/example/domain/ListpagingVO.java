package com.example.domain;

import lombok.Data;

@Data
public class ListpagingVO {
	 private int startRow;
	  private int endRow;
	   
	  /** 현재페이지 */
	    private int pageIndex = 1;
	     
	    /** 페이지사이즈 */
	    private int pageSize = 10; //한페이지에 나오는 게시물 개수
	     
	    private int pageGroupSize = 3;

}
