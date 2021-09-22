/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.api.impl;

import org.joda.time.DateTime;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.dataquality.api.DataqualityService;
import org.openmrs.module.dataquality.api.HTSService;
import org.openmrs.module.dataquality.api.dao.HTSDao;

/**
 * @author lordmaul
 */
public class HTSServiceImpl extends BaseOpenmrsService implements HTSService {
	
	HTSDao dao;
	
	public void setDao(HTSDao dao) {
		this.dao = dao;
	}
	
	@Override
	public int getAllAdultPatientsTestedForHIV(DateTime startDate, DateTime endDate) throws APIException {
		return 0;
		//return dao.getAllAdultPatientsTestedForHIV(startDate, endDate);
	}
	
	@Override
	public int getAllAdultPatientsTestedPositieForHIV(DateTime startDate, DateTime endDate) throws APIException {
		return 1;
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
