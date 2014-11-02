package fr.upmc.dar.sncf.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.upmc.dar.sncf.domain.Message;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.security.CookieManager;
import fr.upmc.dar.sncf.service.MessageService;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;

@Controller
public class InternalMessageController {

	private final String REDIRECT = "redirect:";
	private final String REDIRECT_GARE = REDIRECT + "/gare/";
	private final String REDIRECT_TRAIN = REDIRECT + "/train/";

	@Autowired
	private PersonService personService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private MessageService messageService;

	/**
	 * Create message for a train
	 * 
	 * @param content
	 * @param resourceId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return current page
	 */
	@RequestMapping(value = "message/train/create", method = RequestMethod.POST)
	public String createMessageTrain(
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "resourceId", required = true) String resourceId,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) {
		String origin = REDIRECT_TRAIN + resourceId;

		Cookie cookieToken = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_TOKEN, request);
		Cookie cookieName = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_USERNAME, request);

		if (cookieToken == null || cookieName == null) {
			redirectAttributes.addFlashAttribute("error", "Non connecté");
			return origin;
		}

		Session session = sessionService.findByToken(cookieToken.getValue());
		if (session == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		if (!session.getPerson().getUsername().equals(cookieName.getValue())) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return origin;
		}
		// fetch Person
		Person person = personService.findByUsername(cookieName.getValue());
		if (person == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		Message message = messageService.createMessageTrain(person, content,
				resourceId);
		if (message == null) {
			redirectAttributes.addFlashAttribute("error",
					"Echec de l'envoi du message");
			return origin;
		}

		redirectAttributes.addFlashAttribute("info", "Message envoyé");
		return origin;
	}

	/**
	 * Create message for a station
	 * 
	 * @param content
	 * @param resourceId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return current page
	 */
	@RequestMapping(value = "message/gare/create", method = RequestMethod.POST)
	public String createMessageGare(
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "resourceId", required = true) String resourceId,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) {
		String origin = REDIRECT_GARE + resourceId;

		Cookie cookieToken = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_TOKEN, request);
		Cookie cookieName = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_USERNAME, request);

		if (cookieToken == null || cookieName == null) {
			redirectAttributes.addFlashAttribute("error", "Non connecté");
			return origin;
		}

		Session session = sessionService.findByToken(cookieToken.getValue());
		if (session == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		if (!session.getPerson().getUsername().equals(cookieName.getValue())) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return origin;
		}
		// fetch Person
		Person person = personService.findByUsername(cookieName.getValue());
		if (person == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		Message message = messageService.createMessageGare(person, content,
				resourceId);
		if (message == null) {
			redirectAttributes.addFlashAttribute("error",
					"Echec de l'envoi du message");
			return origin;
		}

		redirectAttributes.addFlashAttribute("info", "Message envoyé");
		return origin;
	}


	@ExceptionHandler(Exception.class)
	public String handleIOException(Exception ex) {
		return "error";
	}
}