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

import dao.ImpactLevelDAO;
import dao.LikelihoodLevelDAO;
import dao.RiskDAO;
import dao.RiskLevelDAO;
import dao.SystemDAO;
import dao.impl.ImpactLevelDAOImpl;
import dao.impl.LikelihoodLevelDAOImpl;
import dao.impl.RiskDAOImpl;
import dao.impl.RiskLevelDAOImpl;
import dao.impl.SystemDAOImpl;
import model.ITSystem;
import model.ImpactLevel;
import model.LikelihoodLevel;
import model.Risk;
import model.RiskAssessment;
import model.RiskLevel;
import model.User;
import utils.MyUtils;

/**
 * Servlet implementation class RiskController
 */
@WebServlet("/risk")
public class RiskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskController() {
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
			
			LikelihoodLevelDAO likelihoodLevelDAO = new LikelihoodLevelDAOImpl();
			Map<Integer, LikelihoodLevel> likelihoods = likelihoodLevelDAO.getAllInSystem(system.getId());
			
			ImpactLevelDAO impactLevelDAO = new ImpactLevelDAOImpl();
			Map<Integer, ImpactLevel> impacts = impactLevelDAO.getAllInSystem(system.getId());
			
			RiskLevelDAO riskLevelDAO = new RiskLevelDAOImpl();
			Map<Integer, RiskLevel> risk_levels = riskLevelDAO.getAllInSystem(system.getId());
			
			RiskDAO riskDAO = new RiskDAOImpl();
			List<Risk> risks = riskDAO.getAllRisksInSystem(system.getId());
			
			List<RiskAssessment> list = processRiskScore(risks, likelihoods, impacts, risk_levels);
			
			request.setAttribute("list", list);
			request.setAttribute("risk_levels", MyUtils.mapToList(risk_levels));
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/riskView.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private List<RiskAssessment> processRiskScore(List<Risk> risks, Map<Integer, LikelihoodLevel> likelihoods, Map<Integer, ImpactLevel> impacts, Map<Integer, RiskLevel> risk_levels) {
		List<RiskAssessment> list = new ArrayList<RiskAssessment>();
		
		for(Risk risk: risks) {
			LikelihoodLevel likelihood = likelihoods.get(risk.getLikelihood_level_id());
			ImpactLevel impact = impacts.get(risk.getImpact_level_id());
			
			double risk_score = -1;
			if(likelihood != null && impact != null) {
				risk_score = (double)likelihood.getScore()*impact.getScore()/100;
			}
			
			if(risk_score == -1) {
				RiskLevel risk_level = null;
				RiskAssessment tmp = new RiskAssessment(risk, likelihood, impact, risk_level);
				list.add(tmp);
				continue;
			}
			
			for(int key: risk_levels.keySet()) {
				RiskLevel risk_level = risk_levels.get(key);
				if(risk_score >= risk_level.getRange_min() && risk_score <= risk_level.getRange_max()) {
					RiskAssessment tmp = new RiskAssessment(risk, likelihood, impact, risk_level);
					list.add(tmp);
					break;
				}
			}
			
		}
		
		return list;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
