package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AssetDAO;
import dao.impl.AssetDAOImpl;
import model.Asset;

/**
 * Servlet implementation class AssetActionController
 */
@WebServlet("/asset/action")
public class AssetActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssetActionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			AssetDAO assetDAO = new AssetDAOImpl();
			Asset asset = assetDAO.get(id);
			
			request.setAttribute("asset", asset);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/assetActionView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("update")) {
			updateAsset(request, response);
		} else if(action.equalsIgnoreCase("delete")) {
			deleteAsset(request, response);
		}  
	}
	
	private void updateAsset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		AssetDAO assetDAO = new AssetDAOImpl();
		Asset asset = new Asset();
		asset.setId(id);
		asset.setName(name);
		asset.setDescription(description);
		
		assetDAO.update(asset);
		
		String successMessage = "Cập nhật thành công!";
//		request.setAttribute("successMessage", successMessage);
//		request.setAttribute("asset", asset);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/assetActionView.jsp");
//		dispatcher.forward(request, response);

		request.getSession().setAttribute("successMessage", successMessage);
		String url = request.getHeader("referer");
		response.sendRedirect(url);
//		response.sendRedirect(request.getContextPath() + "/asset/action?id=" + id);
	}
	
	private void deleteAsset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		
		AssetDAO assetDAO = new AssetDAOImpl();
		assetDAO.delete(id);
		
		response.sendRedirect(request.getContextPath() + "/asset");
	}

}
