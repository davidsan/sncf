package fr.upmc.dar.sncf.service;

import fr.upmc.dar.sncf.domain.Session;

public interface SessionService {

	Session get(Long id);

	Session save(Session session);

	Session findByToken(String token);

	void delete(Session session);

	boolean isValidSession(Session session);

	Session create(String username, String ip);
}
