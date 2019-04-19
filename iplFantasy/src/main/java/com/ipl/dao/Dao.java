package com.ipl.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ipl.pojo.Fixture;
import com.ipl.pojo.Player;
import com.ipl.pojo.Team;
import com.ipl.pojo.User;
import com.ipl.pojo.Userselection;

public class Dao {
	
	private static final Logger log = Logger.getAnonymousLogger();
    
	private static final ThreadLocal sessionThread = new ThreadLocal();
	static SessionFactory sessionFactory;
    //You havnt nt put classes yet

    protected Dao() {
    	Configuration conf = new Configuration().configure("hibernate.cfg.xml");
    	conf.addAnnotatedClass(User.class);
    	conf.addAnnotatedClass(Team.class);
    	conf.addAnnotatedClass(Player.class);
    	conf.addAnnotatedClass(Fixture.class);
    	conf.addAnnotatedClass(Userselection.class);
    	sessionFactory = conf.buildSessionFactory();
    }

    public static Session getSession()
    {
    	
        Session session = (Session) Dao.sessionThread.get();
        
        if (session == null)
        {
            session = sessionFactory.openSession();
            Dao.sessionThread.set(session);
        }
        return session;
    }

    protected void begin() {
        getSession().beginTransaction();
    }

    protected void commit() {
        getSession().getTransaction().commit();
    }

    protected void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot rollback", e);
        }
        try {
            getSession().close();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot close", e);
        }
        Dao.sessionThread.set(null);
    }

    public static void close() {
        getSession().close();
        Dao.sessionThread.set(null);
    }
}