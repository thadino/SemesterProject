/**

 *

 */
package com.metasearch.service.dao;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import openshift_deploy.DeploymentConfiguration;

import org.json.JSONObject;

/**
 * @author Goran
 *
 */

public class ReservationAuditLogDAO
{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    private static EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

    public static synchronized ReservationAuditLog addEntry(JSONObject reservation, int status)
    {

        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        try{
            System.out.println("i add entry");
        Date queryDate = new Date();
        Calendar dateTime = Calendar.getInstance();
        dateTime.setTime(queryDate);

        Calendar date = Calendar.getInstance();
        date.clear();
        date.set(Calendar.YEAR, dateTime.get(Calendar.YEAR));
        date.set(Calendar.MONTH, dateTime.get(Calendar.MONTH));
        date.set(Calendar.DATE, dateTime.get(Calendar.DATE));

        ReservationAuditLog auditLog = new ReservationAuditLog();

        auditLog.setResDateTime(queryDate);
        String flightID = reservation.getString(JSONConstants.FLIGHT_ID);
        Integer seatNum = reservation.getInt(JSONConstants.SEAT_NUMBER);
        String reserveeName = reservation.getString(JSONConstants.RESERVEE_NAME);
        String reserveePhone = reservation.getString(JSONConstants.RESERVEE_PHONE);
        String reserveeEmail = reservation.getString(JSONConstants.RESERVEE_EMAIL);
        String passengerList = reservation.getJSONArray(JSONConstants.PASSENGERS).toString();
        auditLog.setFlightID(flightID);
        auditLog.setPassengers(passengerList);
        auditLog.setReserveeEmail(reserveeEmail);
        auditLog.setReserveeName(reserveeName);
        auditLog.setReserveePhone(reserveePhone);
        auditLog.setSeatNumber(seatNum);
        auditLog.setReqStatus(status);

        System.out.println("Persisting reservation audit log " + auditLog);

        em.getTransaction().begin();
        em.persist(auditLog);
        em.getTransaction().commit();
         System.out.println("slut add entry");

        return auditLog;
        }
        finally
        {
            em.close();
        }
    }

}

