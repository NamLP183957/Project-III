package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AssetDAO;
import dao.SystemDAO;
import dao.impl.AssetDAOImpl;
import dao.impl.SystemDAOImpl;
import model.Asset;
import model.ITSystem;
import model.User;
import utils.MyUtils;


@WebServlet("/asset/add")
public class AssetAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AssetAddController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			User user = MyUtils.getUserInSession(request);
			SystemDAO systemDAO = new SystemDAOImpl();
			ITSystem system = systemDAO.getByUserId(user.getId());
			
			request.setAttribute("system_id", system.getId());
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/assetAddView.jsp");
			dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int system_id = Integer.parseInt(request.getParameter("system_id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		AssetDAO assetDAO = new AssetDAOImpl();
		Asset asset = new Asset();
		asset.setName(name);
		asset.setDescription(description);
		asset.setSystem_id(system_id);
		
		assetDAO.insert(asset);
		
		String successMessage = "Thêm mới thành công!";
		request.setAttribute("successMessage", successMessage);
		request.setAttribute("system_id", system_id);
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/assetAddView.jsp");
		dispatcher.forward(request, response);
	}

}
