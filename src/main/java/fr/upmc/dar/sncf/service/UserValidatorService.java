package fr.upmc.dar.sncf.service;


public interface UserValidatorService {

	boolean validateUsername(String username);

	boolean validatePassword(String password);

}
