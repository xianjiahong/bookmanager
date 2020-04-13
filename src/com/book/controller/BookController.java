package com.book.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.pojo.BookInfo;
import com.book.pojo.Pager;
import com.book.service.BookInfoService;
import com.book.service.BookInfoServiceImpl;
import com.mysql.jdbc.StringUtils;

/**
 * Servlet implementation class BookController
 */
@WebServlet("/BookController")
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookInfoService bis = new BookInfoServiceImpl();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		if ("show".equals(op)) {
			showInfo(request,response);
		}
	}
	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// ÉèÖÃ×Ö·û±àÂë
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		//µ÷ÓÃÉ¾³ýµÄ·½·¨
		boolean isOk = bis.deleteBook(id);
		if(isOk) {
			response.sendRedirect("admin/book-mgr.jsp");
		}else {
			response.sendRedirect("admin/book-mgr.jsp");
		}
	}

	private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//ÉèÖÃ×Ö·û±àÂë
		request.setCharacterEncoding("UTF-8");
		String bookName = request.getParameter("bookName");
		String author = request.getParameter("author");
		String categoryId = request.getParameter("categoryId");
		int price = Integer.parseInt(request.getParameter("price"));
		String publisher = request.getParameter("publisher");
		String photo = request.getParameter("photo");
		BookInfo book = new BookInfo();
		
	}
	private void showInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		showPageList(request, response);
		response.sendRedirect("user/index.jsp");
	}

	private void showPageList(HttpServletRequest request, HttpServletResponse response) {
		String pageIndex = request.getParameter("pageIndex");
		String bookName = request.getParameter("bookName");
		int currPage = 0;
		Pager pg = new Pager();
		int totalCount = bis.getcount(bookName);
		pg.setTotalCount(totalCount);
		if (StringUtils.isNullOrEmpty(pageIndex)) {
			currPage = 1;
		}else {
			if(Integer.parseInt(pageIndex)<=0) {
				currPage = 1;
			}else if(Integer.parseInt(pageIndex)>=pg.getTotalPages()) {
				currPage = pg.getTotalPages();
			}else {
				currPage = Integer.parseInt(pageIndex);
			}
		}
		pg.setCurrPage(currPage);
		int from = (currPage-1)*pg.getPageSize();
		List list = bis.getBookList(bookName, from, pg.getPageSize());
		pg.setPageLists(list);
		request.getSession().setAttribute("pg", pg);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
