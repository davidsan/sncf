package fr.upmc.dar.sncf.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.upmc.dar.sncf.repository.PersonCustomRepository;

public class PersonRepositoryImpl implements PersonCustomRepository {

	@PersistenceContext
	private EntityManager em;

}
