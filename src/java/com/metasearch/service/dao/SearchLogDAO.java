/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metasearch.service.dao;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author kaspe
 */
public class SearchLogDAO
{

    public static synchronized void addEntry(String from, String to, String searchedDate, int ticketNumber)
    {
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        try
        {
            em.getTransaction().begin();
            SearchLog sl = new SearchLog();
            sl.setDepartureDate(searchedDate);
            sl.setFromAirport(from);
            sl.setSeatsRequested(ticketNumber);
            sl.setToAirport(to);
            Date queryDate = new Date();
            Calendar dateTime = Calendar.getInstance();
            dateTime.setTime(queryDate);

            Calendar date = Calendar.getInstance();
            date.clear();
            date.set(Calendar.YEAR, dateTime.get(Calendar.YEAR));
            date.set(Calendar.MONTH, dateTime.get(Calendar.MONTH));
            date.set(Calendar.DATE, dateTime.get(Calendar.DATE));
            date.set(Calendar.HOUR, dateTime.get(Calendar.HOUR));
             date.set(Calendar.MINUTE, dateTime.get(Calendar.MINUTE));
            date.set(Calendar.SECOND, dateTime.get(Calendar.SECOND));

            sl.setReqDateTime(date.getTime());
            em.persist(sl);
            em.getTransaction().commit();
        }

        finally
        {
            em.close();
        }
    }

}
