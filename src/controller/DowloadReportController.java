package controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import utils.Report;

/**
 * Servlet implementation class DowloadReportController
 */
@WebServlet("/download")
public class DowloadReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DowloadReportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // Thiết lập thông tin trả về
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-disposition", "attachment; filename=report.docx");

	    response.flushBuffer();
	    OutputStream outStream = response.getOutputStream();
	    
	    Document doc = Report.createDocument(request);
	    doc.saveToFile(outStream, FileFormat.Docx);
	    outStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
