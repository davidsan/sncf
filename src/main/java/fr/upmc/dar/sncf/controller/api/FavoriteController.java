package fr.upmc.dar.sncf.controller.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.upmc.dar.sncf.domain.Favorite;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.external.api.Raildar;
import fr.upmc.dar.sncf.service.FavoriteService;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;

@RestController
@RequestMapping("api/favorite")
public class FavoriteController {

	@Autowired
	private PersonService personService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private FavoriteService favoriteService;

	/**
	 * Add a train in favorite
	 * 
	 * @param username
	 * @param token
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/train/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Favorite> createFavoriteTrain(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "resourceId", required = true) String resourceId) {
		Session session = sessionService.findByToken(token);
		if (session == null) {
			return new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN);
		}
		if (!session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN);
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN);
		}
		// fetch Person
		Person person = personService.findByUsername(username);
		if (person == null) {
			return new ResponseEntity<Favorite>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String resourceName;
		try {
			resourceName = Raildar.fetchTrainName(resourceId);
		} catch (IOException e) {
			return new ResponseEntity<Favorite>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Favorite favorite = favoriteService.createFavoriteTrain(person,
				resourceId, resourceName);
		return new ResponseEntity<Favorite>(favorite, HttpStatus.CREATED);
	}

	/**
	 * Add a station in favorite
	 * 
	 * @param username
	 * @param token
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/gare/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Favorite> createFavoriteGare(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "resourceId", required = true) String resourceId) {
		Session session = sessionService.findByToken(token);
		if (session == null) {
			return new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN);
		}
		if (!session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN);
		}
		// check for expired session
		if (!sessionService.isValidSession(session)) {
			return new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN);
		}
		// fetch Person
		Person person = personService.findByUsername(username);
		if (person == null) {
			return new ResponseEntity<Favorite>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String resourceName;
		try {
			resourceName = Raildar.fetchGareName(resourceId);
		} catch (IOException e) {
			return new ResponseEntity<Favorite>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Favorite favorite = favoriteService.createFavoriteGare(person,
				resourceId, resourceName);
		return new ResponseEntity<Favorite>(favorite, HttpStatus.CREATED);
	}

	/**
	 * Get all favorites for a train
	 * 
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/train/{resourceId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Favorite>> getFavoriteTrain(
			@PathVariable("resourceId") String resourceId) {
		List<Favorite> favorite = favoriteService.getFavoriteTrain(resourceId);
		return new ResponseEntity<List<Favorite>>(favorite, HttpStatus.OK);
	}

	/**
	 * Get all favorites for a station
	 * 
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/gare/{resourceId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Favorite>> getFavoriteStation(
			@PathVariable("resourceId") String resourceId) {
		List<Favorite> favorite = favoriteService.getFavoriteGare(resourceId);
		return new ResponseEntity<List<Favorite>>(favorite, HttpStatus.OK);
	}

	/**
	 * Get all favorites train of an user
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/train/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Favorite>> getFavoriteTrainByPerson(

	@RequestParam(value = "username", required = true) String username) {
		Person p = personService.findByUsername(username);
		List<Favorite> favorites = favoriteService.getFavoriteTrainByPerson(p);
		return new ResponseEntity<List<Favorite>>(favorites, HttpStatus.OK);
	}

	/**
	 * Get all favorites station of an user
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/gare/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Favorite>> getFavoriteGareByPerson(
			@RequestParam(value = "username", required = true) String username) {
		Person p = personService.findByUsername(username);
		List<Favorite> favorites = favoriteService.getFavoriteGareByPerson(p);
		return new ResponseEntity<List<Favorite>>(favorites, HttpStatus.OK);
	}

	/**
	 * Remove a train from an user's favorites
	 * 
	 * @param resourceId
	 * @param username
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/delete/train", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> deleteFavoriteTrain(
			@RequestParam("resourceId") String resourceId,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "token", required = true) String token) {
		// retrieve session
		Session session = sessionService.findByToken(token);
		if (session == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		if (!session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}

		Person person = personService.findByUsername(username);

		favoriteService.deleteFavoriteTrain(person, resourceId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Remove a station from an user's favorites
	 * 
	 * @param resourceId
	 * @param username
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/delete/gare", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> deleteFavoriteGare(
			@RequestParam("resourceId") String resourceId,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "token", required = true) String token) {
		// retrieve session
		Session session = sessionService.findByToken(token);
		if (session == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		if (!session.getPerson().getUsername().equals(username)) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}

		Person person = personService.findByUsername(username);

		favoriteService.deleteFavoriteGare(person, resourceId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
