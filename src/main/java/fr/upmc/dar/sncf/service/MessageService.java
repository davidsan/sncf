package fr.upmc.dar.sncf.service;

import java.util.List;

import fr.upmc.dar.sncf.domain.Message;
import fr.upmc.dar.sncf.domain.Person;

public interface MessageService {

	Message getMessage(Long messageId);

	@Deprecated
	Message createMessage(Person person, String content, String resourceId,
			Long resourceType);

	Message createMessageTrain(Person person, String content, String resourceId);

	Message createMessageGare(Person person, String content, String resourceId);

	List<Message> getMessageTrain(String resourceId);

	List<Message> getMessageGare(String resourceId);

	List<Message> getMessageByPerson(Person p);
}
