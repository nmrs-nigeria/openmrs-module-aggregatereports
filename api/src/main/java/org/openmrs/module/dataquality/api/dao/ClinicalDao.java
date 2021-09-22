/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.dataquality.Constants;
import org.openmrs.module.dataquality.Misc;

/**
 * @author lordmaul
 */
public class ClinicalDao {
	
	DbSessionFactory sessionFactory;
	
	public DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Session getHibernateSession() {
		
		return sessionFactory.getHibernateSessionFactory().getCurrentSession();
	}
	
	public Integer getActivePatientsWithDocumentedEducationalStatus() {
		StringBuilder queryString = new StringBuilder(
		        "SELECT DISTINCT patient.patient_id, MAX(encounter.encounter_datetime) AS last_encounter  FROM patient ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=?  "
		                + " AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id=?) "
		                + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
		                + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
		                + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL (durationobs.value_numeric+28) DAY) > ? AND patient.voided=0 "
		                + " AND durationobs.value_numeric IS NOT NULL");
		queryString.append(" AND patient.patient_id  IN (SELECT person_id FROM obs "
		        + "JOIN encounter ON encounter.patient_id=obs.person_id AND encounter.form_id="
		        + Constants.HIV_ENROLLMENT_FORM
		        + " WHERE concept_id=1712 AND value_coded IS NOT NULL )  group by patient.patient_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		DateTime now = new DateTime(new Date());
		//now = now.minusDays(28);
		int i = 0;
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		List<Object> data = query.list();
		//Object[] dataObject = (Object[]) data.get(0);
		
		//return Integer.parseInt(dataObject[0].toString());
		return data.size();
	}
	
