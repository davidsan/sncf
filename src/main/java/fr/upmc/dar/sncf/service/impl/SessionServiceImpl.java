package fr.upmc.dar.sncf.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.domain.Session;
import fr.upmc.dar.sncf.repository.SessionRepository;
import fr.upmc.dar.sncf.service.PersonService;
import fr.upmc.dar.sncf.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

	private static final int SESSION_EXPIRY_HOUR = 1;

	@Autowired
	private SessionRepository repository;

	@Autowired
	private PersonService personService;

	@Transactional(readOnly = true)
	@Override
	public Session get(Long id) {
		return repository.findOne(id);
	}

	@Transactional
	@Override
	public Session save(Session session) {
		return repository.save(session);
	}

	@Transactional(readOnly = true)
	@Override
	public Session findByToken(String token) {
		return repository.findByToken(token);
	}

	@Override
	public void delete(Session session) {
		repository.delete(session);
	}

	@Override
	public boolean isValidSession(Session session) {
		Calendar calSession = Calendar.getInstance();
		Date dateNow = new Date();

		calSession.setTime(session.getDate());
		calSession.add(Calendar.HOUR, SESSION_EXPIRY_HOUR);

		if (calSession.getTime().before(dateNow)) {
			this.delete(session);
			return false;
		}

		return true;
	}

	@Override
	public Session create(String username, String ip) {
		Person person = personService.findByUsername(username);
		if (person == null) {
			return null;
		}
		return save(new Session(person, ip));
	}
}
