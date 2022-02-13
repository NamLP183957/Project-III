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
import dao.SystemDAO;
import dao.impl.AssetDAOImpl;
import dao.impl.ImpactLevelDAOImpl;
import dao.impl.LikelihoodLevelDAOImpl;
import dao.impl.RiskDAOImpl;
import dao.impl.RiskLevelDAOImpl;
import dao.impl.SystemDAOImpl;
import model.Asset;
import model.ITSystem;
import model.ImpactLevel;
import model.LikelihoodLevel;
import model.Risk;
import model.RiskAssessment;
import model.RiskLevel;
import model.User;
import utils.MyUtils;

/**
 * Servlet implementation class RiskAddController
 */
@WebServlet("/risk/add")
public class RiskAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskAddController() {
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
			
			LikelihoodLevelDAO likelihoodLevelDAO = new LikelihoodLevelDAOImpl();
			Map<Integer, LikelihoodLevel> likelihoods = likelihoodLevelDAO.getAllInSystem(system.getId());
			
			ImpactLevelDAO impactLevelDAO = new ImpactLevelDAOImpl();
			Map<Integer, ImpactLevel> impacts = impactLevelDAO.getAllInSystem(system.getId());
			
			RiskLevelDAO riskLevelDAO = new RiskLevelDAOImpl();
			Map<Integer, RiskLevel> risk_levels = riskLevelDAO.getAllInSystem(system.getId());
			
			request.setAttribute("system_id", system.getId());
			request.setAttribute("assets", assets);
			request.setAttribute("likelihoods", MyUtils.mapToList(likelihoods));
			request.setAttribute("impacts", MyUtils.mapToList(impacts));
			request.setAttribute("risk_levels", MyUtils.mapToList(risk_levels));
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/riskAddView.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int system_id = Integer.parseInt(request.getParameter("system_id"));
		String short_description = request.getParameter("short_description");
		String flaw = request.getParameter("flaw");
		String threat = request.getParameter("threat");
		int threat_type = Integer.parseInt(request.getParameter("threat-type"));
		String solution = request.getParameter("solution");
		int likelihood = -1;
		if(request.getParameter("likelihood") !=null) {
			likelihood = Integer.parseInt(request.getParameter("likelihood"));
		}
		int impact = -1;
		if(request.getParameter("impact") !=null) {
			impact = Integer.parseInt(request.getParameter("impact"));
		}
		
		String tmp[] = request.getParameterValues("tag_asset");
		List<Integer> assets = new ArrayList<>();
		if(tmp != null) {
			for(String s: tmp) {
				assets.add(Integer.parseInt(s));
			}
		}
		
		
		Risk risk = new Risk(short_description, flaw, threat, threat_type, solution, system_id, likelihood, impact);
		RiskDAO riskDAO = new RiskDAOImpl();
		riskDAO.insert(risk, assets);
		
		String successMessage = "Thêm mới thành công!";
//		request.setAttribute("successMessage", successMessage);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/riskAddView.jsp");
//		dispatcher.forward(request, response);
		request.getSession().setAttribute("successMessage", successMessage);
		String url = request.getHeader("referer");
		response.sendRedirect(url);
//		or
//		doGet(request, response);
//		or
//		response.sendRedirect(request.getContextPath() + "/risk/add");
	}

}
