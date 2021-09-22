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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openmrs.module.dataquality.Misc;

/**
 * @author lordmaul
 */
public class PharmacyDao {
	
	public List<Map<String,String>> getPharmacyEncounters(int patientId)
      {
          List<Map<String,String>> pharmacyEncounters = new ArrayList<>();
          
          PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    String query = " select encounter.patient_id, encounter.encounter_id, encounter.encounter_datetime AS pickupdate, quantityobs.value_numeric AS quantity, " +
                                    " refillobs.value_numeric AS days_refill, IFNULL(regimenlineobs.value_coded, 0) AS regimenline, regimenobs.value_coded AS regimen  " +
                                    "  FROM encounter " +
                                    "  LEFT JOIN obs refillobs ON refillobs.encounter_id=encounter.encounter_id AND refillobs.concept_id=159368 AND refillobs.voided=0 " +
                                    "  AND refillobs.obs_group_id IN (select obs_id from obs where concept_id=162240) " +
                                    "  LEFT JOIN obs quantityobs ON quantityobs.encounter_id=encounter.encounter_id AND quantityobs.concept_id=1443 AND quantityobs.voided=0 " +                            
                                    "  AND quantityobs.obs_group_id IN (select obs_id from obs where concept_id=162240) " +
                                    " LEFT JOIN obs regimenlineobs ON regimenlineobs.encounter_id=encounter.encounter_id AND regimenlineobs.concept_id=165708"+ 
                                    " LEFT JOIN obs regimenobs ON regimenobs.encounter_id=encounter.encounter_id AND regimenobs.concept_id IN (164506, 164513, 165702, 164507, 164514, 165703)"+ 
                                    "  WHERE encounter.encounter_type=13 AND encounter.voided=0 AND encounter.patient_id=? GROUP BY encounter.encounter_id ORDER BY encounter.encounter_datetime   ";
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
                        tempMap.put("pickupdate", rs.getString("pickupdate"));
                        tempMap.put("days_refill", rs.getString("days_refill"));
                        tempMap.put("regimenline", rs.getString("regimenline"));
                        tempMap.put("regimen", rs.getString("regimen"));
                        tempMap.put("quantity", rs.getString("quantity"));
                        pharmacyEncounters.add(tempMap);
                    }
                    return pharmacyEncounters;

            }
            catch (SQLException ex) {
                    Database.handleException(ex);
                    return null;
            }
            finally {
                    Database.cleanUp(rs, stmt, con);
            }
          
          
      }
	
	public int savePharmacyEncounters(List<Map<String, String>> allPharmacyEncounters) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "INSERT INTO dqr_pharmacy (patient_id, encounter_id, pickupdate, days_refill, next_encounter_id, regimen_line, regimen, quantity)VALUES";
			for (int i = 0; i < allPharmacyEncounters.size(); i++) {
				query += "(?, ?, ?, ?, ?, ?, ?, ?),";
			}
			
			query = query.substring(0, query.length() - 1);
			query += " ON DUPLICATE KEY UPDATE pickupdate=VALUES(pickupdate), days_refill=VALUES(days_refill), next_encounter_id=VALUES(next_encounter_id), ";
			query += " regimen_line=VALUES(regimen_line), regimen=VALUES(regimen), quantity=VALUES(quantity) ";
			int i = 1;
			stmt = con.prepareStatement(query);
			
			int nextEncounterId = 0;
			
			for (int j = 0; j < allPharmacyEncounters.size(); j++) {
				
				if (j < (allPharmacyEncounters.size() - 1)) {
					nextEncounterId = (Integer.parseInt(allPharmacyEncounters.get(j).get("patient_id")) == Integer
					        .parseInt(allPharmacyEncounters.get(j + 1).get("patient_id"))) ? Integer
					        .parseInt(allPharmacyEncounters.get(j + 1).get("encounter_id")) : 0;
				} else {
					nextEncounterId = 0;
				}
				
				stmt.setInt(i++, Integer.parseInt(allPharmacyEncounters.get(j).get("patient_id")));
				stmt.setInt(i++, Integer.parseInt(allPharmacyEncounters.get(j).get("encounter_id")));
				stmt.setString(i++,
				    (!allPharmacyEncounters.get(j).get("pickupdate").equalsIgnoreCase("")) ? allPharmacyEncounters.get(j)
				            .get("pickupdate") : null);
				
				stmt.setString(i++, allPharmacyEncounters.get(j).get("days_refill"));
				stmt.setInt(i++, nextEncounterId);
				stmt.setString(i++, Misc.getRegimenLine(Integer.parseInt(allPharmacyEncounters.get(j).get("regimenline"))));
				stmt.setString(i++, allPharmacyEncounters.get(j).get("regimen"));
				stmt.setString(i++, allPharmacyEncounters.get(j).get("quantity"));
			}
			//stmt.setFetchSize(200);
			if (allPharmacyEncounters.size() > 0)
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
	
	public int getPtsWithMissedAppointment(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(DISTINCT dqr_meta.patient_id) AS count FROM dqr_meta ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" SELECT dqr_pharmacy.patient_id, dqr_pharmacy.pickupdate,  dqr_pharmacy.days_refill FROM dqr_pharmacy ");
			queryString.append(" WHERE dqr_pharmacy.next_encounter_id=0 AND ");
			queryString
			        .append("  DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill ) DAY) BETWEEN ? AND ? ");
			queryString.append("  AND DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill + 7) DAY) < ? ");
			queryString.append("  group by dqr_pharmacy.patient_id ");
			queryString.append(" )pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			queryString.append("  ");
			
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
	
	public int getPtsWithAppointment(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(DISTINCT dqr_meta.patient_id) AS count FROM dqr_meta ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" SELECT dqr_pharmacy.patient_id, dqr_pharmacy.pickupdate,  dqr_pharmacy.days_refill FROM dqr_pharmacy ");
			queryString.append(" WHERE dqr_pharmacy.next_encounter_id=0 AND ");
			queryString
			        .append("  DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill ) DAY) BETWEEN ? AND ? ");
			queryString.append("  group by dqr_pharmacy.patient_id ");
			queryString.append(" )pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			queryString.append("  ");
			
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
	
	public int getPedPtsWithMissedAppointment(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(DISTINCT dqr_meta.patient_id) AS count FROM dqr_meta ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" SELECT dqr_pharmacy.patient_id, dqr_pharmacy.pickupdate,  dqr_pharmacy.days_refill FROM dqr_pharmacy ");
			queryString.append(" WHERE dqr_pharmacy.next_encounter_id=0 AND ");
			queryString
			        .append("  DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill ) DAY) BETWEEN ? AND ? ");
			queryString.append("  AND DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill + 7) DAY) < ? ");
			queryString.append("  group by dqr_pharmacy.patient_id ");
			queryString.append(" )pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			queryString.append("  WHERE TIMESTAMPDIFF(YEAR,dqr_meta.dob, ?) < 15 ");
			queryString.append(" ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
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
	
	public int getPedPtsWithAppointment(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(DISTINCT dqr_meta.patient_id) AS count FROM dqr_meta ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" SELECT dqr_pharmacy.patient_id, dqr_pharmacy.pickupdate,  dqr_pharmacy.days_refill FROM dqr_pharmacy ");
			queryString.append(" WHERE dqr_pharmacy.next_encounter_id=0 AND ");
			queryString
			        .append("  DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill ) DAY) BETWEEN ? AND ? ");
			queryString.append("  group by dqr_pharmacy.patient_id ");
			queryString.append(" )pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			queryString.append("  WHERE TIMESTAMPDIFF(YEAR,dqr_meta.dob, ?) < 15 ");
			queryString.append("  ");
			
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
	
	public List<Map<String,String>> getAllPtsWithMissedAppointment(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "SELECT dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, dqr_meta.art_start_date, pharmacy.pickupdate, pharmacy.days_refill, patient_identifier.identifier   FROM dqr_meta ");
                        queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" SELECT dqr_pharmacy.patient_id,  dqr_pharmacy.pickupdate,  dqr_pharmacy.days_refill FROM dqr_pharmacy ");
			queryString.append(" WHERE dqr_pharmacy.next_encounter_id=0 AND ");
			queryString
			        .append("  DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill ) DAY) BETWEEN ? AND ? ");
			queryString.append("  AND DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill + 7) DAY) < ? ");
			queryString.append("  group by dqr_pharmacy.patient_id ");
			queryString.append(" )pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			queryString.append("  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();


                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), endDate);
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
                        tempPatient.put("lastpickup", rs.getString("pickupdate"));
                        tempPatient.put("refill", rs.getString("days_refill"));
                        tempPatient.put("artStartDate", rs.getString("art_start_date"));
                        allPatients.add(tempPatient);
                    }
                    return allPatients;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return null;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public List<Map<String,String>> getAllPedPtsWithMissedAppointment(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, dqr_meta.art_start_date, pharmacy.pickupdate, pharmacy.days_refill, patient_identifier.identifier   FROM dqr_meta ");
                        queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" SELECT dqr_pharmacy.patient_id, dqr_pharmacy.pickupdate,  dqr_pharmacy.days_refill FROM dqr_pharmacy ");
			queryString.append(" WHERE dqr_pharmacy.next_encounter_id=0 AND ");
			queryString
			        .append("  DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill ) DAY) BETWEEN ? AND ? ");
			queryString.append("  AND DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill + 7) DAY) < ? ");
			queryString.append("  group by dqr_pharmacy.patient_id ");
			queryString.append(" )pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			queryString.append("  WHERE TIMESTAMPDIFF(YEAR,dqr_meta.dob, ?) < 15  ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			rs = stmt.executeQuery();


                    while(rs.next())
                    {
                       
                        String age = Misc.getAge(rs.getString("dob"), endDate);
                        Map<String, String> tempPatient = new HashMap<>();
                        String ageRange = Misc.getAgeRange(age);
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
                        tempPatient.put("lastpickup", rs.getString("pickupdate"));
                        tempPatient.put("refill", rs.getString("days_refill"));
                        tempPatient.put("artStartDate", rs.getString("art_start_date"));
                        allPatients.add(tempPatient);
                    }
                    return allPatients;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return null;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getTotalPtsWithLastPickupQuantity(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id "
			                + "	 WHERE  "
			                + " dqr_pharmacy.quantity > 0 "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND (lastpickup.pickupdate BETWEEN ? AND ?) "
			                + "	  )  ");
			
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
	
	public List<Map<String,String>>  getAllPtsWithoutLastPickupQuantity(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
			        +" JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " 
                                +" JOIN encounter ON encounter.encounter_id=dqr_pharmacy.encounter_id " 
                                +"	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                                    "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " +
                                        "	 WHERE "
			                + " (dqr_pharmacy.quantity <= 0 OR dqr_pharmacy.quantity IS NULL) "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND (lastpickup.pickupdate BETWEEN ? AND ?) "
			                + "	 )  GROUP BY dqr_meta.patient_id ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			while (rs.next()) {

                            int encounterId = rs.getInt("encounter_id");
                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            Map<String, String> tempData = new HashMap<>();
                            tempData.put("patientId", patientId);
                            tempData.put("patientIdentifier", patientIdentifier);
                            tempData.put("firstName", firstName);
                            tempData.put("lastName", lastName);
                            if(encounterId == 0)
                            {
                                tempData.put("link", "/coreapps/clinicianfacing/patient.page?patientId="+patientId);
                            }
                            else{
                                tempData.put("link", "/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId="+patientId+"&encounterId="+encounterId+"");
                            }

                            allPatients.add(tempData);
                        }
                    return allPatients;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return null;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getTotalPtsWithLastPickupDuration(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count  FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id "
			                + "	 WHERE  "
			                + " dqr_pharmacy.days_refill > 0 "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND (lastpickup.pickupdate BETWEEN ? AND ?) "
			                + "	  )  ");
			
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
	
	public List<Map<String,String>> getAllPtsWithoutLastPickupDuration(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                                +" JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " +
                                    "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                                    "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                                  +"	JOIN encounter ON encounter.encounter_id=dqr_pharmacy.encounter_id " 
                                        +"	 WHERE  "
			                + " (dqr_pharmacy.days_refill < 0 OR dqr_pharmacy.days_refill IS NULL  ) "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
			                + "	 AND (lastpickup.pickupdate BETWEEN ? AND ? )  ) GROUP BY dqr_meta.patient_id ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while (rs.next()) {

                            int encounterId = rs.getInt("encounter_id");
                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            Map<String, String> tempData = new HashMap<>();
                            tempData.put("patientId", patientId);
                            tempData.put("patientIdentifier", patientIdentifier);
                            tempData.put("firstName", firstName);
                            tempData.put("lastName", lastName);
                            if(encounterId == 0)
                            {
                                tempData.put("link", "/coreapps/clinicianfacing/patient.page?patientId="+patientId);
                            }
                            else{
                                tempData.put("link", "/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId="+patientId+"&encounterId="+encounterId+"");
                            }

                            allPatients.add(tempData);
                        }
                        return allPatients;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return new ArrayList<>();
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getTotalPtsWithLastPickupRegimen(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id "
			                + "	 WHERE  "
			                + " dqr_pharmacy.regimen > 0 "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND (lastpickup.pickupdate BETWEEN ? AND ?) "
			                + "	  )  ");
			
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
	
	public List<Map<String,String>>  getAllPtsWithLastPickupNoRegimen(String startDate, String endDate) {
		
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String, String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(
                            " SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                                    +" JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " +
                                    "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                                    "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                                  +"	JOIN encounter ON encounter.encounter_id=dqr_pharmacy.encounter_id " 
                                    
                                    + "	 WHERE  "
                                    + " (dqr_pharmacy.regimen < 0 OR dqr_pharmacy.regimen IS NULL) "
                                    + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
                                    + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
                                    + "	 AND (lastpickup.pickupdate BETWEEN ? AND ?) )  ");

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();
                    while (rs.next()) {

                            int encounterId = rs.getInt("encounter_id");
                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            Map<String, String> tempData = new HashMap<>();
                            tempData.put("patientId", patientId);
                            tempData.put("patientIdentifier", patientIdentifier);
                            tempData.put("firstName", firstName);
                            tempData.put("lastName", lastName);
                            if(encounterId == 0)
                            {
                                tempData.put("link", "/coreapps/clinicianfacing/patient.page?patientId="+patientId);
                            }
                            else{
                                tempData.put("link", "/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId="+patientId+"&encounterId="+encounterId+"");
                            }

                            allPatients.add(tempData);
                        }
                        return allPatients;
            }
            catch (SQLException ex) {
                    Database.handleException(ex);
                    return new ArrayList<>();
            }
            finally {
                    Database.cleanUp(rs, stmt, con);
            }
	}
	
	public int getTotalPtsWithLastPickupQtyLessThan180(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id "
			                + "	 WHERE  "
			                + " dqr_pharmacy.days_refill <= 180 "
			                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND lastpickup.pickupdate BETWEEN ? AND ? )  ");
			
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
	
	public List<Map<String,String>> getAllPtsWithLastPickupQtyMoreThan180(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
			        +"	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                                "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " 
                                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " 
                                +"	JOIN encounter ON encounter.encounter_id=dqr_pharmacy.encounter_id " 
                                + "	 WHERE  "
                                + " (dqr_pharmacy.days_refill > 180 OR dqr_pharmacy.days_refill IS NULL)"
                                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
                                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND  lastpickup.pickupdate BETWEEN ? AND ? )   group by dqr_meta.patient_id ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			while (rs.next()) {

                            int encounterId = rs.getInt("encounter_id");
                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            Map<String, String> tempData = new HashMap<>();
                            tempData.put("patientId", patientId);
                            tempData.put("patientIdentifier", patientIdentifier);
                            tempData.put("firstName", firstName);
                            tempData.put("lastName", lastName);
                            if(encounterId == 0)
                            {
                                tempData.put("link", "/coreapps/clinicianfacing/patient.page?patientId="+patientId);
                            }
                            else{
                                tempData.put("link", "/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId="+patientId+"&encounterId="+encounterId+"");
                            }

                            allPatients.add(tempData);
                        }
                        return allPatients;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return new ArrayList<>();
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getTotalPtsWithLastPick(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE  "
			                + "  dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id  AND lastpickup.pickupdate BETWEEN ? AND ?"
			                + "	 )  ");
			
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
}
