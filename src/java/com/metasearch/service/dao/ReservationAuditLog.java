package com.metasearch.service.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the reservation_audit_log database table.
 * 
 */
@Entity
@Table(name="reservation_audit_log")
@NamedQuery(name="ReservationAuditLog.findAll", query="SELECT r FROM ReservationAuditLog r")
public class ReservationAuditLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReservationAuditLogPK id;

	private int flightID;

	private String passengers;

	@Temporal(TemporalType.TIMESTAMP)
	private Date resDateTime;

	private String reserveeEmail;

	private String reserveeName;

	private String reserveePhone;

	private int seatNumber;
	
	private int status; 

	public ReservationAuditLog() {
	}

	public ReservationAuditLogPK getId() {
		return this.id;
	}

	public void setId(ReservationAuditLogPK id) {
		this.id = id;
	}

	public int getFlightID() {
		return this.flightID;
	}

	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public String getPassengers() {
		return this.passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public Date getResDateTime() {
		return this.resDateTime;
	}

	public void setResDateTime(Date resDateTime) {
		this.resDateTime = resDateTime;
	}

	public String getReserveeEmail() {
		return this.reserveeEmail;
	}

	public void setReserveeEmail(String reserveeEmail) {
		this.reserveeEmail = reserveeEmail;
	}

	public String getReserveeName() {
		return this.reserveeName;
	}

	public void setReserveeName(String reserveeName) {
		this.reserveeName = reserveeName;
	}

	public String getReserveePhone() {
		return this.reserveePhone;
	}

	public void setReserveePhone(String reserveePhone) {
		this.reserveePhone = reserveePhone;
	}

	public int getSeatNumber() {
		return this.seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * @return the reqStatus
	 */
	public int getReqStatus() {
		return status;
	}

	/**
	 * @param reqStatus the reqStatus to set
	 */
	public void setReqStatus(int reqStatus) {
		this.status = reqStatus;
	} 

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReservationAuditLog [id=" + id + ", flightID=" + flightID
				+ ", passengers=" + passengers + ", resDateTime=" + resDateTime
				+ ", reserveeEmail=" + reserveeEmail + ", reserveeName="
				+ reserveeName + ", reserveePhone=" + reserveePhone
				+ ", seatNumber=" + seatNumber + ", reqStatus=" + status
				+ "]";
	}

}