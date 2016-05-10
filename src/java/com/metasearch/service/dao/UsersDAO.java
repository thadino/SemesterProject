/**
 * 
 */
package com.metasearch.service.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

import org.json.JSONObject;

/**
 * @author Goran
 *
 */
public class UsersDAO {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(DeploymentConfiguration.PU_NAME); 
	private static EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager(); 
	
	
	public static void addEntry(User user) { 
		System.out.println("Persisting user "+user); 
		
		manager.getTransaction().begin(); 
		manager.persist(user); 
		manager.getTransaction().commit(); 
	} 
	
	
	public static void deleteUser(String userName) { 
		Query query = manager.createQuery("DELETE FROM User u WHERE"
        		+ " u.userName = :u"
        		, User.class);
        query.setParameter("u", userName); 
        manager.getTransaction().begin(); 
        int deleted = query.executeUpdate(); 
        manager.getTransaction().commit(); 
        System.out.println("Deleted size: "+deleted); 
	}
	
	
	public static List<User> getByRole(String role) { 
		Query query = manager.createQuery("Select u FROM User u WHERE"
        		+ " u.role = :userRole"
        		, User.class);
        query.setParameter("userRole", role); 
        List<User> users = query.getResultList(); 
        System.out.println("Flights list size: "+users.size()); 
		return users; 
	}
	
} 
