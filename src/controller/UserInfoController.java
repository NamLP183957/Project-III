package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;
import utils.MyUtils;


@WebServlet("/user/info")
public class UserInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			User user = MyUtils.getUserInSession(request);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String successMessage = "";
		
		User user = MyUtils.getUserInSession(request);
		
		UserDAO userDAO = new UserDAOImpl();
		
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String organization = request.getParameter("organization");
		String position = request.getParameter("position");
		
		
		user.setName(name);
		user.setPhone(phone);
		user.setOrganization(organization);
		user.setPosition(position);
		
		userDAO.updateInfo(user);
		successMessage = "Cập nhật thông tin thành công!";
		request.setAttribute("successMessage", successMessage);
		request.setAttribute("user", user);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
		dispatcher.forward(request, response);
//		request.getSession().setAttribute("successMessage", successMessage);
//		String url = request.getHeader("referer");
//		response.sendRedirect(url);
	}

}
