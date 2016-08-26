package com.pctrade.price.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoUploadedFileImpl;
import com.pctrade.price.entity.UploadedFile;

public class FileInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String fileName = (String) session.getAttribute("lastFileNameUpload");
		int fileSize = (Integer) session.getAttribute("fileSize");
		String date = (String) session.getAttribute("dateOfUpload");

		DaoUploadedFileImpl daoUploadedFile = new DaoUploadedFileImpl();
		UploadedFile uploadedFile = new UploadedFile();
		uploadedFile.setFileName(fileName);
		uploadedFile.setSizeKb(fileSize);
		uploadedFile.setUploadDate(date);

		daoUploadedFile.createUploadedFileInfo(uploadedFile);
		System.out.println(" you were here  0_o");

		String encodeURL = response.encodeURL("/lastUploadFile.jsp");
		request.getRequestDispatcher(encodeURL).forward(request, response);
	}

}
