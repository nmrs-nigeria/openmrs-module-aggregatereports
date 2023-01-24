/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dataquality.api;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.dataquality.DataqualityConfig;
import org.openmrs.module.dataquality.Item;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface DataqualityService extends OpenmrsService {
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	Item getItemByUuid(String uuid) throws APIException;
	
	/**
	 * Saves an item. Sets the owner to superuser, if it is not set. It can be called by users with
	 * this module's privilege. It is executed in a transaction.
	 * 
	 * @param item
	 * @return
	 * @throws APIException
	 */
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	Item saveItem(Item item) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getActivePatientCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getActivePatientsWithDocumentedEducationalStatus() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getActivePatientsWithDocumentedMaritalStatus() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getActivePatientsWithDocumentedOccupationalStatus() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsOnARTCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsOnArtWithInitialRegimen(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsWithDocumentedDobCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsWithDocumentedAddress(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsWithDocumentedGenderCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsWithDocumentedPostiveDateCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsWithDocumentedHIVEnrollmentCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsWhoPickARVCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPatientsWithDocCd4CntCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithClinicalVisitCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithClinicalVisitDocWeightCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithClinicalVisitFunctionalStatusCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithClinicalVisitNextAppDateCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getInactivePtsCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getInactivePtsWithReasonCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithClinicalVisitDocMUACCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPedPtsWithClinicalVisitCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithClinicalVisitDocWHOCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithClinicalVisitDocTBStatusCount(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithDocLastARVPickupCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithDocLastARVPickupWithRegiminCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithDocLastARVPickupWithDurationCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithDocLastARVPickupWithDurationMoreThan180Count() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsEligibleForVLWithoutResultCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsEligibleForVLWithResultCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsEligibleForVLWithSampleCollectionCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsEligibleForVLWithSampleSentCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsEligibleForVLWithSampleReceivedCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithDocLastARVPickupWithQtyCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsEligibleForVLCount() throws APIException;
	
	//views for data quality issues
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getActivePatientsWithoutDocumentedEducationalStatus() throws APIException;
	
	//views for data quality issues
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getActivePatientsWithoutDocumentedMaritalStatus() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getActivePatientsWithoutDocumentedOccupationalStatus() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPatientsWithoutDocumentedDob(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPatientsWithoutDocumentedGender(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPatientsWithoutDocumentedAddress(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithClinicalVisitNoDocWeight(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPatientsWithoutDocumentedPostiveDate(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPatientsWithoutDocumentedHIVEnrollment(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPatientsWithoutDocCd4Cnt(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithClinicalVisitDocNoWeight(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithClinicalVisitNoDocMUAC(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithClinicalVisitNoDocWHO(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithClinicalVisitNoDocTBStatus(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithClinicalVisitNoFunctionalStatus(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsOnArtWithNoInitialRegimen(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithClinicalVisitNoNextAppDate(String startDate, String endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getInactivePtsWithoutReasonCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithDocLastARVPickupWithoutQtyCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithDocLastARVPickupWithoutDurationCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithDocLastARVPickupWithoutRegiminCount() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsWithDocLastARVPickupWithDurationMoreThan180() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsEligibleForVLWithoutResult() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsEligibleForVLResult() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getPtsWithVLResult() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsEligibleForVLWithoutSampleCollection() throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	List<Object> getPtsEligibleForVLWithoutSampleReceived() throws APIException;
}
