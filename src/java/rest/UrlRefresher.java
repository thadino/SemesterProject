/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.metasearch.service.dao.Airline;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author kaspe
 */
public class UrlRefresher
{

    public static void refreshURLs()
    {
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            Query q = em.createNamedQuery("airline.findAll", Airline.class);
            DeploymentConfiguration.urls = q.getResultList();
        }
        finally
        {

        }
    }
}
