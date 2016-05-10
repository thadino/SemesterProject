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
    @NamedQuery(name="airline.findAll", query="SELECT a FROM Airline a"),
    @NamedQuery(name="airline.findByName",query="SELECT a FROM Airline a WHERE a.name = :name"),
    @NamedQuery(name="airline.findByURL",query="SELECT a FROM Airline a WHERE a.url = :url")
})
public class Airline implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String url;

    public Airline()
    {
    }

    public Airline(String name, String url)
    {
        this.name = name;
        this.url = url;
    }
    

    
    
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    
    

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    

    
}