package com.ipl.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ipl.dao.UserDao;
import com.ipl.pojo.User;

public class LoginValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		UserDao ud = new UserDao();
		User user = (User) target;
		String username = "",pwd = "";
		String valid = null;
		if(errors.hasErrors()) {
			return;
		}
		if(user == null) {
			return;
		}else {
			//check if we recieved values for both feilds
			if(user.getUsername() != null && !user.getUsername().isEmpty()) {
				if(user.getPassword() != null && !user.getPassword().isEmpty()) {
					username = user.getUsername();
					pwd = user.getPassword();
					valid = ud.loginUser(username, pwd);
				}
				
				if(valid == null) {
					errors.rejectValue("password", "empty.password", "Invalid credentials");
				}else if(valid.equalsIgnoreCase("valid")) {
					errors.rejectValue("password", "empty.password", "User not yet authorized");
				}
			}else {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty.username", "Username is Required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password", "Password is Required");
			}
		}
	}

}
