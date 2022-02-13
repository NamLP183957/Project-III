package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import dao.SystemDAO;
import dao.impl.SystemDAOImpl;
import model.ITSystem;
import model.User;
import utils.MyUtils;
/**
 * Servlet implementation class SystemAddController
 */
@WebServlet("/system/add")
@MultipartConfig
public class SystemAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "images";
	
    public SystemAddController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/systemAddView.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		List<String> imageLinks = processImage(request);
		
		User user = MyUtils.getUserInSession(request);
		ITSystem system = new ITSystem();;
		system.setName(name);
		system.setDescription(description);
		
		SystemDAO systemDAO = new SystemDAOImpl();
		systemDAO.insert(user, system, imageLinks);
		
		response.sendRedirect(request.getContextPath() + "/system"); 
	}
	
	private List<String> processImage(HttpServletRequest request) throws IOException, ServletException {
		String realPath = request.getServletContext().getRealPath("/" + SAVE_DIR);
		realPath = realPath.replace('\\', '/');
		
		if(!Files.exists(Path.of(realPath))) {
			Files.createDirectory(Path.of(realPath));
		}
		
		List<String> imageLinks = new ArrayList<String>();
		List<Part> fileParts = request.getParts().stream().filter(part -> "image".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());
		for (Part filePart : fileParts) {
	        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	        filePart.write(realPath + "/" + fileName);
	        imageLinks.add(SAVE_DIR + "/" + fileName);
	    }
		
		return imageLinks;
		
	}
}
