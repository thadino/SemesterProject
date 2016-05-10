/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metasearch.service.dao;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;
import static openshift_deploy.DeploymentConfiguration.urls;

/**
 *
 * @author kaspe
 */
public class AirlineDAO
{

    static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public static synchronized void addEntry(String name, String url)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            Airline a = new Airline(name, url);
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();

            urls.add(a);
        }
        finally
        {
            em.close();
        }

    }
    
    public static synchronized void deleteAirline(String name) throws Exception
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("airline.findByName");
            q.setParameter("name", name);
            Airline a = (Airline) q.getSingleResult();
            
            em.remove(a);
            em.getTransaction().commit();
            urls.remove(a);
        }
        finally
        {
            em.close();
        }

    }
}
