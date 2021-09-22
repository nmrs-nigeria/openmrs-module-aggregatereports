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
public class LabDao {
	
	public List<Map<String,String>> getLabEncounters(int patientId)
      {
          List<Map<String,String>> labEncounters = new ArrayList<>();
          
          PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    String query = " SELECT encounter.encounter_id, vlobs.value_coded AS vl_ordered, " +
                                    "vlresultobs.value_numeric AS vlresult, samplecollectionobs.value_datetime AS sample_collection_date, date_receivedobs.value_datetime AS date_received, " +
                                    "date_sent_obs.value_datetime AS datesent " +
                                    " from encounter " +
                                    " LEFT JOIN obs vlobs ON vlobs.encounter_id=encounter.encounter_id AND vlobs.concept_id=165765 AND vlobs.voided=0 " +
                                    " LEFT JOIN obs vlresultobs ON vlresultobs.encounter_id=encounter.encounter_id AND vlresultobs.concept_id=856 AND vlresultobs.voided=0 " +
                                    " LEFT JOIN obs samplecollectionobs ON samplecollectionobs.encounter_id=encounter.encounter_id AND samplecollectionobs.concept_id=159951 AND samplecollectionobs.voided=0 " +
                                    " LEFT JOIN obs date_sent_obs ON date_sent_obs.encounter_id=encounter.encounter_id AND date_sent_obs.concept_id=165988 AND date_sent_obs.voided=0  " +
                                     " LEFT JOIN obs date_receivedobs ON date_receivedobs.encounter_id=encounter.encounter_id AND date_receivedobs.concept_id=165716 AND date_receivedobs.voided=0  " +
                                    " WHERE encounter.encounter_type=11  AND encounter.patient_id=? AND encounter.voided=0 " +
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
                        tempMap.put("encounter_id", rs.getString("encounter_id"));
                        tempMap.put("patient_id", patientId+"");
                        tempMap.put("vl_ordered",Misc.isVLOrdered(rs.getInt("vl_ordered")));
                        tempMap.put("vlresult", rs.getString("vlresult"));
                        tempMap.put("sample_collection_date", rs.getString("sample_collection_date"));
                        tempMap.put("date_received", rs.getString("date_received"));
                        tempMap.put("datesent", rs.getString("datesent"));

                        labEncounters.add(tempMap);
                    }
                    return labEncounters;

            }
            catch (SQLException ex) {
                    Database.handleException(ex);
                    return null;
            }
            finally {
                    Database.cleanUp(rs, stmt, con);
            }
          
          
      }
	
	public int saveLabEncounters(List<Map<String, String>> allLabEncounters) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "INSERT INTO dqr_lab (patient_id, encounter_id, vl_result, date_sample_sent, sample_collection_date, vl_order, date_received_at_lab)VALUES";
			for (int i = 0; i < allLabEncounters.size(); i++) {
				query += "(?, ?, ?, ?, ?, ?, ?),";
			}
			
			query = query.substring(0, query.length() - 1);
			query += " ON DUPLICATE KEY UPDATE vl_result=VALUES(vl_result), date_sample_sent=VALUES(date_sample_sent), sample_collection_date=VALUES(sample_collection_date), ";
			query += " vl_order=VALUES(vl_order), date_received_at_lab=VALUES(date_received_at_lab) ";
			
			int i = 1;
			stmt = con.prepareStatement(query);
			
			for (int j = 0; j < allLabEncounters.size(); j++) {
				
				String dateSent = (allLabEncounters.get(j).get("datesent") != null && !allLabEncounters.get(j)
				        .get("datesent").equalsIgnoreCase("")) ? allLabEncounters.get(j).get("datesent") : null;
				String sampleCollectionDate = (allLabEncounters.get(j).get("sample_collection_date") != null && !allLabEncounters
				        .get(j).get("sample_collection_date").equalsIgnoreCase("")) ? allLabEncounters.get(j).get(
				    "sample_collection_date") : null;
				
				String dateReceived = (allLabEncounters.get(j).get("date_received") != null && !allLabEncounters.get(j)
				        .get("date_received").equalsIgnoreCase("")) ? allLabEncounters.get(j).get("date_received") : null;
				
				stmt.setInt(i++, Integer.parseInt(allLabEncounters.get(j).get("patient_id")));
				stmt.setInt(i++, Integer.parseInt(allLabEncounters.get(j).get("encounter_id")));
				stmt.setString(i++, allLabEncounters.get(j).get("vlresult"));
				stmt.setString(i++, dateSent);
				stmt.setString(i++, sampleCollectionDate);
				stmt.setString(i++, allLabEncounters.get(j).get("vl_ordered"));
				stmt.setString(i++, dateReceived);
				
			}
			//stmt.setFetchSize(200);
			if (allLabEncounters.size() > 0)
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
	
	public List<Map<String,String>>  getAllARVPtsWithVLRequest6Months(String startDate, String endDate, int type) {
		
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String, String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(
                            "select dqr_meta.patient_id, dqr_meta.dob, lab.first_sample, firstpharmacy.firstpickup, dqr_meta.gender, patient_identifier.identifier, pharmacy.lastpickup, pharmacy.days_refill, art_start_date FROM dqr_meta ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 ");
                    queryString.append(" JOIN ( ");
                    queryString.append(" SELECT MAX(pickupdate) AS lastpickup, days_refill, patient_id FROM dqr_pharmacy ");
                    queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
                    queryString.append(" HAVING lastpickup >=? ) ");
                    queryString.append(" pharmacy ON pharmacy.patient_id=dqr_meta.patient_id AND days_refill IS NOT NULL ");
                    queryString.append(" JOIN ( ");
                    queryString.append(" SELECT MIN(pickupdate) AS firstpickup, days_refill, patient_id FROM dqr_pharmacy ");
                    queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
                    queryString.append(" ) ");
                    queryString.append(" firstpharmacy ON firstpharmacy.patient_id=dqr_meta.patient_id AND firstpharmacy.days_refill IS NOT NULL ");
                    
                    
                    queryString.append("  LEFT JOIN ( "
                            + " SELECT MIN(dqr_lab.sample_collection_date) AS first_sample, patient_id FROM dqr_lab "
                            + "    group BY patient_id ) lab ON lab.patient_id=dqr_meta.patient_id");
                    queryString.append(" WHERE art_start_date >=? AND ");
                    queryString.append(" DATE_ADD(pharmacy.lastpickup,  INTERVAL (pharmacy.days_refill+28) DAY) > ?  ");
                    queryString.append(" AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) ");
                    if(type == 1)
                    {
                        queryString.append(" AND datediff(first_sample, dqr_meta.art_start_date) <=180 ");
                    }else{
                        queryString.append(" AND datediff(first_sample, dqr_meta.art_start_date) > 180 ");
                    }
                    
                    //queryString.append(" group by person.person_id ");

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();


                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
                        tempPatient.put("firstpickup", rs.getString("firstpickup"));
                        tempPatient.put("firstSampleDate", rs.getString("first_sample"));
                        tempPatient.put("artStartDate", rs.getString("art_start_date"));
                        tempPatient.put("daysRefill", rs.getString("days_refill"));
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
	
	public List<Map<String,String>>  getAllARVPtsWithVLRequest7Months(String startDate, String endDate, int type) {
		
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String, String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(
                            "select dqr_meta.patient_id, dqr_meta.dob, lab.first_sample, firstpharmacy.firstpickup, dqr_meta.gender, patient_identifier.identifier, pharmacy.lastpickup, pharmacy.days_refill, art_start_date FROM dqr_meta ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 ");
                    queryString.append(" JOIN ( ");
                    queryString.append(" SELECT MAX(pickupdate) AS lastpickup, days_refill, patient_id FROM dqr_pharmacy ");
                    queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
                    queryString.append(" HAVING lastpickup >=? ) ");
                    queryString.append(" pharmacy ON pharmacy.patient_id=dqr_meta.patient_id AND days_refill IS NOT NULL ");
                    
                    queryString.append(" JOIN ( ");
                    queryString.append(" SELECT MIN(pickupdate) AS firstpickup, days_refill, patient_id FROM dqr_pharmacy ");
                    queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
                    queryString.append(" ) ");
                    queryString.append(" firstpharmacy ON firstpharmacy.patient_id=dqr_meta.patient_id AND firstpharmacy.days_refill IS NOT NULL ");
                    
                    
                    queryString.append("  LEFT JOIN ( "
                            + " SELECT MIN(dqr_lab.sample_collection_date) AS first_sample, patient_id FROM dqr_lab "
                            + "    group BY patient_id ) lab ON lab.patient_id=dqr_meta.patient_id");
                    queryString.append(" WHERE art_start_date >=? AND ");
                    queryString.append(" DATE_ADD(pharmacy.lastpickup,  INTERVAL (pharmacy.days_refill+28) DAY) > ?  ");
                    queryString.append(" AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) ");
                     if(type == 1)
                    {
                        queryString.append(" AND datediff(first_sample, dqr_meta.art_start_date) <=210 ");
                    }else{
                        queryString.append(" AND datediff(first_sample, dqr_meta.art_start_date) > 210 ");
                    }
                    //queryString.append(" group by person.person_id ");

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();


                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
                        tempPatient.put("firstpickup", rs.getString("firstpickup"));
                        tempPatient.put("firstSampleDate", rs.getString("first_sample"));
                        tempPatient.put("artStartDate", rs.getString("art_start_date"));
                        tempPatient.put("daysRefill", rs.getString("days_refill"));
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
	
	public int getTotalPtsWithSuppressedFirstVl(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder("select COUNT(dqr_meta.patient_id) AS count FROM dqr_meta ");
			queryString.append(" JOIN ( ");
			
			queryString.append(" SELECT MIN(pickupdate) AS firstpickup, days_refill, patient_id FROM dqr_pharmacy ");
			queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
			queryString.append(" ) ");
			queryString
			        .append(" firstpharmacy ON firstpharmacy.patient_id=dqr_meta.patient_id AND firstpharmacy.days_refill IS NOT NULL ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" SELECT MIN(dqr_lab.sample_collection_date) AS first_vl, vl_result, patient_id FROM dqr_lab ");
			queryString.append("    WHERE vl_result IS NOT NULL AND vl_result < 1000  GROUP BY patient_id ");
			queryString.append(") lab ON lab.patient_id=dqr_meta.patient_id ");
			queryString.append(" WHERE dqr_meta.art_start_date BETWEEN ? AND ? ");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			
			int count = rs.getInt("count");
			return count;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public List<Map<String,String>> getAllPtsWithSuppressedFirstVl(String startDate, String endDate, int type) {
		
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String,String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder("SELECT dqr_meta.patient_id, dqr_meta.dob, lab.first_vl, lab.vl_result, firstpharmacy.firstpickup, dqr_meta.gender, patient_identifier.identifier, art_start_date  FROM dqr_meta ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 ");
                    queryString.append("JOIN ( ");
                    queryString
                            .append(" SELECT MIN(dqr_lab.sample_collection_date) AS first_vl, vl_result, patient_id FROM dqr_lab ");
                    queryString.append("    WHERE vl_result IS NOT NULL ");
                    if(type == 1)
                    {
                        queryString.append(" AND vl_result < 1000  ");
                    }else{
                        queryString.append(" AND vl_result >= 1000  ");
                    }
                    
                    queryString.append( " GROUP BY patient_id ");
                    queryString.append(") lab ON lab.patient_id=dqr_meta.patient_id ");
                    queryString.append(" JOIN ( ");
                    
                    queryString.append(" SELECT MIN(pickupdate) AS firstpickup, days_refill, patient_id FROM dqr_pharmacy ");
                    queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
                    queryString.append(" ) ");
                    queryString.append(" firstpharmacy ON firstpharmacy.patient_id=dqr_meta.patient_id AND firstpharmacy.days_refill IS NOT NULL ");
                    queryString.append(" WHERE dqr_meta.art_start_date BETWEEN ? AND ? GROUP BY dqr_meta.patient_id ");
                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();


                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("artStartDate", rs.getString("art_start_date"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
                        tempPatient.put("firstPickup", rs.getString("firstpickup"));
                        tempPatient.put("firstVLDate", rs.getString("first_vl"));
                        tempPatient.put("vlResult", rs.getString("vl_result"));
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
	
	public List<Map<String,String>> getAllPedPtsWithSuppressedFirstVl(String startDate, String endDate, int type) {
		
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String,String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder("SELECT dqr_meta.patient_id, dqr_meta.dob, lab.first_vl, lab.vl_result, firstpharmacy.firstpickup, dqr_meta.gender, patient_identifier.identifier, art_start_date  FROM dqr_meta ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 ");
                    queryString.append("JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.sample_collection_date= ( \n" +
"			 SELECT MIN(dqr_lab.sample_collection_date) FROM dqr_lab first_lab\n" +
"				where first_lab.patient_id=dqr_lab.patient_id " +
"			)  ");
                    if(type == 1)
                    {
                        queryString.append(" AND (vl_result IS NOT NULL AND vl_result < 1000  )");
                    }else{
                        queryString.append(" AND ( vl_result >= 1000 OR vl_result IS NULL )  ");
                    }
                    queryString.append( " GROUP BY patient_id ");
                    queryString.append(") lab ON lab.patient_id=dqr_meta.patient_id ");
                    queryString.append(" JOIN ( ");
                    
                    queryString.append(" SELECT MIN(pickupdate) AS firstpickup, days_refill, patient_id FROM dqr_pharmacy ");
                    queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
                    queryString.append(" ) ");
                    queryString.append(" firstpharmacy ON firstpharmacy.patient_id=dqr_meta.patient_id AND firstpharmacy.days_refill IS NOT NULL ");
                    queryString.append(" WHERE dqr_meta.art_start_date BETWEEN ? AND ? AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, dqr_meta.art_start_date) < 15   GROUP BY dqr_meta.patient_id ");
                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();


                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("artStartDate", rs.getString("art_start_date"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
                        tempPatient.put("firstPickup", rs.getString("firstpickup"));
                        tempPatient.put("firstVLDate", rs.getString("first_vl"));
                        tempPatient.put("vlResult", rs.getString("vl_result"));
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
	
	public int getTotalPedPtsWithSuppressedFirstVl(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder("select COUNT(dqr_meta.patient_id) AS count FROM dqr_meta ");
			queryString.append(" JOIN ( ");
			
			queryString.append(" SELECT MIN(pickupdate) AS firstpickup, days_refill, patient_id FROM dqr_pharmacy ");
			queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
			queryString.append(" ) ");
			queryString
			        .append(" firstpharmacy ON firstpharmacy.patient_id=dqr_meta.patient_id AND firstpharmacy.days_refill IS NOT NULL ");
			queryString.append("JOIN ( ");
			queryString
			        .append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.sample_collection_date= ( "
			                + "			 SELECT MIN(dqr_lab.sample_collection_date) FROM dqr_lab first_lab "
			                + "				where first_lab.patient_id=dqr_lab.patient_id "
			                + "			)  AND vl_result IS NOT NULL AND vl_result < 1000");
			queryString
			        .append(" WHERE dqr_meta.art_start_date BETWEEN ? AND ? AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, CURDATE()) < 15 ");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
			
			int count = rs.getInt("count");
			return count;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	//get ipt encounters   
	public List<Map<String,String>> getIPTEncounters(int patientId)
     {
         
         
          List<Map<String,String>> iptEncounters = new ArrayList<>();
          
          PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                    con = Database.connectionPool.getConnection();

                    String query = " SELECT encounter.encounter_id, genexpertobs.value_coded AS genexpert, chestxrayobs.value_coded AS chestxray,  " +
                                    " cultureobs.value_coded AS culture, ipteligibleobs.value_coded AS ipteligible, iptstartdateobs.value_datetime AS iptstartdate FROM encounter\n" +
                                    " LEFT JOIN obs genexpertobs ON genexpertobs.encounter_id=encounter.encounter_id AND genexpertobs.concept_id=165975 AND genexpertobs.voided=0 \n" +
                                    " LEFT JOIN obs chestxrayobs ON chestxrayobs.encounter_id=encounter.encounter_id AND chestxrayobs.concept_id=165972 AND chestxrayobs.voided=0  \n" +
                                    "  LEFT JOIN obs cultureobs ON cultureobs.encounter_id=encounter.encounter_id AND cultureobs.concept_id=165969 AND cultureobs.voided=0  \n" +
                                    "   LEFT JOIN obs ipteligibleobs ON ipteligibleobs.encounter_id=encounter.encounter_id AND ipteligibleobs.concept_id=165986 AND ipteligibleobs.voided=0\n" +
                                    "   LEFT JOIN obs iptstartdateobs ON iptstartdateobs.encounter_id=encounter.encounter_id AND iptstartdateobs.concept_id=165986 AND iptstartdateobs.voided=0\n" +
                                    " WHERE encounter.encounter_type=23  AND encounter.patient_id=? AND encounter.voided=0 " +
                                    " GROUP BY encounter.encounter_id   ";
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
                        tempMap.put("encounter_id", rs.getString("encounter_id"));
                        tempMap.put("patient_id", patientId+"");
                        tempMap.put("genexpert",Misc.getGenexpertStatus(rs.getInt("genexpert")));
                        tempMap.put("chestxray", Misc.getChestXrayStatus(rs.getInt("chestxray")));
                        tempMap.put("culture", Misc.getHIVStatus(rs.getInt("culture")));
                        tempMap.put("ipteligible", Misc.getYesNo(rs.getInt("ipteligible")));
                        tempMap.put("iptstartdate", rs.getString("iptstartdate"));
                        iptEncounters.add(tempMap);
                    }
                    return iptEncounters;

            }
            catch (SQLException ex) {
                    Database.handleException(ex);
                    return null;
            }
            finally {
                    Database.cleanUp(rs, stmt, con);
            }
          
          
      }	
	public int saveIPTEncounters(List<Map<String, String>> allIPTEncounters) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			
			String query = "INSERT INTO dqr_ipt (patient_id, encounter_id, genexpert, chestxray, culture, eligible_for_ipt, ipt_start_date)VALUES";
			for (int i = 0; i < allIPTEncounters.size(); i++) {
				query += "(?, ?, ?, ?, ?, ?, ?),";
			}
			
			query = query.substring(0, query.length() - 1);
			query += " ON DUPLICATE KEY UPDATE genexpert=VALUES(genexpert), chestxray=VALUES(chestxray), culture=VALUES(culture), ";
			query += " eligible_for_ipt=VALUES(eligible_for_ipt), ipt_start_date=VALUES(ipt_start_date) ";
			
			int i = 1;
			stmt = con.prepareStatement(query);
			
			for (int j = 0; j < allIPTEncounters.size(); j++) {
				
				System.out.println(allIPTEncounters.get(j).get("genexpert"));
				String iptStartDate = (allIPTEncounters.get(j).get("iptstartdate") != null && !allIPTEncounters.get(j)
				        .get("datesent").equalsIgnoreCase("")) ? allIPTEncounters.get(j).get("iptstartdate") : null;
				
				stmt.setInt(i++, Integer.parseInt(allIPTEncounters.get(j).get("patient_id")));
				stmt.setInt(i++, Integer.parseInt(allIPTEncounters.get(j).get("encounter_id")));
				stmt.setString(i++, allIPTEncounters.get(j).get("genexpert"));
				stmt.setString(i++, allIPTEncounters.get(j).get("chestxray"));
				stmt.setString(i++, allIPTEncounters.get(j).get("culture"));
				stmt.setString(i++, allIPTEncounters.get(j).get("ipteligible"));
				stmt.setString(i++, iptStartDate);
				
			}
			//stmt.setFetchSize(200);
			if (allIPTEncounters.size() > 0) {
				stmt.executeUpdate();
				//System.out.println("executing");
			} else {
				//System.out.println("nothign to execute");
			}
			
			return 1;
			
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int getTotalScreenedPatients(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			queryString
			        .append(" AND dqr_meta.patient_id IN (SELECT dqr_ipt.patient_id FROM dqr_ipt JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id WHERE encounter.encounter_datetime BETWEEN ? AND ?) ");
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
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
	
	public List<Map<String, String>> getAllScreenedPatients(String startDate, String endDate, boolean isBeforePeriod) {
            //indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
            //pretty strange.

            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String, String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(
                            "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                   queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                    queryString
                            .append(" LEFT JOIN ( "
                                    + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
                                    + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
                    queryString.append(" WHERE art_start_date IS NOT NULL   ");
                    if (isBeforePeriod == false) {
                            queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
                    } else {
                            queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
                    }

                    queryString
                            .append(" AND dqr_meta.patient_id IN (SELECT dqr_ipt.patient_id FROM dqr_ipt JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id WHERE encounter.encounter_datetime BETWEEN ? AND ?) ");

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    if (isBeforePeriod == false) {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                    } else {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, startDate);
                    }
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);

                    rs = stmt.executeQuery();

                    rs = stmt.executeQuery();
                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("patientId", rs.getString("patient_id"));
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsPresumptiveTb(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_clinicals.patient_id FROM dqr_clinicals  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id WHERE tb_status='Presumptive TB' GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? "
			                + " )clinicals ON clinicals.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
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
	
	public List<Map<String, String>>  getAllPtsPresumptiveTb(String startDate, String endDate, boolean isBeforePeriod) {
            //indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
            //pretty strange.

            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String, String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(
                            "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                    queryString
                            .append(" LEFT JOIN ( "
                                    + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
                                    + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");

                    queryString
                            .append("  JOIN ("
                                    + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_clinicals.patient_id FROM dqr_clinicals  "
                                    + "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id WHERE tb_status='Presumptive TB' GROUP BY patient_id  "
                                    + "    HAVING max_encounter BETWEEN ? AND ? "
                                    + " )clinicals ON clinicals.patient_id=dqr_meta.patient_id");
                    queryString.append(" WHERE art_start_date IS NOT NULL   ");
                    if (isBeforePeriod == false) {
                            queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
                    } else {
                            queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
                    }

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);

                    if (isBeforePeriod == false) {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                    } else {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, startDate);
                    }

                    rs = stmt.executeQuery();

                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("patientId", rs.getString("patient_id"));
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsConfirmedTb(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_clinicals.patient_id FROM dqr_clinicals  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id WHERE tb_status='Confirmed TB' GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? "
			                + " )clinicals ON clinicals.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
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
	
	public List<Map<String, String>> getAllPtsConfirmedTb(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                        queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_clinicals.patient_id FROM dqr_clinicals  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id WHERE tb_status='Confirmed TB' GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? "
			                + " )clinicals ON clinicals.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                            String ageRange = Misc.getAgeRange(age);
                            Map<String, String> tempPatient = new HashMap<>();
                            tempPatient.put("patientId", rs.getString("patient_id"));
                            tempPatient.put("artNumber", rs.getString("identifier"));
                            tempPatient.put("dob", age);
                            tempPatient.put("ageRange", ageRange);
                            tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsOnTbTreatment(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_clinicals.patient_id FROM dqr_clinicals  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id WHERE tb_status='TB Treatmen' GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? "
			                + " )clinicals ON clinicals.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
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
	
	public List<Map<String, String>>  getAllPtsOnTbTreatment(String startDate, String endDate, boolean isBeforePeriod) {
            //indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
            //pretty strange.

            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String, String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(
                            "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                    queryString
                            .append(" LEFT JOIN ( "
                                    + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
                                    + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");

                    queryString
                            .append("  JOIN ("
                                    + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_clinicals.patient_id FROM dqr_clinicals  "
                                    + "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id WHERE tb_status='TB Treatmen' GROUP BY patient_id  "
                                    + "    HAVING max_encounter BETWEEN ? AND ? "
                                    + " )clinicals ON clinicals.patient_id=dqr_meta.patient_id");
                    queryString.append(" WHERE art_start_date IS NOT NULL   ");
                    if (isBeforePeriod == false) {
                            queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
                    } else {
                            queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
                    }

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);

                    if (isBeforePeriod == false) {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                    } else {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, startDate);
                    }

                    rs = stmt.executeQuery();
                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("patientId", rs.getString("patient_id"));
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsTestedForTb(String startDate, String endDate, boolean isBeforePeriod, String type) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, genexpert, chestxray, culture, dqr_ipt.patient_id FROM dqr_ipt  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id ");
			
			queryString.append(" WHERE 1=1 ");
			queryString.append(" AND " + type + "!='' ");
			
			queryString.append("    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
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
	
	public List<Map<String, String>> getAllPtsTestedForTb(String startDate, String endDate, boolean isBeforePeriod, String type) {
            //indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
            //pretty strange.

            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            List<Map<String, String>> allPatients = new ArrayList<>();
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(
                            "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                    queryString
                            .append(" LEFT JOIN ( "
                                    + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
                                    + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");

                    queryString
                            .append("  JOIN ("
                                    + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, genexpert, chestxray, culture, dqr_ipt.patient_id FROM dqr_ipt  "
                                    + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id ");

                    queryString.append(" WHERE 1=1 ");
                    queryString.append(" AND " + type + "!='' ");

                    queryString.append("    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
                    queryString.append(" WHERE art_start_date IS NOT NULL   ");
                    if (isBeforePeriod == false) {
                            queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
                    } else {
                            queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
                    }

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);

                    if (isBeforePeriod == false) {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, endDate);
                    } else {
                            stmt.setString(i++, startDate);
                            stmt.setString(i++, startDate);
                    }

                    rs = stmt.executeQuery();

                   while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("patientId", rs.getString("patient_id"));
                        tempPatient.put("artNumber", rs.getString("identifier"));
                        tempPatient.put("dob", age);
                        tempPatient.put("ageRange", ageRange);
                        tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsDiagnoseddForTb(String startDate, String endDate, boolean isBeforePeriod, String type,
	        int isPositive) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, genexpert, chestxray, culture, dqr_ipt.patient_id FROM dqr_ipt  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id ");
			
			queryString.append(" WHERE 1=1 ");
			if (isPositive == 1) {
				if (type.equalsIgnoreCase("genexpert")) {
					queryString.append(" AND " + type + "='PTB positive MTB detected' ");
				} else if (type.equalsIgnoreCase("chestxray")) {
					queryString.append(" AND " + type + "='Suggestive' ");
				} else if (type.equalsIgnoreCase("culture")) {
					queryString.append(" AND " + type + "='Positive' ");
				}
			} else {
				queryString.append(" AND " + type + "!='' AND " + type + " IS NOT NULL");
			}
			queryString.append("    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
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
	
	public List<Map<String, String>> getAllPtsDiagnoseddForTb(String startDate, String endDate, boolean isBeforePeriod, String type, int isPositive) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                        queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, genexpert, chestxray, culture, dqr_ipt.patient_id FROM dqr_ipt  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id ");
			
			queryString.append(" WHERE 1=1 ");
                        if(isPositive == 1){
                            if (type.equalsIgnoreCase("genexpert")) {
                                    queryString.append(" AND " + type + "='PTB positive MTB detected' ");
                            } else if (type.equalsIgnoreCase("chestxray")) {
                                    queryString.append(" AND " + type + "='Suggestive' ");
                            } else if (type.equalsIgnoreCase("culture")) {
                                    queryString.append(" AND " + type + "='Positive' ");
                            }
                        } else {
                            queryString.append(" AND " + type + "!='' AND "+type+" IS NOT NULL");
                        }
			
			queryString.append("    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next())
                        {
                            String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                            String ageRange = Misc.getAgeRange(age);
                            Map<String, String> tempPatient = new HashMap<>();
                            tempPatient.put("patientId", rs.getString("patient_id"));
                            tempPatient.put("artNumber", rs.getString("identifier"));
                            tempPatient.put("dob", age);
                            tempPatient.put("ageRange", ageRange);
                            tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsEligibleForIPT(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_ipt.patient_id FROM dqr_ipt  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id WHERE eligible_for_ipt='Yes' GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
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
	
	public List<Map<String,String>> getAllPtsEligibleForIPT(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                        queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_ipt.patient_id FROM dqr_ipt  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id WHERE eligible_for_ipt='Yes' GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next())
                        {
                            String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                            String ageRange = Misc.getAgeRange(age);
                            Map<String, String> tempPatient = new HashMap<>();
                            tempPatient.put("patientId", rs.getString("patient_id"));
                            tempPatient.put("artNumber", rs.getString("identifier"));
                            tempPatient.put("dob", age);
                            tempPatient.put("ageRange", ageRange);
                            tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsStartedOnIPT(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_ipt.patient_id FROM dqr_ipt  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id WHERE ipt_start_date IS NOT NULL GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
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
	
	public List<Map<String, String>> getAllPtsStartedOnIPT(String startDate, String endDate, boolean isBeforePeriod) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date FROM dqr_meta  ");
                        queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
			queryString
			        .append(" LEFT JOIN ( "
			                + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
			                + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
			
			queryString
			        .append("  JOIN ("
			                + "     SELECT MAX(encounter.encounter_datetime) AS max_encounter, dqr_ipt.patient_id FROM dqr_ipt  "
			                + "	JOIN encounter ON encounter.encounter_id=dqr_ipt.encounter_id WHERE ipt_start_date IS NOT NULL GROUP BY patient_id  "
			                + "    HAVING max_encounter BETWEEN ? AND ? " + " )ipt ON ipt.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date IS NOT NULL   ");
			if (isBeforePeriod == false) {
				queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
			} else {
				queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) ");
			}
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			
			if (isBeforePeriod == false) {
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
				stmt.setString(i++, startDate);
				stmt.setString(i++, endDate);
			} else {
				stmt.setString(i++, startDate);
				stmt.setString(i++, startDate);
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next())
                        {
                            String age = Misc.getAge(rs.getString("dob"), rs.getString("art_start_date"));
                            String ageRange = Misc.getAgeRange(age);
                            Map<String, String> tempPatient = new HashMap<>();
                            tempPatient.put("patientId", rs.getString("patient_id"));
                            tempPatient.put("artNumber", rs.getString("identifier"));
                            tempPatient.put("dob", age);
                            tempPatient.put("ageRange", ageRange);
                            tempPatient.put("gender", rs.getString("gender"));
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
	
	public int getTotalPtsEligibleForVL(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			
			StringBuilder queryString = new StringBuilder(
			        "select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " LEFT JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id "
			                + "        AND dqr_lab.sample_collection_date=(SELECT MAX(sample_collection_date) FROM dqr_lab lastlab WHERE lastlab.patient_id=dqr_lab.patient_id "
			                + "          HAVING MAX(sample_collection_date) <=? " + "   )"
			                + "		JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id  WHERE   "
			                + "	DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ? "
			                + "AND dqr_pharmacy.pickupdate= ( " + "	SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "	WHERE lastpickup.patient_id=dqr_pharmacy.patient_id " + "	 HAVING MAX(pickupdate) <=? )  "
			                + "	AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  "
			                + "AND dqr_meta.art_start_date <= ? ");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);//the patients must have been on art at least 6 months before the end date;
			
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
	
	public int getTotalPtsEligibleForVLWithResult(String startDate, String aYearAgo, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			
			StringBuilder queryString = new StringBuilder(
			        "select COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + " LEFT JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id "
			                + "        AND dqr_lab.sample_collection_date=(SELECT MAX(sample_collection_date) FROM dqr_lab lastlab WHERE lastlab.patient_id=dqr_lab.patient_id "
			                + "          HAVING MAX(sample_collection_date) >? " + "   )"
			                + "		JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id  WHERE   "
			                + "	DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ? "
			                + "AND dqr_pharmacy.pickupdate= ( " + "	SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
			                + "	WHERE lastpickup.patient_id=dqr_pharmacy.patient_id " + "	 HAVING MAX(pickupdate) <=? )  "
			                + "	AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  "
			                + "AND dqr_meta.art_start_date <= ?  AND dqr_lab.vl_result IS  NOT NULL");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			System.out.println(aYearAgo + " " + endDate + " " + startDate);
			stmt.setString(i++, aYearAgo);
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);//the patients must have been on art at least 6 months before the end date;
			
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
	
	public List<Map<String,String>> getAllPtsEligibleForVLWithoutResult(String startDate, String oneYearAgo, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			
			StringBuilder queryString = new StringBuilder(
			        "select IFNULL(dqr_lab.encounter_id, 0) AS encounter_id,  dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier  FROM dqr_meta  "
                          
                                +" JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                                        + "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " 
                                + "     LEFT JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id "
                                + "        AND dqr_lab.sample_collection_date=(SELECT MAX(sample_collection_date) FROM dqr_lab lastlab WHERE lastlab.patient_id=dqr_lab.patient_id "
                                + "          HAVING MAX(sample_collection_date) >? " + "   )"//greater than one year ago 
                                + "		JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id  WHERE   "
                                + "	DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ? "
                                + "AND dqr_pharmacy.pickupdate= ( " + "	SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
                                + "	WHERE lastpickup.patient_id=dqr_pharmacy.patient_id " + "	 HAVING MAX(pickupdate) <=? )  "
                                + "	AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  "
                                + " AND dqr_meta.art_start_date <= ?  AND dqr_lab.vl_result IS   NULL GROUP BY dqr_meta.patient_id ");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, oneYearAgo);
                        stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
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
	
	public int getTotalPtsWithVlResult(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			StringBuilder queryString = new StringBuilder(
			        "select count(DISTINCT dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString.append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.vl_result IS NOT NULL ");
			queryString.append(" JOIN encounter ON encounter.encounter_id=dqr_lab.encounter_id ");
			queryString.append(" WHERE   dqr_lab.vl_order='true' AND ");
			queryString.append("  encounter.encounter_datetime= (  SELECT MAX(encounter_datetime) FROM encounter labvl "
			        + " WHERE labvl.patient_id=dqr_lab.patient_id AND labvl.form_id=21 "
			        + "	 HAVING MAX(encounter_datetime) <= ? )");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
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
	
	public int getTotalPtsWithVlResultAndCollectionDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			StringBuilder queryString = new StringBuilder(
			        "select count(DISTINCT dqr_meta.patient_id ) AS count  FROM dqr_meta  ");
			queryString.append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.vl_result IS NOT NULL ");
			queryString.append(" JOIN encounter ON encounter.encounter_id=dqr_lab.encounter_id ");
			queryString.append(" WHERE  dqr_lab.sample_collection_date IS NOT NULL AND dqr_lab.vl_order='true' AND  ");
			queryString.append("  encounter.encounter_datetime= (  SELECT MAX(encounter_datetime) FROM encounter labvl "
			        + " WHERE labvl.patient_id=dqr_lab.patient_id AND labvl.form_id=21 "
			        + "	 HAVING MAX(encounter_datetime) <= ? )");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
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
	
	public List<Map<String,String>>  getAllPtsWithVlResultWithoutCollectionDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			StringBuilder queryString = new StringBuilder(
			 " SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "+
                         " JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                       + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " );
                        
			queryString.append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id  ");
			queryString.append(" JOIN encounter ON encounter.encounter_id=dqr_lab.encounter_id ");
			queryString.append(" WHERE  dqr_lab.sample_collection_date IS  NULL AND dqr_lab.vl_result IS NOT NULL AND dqr_lab.vl_order='true' AND ");
			queryString.append("  encounter.encounter_datetime= (  SELECT MAX(encounter_datetime) FROM encounter labvl "
			        + " WHERE labvl.patient_id=dqr_lab.patient_id AND labvl.form_id=21 "
			        + "	 HAVING MAX(encounter_datetime) <= ? ) GROUP BY dqr_meta.patient_id ");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
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
	
	public int getTotalPtsWithVlResultAndSampleSentDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			StringBuilder queryString = new StringBuilder(
			        "select count(DISTINCT dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString.append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.vl_result IS NOT NULL ");
			queryString.append(" JOIN encounter ON encounter.encounter_id=dqr_lab.encounter_id ");
			queryString.append(" WHERE  dqr_lab.date_sample_sent IS NOT NULL AND dqr_lab.vl_order='true' AND ");
			queryString.append("  encounter.encounter_datetime= (  SELECT MAX(encounter_datetime) FROM encounter labvl "
			        + " WHERE labvl.patient_id=dqr_lab.patient_id AND labvl.form_id=21 "
			        + "	 HAVING MAX(encounter_datetime) <= ? )");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
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
	
	public int getTotalPtsWithVlResultAndSampleReceivedDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			StringBuilder queryString = new StringBuilder(
			        "select count(DISTINCT dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString.append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.vl_result IS NOT NULL ");
			queryString.append(" JOIN encounter ON encounter.encounter_id=dqr_lab.encounter_id ");
			queryString
			        .append(" WHERE  (dqr_lab.date_received_at_lab IS NOT NULL AND dqr_lab.date_received_at_lab !='' ) AND dqr_lab.vl_order='true' ");
			queryString.append("  encounter.encounter_datetime= (  SELECT MAX(encounter_datetime) FROM encounter labvl "
			        + " WHERE labvl.patient_id=dqr_lab.patient_id AND labvl.form_id=21 "
			        + "	 HAVING MAX(encounter_datetime) <= ? )");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
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
	
	public List<Map<String,String>>  getAllPtsWithVlResultAndNoSampleSentDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			StringBuilder queryString = new StringBuilder(
			        "SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "+
                        " JOIN person_name ON person_name.person_id=dqr_meta.patient_id   ");
			queryString.append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.vl_result IS NOT NULL ");
			queryString.append(" JOIN encounter ON encounter.encounter_id=dqr_lab.encounter_id ");
			queryString.append(" WHERE  dqr_lab.date_sample_sent IS NULL AND ");
			queryString.append("  encounter.encounter_datetime= (  SELECT MAX(encounter_datetime) FROM encounter labvl "
			        + " WHERE labvl.patient_id=dqr_lab.patient_id AND labvl.form_id=21 "
			        + "	 HAVING MAX(encounter_datetime) <= ? )");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
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
	
	public List<Map<String,String>>  getAllPtsWithVlResultAndNoSampleReceivedDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			StringBuilder queryString = new StringBuilder(
			        "SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "+
                        " JOIN person_name ON person_name.person_id=dqr_meta.patient_id  "
                      + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 ");
			queryString.append(" JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id ");
			queryString.append(" JOIN encounter ON encounter.encounter_id=dqr_lab.encounter_id ");
			queryString.append(" WHERE  (dqr_lab.date_received_at_lab IS NULL OR dqr_lab.date_received_at_lab='') AND dqr_lab.vl_result IS NOT NULL AND  dqr_lab.vl_order='true' AND ");
			queryString.append("  encounter.encounter_datetime= (  SELECT MAX(encounter_datetime) FROM encounter labvl "
			        + " WHERE labvl.patient_id=dqr_lab.patient_id AND labvl.form_id=21 "
			        + "	 HAVING MAX(encounter_datetime) <= ? ) GROUP BY dqr_meta.patient_id ");
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			rs.next();
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
}
