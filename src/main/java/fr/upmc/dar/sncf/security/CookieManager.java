package fr.upmc.dar.sncf.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import fr.upmc.dar.sncf.domain.Session;

public class CookieManager {
	public static final int COOKIE_EXPIRY = 60 * 60;
	public static final String COOKIE_PATH = "/";
	public static final String COOKIE_SNCF_TOKEN = "token";
	public static final String COOKIE_SNCF_USERNAME = "username";

	public static Cookie createCookie(String key, String value, int age) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(age);
		return cookie;
	}

	public static Cookie createCookieToken(Session session) {
		return (session == null) ? createCookie(COOKIE_SNCF_TOKEN, null, 0)
				: createCookie(COOKIE_SNCF_TOKEN, session.getToken(),
						COOKIE_EXPIRY);
	}

	public static Cookie createCookieName(Session session) {
		return (session == null || session.getPerson() == null) ? createCookie(
				COOKIE_SNCF_USERNAME, null, 0) : createCookie(
				COOKIE_SNCF_USERNAME, session.getPerson().getUsername(),
				COOKIE_EXPIRY);
	}

	public static Cookie retrieveCookie(String name, HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public static String retrieveValue(HttpServletRequest request, String name) {
		Cookie cookie = retrieveCookie(name, request);
		if (cookie == null) {
			return null;
		}
		return cookie.getValue();
	}

}
