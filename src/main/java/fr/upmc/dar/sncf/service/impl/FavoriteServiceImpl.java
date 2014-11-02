package fr.upmc.dar.sncf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.upmc.dar.sncf.domain.Favorite;
import fr.upmc.dar.sncf.domain.Message;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.repository.FavoriteRepository;
import fr.upmc.dar.sncf.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteRepository repository;

	@Transactional
	@Override
	public Favorite createFavoriteTrain(Person p, String resourceId,
			String resourceName) {
		Favorite favorite = new Favorite(p, resourceId,
				Message.RESOURCE_ID_TRAIN, resourceName);
		return repository.save(favorite);
	}

	@Transactional
	@Override
	public Favorite createFavoriteGare(Person p, String resourceId,
			String resourceName) {
		Favorite favorite = new Favorite(p, resourceId,
				Message.RESOURCE_ID_GARE, resourceName);
		return repository.save(favorite);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Favorite> getFavoriteTrainByPerson(Person p) {
		return repository.getFavoriteByPerson(p, Message.RESOURCE_ID_TRAIN);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Favorite> getFavoriteGareByPerson(Person p) {
		return repository.getFavoriteByPerson(p, Message.RESOURCE_ID_GARE);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Favorite> getFavoriteTrain(String resourceId) {
		return repository.getFavorite(resourceId, Message.RESOURCE_ID_TRAIN);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Favorite> getFavoriteGare(String resourceId) {
		return repository.getFavorite(resourceId, Message.RESOURCE_ID_GARE);
	}

	@Override
	public void deleteFavoriteTrain(Person p, String resourceId) {
		repository.deleteFavorite(p, resourceId, Message.RESOURCE_ID_TRAIN);
	}

	@Override
	public void deleteFavoriteGare(Person p, String resourceId) {
		repository.deleteFavorite(p, resourceId, Message.RESOURCE_ID_GARE);
	}
}
