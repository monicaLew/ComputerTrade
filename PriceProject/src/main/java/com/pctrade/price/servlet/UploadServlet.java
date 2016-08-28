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
	// private int maxFileSize = 100 * 1024;
	private int maxMemSize = 9 * 1024; // проверить чет не работает
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
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File("c:\\temp")); // create folder in
															// file
															// system for ?

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// upload.setSizeMax(maxFileSize);
			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
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

					ArrayList<String> thisSession = new ArrayList<String>();
					thisSession.add((String) session.getAttribute("lastFileNameUpload"));
					thisSession.add(String.valueOf(sizeInKB));
					thisSession.add(dOfUpload);

					Object historyAttribute = session.getAttribute("fileUploadHistory");
					@SuppressWarnings("unchecked")
					ArrayList<ArrayList<String>> filesUploadHistory = (ArrayList<ArrayList<String>>) historyAttribute;
					if (filesUploadHistory == null) {
						filesUploadHistory = new ArrayList<ArrayList<String>>();
					}
					filesUploadHistory.add(thisSession);
					session.setAttribute("fileUploadHistory", filesUploadHistory);

					String encodeURL = response.encodeURL("/lastUploadFile.jsp");
					request.getRequestDispatcher(encodeURL).forward(request, response);
				}
			}
		} catch (Exception ex) {
			System.out.println("словили");
			session.setAttribute("exception", ex);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		System.out.println("doGET");
		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}