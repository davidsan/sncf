package fr.upmc.dar.sncf.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.upmc.dar.sncf.repository.SessionCustomRepository;

public class SessionRepositoryImpl implements SessionCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

}
