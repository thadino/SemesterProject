/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metasearch.service.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author kaspe
 */
public class ReservationDAO
{

    public static synchronized List<Reservation> getAllReservationsByName(String reserveeName)
    {
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            Query query = em.createNamedQuery("reservation.findByName", Reservation.class);
            query.setParameter("name", reserveeName);
            return query.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public static synchronized void addEntry(Reservation r)
    {

        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        try
        {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();

        }
        finally
        {
            em.close();
        }

    }
}
