/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dataquality.api.impl;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.dataquality.Item;
import org.openmrs.module.dataquality.api.DataqualityService;
import org.openmrs.module.dataquality.api.dao.DataqualityDao;
import org.openmrs.module.dataquality.api.dao.DataqualityErrorDao;

public class DataqualityServiceImpl extends BaseOpenmrsService implements DataqualityService {
	
	DataqualityDao dao;
	
	DataqualityErrorDao errorDao;
	
	UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(DataqualityDao dao) {
		this.dao = dao;
	}
	
	public void setErrorDao(DataqualityErrorDao errorDao) {
		this.errorDao = errorDao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Item getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}
	
	@Override
	public Item saveItem(Item item) throws APIException {
		if (item.getOwner() == null) {
			item.setOwner(userService.getUser(1));
		}
		
		return dao.saveItem(item);
	}
	
	@Override
	public int getActivePatientCount() throws APIException {
		return dao.getActivePatientCount();
	}
	
	@Override
	public int getActivePatientsWithDocumentedEducationalStatus() throws APIException {
		//System.out.println(dao.getActivePatientDocumentedEducationStatusCount());
		return dao.getActivePatientsWithDocumentedEducationalStatus();
	}
	
	@Override
	public int getActivePatientsWithDocumentedMaritalStatus() throws APIException {
		return dao.getActivePatientsWithDocumentedMaritalStatus();
	}
	
	@Override
	public int getActivePatientsWithDocumentedOccupationalStatus() throws APIException {
		return dao.getActivePatientsWithDocumentedOccupationalStatus();
	}
	
	@Override
	public int getPatientsOnARTCount(String startDate, String endDate) throws APIException {
		return dao.getPatientsOnARTCount(startDate, endDate);
	}
	
	@Override
	public int getPtsOnArtWithInitialRegimen(String startDate, String endDate) throws APIException {
		return dao.getPtsOnArtWithInitialRegimen(startDate, endDate);
	}
	
	@Override
	public int getPatientsWithDocumentedDobCount(String startDate, String endDate) throws APIException {
		return dao.getPatientsWithDocumentedDobCount(startDate, endDate);
	}
	
	@Override
	public int getPatientsWithDocumentedGenderCount(String startDate, String endDate) throws APIException {
		return dao.getPatientsWithDocumentedGenderCount(startDate, endDate);
	}
	
	@Override
	public int getPatientsWithDocumentedPostiveDateCount(String startDate, String endDate) throws APIException {
		return dao.getPatientsWithDocumentedPostiveDateCount(startDate, endDate);
	}
	
	@Override
	public int getPatientsWithDocumentedHIVEnrollmentCount(String startDate, String endDate) throws APIException {
		return dao.getPatientsWithDocumentedHIVEnrollmentCount(startDate, endDate);
	}
	
	@Override
	public int getPatientsWhoPickARVCount(String startDate, String endDate) throws APIException {
		return dao.getPatientsWhoPickARVCount(startDate, endDate);
	}
	
	@Override
	public int getPatientsWithDocCd4CntCount(String startDate, String endDate) throws APIException {
		return dao.getPatientsWithDocCd4CntCount(startDate, endDate);
	}
	
	@Override
	public int getPtsWithClinicalVisitCount(String startDate, String endDate) throws APIException {
		return dao.getPtsWithClinicalVisitCount(startDate, endDate);
	}
	
	@Override
	public int getPtsWithClinicalVisitDocWeightCount(String startDate, String endDate) throws APIException {
		return dao.getPtsWithClinicalVisitDocWeightCount(startDate, endDate);
	}
	
	@Override
	public int getPtsWithClinicalVisitFunctionalStatusCount(String startDate, String endDate) throws APIException {
		return dao.getPtsWithClinicalVisitFunctionalStatusCount(startDate, endDate);
	}
	
	@Override
	public int getPtsWithClinicalVisitNextAppDateCount(String startDate, String endDate) throws APIException {
		return dao.getPtsWithClinicalVisitNextAppDateCount(startDate, endDate);
	}
	
	@Override
	public int getInactivePtsCount() throws APIException {
		return dao.getInactivePtsCount();
	}
	
