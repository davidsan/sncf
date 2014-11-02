package fr.upmc.dar.sncf.service.impl;

import org.apache.commons.validator.routines.RegexValidator;
import org.springframework.stereotype.Service;

import fr.upmc.dar.sncf.service.UserValidatorService;

@Service
public class UserValidatorServiceImpl implements UserValidatorService {

	private RegexValidator usernameValidator = new RegexValidator(
			"[a-z][0-9a-z]{1,15}", false);

	private RegexValidator passwordValidator = new RegexValidator(
			".{6,32}", false);
	
	@Override
	public boolean validateUsername(String username) {
return usernameValidator.isValid(username);
	}

	@Override
	public boolean validatePassword(String password) {
return passwordValidator.isValid(password);
	}

}
