package com.metasearch.service.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the audit_log database table.
 * 
 */
@Entity
@Table(name="audit_log")
@NamedQuery(name="AuditLog.findAll", query="SELECT a FROM AuditLog a")
public class AuditLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuditLogPK id;

//	@Temporal(TemporalType.DATE)
	private String departureDate;

	private String fromAirport;

	@Temporal(TemporalType.TIMESTAMP)
	private Date reqDateTime;

	private int seatsRequested;

	private String toAirport;

	public AuditLog() {
	}

	public AuditLogPK getId() {
		return this.id;
	}

	public void setId(AuditLogPK id) {
		this.id = id;
	}

	public String getDepartureDate() {
		return this.departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getFromAirport() {
		return this.fromAirport;
	}

	public void setFromAirport(String fromAirport) {
		this.fromAirport = fromAirport;
	}

	public Date getReqDateTime() {
		return this.reqDateTime;
	}

	public void setReqDateTime(Date reqDateTime) {
		this.reqDateTime = reqDateTime;
	}

	public int getSeatsRequested() {
		return this.seatsRequested;
	}

	public void setSeatsRequested(int seatsRequested) {
		this.seatsRequested = seatsRequested;
	}

	public String getToAirport() {
		return this.toAirport;
	}

	public void setToAirport(String toAirport) {
		this.toAirport = toAirport;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuditLog [id=" + id + ", departureDate=" + departureDate
				+ ", fromAirport=" + fromAirport + ", reqDateTime="
				+ reqDateTime + ", seatsRequested=" + seatsRequested
				+ ", toAirport=" + toAirport + "]";
	}

}