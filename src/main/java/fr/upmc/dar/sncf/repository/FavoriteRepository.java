package fr.upmc.dar.sncf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import fr.upmc.dar.sncf.domain.Favorite;
import fr.upmc.dar.sncf.domain.Person;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>,
		FavoriteCustomRepository {

	@Query("SELECT f FROM Favorite f WHERE f.person = ? AND f.resourceType = ?")
	List<Favorite> getFavoriteByPerson(Person p, Long resourceType);

	@Query("SELECT f FROM Favorite f WHERE f.resourceId = ? AND f.resourceType = ?")
	List<Favorite> getFavorite(String resourceId, Long resourceType);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("DELETE FROM Favorite f WHERE f.person = ? AND f.resourceId = ? AND f.resourceType = ?")
	void deleteFavorite(Person person, String resourceId, Long resourceType);
}
