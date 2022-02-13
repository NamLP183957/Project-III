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
import dao.RiskLevelDAO;
import dao.SystemDAO;
import dao.impl.ImpactLevelDAOImpl;
import dao.impl.LikelihoodLevelDAOImpl;
import dao.impl.RiskLevelDAOImpl;
import dao.impl.SystemDAOImpl;
import model.ITSystem;
import model.ImpactLevel;
import model.LikelihoodLevel;
import model.RiskLevel;
import model.User;
import utils.MyUtils;

/**
 * Servlet implementation class RiskSettingController
 */
@WebServlet("/risk/setting")
public class RiskSettingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskSettingController() {
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
			List<Integer> l_used = likelihoodLevelDAO.usedInSystem(system.getId());
			
			ImpactLevelDAO impactLevelDAO = new ImpactLevelDAOImpl();
			Map<Integer, ImpactLevel> impacts = impactLevelDAO.getAllInSystem(system.getId());
			List<Integer> i_used = impactLevelDAO.usedInSystem(system.getId());
			
			RiskLevelDAO riskLevelDAO = new RiskLevelDAOImpl();
			Map<Integer, RiskLevel> risk_levels = riskLevelDAO.getAllInSystem(system.getId());
			
			request.setAttribute("system_id", system.getId());
			request.setAttribute("likelihoods", MyUtils.mapToList(likelihoods));
			request.setAttribute("impacts", MyUtils.mapToList(impacts));
			request.setAttribute("risk_levels", MyUtils.mapToList(risk_levels));
			request.setAttribute("l_used", l_used);
			request.setAttribute("i_used", i_used);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/riskSettingView.jsp");
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
		String setting = request.getParameter("setting");
		String successMessage = "";
		if(setting.equalsIgnoreCase("impact_level")) {
			setImpactLevel(request, response);
			successMessage = "Cập nhật thang đo Mức Độ Tác Động thành công!";
		} else if(setting.equalsIgnoreCase("likelihood_level")) {
			setLikelihoodLevel(request, response);
			successMessage = "Cập nhật thang đo Khả Năng Xảy Ra thành công!";
		} else if(setting.equalsIgnoreCase("risk_level")) {
			setRiskLevel(request, response);
			successMessage = "Cập nhật thang đo Mức Độ Rủi Ro thành công!";
		}
		
		request.getSession().setAttribute("successMessage", successMessage);
		String url = request.getHeader("referer");
		response.sendRedirect(url);
	}
	
	private void setImpactLevel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int system_id = Integer.parseInt(request.getParameter("system_id"));
		List<Integer> id_after = changeStringsToIntegers(request.getParameterValues("impact_id"));
		String label[] = request.getParameterValues("impact_label");
		List<Integer> score = changeStringsToIntegers(request.getParameterValues("impact_score"));
		String color[] = request.getParameterValues("impact_color");
		
		ImpactLevelDAO impactLevelDAO = new ImpactLevelDAOImpl();
		List<Integer> id_before = impactLevelDAO.getAllIdInSystem(system_id);
		List<Integer> id_delete = getListIdShouldDelete(id_before, id_after);
		
		impactLevelDAO.doUpdateAll(id_after, id_delete, label, score, color, system_id);
	}
	
	private void setLikelihoodLevel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int system_id = Integer.parseInt(request.getParameter("system_id"));
		List<Integer> id_after = changeStringsToIntegers(request.getParameterValues("likelihood_id"));
		String label[] = request.getParameterValues("likelihood_label");
		List<Integer> score = changeStringsToIntegers(request.getParameterValues("likelihood_score"));
		String color[] = request.getParameterValues("likelihood_color");
		
		LikelihoodLevelDAO likelihoodLevelDAO = new LikelihoodLevelDAOImpl();
		List<Integer> id_before = likelihoodLevelDAO.getAllIdInSystem(system_id);
		List<Integer> id_delete = getListIdShouldDelete(id_before, id_after);
		
		likelihoodLevelDAO.doUpdateAll(id_after, id_delete, label, score, color, system_id);
		
	}
	
	private void setRiskLevel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int system_id = Integer.parseInt(request.getParameter("system_id"));
		List<Integer> id_after = changeStringsToIntegers(request.getParameterValues("risk_id"));
		String label[] = request.getParameterValues("risk_label");
		List<Integer> min = changeStringsToIntegers(request.getParameterValues("risk_score_min"));
		List<Integer> max = changeStringsToIntegers(request.getParameterValues("risk_score_max"));
		String color[] = request.getParameterValues("risk_color");
		
		RiskLevelDAO riskLevelDAO = new RiskLevelDAOImpl();
		List<Integer> id_before = riskLevelDAO.getAllIdInSystem(system_id);
		List<Integer> id_delete = getListIdShouldDelete(id_before, id_after);
		
		riskLevelDAO.doUpdateAll(id_after, id_delete, label, min, max, color, system_id);

	}
	
	private List<Integer> changeStringsToIntegers(String arr[]) {
		List<Integer> list = new ArrayList<>();
		for(String s: arr) {
			list.add(Integer.parseInt(s));
		}
		
		return list;
	}
	
	private List<Integer> getListIdShouldDelete(List<Integer> before, List<Integer> after) {
		List<Integer> list = new ArrayList<>();
		for(int i: before) {
			if(after.contains(i) == false) {
				list.add(i);
			}
		}
		
		return list;
	}


}
