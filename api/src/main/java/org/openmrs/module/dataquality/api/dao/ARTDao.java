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
import org.joda.time.DateTime;
import org.openmrs.module.dataquality.Constants;
import org.openmrs.module.dataquality.Misc;

/**
 * @author lordmaul
 */
public class ARTDao {
	
	public int getPtsStartedOnArt(String startDate, String endDate, boolean isBeforePeriod) {
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
	
	public List<Map<String,String>> getAllPtsStartedOnArt(String startDate, String endDate, boolean isBeforePeriod) {
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

                    StringBuilder queryString = new StringBuilder("select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, dqr_meta.art_start_date  FROM dqr_meta  ");
                    
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                    queryString.append(" LEFT JOIN ( "
                                    + "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id "
                                    + ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
                    queryString.append(" WHERE art_start_date IS NOT NULL   ");
                    if (isBeforePeriod == false) {
                        queryString.append(" AND ( art_start_date BETWEEN ? AND ? ) AND (pharmacy.first_pickup BETWEEN ? AND ? ) ");
                    } else {
                        queryString.append(" AND ( art_start_date < ? AND pharmacy.first_pickup < ?) GROUP BY dqr_meta.patient_id ");
                    }
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
	
	public int getAdultsStartedOnArt(String startDate, String endDate) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder("select count(DIStinct patient_id ) AS count FROM dqr_meta  ");
			queryString.append(" WHERE art_start_date IS NOT NULL AND ( art_start_date BETWEEN ? AND ? ) ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR,dqr_meta.dob, dqr_meta.art_start_date) >=15 ");
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			/*stmt.setInt(i++, Constants.ARV_REGIMEN_DURATION);
			stmt.setInt(i++, Constants.ARV_GROUPING_CONCEPT);
			stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);*/
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
	
	public List<Map<String,String>> getAllAdultsStartedOnArt(String startDate, String endDate) {
            //indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
            //pretty strange.
            List<Map<String, String>> allPatients = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(" SELECT dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, pharmacy.first_pickup, art_start_date ");
                    queryString.append(" FROM dqr_meta  ");
                    queryString.append( " LEFT JOIN ( " +
                    "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id " +
                    ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                    queryString.append(" WHERE art_start_date IS NOT NULL AND ( art_start_date BETWEEN ? AND ? ) ");
                    queryString.append(" AND TIMESTAMPDIFF(YEAR,dqr_meta.dob, art_start_date) >=15 GROUP BY dqr_meta.patient_id ");

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    /*stmt.setInt(i++, Constants.ARV_REGIMEN_DURATION);
                    stmt.setInt(i++, Constants.ARV_GROUPING_CONCEPT);
                    stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);*/
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();

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
                        tempPatient.put("firstPickup", rs.getString("first_pickup"));
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
	
	public List<Map<String,String>> getAllPedsStartedOnArt(String startDate, String endDate) {
            //indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
            //pretty strange.
            List<Map<String, String>> allPatients = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(" SELECT dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, pharmacy.first_pickup, art_start_date ");
                    queryString.append(" FROM dqr_meta  ");
                    queryString.append( " LEFT JOIN ( " +
                    "	SELECT patient_id, MIN(dqr_pharmacy.pickupdate) AS first_pickup FROM dqr_pharmacy GROUP BY patient_id " +
                    ") pharmacy ON pharmacy.patient_id=dqr_meta.patient_id ");
                    queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                    queryString.append(" WHERE art_start_date IS NOT NULL AND ( art_start_date BETWEEN ? AND ? ) ");
                    queryString.append(" AND TIMESTAMPDIFF(YEAR,dqr_meta.dob, art_start_date) <15  GROUP BY dqr_meta.patient_id ");

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    /*stmt.setInt(i++, Constants.ARV_REGIMEN_DURATION);
                    stmt.setInt(i++, Constants.ARV_GROUPING_CONCEPT);
                    stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);*/
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();

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
                        tempPatient.put("firstPickup", rs.getString("first_pickup"));
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
	
	public int getPedsStartedOnArt(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DINSTINCT dqr_meta.patient_id ) AS count FROM dqr_meta  ");
			queryString.append(" JOIN ( ");
			queryString.append("  SELECT MIN(pickupdate) AS firstpickup, days_refill, patient_id FROM dqr_pharmacy ");
			queryString
			        .append(" GROUP BY dqr_pharmacy.patient_id "
			                + "  )     "
			                + " firstpharmacy ON firstpharmacy.patient_id=dqr_meta.patient_id AND firstpharmacy.days_refill IS NOT NULL ");
			queryString.append(" WHERE art_start_date IS NOT NULL AND ( art_start_date BETWEEN ? AND ? ) ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR,dqr_meta.dob, art_start_date) <15 ");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
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
	
	public int getNoPatientsReceivingARV(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta ");
			queryString.append(" JOIN ( ");
			queryString.append(" SELECT MAX(pickupdate) AS lastpickup, days_refill, patient_id FROM dqr_pharmacy ");
			queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
			queryString.append(" HAVING lastpickup <=? ) ");
			queryString.append(" pharmacy ON pharmacy.patient_id=dqr_meta.patient_id AND days_refill IS NOT NULL ");
			queryString.append(" WHERE art_start_date >=? AND ");
			queryString.append(" DATE_ADD(pharmacy.lastpickup,  INTERVAL (pharmacy.days_refill+28) DAY) > ?  ");
			queryString.append(" AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, endDate);
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
	
	public List<Map<String,String>>  getAllPatientsReceivingARV(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select dqr_meta.patient_id, dqr_meta.dob, dqr_meta.gender, patient_identifier.identifier, pharmacy.lastpickup, pharmacy.days_refill, art_start_date FROM dqr_meta ");
			queryString.append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4");
                        queryString.append(" JOIN ( ");
			queryString.append(" SELECT MAX(pickupdate) AS lastpickup, days_refill, patient_id FROM dqr_pharmacy ");
			queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
			queryString.append(" HAVING lastpickup <=? ) ");
			queryString.append(" pharmacy ON pharmacy.patient_id=dqr_meta.patient_id AND days_refill IS NOT NULL ");
                        queryString.append(" WHERE art_start_date >=? AND ");
			queryString.append(" DATE_ADD(pharmacy.lastpickup,  INTERVAL (pharmacy.days_refill+28) DAY) > ?  ");
			queryString.append(" AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			///queryString.append(" WHERE art_start_date >=? AND ");
			stmt.setString(i++, endDate);
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
                        tempPatient.put("lastPickup", rs.getString("lastpickup"));
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
	
	public int getARVPtsWithVLRequest6Months(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta ");
			queryString.append(" JOIN ( ");
			queryString.append(" SELECT MAX(pickupdate) AS lastpickup, days_refill, patient_id FROM dqr_pharmacy ");
			queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
			queryString.append(" HAVING lastpickup <=? ) ");
			queryString.append(" pharmacy ON pharmacy.patient_id=dqr_meta.patient_id AND days_refill IS NOT NULL ");
			queryString.append("  LEFT JOIN ( "
			        + " SELECT MIN(dqr_lab.sample_collection_date) AS first_sample, patient_id FROM dqr_lab "
			        + "    group BY patient_id " + " ) lab ON lab.patient_id=dqr_meta.patient_id");
			queryString.append(" WHERE art_start_date >=? AND ");
			queryString.append(" DATE_ADD(pharmacy.lastpickup,  INTERVAL (pharmacy.days_refill+28) DAY) > ?  ");
			queryString.append(" AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) ");
			queryString.append(" AND datediff(first_sample, dqr_meta.art_start_date) <=180 ");
			//queryString.append(" group by person.person_id ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
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
	
	public int getARVPtsWithVLRequest7Months(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(DIStinct dqr_meta.patient_id ) AS count FROM dqr_meta ");
			queryString.append(" JOIN ( ");
			queryString.append(" SELECT MAX(pickupdate) AS lastpickup, days_refill, patient_id FROM dqr_pharmacy ");
			queryString.append(" GROUP BY dqr_pharmacy.patient_id ");
			queryString.append(" HAVING lastpickup >=? ) ");
			queryString.append(" pharmacy ON pharmacy.patient_id=dqr_meta.patient_id AND days_refill IS NOT NULL ");
			queryString.append("  LEFT JOIN ( "
			        + " SELECT MIN(dqr_lab.sample_collection_date) AS first_sample, patient_id FROM dqr_lab "
			        + "    group BY patient_id " + " )lab ON lab.patient_id=dqr_meta.patient_id ");
			queryString.append(" WHERE art_start_date >=? AND ");
			queryString.append(" DATE_ADD(pharmacy.lastpickup,  INTERVAL (pharmacy.days_refill+28) DAY) > ?  ");
			queryString.append(" AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) ");
			queryString.append(" AND datediff(first_sample, dqr_meta.art_start_date) <=210 ");
			//queryString.append(" group by person.person_id ");
			
			int i = 1;
			DateTime now = new DateTime();
			String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
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
