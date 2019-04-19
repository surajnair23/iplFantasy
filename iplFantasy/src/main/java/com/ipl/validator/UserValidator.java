package com.ipl.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ipl.dao.*;
import com.ipl.exception.*;
import com.ipl.pojo.*;

public class UserValidator implements Validator{
	
	public UserValidator() {}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		UserDao ud = new UserDao();
		User u = (User) target;
		String username = u.getUsername();
		Boolean ifExists = false;
		try {
			if(username != null)
			{
				if(!username.isEmpty())
					ifExists = ud.authenticate(username);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(errors.hasErrors()) {
			return;
		}
		
		if(ifExists) {
			errors.rejectValue("username", "empty.username", "Username is already taken");
		}
		
		if(!String.valueOf(u.getPhone()).matches("^\\d{10}$")) {
			errors.rejectValue("phone", "empty.phone", "Phone should contain atleast 10 digits");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fName", "empty.fName", "First Name is Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lName", "empty.lName", "Last Name is Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "empty.phone", "Phone number is Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty.email", "Email is Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password", "Password is Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username","empty.username", "Username is required");
	}

}
