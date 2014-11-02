package fr.upmc.dar.sncf.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;
import fr.upmc.dar.sncf.service.UserValidatorService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthController.class);

	@Autowired
	private PersonService personService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserValidatorService userValidatorService;

	/**
	 * Signup REST
	 * 
	 * @param user
	 * @return Return the person entity if successful
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Person> signup(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "mail", required = true) String mail) {

		logger.info("{0} try to signup using the REST API", username);
		if (!EmailValidator.getInstance().isValid(mail)) {
			return new ResponseEntity<Person>(HttpStatus.NOT_ACCEPTABLE);
		}
		if (!userValidatorService.validateUsername(username)) {
			return new ResponseEntity<Person>(HttpStatus.NOT_ACCEPTABLE);
		}
		if (!userValidatorService.validatePassword(password)) {
			return new ResponseEntity<Person>(HttpStatus.NOT_ACCEPTABLE);
		}
		Person user = new Person(username, password, mail);
		user = personService.save(user);

		if (user == null) {
			return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Person>(user, HttpStatus.CREATED);
	}

	/**
	 * Signin REST
	 * 
	 * @return Return the session entity if successful
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Session> signin(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletRequest request) {
		logger.info("{0} try to signin using the REST API", username);

		// validate credentials
		if (!personService.validateCredentials(username, password)) {
			return new ResponseEntity<Session>(HttpStatus.FORBIDDEN);
		}

		// create session
		Session session = sessionService.create(username,
				request.getRemoteAddr());

		if (session == null) {
			return new ResponseEntity<Session>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Session>(session, HttpStatus.OK);
	}

	/**
	 * Signout REST
	 * 
	 * @return Return the session entity if successful
	 */
	@RequestMapping(value = "/signout", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> signout(
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "username", required = true) String username) {
		logger.info("{0} try to signout using the REST API", username);
		// retrieve session
		Session session = sessionService.findByToken(token);

		if (session == null
				|| !session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		sessionService.delete(session);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