	@Override
	public int getInactivePtsWithReasonCount() throws APIException {
		return dao.getInactivePtsWithReasonCount();
	}
	
	@Override
	public int getPtsWithClinicalVisitDocMUACCount(String startDate, String endDate) throws APIException {
		return dao.getPtsWithClinicalVisitDocMUACCount(startDate, endDate);
	}
	
	@Override
	public int getPtsWithClinicalVisitDocWHOCount(String startDate, String endDate) throws APIException {
		return dao.getPtsWithClinicalVisitDocWHOCount(startDate, endDate);
	}
	
	@Override
	public int getPtsWithDocLastARVPickupCount() throws APIException {
		return dao.getPtsWithDocLastARVPickupCount();
	}
	
	@Override
	public int getPtsWithDocLastARVPickupWithRegiminCount() throws APIException {
		return dao.getPtsWithDocLastARVPickupWithRegiminCount();
	}
	
	@Override
	public int getPtsWithDocLastARVPickupWithDurationCount() throws APIException {
		return dao.getPtsWithDocLastARVPickupWithDurationCount();
	}
	
	@Override
	public int getPtsWithDocLastARVPickupWithDurationMoreThan180Count() throws APIException {
		return dao.getPtsWithDocLastARVPickupWithDurationMoreThan180Count();
	}
	
	@Override
	public int getPtsEligibleForVLWithoutResultCount() throws APIException {
		return dao.getPtsEligibleForVLWithoutResultCount();
	}
	
	@Override
	public int getPtsEligibleForVLWithResultCount() throws APIException {
		return dao.getPtsEligibleForVLWithResultCount();
	}
	
	@Override
	public int getPtsEligibleForVLWithSampleCollectionCount() throws APIException {
		return dao.getPtsEligibleForVLWithSampleCollectionCount();
	}
	
	@Override
	public int getPtsEligibleForVLWithSampleSentCount() throws APIException {
		return dao.getPtsEligibleForVLWithSampleSentCount();
	}
	
	@Override
	public int getPtsEligibleForVLWithSampleReceivedCount() throws APIException {
		return dao.getPtsEligibleForVLWithSampleReceivedCount();
	}
	
	@Override
	public int getPtsWithDocLastARVPickupWithQtyCount() throws APIException {
		return dao.getPtsWithDocLastARVPickupWithQtyCount();
	}
	
	@Override
	public int getPtsEligibleForVLCount() throws APIException {
		return (this.getPtsEligibleForVLWithoutResultCount() + this.getPtsEligibleForVLWithResultCount());
	}
	
	@Override
	public int getPtsWithClinicalVisitDocTBStatusCount(String startDate, String endDate) throws APIException {
		return dao.getPtsWithClinicalVisitDocTBStatusCount(startDate, endDate);
	}
	
	@Override
	public int getPatientsWithDocumentedAddress(String startDate, String endDate) throws APIException {
		return dao.getPatientsWithDocumentedAddress(startDate, endDate);
	}
	
	@Override
	public int getPedPtsWithClinicalVisitCount(String startDate, String endDate) throws APIException {
		return dao.getPedPtsWithClinicalVisitCount(startDate, endDate);
	}
	
	@Override
	public List<Object> getActivePatientsWithoutDocumentedEducationalStatus() throws APIException {
		//System.out.println(dao.getActivePatientDocumentedEducationStatusCount());
		return errorDao.getActivePatientsWithoutDocumentedEducationalStatus();
	}
	
	@Override
	public List<Object> getActivePatientsWithoutDocumentedMaritalStatus() throws APIException {
		return errorDao.getActivePatientsWithoutDocumentedMaritalStatus();
	}
	
	@Override
	public List<Object> getActivePatientsWithoutDocumentedOccupationalStatus() throws APIException {
		return errorDao.getActivePatientsWithoutDocumentedOccupationalStatus();
	}
	
	@Override
	public List<Object> getPatientsWithoutDocumentedDob(String startDate, String endDate) throws APIException {
		return errorDao.getPatientsWithDocumentedDob(startDate, endDate);
	}
	
	@Override
	public List<Object> getPatientsWithoutDocumentedGender(String startDate, String endDate) throws APIException {
		return errorDao.getPatientsWithoutDocumentedGender(startDate, endDate);
	}
	
