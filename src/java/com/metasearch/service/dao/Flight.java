package com.metasearch.service.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the flights database table.
 * 
 */
@Entity
@Table(name="flights")
@NamedQuery(name="Flight.findAll", query="SELECT f FROM Flight f")
public class Flight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String airline;

	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;

	private String destinationAirport;

	private String originAirport;

	private int pricePerSeatEuro;

	private int seatsAvailable;

	private int tripDurationMins;

	public Flight() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAirline() {
		return this.airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public Date getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getDestinationAirport() {
		return this.destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public String getOriginAirport() {
		return this.originAirport;
	}

	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	public int getPricePerSeatEuro() {
		return this.pricePerSeatEuro;
	}

	public void setPricePerSeatEuro(int pricePerSeatEuro) {
		this.pricePerSeatEuro = pricePerSeatEuro;
	}

	public int getSeatsAvailable() {
		return this.seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public int getTripDurationMins() {
		return this.tripDurationMins;
	}

	public void setTripDurationMins(int tripDurationMins) {
		this.tripDurationMins = tripDurationMins;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Flight [id=" + id + ", airline=" + airline + ", departureTime="
				+ departureTime + ", destinationAirport=" + destinationAirport
				+ ", originAirport=" + originAirport + ", pricePerSeatEuro="
				+ pricePerSeatEuro + ", seatsAvailable=" + seatsAvailable
				+ ", tripDurationMins=" + tripDurationMins + "]";
	}

}