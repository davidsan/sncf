package fr.upmc.dar.sncf.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.upmc.dar.sncf.repository.FavoriteCustomRepository;

public class FavoriteRepositoryImpl implements FavoriteCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;
}
