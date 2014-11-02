package fr.upmc.dar.sncf.service;

import java.util.List;

import fr.upmc.dar.sncf.domain.Person;

public interface PersonService {

	Person get(Long id);

	List<Person> getAll();

	Person save(Person user);

	Person findByUsername(String username);

	boolean validateCredentials(String username, String password);
}
