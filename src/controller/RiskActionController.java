package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AssetDAO;
import dao.ImpactLevelDAO;
import dao.LikelihoodLevelDAO;
import dao.RiskDAO;
import dao.RiskLevelDAO;
import dao.impl.AssetDAOImpl;
import dao.impl.ImpactLevelDAOImpl;
import dao.impl.LikelihoodLevelDAOImpl;
import dao.impl.RiskDAOImpl;
import dao.impl.RiskLevelDAOImpl;
import model.Asset;
import model.ImpactLevel;
import model.LikelihoodLevel;
import model.Risk;
import model.RiskLevel;
import utils.MyUtils;

/**
 * Servlet implementation class RiskActionController
 */
@WebServlet("/risk/action")
public class RiskActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskActionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			RiskDAO riskDAO = new RiskDAOImpl();
			Risk risk = riskDAO.get(id);
			
			AssetDAO assetDAO = new AssetDAOImpl();
			List<Asset> allAssets = assetDAO.getAllAssetsInSystem(risk.getSystem_id());
			List<Asset> assets = assetDAO.getAssetsOfRisk(id);
			
			LikelihoodLevelDAO likelihoodLevelDAO = new LikelihoodLevelDAOImpl();
			Map<Integer, LikelihoodLevel> likelihoods = likelihoodLevelDAO.getAllInSystem(risk.getSystem_id());
			
			ImpactLevelDAO impactLevelDAO = new ImpactLevelDAOImpl();
			Map<Integer, ImpactLevel> impacts = impactLevelDAO.getAllInSystem(risk.getSystem_id());
			
			RiskLevelDAO riskLevelDAO = new RiskLevelDAOImpl();
			Map<Integer, RiskLevel> risk_levels = riskLevelDAO.getAllInSystem(risk.getSystem_id());
			
			request.setAttribute("risk", risk);
			request.setAttribute("assets", assets);
			request.setAttribute("allAssets", allAssets);
			request.setAttribute("likelihoods", MyUtils.mapToList(likelihoods));
			request.setAttribute("impacts", MyUtils.mapToList(impacts));
			request.setAttribute("risk_levels", MyUtils.mapToList(risk_levels));
			request.setAttribute("l", likelihoods.get(risk.getLikelihood_level_id()));
			request.setAttribute("i", impacts.get(risk.getImpact_level_id()));
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/riskActionView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("update")) {
			updateRiskInfo(request, response);
		} else if(action.equalsIgnoreCase("assess")) {
			updateRiskAssessment(request, response);
		} else if(action.equalsIgnoreCase("delete")) {
			deleteRisk(request, response);
		} 
	}
	
	private void updateRiskInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String short_description = request.getParameter("short_description");
		String flaw = request.getParameter("flaw");
		String threat = request.getParameter("threat");
		int threat_type = Integer.parseInt(request.getParameter("threat-type"));
		String solution = request.getParameter("solution");
		
		String tmp[] = request.getParameterValues("tag_asset");
		List<Integer> assets = new ArrayList<>();
		if(tmp != null) {
			for(String s: tmp) {
				assets.add(Integer.parseInt(s));
			}
		}
		
		
		Risk risk = new Risk(id, short_description, flaw, threat, threat_type, solution);
		RiskDAO riskDAO = new RiskDAOImpl();
		riskDAO.updateInfo(risk, assets);
		
		String successMessage1 = "Cập nhật thông tin thành công!";
//		request.setAttribute("successMessage1", successMessage1);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/riskActionView.jsp");
//		dispatcher.forward(request, response);
		request.getSession().setAttribute("successMessage1", successMessage1);
		String url = request.getHeader("referer");
		response.sendRedirect(url);
//		response.sendRedirect(request.getContextPath() + "/risk/action?id=" + id);
	}

	private void updateRiskAssessment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		
		int likelihood = -1;
		if(request.getParameter("likelihood") != null) {
			likelihood = Integer.parseInt(request.getParameter("likelihood"));
		}
		
		int impact = -1;
		if(request.getParameter("impact") != null) {
			impact = Integer.parseInt(request.getParameter("impact"));
		}

		
		RiskDAO riskDAO = new RiskDAOImpl();
		riskDAO.updateAssessment(id, likelihood, impact);
		
		String successMessage2 = "Cập nhật đánh giá thành công!";
//		request.setAttribute("successMessage2", successMessage2);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/riskActionView.jsp");
//		dispatcher.forward(request, response);	
		request.getSession().setAttribute("successMessage2", successMessage2);
		String url = request.getHeader("referer");
		response.sendRedirect(url);
	}
	
	private void deleteRisk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		
		RiskDAO riskDAO = new RiskDAOImpl();
		riskDAO.delete(id);
		
		response.sendRedirect(request.getContextPath() + "/risk");
	}
}
