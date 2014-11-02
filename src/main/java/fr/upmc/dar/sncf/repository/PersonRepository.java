package fr.upmc.dar.sncf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.upmc.dar.sncf.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long>,
		PersonCustomRepository {

	@Query("select p from Person p where p.username = ?")
	Person findByUsername(String username);

}
