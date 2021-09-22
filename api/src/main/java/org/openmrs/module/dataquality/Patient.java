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
	
	private float viralLoad;
	
	private String sampleCollectionDate;
	
	private int age;
	
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
	
}
