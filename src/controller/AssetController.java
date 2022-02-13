package controller;

import java.io.IOException;
import java.util.List;

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

/**
 * Servlet implementation class AssetController
 */
@WebServlet("/asset")
public class AssetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AssetController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = MyUtils.getUserInSession(request);
			SystemDAO systemDAO = new SystemDAOImpl();
			ITSystem system = systemDAO.getByUserId(user.getId());
			
			AssetDAO assetDAO = new AssetDAOImpl();
			List<Asset> assets = assetDAO.getAllAssetsInSystem(system.getId());
			
			request.setAttribute("assets", assets);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/assetView.jsp");
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
