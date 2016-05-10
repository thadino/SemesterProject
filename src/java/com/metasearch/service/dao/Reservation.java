 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metasearch.service.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author kaspe
 */
@Entity
@NamedQueries
({
    @NamedQuery(name="reservation.findAll", query="SELECT r FROM Reservation r"),
    @NamedQuery(name="reservation.findByName", query="SELECT r FROM Reservation r WHERE r.reserveeName = :name"),
    @NamedQuery(name = "reservation.findAllByEmail", query = "Select r from Reservation r where r.reserveeEmail = :email")
})
public class Reservation implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String flightID;

    private String reserveeEmail;

    private String reserveeName;

    private String reserveePhone;

    private int seatNumber;

    private String passengers;

    public Reservation(String reserveeEmail, String reserveeName, String reserveePhone, int seatNumber, String passengers, String flightID)
    {
        this.reserveeEmail = reserveeEmail;
        this.reserveeName = reserveeName;
        this.reserveePhone = reserveePhone;
        this.seatNumber = seatNumber;
        this.passengers = passengers;
        this.flightID = flightID;
    }

    public Reservation()
    {
    }
    
    

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getReserveeEmail()
    {
        return reserveeEmail;
    }

    public void setReserveeEmail(String reserveeEmail)
    {
        this.reserveeEmail = reserveeEmail;
    }

    public String getReserveeName()
    {
        return reserveeName;
    }

    public void setReserveeName(String reserveeName)
    {
        this.reserveeName = reserveeName;
    }

    public String getReserveePhone()
    {
        return reserveePhone;
    }

    public void setReserveePhone(String reserveePhone)
    {
        this.reserveePhone = reserveePhone;
    }

    public int getSeatNumber()
    {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber)
    {
        this.seatNumber = seatNumber;
    }

    public String getPassengers()
    {
        return passengers;
    }

    public void setPassengers(String passengers)
    {
        this.passengers = passengers;
    }

    public String getFlightID()
    {
        return flightID;
    }

    public void setFlightID(String flightID)
    {
        this.flightID = flightID;
    }

}
