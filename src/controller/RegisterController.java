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



@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        String errorMessage1 = "";
        String errorMessage2 = "";
        boolean hasError = false;
        String successMessage = "Đăng ký thành công!";
        UserDAO userDAO = new UserDAOImpl();
        
        if(userDAO.checkUsername(email)== false) {
        	errorMessage1 = "Tên tài khoản đã tồn tại!";
        	hasError = true;
        }
        
        if(userDAO.checkEmail(email)== false) {
        	errorMessage2 = "Email đã được sử dụng!";
        	hasError = true;
        }
        
        if(hasError) {
        	 request.setAttribute("errorMessage1", errorMessage1);
        	 request.setAttribute("errorMessage2", errorMessage2);
        	 request.setAttribute("email", email);
             request.setAttribute("uname", username);
             request.setAttribute("pwd", password);
             
        } else {
        	request.setAttribute("successMessage", successMessage);
        	User user = new User();
        	user.setEmail(email);
        	user.setUsername(username);
        	user.setPassword(hashPassword(password));
        	userDAO.insert(user);
        }
        
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");
        dispatcher.forward(request, response);
       
	}
	
	private String hashPassword(String plainTextPassword){
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

}
