package fr.upmc.dar.sncf.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.upmc.dar.sncf.domain.Message;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.service.MessageService;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;

@RestController
@RequestMapping("api/message")
public class MessageController {

	private static final Logger logger = LoggerFactory
			.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private MessageService messageService;

	/**
	 * Get a message from his id
	 * 
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "/{messageId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Message> getMessage(
			@PathVariable("messageId") Long messageId) {
		logger.info("Show message with id {}", messageId);
		Message message = messageService.getMessage(messageId);
		if (message == null) {
			return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	/**
	 * Create a message
	 * 
	 * @param username
	 * @param token
	 * @param content
	 * @param resourceId
	 * @param resourceType
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Message> createMessage(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "resourceId", required = true) String resourceId,
			@RequestParam(value = "resourceType", required = true) Long resourceType) {
		Session session = sessionService.findByToken(token);
		if (session == null) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		if (!session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		// fetch Person
		Person person = personService.findByUsername(username);
		if (person == null) {
			return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Message message = messageService.createMessage(person, content,
				resourceId, resourceType);
		return new ResponseEntity<Message>(message, HttpStatus.CREATED);
	}

	/**
	 * Create a message for a train
	 * 
	 * @param username
	 * @param token
	 * @param content
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/train/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Message> createMessageTrain(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "resourceId", required = true) String resourceId) {

		logger.info("Create a new message for the train with id {}", resourceId);
		Session session = sessionService.findByToken(token);
		if (session == null) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		if (!session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		// fetch Person
		Person person = personService.findByUsername(username);
		if (person == null) {
			return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Message message = messageService.createMessageTrain(person, content,
				resourceId);
		return new ResponseEntity<Message>(message, HttpStatus.CREATED);
	}

	/**
	 * Create a message for a station
	 * 
	 * @param username
	 * @param token
	 * @param content
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/gare/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Message> createMessageGare(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "resourceId", required = true) String resourceId) {
		logger.info("Create a new message for the station with id {}",
				resourceId);
		Session session = sessionService.findByToken(token);
		if (session == null) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		if (!session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return new ResponseEntity<Message>(HttpStatus.FORBIDDEN);
		}
		// fetch Person
		Person person = personService.findByUsername(username);
		if (person == null) {
			return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Message message = messageService.createMessageGare(person, content,
				resourceId);
		return new ResponseEntity<Message>(message, HttpStatus.CREATED);
	}

	/**
	 * Return messages for a train
	 * 
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/train/{resourceId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Message>> getMessageTrain(
			@PathVariable("resourceId") String resourceId) {
		logger.info("List messages for the train with id {}", resourceId);
		List<Message> messages = messageService.getMessageTrain(resourceId);
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
	}

	/**
	 * Return messages for a station
	 * 
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/gare/{resourceId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Message>> getMessageStation(
			@PathVariable("resourceId") String resourceId) {
		logger.info("List messages for the station with id {}", resourceId);
		List<Message> messages = messageService.getMessageGare(resourceId);
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
	}

	/**
	 * Return messages posted by an user
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Message>> getMessageByPerson(
			@RequestParam(value = "username", required = true) String username) {
		logger.info("List messages for the user {}", username);
		Person p = personService.findByUsername(username);
		List<Message> messages = messageService.getMessageByPerson(p);
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
	}
}