	public List<Object> getActivePatientsWithoutDocumentedEducationalStatus() {
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, IFNULL(hivE.encounter_id, 0) AS encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name, MAX(encounter.encounter_datetime) AS last_encounter  FROM person ");
		queryString
		        .append("  JOIN patient ON patient.patient_id=person.person_id "
		                + "JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN person_name ON person_name.person_id=person.person_id ");
		queryString.append("  LEFT JOIN encounter hivE ON hivE.patient_id=person.person_id AND hivE.form_id=?");
		
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=?  "
		                + " AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id=?) "
		                + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
		                + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
		                + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL (durationobs.value_numeric+28) DAY) > ? AND patient.voided=0 "
		                + " AND durationobs.value_numeric IS NOT NULL");
		queryString
		        .append(" AND (person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=1712 AND value_coded IS NULL ) "
		                + " OR person.person_id NOT IN (SELECT person_id FROM obs WHERE concept_id=1712)  ) ");
		queryString.append(" GROUP BY person.person_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		System.out.println(queryString.toString());
		DateTime now = new DateTime(new Date());
		//now = now.minusDays(28);
		int i = 0;
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		query.setInteger(i++, Constants.HIV_ENROLLMENT_FORM);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getActivePatientsWithDocumentedMaritalStatus() {
		StringBuilder queryString = new StringBuilder(
		        "SELECT DISTINCT patient.patient_id, MAX(encounter.encounter_datetime) AS last_encounter FROM patient ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=?  "
		                + " AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id=?) "
		                + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
		                + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
		                + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL (durationobs.value_numeric+28) DAY) > ? AND patient.voided=0 "
		                + " AND durationobs.value_numeric IS NOT NULL");
		queryString
		        .append(" AND patient.patient_id  IN (SELECT person_id FROM obs "
		                + "JOIN encounter ON encounter.patient_id=obs.person_id AND encounter.form_id="
		                + Constants.HIV_ENROLLMENT_FORM
		                + " WHERE concept_id=1054 AND value_coded IS NOT NULL )   AND patient.voided=0 GROUP BY patient.patient_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		DateTime now = new DateTime(new Date());
		//now = now.minusDays(28);
		int i = 0;
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		List<Object> data = query.list();
		//Object[] dataObject = (Object[]) data.get(0);
		
		//return Integer.parseInt(dataObject[0].toString());
		return data.size();
	}
	
	public List<Object> getActivePatientsWithoutDocumentedMaritalStatus() {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, IFNULL(hivE.encounter_id, 0) AS encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name, MAX(encounter.encounter_datetime) AS last_encounter  FROM person ");
		queryString
		        .append("  JOIN patient ON patient.patient_id=person.person_id "
		                + "JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN person_name ON person_name.person_id=person.person_id ");
		queryString.append("  LEFT JOIN encounter hivE ON hivE.patient_id=person.person_id AND hivE.form_id=?");
		
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=?  "
		                + " AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id=?) "
		                + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
		                + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
		                + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL (durationobs.value_numeric+28) DAY) > ? AND patient.voided=0 "
		                + " AND durationobs.value_numeric IS NOT NULL");
		queryString
		        .append(" AND (person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=1054 AND value_coded IS NULL ) "
		                + " OR person.person_id NOT IN (SELECT person_id FROM obs WHERE concept_id=1054)  ) ");
		queryString.append(" GROUP BY person.person_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		DateTime now = new DateTime(new Date());
		//now = now.minusDays(28);
		int i = 0;
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		query.setInteger(i++, Constants.HIV_ENROLLMENT_FORM);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getActivePatientsWithDocumentedOccupationalStatus() {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT DISTINCT patient.patient_id, MAX(encounter.encounter_datetime) AS last_encounter FROM patient ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=?  "
		                + " AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id=?) "
		                + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
		                + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
		                + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL (durationobs.value_numeric+28) DAY) > ? AND patient.voided=0 "
		                + " AND durationobs.value_numeric IS NOT NULL");
		queryString
		        .append(" AND patient.patient_id  IN (SELECT person_id FROM obs WHERE concept_id=1542 AND value_coded IS NOT NULL ) AND patient.voided=0 GROUP BY patient.patient_id ");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		DateTime now = new DateTime(new Date());
		//now = now.minusDays(28);
		int i = 0;
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		List<Object> data = query.list();
		//Object[] dataObject = (Object[]) data.get(0);
		
		//return Integer.parseInt(dataObject[0].toString());
		return data.size();
	}
	
	public List<Object> getActivePatientsWithoutDocumentedOccupationalStatus() {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, IFNULL(hivE.encounter_id, 0) AS encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name, MAX(encounter.encounter_datetime) AS last_encounter  FROM person ");
		queryString
		        .append("  JOIN patient ON patient.patient_id=person.person_id "
		                + "JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN person_name ON person_name.person_id=person.person_id ");
		queryString.append("  LEFT JOIN encounter hivE ON hivE.patient_id=person.person_id AND hivE.form_id=?");
		
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=?  "
		                + " AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id=?) "
		                + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
		                + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
		                + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL (durationobs.value_numeric+28) DAY) > ? AND patient.voided=0 "
		                + " AND durationobs.value_numeric IS NOT NULL");
		queryString
		        .append(" AND (person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=1542 AND value_coded IS NULL ) "
		                + " OR person.person_id NOT IN (SELECT person_id FROM obs WHERE concept_id=1542)  ) ");
		queryString.append(" GROUP BY person.person_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		DateTime now = new DateTime(new Date());
		//now = now.minusDays(28);
		int i = 0;
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		query.setInteger(i++, Constants.HIV_ENROLLMENT_FORM);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPatientsWithDocumentedDobCount(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder("SELECT DISTINCT patient.patient_id FROM patient  ");
		queryString.append(" JOIN person ON person.person_id=patient.patient_id ");
		queryString
		        .append("where patient.patient_id  IN (SELECT   patient_id FROM patient_program WHERE program_id=1 AND date_enrolled BETWEEN ? AND ?  ) AND patient.voided=0 ");
		queryString.append(" AND person.birthdate IS NOT NULL GROUP BY patient.patient_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		//Object data = query.uniqueResult();
		
		//return Integer.parseInt(data.toString());
		List<Object> data = query.list();
		
		//return Integer.parseInt(data.toString());
		return data.size();
	}
	
	public List<Object> getPatientsWithDocumentedDob(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN person_name ON person_name.person_id=person.person_id ");
		queryString
		        .append("where person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString.append(" AND person.birthdate IS NULL ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPatientsWithDocumentedGenderCount(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder("SELECT  DISTINCT person.person_id FROM person  ");
		queryString.append("JOIN patient ON patient.patient_id=person.person_id ");
		queryString
		        .append("where person.person_id  IN (SELECT patient_id FROM patient_program WHERE program_id=1 AND date_enrolled BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString.append(" AND (person.gender='M' OR person.gender='F') GROUP BY patient.patient_id  ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		List<Object> data = query.list();
		
		//return Integer.parseInt(data.toString());
		return data.size();
	}
	
	public List<Object> getPatientsWithoutDocumentedGender(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN person_name ON person_name.person_id=person.person_id ");
		queryString
		        .append("where person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString
		        .append(" AND (person.gender !='M' AND person.gender !='F' AND person.gender !='Male' AND person.gender !='Female')  ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		List<Object> data = query.list();
		return data;
	}
	
	//new queries start here
	
	public List<Map<String,String>> getClinicalEncounters(int patientId)
      {
          List<Map<String,String>> clinicalEncounters = new ArrayList<>();
          
          PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "  SELECT encounter.encounter_id, weightobs.value_numeric AS weight, whostagingobs.value_coded AS whostage, " +
                                        " muacobs.value_coded AS muac, tbstatusobs.value_coded AS tbstatus, " +
                                        " functionalstatusobs.value_coded AS functional_status, nextappdateobs.value_datetime AS nextappdate " +
                                        " from encounter " +
                                        " LEFT JOIN obs weightobs ON weightobs.encounter_id=encounter.encounter_id AND weightobs.concept_id=5089 AND weightobs.voided=0 " +
                                        " LEFT JOIN obs muacobs ON muacobs.encounter_id=encounter.encounter_id AND muacobs.concept_id=165935 AND muacobs.voided=0 " +
                                        " LEFT JOIN obs tbstatusobs ON tbstatusobs.encounter_id=encounter.encounter_id AND tbstatusobs.concept_id=1659 AND tbstatusobs.voided=0  " +
                                        " LEFT JOIN obs functionalstatusobs ON functionalstatusobs.encounter_id=encounter.encounter_id AND functionalstatusobs.concept_id=165039 AND functionalstatusobs.voided=0  " +
                                        " LEFT JOIN obs whostagingobs ON whostagingobs.encounter_id=encounter.encounter_id AND whostagingobs.concept_id=5356 AND whostagingobs.voided=0  " +
                                        " LEFT JOIN obs nextappdateobs ON nextappdateobs.encounter_id=encounter.encounter_id AND nextappdateobs.concept_id=5096 AND nextappdateobs.voided=0 " +
                                        " WHERE encounter.encounter_type=12  AND patient_id=? AND encounter.voided=0 " +
                                        " GROUP BY encounter.encounter_id  ";
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        stmt.setInt(i++, patientId);
			//stmt.setFetchSize(200);
			rs = stmt.executeQuery();
			//rs.setFetchSize(limit);
			while (rs.next()) {
                            Map<String,String> tempMap = new HashMap<>();
                            tempMap.put("patient_id", patientId+"");
                            tempMap.put("encounter_id", rs.getString("encounter_id"));
                            tempMap.put("weight", rs.getString("weight"));
                            tempMap.put("muac", Misc.getMuac(rs.getInt("muac")));
                            tempMap.put("tbstatus", Misc.getTBStatus(rs.getInt("tbstatus")));
                            tempMap.put("functional_status", Misc.getFunctionalStatus(rs.getInt("functional_status")));
                            tempMap.put("who_stage", Misc.getWHOStageStatus(rs.getInt("whostage")));
                            tempMap.put("nextappdate", rs.getString("nextappdate"));  
                            clinicalEncounters.add(tempMap);
			}
			return clinicalEncounters;
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return null;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
          
          
      }
	
	public int saveClinicalEncounters(List<Map<String, String>> allClinicalEncounters) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "INSERT INTO dqr_clinicals (patient_id, encounter_id, weight, muac, tb_status, functional_status, nextapp_date, who_stage)VALUES";
			for (int i = 0; i < allClinicalEncounters.size(); i++) {
				query += "(?, ?, ?, ?, ?, ?, ?, ?),";
			}
			
			query = query.substring(0, query.length() - 1);
			query += " ON DUPLICATE KEY UPDATE weight=VALUES(weight), muac=VALUES(muac), tb_status=VALUES(tb_status), ";
			query += " functional_status=VALUES(functional_status), nextapp_date=VALUES(nextapp_date), who_stage=VALUES(who_stage)";
			
			int i = 1;
			stmt = con.prepareStatement(query);
			
			for (int j = 0; j < allClinicalEncounters.size(); j++) {
				
				//check if next appointment date is not equal to null
				String nextAppDate = (allClinicalEncounters.get(j).get("nextappdate") != null && !allClinicalEncounters
				        .get(j).get("nextappdate").equalsIgnoreCase("")) ? allClinicalEncounters.get(j).get("nextappdate")
				        : null;
				//System.out.println("next appdate: " + allClinicalEncounters.get(j).get("nextappdate"));
				stmt.setInt(i++, Integer.parseInt(allClinicalEncounters.get(j).get("patient_id")));
				stmt.setInt(i++, Integer.parseInt(allClinicalEncounters.get(j).get("encounter_id")));
				stmt.setString(i++, allClinicalEncounters.get(j).get("weight"));
				stmt.setString(i++, allClinicalEncounters.get(j).get("muac"));
				stmt.setString(i++, allClinicalEncounters.get(j).get("tbstatus"));
				stmt.setString(i++, allClinicalEncounters.get(j).get("functional_status"));
				stmt.setString(i++, nextAppDate);
				stmt.setString(i++, allClinicalEncounters.get(j).get("who_stage"));
				
			}
			//stmt.setFetchSize(200);
			if (allClinicalEncounters.size() > 0)
				stmt.executeUpdate();
			
			return 1;
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getNoActivePtsWithWithEducationalStatus(String startDate, String endDate) {
		
		System.out.println(startDate);
		System.out.println(endDate);
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE  "
			                + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ?  "
			                + "     AND dqr_pharmacy.pickupdate= ( "
			                + "		SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
			                + "	 HAVING MAX(pickupdate) <=? )   "
			                + " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  ");
			queryString.append(" AND dqr_meta.education_status!='' ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			//stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getNoActivePtsWithWithMaritalStatus(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE   "
			                + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ?  "
			                + "     AND dqr_pharmacy.pickupdate= ( "
			                + "		SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
			                + "	 HAVING MAX(pickupdate) <=? )   "
			                + " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  ");
			
			queryString.append(" AND dqr_meta.marital_status!='' ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			//stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getNoActivePtsWithWithOccupationalStatus(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE   "
			                + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ?  "
			                + "     AND dqr_pharmacy.pickupdate= ( "
			                + "		SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
			                + "	 HAVING MAX(pickupdate) <=? )   "
			                + " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  ");
			
			queryString.append(" AND dqr_meta.occupation!='' ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			//stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithDocDob(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.dob !=''  AND dqr_meta.dob IS NOT NULL ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithInitialRegimen(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.dob !=''  AND dqr_meta.dob IS NOT NULL "
			        + " AND dqr_meta.patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE regimen_line IS NOT NULL)");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithDocGender(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.gender !=''  AND dqr_meta.gender IS NOT NULL ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithDocAddress(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.address !=''  AND dqr_meta.address IS NOT NULL ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithDocHIVDiagnosisDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.hiv_diagnosis_date !=''  AND dqr_meta.hiv_diagnosis_date IS NOT NULL ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithDocDrugPickup(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.patient_id IN (SELECT patient_id FROM dqr_pharmacy) ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithDocCd4(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date BETWEEN ? AND ? ");
			queryString
			        .append(" AND dqr_meta.patient_id IN (SELECT person_id FROM obs WHERE concept_id=5497 AND value_numeric IS NOT NULL AND obs.voided=0 ) ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnARTWithDocHIVEnrollmentDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.hiv_enrollment_date !=''  AND dqr_meta.hiv_enrollment_date IS NOT NULL ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsWithClinicVisitDocWeight(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta  "
			                +
			                
			                " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ? AND (dqr_clinicals.weight IS NOT NULL) )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsWithClinicVisitDocNextAppDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta  "
			                +
			                
			                " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ? AND dqr_clinicals.nextapp_date IS NOT NULL )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsWithClinicVisitDocMuac(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT( distinct dqr_meta.patient_id) AS count FROM dqr_meta  "
			                +
			                
			                " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ? AND dqr_clinicals.muac IS NOT NULL AND dqr_clinicals.muac != '' )  "
			                + " AND TIMESTAMPDIFF(YEAR,dqr_meta.dob, ?) <15");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsWithClinicVisitDocWhoStage(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta  "
			                +
			                
			                " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ? AND dqr_clinicals.who_stage IS NOT NULL AND dqr_clinicals.who_stage != '' )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsWithClinicVisitDocTBStatus(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta  "
			                +
			                
			                " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ? AND dqr_clinicals.tb_status IS NOT NULL AND dqr_clinicals.tb_status != '' )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsWithClinicVisitDocFunctionalStatusStatus(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta  "
			                +
			                
			                " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ? AND dqr_clinicals.functional_status IS NOT NULL AND dqr_clinicals.functional_status != '' )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsWithClinicVisit(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT( distinct dqr_meta.patient_id) AS count FROM dqr_meta  " +
			        
			        " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ?  )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPedPtsWithClinicVisit(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "  select COUNT( distinct dqr_meta.patient_id) AS count FROM dqr_meta  "
			                +
			                
			                " WHERE  dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals "
			                + " JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id "
			                + " WHERE encounter.encounter_datetime BETWEEN ? AND ?  ) AND  TIMESTAMPDIFF(YEAR,dqr_meta.dob, ?) <15 ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getPtsStartedOnART(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(" select COUNT(dqr_meta.patient_id) AS count FROM dqr_meta "
			        + "	 WHERE art_start_date BETWEEN ? AND ? ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getNoActivePts(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE   "
			                + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ?  "
			                + "	 AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) "
			                + "     AND dqr_pharmacy.pickupdate= ( "
			                + "		SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
			                + "	 HAVING MAX(pickupdate) <=? )   "
			                + " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			//stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getInactiveActivePtsWithDocReason(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE  "
			                + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) < ?  "
			                + "	 AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
			                + "	 HAVING MAX(pickupdate) <=? )   " + " AND (dqr_meta.termination_status=1065   )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getInactiveActivePts(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE  "
			                + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) < ?  "
			                + "	 AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
			                + "	 HAVING MAX(pickupdate) <=? )  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
}
