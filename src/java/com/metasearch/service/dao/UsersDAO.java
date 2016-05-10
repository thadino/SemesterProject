/**
 *
 */
package com.metasearch.service.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

import org.json.JSONObject;
import security.PasswordStorage;

/**
 * @author Goran
 *
 */
public class UsersDAO
{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//    private static EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

    public static synchronized void addEntry(User user)
    {
        System.out.println("Persisting user " + user);
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            manager.getTransaction().begin();
            manager.persist(user);
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
    }

    public static synchronized void editUser(JSONObject personJSON) throws PasswordStorage.CannotPerformOperationException
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        try
        {
            String userName = personJSON.getString("userName");
            System.out.println(" ----- " + userName);
            String password = personJSON.getString("passWord");
            System.out.println(" ----- " + password);
            String email = personJSON.getString("email");
            System.out.println(" ----- " + email);
            String role = personJSON.getString("role");
            System.out.println(" ----- " + role);
            
            em.getTransaction().begin();            
            User u = em.find(User.class, userName);
            u.setEmail(email);
            u.setPassword(PasswordStorage.createHash(password));
            u.setUserName(userName);
            em.getTransaction().commit();

        }

        finally
        {
            em.close();
        }
    }

    public static synchronized void deleteUser(String userName)
    {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            Query query = manager.createQuery("DELETE FROM User u WHERE"
                    + " u.userName = :u", User.class);
            query.setParameter("u", userName);
            manager.getTransaction().begin();
            int deleted = query.executeUpdate();
            manager.getTransaction().commit();
            System.out.println("Deleted size: " + deleted);
        }
        finally
        {
            manager.close();
        }
    }

    public static synchronized List<User> getByRole(String role)
    {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            Query query = manager.createNativeQuery("Select u FROM systemuser_userrole WHERE"
                    + " roleName = :userRole", User.class);
            query.setParameter("userRole", role);
            List<User> users = query.getResultList();
            System.out.println("Flights list size: " + users.size());
            return users;
        }
        finally
        {
            manager.close();
        }
    }

}
