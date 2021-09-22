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
import java.util.List;
import org.openmrs.module.dataquality.OTZPatient;

/**
 * @author lordmaul
 */
public class OTZDao {
	
	public List<OTZPatient> getTotalAYPLHIVEnrolledInOTZ(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "SELECT patient_program.date_enrolled, patient_identifier.identifier,  dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta \n" +
                                " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                " JOIN person ON person.person_id=dqr_meta.patient_id\n" +
                                " JOIN person_name ON person_name.person_id=dqr_meta.patient_id\n" +
                                " JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.date_enrolled BETWEEN ? AND ? "+
                                " where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
                                " AND patient_program.date_enrolled BETWEEN ? AND ? " + ") GROUP BY dqr_meta.patient_id ");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithScheduledPickup6MonthsBefore(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT dqr_meta.patient_id, patient_identifier.identifier, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, obs.value_datetime AS nextappdate, patient_program.date_enrolled,  TIMESTAMPDIFF(MONTH, obs.value_datetime, patient_program.date_enrolled ),   person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta \n" +
                        "	 JOIN person ON person.person_id=dqr_meta.patient_id " +
                        "	 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                        "     JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                        "      JOIN encounter carecard ON carecard.patient_id=dqr_meta.patient_id AND carecard.form_id=14 AND  " +
                        "     carecard.encounter_datetime=(SELECT encounter.encounter_datetime FROM encounter " +
                        "      JOIN obs ON obs.encounter_id=encounter.encounter_id AND obs.concept_id=5096\n" +
                        "      WHERE encounter.form_id=14 AND carecard.patient_id=encounter.patient_id AND encounter.voided=0 AND TIMESTAMPDIFF(MONTH, obs.value_datetime,  patient_program.date_enrolled ) <= 6   ORDER BY encounter_datetime DESC LIMIT 0,1 " +
                        "     ) " +
                        "     JOIN obs ON obs.encounter_id=carecard.encounter_id AND obs.concept_id=5096 " +
                        "	 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 " +
                        "	 AND patient_program.date_enrolled BETWEEN  ? AND ? ) GROUP BY dqr_meta.patient_id ");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id,  YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  obs.value_datetime AS nextappdate,\n" +
                        "    patient_program.date_enrolled, TIMESTAMPDIFF(MONTH, obs.value_datetime, patient_program.date_enrolled), " +
                        "    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                        "        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
                        "        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND  patient_program.date_enrolled BETWEEN ? AND ?" +
                        "        AND patient_program.program_id = 5 " +
                        "        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                        "        JOIN encounter carecard ON carecard.patient_id = dqr_meta.patient_id\n" +
                        "        AND carecard.form_id = 14\n" +
                        "        AND carecard.encounter_datetime = (SELECT encounter.encounter_datetime\n" +
                        "           FROM encounter JOIN " +
                        "            obs ON obs.encounter_id = encounter.encounter_id\n" +
                        "                AND obs.concept_id = 5096\n" +
                        "        WHERE\n" +
                        "            encounter.form_id = 14\n" +
                        "                AND carecard.patient_id = encounter.patient_id\n" +
                        "                AND encounter.voided = 0\n" +
                        "                AND TIMESTAMPDIFF(MONTH,\n" +
                        "                obs.value_datetime,\n" +
                        "                patient_program.date_enrolled) <= 6\n" +
                        "        ORDER BY encounter_datetime DESC\n" +
                        "        LIMIT 0 , 1)\n" +
                        " JOIN obs ON obs.encounter_id = carecard.encounter_id AND obs.concept_id = 5096\n" +
                        "        JOIN dqr_pharmacy ON dqr_pharmacy.patient_id = carecard.patient_id\n" +
                        "        AND (TIMESTAMPDIFF(DAY, dqr_pharmacy.pickupdate, obs.value_datetime) <= 7\n" +
                        "        OR TIMESTAMPDIFF(DAY, obs.value_datetime, dqr_pharmacy.pickupdate) <= 7)\n" +
                        " WHERE dqr_meta.patient_id IN (SELECT \n" +
                        "            patient_id FROM patient_program\n" +
                        "        WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?)\n" +
                        "       GROUP BY dqr_meta.patient_id ");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithGoodAdhScore6MonthsBefore(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT \n" +
                                    "    dqr_meta.patient_id, patient_identifier.identifier, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, obs.value_coded,\n" +
                                    "    TIMESTAMPDIFF(MONTH,carecard.encounter_datetime,patient_program.date_enrolled),person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                    "FROM dqr_meta\n" +
                                    "        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                    "        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                    "        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                                    "        JOIN encounter carecard ON carecard.patient_id = dqr_meta.patient_id\n" +
                                    "        AND carecard.form_id IN (14, 69)\n" +
                                    "        AND carecard.encounter_datetime =\n" +
                                    "        (SELECT encounter.encounter_datetime  FROM encounter\n" +
                                    "              WHERE encounter.form_id IN (14, 69) AND carecard.patient_id = encounter.patient_id AND encounter.voided = 0 AND\n" +
                                    "              TIMESTAMPDIFF(MONTH,encounter.encounter_datetime,patient_program.date_enrolled) <=6\n" +
                                    "			ORDER BY encounter_datetime DESC LIMIT 0 , 1)\n" +
                                    "        JOIN obs ON obs.encounter_id = carecard.encounter_id AND obs.concept_id = 165290 AND obs.value_coded=165287\n" +
                                    "        \n" +
                                    "WHERE\n" +
                                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) " +
                                    "GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL12MonthsBefore(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id "+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND" +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL12MonthsBeforeAndBelow200(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                         StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                  " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result <200 AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result >=200 AND baselinelab.vl_result <1000 AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result >=1000  AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL6MonthsBefore(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=6 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True'  AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL6MonthsBeforeAndBelow200(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result <200  AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result >=200 AND baselinelab.vl_result <1000  AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                         StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                  " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result >=1000  AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	private List<OTZPatient> getTotalEnrolledWithVL6To12MonthsBeforeAndBelow1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, baselinelab.vl_result, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
"                                person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
"                            FROM dqr_meta \n" +
"                                    JOIN person ON person.person_id = dqr_meta.patient_id \n" +
"                                    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
"                                    JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"                                    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id AND\n" +
"                                    baselinelab.sample_collection_date =\n" +
"									(SELECT dqr_lab.sample_collection_date  FROM dqr_lab\n" +
"                                          WHERE  baselinelab.patient_id = dqr_lab.patient_id  AND\n" +
"                                          TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date ,patient_program.date_enrolled) >6 AND\n" +
"                                          TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date ,patient_program.date_enrolled) <12 AND dqr_lab.vl_result < 1000\n" +
"									ORDER BY sample_collection_date DESC LIMIT 0, 1)\n" +
"                            WHERE \n" +
"                                dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?)" +
"                            GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	private List<OTZPatient> getTotalEnrolledWithoutBaselineVLART6Months(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
                            "		person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                            "		FROM dqr_meta \n" +
                            "		  JOIN obs artobs ON artobs.concept_id=159599 AND artobs.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                            "				JOIN person ON person.person_id = dqr_meta.patient_id \n" +
                            "				JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                            "				JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                            "		\n" +
                            "		WHERE dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_lab WHERE vl_order='True' AND TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date, patient_program.date_enrolled ) <= 12 ) AND\n" +
                            "			dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) \n" +
                            "		 AND TIMESTAMPDIFF(MONTH,artobs.value_datetime, patient_program.date_enrolled) >=6\n" +
                            "		GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEligibleForMonthZeroVL(String startDate, String endDate, String sixMonthsAgo) {
		List<OTZPatient> patientsWithVL = this.getTotalEnrolledWithVL6To12MonthsBeforeAndBelow1000(startDate, endDate,
		    sixMonthsAgo);
		List<OTZPatient> patientsWithoutVL = this.getTotalEnrolledWithoutBaselineVLART6Months(startDate, endDate,
		    sixMonthsAgo);
		
		List<OTZPatient> allPatients = patientsWithVL;
		allPatients.addAll(patientsWithoutVL);
		
		return allPatients;
	}
	
	public List<OTZPatient> getTotalVLWithSampleCollectedAtEnrollment(String startDate, String endDate, String sixMonthsAgo) 
       {
           PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, obs.value_numeric,\n" +
                                "    TIMESTAMPDIFF(MONTH,labform.encounter_datetime,patient_program.date_enrolled),person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "FROM dqr_meta\n" +
                                "        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
                                "        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                "        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "        JOIN encounter labform ON labform.patient_id = dqr_meta.patient_id\n" +
                                "        AND labform.form_id=21\n" +
                                "        AND labform.encounter_datetime = " +
                                "        (SELECT encounter.encounter_datetime  FROM encounter\n" +
                                "              WHERE encounter.form_id=21 AND labform.patient_id = encounter.patient_id AND encounter.voided = 0 AND\n" +
                                "              TIMESTAMPDIFF(MONTH,encounter.encounter_datetime,patient_program.date_enrolled) =0\n" +
                                "			ORDER BY encounter_datetime DESC LIMIT 0, 1)\n" +
                                "        JOIN obs ON obs.encounter_id = labform.encounter_id AND obs.concept_id = 856 AND obs.value_numeric IS NOT NULL ANd obs.voided=0\n" +
                                " WHERE " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) " +
                                " GROUP BY dqr_meta.patient_id ");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment(String startDate, String endDate,
	        String sixMonthsAgo) {
		List<OTZPatient> eligibleAtMonthZero = this.getTotalEligibleForMonthZeroVL(startDate, endDate, sixMonthsAgo);
		
		List<OTZPatient> totalWithSampleCollectedAtMonthZero = this.getTotalVLWithSampleCollectedAtEnrollment(startDate,
		    endDate, sixMonthsAgo);
		
		List<OTZPatient> allPatients = eligibleAtMonthZero;
		allPatients.retainAll(totalWithSampleCollectedAtMonthZero);
		return allPatients;
		
	}
	
	public List<OTZPatient> getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(String startDate, String endDate, String sixMonthsAgo) 
       {
           PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
"                                  FROM dqr_meta \n" +
"                                        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
"                                        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?" +
"                                        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"                                        JOIN dqr_lab labformbaseline ON labformbaseline.patient_id = dqr_meta.patient_id \n" +
"                                        AND labformbaseline.sample_collection_date =\n" +
"                                        (SELECT dqr_lab.sample_collection_date  FROM dqr_lab\n" +
"                                              WHERE  labformbaseline.patient_id = dqr_lab.patient_id AND\n" +
"                                              TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12\n" +
"                                			ORDER BY sample_collection_date DESC LIMIT 0, 1)\n" +
"                                	 JOIN dqr_lab labformmonthzero ON labformmonthzero.patient_id = dqr_meta.patient_id \n" +
"                                        AND labformmonthzero.sample_collection_date =\n" +
"                                        (SELECT dqr_lab.sample_collection_date  FROM dqr_lab\n" +
"                                              WHERE  labformmonthzero.patient_id = dqr_lab.patient_id  AND\n" +
"                                              TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) =0\n" +
"                                			ORDER BY sample_collection_date DESC LIMIT 0, 1)\n" +
"                                WHERE\n" +
"                                    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) AND labformbaseline.vl_result < 1000 \n" +
"                                    AND labformmonthzero.vl_result < 200\n" +
"                                GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(String startDate, String endDate, String sixMonthsAgo) 
       {
           PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
"                                  FROM dqr_meta \n" +
"                                        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
"                                        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
"                                        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"                                        JOIN dqr_lab labformbaseline ON labformbaseline.patient_id = dqr_meta.patient_id \n" +
                                 
"                                        AND labformbaseline.sample_collection_date =\n" +
"                                        (SELECT dqr_lab.sample_collection_date  FROM dqr_lab\n" +
"                                              WHERE  labformbaseline.patient_id = dqr_lab.patient_id AND\n" +
"                                              TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12\n" +
"                                			ORDER BY sample_collection_date DESC LIMIT 0, 1)\n" +
"                                	 JOIN dqr_lab labformmonthzero ON labformmonthzero.patient_id = dqr_meta.patient_id \n" +
"                                        AND labformmonthzero.sample_collection_date =\n" +
"                                        (SELECT dqr_lab.sample_collection_date  FROM dqr_lab\n" +
"                                              WHERE  labformmonthzero.patient_id = dqr_lab.patient_id  AND\n" +
"                                              TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) =0\n" +
"                                			ORDER BY sample_collection_date DESC LIMIT 0, 1)\n" +
"                                WHERE\n" +
"                                    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) AND labformbaseline.vl_result < 1000 \n" +
"                                    AND labformmonthzero.vl_result > 200\n" +
"                                GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(String startDate, String endDate, String sixMonthsAgo) 
       {
           PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
"                                  FROM dqr_meta \n" +
"                                        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
"                                        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
"                                        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"                                        JOIN dqr_lab labformbaseline ON labformbaseline.patient_id = dqr_meta.patient_id \n" +
"                                        AND labformbaseline.sample_collection_date =\n" +
"                                        (SELECT dqr_lab.sample_collection_date  FROM dqr_lab\n" +
"                                              WHERE  labformbaseline.patient_id = dqr_lab.patient_id AND\n" +
"                                              TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) <=12\n" +
"                                			ORDER BY sample_collection_date DESC LIMIT 0, 1)\n" +
"                                	 JOIN dqr_lab labformmonthzero ON labformmonthzero.patient_id = dqr_meta.patient_id \n" +
"                                        AND labformmonthzero.sample_collection_date =\n" +
"                                        (SELECT dqr_lab.sample_collection_date  FROM dqr_lab\n" +
"                                              WHERE  labformmonthzero.patient_id = dqr_lab.patient_id  AND\n" +
"                                              TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,patient_program.date_enrolled) =0\n" +
"                                			ORDER BY sample_collection_date DESC LIMIT 0, 1)\n" +
"                                WHERE\n" +
"                                    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) AND labformbaseline.vl_result < 1000 \n" +
"                                    AND labformmonthzero.vl_result > 1000\n" +
"                                GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithScheduledPickupAfter(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, obs.value_datetime AS nextappdate, patient_program.date_enrolled,  TIMESTAMPDIFF(MONTH, obs.value_datetime, patient_program.date_enrolled ),   person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta \n" +
                        "	 JOIN person ON person.person_id=dqr_meta.patient_id " +
                        "	 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ?  " +
                        "     JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                        "      JOIN encounter carecard ON carecard.patient_id=dqr_meta.patient_id AND carecard.form_id=14 AND  " +
                        "     carecard.encounter_datetime=(SELECT encounter.encounter_datetime FROM encounter " +
                        "      JOIN obs ON obs.encounter_id=encounter.encounter_id AND obs.concept_id=5096\n" +
                        "      WHERE encounter.form_id=14 AND carecard.patient_id=encounter.patient_id AND encounter.voided=0 AND TIMESTAMPDIFF(MONTH, patient_program.date_enrolled, obs.value_datetime )>0   ORDER BY encounter_datetime DESC LIMIT 0,1 " +
                        "     ) " +
                        "     JOIN obs ON obs.encounter_id=carecard.encounter_id AND obs.concept_id=5096 " +
                        "	 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 " +
                        "	 AND patient_program.date_enrolled BETWEEN  ? AND ? ) GROUP BY dqr_meta.patient_id ");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWhoKeptScheduledPickupAfter(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id,  YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  obs.value_datetime AS nextappdate,\n" +
                        "    patient_program.date_enrolled, TIMESTAMPDIFF(MONTH, obs.value_datetime, patient_program.date_enrolled), " +
                        "    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
                        "        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
                        "        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id  AND patient_program.program_id = 5  AND patient_program.date_enrolled BETWEEN ? AND ?" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                      
                        "        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                        "        JOIN encounter carecard ON carecard.patient_id = dqr_meta.patient_id\n" +
                        "        AND carecard.form_id = 14\n" +
                        "        AND carecard.encounter_datetime = (SELECT encounter.encounter_datetime\n" +
                        "           FROM encounter JOIN " +
                        "            obs ON obs.encounter_id = encounter.encounter_id\n" +
                        "                AND obs.concept_id = 5096\n" +
                        "        WHERE\n" +
                        "            encounter.form_id = 14\n" +
                        "                AND carecard.patient_id = encounter.patient_id\n" +
                        "                AND encounter.voided = 0\n" +
                        "                AND TIMESTAMPDIFF(MONTH, patient_program.date_enrolled, obs.value_datetime) > 0 " +
                        "        ORDER BY encounter_datetime DESC " +
                        "        LIMIT 0 , 1)" +
                        "  JOIN obs ON obs.encounter_id = carecard.encounter_id AND obs.concept_id = 5096\n" +
                        "        JOIN dqr_pharmacy ON dqr_pharmacy.patient_id = carecard.patient_id\n" +
                        "        AND (TIMESTAMPDIFF(DAY, dqr_pharmacy.pickupdate, obs.value_datetime) <= 7\n" +
                        "        OR TIMESTAMPDIFF(DAY, obs.value_datetime, dqr_pharmacy.pickupdate) <= 7)\n" +
                        " WHERE dqr_meta.patient_id IN (SELECT \n" +
                        "            patient_id FROM patient_program\n" +
                        "        WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )\n" +
                        "       GROUP BY dqr_meta.patient_id ");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithGoodAdhScoreAfter(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT \n" +
                                    "   patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, obs.value_coded,\n" +
                                    "    TIMESTAMPDIFF(MONTH,carecard.encounter_datetime,patient_program.date_enrolled),person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                    "FROM dqr_meta\n" +
                                    "        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                    "        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                    "        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                                    "        JOIN encounter carecard ON carecard.patient_id = dqr_meta.patient_id\n" +
                                    "        AND carecard.form_id IN (14, 69)\n" +
                                    "        AND carecard.encounter_datetime =\n" +
                                    "        (SELECT encounter.encounter_datetime  FROM encounter\n" +
                                    "              WHERE encounter.form_id IN (14, 69) AND carecard.patient_id = encounter.patient_id AND encounter.voided = 0 AND\n" +
                                    "              TIMESTAMPDIFF(MONTH,encounter.encounter_datetime,patient_program.date_enrolled) <=6\n" +
                                    "			ORDER BY encounter_datetime DESC LIMIT 0 , 1)\n" +
                                    "        JOIN obs ON obs.encounter_id = carecard.encounter_id AND obs.concept_id = 165290 AND obs.value_coded=165287\n" +
                                    "        \n" +
                                    "WHERE\n" +
                                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) " +
                                    "GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledEligibleForVL(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
                                                            "	person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                                            "                            FROM dqr_meta \n" +
                                                            "	JOIN obs artobs ON artobs.concept_id=159599 AND artobs.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                                            "	JOIN person ON person.person_id = dqr_meta.patient_id \n" +
                                                            "	JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                                            "	JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                                                            "   WHERE \n" +
                                                            "   dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_lab WHERE TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date, patient_program.date_enrolled)<= 6)\n" +
                                                            "   AND dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) \n" +
                                                            "   GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledEligibleForVLWithSampleTaken(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
                                                            "	person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                                            "                            FROM dqr_meta \n" +
                                                            "	JOIN obs artobs ON artobs.concept_id=159599 AND artobs.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                                            "	JOIN person ON person.person_id = dqr_meta.patient_id \n" +
                                                            "	JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                                " JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.sample_collection_date>patient_program.date_enrolled AND dqr_lab.vl_order='True' " +            
                                                "	JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                                                            "   WHERE \n" +
                                                            "   dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_lab WHERE TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date, patient_program.date_enrolled)<= 6)\n" +
                                                            "   AND dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) \n" +
                                                            "   GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledEligibleForVLWithSampleTakenAndResult(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
                                                            "	person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                                            "                            FROM dqr_meta \n" +
                                                            "	JOIN obs artobs ON artobs.concept_id=159599 AND artobs.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                                            "	JOIN person ON person.person_id = dqr_meta.patient_id \n" +
                                                            "	JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                                " JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.sample_collection_date>patient_program.date_enrolled AND dqr_lab.vl_order='True' AND dqr_lab.vl_result IS NOT NULL " +            
                                                "	JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                                                            "   WHERE \n" +
                                                            "   dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_lab WHERE TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date, patient_program.date_enrolled)<= 6)\n" +
                                                            "   AND dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) \n" +
                                                            "   GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledEligibleForVLWithSampleTakenAndResultBelow200(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
                                                            "	person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                                            "                            FROM dqr_meta \n" +
                                                            "	JOIN obs artobs ON artobs.concept_id=159599 AND artobs.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                                            "	JOIN person ON person.person_id = dqr_meta.patient_id \n" +
                                                            "	JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                                " JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.sample_collection_date>patient_program.date_enrolled AND dqr_lab.vl_order='True' AND dqr_lab.vl_result<200 " +            
                                                "	JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                                                            "   WHERE \n" +
                                                            "   dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_lab WHERE TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date, patient_program.date_enrolled)<= 6)\n" +
                                                            "   AND dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) \n" +
                                                            "   GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove200Below1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
                                                            "	person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                                            "                            FROM dqr_meta \n" +
                                                            "	JOIN obs artobs ON artobs.concept_id=159599 AND artobs.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                                            "	JOIN person ON person.person_id = dqr_meta.patient_id \n" +
                                                            "	JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                                " JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.sample_collection_date>patient_program.date_enrolled AND dqr_lab.vl_order='True' AND dqr_lab.vl_result>200 AND dqr_lab.vl_result<1000 " +            
                                                "	JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                                                            "   WHERE \n" +
                                                            "   dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_lab WHERE TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date, patient_program.date_enrolled)<= 6)\n" +
                                                            "   AND dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) \n" +
                                                            "   GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier,dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled,\n" +
                                                            "	person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                                            "                            FROM dqr_meta \n" +
                                                            "	JOIN obs artobs ON artobs.concept_id=159599 AND artobs.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                                            "	JOIN person ON person.person_id = dqr_meta.patient_id \n" +
                                                            "	JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                                " JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id AND dqr_lab.sample_collection_date>patient_program.date_enrolled AND dqr_lab.vl_order='True' AND dqr_lab.vl_result>=1000 " +            
                                                "	JOIN person_name ON person_name.person_id = dqr_meta.patient_id \n" +
                                                            "   WHERE \n" +
                                                            "   dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_lab WHERE TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date, patient_program.date_enrolled)<= 6)\n" +
                                                            "   AND dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) \n" +
                                                            "   GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(0);
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12Months(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?  " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND" +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultBelow200(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result < 200 AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultAbove200Below1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 200 AND baselinelab.vl_result < 1000 AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultAbove1000(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 1000 AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultAbove1000CompletedEAC(String startDate, String endDate, String sixMonthsAgo) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                               
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id " +
                                " JOIN obs ON obs.concept_id=166097 AND obs.value_coded=165645 AND obs.obs_datetime >=baselinelab.sample_collection_date "+
                                "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                                "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                                "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                                "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                                "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                                "	WHERE " +
                                "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 1000 AND " +
                                
                                
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getFloat("vl_result"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl(String startDate, String endDate, String sixMonthsAgo) {
		
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Connection con = null;
     List<OTZPatient> allPatients = new ArrayList<>();
    try {
            con = Database.connectionPool.getConnection();
            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            /*StringBuilder queryString = new StringBuilder(
                    "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
                            + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
                            + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
            queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
            StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                    "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                    "	FROM dqr_meta" +
                    "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                     " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                    "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                    "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                    "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                    "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                    "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                    "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                    "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                    "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                    " JOIN dqr_lab ON dqr_lab.vl_order='True' AND dqr_lab.patient_id=dqr_meta.patient_id AND TIMESTAMPDIFF(MONTH, dqr_lab.sample_collection_date, baselinelab.sample_collection_date) BETWEEN 0 AND 6"+
                    " JOIN obs ON obs.concept_id=164980 AND obs.encounter_id=dqr_lab.encounter_id AND obs.value_coded=162081 "+
                    "	WHERE " +
                    "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 1000 AND " +
                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                    "    GROUP BY dqr_meta.patient_id");
            //DateTime now = new DateTime(new Date());
            int i = 1;
            //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            stmt = con.prepareStatement(queryString.toString());
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            //stmt.setString(i++, sixMonthsAgo);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                OTZPatient tempPatient = new OTZPatient();
                tempPatient.setPatientId(rs.getInt("patient_id"));
                tempPatient.setAge(rs.getInt("age"));
                tempPatient.setPepfarId(rs.getString("identifier"));
                tempPatient.setDob(rs.getString("birthdate"));
                tempPatient.setGender(rs.getString("gender"));
                tempPatient.setGivenName(rs.getString("given_name"));
                tempPatient.setFamilyName(rs.getString("family_name"));
                tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                tempPatient.setViralLoad(rs.getFloat("vl_result"));
                allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200(String startDate, String endDate, String sixMonthsAgo) {
		
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Connection con = null;
     List<OTZPatient> allPatients = new ArrayList<>();
    try {
            con = Database.connectionPool.getConnection();
            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            /*StringBuilder queryString = new StringBuilder(
                    "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
                            + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
                            + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
            queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
            StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                    "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                    "	FROM dqr_meta" +
                    "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                     " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                    "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                    "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                    "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                    "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                    "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                    "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                    "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                    "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                    " JOIN dqr_lab ON dqr_lab.vl_order='True' AND dqr_lab.patient_id=dqr_meta.patient_id AND TIMESTAMPDIFF(MONTH, dqr_lab.sample_collection_date, baselinelab.sample_collection_date) BETWEEN 0 AND 6 "+
                    " JOIN obs ON obs.concept_id=164980 AND obs.encounter_id=dqr_lab.encounter_id AND obs.value_coded=162081 "+
                    "	WHERE " +
                    "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 1000 AND dqr_lab.vl_result <200 AND dqr_lab.vl_order='True' AND " +
                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                    "    GROUP BY dqr_meta.patient_id");
            //DateTime now = new DateTime(new Date());
            int i = 1;
            //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            stmt = con.prepareStatement(queryString.toString());
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            //stmt.setString(i++, sixMonthsAgo);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                OTZPatient tempPatient = new OTZPatient();
                tempPatient.setPatientId(rs.getInt("patient_id"));
                tempPatient.setAge(rs.getInt("age"));
                tempPatient.setDob(rs.getString("birthdate"));
                tempPatient.setGender(rs.getString("gender"));
                tempPatient.setPepfarId(rs.getString("identifier"));
                tempPatient.setGivenName(rs.getString("given_name"));
                tempPatient.setFamilyName(rs.getString("family_name"));
                tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                tempPatient.setViralLoad(rs.getFloat("vl_result"));
                allPatients.add(tempPatient);
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
    }	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000(String startDate, String endDate, String sixMonthsAgo) {
		
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Connection con = null;
     List<OTZPatient> allPatients = new ArrayList<>();
    try {
            con = Database.connectionPool.getConnection();
            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            /*StringBuilder queryString = new StringBuilder(
                    "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
                            + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
                            + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
            queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
            StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                    "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                    "	FROM dqr_meta" +
                    "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                     " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                    "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                    "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                    "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                    "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                    "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                    "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                    "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                    "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                    " JOIN dqr_lab ON dqr_lab.vl_order='True' AND dqr_lab.patient_id=dqr_meta.patient_id AND TIMESTAMPDIFF(MONTH, dqr_lab.sample_collection_date, baselinelab.sample_collection_date) BETWEEN 0 AND 6 "+
                    "   JOIN obs ON obs.concept_id=164980 AND obs.encounter_id=dqr_lab.encounter_id "+
                    "	WHERE " +
                    "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 1000 AND dqr_lab.vl_result >=200 AND dqr_lab.vl_result < 1000 AND obs.value_coded=162081 AND " +
                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                    "    GROUP BY dqr_meta.patient_id");
            //DateTime now = new DateTime(new Date());
            int i = 1;
            //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            stmt = con.prepareStatement(queryString.toString());
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            //stmt.setString(i++, sixMonthsAgo);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                OTZPatient tempPatient = new OTZPatient();
                tempPatient.setPatientId(rs.getInt("patient_id"));
                tempPatient.setAge(rs.getInt("age"));
                tempPatient.setPepfarId(rs.getString("identifier"));
                tempPatient.setDob(rs.getString("birthdate"));
                tempPatient.setGender(rs.getString("gender"));
                tempPatient.setGivenName(rs.getString("given_name"));
                tempPatient.setFamilyName(rs.getString("family_name"));
                tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                tempPatient.setViralLoad(rs.getFloat("vl_result"));
                allPatients.add(tempPatient);
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
    }	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove1000(String startDate, String endDate, String sixMonthsAgo) {
		
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Connection con = null;
     List<OTZPatient> allPatients = new ArrayList<>();
    try {
            con = Database.connectionPool.getConnection();
            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            /*StringBuilder queryString = new StringBuilder(
                    "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
                            + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
                            + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
            queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
            StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                    "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                    "	FROM dqr_meta" +
                    "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                     " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                    "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                    "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                    "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id" +
                    "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                    "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND" +
                    "                                    TIMESTAMPDIFF(MONTH,dqr_lab.sample_collection_date,?) <=12 AND" +
                    "                                    TIMESTAMPDIFF(DAY,dqr_lab.sample_collection_date,patient_program.date_enrolled) >=1" +
                    "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                    " JOIN dqr_lab ON dqr_lab.vl_order='True' AND dqr_lab.patient_id=dqr_meta.patient_id AND TIMESTAMPDIFF(MONTH, dqr_lab.sample_collection_date, baselinelab.sample_collection_date) BETWEEN 0 AND 6 "+
                    "   JOIN obs ON obs.concept_id=164980 AND obs.encounter_id=dqr_lab.encounter_id "+
                    "	WHERE " +
                    "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 1000 AND dqr_lab.vl_result >=1000 AND obs.value_coded=162081 AND " +
                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                    "    GROUP BY dqr_meta.patient_id");
            //DateTime now = new DateTime(new Date());
            int i = 1;
            //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            stmt = con.prepareStatement(queryString.toString());
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            //stmt.setString(i++, sixMonthsAgo);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                OTZPatient tempPatient = new OTZPatient();
                tempPatient.setPatientId(rs.getInt("patient_id"));
                tempPatient.setAge(rs.getInt("age"));
                tempPatient.setPepfarId(rs.getString("identifier"));
                tempPatient.setDob(rs.getString("birthdate"));
                tempPatient.setGender(rs.getString("gender"));
                tempPatient.setGivenName(rs.getString("given_name"));
                tempPatient.setFamilyName(rs.getString("family_name"));
                tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                tempPatient.setViralLoad(rs.getFloat("vl_result"));
                allPatients.add(tempPatient);
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
    }	public List<OTZPatient> getTotalEnrolledWithSwitchTo2ndLine(String startDate, String endDate, String sixMonthsAgo) {
		
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Connection con = null;
     List<OTZPatient> allPatients = new ArrayList<>();
    try {
            con = Database.connectionPool.getConnection();
            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            /*StringBuilder queryString = new StringBuilder(
                    "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
                            + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
                            + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
            queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
            StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, patient_program.date_enrolled,  dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
"                                 JOIN person ON person.person_id=dqr_meta.patient_id\n" +
"                                 JOIN person_name ON person_name.person_id=dqr_meta.patient_id\n" +
                     " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"                                 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.date_enrolled BETWEEN ? AND ?" +
"                                 JOIN dqr_pharmacy pharmacybefore ON pharmacybefore.patient_id=dqr_meta.patient_id AND\n" +
"                                 pharmacybefore.pickupdate=(\n" +
"									 SELECT dqr_pharmacy.pickupdate FROM dqr_pharmacy\n" +
"										  WHERE dqr_pharmacy.patient_id=pharmacybefore.patient_id AND TIMESTAMPDIFF(DAY, dqr_pharmacy.pickupdate,  patient_program.date_enrolled ) > 1   ORDER BY pickupdate DESC LIMIT 0,1\n" +
"									)\n" +
"                                    \n" +
"							JOIN dqr_pharmacy pharmacyafter ON pharmacyafter.patient_id=dqr_meta.patient_id AND\n" +
"                                 pharmacyafter.pickupdate=(\n" +
"									 SELECT dqr_pharmacy.pickupdate FROM dqr_pharmacy\n" +
"										  WHERE dqr_pharmacy.patient_id=pharmacyafter.patient_id AND TIMESTAMPDIFF(DAY, dqr_pharmacy.pickupdate,  patient_program.date_enrolled ) < 1   ORDER BY pickupdate DESC LIMIT 0,1\n" +
"									)\n" +
"                                 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
"                                 AND (pharmacybefore.regimen_line='Adult 1st line ARV regimen' OR pharmacybefore.regimen_line='Child 1st line ARV regimen')\n" +
"                                 AND (pharmacyafter.regimen_line='Adult 2nd line ARV regimen' OR pharmacyafter.regimen_line='Child 2nd line ARV regimen')\n" +
"                                 AND patient_program.date_enrolled BETWEEN ? AND ?) GROUP BY dqr_meta.patient_id");
            //DateTime now = new DateTime(new Date());
            int i = 1;
            //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            stmt = con.prepareStatement(queryString.toString());
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            //stmt.setString(i++, sixMonthsAgo);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                OTZPatient tempPatient = new OTZPatient();
                tempPatient.setPatientId(rs.getInt("patient_id"));
                tempPatient.setAge(rs.getInt("age"));
                tempPatient.setDob(rs.getString("birthdate"));
                tempPatient.setPepfarId(rs.getString("identifier"));
                tempPatient.setGender(rs.getString("gender"));
                tempPatient.setGivenName(rs.getString("given_name"));
                tempPatient.setFamilyName(rs.getString("family_name"));
                tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                tempPatient.setViralLoad(rs.getFloat("vl_result"));
                allPatients.add(tempPatient);
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
    }	public List<OTZPatient> getTotalEnrolledWithSwitchTo3rdLine(String startDate, String endDate, String sixMonthsAgo) {
		
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Connection con = null;
     List<OTZPatient> allPatients = new ArrayList<>();
    try {
            con = Database.connectionPool.getConnection();
            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            /*StringBuilder queryString = new StringBuilder(
                    "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
                            + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
                            + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
            queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
            StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, patient_program.date_enrolled,  dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
"                                 JOIN person ON person.person_id=dqr_meta.patient_id\n" +
"                                 JOIN person_name ON person_name.person_id=dqr_meta.patient_id\n" +
                     " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"                                 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.date_enrolled BETWEEN ? AND ?" +
"                                 JOIN dqr_pharmacy pharmacybefore ON pharmacybefore.patient_id=dqr_meta.patient_id AND\n" +
"                                 pharmacybefore.pickupdate=(\n" +
"									 SELECT dqr_pharmacy.pickupdate FROM dqr_pharmacy\n" +
"										  WHERE dqr_pharmacy.patient_id=pharmacybefore.patient_id AND TIMESTAMPDIFF(DAY, dqr_pharmacy.pickupdate,  patient_program.date_enrolled ) > 1   ORDER BY pickupdate DESC LIMIT 0,1\n" +
"									)\n" +
"                                    \n" +
"							JOIN dqr_pharmacy pharmacyafter ON pharmacyafter.patient_id=dqr_meta.patient_id AND\n" +
"                                 pharmacyafter.pickupdate=(\n" +
"									 SELECT dqr_pharmacy.pickupdate FROM dqr_pharmacy\n" +
"										  WHERE dqr_pharmacy.patient_id=pharmacyafter.patient_id AND TIMESTAMPDIFF(DAY, dqr_pharmacy.pickupdate,  patient_program.date_enrolled ) < 1   ORDER BY pickupdate DESC LIMIT 0,1\n" +
"									)\n" +
"                                 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
"                                 AND (pharmacybefore.regimen_line!='Adult 3rd Line ARV Regimens' OR pharmacybefore.regimen_line!='Child 3rd Line ARV Regimens')\n" +
"                                 AND (pharmacyafter.regimen_line='Adult 3rd Line ARV Regimens' OR pharmacyafter.regimen_line='Child 3rd Line ARV Regimens')\n" +
"                                 AND patient_program.date_enrolled BETWEEN ? AND ?) GROUP BY dqr_meta.patient_id");
            //DateTime now = new DateTime(new Date());
            int i = 1;
            //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            stmt = con.prepareStatement(queryString.toString());
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            //stmt.setString(i++, sixMonthsAgo);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                OTZPatient tempPatient = new OTZPatient();
                tempPatient.setPatientId(rs.getInt("patient_id"));
                tempPatient.setPepfarId(rs.getString("identifier"));
                tempPatient.setAge(rs.getInt("age"));
                tempPatient.setDob(rs.getString("birthdate"));
                tempPatient.setGender(rs.getString("gender"));
                tempPatient.setGivenName(rs.getString("given_name"));
                tempPatient.setFamilyName(rs.getString("family_name"));
                tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                tempPatient.setViralLoad(rs.getFloat("vl_result"));
                allPatients.add(tempPatient);
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
    }	public List<OTZPatient> getTotalAYPLHIVEnrolledInOTZWhoComplete7(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        "SELECT patient_identifier.identifier, patient_program.date_enrolled,  dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta \n" +
                                " JOIN person ON person.person_id=dqr_meta.patient_id\n" +
                                         " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                " JOIN person_name ON person_name.person_id=dqr_meta.patient_id\n" +
                                " JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? "+
                                " where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
                                " AND patient_program.date_enrolled BETWEEN ? AND ? " + ") GROUP BY dqr_meta.patient_id ");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledAndTransferredOutAfter(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, patient_program.date_enrolled,    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
"	JOIN person ON person.person_id=dqr_meta.patient_id \n" +
"		 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
"		 JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"		  JOIN obs trackingobs oN trackingobs.concept_id=165470 AND trackingobs.person_id=dqr_meta.patient_id AND \n" +
"          trackingobs.value_coded=159492 AND trackingobs.obs_datetime > patient_program.date_enrolled\n" +
"		 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
"		 AND patient_program.date_enrolled BETWEEN ? AND ?) GROUP BY dqr_meta.patient_id");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledAndLTFUAfter(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, lastpickup.pickupdate,  dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, patient_program.date_enrolled,    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
                        "	JOIN person ON person.person_id=dqr_meta.patient_id \n" +
                        "		 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                        "		 JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                        "		 JOIN dqr_pharmacy lastpickup ON lastpickup.patient_id=dqr_meta.patient_id AND \n" +
                        "         lastpickup.pickupdate=(SELECT dqr_pharmacy.pickupdate FROM dqr_pharmacy \n" +
                        "				WHERE dqr_pharmacy.patient_id=lastpickup.patient_id   ORDER BY pickupdate DESC LIMIT 0,1)\n" +
                        "		 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
                        "		 AND patient_program.date_enrolled BETWEEN  ? AND ? )\n" +
                        "         AND DATE_ADD(lastpickup.pickupdate,  INTERVAL (lastpickup.days_refill + 28) DAY) <? " +
                        "         GROUP BY dqr_meta.patient_id");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                         stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledAndDiedAfter(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, patient_program.date_enrolled,    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
"	JOIN person ON person.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"		 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
"		 JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
"		  JOIN obs trackingobs oN trackingobs.concept_id=165470 AND trackingobs.person_id=dqr_meta.patient_id AND \n" +
"          trackingobs.value_coded=165889 AND trackingobs.obs_datetime > patient_program.date_enrolled\n" +
"		 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
"		 AND patient_program.date_enrolled BETWEEN ? AND ?) GROUP BY dqr_meta.patient_id");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
                        stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledAndOptedOutAfter(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, patient_program.date_enrolled,    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
"	JOIN person ON person.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"		 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
"		 JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
"		  JOIN obs outcomeobs oN outcomeobs.concept_id=166275 AND outcomeobs.person_id=dqr_meta.patient_id AND \n" +
"          outcomeobs.value_coded=166351 AND outcomeobs.obs_datetime >= patient_program.date_enrolled\n" +
"		 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
"		 AND patient_program.date_enrolled BETWEEN ? AND ?) GROUP BY dqr_meta.patient_id");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledAndTransitionedAfter(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, patient_program.date_enrolled,    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
                "	JOIN person ON person.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                "		 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                "		 JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
                "		  JOIN obs transitionedobs oN transitionedobs.concept_id=166272 AND transitionedobs.person_id=dqr_meta.patient_id AND \n" +
                "          transitionedobs.value_coded=1065 AND transitionedobs.obs_datetime >= patient_program.date_enrolled\n" +
                "		 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
                "		 AND patient_program.date_enrolled BETWEEN ? AND ?) GROUP BY dqr_meta.patient_id");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledAndExitedAfter(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, patient_program.date_enrolled,    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta\n" +
"	JOIN person ON person.person_id=dqr_meta.patient_id \n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
"		 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
"		 JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
"		  JOIN obs outcomeobs oN outcomeobs.concept_id=166275 AND outcomeobs.person_id=dqr_meta.patient_id AND \n" +
"          outcomeobs.value_coded=166274 AND outcomeobs.obs_datetime >= patient_program.date_enrolled\n" +
"		 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 \n" +
"		 AND patient_program.date_enrolled BETWEEN ? AND ?) GROUP BY dqr_meta.patient_id");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
                        stmt.setString(i++, endDate);
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalPtsEnrolledAndEligibleForVL(String startDate, String endDate, int month) {//month could be month 6 month 12 or month 18
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			
                        StringBuilder queryString = new StringBuilder(
			        " select dqr_meta.patient_id, dqr_meta.gender, dqr_meta.dob, patient_identifier.identifier, YEAR(CURDATE()) - YEAR(dqr_meta.dob) AS age,  dqr_meta.art_start_date, patient_program.date_enrolled, dqr_lab.sample_collection_date, dqr_lab.vl_result  FROM dqr_meta " +
                                "      JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                                        + "JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                        " LEFT JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id " +
                                        "  AND dqr_lab.sample_collection_date=(SELECT sample_collection_date FROM dqr_lab lastlab WHERE lastlab.patient_id=dqr_lab.patient_id " +
                                        " AND DATE_SUB(sample_collection_date, INTERVAL 30 DAY) <= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH)  ORDER BY sample_collection_date DESC LIMIT 0,1) " +
                                        " LEFT JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id  WHERE   " +
                                        " DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) MONTH) >= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH)  " +
                                        " AND dqr_pharmacy.pickupdate= ( 	SELECT pickupdate FROM dqr_pharmacy lastpickup " +
                                        " WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND pickupdate <= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH) "
                                        + "ORDER BY pickupdate LIMIT 0,1   ) " +
                                        "  " +
                                        " AND dqr_meta.art_start_date <= DATE_SUB(patient_program.date_enrolled,  INTERVAL ? MONTH)  AND patient_program.date_enrolled BETWEEN ? AND ? ");
                        
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
                        stmt.setString(i++, startDate);//
                        stmt.setString(i++, endDate);//
			stmt.setInt(i++, month);
			stmt.setInt(i++, month);
			stmt.setInt(i++, month);
                        stmt.setInt(i++, month + 6);
			stmt.setString(i++, startDate);//
                        stmt.setString(i++, endDate);//
                        
			
			rs = stmt.executeQuery();
			
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            
                            OTZPatient tempPatient = new OTZPatient();
                            
                            tempPatient.setDob(rs.getString("dob"));
                            tempPatient.setArtStartDate(rs.getString("art_start_date"));
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setSampleCollectionDate(rs.getString("sample_collection_date"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            //tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setViralLoad(rs.getInt("vl_result"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalPtsEnrolledWithVLResult(String startDate, String endDate, int month) {//month could be month 6 month 12 or month 18
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			
                        StringBuilder queryString = new StringBuilder(
			        " select dqr_meta.patient_id, dqr_meta.dob,  dqr_meta.gender, patient_identifier.identifier, YEAR(CURDATE()) - YEAR(dqr_meta.dob) AS age,  dqr_meta.art_start_date, patient_program.date_enrolled, dqr_lab.sample_collection_date, dqr_lab.vl_result  FROM dqr_meta " +
                                "      JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                                        + "JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                        " LEFT JOIN dqr_lab ON dqr_lab.patient_id=dqr_meta.patient_id " +
                                        "  AND dqr_lab.sample_collection_date=(SELECT sample_collection_date FROM dqr_lab lastlab WHERE lastlab.patient_id=dqr_lab.patient_id " +
                                        " AND sample_collection_date <= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH)  ORDER BY sample_collection_date DESC LIMIT 0,1) " +
                                        " LEFT JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id  WHERE   " +
                                        " DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) MONTH) >= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH)  " +
                                        " AND dqr_pharmacy.pickupdate= ( 	SELECT pickupdate FROM dqr_pharmacy lastpickup " +
                                        " WHERE lastpickup.patient_id=dqr_pharmacy.patient_id AND pickupdate <= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH) ORDER BY pickupdate LIMIT 0,1	 ) " +
                                        " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 ) " +
                                        "   AND patient_program.date_enrolled BETWEEN ? AND ? ");
                        
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
                        stmt.setString(i++, startDate);//
                        stmt.setString(i++, endDate);//
			stmt.setInt(i++, month);
			stmt.setInt(i++, month);
			stmt.setInt(i++, month);
			stmt.setString(i++, startDate);//
                        stmt.setString(i++, endDate);//
			
			rs = stmt.executeQuery();
			
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setDob(rs.getString("dob"));
                            tempPatient.setArtStartDate(rs.getString("art_start_date"));
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setSampleCollectionDate(rs.getString("sample_collection_date"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setGender(rs.getString("gender"));
                            //tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setViralLoad(rs.getInt("vl_result"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledAndCompletedEACPast12Months(String startDate, String endDate, int month) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT dqr_meta.art_start_date, patient_identifier.identifier, dqr_meta.dob,  baselinelab.vl_result, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, " +
                                "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                "	FROM dqr_meta" +
                                "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                               
                                "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                                " JOIN obs ON obs.concept_id=166097 AND obs.value_coded=165645 AND obs.obs_datetime >= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH) AND obs.person_id=dqr_meta.patient_id  "+
                                " JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id AND baselinelab.sample_collection_date >obs.obs_datetime "+
                                " AND  baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab "+
                    		"   WHERE baselinelab.patient_id = dqr_lab.patient_id  AND " + 
                                "  dqr_lab.sample_collection_date <= DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH) "+
                                  " AND dqr_lab.sample_collection_date >obs.obs_datetime "+
                                    " ORDER BY dqr_lab.sample_collection_date DESC LIMIT 0, 1) "+
                                
                                "	WHERE baselinelab.vl_result >=1000 AND " +
                                "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                                "    GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setInt(i++, month-12);//i.e 12 months ago
                        stmt.setInt(i++, month);//i.e month
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setDob(rs.getString("dob"));
                            tempPatient.setArtStartDate(rs.getString("art_start_date"));
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            tempPatient.setViralLoad(rs.getInt("vl_result"));
                            
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithVLPast12MonthsWithRepeatVl(String startDate, String endDate, int month) {
		
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
         List<OTZPatient> allPatients = new ArrayList<>();
        try {
            con = Database.connectionPool.getConnection();
            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

            //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            /*StringBuilder queryString = new StringBuilder(
                    "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
                            + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
                            + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
            queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
            StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.dob, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, baselinelab.vl_result," +
                    "    person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                    "	FROM dqr_meta" +
                    "    JOIN person ON person.person_id = dqr_meta.patient_id " +
                     " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                    "    JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                    "    JOIN person_name ON person_name.person_id = dqr_meta.patient_id"+
                    "    JOIN dqr_lab baselinelab ON baselinelab.patient_id=dqr_meta.patient_id " +
                    "    AND baselinelab.sample_collection_date =(SELECT dqr_lab.sample_collection_date  FROM dqr_lab" +
                    "									WHERE baselinelab.patient_id = dqr_lab.patient_id  AND " +
                    "                                    dqr_lab.sample_collection_date >=DATE_ADD(patient_program.date_enrolled,  INTERVAL ? MONTH)  " +
                    "                                    ORDER BY sample_collection_date DESC LIMIT 0, 1)" +
                   " JOIN dqr_lab ON dqr_lab.vl_order='True' AND dqr_lab.patient_id=dqr_meta.patient_id AND TIMESTAMPDIFF(MONTH, dqr_lab.sample_collection_date, baselinelab.sample_collection_date) BETWEEN 0 AND 6 "+
                    " JOIN obs ON obs.concept_id=164980 AND obs.encounter_id=dqr_lab.encounter_id "+
                    "	WHERE " +
                    "    baselinelab.vl_order='True' AND baselinelab.vl_result >= 1000 AND obs.value_coded=162081 AND " +
                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? )" +
                    "    GROUP BY dqr_meta.patient_id");
            //DateTime now = new DateTime(new Date());
            int i = 1;
            //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            stmt = con.prepareStatement(queryString.toString());
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            stmt.setInt(i++, month-12);
            stmt.setString(i++, startDate);
            stmt.setString(i++, endDate);
            //stmt.setString(i++, sixMonthsAgo);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                OTZPatient tempPatient = new OTZPatient();
                tempPatient.setDob(rs.getString("dob"));
                tempPatient.setArtStartDate(rs.getString("art_start_date"));
                tempPatient.setPatientId(rs.getInt("patient_id"));
                tempPatient.setAge(rs.getInt("age"));
                tempPatient.setPepfarId(rs.getString("identifier"));
                tempPatient.setDob(rs.getString("birthdate"));
                tempPatient.setGender(rs.getString("gender"));
                tempPatient.setGivenName(rs.getString("given_name"));
                tempPatient.setFamilyName(rs.getString("family_name"));
                tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                tempPatient.setViralLoad(rs.getFloat("vl_result"));
                allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithScheduledPickupMonthN(String startDate, String endDate, int month) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.dob, dqr_meta.art_start_date, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age, obs.value_datetime AS nextappdate, patient_program.date_enrolled,  TIMESTAMPDIFF(MONTH, obs.value_datetime, patient_program.date_enrolled ),   person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta \n" +
                        "	 JOIN person ON person.person_id=dqr_meta.patient_id " +
                        "	 JOIN patient_program ON patient_program.patient_id=dqr_meta.patient_id AND patient_program.program_id=5 AND patient_program.date_enrolled BETWEEN ? AND ?  " +
                        "     JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                        "      JOIN encounter carecard ON carecard.patient_id=dqr_meta.patient_id AND carecard.form_id=14 AND  " +
                        "     carecard.encounter_datetime=(SELECT encounter.encounter_datetime FROM encounter " +
                        "      JOIN obs ON obs.encounter_id=encounter.encounter_id AND obs.concept_id=5096\n" +
                        "      WHERE encounter.form_id=14 AND carecard.patient_id=encounter.patient_id AND encounter.voided=0 AND TIMESTAMPDIFF(MONTH, patient_program.date_enrolled, obs.value_datetime )= ?   ORDER BY encounter_datetime DESC LIMIT 0,1 " +
                        "     ) " +
                        "     JOIN obs ON obs.encounter_id=carecard.encounter_id AND obs.concept_id=5096 " +
                        "	 where dqr_meta.patient_id IN (SELECT patient_id FROM patient_program where program_id=5 " +
                        "	 AND patient_program.date_enrolled BETWEEN  ? AND ? ) GROUP BY dqr_meta.patient_id ");
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setInt(i++, month);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setDob(rs.getString("dob"));
                            tempPatient.setArtStartDate(rs.getString("art_start_date"));
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWhoKeptScheduledPickupMonthN(String startDate, String endDate, int month) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT patient_identifier.identifier, dqr_meta.dob, dqr_meta.patient_id,  YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  obs.value_datetime AS nextappdate,\n" +
                        "    patient_program.date_enrolled, TIMESTAMPDIFF(MONTH, obs.value_datetime, patient_program.date_enrolled), " +
                        "    person.gender, person.birthdate, person_name.given_name, person_name.family_name FROM dqr_meta " +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                        "        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
                        "        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND  patient_program.date_enrolled BETWEEN ? AND ?" +
                        "        AND patient_program.program_id = 5 " +
                        "        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                        "        JOIN encounter carecard ON carecard.patient_id = dqr_meta.patient_id\n" +
                        "        AND carecard.form_id = 14\n" +
                        "        AND carecard.encounter_datetime = (SELECT encounter.encounter_datetime\n" +
                        "           FROM encounter JOIN " +
                        "            obs ON obs.encounter_id = encounter.encounter_id\n" +
                        "                AND obs.concept_id = 5096\n" +
                        "        WHERE\n" +
                        "            encounter.form_id = 14\n" +
                        "                AND carecard.patient_id = encounter.patient_id\n" +
                        "                AND encounter.voided = 0\n" +
                        "                AND TIMESTAMPDIFF(MONTH,\n" +
                        "                obs.value_datetime,\n" +
                        "                patient_program.date_enrolled) = ? " +
                        "        ORDER BY encounter_datetime DESC\n" +
                        "        LIMIT 0 , 1)\n" +
                        " JOIN obs ON obs.encounter_id = carecard.encounter_id AND obs.concept_id = 5096\n" +
                        "        JOIN dqr_pharmacy ON dqr_pharmacy.patient_id = carecard.patient_id\n" +
                        "        AND (TIMESTAMPDIFF(DAY, dqr_pharmacy.pickupdate, obs.value_datetime) <= 7\n" +
                        "        OR TIMESTAMPDIFF(DAY, obs.value_datetime, dqr_pharmacy.pickupdate) <= 2)\n" +
                        " WHERE dqr_meta.patient_id IN (SELECT \n" +
                        "            patient_id FROM patient_program\n" +
                        "        WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?)\n" +
                        "       GROUP BY dqr_meta.patient_id ");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setString(i++, startDate);
                         stmt.setInt(i++, month);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setDob(rs.getString("dob"));
                            tempPatient.setArtStartDate(rs.getString("art_start_date"));
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
	
	public List<OTZPatient> getTotalEnrolledWithGoodAdhScoreMonthN(String startDate, String endDate, int month) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<OTZPatient> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			/*StringBuilder queryString = new StringBuilder(
			        "SELECT COUNT(distinct dqr_meta.patient_id) AS count FROM dqr_meta "
			                + "where patient_id IN (SELECT patient_id FROM patient_program where program_id=5 "
			                + "AND patient_program.date_enrolled BETWEEN ? AND ? " + ")");
			queryString.append(" AND patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE pickupdate >=?)");*/
                        StringBuilder queryString = new StringBuilder("SELECT \n" +
                                    "   patient_identifier.identifier, dqr_meta.art_start_date, dqr_meta.dob, dqr_meta.patient_id, YEAR(CURDATE()) - YEAR(person.birthdate) AS age,  patient_program.date_enrolled, obs.value_coded,\n" +
                                    "    TIMESTAMPDIFF(MONTH,carecard.encounter_datetime,patient_program.date_enrolled),person.gender, person.birthdate, person_name.given_name, person_name.family_name\n" +
                                    "FROM dqr_meta\n" +
                                    "        JOIN person ON person.person_id = dqr_meta.patient_id\n" +
                                 " JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "+
                                    "        JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ? " +
                                    "        JOIN person_name ON person_name.person_id = dqr_meta.patient_id\n" +
                                    "        JOIN encounter carecard ON carecard.patient_id = dqr_meta.patient_id\n" +
                                    "        AND carecard.form_id IN (14, 69)\n" +
                                    "        AND carecard.encounter_datetime =\n" +
                                    "        (SELECT encounter.encounter_datetime  FROM encounter\n" +
                                    "              WHERE encounter.form_id IN (14, 69) AND carecard.patient_id = encounter.patient_id AND encounter.voided = 0 AND\n" +
                                    "              TIMESTAMPDIFF(MONTH,encounter.encounter_datetime,patient_program.date_enrolled) = ? " +
                                    "			ORDER BY encounter_datetime DESC LIMIT 0 , 1)\n" +
                                    "        JOIN obs ON obs.encounter_id = carecard.encounter_id AND obs.concept_id = 165290 AND obs.value_coded=165287\n" +
                                    "        \n" +
                                    "WHERE\n" +
                                    "    dqr_meta.patient_id IN (SELECT patient_id FROM patient_program WHERE program_id = 5 AND patient_program.date_enrolled BETWEEN ? AND ?) " +
                                    "GROUP BY dqr_meta.patient_id");
			//DateTime now = new DateTime(new Date());
                        int i = 1;
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
                        stmt.setInt(i++, month);
                        stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			//stmt.setString(i++, sixMonthsAgo);
			rs = stmt.executeQuery();
			while(rs.next())
                        {
                            OTZPatient tempPatient = new OTZPatient();
                            tempPatient.setDob(rs.getString("dob"));
                            tempPatient.setArtStartDate(rs.getString("art_start_date"));
                            tempPatient.setPatientId(rs.getInt("patient_id"));
                            tempPatient.setPepfarId(rs.getString("identifier"));
                            tempPatient.setAge(rs.getInt("age"));
                            tempPatient.setDob(rs.getString("birthdate"));
                            tempPatient.setGender(rs.getString("gender"));
                            tempPatient.setGivenName(rs.getString("given_name"));
                            tempPatient.setFamilyName(rs.getString("family_name"));
                            tempPatient.setEnrollmentDate(rs.getString("date_enrolled"));
                            allPatients.add(tempPatient);
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
