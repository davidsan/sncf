package fr.upmc.dar.sncf.service;

import java.util.List;

import fr.upmc.dar.sncf.domain.Favorite;
import fr.upmc.dar.sncf.domain.Person;

public interface FavoriteService {

	Favorite createFavoriteTrain(Person p, String resourceId,
			String resourceName);

	Favorite createFavoriteGare(Person p, String resourceId, String resourceName);

	List<Favorite> getFavoriteTrainByPerson(Person p);

	List<Favorite> getFavoriteGareByPerson(Person p);

	List<Favorite> getFavoriteTrain(String resourceId);

	List<Favorite> getFavoriteGare(String resourceId);

	void deleteFavoriteTrain(Person p, String resourceId);

	void deleteFavoriteGare(Person p, String resourceId);

}
