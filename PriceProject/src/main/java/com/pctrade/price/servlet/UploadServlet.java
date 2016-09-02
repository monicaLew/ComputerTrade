package com.pctrade.price.servlet;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pctrade.price.entity.UploadedFile;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean isMultipart;
	private String filePath;
	private File file;

	public void init() {
		filePath = getServletContext().getInitParameter("file-upload");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		// Check that we have a file upload request
		try {
			isMultipart = ServletFileUpload.isMultipartContent(request);
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if (!isMultipart) {
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Servlet upload</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<p>No file uploaded</p>");
				out.println("</body>");
				out.println("</html>");
				return;
			}

			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator<FileItem> i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					String fileName = fi.getName();
					long sizeInBytes = fi.getSize();
					file = new File(filePath + fileName);
					fi.write(file);

					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat();
					String dOfUpload = dateFormat.format(date);
					int sizeInKB = (int) sizeInBytes / 1024;
					session.setAttribute("fileSize", sizeInKB);
					session.setAttribute("dateOfUpload", dOfUpload);
					session.setAttribute("lastFileNameUpload", fileName);

					UploadedFile uploadedFileInfo = new UploadedFile();
					uploadedFileInfo.setFileName(fileName);
					uploadedFileInfo.setSizeKb(sizeInKB);
					uploadedFileInfo.setUploadDate(dOfUpload);
					session.setAttribute("uploadedFileInfo", uploadedFileInfo);

					String encodeURL = response.encodeURL("/lastUploadFile.jsp");
					request.getRequestDispatcher(encodeURL).forward(request, response);
				}
			}
		} catch (Exception ex) {			
			session.setAttribute("exception", ex);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {		
		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}