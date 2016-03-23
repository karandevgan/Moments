package com.moments.service;

import java.util.ArrayList;
import java.util.List;

import com.moments.model.User;

public class ValidationService {

	public static List<String> validateRegisteredUser(Service service, User user) {
		List<String> errorsList = new ArrayList<String>();

		String userNamePattern = "^[a-z][a-z0-9]{5,15}$";
		if (!user.getUsername().matches(userNamePattern)) {
			String usernameErrorMsg = "Username must start with lowercase letter, can contain only lowercase characters and digits and should be between 6 to 15 characters.";
			errorsList.add(usernameErrorMsg);
		} else {
			if (service.isRegistered(user.getUsername())) {
				String usernameErrorMsg = "Username already registered.";
				errorsList.add(usernameErrorMsg);
			}
		}

		String firstNamePattern = "^[A-Z][a-zA-Z]+$";
		if (!user.getFirst_name().matches(firstNamePattern)) {
			String firstNameErrorMsg = "Must start with uppercase letter. Should contain only letters";
			errorsList.add(firstNameErrorMsg);
		}

		String lastNamePattern = "^[A-Z][a-zA-Z]+$";
		if (!user.getLast_name().matches(lastNamePattern)) {
			String lastNameErrorMsg = "Must start with uppercase letter. Should contain only letters";
			errorsList.add(lastNameErrorMsg);
		}

		String emailPattern = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+[^.]+$";
		if (!user.getEmail().matches(emailPattern)) {
			String emailErrorMsg = "Enter a valid email";
			errorsList.add(emailErrorMsg);
		} else {
			if(service.isEmailRegistered(user.getEmail())) {
				String emailErrorMsg = "Email already registered";
				errorsList.add(emailErrorMsg);
			}
		}

		String passwordPattern = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,18}";
		if (!user.getPassword().matches(passwordPattern)) {
			String passwordErrorMsg = "Password must contain at least one number and one uppercase and lowercase letter, and between 6 to 18 characters";
			errorsList.add(passwordErrorMsg);
		}

		return errorsList;
	}
	
	public static List<String> validateSigninUser(Service service,User user)
	{
		List<String> errorList = new ArrayList<String>();
		String username= user.getUsername().trim();
		if(username.equals(""))
		{
			String usernameErrorMsg="Username is required";
			errorList.add(usernameErrorMsg);
		}
		String password = user.getPassword().trim();
		if(password.equals(""))
		{
			String passwordErrorMsg = "Password is required";
			errorList.add(passwordErrorMsg);
		}
		return errorList;
	} 
 }
