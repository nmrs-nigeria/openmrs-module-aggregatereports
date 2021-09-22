package org.openmrs.module.dataquality;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author lordmaul
 */
public class Constants {
	
	public final static int STARTED_ART_LAST_6MONTHS_COHORT = 1;
	
	public final static int DOCUMENTED_EDUCATIONAL_STATUS_COHORT = 2;
	
	public final static int DOCUMENTED_MARITAL_STATUS_COHORT = 3;
	
	public final static int EVER_ENROLLED_IN_CARE_COHORT = 4;
	
	public final static int ACTIVE_COHORT = 5;
	
	public final static int CLINIC_VISIT_LAST_6MONTHS_COHORT = 6;
	
	public final static int DOCUMENTED_OCCUPATIONAL_STATUS_COHORT = 7;
	
	public final static int DOCUMENTED_DIAGNOSIS_STATUS_COHORT = 8;
	
	public final static int DOCUMENTED_SEX_COHORT = 9;
	
	public final static int DOCUMENTED_ADDRESS_COHORT = 10;
	
	public final static int CLIENT_INTAKE_FORM = 10;
	
	public final static int ACTIVE_DOCUMENTED_EDUCATIONAL_STATUS_COHORT = 11;
	
	public final static int ACTIVE_DOCUMENTED_MARITAL_STATUS_COHORT = 12;
	
	public final static int ACTIVE_DOCUMENTED_OCCUPATIONAL_STATUS_COHORT = 13;
	
	public final static int STARTED_ART_LAST_6MONTHS_DOCUMENTED_DOB = 14;
	
	public final static int DOCUMENTED_DOB_COHORT = 15;
	
	public final static int STARTED_ART_LAST_6MONTHS_DOCUMENTED_SEX = 16;
	
	public final static int STARTED_ART_LAST_6MONTHS_DOCUMENTED_DATECONFIRMED_POSITIVE = 17;
	
	public final static int STARTED_ART_LAST_6MONTHS_DOCUMENTED_HIVENROLLMENT = 18;
	
	public final static int PICKED_UP_ARV_DRUG_LAST_6MONTHS_COHORT = 19;
	
	public final static int DOCUMENTED_ART_START_DATE_COHORT = 20;
	
	public final static int DOCUMENTED_ART_START_DATE_ARV_PICKUP_COHORT = 21;
	
	public final static int DOCUMENTED_CD4_LAST_6MONTHS = 22;
	
	public final static int STARTED_ART_LAST_6MONTHS_DOCUMENTED_CD4_COUNT = 23;
	
	public final static int DOCUMENTED_WEIGHT_LAST_6MONTHS = 24;
	
	public final static int CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WEIGH = 25;
	
	public final static int PEDIATRIC_CLINIC_VISIT_LAST_6MONTHS = 26;
	
	public final static int PEDIATRIC_CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_MUAC = 27;
	
	public final static int DOCUMENTED_WHO_LAST_6MONTHS = 28;
	
	public final static int CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WHO = 29;
	
	public final static int DOCUMENTED_TB_STATUS_LAST_6MONTHS = 30;
	
	public final static int CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_TB_STATUS = 31;
	
	public final static int LAST_ARV_PHARMACY_PICKUP_COHORT = 32;
	
	public final static int LAST_ARV_PHARMACY_PICKUP_WITH_DURATION = 33;
	
	public final static int LAST_ARV_PHARMACY_PICKUP_WITH_QUANTITY = 34;
	
	public final static int LAST_ARV_PHARMACY_PICKUP_WITH_REGIMEN = 35;
	
	public final static int LAST_ARV_PHARMACY_PICKUP_WITH_DURATION_MORETHAN180DAYS = 36;
	
	public final static int VIRAL_LOAD_ELIGIBLE_WITH_DOCUMENTED_RESULT = 37;
	
	public final static int VIRAL_LOAD_ELIGIBLE_COHORT = 38;
	
	public final static int VIRAL_LOAD_RESULT_WITH_SAMPLE_COLLECTION_DATE = 39;
	
