package fr.upmc.dar.sncf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.upmc.dar.sncf.domain.Message;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.repository.MessageRepository;
import fr.upmc.dar.sncf.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository repository;

	@Transactional(readOnly = true)
	@Override
	public Message getMessage(Long messageId) {
		Message message = repository.findOne(messageId);
		return message;
	}

	@Transactional
	@Override
	public Message createMessageTrain(Person person, String content,
			String resourceId) {
		Message message = new Message(person, content, resourceId,
				Message.RESOURCE_ID_TRAIN);
		return repository.save(message);
	}

	@Transactional
	@Override
	public Message createMessageGare(Person person, String content,
			String resourceId) {
		Message m = new Message(person, content, resourceId,
				Message.RESOURCE_ID_GARE);
		return repository.save(m);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Message> getMessageTrain(String resourceId) {
		List<Message> messages = repository.getMessages(resourceId,
				Message.RESOURCE_ID_TRAIN);
		return messages;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Message> getMessageGare(String resourceId) {
		List<Message> messages = repository.getMessages(resourceId,
				Message.RESOURCE_ID_GARE);
		return messages;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Message> getMessageByPerson(Person p) {
		return repository.getMessageByPerson(p);
	}

	@Transactional
	@Override
	public Message createMessage(Person person, String content,
			String resourceId, Long resourceType) {
		Message m = new Message(person, content, resourceId, resourceType);
		return repository.save(m);
	}
}
