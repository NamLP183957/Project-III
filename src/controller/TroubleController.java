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
import dao.TroubleDAO;
import dao.impl.SystemDAOImpl;
import dao.impl.TroubleDAOImpl;
import model.ITSystem;
import model.Trouble;
import model.User;
import utils.MyUtils;

/**
 * Servlet implementation class TroubleController
 */
@WebServlet("/trouble")
public class TroubleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TroubleController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = MyUtils.getUserInSession(request);
			SystemDAO systemDAO = new SystemDAOImpl();
			ITSystem system = systemDAO.getByUserId(user.getId());
			
			TroubleDAO troubleDAO = new TroubleDAOImpl();
			List<Trouble> list = troubleDAO.getAllInSystem(system.getId());
			
			request.setAttribute("troubles", list);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/troubleView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
