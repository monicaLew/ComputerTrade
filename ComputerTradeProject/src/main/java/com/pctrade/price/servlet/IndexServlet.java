package com.pctrade.price.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)         // почеум гет? пост не проходит
			throws ServletException, IOException {
		System.out.println("yoou were in a IndexServlet " + System.currentTimeMillis());
		request.getRequestDispatcher("mainMenu.jsp").forward(request, response);
	}
}
