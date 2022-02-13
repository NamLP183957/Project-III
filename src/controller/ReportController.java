package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RiskDAO;
import dao.RiskLevelDAO;
import dao.SystemDAO;
import dao.TroubleDAO;
import dao.impl.RiskDAOImpl;
import dao.impl.RiskLevelDAOImpl;
import dao.impl.SystemDAOImpl;
import dao.impl.TroubleDAOImpl;
import model.ITSystem;
import model.RiskLevel;
import model.User;
import utils.MyUtils;

/**
 * Servlet implementation class ReportController
 */
@WebServlet("/report")
public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportController() {
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
		
//		Date date = new Date();
//		int year = date.getYear();
//		System.out.println(year); // tính từ 1900
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		TroubleDAO troubleDAO = new TroubleDAOImpl();
		List<Integer> troubleStatus = troubleDAO.countNumGroupbyStatus(system.getId(), year);
		
		List<List<Integer>> troubleMonth = new ArrayList<>();
		for(int status = 0; status <= 2; ++status ) {
			List<Integer> list = troubleDAO.countNumGroupbyMonth(system.getId(), year, status);
			troubleMonth.add(list);
		}
		
		RiskDAO riskDAO = new RiskDAOImpl();
		int num_risk_system = riskDAO.getNumRisksInSystem(system.getId());
		int num_risk_assessment = riskDAO.getNumRisksInAssessment(system.getId());
		
		RiskLevelDAO riskLevelDAO = new RiskLevelDAOImpl();
		List<RiskLevel> riskLevels = MyUtils.mapToList(riskLevelDAO.getAllInSystem(system.getId()));
		
		Map<Integer, Integer> map = riskDAO.countByRiskLevel(system.getId());
		
		
		
		request.setAttribute("year", year);
		request.setAttribute("troubleStatus", troubleStatus);
		request.setAttribute("troubleMonth", troubleMonth);
		
		request.setAttribute("num_risk_system", num_risk_system);
		request.setAttribute("num_risk_assessment", num_risk_assessment);
		
		request.setAttribute("riskLevels", riskLevels);
		request.setAttribute("map", map);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/reportView.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
