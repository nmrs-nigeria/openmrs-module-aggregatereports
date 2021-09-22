/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openmrs.module.dataquality;

/**
 * @author lordmaul
 */

public class OTZPatient extends Patient {
	
	private String enrollmentDate;
	
	public String getEnrollmentDate() {
		return enrollmentDate;
	}
	
	public void setEnrollmentDate(String enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	
}
