/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dataquality.api.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.dataquality.Constants;
import org.openmrs.module.dataquality.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

public class DataqualityErrorDao {
	
	DbSessionFactory sessionFactory;
	
	private final String activeQueryJoinString = " JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id) "
	        + "  JOIN obs obsgroup ON obsgroup.person_id=person.person_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
	        + "  JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
	        + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL durationobs.value_numeric DAY) > ? AND person.voided=0 "
	        + " AND durationobs.value_numeric IS NOT NULL";
	
	private final String inActiveQueryJoinString = " JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id) "
	        + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
	        + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
	        + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL durationobs.value_numeric DAY) < ? AND patient.voided=0 "
	        + " AND durationobs.value_numeric IS NOT NULL";
	
	//where the duration for the last arv refill + 28 > now
	
	public DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Item getItemByUuid(String uuid) {
		return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
	}
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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
	
	public List<Object> getActivePatientsWithoutDocumentedMaritalStatus() {
		/*StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, IFNULL(hivE.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name, MAX(encounter.encounter_datetime) AS last_encounter FROM person ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN person_name ON person_name.person_id=person.person_id ");
		queryString.append("  LEFT JOIN encounter hivE ON hivE.patient_id=person.person_id AND hivE.form_id=?");
		queryString.append(activeQueryJoinString);
		queryString
		        .append(" AND (person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=1054 AND value_coded IS NULL ) "
		                + " OR person.person_id NOT IN (SELECT person_id FROM obs WHERE concept_id=1054)  ) AND person.voided=0 ");
		queryString.append(" GROUP BY person.person_id ");*/
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
	
	public List<Object> getActivePatientsWithoutDocumentedOccupationalStatus() {
		/*StringBuilder queryString = new StringBuilder(
		        "SELECT  person.person_id, IFNULL(hivE.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name, MAX(encounter.encounter_datetime) AS last_encounter FROM person ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN person_name ON person_name.person_id=person.person_id ");
		queryString.append("  LEFT JOIN encounter hivE ON hivE.patient_id=person.person_id AND hivE.form_id=?");
		queryString.append(activeQueryJoinString);
		queryString
		        .append(" AND (person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=1542 AND value_coded IS NULL ) "
		                + " OR person.person_id NOT IN (SELECT person_id FROM obs WHERE concept_id=1542)  ) AND person.voided=0 ");
		queryString.append(" GROUP BY person.person_id LIMIT 0, 100");*/
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
	
	public Integer getPatientsOnARTCount(String startDate, String endDate) {//patients who started art in the last 6 months
		StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count FROM person  ");
		
		queryString
		        .append("where person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		Object data = query.uniqueResult();
		System.out.println("hiii");
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getPtsOnArtWithNoInitialRegimen(String startDate, String endDate) { //patients who started art in the last 6 mts with initial art regimen
	
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, IFNULL(encounter.encounter_id, 0) AS encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=person.person_id ");
		queryString.append(" LEFT JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id=? ");
		queryString
		        .append(" LEFT JOIN obs ON obs.concept_id IN (164506, 164513, 165702, 164507, 164514, 165703) AND obs.encounter_id=encounter.encounter_id ");
		queryString.append("where  person.voided=0 ");
		queryString
		        .append(" AND person.person_id  IN (SELECT patient_id FROM patient_program WHERE program_id=1 AND date_enrolled BETWEEN ? AND ? ) AND obs.value_coded IS NULL ");
		
		/*StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(e.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient  ");
		queryString.append("  JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter enc WHERE enc.encounter_id =encounter.encounter_id ) ");
		queryString
		        .append(" JOIN obs  ON obs.person_id=patient.patient_id AND obs.concept_id IN (164506, 164513, 165702, 164507, 164514, 165703) AND obs.encounter_id=encounter.encounter_id ");
		
		queryString
		        .append("where person.person_id NOT  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		*/
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		List<Object> data = query.list();
		return data;
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
	
	public List<Object> getPatientsWithoutDocumentedAddress(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString
		        .append(" LEFT JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append(" LEFT JOIN person_name ON person_name.person_id=person.person_id ");
		//queryString.append(" LEFT JOIN person_address ON  person_address.person_id=person.person_id AND (person_address.address2 IS  NULL || person_address.city_village IS NULL)");
		queryString
		        .append(" where person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString
		        .append(" AND person.person_id NOT  IN (SELECT person_id FROM person_address WHERE person_address.address2 IS NOT NULL || person_address.city_village IS NOT NULL ) GROUP BY person.person_id");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		List<Object> data = query.list();
		return data;
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
	
	public List<Object> getPatientsWithoutDocumentedPostiveDate(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, IFNULL(encounter.encounter_id, 0) AS encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=person.person_id ");
		queryString.append(" LEFT JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id=? ");
		queryString
		        .append("where person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString
		        .append(" AND person.person_id NOT IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime IS NOT NULL)");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		query.setInteger(i++, Constants.HIV_ENROLLMENT_FORM);
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		query.setInteger(i++, Constants.DATE_CONFIRMED_POSITIVE);
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPatientsWithoutDocumentedHIVEnrollment(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=person.person_id ");
		
		queryString
		        .append(" where person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString
		        .append(" AND person.person_id NOT IN ( SELECT patient_program.patient_id FROM patient_program WHERE patient_program.program_id=?) ");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		query.setInteger(i++, Constants.HIV_PROGRAM_ID);
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPatientsWhoPickARVCount(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder("SELECT COUNT(person.person_id) as count FROM person  ");
		queryString
		        .append("where person.person_id  IN (SELECT person_id FROM obs WHERE concept_id=? AND value_datetime BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString
		        .append(" AND person.person_id IN (SELECT person_id FROM obs WHERE concept_id=? AND value_coded IS NOT NULL)");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		query.setInteger(i++, Constants.REGIMEN_LINE_CONCEPT);
		Object data = query.uniqueResult();
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getPatientsWithoutDocCd4Cnt(String startDate, String endDate) {//pts newly started on art without documented cd 4 count
		StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString.append("JOIN patient ON patient.patient_id=person.person_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=person.person_id ");
		queryString
		        .append("where person.person_id  IN (SELECT patient_id FROM patient_program WHERE program_id=1 AND date_enrolled BETWEEN ? AND ?  ) AND person.voided=0 ");
		queryString
		        .append(" AND person.person_id NOT IN (SELECT person_id FROM obs WHERE concept_id=? AND value_numeric IS NOT NULL) GROUP BY patient.patient_id");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		query.setInteger(i++, Constants.CD4_COUNT_CONCEPT);
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPtsWithClinicalVisitCount(String startDate, String endDate) {
		/*StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count FROM person  ");
		queryString
		        .append("JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id IN (22, 14, 20) AND encounter_datetime BETWEEN ? AND ? ");
		queryString.append("  WHERE  person.voided=0   ");*/
		StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT patient.patient_id) FROM patient ");
		queryString.append(" JOIN( ");
		queryString
		        .append(" SELECT encounter.encounter_datetime, encounter.encounter_id, encounter.patient_id, obs.value_numeric ");
		queryString.append("  FROM encounter ");
		queryString
		        .append("  JOIN (SELECT encounter_id, patient_id, MAX(encounter_datetime) AS lastdate FROM encounter GROUP BY patient_id) enc ");
		queryString.append(" ON enc.patient_id = encounter.patient_id AND enc.lastdate = encounter.encounter_datetime ");
		queryString
		        .append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? AND encounter.form_id IN (22, 14, 20) group by encounter.patient_id order by encounter.patient_id ");
		
		queryString.append(")");
		queryString.append("e ON e.patient_id=patient.patient_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		Object data = query.uniqueResult();
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getPtsWithClinicalVisitNoDocWeight(String startDate, String endDate) {
		System.out.println(startDate);
		System.out.println(endDate);
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(e.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN( ");
		queryString
		        .append(" SELECT encounter.encounter_datetime, encounter.encounter_id, encounter.patient_id, obs.value_numeric ");
		queryString.append("  FROM encounter ");
		queryString
		        .append("  JOIN (SELECT encounter_id, patient_id, MAX(encounter_datetime) AS lastdate FROM encounter GROUP BY patient_id) enc ");
		queryString.append(" ON enc.patient_id = encounter.patient_id AND enc.lastdate = encounter.encounter_datetime ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.person_id=encounter.patient_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? AND encounter.form_id IN (22, 14, 20) group by encounter.patient_id order by encounter.patient_id ");
		
		queryString.append(")");
		queryString.append("e ON e.patient_id=patient.patient_id ");
		queryString.append("WHERE e.value_numeric IS  NULL");
		/*queryString
		        .append("           SELECT encounter.encounter_id, encounter.patient_id, MAX(encounter.encounter_datetime) AS encdate, obs.value_coded  FROM encounter ");
		queryString.append("           LEFT JOIN obs ON obs.encounter_id=encounter.encounter_id AND obs.concept_id=? ");
		queryString
		        .append("            WHERE value_numeric IS NOT NULL AND encounter.form_id IN (22, 14, 20) AND encounter_datetime BETWEEN ? AND ?");
		queryString.append(" group by encounter.patient_id) ");
		queryString.append(" enc ON enc.patient_id=patient.patient_id) ");
		
		/* queryString
		 .append(" JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=person.person_id ");
		queryString
		 .append("JOIN (SELECT encounter_id, patient_id, form_id, max(encounter.encounter_datetime) AS encdate FROM encounter ");
		queryString
		 .append(" WHERE  encounter.form_id IN (22, 14, 20) AND encounter_datetime BETWEEN ? AND ? GROUP BY encounter.patient_id) enc ");
		queryString.append(" ON enc.patient_id=person.person_id ");
		queryString
		 .append(" WHERE person.person_id NOT IN (SELECT person_id FROM obs WHERE obs.encounter_id=enc.encounter_id AND obs.concept_id=? AND obs.value_numeric IS NOT NULL)");
		*/
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		System.out.println(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.WEIGHT_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsWithClinicalVisitNoFunctionalStatus(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(encounter.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append("  JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append("JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id IN (22, 14, 20) "
		                + "AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id IN (22, 14, 20) )");
		//queryString.append(" LEFT JOIN obs  ON obs.person_id=encounter.patient_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString.append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? AND encounter.form_id IN (22, 14, 20)  ");
		
		queryString
		        .append(" AND patient.patient_id NOT IN (SELECT person_id FROM obs WHERE "
		                + "obs.concept_id=? AND obs.value_coded IS NOT NULL AND obs.encounter_id=encounter.encounter_id) GROUP BY patient.patient_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		query.setInteger(i++, Constants.FUNCTIONAL_STATUS_CONCEPT);
		
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsWithClinicalVisitNoNextAppDate(String startDate, String endDate) {
		/*StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count FROM person  ");
		queryString
		        .append("JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id IN (22, 14, 20) AND encounter_datetime BETWEEN ? AND ? ");
		queryString
		        .append("JOIN obs wobs ON wobs.encounter_id=encounter.encounter_id AND wobs.concept_id=?  AND wobs.obs_datetime=(SELECT MAX(obs_datetime) FROM obs c WHERE c.obs_id =wobs.obs_id )  AND wobs.value_datetime IS NOT NULL ");
		*/
		StringBuilder queryString = new StringBuilder(
		        "  SELECT patient.patient_id, IFNULL(encounter.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append("  JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id IN (22, 14, 20) "
		                + "AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id IN (22, 14, 20) )");
		queryString
		        .append(" LEFT JOIN obs  ON obs.person_id=encounter.patient_id AND (obs.concept_id=? OR obs.concept_id=?) AND obs.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? AND encounter.form_id IN (22, 14, 20)  AND  obs.value_datetime IS NULL AND patient.voided=0 group by patient.patient_id  ");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.NEXT_APPOINTMENT_DATE);
		query.setInteger(i++, Constants.NEXT_APPOINTMENT_DATE2);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		//query.setString(i++, startDate);
		//query.setString(i++, endDate);
		
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getInactivePtsCount() {
		DateTime now = new DateTime(new Date());
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count FROM person  ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter enc WHERE enc.encounter_id=encounter.encounter_id ) ");
		queryString
		        .append(" JOIN obs obsgroup ON obsgroup.person_id=person.person_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" JOIN obs durationobs ON durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=? AND durationobs.value_numeric IS NOT NULL ");
		queryString.append(" WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL durationobs.value_numeric DAY) < ? ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		Object data = query.uniqueResult();
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getInactivePtsWithoutReasonCount() {
		DateTime now = new DateTime(new Date());
		now = now.minusDays(28);
		String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(encounter.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append("  JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		
		queryString
		        .append("JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.form_id="
		                + Constants.PHARMACY_FORM_ID
		                + " AND e.patient_id=encounter.patient_id)  "
		                + " JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id "
		                + " JOIN obs durationobs ON durationobs.encounter_id=encounter.encounter_id AND durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=?  "
		                + " WHERE DATE_ADD(encounter.encounter_datetime,  INTERVAL (durationobs.value_numeric+28) DAY) < ? AND patient.voided=0 "
		                + " AND durationobs.value_numeric IS NOT NULL ");
		queryString
		        .append(" AND patient.patient_id NOT IN ( SELECT person_id FROM  obs WHERE obs.concept_id=?) GROUP BY patient.patient_id");
		
		System.out.println(queryString.toString());
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		query.setString(i++, nowString);
		query.setInteger(i++, Constants.EXIT_REASON_CONCEPT);
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsWithClinicalVisitNoDocMUAC(String startDate, String endDate) {
		/*StringBuilder queryString = new StringBuilder(
		        "SELECT person.person_id, enc.encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name   FROM person  ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=person.person_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=person.person_id ");
		
		queryString
		        .append("JOIN (SELECT encounter_id, patient_id, form_id, max(encounter.encounter_datetime) AS encdate FROM encounter ");
		queryString
		        .append(" WHERE  encounter.form_id IN (22, 14, 20) AND encounter_datetime BETWEEN ? AND ? GROUP BY encounter.patient_id) enc ");
		queryString.append(" ON enc.patient_id=person.person_id ");
		queryString
		        .append("WHERE TIMESTAMPDIFF(YEAR,person.birthdate,CURDATE()) > 0 AND TIMESTAMPDIFF(YEAR,person.birthdate,CURDATE()) <15 ");
		queryString
		        .append(" AND person.person_id NOT IN (SELECT person_id FROM obs WHERE obs.encounter_id=enc.encounter_id AND obs.concept_id=? AND obs.value_numeric IS NOT NULL)");
		*/
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(encounter.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append(" JOIN person ON person.person_id=patient.patient_id ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append("JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id IN (22, 14, 20) "
		                + "AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter e WHERE e.patient_id=encounter.patient_id AND e.form_id IN (22, 14, 20) )");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.person_id=encounter.patient_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString.append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? AND encounter.form_id IN (22, 14, 20)  ");
		
		queryString.append(" AND  obs.value_numeric IS  NULL AND patient.voided=0 AND ");
		queryString
		        .append("  TIMESTAMPDIFF(YEAR,person.birthdate,CURDATE()) >= 0 AND TIMESTAMPDIFF(YEAR,person.birthdate,CURDATE()) <15  GROUP BY patient.patient_id");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.MUAC_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPedPtsWithClinicalVisitCount(String startDate, String endDate) {
		StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count  FROM person  ");
		queryString
		        .append("JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id IN (22, 14, 20) AND encounter_datetime BETWEEN ? AND ? ");
		queryString
		        .append("WHERE TIMESTAMPDIFF(YEAR,person.birthdate,CURDATE()) > 0 AND TIMESTAMPDIFF(YEAR,person.birthdate,CURDATE()) <15 ");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		Object data = query.uniqueResult();
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getPtsWithClinicalVisitNoDocWHO(String startDate, String endDate) {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(e.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN( ");
		queryString
		        .append(" SELECT encounter.encounter_datetime, encounter.encounter_id, encounter.patient_id, obs.value_coded ");
		queryString.append("  FROM encounter ");
		queryString
		        .append("  JOIN (SELECT encounter_id, patient_id, MAX(encounter_datetime) AS lastdate FROM encounter GROUP BY patient_id) enc ");
		queryString.append(" ON enc.patient_id = encounter.patient_id AND enc.lastdate = encounter.encounter_datetime ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.person_id=encounter.patient_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? AND encounter.form_id IN (22, 14, 20) group by encounter.patient_id order by encounter.patient_id ");
		
		queryString.append(")");
		queryString.append("e ON e.patient_id=patient.patient_id ");
		queryString.append("WHERE e.value_coded IS  NULL");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.WHO_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsWithClinicalVisitDocNoTBStatus(String startDate, String endDate) {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(e.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append("  JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append("  JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString.append("  JOIN( ");
		queryString
		        .append(" SELECT encounter.encounter_datetime, encounter.encounter_id, encounter.patient_id, obs.value_coded ");
		queryString.append("  FROM encounter ");
		queryString
		        .append("  JOIN (SELECT encounter_id, patient_id, MAX(encounter_datetime) AS lastdate FROM encounter GROUP BY patient_id) enc ");
		queryString.append(" ON enc.patient_id = encounter.patient_id AND enc.lastdate = encounter.encounter_datetime ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.person_id=encounter.patient_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" WHERE encounter.encounter_datetime BETWEEN ? AND ? AND encounter.form_id IN (22, 14, 20) group by encounter.patient_id order by encounter.patient_id ");
		
		queryString.append(")");
		queryString.append("e ON e.patient_id=patient.patient_id ");
		queryString.append("WHERE e.value_coded IS NULL");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		
		query.setInteger(i++, Constants.TB_STATUS_CONCEPT);
		query.setString(i++, startDate);
		query.setString(i++, endDate);
		
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPtsWithDocLastARVPickupCount() {
		StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count FROM person  ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter enc WHERE enc.encounter_id =encounter.encounter_id ) ");
		queryString
		        .append(" JOIN obs obsgroup ON obsgroup.person_id=person.person_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		Object data = query.uniqueResult();
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getPtsWithDocLastARVPickupWithoutRegimin() {
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(e.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name, obs.value_coded FROM person  ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=person.person_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter enc WHERE enc.encounter_id =encounter.encounter_id ) ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.person_id=person.person_id AND obs.concept_id IN (164506, 164513, 165702, 164507, 164514, 165703) AND obs.encounter_id=encounter.encounter_id "
		                + " WHERE  patient.voided=0  GROUP BY person.person_id HAVING obs.value_coded IS  NULL");
		//queryString.append(" JOIN obs  ON obs.person_id=person.person_id AND obs.concept_id IN (164506, 164513, 165702, 164507, 164514, 165703) AND obs.encounter_id=encounter.encounter_id ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsWithDocLastARVPickupWithoutDurationCount() {
		/*StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(e.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name FROM patient ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN( ");
		queryString
		        .append(" SELECT encounter.encounter_datetime, encounter.encounter_id, encounter.patient_id, obs.value_numeric ");
		queryString.append("  FROM encounter ");
		queryString
		        .append("  LEFT JOIN (SELECT encounter_id, patient_id, MAX(encounter_datetime) AS lastdate FROM encounter GROUP BY patient_id) enc ");
		queryString.append(" ON enc.patient_id = encounter.patient_id AND enc.lastdate = encounter.encounter_datetime ");
		queryString.append(" JOIN obs obsgroup ON  obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.obs_group_id=obsgroup.obs_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString.append(" WHERE encounter.form_id=? group by encounter.patient_id order by encounter.patient_id ");
		
		queryString.append(")");
		queryString.append("e ON e.patient_id=patient.patient_id ");
		queryString.append("WHERE e.value_numeric IS   NULL");*/
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(encounter.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name, obs.value_numeric FROM patient ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter enc WHERE enc.patient_id =encounter.patient_id AND enc.form_id=? ) ");
		
		queryString
		        .append(" LEFT JOIN obs obsgroup ON  obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.obs_group_id=obsgroup.obs_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString.append(" WHERE  patient.voided=0 ");
		/*queryString
		        .append(" AND patient.patient_id NOT IN (SELECT obs.person_id FROM obs "
		                + " JOIN obs qtyobs ON qtyobs.obs_group_id=obs.obs_id AND qtyobs.concept_id=? "
		                + " WHERE obs.concept_id=? AND  obs.encounter_id=encounter.encounter_id AND qtyobs.value_numeric IS NOT NULL )");*/
		
		queryString.append("  GROUP BY encounter.patient_id HAVING obs.value_numeric IS  NULL ");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsWithDocLastARVPickupWithDurationMoreThan180() {
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(encounter.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name, durationobs.value_numeric FROM patient  ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter enc WHERE enc.patient_id =encounter.patient_id AND enc.form_id=? ) ");
		queryString
		        .append("  JOIN obs obsgroup ON obsgroup.person_id=patient.patient_id AND obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id ");
		queryString
		        .append("  LEFT JOIN obs durationobs ON durationobs.obs_group_id=obsgroup.obs_id AND durationobs.concept_id=? ");
		queryString.append(" WHERE patient.voided=0 GROUP BY patient.patient_id HAVING durationobs.value_numeric > 180");
		//queryString.append("WHERE e.value_numeric > 180");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_DURATION);
		
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsEligibleForVLWithoutResult() {
		DateTime now = new DateTime(new Date());
		DateTime sixMonthsAgo = now.minusMonths(6);
		DateTime aYearAgo = now.minusMonths(12);
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name FROM person  ");
		queryString.append("JOIN patient ON patient.patient_id=person.person_id ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		
		queryString
		        .append(" JOIN obs artobs ON artobs.person_id=person.person_id AND artobs.concept_id=? AND artobs.value_datetime <=? ");//less than or eq to 6 months ago
		queryString.append(" WHERE patient.voided=0 AND person.person_id NOT IN ");
		//queryString.append(" ( SELECT obs.person_id FROM obs ");
		//queryString.append(" JOIN obs resultdateobs ON resultdateobs.encounter_id=obs.encounter_id WHERE obs.concept_id=? AND resultdateobs.concept_id=? AND resultdateobs.value_datetime>=? ) GROUP BY patient.patient_id");
		queryString
		        .append(" ( SELECT obs.person_id FROM obs WHERE obs.concept_id=?  AND obs.obs_datetime>=? ) GROUP BY patient.patient_id ");
		//queryString.append(" JOIN obs resultdateobs ON resultdateobs.encounter_id=obs.encounter_id WHERE obs.concept_id=? AND resultdateobs.concept_id=? AND resultdateobs.value_datetime>=? ) GROUP BY patient.patient_id");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, sixMonthsAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		query.setInteger(i++, Constants.VIRAL_LOAD_CONCEPT);
		//query.setInteger(i++, Constants.VIRAL_LOAD_RESULT_DATE_CONCEPT);
		query.setString(i++, aYearAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPtsEligibleForVLWithResultCount() {
		StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count FROM person  ");
		queryString
		        .append(" JOIN obs artobs ON artobs.person_id=person.person_id AND artobs.concept_id=? AND artobs.value_datetime <=? ");//less than or eq to 6 months ago
		queryString.append(" WHERE person.person_id IN ");
		queryString
		        .append(" ( SELECT obs.person_id FROM obs JOIN obs resultdateobs ON resultdateobs.encounter_id=obs.encounter_id WHERE obs.concept_id=? AND resultdateobs.concept_id=? AND resultdateobs.value_datetime>=? )");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		DateTime now = new DateTime(new Date());
		DateTime sixMonthsAgo = now.minusMonths(6);
		DateTime aYearAgo = now.minusMonths(12);
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, sixMonthsAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		query.setInteger(i++, Constants.VIRAL_LOAD_CONCEPT);
		query.setInteger(i++, Constants.VIRAL_LOAD_RESULT_DATE_CONCEPT);
		query.setString(i++, aYearAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		Object data = query.uniqueResult();
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getPtsEligibleForVLWithoutSampleCollection() {//patients with vl without sample collection date
		StringBuilder queryString = new StringBuilder(
		        "SELECT  patient.patient_id, IFNULL(vlobs.encounter_id, 0) AS encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name,  MAX(vlobs.obs_datetime), IFNULL(samplecollectionobs.value_datetime, 0) AS samplecollectiondate FROM person ");
		queryString.append(" JOIN patient ON patient.patient_id=person.person_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString.append(" JOIN obs vlobs ON vlobs.person_id=person.person_id AND vlobs.concept_id=? ");
		queryString
		        .append(" LEFT JOIN obs samplecollectionobs ON samplecollectionobs.encounter_id=vlobs.encounter_id AND samplecollectionobs.concept_id=? ");
		
		queryString.append(" WHERE patient.voided=0  GROUP BY patient.patient_id HAVING samplecollectiondate=0");
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		//DateTime now = new DateTime(new Date());
		//DateTime sixMonthsAgo = now.minusMonths(6);
		//DateTime aYearAgo = now.minusMonths(12);
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		//query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		//query.setString(i++, sixMonthsAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		query.setInteger(i++, Constants.VIRAL_LOAD_CONCEPT);
		query.setInteger(i++, Constants.DATE_SAMPLE_COLLECTED_CONCEPT);
		//query.setString(i++, aYearAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		
		List<Object> data = query.list();
		return data;
	}
	
	public Integer getPtsEligibleForVLWithSampleSentCount() {
		StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT person.person_id) as count FROM person  ");
		
		queryString
		        .append(" JOIN obs artobs ON artobs.person_id=person.person_id AND artobs.concept_id=? AND artobs.value_datetime <=? ");//less than or eq to 6 months ago
		queryString.append(" WHERE person.person_id IN ");
		queryString
		        .append(" ( SELECT obs.person_id FROM obs JOIN obs samplesentobs ON samplesentobs.encounter_id=obs.encounter_id WHERE obs.concept_id=? AND samplesentobs.value_datetime>=? )");
		DateTime now = new DateTime(new Date());
		DateTime sixMonthsAgo = now.minusMonths(6);
		DateTime aYearAgo = now.minusMonths(12);
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.ART_START_DATE_CONCEPT);
		query.setString(i++, sixMonthsAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		query.setInteger(i++, Constants.VIRAL_LOAD_CONCEPT);
		query.setInteger(i++, Constants.DATE_SAMPLE_SENT_CONCEPT);
		query.setString(i++, aYearAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		Object data = query.uniqueResult();
		
		return Integer.parseInt(data.toString());
	}
	
	public List<Object> getPtsEligibleForVLWithoutSampleReceived() {
		StringBuilder queryString = new StringBuilder(
		        " SELECT  patient.patient_id, IFNULL(vlobs.encounter_id, 0) AS encounter_id,  patient_identifier.identifier, person_name.given_name, person_name.family_name,  MAX(vlobs.obs_datetime), IFNULL(samplereceivedobs.value_datetime, 0) AS samplereceiveddate FROM person ");
		queryString.append(" JOIN patient ON patient.patient_id=person.person_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString.append(" JOIN obs vlobs ON vlobs.person_id=person.person_id AND vlobs.concept_id=? ");
		queryString
		        .append(" LEFT JOIN obs samplereceivedobs ON samplereceivedobs.encounter_id=vlobs.encounter_id AND samplereceivedobs.concept_id=? ");
		
		queryString.append(" WHERE patient.voided=0  GROUP BY patient.patient_id HAVING samplereceiveddate=0");
		DateTime now = new DateTime(new Date());
		DateTime sixMonthsAgo = now.minusMonths(6);
		DateTime aYearAgo = now.minusMonths(12);
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEPT);
		query.setInteger(i++, Constants.VIRAL_LOAD_CONCEPT);
		//query.setString(i++, sixMonthsAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		//query.setInteger(i++, Constants.VIRAL_LOAD_CONCEPT);
		query.setInteger(i++, Constants.DATE_RECEIVED_AT_PCR_LAB);
		//query.setString(i++, aYearAgo.toString("yyyy'-'MM'-'dd' 'HH':'mm"));
		List<Object> data = query.list();
		return data;
	}
	
	public List<Object> getPtsWithDocLastARVPickupWithoutQtyCount() {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT patient.patient_id, IFNULL(encounter.encounter_id, 0) AS encounter_id, patient_identifier.identifier, person_name.given_name, person_name.family_name, obs.value_numeric FROM patient ");
		queryString.append(" JOIN person_name ON person_name.person_id=patient.patient_id ");
		queryString
		        .append(" JOIN patient_identifier ON patient_identifier.patient_id=patient.patient_id AND patient_identifier.preferred=1 ");
		queryString
		        .append(" JOIN encounter ON encounter.patient_id=patient.patient_id AND encounter.form_id=? AND encounter.encounter_datetime=(SELECT MAX(encounter_datetime) FROM encounter enc WHERE enc.patient_id =encounter.patient_id AND enc.form_id=? ) ");
		
		queryString
		        .append(" LEFT JOIN obs obsgroup ON  obsgroup.concept_id=? AND obsgroup.encounter_id=encounter.encounter_id ");
		queryString
		        .append(" LEFT JOIN obs  ON obs.obs_group_id=obsgroup.obs_id AND obs.concept_id=? AND obs.encounter_id=encounter.encounter_id ");
		queryString.append(" WHERE  patient.voided=0 ");
		/*queryString
		        .append(" AND patient.patient_id NOT IN (SELECT obs.person_id FROM obs "
		                + " JOIN obs qtyobs ON qtyobs.obs_group_id=obs.obs_id AND qtyobs.concept_id=? "
		                + " WHERE obs.concept_id=? AND  obs.encounter_id=encounter.encounter_id AND qtyobs.value_numeric IS NOT NULL )");*/
		
		queryString.append("  GROUP BY patient.patient_id HAVING obs.value_numeric IS  NULL  ");
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		int i = 0;
		//stmt.setInt(i++, Constants.ART_START_DATE_CONCEP
		//T);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.PHARMACY_FORM_ID);
		query.setInteger(i++, Constants.ARV_GROUPING_CONCEPT);
		query.setInteger(i++, Constants.ARV_REGIMEN_QUANTITY);
		
		List<Object> data = query.list();
		return data;
	}
	
}
