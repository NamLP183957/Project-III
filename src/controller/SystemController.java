package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SystemDAO;
import dao.impl.SystemDAOImpl;
import model.User;
import utils.MyUtils;
import model.ITSystem;

@WebServlet("/system")
public class SystemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SystemController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			User user = MyUtils.getUserInSession(request);

			SystemDAO systemDAO = new SystemDAOImpl();
			ITSystem system = systemDAO.getByUserId(user.getId());
			request.setAttribute("system", system);
			
			if(system != null) {
				List<String> images = systemDAO.getImages(system.getId());
				request.setAttribute("images", images);
			}
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/systemView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
