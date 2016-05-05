package com.metasearch.service.dao;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the reservation_audit_log database table.
 * 
 */
@Embeddable
public class ReservationAuditLogPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	private String resID;

	@Temporal(TemporalType.DATE)
	private java.util.Date resDate;

	public ReservationAuditLogPK() {
	}
//	public String getResID() {
//		return this.resID;
//	}
//	public void setResID(String resID) {
//		this.resID = resID;
//	}
	public java.util.Date getResDate() {
		return this.resDate;
	}
	public void setResDate(java.util.Date resDate) {
		this.resDate = resDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationAuditLogPK other = (ReservationAuditLogPK) obj;
		if (resDate == null) {
			if (other.resDate != null)
				return false;
		} else if (!resDate.equals(other.resDate))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resDate == null) ? 0 : resDate.hashCode());
		return result;
	}
}