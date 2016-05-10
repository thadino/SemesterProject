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
            urls = new ArrayList();
            Query q = em.createNamedQuery("airline.findAll", Airline.class);
            for (int y = 0; y < q.getResultList().size(); y++)
            {
                Airline bob = (Airline) q.getResultList().get(y);
                urls.add(bob);
                System.out.println("Følgende URL lagt i liste: " + urls.get(y).getUrl());
            }
            urls.add(a);
        }
        finally
        {
            em.close();
        }

    }

    public static synchronized void deleteAirline(String id) throws Exception
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            em.getTransaction().begin();
//            Query q = em.createNamedQuery("airline.findByURL");
//            q.setParameter("url", url);
            Airline a = (Airline) em.find(Airline.class, Long.parseLong(id));
            urls = new ArrayList();
//            Airline a = (Airline) q.getSingleResult();
            em.remove(a);
            DeploymentConfiguration.urls.remove(a);
            System.out.println("HER ER URLS::::::::::: " + urls.toString());
            em.getTransaction().commit();

            urls = new ArrayList();
            Query q = em.createNamedQuery("airline.findAll", Airline.class);
            for (int y = 0; y < q.getResultList().size(); y++)
            {

                Airline bob = (Airline) q.getResultList().get(y);
                urls.add(bob);
                System.out.println("Følgende URL lagt i liste: " + urls.get(y).getUrl());
            }

        }
        finally
        {
            em.close();
        }

    }
}
