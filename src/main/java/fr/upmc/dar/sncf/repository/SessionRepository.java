package fr.upmc.dar.sncf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.upmc.dar.sncf.domain.Session;

public interface SessionRepository extends JpaRepository<Session, Long>,
		SessionCustomRepository {

	@Query("select s from Session s where s.token = ?")
	Session findByToken(String token);

}
