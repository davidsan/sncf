package fr.upmc.dar.sncf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import fr.upmc.dar.sncf.domain.HashedPasswordTuple;
import fr.upmc.dar.sncf.domain.Person;
import fr.upmc.dar.sncf.repository.PersonRepository;
import fr.upmc.dar.sncf.service.HashGeneratorService;
import fr.upmc.dar.sncf.service.PersonService;

@Validated
@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository repository;

	@Autowired
	private HashGeneratorService hashGeneratorService;

	@Transactional(readOnly = true)
	@Override
	public Person get(Long id) {
		Person person = repository.findOne(id);
		return person;
	}

	@Override
	public List<Person> getAll() {
		return repository.findAll();
	}

	@Transactional
	@Override
	public Person save(Person person) {
		HashedPasswordTuple hashedPasswordTuple = hashGeneratorService
				.generateHash(person.getPassword());
		person.setPassword(hashedPasswordTuple.getHashedPassword());
		// from now the password in the person entity is the salted & hashed
		// version
		person.setSalt(hashedPasswordTuple.getSalt());
		return repository.save(person);
	}

	@Override
	public Person findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public boolean validateCredentials(String username, String password) {
		Person match = repository.findByUsername(username);
		// no user with this username
		if (match == null) {
			return false;
		}

		// compute salted hash of password
		String hashedPassword = hashGeneratorService.getSaltedHash(password,
				match.getSalt());
		return match.getPassword().equals(hashedPassword);

	}

}
