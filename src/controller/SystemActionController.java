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

import org.mindrot.jbcrypt.BCrypt;

import dao.SystemDAO;
import dao.UserDAO;
import dao.impl.SystemDAOImpl;
import dao.impl.UserDAOImpl;
import model.User;
import utils.MyUtils;
import model.ITSystem;


@WebServlet("/system/action")
@MultipartConfig
public class SystemActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "images";   

    public SystemActionController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			User user = MyUtils.getUserInSession(request);

			SystemDAO systemDAO = new SystemDAOImpl();
			ITSystem system = systemDAO.getByUserId(user.getId());
			request.setAttribute("system", system);
			
			if(system != null) {
				List<String> images = systemDAO.getImages(system.getId());
				request.setAttribute("images", images);
			}
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/systemActionView.jsp");
			dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("update")) {
			updateSystem(request, response);
		} else if(action.equalsIgnoreCase("change-owner")) {
			changeOwnerSystem(request, response);
		} else if(action.equalsIgnoreCase("delete")) {
			deleteSystem(request, response);
		}  
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
	
	private void updateSystem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		List<String> imageLinks = processImage(request);
		
		ITSystem system = new ITSystem();
		system.setId(id);
		system.setName(name);
		system.setDescription(description);
		
		SystemDAO systemDAO = new SystemDAOImpl();
		systemDAO.update(system, imageLinks);
		
		String successMessage = "Cập nhật thông tin thành công!";
		request.setAttribute("successMessage", successMessage);
		request.setAttribute("system", system);
		request.setAttribute("images", imageLinks);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/systemActionView.jsp");
		dispatcher.forward(request, response);
//		request.getSession().setAttribute("successMessage", successMessage);
//		String url = request.getHeader("referer");
//		response.sendRedirect(url);
	}
	
	private boolean checkPass(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}
	
	private void changeOwnerSystem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String errorMesssage2 = "";
		boolean hasError = false;
		User user = MyUtils.getUserInSession(request);
		UserDAO userDAO = new UserDAOImpl();
		
		
		int id = Integer.parseInt(request.getParameter("id"));
		String new_owner = request.getParameter("new-owner");
		String new_owner_confirm = request.getParameter("new-owner-confirm");
		String password_confirm = request.getParameter("password-confirm");
		
		if(new_owner.equals(new_owner_confirm) == false) {
			errorMesssage2 = "Xác nhận tên chủ sở hữu mới không khớp!";
			hasError = true;
		} else if(checkPass(password_confirm, user.getPassword()) == false) {
			errorMesssage2 = "Mật khẩu không đúng!";
			hasError = true;
		} else if(userDAO.isExistAccount(new_owner) == false) {
			errorMesssage2 = "Tài khoản chủ sở hữu mới không tồn tại!";
			hasError = true;
		} else if(userDAO.hasSystem(new_owner) == true) {
			errorMesssage2 = "Chủ sở hữu mới đã sở hữu một hệ thống khác!";
			hasError = true;
		}
		
		if(hasError) {
//			request.setAttribute("errorMessage2", errorMesssage2);
//			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/systemActionView.jsp");
//			dispatcher.forward(request, response);
			request.getSession().setAttribute("errorMessage2", errorMesssage2);
			String url = request.getHeader("referer");
			response.sendRedirect(url);
		} else {
			SystemDAO systemDAO = new SystemDAOImpl();
			systemDAO.changeOwner(id, new_owner);
			response.sendRedirect(request.getContextPath() + "/system");
		}
	}

	private void deleteSystem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String errorMessage3 = "";
		User user = MyUtils.getUserInSession(request);
		
		int id = Integer.parseInt(request.getParameter("id"));
		String pwd = request.getParameter("password_confirm");
		String text = request.getParameter("text_confirm");
		boolean hasError = false;
		
		if( text.equals("I want to delete this system") == false) {
			errorMessage3 += "Câu xác nhận không đúng!";
			hasError = true;
		}
		
		if(checkPass(pwd, user.getPassword()) == false) {
			errorMessage3 += " Mật khẩu không đúng!";
			hasError = true;
		} 
		
		if(hasError) {
//			request.setAttribute("errorMessage3", errorMessage3);
//			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/systemActionView.jsp");
//			dispatcher.forward(request, response);
			request.getSession().setAttribute("errorMessage3", errorMessage3);
			String url = request.getHeader("referer");
			response.sendRedirect(url);
		} else {
			SystemDAO systemDAO = new SystemDAOImpl();
			systemDAO.delete(id);
			response.sendRedirect(request.getContextPath() + "/system");
		}
	}
}
