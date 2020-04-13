package com.book.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.pojo.BookOrders;
import com.book.service.OrderService;
import com.book.service.OrderServiceImpl;

/**
 * Servlet implementation class OrdersController
 */
@WebServlet("/OrdersController")
public class OrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrderService os = new OrderServiceImpl();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdersController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		if("add".equals(op)) {
			addCar(request,response);
		}else if("showCar".equals(op)) {
			showOrderList(request,response);
		}else if("update".equals(op)) {
			updateOrder(request,response);
		}
	}

	private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		String oid = request.getParameter("oid");
		int count = Integer.parseInt(request.getParameter("count"));
		double price = Double.parseDouble(request.getParameter("price"));
		double curPrice = price*count;
		boolean isOk = os.updateOrders(oid,count,curPrice);
		if(isOk) {
			pw.write("{\"result\":\"true\"}");
		}else {
			pw.write("{\"result\":\"false\"}");
		}
		pw.flush();
		pw.close();
		
	}

	private void showOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = ( String)request. getSession( ) . getAttribute("showUser");
		List<BookOrders> list = os.findOrders(userId);
		request.getSession().setAttribute("list", list);
		response.sendRedirect("user/cart.jsp");
	}

	private void addCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		double price = Integer.parseInt(request.getParameter("price"));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		String orderId = sdf.format(date);
		int rand = (int)(Math.random()*(999999-100000+1)+100000);
		orderId += rand;
		System.out.println("���:"+orderId);
		String userId = (String) request.getSession().getAttribute("showUser");
		BookOrders order = new BookOrders(orderId, id, 1, price, date, userId);
		boolean isOk = os.addOrders(order);
		PrintWriter pw = response.getWriter();
		if(isOk) {
			pw.write("{\"result\":\"true\"}");
		}else {
			pw.write("{\"result\":\"false\"}");
		}
		pw.flush();
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
