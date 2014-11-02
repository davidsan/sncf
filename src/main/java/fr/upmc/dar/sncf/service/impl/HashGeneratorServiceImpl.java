package fr.upmc.dar.sncf.service.impl;

import org.springframework.stereotype.Service;

import fr.upmc.dar.sncf.domain.HashedPasswordTuple;
import fr.upmc.dar.sncf.security.SaltedHashGenerator;
import fr.upmc.dar.sncf.service.HashGeneratorService;

@Service
public class HashGeneratorServiceImpl implements HashGeneratorService {

	@Override
	public HashedPasswordTuple generateHash(String password) {
		return SaltedHashGenerator.hashPasswordUser(password);
	}

	@Override
	public String getSaltedHash(String password, int salt) {
		return SaltedHashGenerator.getSaltedHash(password, salt);
	}

}
