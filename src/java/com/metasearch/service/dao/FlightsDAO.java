/**
 * 
 */
package com.metasearch.service.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.metasearch.service.exception.FlightNotFoundException;
import com.metasearch.service.exception.InsufficientTicketsException;

/**
 * @author Goran
 *
 */
public class FlightsDAO {

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("FlightService"); 
	private static EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
	
	public static List<Flight> getFlights(String from, Date date, int requiredTickets) { 
		System.out.println("Date "+date); 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1); 
		Date endDate = calendar.getTime(); 
		System.out.println("Date end "+endDate); 
		
//		manager.flush(); 
		Query query = manager.createQuery("Select f FROM Flight f WHERE"
        		+ " f.originAirport = :from "
        		+ "and f.departureTime > :periodStart "
        		+ "and f.departureTime < :periodEnd "
        		+ "and f.seatsAvailable > :required ", Flight.class);
        query.setParameter("from", from); 
        query.setParameter("periodStart", date); 
        query.setParameter("periodEnd", endDate); 
        query.setParameter("required", requiredTickets); 
        List<Flight> flights = query.getResultList(); 
        System.out.println("Flights list size: "+flights.size()); 
		return flights; 
	} 
	
	
	public static List<Flight> getFlights(String from, String to, Date date, int requiredTickets) { 
		System.out.println("Date "+date); 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1); 
		Date endDate = calendar.getTime(); 
		System.out.println("Date end "+endDate); 
//		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		Query query = manager.createQuery("Select f FROM Flight f WHERE"
        		+ " f.originAirport = :from "
        		+ "and f.destinationAirport = :destination "
        		+ "and f.departureTime > :periodStart "
        		+ "and f.departureTime < :periodEnd "
        		+ "and f.seatsAvailable > :required ", Flight.class);
        query.setParameter("from", from); 
        query.setParameter("destination", to); 
        query.setParameter("periodStart", date); 
        query.setParameter("periodEnd", endDate); 
        query.setParameter("required", requiredTickets); 
        List<Flight> flights = query.getResultList(); 
        System.out.println("Flights list size: "+flights.size()); 
		return flights; 
	} 
	
	
	public static synchronized Flight reserve(int flightID, int ticketNumber) throws FlightNotFoundException, InsufficientTicketsException { 
		Query query = manager.createQuery("Select f FROM Flight f WHERE"
        		+ " f.id = :id "
        		, Flight.class);
        query.setParameter("id", flightID); 
        try { 
        	Flight flight = (Flight)query.getSingleResult(); 
        	int currentlyAvailable = flight.getSeatsAvailable(); 
            if(currentlyAvailable < ticketNumber) { 
            	throw new InsufficientTicketsException("Insufficient tickets free, requested "+ticketNumber+", available "+currentlyAvailable); 
            } 
            System.out.println("Flight "+flight); 
            flight.setSeatsAvailable(currentlyAvailable - ticketNumber);
            System.out.println("Persisting "+flight); 
            manager.getTransaction().begin(); 
    		manager.persist(flight); 
    		manager.getTransaction().commit(); 
    		return flight; 
        } catch (NoResultException e) { 
        	throw new FlightNotFoundException("No flight with flight ID "+flightID+" found"); 
        }         
	}
}
