package fr.upmc.dar.sncf.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.upmc.dar.sncf.domain.Favorite;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.external.api.Raildar;
import fr.upmc.dar.sncf.security.CookieManager;
import fr.upmc.dar.sncf.service.FavoriteService;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;

@Controller
public class InternalFavoriteController {

	private final String REDIRECT = "redirect:";
	private final String GARE = "/gare/";
	private final String TRAIN = "/train/";

	@Autowired
	private PersonService personService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private FavoriteService favoriteService;

	/**
	 * Create a new favorite train
	 * 
	 * @param resourceId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return to the origin page
	 */
	@RequestMapping(value = "favorite/train/create/{resourceId}", method = RequestMethod.POST)
	public String createFavoriteTrain(
			@PathVariable("resourceId") String resourceId,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) {
		String origin = REDIRECT + TRAIN + resourceId;

		Cookie cookieToken = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_TOKEN, request);
		Cookie cookieName = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_USERNAME, request);

		if (preTest(cookieToken, cookieName, redirectAttributes)) {
			return origin;
		}
		// fetch Person
		Person person = personService.findByUsername(cookieName.getValue());
		if (person == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}

		String resourceName;
		try {
			resourceName = Raildar.fetchTrainName(resourceId);
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error",
					"Echec de l'enregistrement du favoris");
			return origin;
		}

		Favorite favorite = favoriteService.createFavoriteTrain(person,
				resourceId, resourceName);
		if (favorite == null) {
			redirectAttributes.addFlashAttribute("error",
					"Echec de l'enregistrement du favoris");
			return origin;
		}

		redirectAttributes.addFlashAttribute("info", "Favoris enregistré");
		return origin;
	}

	/**
	 * Create a new favorite station
	 * 
	 * @param resourceId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return to the origin page
	 */
	@RequestMapping(value = "favorite/gare/create/{resourceId}", method = RequestMethod.POST)
	public String createFavoriteGare(
			@PathVariable("resourceId") String resourceId,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) {
		String origin = REDIRECT + GARE + resourceId;

		Cookie cookieToken = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_TOKEN, request);
		Cookie cookieName = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_USERNAME, request);

		if (preTest(cookieToken, cookieName, redirectAttributes)) {
			return origin;
		}

		// fetch Person
		Person person = personService.findByUsername(cookieName.getValue());
		if (person == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}

		String resourceName;
		try {
			resourceName = Raildar.fetchGareName(resourceId);
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error",
					"Echec de l'enregistrement du favoris");
			return origin;
		}

		Favorite favorite = favoriteService.createFavoriteGare(person,
				resourceId, resourceName);
		if (favorite == null) {
			redirectAttributes.addFlashAttribute("error",
					"Echec de l'enregistrement du favoris");
			return origin;
		}

		redirectAttributes.addFlashAttribute("info", "Favoris enregistré");
		return origin;
	}

	/**
	 * Delete a favorite station
	 * 
	 * @param resourceId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return to the origin page
	 */
	@RequestMapping(value = "favorite/gare/delete/{resourceId}", method = RequestMethod.GET)
	public String deleteFavoriteGare(
			@PathVariable("resourceId") String resourceId,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) {
		String origin = REDIRECT + GARE + resourceId;

		Cookie cookieToken = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_TOKEN, request);
		Cookie cookieName = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_USERNAME, request);

		if (preTest(cookieToken, cookieName, redirectAttributes)) {
			return origin;
		}

		// fetch Person
		Person person = personService.findByUsername(cookieName.getValue());
		if (person == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		// delete the favorite
		favoriteService.deleteFavoriteGare(person, resourceId);

		redirectAttributes.addFlashAttribute("info", "Favoris retiré");
		return origin;
	}

	/**
	 * Delete a favorite train
	 * 
	 * @param resourceId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return to the origin page
	 */
	@RequestMapping(value = "favorite/train/delete/{resourceId}", method = RequestMethod.GET)
	public String deleteFavoriteTrain(
			@PathVariable("resourceId") String resourceId,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) {
		String origin = REDIRECT + TRAIN + resourceId;
		Cookie cookieToken = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_TOKEN, request);
		Cookie cookieName = CookieManager.retrieveCookie(
				CookieManager.COOKIE_SNCF_USERNAME, request);

		if (preTest(cookieToken, cookieName, redirectAttributes)) {
			return origin;
		}

		// fetch Person
		Person person = personService.findByUsername(cookieName.getValue());
		if (person == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return origin;
		}
		favoriteService.deleteFavoriteTrain(person, resourceId);

		redirectAttributes.addFlashAttribute("info", "Favoris retiré");
		return origin;
	}

	private boolean preTest(Cookie cookieToken, Cookie cookieName,
			final RedirectAttributes redirectAttributes) {

		if (cookieToken == null || cookieName == null) {
			redirectAttributes.addFlashAttribute("error", "Non connecté");
			return true;
		}

		Session session = sessionService.findByToken(cookieToken.getValue());
		if (session == null) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return true;
		}
		if (!session.getPerson().getUsername().equals(cookieName.getValue())) {
			redirectAttributes.addFlashAttribute("error", "Session invalide");
			return true;
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(Exception.class)
	public String handleIOException(Exception ex) {
		return "error";
	}
}
