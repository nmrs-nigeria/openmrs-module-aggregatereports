/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality;

/**
 * @author lordmaul
 */
public class Patient {
	
	private int patientId;
	
	private String pepfarId;
	
	private String givenName;
	
	private String artStartDate;
	
	private String familyName;
	
	private String dob;
	
	private String gender;
	
	private float previousViralLoad;
	
	public float getPreviousViralLoad() {
		return previousViralLoad;
	}
	
	public void setPreviousViralLoad(float previousViralLoad) {
		this.previousViralLoad = previousViralLoad;
	}
	
	public String getPreviousSampleCollectionDate() {
		return previousSampleCollectionDate;
	}
	
	public void setPreviousSampleCollectionDate(String previousSampleCollectionDate) {
		this.previousSampleCollectionDate = previousSampleCollectionDate;
	}
	
	private float viralLoad;
	
	private String lastPickupDate;
	
	private String nextAppointmentDate;
	
	private String sampleCollectionDate;
	
	private String previousSampleCollectionDate;
	
	private int age;
	
	private int cage;
	
	private String fullDisclosure = "";
	
	private String fullDisclosureDate = "";
	
	public int getPatientId() {
		return patientId;
	}
	
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	public String getGivenName() {
		return givenName;
	}
	
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public float getViralLoad() {
		return viralLoad;
	}
	
	public void setViralLoad(float viralLoad) {
		this.viralLoad = viralLoad;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPepfarId() {
		return pepfarId;
	}
	
	public void setPepfarId(String pepfarId) {
		this.pepfarId = pepfarId;
	}
	
	public String getSampleCollectionDate() {
		return sampleCollectionDate;
	}
	
	public void setSampleCollectionDate(String sampleCollectionDate) {
		this.sampleCollectionDate = sampleCollectionDate;
	}
	
	public String getArtStartDate() {
		return artStartDate;
	}
	
	public void setArtStartDate(String artStartDate) {
		this.artStartDate = artStartDate;
	}
	
	public String getLastPickupDate() {
		return lastPickupDate;
	}
	
	public void setLastPickupDate(String lastPickupDate) {
		this.lastPickupDate = lastPickupDate;
	}
	
	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}
	
	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}
	
	public String getFullDisclosure() {
		return fullDisclosure;
	}
	
	public void setFullDisclosure(String fullDisclosure) {
		this.fullDisclosure = fullDisclosure;
	}
	
	public String getFullDisclosureDate() {
		return fullDisclosureDate;
	}
	
	public void setFullDisclosureDate(String fullDisclosureDate) {
		this.fullDisclosureDate = fullDisclosureDate;
	}
	
	public int getCage() {
		return cage;
	}
	
	public void setCage(int cage) {
		this.cage = cage;
	}
	
}
