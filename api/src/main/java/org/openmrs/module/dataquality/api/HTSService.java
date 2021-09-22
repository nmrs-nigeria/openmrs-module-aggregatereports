/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.api;

import org.joda.time.DateTime;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.dataquality.DataqualityConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lordmaul
 */
public interface HTSService extends OpenmrsService {
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getAllAdultPatientsTestedForHIV(DateTime startDate, DateTime endDate) throws APIException;
	
	@Authorized(DataqualityConfig.MODULE_PRIVILEGE)
	@Transactional
	int getAllAdultPatientsTestedPositieForHIV(DateTime startDate, DateTime endDate) throws APIException;
	
}
