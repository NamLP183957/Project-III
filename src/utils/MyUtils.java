package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

public class MyUtils {

    public static void storeUserInSession(HttpServletRequest request, User loginedUser) {

        request.getSession().setAttribute("user", loginedUser);
    }
    
    public static void deleteUserInSession(HttpServletRequest request) {
    	request.getSession().setAttribute("user", null);
    }
 
    public static User getUserInSession(HttpServletRequest request) {
        User loginedUser = (User) request.getSession().getAttribute("user");
        return loginedUser;
    }
 

    public static void storeUsernameInCookie(HttpServletResponse response, User user) {
        Cookie cookieUserName = new Cookie("ATT_NAME_USER_NAME", user.getUsername());
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }
 
    public static String getUsernameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ATT_NAME_USER_NAME".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
 
    public static void deleteUsernameInCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie("ATT_NAME_USER_NAME", null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
    
    public static <V> List<V> mapToList(Map<Integer, V> map) {
		List<V> list = new ArrayList<V>();
		for(int key: map.keySet()) {
			list.add(map.get(key));
		}
		
		return list;
	}
}
