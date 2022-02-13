package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SystemDAO;
import dao.impl.SystemDAOImpl;
import model.ITSystem;
import model.User;
import utils.MyUtils;

/**
 * Servlet Filter implementation class SystemFilter
 */
public class SystemFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SystemFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		User user = MyUtils.getUserInSession(req);
		SystemDAO systemDAO = new SystemDAOImpl();
		ITSystem system = systemDAO.getByUserId(user.getId());
		if(system != null) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + "/system");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
