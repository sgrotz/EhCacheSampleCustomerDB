package com.ehcache.objects;

import java.io.Serializable;

public class customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7495467113963418373L;
	
	public int ID;
	public String FIRSTNAME;
	public String LASTNAME;
	public String REGION;
	public String ADDRESS;
	
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public String getFIRSTNAME() {
		return FIRSTNAME;
	}
	
	public void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME = fIRSTNAME;
	}
	
	public String getLASTNAME() {
		return LASTNAME;
	}
	
	public void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}
	
	public String getREGION() {
		return REGION;
	}
	
	public void setREGION(String rEGION) {
		REGION = rEGION;
	}
	
	public String getADDRESS() {
		return ADDRESS;
	}
	
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	
	
	
	
	
}
