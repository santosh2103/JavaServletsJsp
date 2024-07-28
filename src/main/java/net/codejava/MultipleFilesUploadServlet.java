package net.codejava;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet("/upload_multiple")
@MultipartConfig(location = "E:\\Multiple_Files_Upload", fileSizeThreshold = 1024 * 1024, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 40 // 40 MB
)
public class MultipleFilesUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public MultipleFilesUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String description = request.getParameter("description");
		System.out.println(description);
		
		String message ="";

		try {
			Collection <Part> parts = request.getParts();
			for(Part filePart : parts) {
				String fileName = getFileName(filePart);
				if(fileName != null) {
					filePart.write(fileName);
				}	
			}
			
			message = "uploading Success";
		} catch(Exception ex) {
			message = "uploading Failed" + ex.getMessage();
		}
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("message.jsp").forward(request, response);
		
		
		
	}

	private String getFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		System.out.println(contentDisposition);

		if(!contentDisposition.contains("filename=")) {
			return null;
		}
		 
		int beginIndex= contentDisposition.indexOf("filename=") + 10;
		int endIndex = contentDisposition.length() - 1;
		
		return contentDisposition.substring(beginIndex, endIndex);
	}

}
