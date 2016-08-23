package servlet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import com.mysql.fabric.xmlrpc.base.Data;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 100 * 1024;
   private int maxMemSize = 9 * 1024;
   private File file ;

   public void init( ){
      	   
      filePath = getServletContext().getInitParameter("file-upload"); // store directory(described in web.xml
   }
   public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException {
	   request.setCharacterEncoding("UTF-8");	
	   HttpSession session = request.getSession();
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      response.setCharacterEncoding("UTF-8");
      java.io.PrintWriter out = response.getWriter( );
      if( !isMultipart ){
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
      
      //File file = File.createTempFile("prefix", "suffix");         !!
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
     
      upload.setSizeMax( maxFileSize );

      try{ 
      // Parse the request to get file items.
      List fileItems = upload.parseRequest(request);
	
      // Process the uploaded file items
      Iterator i = fileItems.iterator();

      
      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {            
            String fieldName = fi.getFieldName();          // Get the uploaded file parameters
            String fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
           Random rand = new Random();
           String s = String.valueOf(rand.nextInt(100));
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){      
//            	String first = fileName.substring(fileName.lastIndexOf("\\"),fileName.lastIndexOf("."));  //сохр как? с разными именами
//            	System.out.println(first);            	            	
//            	String second = fileName.substring(fileName.lastIndexOf("."));
//            	System.out.println(second);
//            	System.out.println(first + second + " *************"); 
//            	 file = new File(filePath + first + s + second);
               file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\"))) ;   //fileName = fileName.substring(fileName.lastIndexOf(File.separatorChar)+1);
               
               session.setAttribute("lastFileNameUpload", fileName.substring( fileName.lastIndexOf("\\")+1));               
            }
            else{            	
               file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
               session.setAttribute("lastFileNameUpload", fileName.substring( fileName.lastIndexOf("\\")+2));
            }
            fi.write( file ) ;         
            
            LocalDate dOfUpload2 = LocalDate.now( ZoneId.of( "Europe/Minsk" )) ;
            System.out.println(dOfUpload2 + " MmmiiinnnsskkK" );
            Date date = new Date();
             SimpleDateFormat  dateFormat = new SimpleDateFormat();
            String dOfUpload = dateFormat.format(date);
            
            int sizeInKB = (int)sizeInBytes/1024;

            ArrayList<String> thisSession = new ArrayList<String>();
            
            thisSession.add((String)session.getAttribute("lastFileNameUpload"));
            thisSession.add(String.valueOf(sizeInKB));
            thisSession.add(dOfUpload);

            Object historyAttribute = session.getAttribute("fileUploadHistory");
            ArrayList<ArrayList<String>> filesUploadHistory = (ArrayList<ArrayList<String>>)historyAttribute;
            if(filesUploadHistory==null){
            	filesUploadHistory = new ArrayList<ArrayList<String>>();
            }                        
            filesUploadHistory.add(thisSession);
            
            session.setAttribute("fileUploadHistory", filesUploadHistory);
            
            session.setAttribute("fileSize", sizeInKB);
            session.setAttribute("dateOfUpload", dOfUpload);
            
            String encodeURL = response.encodeURL("/lastUploadFile.jsp");
            request.getRequestDispatcher(encodeURL).forward(request, response);
         }
      }

   }catch(Exception ex) {
	   out.println("<html>");
       out.println("<head>");
       out.println("<title>SVC Write</title>");  
       out.println("</head>");
       out.println("<body>");       
	   out.println("<h2> UploadFail ! </h2>"); 
	   out.println("<h3> Check File matching </h3>"); 
	   out.println("<br>"); 
	   out.println("<br>"); 
	   out.println("<form action=\"index.jsp\" method=\"post\" >"); 
	   out.println("<input type=\"submit\" value=\"Back to Menu\" />"); 
	   out.println("</form>");
	   out.println("</body>");
       out.println("</html>");
       System.out.println(ex);
   }
   }
   public void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        System.out.println("doGET");       
	   
        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
   } 
}