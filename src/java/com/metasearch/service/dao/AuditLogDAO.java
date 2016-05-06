/**
 * 
 */
package com.metasearch.service.dao;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Goran
 *
 */
public class AuditLogDAO {

	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("FlightService"); 
	private static EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager(); 
	
	
	public static synchronized void addEntry(String from, String to, String searchedDate, int ticketNumber) { 
		Date queryDate = new Date(); 
		Calendar dateTime = Calendar.getInstance(); 
		dateTime.setTime(queryDate); 
		
		Calendar date  = Calendar.getInstance(); 
		date.clear(); 
		date.set(Calendar.YEAR, dateTime.get(Calendar.YEAR)); 
		date.set(Calendar.MONTH, dateTime.get(Calendar.MONTH)); 
		date.set(Calendar.DATE, dateTime.get(Calendar.DATE)); 
		
		AuditLog auditLog = new AuditLog(); 
		AuditLogPK key = new AuditLogPK(); 
		key.setReqDate(date.getTime()); 
		
		auditLog.setId(key); 
		auditLog.setReqDateTime(queryDate);
		auditLog.setFromAirport(from); 
		auditLog.setToAirport(to); 
		auditLog.setDepartureDate(searchedDate); 
		auditLog.setSeatsRequested(ticketNumber); 
		
		System.out.println("Persisting "+auditLog); 
		
		manager.getTransaction().begin();
		manager.persist(auditLog);
		manager.getTransaction().commit();
	}
}
