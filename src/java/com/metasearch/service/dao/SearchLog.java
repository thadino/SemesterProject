/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metasearch.service.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kaspe
 */
@Entity
public class SearchLog implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String departureDate;

    private String fromAirport;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date reqDateTime;

    private int seatsRequested;

    private String toAirport;

    public SearchLog()
    {
    }

    public SearchLog(String departureDate, String fromAirport, Date reqDateTime, int seatsRequested, String toAirport)
    {
        this.departureDate = departureDate;
        this.fromAirport = fromAirport;
        this.reqDateTime = reqDateTime;
        this.seatsRequested = seatsRequested;
        this.toAirport = toAirport;
    }
    
    

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDepartureDate()
    {
        return departureDate;
    }

    public void setDepartureDate(String departureDate)
    {
        this.departureDate = departureDate;
    }

    public String getFromAirport()
    {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport)
    {
        this.fromAirport = fromAirport;
    }

    public Date getReqDateTime()
    {
        return reqDateTime;
    }

    public void setReqDateTime(Date reqDateTime)
    {
        this.reqDateTime = reqDateTime;
    }

    public int getSeatsRequested()
    {
        return seatsRequested;
    }

    public void setSeatsRequested(int seatsRequested)
    {
        this.seatsRequested = seatsRequested;
    }

    public String getToAirport()
    {
        return toAirport;
    }

    public void setToAirport(String toAirport)
    {
        this.toAirport = toAirport;
    }
    

}
