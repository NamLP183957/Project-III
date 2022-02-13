package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AssetDAO;
import dao.RiskDAO;
import dao.SystemDAO;
import dao.TroubleDAO;
import dao.impl.AssetDAOImpl;
import dao.impl.RiskDAOImpl;
import dao.impl.SystemDAOImpl;
import dao.impl.TroubleDAOImpl;
import model.Asset;
import model.ITSystem;
import model.Risk;
import model.Trouble;
import model.User;
import utils.MyUtils;

/**
 * Servlet implementation class TroubleActionController
 */
@WebServlet("/trouble/action")
public class TroubleActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TroubleActionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			User user = MyUtils.getUserInSession(request);
			SystemDAO systemDAO = new SystemDAOImpl();
			ITSystem system = systemDAO.getByUserId(user.getId());
			
			TroubleDAO troubleDAO = new TroubleDAOImpl();
			Trouble trouble = troubleDAO.get(id);
			
			AssetDAO assetDAO = new AssetDAOImpl();
			List<Asset> allAssets = assetDAO.getAllAssetsInSystem(system.getId());
			List<Asset> assets = assetDAO.getAssetsOfTrouble(id);
			
			RiskDAO riskDAO = new RiskDAOImpl();
			List<Risk> allRisks = riskDAO.getAllRisksInSystem(system.getId());
			List<Risk> risks = riskDAO.getRisksOfTrouble(id);
			
			request.setAttribute("system_id", system.getId());
			request.setAttribute("trouble", trouble);
			request.setAttribute("risks", risks);
			request.setAttribute("assets", assets);
			request.setAttribute("allRisks", allRisks);
			request.setAttribute("allAssets", allAssets);
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/troubleActionView.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("update")) {
			updateTrouble(request, response);
		} else if(action.equalsIgnoreCase("delete")) {
			deleteTrouble(request, response);
		} 
	}
	
	private void updateTrouble(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String short_description = request.getParameter("short_description");
		String detail = request.getParameter("detail");
		int status = Integer.parseInt(request.getParameter("status"));
		String solution = request.getParameter("solution");
		
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		if(time.length() <= 5 ) {
			time = time + ":00";
		}
		String time_happen = date + " " + time;
//		System.out.println(time_happen);
		String tmp[] = request.getParameterValues("tag_asset");
		List<Integer> assets = new ArrayList<>();
		if(tmp != null) {
			for(String s: tmp) {
				assets.add(Integer.parseInt(s));
			}
		}
		
		tmp = request.getParameterValues("tag_risk");
		List<Integer> risks = new ArrayList<>();
		if(tmp != null) {
			for(String s: tmp) {
				risks.add(Integer.parseInt(s));
			}
		}
		
		TroubleDAO troubleDAO = new TroubleDAOImpl();
		Trouble trouble = new Trouble(id, short_description, detail, status, solution, time_happen);
		troubleDAO.update(trouble, assets, risks);
		
		String successMessage = "Cập nhật thành công!";
//		request.setAttribute("successMessage", successMessage);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/troubleAddView.jsp");
//		dispatcher.forward(request, response);
		request.getSession().setAttribute("successMessage", successMessage);
		String url = request.getHeader("referer");
		response.sendRedirect(url);
	}
	
	private void deleteTrouble(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		
		TroubleDAO troubleDAO = new TroubleDAOImpl();
		troubleDAO.delete(id);
		
		response.sendRedirect(request.getContextPath() + "/trouble");
	}

}
