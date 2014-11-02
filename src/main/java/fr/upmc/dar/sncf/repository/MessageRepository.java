package fr.upmc.dar.sncf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.upmc.dar.sncf.domain.Message;
import fr.upmc.dar.sncf.domain.Person;

public interface MessageRepository extends JpaRepository<Message, Long>,
		MessageCustomRepository {

	@Query("select m from Message m where m.resourceId = ? and m.resourceType = ?")
	List<Message> getMessages(String resourceId, Long resourceType);

	@Query("select m from Message m where m.person = ?")
	List<Message> getMessageByPerson(Person person);

}
