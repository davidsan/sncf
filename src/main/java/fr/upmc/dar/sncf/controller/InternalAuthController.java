package fr.upmc.dar.sncf.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.upmc.dar.sncf.Application;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.security.CookieManager;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;
import fr.upmc.dar.sncf.service.UserValidatorService;

@Controller
public class InternalAuthController {

	private static final Logger logger = LoggerFactory
			.getLogger(Application.class);

	private final String REDIRECT = "redirect:";
	private final String REDIRECT_SIGNUP = REDIRECT + "/signup";
	private final String REDIRECT_SIGNIN = REDIRECT + "/signin";

	@Autowired
	private PersonService personService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserValidatorService userValidatorService;

	/**
	 * Sign up user method
	 * 
	 * @param username
	 * @param password
	 * @param mail
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return to signin page
	 * @throws IOException
	 */
	@RequestMapping(value = "auth/signup", method = RequestMethod.POST)
	public String signup(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "mail", required = true) String mail,
			Model model, HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes) throws IOException {
		logger.info("{0} try to signup", username);
		if (!EmailValidator.getInstance().isValid(mail)) {
			redirectAttributes.addFlashAttribute("error",
					"L'adresse électronique soumise est invalide");
			return REDIRECT_SIGNUP;
		}
		if (!userValidatorService.validateUsername(username)) {
			redirectAttributes
					.addFlashAttribute(
							"error",
							"Le nom d'utilisateur ne doit contenir que des caractères alphanumériques et sa longueur doit être comprise entre 2 et 16 caractères");
			return REDIRECT_SIGNUP;
		}

		if (!userValidatorService.validatePassword(password)) {
			redirectAttributes
					.addFlashAttribute("error",
							"La longueur du mot de passe doit être comprise entre 6 et 32 caractères");
			return REDIRECT_SIGNUP;
		}

		Person user = new Person(username, password, mail);
		try {
			user = personService.save(user);
		} catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("error",
					"Nom d'utilisateur déjà utilisé");
			return REDIRECT_SIGNUP;
		}

		if (user == null) {
			redirectAttributes.addFlashAttribute("error", "Erreur interne");
			return REDIRECT_SIGNUP;
		}

		redirectAttributes.addFlashAttribute("info",
				"Vous pouvez maintenant vous connecter");
		return REDIRECT_SIGNIN;
	}

	/**
	 * Sign in user method
	 * 
	 * @param username
	 * @param password
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return go to landing page
	 * @throws IOException
	 */
	@RequestMapping(value = "auth/signin", method = RequestMethod.POST)
	public String signin(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			Model model, HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes) throws IOException {

		logger.info("{0} try to signin", username);

		// validate credentials
		if (!personService.validateCredentials(username, password)) {
			redirectAttributes.addFlashAttribute("error",
					"Nom d'utilisateur ou mot de passe incorrect");
			return REDIRECT_SIGNIN;
		}

		// create session
		Session session = sessionService.create(username,
				request.getRemoteAddr());

		if (session == null) {
			redirectAttributes.addFlashAttribute("error", "Erreur interne");
			return REDIRECT_SIGNIN;
		}

		response.addCookie(CookieManager.createCookieToken(session));
		response.addCookie(CookieManager.createCookieName(session));
		response.sendRedirect("/");
		return null;
	}

	/**
	 * Sign out user method
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return to landing page
	 * @throws IOException
	 */
	@RequestMapping(value = "signout", method = RequestMethod.GET)
	public String signout(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Cookie cookieToken = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_TOKEN, request);
		Cookie cookieName = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_USERNAME, request);
		logger.info("{0} try to signout", cookieName);
		// session removal
		if (cookieToken != null && cookieName != null) {
			String token = cookieToken.getValue();
			String username = cookieName.getValue();
			Session session = sessionService.findByToken(token);
			if (session != null
					&& session.getPerson().getUsername().equals(username)) {
				sessionService.delete(session);
			}
		}

		// cookies removal
		response.addCookie(CookieManager.createCookieName(null));
		response.addCookie(CookieManager.createCookieToken(null));
		response.sendRedirect("/");
		return null;
	}

	@ExceptionHandler(Exception.class)
	public String handleIOException(Exception ex) {
		return "error";
	}
}