	@Override
	public List<Object> getPatientsWithoutDocumentedAddress(String startDate, String endDate) throws APIException {
		return errorDao.getPatientsWithoutDocumentedAddress(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsWithClinicalVisitNoDocWeight(String startDate, String endDate) throws APIException {
		return errorDao.getPtsWithClinicalVisitNoDocWeight(startDate, endDate);
	}
	
	@Override
	public List<Object> getPatientsWithoutDocumentedPostiveDate(String startDate, String endDate) throws APIException {
		return errorDao.getPatientsWithoutDocumentedPostiveDate(startDate, endDate);
	}
	
	@Override
	public List<Object> getPatientsWithoutDocumentedHIVEnrollment(String startDate, String endDate) throws APIException {
		return errorDao.getPatientsWithoutDocumentedHIVEnrollment(startDate, endDate);
	}
	
	@Override
	public List<Object> getPatientsWithoutDocCd4Cnt(String startDate, String endDate) throws APIException {
		return errorDao.getPatientsWithoutDocCd4Cnt(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsWithClinicalVisitDocNoWeight(String startDate, String endDate) throws APIException {
		return errorDao.getPtsWithClinicalVisitNoDocWeight(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsWithClinicalVisitNoDocMUAC(String startDate, String endDate) throws APIException {
		return errorDao.getPtsWithClinicalVisitNoDocMUAC(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsWithClinicalVisitNoDocWHO(String startDate, String endDate) throws APIException {
		return errorDao.getPtsWithClinicalVisitNoDocWHO(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsWithClinicalVisitNoDocTBStatus(String startDate, String endDate) throws APIException {
		return errorDao.getPtsWithClinicalVisitDocNoTBStatus(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsWithClinicalVisitNoFunctionalStatus(String startDate, String endDate) throws APIException {
		return errorDao.getPtsWithClinicalVisitNoFunctionalStatus(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsOnArtWithNoInitialRegimen(String startDate, String endDate) throws APIException {
		return errorDao.getPtsOnArtWithNoInitialRegimen(startDate, endDate);
	}
	
	@Override
	public List<Object> getPtsWithClinicalVisitNoNextAppDate(String startDate, String endDate) throws APIException {
		return errorDao.getPtsWithClinicalVisitNoNextAppDate(startDate, endDate);
	}
	
	@Override
	public List<Object> getInactivePtsWithoutReasonCount() throws APIException {
		return errorDao.getInactivePtsWithoutReasonCount();
	}
	
	@Override
	public List<Object> getPtsWithDocLastARVPickupWithoutQtyCount() throws APIException {
		return errorDao.getPtsWithDocLastARVPickupWithoutQtyCount();
	}
	
	@Override
	public List<Object> getPtsWithDocLastARVPickupWithoutDurationCount() throws APIException {
		return errorDao.getPtsWithDocLastARVPickupWithoutDurationCount();
	}
	
	@Override
	public List<Object> getPtsWithDocLastARVPickupWithoutRegiminCount() throws APIException {
		return errorDao.getPtsWithDocLastARVPickupWithoutRegimin();
	}
	
	@Override
	public List<Object> getPtsWithDocLastARVPickupWithDurationMoreThan180() throws APIException {
		return errorDao.getPtsWithDocLastARVPickupWithDurationMoreThan180();
	}
	
	@Override
	public List<Object> getPtsEligibleForVLWithoutResult() throws APIException {
		return errorDao.getPtsEligibleForVLWithoutResult();
	}
	
	@Override
	public List<Object> getPtsEligibleForVLWithoutSampleCollection() throws APIException {
		return errorDao.getPtsEligibleForVLWithoutSampleCollection();
	}
	
	@Override
	public List<Object> getPtsEligibleForVLWithoutSampleReceived() throws APIException {
		return errorDao.getPtsEligibleForVLWithoutSampleReceived();
	}
	
	@Override
	public int getPtsEligibleForVLResult() throws APIException {
		return dao.getPtsEligibleForVLCount();
	}
	
	@Override
	public int getPtsWithVLResult() throws APIException {
		return dao.getPtsWithVLResult();
	}
	
}
