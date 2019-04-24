package com.ipl.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.ipl.exception.UserException;
import com.ipl.pojo.User;

@SuppressWarnings("deprecation")
public class UserDao extends Dao {

	public UserDao() {
	}

	public boolean register(User u) throws UserException {
		Boolean result = false;
		try {
			begin();
			getSession().save(u);
			commit();
			result = true;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}finally {
			close();
		}
		return result;
	}
	
	//function to check if username already exists
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean authenticate(String val) throws UserException{
		Boolean result = false;
		try {
			begin();
			Query q = getSession().createQuery("from User where username=:username");
			q.setString("username", val);
			List<String> list = q.list(); 
			//check if it contains
			if(list != null) {
				if(!list.isEmpty()) {
					commit();
					result = true; }
			}
		}catch(HibernateException e) {
			rollback();
			throw new UserException("Exception while validating"+e.getMessage());
		}finally {
			close();
		}
		return result;
	}
	
	//function to facilitate login
	public String loginUser(String username, String pwd) {
		String result = null;
		try {
			begin();
			Criteria c = getSession().createCriteria(User.class);
			c.add(Restrictions.eq("username", username));
			c.add(Restrictions.eq("password",pwd));
			c.setMaxResults(1);
			User userRes = (User) c.uniqueResult();
			//check if it contains
			if(userRes != null) {
				commit();
				result = "valid";
				if(userRes.getIsApproved())
					result = "authorized";
			}
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return result;
	}

	//function to checkforAdminLogin
	public boolean isAdmin(User user) {
		Boolean result = false;
		Criteria c = getSession().createCriteria(User.class);
		c.add(Restrictions.ilike("username", user.getUsername()));
		c.setMaxResults(1);
		User userRes = (User) c.uniqueResult();
		if(userRes.getIsAdmin()) {
			result = true;
		}
		return result;
	}
	
	//approve users into the system from admin
	public boolean approveUser(long userid) {
		boolean result = false;
		int update = 0;
		try {
			begin();
			@SuppressWarnings("rawtypes")
			Query q = getSession().createQuery("update User set isApproved=:val " + "where userId=:userId");
			q.setBoolean("val", true);
			q.setLong("userId", userid);
			update = q.executeUpdate();
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
		result = update > 0 ? true : false;
		return result;
	}
	
	//refuse access to system by deleting user by admin
	public boolean deleteUser(long val) {
		boolean result = false;
		int update = 0;
		try {
			begin();
			Criteria c = getSession().createCriteria(User.class);
			c.add(Restrictions.idEq(val));
			c.setMaxResults(1);
			User userRes = (User) c.uniqueResult();
			getSession().delete(userRes);
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
		result = update > 0 ? true : false;
		return result;
	}

	public User getUserObj(String username) {
		// TODO Auto-generated method stub
		Criteria c = getSession().createCriteria(User.class);
		c.add(Restrictions.eq("username", username));
		
		User user = (User) c.uniqueResult(); 
		return user;
	}
}
