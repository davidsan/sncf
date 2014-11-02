package fr.upmc.dar.sncf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.upmc.dar.sncf.Application;
import fr.upmc.dar.sncf.domain.Favorite;
import fr.upmc.dar.sncf.domain.Message;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.security.CookieManager;
import fr.upmc.dar.sncf.service.FavoriteService;
import fr.upmc.dar.sncf.service.MessageService;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;

@Controller
public class ApplicationController {

	private static final Logger logger = LoggerFactory
			.getLogger(Application.class);

	@Autowired
	private PersonService personService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private FavoriteService favoriteService;

	/**
	 * Home
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		logger.info("Access to home page");
		injectUser(model, request);
		return "landing";
	}

	/**
	 * Signup
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "signup" }, method = RequestMethod.GET)
	public String signup(Model model) {
		logger.info("Access to signup page");
		return "signup";
	}

	/**
	 * Signin
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "signin" }, method = RequestMethod.GET)
	public String signin(Model model) {
		logger.info("Access to signin page");
		return "signin";
	}

	/**
	 * Gare (one)
	 * 
	 * @param gareId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/gare/{gareId}", method = RequestMethod.GET)
	public String gare(@PathVariable("gareId") String gareId, Model model,
			HttpServletRequest request) {
		logger.info("Access to station #{0} page", gareId);
		Person person = injectUser(model, request);
		if (person != null) {
			List<Favorite> favgares = favoriteService.getFavoriteGare(gareId);
			model.addAttribute("favgares", favgares);
			for (Favorite favorite : favgares) {
				if (favorite.getPerson().getId() == person.getId()) {
					model.addAttribute("favorited", true);
				}
			}
		}
		model.addAttribute("gareId", gareId);
		List<Message> messages = messageService.getMessageGare(gareId);
		model.addAttribute("messages", messages);
		return "station";
	}

	/**
	 * Train (one)
	 * 
	 * @param trainId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/train/{trainId}", method = RequestMethod.GET)
	public String train(@PathVariable("trainId") String trainId, Model model,
			HttpServletRequest request) {
		logger.info("Access to train #{0} page", trainId);
		Person person = injectUser(model, request);
		if (person != null) {
			List<Favorite> favtrains = favoriteService
					.getFavoriteTrain(trainId);
			model.addAttribute("favtrains", favtrains);
			for (Favorite favorite : favtrains) {
				if (favorite.getPerson().getId() == person.getId()) {
					model.addAttribute("favorited", true);
				}
			}
		}
		model.addAttribute("trainId", trainId);
		List<Message> messages = messageService.getMessageTrain(trainId);
		model.addAttribute("messages", messages);
		return "train";
	}

	/**
	 * Search train page
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/train" }, method = RequestMethod.GET)
	public String train(Model model, HttpServletRequest request) {
		logger.info("Access to main train page");
		injectUser(model, request);
		return "itineraire";
	}

	/**
	 * Search station page
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/gare" }, method = RequestMethod.GET)
	public String gare(Model model, HttpServletRequest request) {
		logger.info("Access to main station page");
		injectUser(model, request);
		return "stations";
	}

	/**
	 * About us page
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/apropos" }, method = RequestMethod.GET)
	public String apropos(Model model, HttpServletRequest request) {
		logger.info("Access to about us page");
		injectUser(model, request);
		return "about";
	}

	/**
	 * User page
	 * 
	 * @param username
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/voyageur/{username}", method = RequestMethod.GET)
	public String voyageur(@PathVariable("username") String username,
			Model model, HttpServletRequest request) {
		logger.info("Access to user {0} page", username);
		Person person = personService.findByUsername(username);
		if (person == null) {
			return "landing";
		}
		model.addAttribute("user", person);
		List<Message> commentaires = messageService.getMessageByPerson(person);
		model.addAttribute("commentaires", commentaires);
		List<Favorite> favgares = favoriteService
				.getFavoriteGareByPerson(person);
		model.addAttribute("favgares", favgares);
		List<Favorite> favtrains = favoriteService
				.getFavoriteTrainByPerson(person);
		model.addAttribute("favtrains", favtrains);

		return "user";
	}

	/**
	 * Error page
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String error(Model model, HttpServletRequest request) {
		logger.info("Access to error page");
		injectUser(model, request);
		return "error";
	}

	/**
	 * Inject the user (Person class) retrieved in the cookie into the model
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	private Person injectUser(Model model, HttpServletRequest request) {
		String cookieUsername = CookieManager.retrieveValue(request,
				CookieManager.COOKIE_SNCF_USERNAME);
		String cookieToken = CookieManager.retrieveValue(request,
				CookieManager.COOKIE_SNCF_TOKEN);
		if (cookieUsername == null || cookieToken == null) {
			return null;
		}

		try {
			Session session = sessionService.findByToken(cookieToken);
			if (session == null
					|| !session.getPerson().getUsername()
							.equals(cookieUsername)) {
				return null;
			}

			Person person = personService.findByUsername(cookieUsername);
			model.addAttribute("user", person);
			return person;
		} catch (Exception e) {
			logger.debug("findByToken nested exception");
			return null;
		}
	}

	@ExceptionHandler(Exception.class)
	public String handleIOException(Exception ex) {
		return "error";
	}

}