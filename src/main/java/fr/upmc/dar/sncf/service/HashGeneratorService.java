package fr.upmc.dar.sncf.service;

import fr.upmc.dar.sncf.domain.HashedPasswordTuple;

public interface HashGeneratorService {

	HashedPasswordTuple generateHash(String password);

	String getSaltedHash(String password, int salt);
}
