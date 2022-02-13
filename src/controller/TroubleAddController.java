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
 * Servlet implementation class TroubleAddController
 */
@WebServlet("/trouble/add")
public class TroubleAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TroubleAddController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = MyUtils.getUserInSession(request);
			SystemDAO systemDAO = new SystemDAOImpl();
			ITSystem system = systemDAO.getByUserId(user.getId());
			
			AssetDAO assetDAO = new AssetDAOImpl();
			List<Asset> assets = assetDAO.getAllAssetsInSystem(system.getId());
			
			RiskDAO riskDAO = new RiskDAOImpl();
			List<Risk> risks = riskDAO.getAllRisksInSystem(system.getId());
			
			request.setAttribute("system_id", system.getId());
			request.setAttribute("risks", risks);
			request.setAttribute("assets", assets);
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/troubleAddView.jsp");
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
		
		int system_id = Integer.parseInt(request.getParameter("system_id"));
		String short_description = request.getParameter("short_description");
		String detail = request.getParameter("detail");
		int status = Integer.parseInt(request.getParameter("status"));
		String solution = request.getParameter("solution");
		
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String time_happen = date + " " + time + ":00";
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
		Trouble trouble = new Trouble(short_description, detail, status, solution,system_id, time_happen);
		troubleDAO.insert(trouble, assets, risks);
		
		String successMessage = "Thêm mới thành công!";
//		request.setAttribute("successMessage", successMessage);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/troubleAddView.jsp");
//		dispatcher.forward(request, response);
		request.getSession().setAttribute("successMessage", successMessage);
		String url = request.getHeader("referer");
		response.sendRedirect(url);
	}

}
