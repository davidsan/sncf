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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.service.PersonService;

@RestController
@RequestMapping("api/users")
public class PersonController {
	private static final Logger logger = LoggerFactory
			.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	/**
	 * Return a user by his id
	 * 
	 * @param id
	 * @return Return a user by his id
	 */
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Person> getUserById(@PathVariable("id") Long id) {
		logger.info("Show user with id {}", id);
		Person person = personService.get(id);
		if (person == null) {
			return new ResponseEntity<Person>(person, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	/**
	 * Return all users
	 * 
	 * @return Return all users
	 */
	@RequestMapping(value = "/lookup", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Person>> getAll() {
		logger.info("List all users");
		return new ResponseEntity<List<Person>>(personService.getAll(), HttpStatus.OK);
	}

	/**
	 * Find user by his username
	 * 
	 * @param username
	 * @return Return user by username
	 */
	@RequestMapping(value = "/find/{username}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Person> getUserByName(
			@PathVariable("username") String username) {
		logger.info("Find by username {}", username);
		Person person = personService.findByUsername(username);
		if (person == null) {
			return new ResponseEntity<Person>(person, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(person, HttpStatus.CREATED);
	}
}
