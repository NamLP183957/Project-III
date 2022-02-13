package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;
import utils.MyUtils;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = MyUtils.getUserInSession(request);
		if(user == null) {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/system"); 
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserDAO userDAO = new UserDAOImpl();
		User user = userDAO.get(username);
		
		if(user == null || checkPass(password, user.getPassword()) == false) {
			String errorMessage = "Tên đăng nhập hoặc Mật khẩu không đúng!";
			 
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("uname", username);
            request.setAttribute("pwd", password);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
            return;
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/system"); 
		}
	}
	
	private boolean checkPass(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}

}
