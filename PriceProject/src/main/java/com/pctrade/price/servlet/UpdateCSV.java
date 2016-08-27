package com.pctrade.price.servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;

public class UpdateCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String date = (String)session.getAttribute("dateOfUpload");
		DaoProductImpl daoProductImpl = new DaoProductImpl();
		daoProductImpl.setNotAvailableStatusForAll();

		int incorr = 0;
		ArrayList<String> justOpenList = new ArrayList<String>();

		BufferedReader fileOut = new BufferedReader(new InputStreamReader(new FileInputStream(
				getServletContext().getInitParameter("file-upload") + session.getAttribute("lastFileNameUpload")),
				"utf-8"));
		for (String line; (line = fileOut.readLine()) != null;) {
			String[] arr = line.split(",");
			// System.out.println(count + ": " + line);
			try{
			Product product = new Product();
			product.setArticleCode(Integer.parseInt(arr[0].trim()));
			product.setArticle(arr[1]);
			product.setPrice(Integer.parseInt(arr[2].trim()));
			product.setDate(date);
			
			int check = daoProductImpl.checkProduct(product);       // проверка есть ли такой артиклКод в базе
			
			if (check == 0){
				daoProductImpl.createProduct(product);             
			}
			else{
				daoProductImpl.updateProduct(product);
			}			
			
			if (arr.length < 3) {
				incorr++;
			}
			justOpenList.add(line);
			}catch (NumberFormatException e) {
				System.out.println(" Здравствуйте, а это Опять Я   !! !! ");
			}
		}
		fileOut.close();

		session.setAttribute("incorr", incorr);
		session.setAttribute("justfileList", justOpenList);	

	}

}
