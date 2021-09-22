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
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.dataquality.Constants;
import org.openmrs.module.dataquality.Misc;

/**
 * @author lordmaul
 */
public class HTSDao {
	
	DbSessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int getAllAdultPatientsTestedForHIV(String startDate, String endDate) {
		//indeed for some reason, doing a count was much slower than selecting the entire record and counting it. 
		//pretty strange.
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			
			StringBuilder queryString = new StringBuilder(
			        "select count(distinct dqr_clients.patient_id) AS count from dqr_clients ");
			queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
			queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
			queryString.append(" where  encounter.encounter_datetime BETWEEN ? AND ? and lastresult is NOT NULL  ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) >=15 ");
			
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
	
	public int getAllAdultPatientsTestedPositiveForHIV(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " select count(distinct dqr_clients.patient_id) AS count from dqr_clients ");
			queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
			queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
			queryString.append(" where  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive' ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) >=15 ");
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			//stmt.setInt(i++, Constants.CLIENT_INTAKE_FORM);
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
	
	public List<Map<String,String>> getAllAdultClientsPatientsTestedPositiveForHIV(String startDate, String endDate, int type) {
		
            List<Map<String, String>> allPatients = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(" select dqr_clients.patient_id, patient_identifier.identifier, setting.value_coded AS testing_modality, ");
                    queryString.append(" dqr_meta.dob, dqr_meta.gender, otherSetting.value_text AS otherModality, encounter.encounter_datetime ");
                    queryString.append(" from dqr_clients ");
                    queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
                    queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
                    queryString.append(" JOIN patient_identifier ON patient_identifier.patient_id=dqr_clients.patient_id AND patient_identifier.identifier_type=8");
                    queryString.append(" LEFT JOIN obs setting ON setting.person_id=dqr_clients.patient_id AND setting.concept_id=165839");
                    queryString.append(" LEFT JOIN obs otherSetting ON otherSetting.person_id=dqr_clients.patient_id AND otherSetting.concept_id=165966");
                    if(type == 1)
                    {
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive'  ");
                    }else{
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and (lastresult='negative' OR lastresult IS NULL)  ");
                    }
                    queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) >=15 ");

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    //stmt.setInt(i++, Constants.CLIENT_INTAKE_FORM);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();

                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("encounter_datetime"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("clientCode", rs.getString("identifier"));
                        tempPatient.put("testingModality", Misc.getSetting(rs.getInt("testing_modality"), rs.getString("otherModality")));
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
	
	public List<Map<String,String>> getAllPedClientsPatientsTestedPositiveForHIV(String startDate, String endDate, int type) {
		
            List<Map<String, String>> allPatients = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(" select dqr_clients.patient_id, patient_identifier.identifier, setting.value_coded AS testing_modality, ");
                    queryString.append(" dqr_meta.dob, dqr_meta.gender, otherSetting.value_text AS otherModality, encounter.encounter_datetime ");
                    queryString.append(" from dqr_clients ");
                    queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
                    queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
                    queryString.append(" JOIN patient_identifier ON patient_identifier.patient_id=dqr_clients.patient_id AND patient_identifier.identifier_type=8");
                    queryString.append(" LEFT JOIN obs setting ON setting.person_id=dqr_clients.patient_id AND setting.concept_id=165839");
                    queryString.append(" LEFT JOIN obs otherSetting ON otherSetting.person_id=dqr_clients.patient_id AND otherSetting.concept_id=165966");
                    if(type == 1)
                    {
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive'  ");
                    }else{
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and (lastresult='negative' OR lastresult IS NULL)  ");
                    }
                    queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) < 15 ");

                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    //stmt.setInt(i++, Constants.CLIENT_INTAKE_FORM);
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();

                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("encounter_datetime"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("clientCode", rs.getString("identifier"));
                        tempPatient.put("testingModality", Misc.getSetting(rs.getInt("testing_modality"), rs.getString("otherModality")));
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
	
	public int getAllPedPatientsTestedForHIV(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        " select count(distinct dqr_clients.patient_id) AS count from dqr_clients ");
			queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
			queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
			queryString.append(" where  encounter.encounter_datetime BETWEEN ? AND ?  ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) <15 ");
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			//stmt.setInt(i++, Constants.CLIENT_INTAKE_FORM);
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
	
	public int getAllPedPatientsTestedPositiveForHIV(String startDate, String endDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			/*StringBuilder queryString = new StringBuilder("select  count( distinct person.person_id) AS count FROM person ");
			queryString.append(" JOIN encounter ON encounter.form_id=? AND encounter.patient_id=person.person_id ");
			queryString.append(" JOIN obs ON obs.concept_id=165843 AND obs.person_id=person.person_id AND obs.voided=0 ");
			queryString.append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? ");
			queryString
			        .append("AND TIMESTAMPDIFF(YEAR,person.birthdate, encounter.encounter_datetime) >= 0 AND TIMESTAMPDIFF(YEAR,person.birthdate,encounter.encounter_datetime) <15 AND person.voided=0 AND obs.value_coded=703 ");
			*/
			StringBuilder queryString = new StringBuilder(
			        "select count(distinct dqr_clients.patient_id) AS count from dqr_clients ");
			queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
			queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
			queryString.append(" where  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive'  ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, CURDATE()) < 15 ");
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
	
	public int getTotalAdultsOfferedIndexTesting(String startDate, String endDate) {//total adults plhiv offered index testing
	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(distinct dqr_clients.patient_id) AS count from dqr_clients ");
			queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
			queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
			queryString
			        .append(" where  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive' AND offered_index='yes'  ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) >=15 ");
			
			//queryString.append(" group by person.person_id ");
			
			int i = 1;
			DateTime now = new DateTime();
			String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			/*stmt.setInt(i++, Constants.OFFERED_INDEX_TESTING_CONCEPT);
			stmt.setInt(i++, Constants.FINAL_HIV_RESULT);
			stmt.setInt(i++, Constants.RESULT_POSITIVE);
			stmt.setInt(i++, Constants.YES_CONCEPT);*/
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
	
	public List<Map<String,String>> getAllAdultsOfferedIndexTesting(String startDate, String endDate, int type) {//total adults plhiv offered index testing
	
            List<Map<String, String>> allPatients = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    
                    
                    StringBuilder queryString = new StringBuilder(" select dqr_clients.patient_id, patient_identifier.identifier, setting.value_coded AS testing_modality, ");
                    queryString.append(" dqr_meta.dob, dqr_meta.gender, otherSetting.value_text AS otherModality, encounter.encounter_datetime ");
                    queryString.append(" from dqr_clients ");
                    queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
                    queryString.append(" JOIN patient_identifier ON patient_identifier.patient_id=dqr_clients.patient_id AND patient_identifier.identifier_type=8");
                    queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
                    queryString.append(" LEFT JOIN obs setting ON setting.person_id=dqr_clients.patient_id AND setting.concept_id=165839");
                    queryString.append(" LEFT JOIN obs otherSetting ON otherSetting.person_id=dqr_clients.patient_id AND otherSetting.concept_id=165966");
                    if(type == 1)
                    {
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive' AND offered_index='yes'  ");
                    }else{
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive' AND (offered_index ='no' OR offered_index IS NULL)  ");
                    }
                    
                    queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) >=15 GROUP BY dqr_clients.patient_id ");

                    //queryString.append(" group by person.person_id ");

                    int i = 1;
                    DateTime now = new DateTime();
                    String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    /*stmt.setInt(i++, Constants.OFFERED_INDEX_TESTING_CONCEPT);
                    stmt.setInt(i++, Constants.FINAL_HIV_RESULT);
                    stmt.setInt(i++, Constants.RESULT_POSITIVE);
                    stmt.setInt(i++, Constants.YES_CONCEPT);*/
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();
                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("encounter_datetime"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("clientCode", rs.getString("identifier"));
                        tempPatient.put("testingModality", Misc.getSetting(rs.getInt("testing_modality"), rs.getString("otherModality")));
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
	
	public List<Map<String,String>> getAllPedsOfferedIndexTesting(String startDate, String endDate, int type) {//total adults plhiv offered index testing
	
            List<Map<String, String>> allPatients = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    StringBuilder queryString = new StringBuilder(" select dqr_clients.patient_id, patient_identifier.identifier, setting.value_coded AS testing_modality, ");
                    queryString.append(" dqr_meta.dob, dqr_meta.gender, otherSetting.value_text AS otherModality, encounter.encounter_datetime ");
                    queryString.append(" from dqr_clients ");
                    queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
                    queryString.append(" JOIN patient_identifier ON patient_identifier.patient_id=dqr_clients.patient_id AND patient_identifier.identifier_type=8");
                    queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
                    queryString.append(" LEFT JOIN obs setting ON setting.person_id=dqr_clients.patient_id AND setting.concept_id=165839");
                    queryString.append(" LEFT JOIN obs otherSetting ON otherSetting.person_id=dqr_clients.patient_id AND otherSetting.concept_id=165966");
                    if(type == 1)
                    {
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive' AND offered_index='yes'  ");
                    }else{
                        queryString.append(" WHERE  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive' AND (offered_index ='no' OR offered_index IS NULL)  ");
                    }
                    queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, encounter.encounter_datetime) <15 ");

                    //queryString.append(" group by person.person_id ");

                    int i = 1;
                    DateTime now = new DateTime();
                    String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(queryString.toString());

                    /*stmt.setInt(i++, Constants.OFFERED_INDEX_TESTING_CONCEPT);
                    stmt.setInt(i++, Constants.FINAL_HIV_RESULT);
                    stmt.setInt(i++, Constants.RESULT_POSITIVE);
                    stmt.setInt(i++, Constants.YES_CONCEPT);*/
                    stmt.setString(i++, startDate);
                    stmt.setString(i++, endDate);
                    rs = stmt.executeQuery();
                    while(rs.next())
                    {
                        String age = Misc.getAge(rs.getString("dob"), rs.getString("encounter_datetime"));
                        String ageRange = Misc.getAgeRange(age);
                        Map<String, String> tempPatient = new HashMap<>();
                        tempPatient.put("clientCode", rs.getString("identifier"));
                        tempPatient.put("testingModality", Misc.getSetting(rs.getInt("testing_modality"), rs.getString("otherModality")));
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
	
	public int getTotalPedsOfferedIndexTesting(String startDate, String endDate) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "select count(distinct dqr_clients.patient_id) AS count from dqr_clients ");
			queryString.append(" JOIN encounter ON dqr_clients.encounter_id=encounter.encounter_id ");
			queryString.append(" JOIN dqr_meta ON dqr_clients.patient_id=dqr_meta.patient_id ");
			queryString
			        .append(" where  encounter.encounter_datetime BETWEEN ? AND ? and lastresult='positive' AND offered_index='yes'  ");
			queryString.append(" AND TIMESTAMPDIFF(YEAR, dqr_meta.dob, CURDATE()) < 15 ");
			
			//queryString.append(" group by person.person_id ");
			
			int i = 1;
			DateTime now = new DateTime();
			String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(queryString.toString());
			
			/*stmt.setInt(i++, Constants.OFFERED_INDEX_TESTING_CONCEPT);
			stmt.setInt(i++, Constants.FINAL_HIV_RESULT);
			stmt.setInt(i++, Constants.RESULT_POSITIVE);
			stmt.setInt(i++, Constants.YES_CONCEPT);*/
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
	
	public DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public List<Map<String,String>> getClientIntakeEncounters(int patientId)
      {
          List<Map<String,String>> clientIntakeEncounters = new ArrayList<>();
          
          PreparedStatement stmt = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                    con = Database.connectionPool.getConnection();
                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);

                    String query = " SELECT encounter.encounter_id, resultobs.value_coded AS final_result, " +
                                    " offeredobs.value_coded AS offered_index FROM encounter " +
                                    " LEFT JOIN obs resultobs ON resultobs.encounter_id=encounter.encounter_id AND resultobs.concept_id=165843 AND resultobs.voided=0 " +
                                    " LEFT JOIN obs offeredobs ON offeredobs.encounter_id=encounter.encounter_id AND offeredobs.concept_id=165794 AND offeredobs.voided=0 " +
                                    " WHERE encounter.encounter_type=2 AND encounter.patient_id=? AND encounter.voided=0 ";
                    int i = 1;
                    //DateTime now = new DateTime(new Date());
                    //String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
                    stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.setInt(i++, patientId);
                    //stmt.setFetchSize(200);
                    rs = stmt.executeQuery();
                    //rs.setFetchSize(limit);
                    while (rs.next()) {
                        
                        //System.out.println("HICV statuys: "+rs.getString("final_result"));
                        String hivFinalResult = (rs.getString("final_result") != null) ? Misc.getHIVStatus(Integer.parseInt(rs.getString("final_result"))) : null;
                        String offeredIndex = (rs.getString("offered_index") != null) ? Misc.getYesNo(Integer.parseInt(rs.getString("offered_index"))) : null;
                        
                        Map<String,String> tempMap = new HashMap<>();
                        tempMap.put("patient_id", patientId+"");
                        tempMap.put("encounter_id", rs.getString("encounter_id"));
                        tempMap.put("final_result", hivFinalResult);
                        tempMap.put("offered_index", offeredIndex);

                        clientIntakeEncounters.add(tempMap);
                    }
                    return clientIntakeEncounters;

            }
            catch (SQLException ex) {
                    Database.handleException(ex);
                    return null;
            }
            finally {
                    Database.cleanUp(rs, stmt, con);
            }
          
          
      }
	
	public int saveClientEncounters(List<Map<String, String>> allClientEncounters) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "INSERT INTO dqr_clients (patient_id, encounter_id, lastresult, offered_index)VALUES";
			for (int i = 0; i < allClientEncounters.size(); i++) {
				query += "(?, ?, ?, ?),";
			}
			
			query = query.substring(0, query.length() - 1);
			query += " ON DUPLICATE KEY UPDATE lastresult=VALUES(lastresult), offered_index=VALUES(offered_index) ";
			
			int i = 1;
			stmt = con.prepareStatement(query);
			
			for (int j = 0; j < allClientEncounters.size(); j++) {
				stmt.setInt(i++, Integer.parseInt(allClientEncounters.get(j).get("patient_id")));
				stmt.setInt(i++, Integer.parseInt(allClientEncounters.get(j).get("encounter_id")));
				stmt.setString(i++, allClientEncounters.get(j).get("final_result"));
				stmt.setString(i++, allClientEncounters.get(j).get("offered_index"));
				
			}
			//stmt.setFetchSize(200);
			if (allClientEncounters.size() > 0)
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
}
