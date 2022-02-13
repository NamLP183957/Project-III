package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;
import utils.MyUtils;

/**
 * Servlet implementation class UserSetting
 */
@WebServlet("/user/setting")
public class UserSettingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			User user = MyUtils.getUserInSession(request);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userSettingView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
				
		if(action.equalsIgnoreCase("update-password")) {
			updatePassword(request, response);	
		} else if(action.equalsIgnoreCase("delete-account")) {
			deleteAccount(request, response);	
		}
		
		
	}
	
	private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		User user = MyUtils.getUserInSession(request);
		
		String old_pwd = request.getParameter("old_password");
		String new_pwd = request.getParameter("new_password");
		
		if(checkPass(old_pwd, user.getPassword()) == false) {
			String errorMessage1 = "Mật khẩu không đúng!";
//			request.setAttribute("errorMessage1", errorMessage1);
			request.getSession().setAttribute("errorMessage1", errorMessage1);
		} else {
			user.setPassword(hashPassword(new_pwd));
			
			UserDAO userDAO = new UserDAOImpl();
			userDAO.updatePassword(user);
			
			String successMessage = "Cập nhật thành công!";
//			request.setAttribute("successMessage", successMessage);
			request.getSession().setAttribute("successMessage", successMessage);
		}
//		ở đây dùng forward vẫn ok, vì user luôn lấy từ session
//		request.setAttribute("user", user);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userSettingView.jsp");
//		dispatcher.forward(request, response);	
		String url = request.getHeader("referer");
		response.sendRedirect(url);
	}
	
	private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		User user = MyUtils.getUserInSession(request);
		
		String pwd = request.getParameter("password_confirm");
		String text = request.getParameter("text_confirm");
		boolean hasError = false;
		String errorMessage2 = "";
		
		if( text.equals("I want to delete my account") == false) {
			errorMessage2 += "Câu xác nhận không đúng!";
			hasError = true;
			
		}

		if(checkPass(pwd, user.getPassword()) == false) {
			errorMessage2 += " Mật khẩu không đúng!";
			hasError = true;
		} 
		
		if(hasError) {
//			ở đây dùng forward vẫn ok, vì user luôn lấy từ session
//			request.setAttribute("user", user);
//			request.setAttribute("errorMessage2", errorMessage2);
//			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userSettingView.jsp");
//			dispatcher.forward(request, response);
			request.getSession().setAttribute("errorMessage2", errorMessage2);
			String url = request.getHeader("referer");
			response.sendRedirect(url);
		} else {
			UserDAO userDAO = new UserDAOImpl();
			userDAO.delete(user.getId());
			MyUtils.deleteUserInSession(request);
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	
	private String hashPassword(String plainTextPassword){
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}
	
	private boolean checkPass(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}

}
