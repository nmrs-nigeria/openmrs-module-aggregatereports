package org.openmrs.module.dataquality.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import jdk.nashorn.internal.runtime.Context;
import org.openmrs.module.dataquality.api.dao.Database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author lordmaul
 */
public class ClinicalDaoHelper {
	
	public List<Map<String,String>> getActivePtsWithoutWithEducationalStatus(String startDate, String endDate) {
	System.out.println(startDate);
        System.out.println(endDate);	
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Map<String, String>> allPatients = new ArrayList<>();
        try {
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                StringBuilder queryString = new StringBuilder(
                        " select IFNULL(hivE.encounter_id, 0) AS encounter_id, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                        + " LEFT JOIN encounter hivE ON hivE.patient_id=dqr_meta.patient_id AND hivE.form_id=23 "
                        + "  JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                        + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                        + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id "
                        + "	 WHERE   "
                        + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ?  "
                        + "     AND dqr_pharmacy.pickupdate= ( "
                        + "		SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
                        + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
                        + "	 HAVING MAX(pickupdate) <=? )   "
                        + " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  ");

                queryString.append(" AND (dqr_meta.education_status ='' OR dqr_meta.education_status IS NULL) GROUP BY dqr_meta.patient_id ");

                int i = 1;
                stmt = con.prepareStatement(queryString.toString());
                //stmt.setString(i++, startDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                rs = stmt.executeQuery();

                while (rs.next()) {
                   
                    String patientId = rs.getString("patient_id");
                    int encounterId = rs.getInt("encounter_id");
                    String patientIdentifier = rs.getString("identifier");
                    String firstName = rs.getString("given_name");
                    String lastName = rs.getString("family_name");
                    Map<String, String> tempData = new HashMap<>();
                    tempData.put("patientId", patientId);
                    tempData.put("encounterId", encounterId+"");
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
            ex.printStackTrace();
                Database.handleException(ex);
                return null;
        }
        finally {
                Database.cleanUp(rs, stmt, con);
        }
    }
	
	public List<Map<String,String>> getActiveAYPLHIV(String startDate, String endDate) {
	System.out.println(startDate);
        System.out.println(endDate);	
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Map<String, String>> allPatients = new ArrayList<>();
        try {
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                StringBuilder queryString = new StringBuilder(
                        "  select IFNULL(hivE.encounter_id, 0) AS encounter_id, dqr_meta.gender, patient_program.date_enrolled AS otz_enrollment_date, dqr_meta.art_start_date, TIMESTAMPDIFF(YEAR, dqr_meta.dob, patient_program.date_enrolled) AS age, YEAR(?) - YEAR(dqr_meta.dob) AS cage, dqr_meta.patient_id, dqr_meta.dob, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta \n" +
                            "                         LEFT JOIN encounter hivE ON hivE.patient_id=dqr_meta.patient_id AND hivE.form_id=23 \n" +
                            "                          JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
                            "       LEFT  JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 " +
                            "                         LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 \n" +
                            "                         JOIN dqr_pharmacy lastpickup ON lastpickup.patient_id=dqr_meta.patient_id \n" +
                            "                         AND lastpickup.pickupdate=(\n" +
                            "							SELECT pickupdate FROM dqr_pharmacy WHERE lastpickup.patient_id=dqr_pharmacy.patient_id\n" +
                            "                            AND pickupdate <=?\n" +
                            "                            ORDER BY pickupdate DESC LIMIT 0,1\n" +
                            "                         )\n" +
                            "                 WHERE   \n" +
                            "                        	 DATE_ADD(lastpickup.pickupdate,  INTERVAL (lastpickup.days_refill+28) DAY) >= ? \n" +
                            "                            \n" +
                            "                         AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  \n" +
                            "\n" +
                            "      AND (TIMESTAMPDIFF(YEAR,dqr_meta.dob, ?) >= 0 AND TIMESTAMPDIFF(YEAR,dqr_meta.dob,?) <=24)\n" +
                            "     GROUP BY dqr_meta.patient_id  ");

                int i = 1;
                stmt = con.prepareStatement(queryString.toString());
                //stmt.setString(i++, startDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                rs = stmt.executeQuery();

                while (rs.next()) {
                   
                    String patientId = rs.getString("patient_id");
                    int encounterId = rs.getInt("encounter_id");
                    String patientIdentifier = rs.getString("identifier");
                    String firstName = rs.getString("given_name");
                    String gender = rs.getString("gender");
                    int age = rs.getInt("age");
                    int cage = rs.getInt("cage");
                    String lastName = rs.getString("family_name");
                    String artStartDate = rs.getString("art_start_date");
                    String dob = rs.getString("dob");
                    String otzEnrollmentDate = rs.getString("otz_enrollment_date");
                    Map<String, String> tempData = new HashMap<>();
                    tempData.put("patientId", patientId);
                    tempData.put("encounterId", encounterId+"");
                    tempData.put("pepfarId", patientIdentifier);
                    tempData.put("patientIdentifier", patientIdentifier);
                    tempData.put("firstName", firstName);
                    tempData.put("artStartDate", artStartDate);
                    tempData.put("lastName", lastName);
                    tempData.put("gender", gender);
                    tempData.put("enrollmentDate", otzEnrollmentDate);
                    tempData.put("dob", dob);
                    tempData.put("age", age+"");
                    tempData.put("cage", cage+"");
                    allPatients.add(tempData);
                }
                return allPatients;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
                Database.handleException(ex);
                return null;
        }
        finally {
                Database.cleanUp(rs, stmt, con);
        }
    }
	
	public List<Map<String,String>> getActiveAYPLHIV2(String startDate, String endDate) {
	System.out.println(startDate);
        System.out.println(endDate);	
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Map<String, String>> allPatients = new ArrayList<>();
        try {
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                StringBuilder queryString = new StringBuilder(
                        "  select dqr_curr.encounter_id, dqr_curr.gender, dqr_curr.otz_enrollment_date, dqr_curr.art_start_date, TIMESTAMPDIFF(YEAR, dqr_curr.dob, dqr_curr.otz_enrollment_date) AS age, YEAR(?) - YEAR(dqr_curr.dob) AS cage, dqr_curr.patient_id, dqr_curr.dob, dqr_curr.given_name, dqr_curr.family_name, dqr_curr.identifier FROM dqr_curr \n" +
                            "                         LEFT JOIN encounter hivE ON hivE.patient_id=dqr_meta.patient_id AND hivE.form_id=23 \n" +
                            "                          JOIN person_name ON person_name.person_id=dqr_meta.patient_id \n" +
                            "       LEFT  JOIN patient_program ON patient_program.patient_id = dqr_meta.patient_id AND patient_program.program_id = 5 " +
                            "                         LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 \n" +
                            "                         JOIN dqr_pharmacy lastpickup ON lastpickup.patient_id=dqr_meta.patient_id \n" +
                            "                         AND lastpickup.pickupdate=(\n" +
                            "							SELECT pickupdate FROM dqr_pharmacy WHERE lastpickup.patient_id=dqr_pharmacy.patient_id\n" +
                            "                            AND pickupdate <=?\n" +
                            "                            ORDER BY pickupdate DESC LIMIT 0,1\n" +
                            "                         )\n" +
                            "                 WHERE   \n" +
                            "                        	 DATE_ADD(lastpickup.pickupdate,  INTERVAL (lastpickup.days_refill+28) DAY) >= ? \n" +
                            "                            \n" +
                            "                         AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  \n" +
                            "\n" +
                            "      AND (TIMESTAMPDIFF(YEAR,dqr_meta.dob,CURDATE()) >= 10 AND TIMESTAMPDIFF(YEAR,dqr_meta.dob,CURDATE()) <=24)\n" +
                            "     GROUP BY dqr_meta.patient_id  ");

                int i = 1;
                stmt = con.prepareStatement(queryString.toString());
                //stmt.setString(i++, startDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                rs = stmt.executeQuery();

                while (rs.next()) {
                   
                    String patientId = rs.getString("patient_id");
                    int encounterId = rs.getInt("encounter_id");
                    String patientIdentifier = rs.getString("identifier");
                    String firstName = rs.getString("given_name");
                    String gender = rs.getString("gender");
                    int age = rs.getInt("age");
                    int cage = rs.getInt("cage");
                    String lastName = rs.getString("family_name");
                    String artStartDate = rs.getString("art_start_date");
                    String dob = rs.getString("dob");
                    String otzEnrollmentDate = rs.getString("otz_enrollment_date");
                    Map<String, String> tempData = new HashMap<>();
                    tempData.put("patientId", patientId);
                    tempData.put("encounterId", encounterId+"");
                    tempData.put("pepfarId", patientIdentifier);
                    tempData.put("patientIdentifier", patientIdentifier);
                    tempData.put("firstName", firstName);
                    tempData.put("artStartDate", artStartDate);
                    tempData.put("lastName", lastName);
                    tempData.put("gender", gender);
                    tempData.put("enrollmentDate", otzEnrollmentDate);
                    tempData.put("dob", dob);
                    tempData.put("age", age+"");
                    tempData.put("cage", cage+"");
                    allPatients.add(tempData);
                }
                return allPatients;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
                Database.handleException(ex);
                return null;
        }
        finally {
                Database.cleanUp(rs, stmt, con);
        }
    }
	
	public List<Map<String,String>> getActiveAYPLHIV3(String startDate, String endDate) {
	System.out.println(startDate);
        System.out.println(endDate);	
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Map<String, String>> allPatients = new ArrayList<>();
        try {
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                StringBuilder queryString = new StringBuilder(
                        "select encounter_id, gender, otz_enrollment_date, artstartdate AS art_start_date, TIMESTAMPDIFF(YEAR, birthdate, otz_enrollment_date) AS age, YEAR(?) - YEAR(birthdate) AS cage, patient_id, birthdate AS dob, " +
                        "given_name, family_name, identifier FROM dqr_pharmacy2 " +
                        "WHERE DATE_ADD(pickupdate,  INTERVAL (days_refill+28) DAY) >= ?  " +
                        "AND (TIMESTAMPDIFF(YEAR,birthdate,CURDATE()) >= 10 AND TIMESTAMPDIFF(YEAR,birthdate,CURDATE()) <=24) " +
                        "GROUP BY patient_id  "
                        );

                int i = 1;
                stmt = con.prepareStatement(queryString.toString());
                //stmt.setString(i++, startDate);
                //stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                rs = stmt.executeQuery();

                while (rs.next()) {
                   
                    String patientId = rs.getString("patient_id");
                    int encounterId = rs.getInt("encounter_id");
                    String patientIdentifier = rs.getString("identifier");
                    String firstName = rs.getString("given_name");
                    String gender = rs.getString("gender");
                    int age = rs.getInt("age");
                    int cage = rs.getInt("cage");
                    String lastName = rs.getString("family_name");
                    String artStartDate = rs.getString("art_start_date");
                    String dob = rs.getString("dob");
                    String otzEnrollmentDate = rs.getString("otz_enrollment_date");
                    Map<String, String> tempData = new HashMap<>();
                    tempData.put("patientId", patientId);
                    tempData.put("encounterId", encounterId+"");
                    tempData.put("pepfarId", patientIdentifier);
                    tempData.put("patientIdentifier", patientIdentifier);
                    tempData.put("firstName", firstName);
                    tempData.put("artStartDate", artStartDate);
                    tempData.put("lastName", lastName);
                    tempData.put("gender", gender);
                    tempData.put("enrollmentDate", otzEnrollmentDate);
                    tempData.put("dob", dob);
                    tempData.put("age", age+"");
                    tempData.put("cage", cage+"");
                    allPatients.add(tempData);
                }
                return allPatients;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
                Database.handleException(ex);
                return null;
        }
        finally {
                Database.cleanUp(rs, stmt, con);
        }
    }
	
	public List<Map<String,String>> getActivePtsWithoutMaritalStatus(String startDate, String endDate) {
	System.out.println(startDate);
        System.out.println(endDate);	
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Map<String, String>> allPatients = new ArrayList<>();
        try {
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                StringBuilder queryString = new StringBuilder(
                        " select IFNULL(hivE.encounter_id, 0) AS encounter_id, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                        + " LEFT JOIN encounter hivE ON hivE.patient_id=dqr_meta.patient_id AND hivE.form_id=23 "
                        + "  JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                        + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                        + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id "
                        + "	 WHERE   "
                        + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ?  "
                        + "     AND dqr_pharmacy.pickupdate= ( "
                        + "		SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
                        + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
                        + "	 HAVING MAX(pickupdate) <=? )   "
                        + " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  ");

                queryString.append(" AND dqr_meta.marital_status ='' GROUP BY dqr_meta.patient_id ");

                int i = 1;
                stmt = con.prepareStatement(queryString.toString());

                //stmt.setString(i++, startDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                rs = stmt.executeQuery();

                while (rs.next()) {
                   
                    String patientId = rs.getString("patient_id");
                    int encounterId = rs.getInt("encounter_id");
                    String patientIdentifier = rs.getString("identifier");
                    String firstName = rs.getString("given_name");
                    String lastName = rs.getString("family_name");
                    Map<String, String> tempData = new HashMap<>();
                    tempData.put("patientId", patientId);
                    tempData.put("encounterId", encounterId+"");
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
            ex.printStackTrace();
                Database.handleException(ex);
                return null;
        }
        finally {
                Database.cleanUp(rs, stmt, con);
        }
    }
	
	public List<Map<String,String>> getActivePtsWithoutOccupationStatus(String startDate, String endDate) {
	System.out.println(startDate);
        System.out.println(endDate);	
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Map<String, String>> allPatients = new ArrayList<>();
        try {
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                StringBuilder queryString = new StringBuilder(
                        " select IFNULL(hivE.encounter_id, 0) AS encounter_id, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                        + " LEFT JOIN encounter hivE ON hivE.patient_id=dqr_meta.patient_id AND hivE.form_id=23 "
                        + "  JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                        + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                        + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id "
                        + "	 WHERE   "
                        + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) >= ?  "
                        + "     AND dqr_pharmacy.pickupdate= ( "
                        + "		SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
                        + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
                        + "	 HAVING MAX(pickupdate) <=? )   "
                        + " AND (dqr_meta.termination_status IS NULL OR dqr_meta.termination_status!=1066 )  ");
                queryString.append(" AND dqr_meta.occupation ='' GROUP BY dqr_meta.patient_id ");

                int i = 1;
                stmt = con.prepareStatement(queryString.toString());

                //stmt.setString(i++, startDate);
                stmt.setString(i++, endDate);
                stmt.setString(i++, endDate);
                rs = stmt.executeQuery();

                while (rs.next()) {
                   
                    String patientId = rs.getString("patient_id");
                    int encounterId = rs.getInt("encounter_id");
                    String patientIdentifier = rs.getString("identifier");
                    String firstName = rs.getString("given_name");
                    String lastName = rs.getString("family_name");
                    Map<String, String> tempData = new HashMap<>();
                    tempData.put("patientId", patientId);
                    tempData.put("encounterId", encounterId+"");
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
            ex.printStackTrace();
                Database.handleException(ex);
                return null;
        }
        finally {
                Database.cleanUp(rs, stmt, con);
        }
    }
	
	public List<Map<String,String>> getPtsStartedOnARTWithoutDocDob(String startDate, String endDate) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Map<String, String>> allPatients = new ArrayList<>();
        try {
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                StringBuilder queryString = new StringBuilder(
                        " select dqr_meta.patient_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM dqr_meta "
                       + "  JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                       + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                       + "	 WHERE art_start_date >=? AND art_start_date <=? ");
                queryString.append(" AND dqr_meta.dob =''  OR dqr_meta.dob IS  NULL GROUP BY dqr_meta.patient_id  ");

                int i = 1;
                stmt = con.prepareStatement(queryString.toString());

                stmt.setString(i++, startDate);
                stmt.setString(i++, endDate);
                rs = stmt.executeQuery();
                while (rs.next()) {
                   
                    String patientId = rs.getString("patient_id");
                    String patientIdentifier = rs.getString("identifier");
                    String firstName = rs.getString("given_name");
                    String lastName = rs.getString("family_name");
                    Map<String, String> tempData = new HashMap<>();
                    tempData.put("patientId", patientId);
                    tempData.put("patientIdentifier", patientIdentifier);
                    tempData.put("firstName", firstName);
                    tempData.put("lastName", lastName);
                    tempData.put("link", "/registrationapp/editSection.page?patientId="+patientId+"&sectionId=demographics&appId=referenceapplication.registrationapp.registerPatient");
            
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
	
	public List<Map<String,String>> getPtsStartedOnARTWithoutDocGender(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select dqr_meta.patient_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM dqr_meta "
                               + "  JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                               + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.gender =''  OR dqr_meta.gender IS NULL GROUP BY dqr_meta.patient_id ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while (rs.next()) {

                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            Map<String, String> tempData = new HashMap<>();
                            tempData.put("patientId", patientId);
                            tempData.put("patientIdentifier", patientIdentifier);
                            tempData.put("firstName", firstName);
                            tempData.put("lastName", lastName);
                            tempData.put("link", "/registrationapp/editSection.page?patientId="+patientId+"&sectionId=demographics&appId=referenceapplication.registrationapp.registerPatient");

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
	
	public List<Map<String,String>> getPtsStartedOnARTWithoutDocAddress(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			        " select dqr_meta.patient_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM dqr_meta "
                               + "  JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                               + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
			                + "	 WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND dqr_meta.address ='' OR dqr_meta.address IS NULL GROUP BY dqr_meta.patient_id ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while (rs.next()) {

                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            Map<String, String> tempData = new HashMap<>();
                            tempData.put("patientId", patientId);
                            tempData.put("patientIdentifier", patientIdentifier);
                            tempData.put("firstName", firstName);
                            tempData.put("lastName", lastName);
                            tempData.put("link", "/registrationapp/editSection.page?patientId="+patientId+"&sectionId=demographics&appId=referenceapplication.registrationapp.registerPatient");

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
	
	public List<Map<String,String>> getPtsStartedOnARTWithoutDocHIVDiagnosisDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			" SELECT IFNULL(hivE.encounter_id, 0) AS encounter_id, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                        + " LEFT JOIN encounter hivE ON hivE.patient_id=dqr_meta.patient_id AND hivE.form_id=23 "
                        + " JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                        + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
			+ " WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND (dqr_meta.hiv_diagnosis_date =''  OR dqr_meta.hiv_diagnosis_date IS  NULL )  GROUP BY dqr_meta.patient_id");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
                        while (rs.next()) {

                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            int encounterId = rs.getInt("encounter_id");
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
	
	public List<Map<String,String>> getPtsStartedOnARTWithoutDocHIVEnrollmentDate(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			StringBuilder queryString = new StringBuilder(
			" SELECT IFNULL(hivE.encounter_id, 0) AS encounter_id, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                        + " LEFT JOIN encounter hivE ON hivE.patient_id=dqr_meta.patient_id AND hivE.form_id=23 "
                        + " JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                        + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
			+ " WHERE art_start_date >=? AND art_start_date <=? ");
			queryString.append(" AND (dqr_meta.hiv_enrollment_date =''  OR dqr_meta.hiv_enrollment_date IS  NULL )  GROUP BY dqr_meta.patient_id ");
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			
			stmt.setString(i++, startDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
                        while (rs.next()) {

                            String patientId = rs.getString("patient_id");
                            String patientIdentifier = rs.getString("identifier");
                            String firstName = rs.getString("given_name");
                            String lastName = rs.getString("family_name");
                            int encounterId = rs.getInt("encounter_id");
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
	
	public List<Map<String,String>> getPtsStartedOnARTWithDocDrugPickup(String startDate, String endDate) {
		
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder(
                            " select dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                        
                        + " JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                        + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "        
                           + "	 WHERE art_start_date >=? AND art_start_date <=? ");
                    queryString.append(" AND dqr_meta.patient_id NOT IN (SELECT patient_id FROM dqr_pharmacy)  GROUP BY dqr_meta.patient_id ");

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());

                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();
                    List<Map<String, String>> allPatients = new ArrayList<>();
                    while (rs.next()) {

                        String patientId = rs.getString("patient_id");
                        String patientIdentifier = rs.getString("identifier");
                        String firstName = rs.getString("given_name");
                        String lastName = rs.getString("family_name");
                        Map<String, String> tempData = new HashMap<>();
                        tempData.put("patientId", patientId);
                        tempData.put("patientIdentifier", patientIdentifier);
                        tempData.put("firstName", firstName);
                        tempData.put("lastName", lastName);
                       tempData.put("link", "/coreapps/clinicianfacing/patient.page?patientId="+patientId);

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
	
	public List<Map<String,String>> getPtsStartedOnARTWithDocCd4(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                 List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder(
                            " select dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "
                           + " JOIN person_name ON person_name.person_id=dqr_meta.patient_id "
                           + " LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 "
                                    + "	 WHERE (art_start_date BETWEEN ? AND ?)  ");
                    queryString.append(" AND dqr_meta.patient_id NOT IN (SELECT person_id FROM obs WHERE concept_id=5497 AND (value_numeric IS NOT NULL OR value_numeric >0) AND obs.voided=0) GROUP BY dqr_meta.patient_id ");

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());

                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();
                    while (rs.next()) {

                        String patientId = rs.getString("patient_id");
                        String patientIdentifier = rs.getString("identifier");
                        String firstName = rs.getString("given_name");
                        String lastName = rs.getString("family_name");
                        Map<String, String> tempData = new HashMap<>();
                        tempData.put("patientId", patientId);
                        tempData.put("patientIdentifier", patientIdentifier);
                        tempData.put("firstName", firstName);
                        tempData.put("lastName", lastName);
                       tempData.put("link", "/coreapps/clinicianfacing/patient.page?patientId="+patientId);

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
	
	public List<Map<String,String>>  getPtsWithClinicVisitWithoutDocWeight(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder( "  SELECT IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta " +
                            "	LEFT JOIN encounter  ON encounter.patient_id=dqr_meta.patient_id AND encounter.form_id IN (22, 14, 20)" +
                            "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                            "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " +
                            "	WHERE dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals " +
                            "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id " +
                            "	WHERE  (dqr_clinicals.weight IS NULL ) " +
                            "            AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter carecard  " +
                            "			WHERE carecard.patient_id=encounter.patient_id AND carecard.encounter_datetime BETWEEN ? AND ? )" +
                            "		)" +
                            "        AND   encounter.encounter_datetime BETWEEN ? AND  ? " +
                            " GROUP BY dqr_meta.patient_id " );

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
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
	
	public List<Map<String,String>>  getPtsWithClinicVisitWithoutDocMuac(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder( "  select IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta " +
                            "	LEFT JOIN encounter  ON encounter.patient_id=dqr_meta.patient_id AND encounter.form_id IN (22, 14, 20)" +
                            "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                            "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " +
                            "	WHERE dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals " +
                            "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id " +
                            "	WHERE  dqr_clinicals.muac IS NULL OR dqr_clinicals.muac=''  " +
                            "            AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter carecard  " +
                            "			WHERE carecard.patient_id=encounter.patient_id AND carecard.encounter_datetime BETWEEN ? AND ? )" +
                            "		)" +
                            "        AND   encounter.encounter_datetime BETWEEN ? AND  ? AND TIMESTAMPDIFF(YEAR,dqr_meta.dob, ?) <15 " +
                            " GROUP BY dqr_meta.patient_id " );

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
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
	
	public List<Map<String,String>>  getPtsWithClinicVisitWithoutDocStaging(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder( "  select IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta " +
                            "	LEFT JOIN encounter  ON encounter.patient_id=dqr_meta.patient_id AND encounter.form_id IN (22, 14, 20)" +
                            "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                            "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " +
                            "	WHERE dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals " +
                            "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id " +
                            "	WHERE  dqr_clinicals.who_stage IS NULL OR dqr_clinicals.who_stage=''  " +
                            "            AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter carecard  " +
                            "			WHERE carecard.patient_id=encounter.patient_id AND carecard.encounter_datetime BETWEEN ? AND ? )" +
                            "		)" +
                            "        AND   encounter.encounter_datetime BETWEEN ? AND  ?  " +
                            " GROUP BY dqr_meta.patient_id " );

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    //stmt.setString(i++, endDate);
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
	
	public List<Map<String,String>>  getPtsWithClinicVisitWithoutDocTBStatus(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder( "  select IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta " +
                            "	LEFT JOIN encounter  ON encounter.patient_id=dqr_meta.patient_id AND encounter.form_id IN (22, 14, 20)" +
                            "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                            "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " +
                            "	WHERE dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals " +
                            "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id " +
                            "	WHERE  dqr_clinicals.tb_status IS NULL OR dqr_clinicals.tb_status=''  " +
                            "            AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter carecard  " +
                            "			WHERE carecard.patient_id=encounter.patient_id AND carecard.encounter_datetime BETWEEN ? AND ? )" +
                            "		)" +
                            "        AND   encounter.encounter_datetime BETWEEN ? AND  ?  " +
                            " GROUP BY dqr_meta.patient_id " );

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                   // stmt.setString(i++, endDate);
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
	
	public List<Map<String,String>>  getPtsWithClinicVisitWithoutDocFunctionalStatus(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder( "  select IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta " +
                            "	LEFT JOIN encounter  ON encounter.patient_id=dqr_meta.patient_id AND encounter.form_id IN (22, 14, 20)" +
                            "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                            "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " +
                            "	WHERE dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals " +
                            "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id " +
                            "	WHERE  dqr_clinicals.functional_status IS NULL OR dqr_clinicals.functional_status='' " +
                            "            AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter carecard  " +
                            "			WHERE carecard.patient_id=encounter.patient_id AND carecard.encounter_datetime BETWEEN ? AND ? )" +
                            "		)" +
                            "        AND   encounter.encounter_datetime BETWEEN ? AND  ?  " +
                            " GROUP BY dqr_meta.patient_id " );

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    //stmt.setString(i++, endDate);
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
	
	public List<Map<String,String>>  getPtsStartedOnARTWithoutInitialRegimen(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                   StringBuilder queryString = new StringBuilder(
                            " select dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "+
                            "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                            "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " 
                                    + "	 WHERE art_start_date >=? AND art_start_date <=? ");
                    queryString.append(" AND dqr_meta.dob !=''  AND dqr_meta.dob IS NOT NULL "
                            + " AND dqr_meta.patient_id IN (SELECT patient_id FROM dqr_pharmacy WHERE regimen_line IS  NULL)");

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    //stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();
                    while (rs.next()) {

                        int encounterId = 0;//rs.getInt("encounter_id");
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
	
	public List<Map<String,String>>  getPtsWithClinicVisitDocNextAppDate(String startDate, String endDate) {
		
            PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                    StringBuilder queryString = new StringBuilder( "  select IFNULL(encounter.encounter_id, 0) AS encounter_id, encounter.encounter_datetime, dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta " +
                            "	LEFT JOIN encounter  ON encounter.patient_id=dqr_meta.patient_id AND encounter.form_id IN (22, 14, 20)" +
                            "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                            "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " +
                            "	WHERE dqr_meta.patient_id IN (SELECT dqr_clinicals.patient_id FROM dqr_clinicals " +
                            "	JOIN encounter ON encounter.encounter_id=dqr_clinicals.encounter_id " +
                            "	WHERE  (dqr_clinicals.nextapp_date IS NULL OR dqr_clinicals.nextapp_date='') " +
                            "            AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter carecard  " +
                            "			WHERE carecard.patient_id=encounter.patient_id AND carecard.encounter_datetime BETWEEN ? AND ? )" +
                            "		)" +
                            "        AND   encounter.encounter_datetime BETWEEN ? AND  ?  " +
                            " GROUP BY dqr_meta.patient_id " );

                    int i = 1;
                    stmt = con.prepareStatement(queryString.toString());
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    //stmt.setString(i++, endDate);
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
	
	public List<Map<String,String>> getInactiveActivePtsWithDocReason(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
                List<Map<String, String>> allPatients = new ArrayList<>();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " SELECT dqr_meta.patient_id, person_name.given_name, person_name.family_name, patient_identifier.identifier FROM dqr_meta "+
                                "	JOIN person_name ON person_name.person_id=dqr_meta.patient_id " +
                                "	LEFT JOIN patient_identifier ON patient_identifier.patient_id=dqr_meta.patient_id AND patient_identifier.identifier_type=4 " 
                                + " JOIN dqr_pharmacy ON dqr_pharmacy.patient_id=dqr_meta.patient_id " + "	 WHERE  "
                                + "	 DATE_ADD(dqr_pharmacy.pickupdate,  INTERVAL (dqr_pharmacy.days_refill+28) DAY) < ?  "
                                + "	 AND (dqr_meta.termination_status IS NULL  ) "
                                + "     AND dqr_pharmacy.pickupdate= (  SELECT MAX(pickupdate) FROM dqr_pharmacy lastpickup "
                                + "        WHERE lastpickup.patient_id=dqr_pharmacy.patient_id "
                                + "	 HAVING MAX(pickupdate) <=? )   " );
			
			int i = 1;
			stmt = con.prepareStatement(queryString.toString());
			stmt.setString(i++, endDate);
			stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			
			while (rs.next()) {

                        int encounterId = 0;//rs.getInt("encounter_id");
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
	/*public List<Map<String,String>>  getPtsStartedOnARTWithoutDocGender(String startDate, String endDate) {
	
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
		queryString.append(" AND dqr_meta.gender =''  OR dqr_meta.gender IS NULL ");
		
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
	}*/
}
