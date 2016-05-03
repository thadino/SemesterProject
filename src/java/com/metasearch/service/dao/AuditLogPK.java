package com.metasearch.service.dao;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the audit_log database table.
 * 
 */
@Embeddable
public class AuditLogPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private String reqID;

	@Temporal(TemporalType.DATE)
	private java.util.Date reqDate;

	public AuditLogPK() {
	}
//	public String getReqID() {
//		return this.reqID;
//	}
//	public void setReqID(String reqID) {
//		this.reqID = reqID;
//	}
	public java.util.Date getReqDate() {
		return this.reqDate;
	}
	public void setReqDate(java.util.Date reqDate) {
		this.reqDate = reqDate;
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
		AuditLogPK other = (AuditLogPK) obj;
		if (reqDate == null) {
			if (other.reqDate != null)
				return false;
		} else if (!reqDate.equals(other.reqDate))
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
		result = prime * result + ((reqDate == null) ? 0 : reqDate.hashCode());
		return result;
	}
}