	public final static int VIRAL_LOAD_ELIGIBLE_WITH_SAMPLE_COLLECTION = 40;
	
	public final static int NEWLY_STARTED_ON_ART_WITH_DOCUMENTED_LGA = 41;
	
	public final static int SAMPLE_COLLECTED_WITH_SAMPLE_SENT_COHORT = 42;
	
	public final static int SAMPLE_SENT_WITH_SAMPLE_RECEIVED_AT_PCR = 43;
	
	public final static int STARTED_ART_LAST_6MONTHS_WITH_INITIAL_REGIMEN = 44;
	
	public final static int CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_NEXT_APPOINTMENT_DATE = 45;
	
	public final static int DOCUMENTED_EXIT_REASON_COHORT = 46;
	
	public final static int DOCUMENTED_EXIT_REASON_INACTIVE_COHORT = 47;
	
	public final static int INACTIVE_PATIENT_COHORT = 48;
	
	public final static int CLINIC_VISIT_LAST_6MONTHS_WITH_FUNCTIONAL_STATUS = 49;
	
	/*
	   Concept IDs
	 */
	public final static int EDUCATIONAL_STATUS_CONCEPT = 1712;
	
	public final static int MARITAL_STATUS_CONCEPT = 1054;
	
	public final static int EXIT_REASON_CONCEPT = 165470;
	
	public final static int OCCUPATIONAL_STATUS_CONCEPT = 1542;
	
	public final static int ART_START_DATE_CONCEPT = 159599;
	
	public final static int TERMINATION_CONCEPT = 165586;
	
	public final static int YES_CONCEPT = 1065;
	
	public final static int DATE_CONFIRMED_POSITIVE = 160554;
	
	public final static int HIV_ENROLLMENT_FORM = 23;
	
	public final static int PHARMACY_FORM_ID = 27;
	
	public final static int FINAL_HIV_RESULT = 165843;
	
	public final static int HIV_PROGRAM_ID = 1;
	
	public final static int RESULT_POSITIVE = 703;
	
	public final static int ART_COMMENCEMENT_FORM_ID = 53;
	
	public final static int REGIMEN_LINE_CONCEPT = 165708;
	
	public final static int CD4_COUNT_CONCEPT = 5497;
	
	public final static int CD4_PERCENT_CONCEPT = 730;
	
	public final static int WEIGHT_CONCEPT = 5089;
	
	public final static int MUAC_CONCEPT = 165243;
	
	public final static int WHO_CONCEPT = 5356;
	
	public final static int NEXT_APPOINTMENT_DATE = 165036;// 5096;
	
	public final static int NEXT_APPOINTMENT_DATE2 = 5096;
	
	public final static int TB_STATUS_CONCEPT = 1659;
	
	public final static int VIRAL_LOAD_CONCEPT = 856;
	
	public final static int VIRAL_LOAD_RESULT_DATE_CONCEPT = 165987;
	
	public final static int DATE_SAMPLE_COLLECTED_CONCEPT = 159951;
	
	public final static int OFFERED_INDEX_TESTING_CONCEPT = 165794;
	
	public final static int ARV_GROUPING_CONCEPT = 162240;
	
	public final static int ARV_REGIMEN_DURATION = 159368;
	
	public final static int ARV_REGIMEN_QUANTITY = 1443;//160856;
	
	public final static int ARV_COMMENCEMENT_FORM = 53;
	
	public final static int DATE_SAMPLE_SENT_CONCEPT = 165988;
	
	public final static int DATE_RECEIVED_AT_PCR_LAB = 165716;
	
	public final static int FUNCTIONAL_STATUS_CONCEPT = 165039;
	
	public final static int TOTAL_ACTIVE_PATIENTS = 11211212;
	
	public final static int STARTED_ART_LAST_6_MONTHS = 11211213;
	
	public final static int HAD_CLINIC_VISIT_6_MONTHS = 11211214;
	
	public final static int HAD_DOC_LAST_PICKUP = 11211215;
	
	public final static int ELIGIBLE_FOR_VIRAL_LOAD = 11211216;
	
	//public static Map<String, String> requestTypes = new HashMap<>();
	
}
