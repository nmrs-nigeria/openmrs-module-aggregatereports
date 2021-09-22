/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dataquality.fragment.controller;

import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
//import liquibase.util.csv.opencsv.CSVWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.openmrs.module.dataquality.Constants;
import org.openmrs.module.dataquality.api.DataqualityService;
import org.openmrs.module.dataquality.api.dao.ClinicalDao;
import org.openmrs.module.dataquality.api.dao.LabDao;
import org.openmrs.module.dataquality.api.dao.PharmacyDao;
import org.openmrs.module.dataquality.util.PatientUtil;

public class UsersFragmentController {
	
        Map <String, Integer> dataCache = new HashMap<>();
        Map <String, DateTime> lastUpdated = new HashMap<>();
        DataqualityService dataQualityService = Context.getService(DataqualityService.class);
        //CohortBuilder cohortBuilder = new CohortBuilder();
        
     ClinicalDao clinicalDao = new ClinicalDao();
     PharmacyDao pharmacyDao = new PharmacyDao();
     LabDao labDao = new LabDao();
        
    public void controller(FragmentModel model, @SpringBean("userService") UserService service) {
        model.addAttribute("constants", Constants.class);	
    }
	
    
    private int getTotalActivePatients()
    {
        int totalCount = 0;
        if(PatientUtil.globalCache.containsKey("activePatients"))
        {
            //check if the value was set just below 1m ago
            DateTime timeSet = PatientUtil.globalCacheTime.get("activePatients");
            if(timeSet.plusMinutes(2).isBeforeNow())
            {
                totalCount = dataQualityService.getActivePatientCount();
                PatientUtil.globalCache.put("activePatients", totalCount);
                PatientUtil.globalCacheTime.put("activePatients", new DateTime());
                
            }else{
                totalCount = PatientUtil.globalCache.get("activePatients");
            }
        }else{//lets go to the database
            
            totalCount = dataQualityService.getActivePatientCount();
            PatientUtil.globalCache.put("activePatients", totalCount);
            PatientUtil.globalCacheTime.put("activePatients", new DateTime());
        }
        return totalCount;
    }
    
    public String getActivePatientsWithDocumentEducationalStaus(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            startDateTime = new DateTime("1990-01-01");
            endDateTime = new DateTime(new Date());
        }
        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsWithEducationalStatus = clinicalDao.getNoActivePtsWithWithEducationalStatus(startDate, endDate);
        int totalActivePatients = clinicalDao.getNoActivePts(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsWithEducationalStatus",  totalPtsWithEducationalStatus+"");
        dataMap.put("totalActivePatients",  totalActivePatients+"");
        //dataMap.put("totalPedPtsWithAppointment",  totalPedPtsWithAppointment+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getActivePatientsWithDocumentMaritalStaus(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            startDateTime = new DateTime("1990-01-01");
            endDateTime = new DateTime(new Date());
        }
        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsWithMaritalStatus = clinicalDao.getNoActivePtsWithWithMaritalStatus(startDate, endDate);
        int totalActivePatients = clinicalDao.getNoActivePts(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsWithMaritalStatus",  totalPtsWithMaritalStatus+"");
        dataMap.put("totalActivePatients",  totalActivePatients+"");
        //dataMap.put("totalPedPtsWithAppointment",  totalPedPtsWithAppointment+"");
        return new JSONObject(dataMap).toString();	
    }
    
    
    public String getActivePatientsWithDocumentOccupationalStaus(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            startDateTime = new DateTime("1990-01-01");
            endDateTime = new DateTime(new Date());
        }
        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsWithOccupationalStatus = clinicalDao.getNoActivePtsWithWithOccupationalStatus(startDate, endDate);
        int totalActivePatients = clinicalDao.getNoActivePts(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsWithOccupationalStatus",  totalPtsWithOccupationalStatus+"");
        dataMap.put("totalActivePatients",  totalActivePatients+"");
        //dataMap.put("totalPedPtsWithAppointment",  totalPedPtsWithAppointment+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsStartedOnARTWithDocDob(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithDob = clinicalDao.getPtsStartedOnARTWithDocDob(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithDob",  totalPtsStartedOnArtWithDob+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsStartedOnARTWithDocGender(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithGender = clinicalDao.getPtsStartedOnARTWithDocGender(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithGender",  totalPtsStartedOnArtWithGender+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsStartedOnARTWithDocAddress(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithAddress = clinicalDao.getPtsStartedOnARTWithDocAddress(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithAddress",  totalPtsStartedOnArtWithAddress+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsStartedOnARTWithDocHivDiagnosisDate(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithHivDiagnosisDate = clinicalDao.getPtsStartedOnARTWithDocHIVDiagnosisDate(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithHivDiagnosisDate",  totalPtsStartedOnArtWithHivDiagnosisDate+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }
    public String getPtsStartedOnARTWithDocHivEnrollmentDate(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithHivEnrollmentDate = clinicalDao.getPtsStartedOnARTWithDocHIVEnrollmentDate(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithHivEnrollmentDate",  totalPtsStartedOnArtWithHivEnrollmentDate+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }


    public String getPtsStartedOnARTWithDocDrugPickup(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithDrugPickup = clinicalDao.getPtsStartedOnARTWithDocDrugPickup(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithDrugPickup",  totalPtsStartedOnArtWithDrugPickup+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsStartedOnARTWithDocCd4(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithCd4 = clinicalDao.getPtsStartedOnARTWithDocCd4(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithCd4",  totalPtsStartedOnArtWithCd4+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }


    public String getPtsClinicVisitDocWeight(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsClinicVisitDocWeight = clinicalDao.getPtsWithClinicVisitDocWeight(startDate, endDate);
        int totalPtsClinicVisit = clinicalDao.getPtsWithClinicVisit(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsClinicVisitDocWeight",  totalPtsClinicVisitDocWeight+"");
        dataMap.put("totalPtsClinicVisit",  totalPtsClinicVisit+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsClinicVisitDocMuac(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsClinicVisitDocMuac = clinicalDao.getPtsWithClinicVisitDocMuac(startDate, endDate);
        int totalPtsClinicVisit = clinicalDao.getPedPtsWithClinicVisit(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsClinicVisitDocMuac",  totalPtsClinicVisitDocMuac+"");
        dataMap.put("totalPtsClinicVisit",  totalPtsClinicVisit+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsClinicVisitDocWhoStage(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsClinicVisitDocWhoStage = clinicalDao.getPtsWithClinicVisitDocWhoStage(startDate, endDate);
        int totalPtsClinicVisit = clinicalDao.getPtsWithClinicVisit(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsClinicVisitDocWhoStage",  totalPtsClinicVisitDocWhoStage+"");
        dataMap.put("totalPtsClinicVisit",  totalPtsClinicVisit+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsClinicVisitDocTBStatus(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsClinicVisitDocTBStatus = clinicalDao.getPtsWithClinicVisitDocTBStatus(startDate, endDate);
        int totalPtsClinicVisit = clinicalDao.getPtsWithClinicVisit(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsClinicVisitDocTBStatus",  totalPtsClinicVisitDocTBStatus+"");
        dataMap.put("totalPtsClinicVisit",  totalPtsClinicVisit+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsClinicVisitDocFunctionalStatus(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsClinicVisitDocFunctionalStatus = clinicalDao.getPtsWithClinicVisitDocFunctionalStatusStatus(startDate, endDate);
        int totalPtsClinicVisit = clinicalDao.getPtsWithClinicVisit(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsClinicVisitDocFunctionalStatus",  totalPtsClinicVisitDocFunctionalStatus+"");
        dataMap.put("totalPtsClinicVisit",  totalPtsClinicVisit+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsStartedOnARTWithInitialRegimen(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsStartedOnArtWithInitialRegimen = clinicalDao.getPtsStartedOnARTWithInitialRegimen(startDate, endDate);
        int totalPtsStartedOnArt = clinicalDao.getPtsStartedOnART(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsStartedOnArtWithInitialRegimen",  totalPtsStartedOnArtWithInitialRegimen+"");
        dataMap.put("totalPtsStartedOnArt",  totalPtsStartedOnArt+"");
        return new JSONObject(dataMap).toString();	
    }
    public String getPtsClinicVisitDocNextAppDate(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsClinicVisitDocNextAppDate = clinicalDao.getPtsWithClinicVisitDocNextAppDate(startDate, endDate);
        int totalPtsClinicVisit = clinicalDao.getPtsWithClinicVisit(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsClinicVisitDocNextAppDate",  totalPtsClinicVisitDocNextAppDate+"");
        dataMap.put("totalPtsClinicVisit",  totalPtsClinicVisit+"");
        return new JSONObject(dataMap).toString();	
    }

    public String getInactivePtsWithDocReason(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalInactivePtsWithReason = clinicalDao.getInactiveActivePtsWithDocReason(startDate, endDate);
        int totalInactivePts = clinicalDao.getInactiveActivePts(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalInactivePtsWithReason",  totalInactivePtsWithReason+"");
        dataMap.put("totalInactivePts",  totalInactivePts+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsWithDrugQuantity(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            endDateTime = new DateTime();
            //startDateTime = endDateTime.minusMonths(6);
            startDateTime = new DateTime("1990-01-01");
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPickupWithQuantity = pharmacyDao.getTotalPtsWithLastPickupQuantity(startDate, endDate);
        int totalPickup = pharmacyDao.getTotalPtsWithLastPick(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPickupWithQuantity",  totalPickupWithQuantity+"");
        dataMap.put("totalPickup",  totalPickup+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsWithDrugDuration(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            endDateTime = new DateTime();
            startDateTime = new DateTime("1990-01-01");
            //startDateTime = endDateTime.minusMonths(6);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPickupWithDuration = pharmacyDao.getTotalPtsWithLastPickupDuration(startDate, endDate);
        int totalPickup = pharmacyDao.getTotalPtsWithLastPick(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPickupWithDuration",  totalPickupWithDuration+"");
        dataMap.put("totalPickup",  totalPickup+"");
        return new JSONObject(dataMap).toString();	
    }
    public String getPtsWithDrugRegimen(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            endDateTime = new DateTime();
           startDateTime = new DateTime("1990-01-01");
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPickupWithRegimen = pharmacyDao.getTotalPtsWithLastPickupRegimen(startDate, endDate);
        int totalPickup = pharmacyDao.getTotalPtsWithLastPick(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPickupWithRegimen",  totalPickupWithRegimen+"");
        dataMap.put("totalPickup",  totalPickup+"");
        return new JSONObject(dataMap).toString();	
    }


    public String getPtsWithDrugPickupQtyLessThan180(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            endDateTime = new DateTime();
            startDateTime = new DateTime("1990-01-01");
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPickupWithQtyLessThan180 = pharmacyDao.getTotalPtsWithLastPickupQtyLessThan180(startDate, endDate);
        int totalPickup = pharmacyDao.getTotalPtsWithLastPick(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPickupWithQtyLessThan180",  totalPickupWithQtyLessThan180+"");
        dataMap.put("totalPickup",  totalPickup+"");
        return new JSONObject(dataMap).toString();	
    }

    public String getPtsEligibleForVLWithResult(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        DateTime startDateTime2;
        DateTime endDateTime2;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
            
        }else{
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(12);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        startDateTime2 = endDateTime.minusMonths(12);
        endDateTime2 = endDateTime.minusMonths(6);
        
        String startDate2 = startDateTime2.toString("yyyy'-'MM'-'dd");
        String endDate2 = endDateTime2.toString("yyyy'-'MM'-'dd");
        
        int totalPtsEligibleForVlWithResult = labDao.getTotalPtsEligibleForVLWithResult(endDate2, startDate2, endDate);
        int totalPtsEligibleForVl = labDao.getTotalPtsEligibleForVL(endDate2, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsEligibleForVlWithResult",  totalPtsEligibleForVlWithResult+"");
        dataMap.put("totalPtsEligibleForVl",  totalPtsEligibleForVl+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsWithResultAndSampleCollectionDate(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(12);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsWithVlResultAndSampleCollectionDate = labDao.getTotalPtsWithVlResultAndCollectionDate(startDate, endDate);
        int totalPtsWithResult = labDao.getTotalPtsWithVlResult(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsWithVlResultAndSampleCollectionDate",  totalPtsWithVlResultAndSampleCollectionDate+"");
        dataMap.put("totalPtsWithResult",  totalPtsWithResult+"");
        return new JSONObject(dataMap).toString();	
    }
    
    public String getPtsWithResultAndSampleReceivedDate(HttpServletRequest request)
    {
       
        DateTime startDateTime;
        DateTime endDateTime;
        if(!request.getParameter("startDate").equalsIgnoreCase(""))
        {
            startDateTime = new DateTime(request.getParameter("startDate"));
            endDateTime = new DateTime(request.getParameter("endDate"));
        }else{
            endDateTime = new DateTime();
            startDateTime = endDateTime.minusMonths(12);
        }
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        int totalPtsWithVlResultAndReceivedDate = labDao.getTotalPtsWithVlResultAndSampleSentDate(startDate, endDate);
        int totalPtsWithResult = labDao.getTotalPtsWithVlResult(startDate, endDate);
        
        Map<String, String> dataMap = new HashMap<>();
        
        dataMap.put("totalPtsWithVlResultAndReceivedDate",  totalPtsWithVlResultAndReceivedDate+"");
        dataMap.put("totalPtsWithResult",  totalPtsWithResult+"");
        return new JSONObject(dataMap).toString();	
    }
    
    
    public String getCohortCounts(HttpServletRequest request) {
        int type = Integer.parseInt(request.getParameter("type"));
        DateTime endDateTime = new DateTime(new Date());
	DateTime startDateTime = endDateTime.minusMonths(6);
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        
        
        if(type == Constants.TOTAL_ACTIVE_PATIENTS)
        {
            Map<String, String> dataMap = new HashMap<>();
            int totalActivePts = this.getTotalActivePatients();
            dataMap.put("totalActivePatients", totalActivePts+"");
            return new JSONObject(dataMap).toString();
        }
        else if( type ==  Constants.STARTED_ART_LAST_6_MONTHS)
        {
            Map<String, String> dataMap = new HashMap<>();
            int totalActivePts = dataQualityService.getPatientsOnARTCount(startDate, endDate);
            dataMap.put("totalPatientsStartedARTLast6Months",  totalActivePts+"");
            return new JSONObject(dataMap).toString();
        }
        else if(type == Constants.HAD_CLINIC_VISIT_6_MONTHS)
        {
            Map<String, String> dataMap = new HashMap<>();
            int totalPtsWithClinicVisit = dataQualityService.getPtsWithClinicalVisitCount(startDate, endDate);
            dataMap.put("totalPatientsClinicVisit",  totalPtsWithClinicVisit+"");
            return new JSONObject(dataMap).toString();
            
        }
        else if(type == Constants.HAD_DOC_LAST_PICKUP)//
        {
            Map<String, String> dataMap = new HashMap<>();
            int totalPtsWithDocARVPickup = dataQualityService.getPtsWithDocLastARVPickupCount();
            dataMap.put("totalPtsWithDocARVPickup",  totalPtsWithDocARVPickup+"");
            return new JSONObject(dataMap).toString();
        }else if(type == Constants.ELIGIBLE_FOR_VIRAL_LOAD )
        {
            Map<String, String> dataMap = new HashMap<>();
            int totalPtsEligibleForVl = dataQualityService.getPtsEligibleForVLResult();//dataQualityService.getPtsEligibleForVLCount();
            dataMap.put("totalPtsEligibleForVl",  totalPtsEligibleForVl+"");
            return new JSONObject(dataMap).toString();
            
        }
        
        if(type == Constants.ACTIVE_DOCUMENTED_EDUCATIONAL_STATUS_COHORT)
        {
            System.out.println("1");
            Map<String, String>dataMap = new HashMap<>();
            
           
            int totalActiveWithDocEducationalStatus = dataQualityService.getActivePatientsWithDocumentedEducationalStatus();//CohortBuilder.getActivePatientsWithDocumentedEducationalStatus();
           // int totalActivePts = CohortBuilder.getActivePatientCount();
           int totalActivePts = 10;//this.getTotalActivePatients();
           
         
           System.out.println("done fetching educational");
            float percent = (totalActiveWithDocEducationalStatus * 100/ totalActivePts);
            dataMap.put("numerator", totalActiveWithDocEducationalStatus+"");
            dataMap.put("denominator", totalActivePts+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
            //return totalActiveWithDocEducationalStatus+"";
        }
        else if(type == Constants.ACTIVE_DOCUMENTED_MARITAL_STATUS_COHORT)
        {
            
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getActivePatientsWithDocumentedMaritalStatus();//CohortBuilder.getActivePatientsWithDocumentedMaritalStatus();
            int denominator = 10; //this.getTotalActivePatients();
            float percent = (numerator * 100 / denominator);
            ;
            System.out.println("done fetching marital");
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }
        else if(type == Constants.ACTIVE_DOCUMENTED_OCCUPATIONAL_STATUS_COHORT)
        {
            System.out.println("3");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getActivePatientsWithDocumentedOccupationalStatus();//CohortBuilder.getActivePatientsWithDocumentedOccupationalStatus();
            int denominator = 10;//this.getTotalActivePatients();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }
        else if(type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_DOB)
        {
            System.out.println("4");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPatientsWithDocumentedDobCount(startDate, endDate);//CohortBuilder.getPatientsWithDocumentedDobCount(startDate, endDate);
            int denominator = 1;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }   
        else if(type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_SEX)
        {
            System.out.println("5");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPatientsWithDocumentedGenderCount(startDate, endDate);//CohortBuilder.getPatientsWithDocumentedGenderCount(startDate, endDate);
            int denominator = 1;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }
        else if(type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_DATECONFIRMED_POSITIVE)
        {
            System.out.println("6");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPatientsWithDocumentedPostiveDateCount(startDate, endDate);//CohortBuilder.getPatientsWithDocumentedPostiveDateCount(startDate, endDate);
            int denominator = 1;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }
        else if(type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_HIVENROLLMENT)
        {
            System.out.println("7");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPatientsWithDocumentedHIVEnrollmentCount(startDate, endDate);//CohortBuilder.getPatientsWithDocumentedHIVEnrollmentCount(startDate, endDate);
            int denominator = 1;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }

        else if(type == Constants.DOCUMENTED_ART_START_DATE_ARV_PICKUP_COHORT)
        {
            System.out.println("8");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPatientsWhoPickARVCount(startDate, endDate);//CohortBuilder.getPatientsWhoPickARVCount(startDate, endDate);
            int denominator = 1;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }        
        
        else if(type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_CD4_COUNT)
        {
            System.out.println("9");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPatientsWithDocCd4CntCount(startDate, endDate);//CohortBuilder.getPatientsWithDocCd4CntCount(startDate, endDate);
            int denominator = 1;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }  
        else if(type == Constants.NEWLY_STARTED_ON_ART_WITH_DOCUMENTED_LGA)
        {
            System.out.println("10");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPatientsWithDocumentedAddress(startDate, endDate);
            int denominator = 1;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }  
        else if(type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WEIGH)
        {
            System.out.println("11");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithClinicalVisitDocWeightCount(startDate, endDate);//CohortBuilder.getPtsWithClinicalVisitDocWeightCount(startDate, endDate);
            int denominator = 10;//dataQualityService.getPtsWithClinicalVisitCount(startDate, endDate);//CohortBuilder.getPtsWithClinicalVisitCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }  
        else if(type == Constants.PEDIATRIC_CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_MUAC)
        {
            System.out.println("12");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithClinicalVisitDocMUACCount(startDate, endDate);//CohortBuilder.getPtsWithClinicalVisitDocMUACCount(startDate, endDate);
            int denominator = dataQualityService.getPedPtsWithClinicalVisitCount(startDate, endDate);//CohortBuilder.getPtsWithClinicalVisitCount(startDate, endDate);//check and confirm. But this should be for pediatrics alone and not total clinic visit
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 

        else if(type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WHO)
        {
            System.out.println("13");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithClinicalVisitDocWHOCount(startDate, endDate);//CohortBuilder.getPtsWithClinicalVisitDocWHOCount(startDate, endDate);
            int denominator = 10;//dataQualityService.getPtsWithClinicalVisitCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        
        else if(type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_TB_STATUS)
        {
            System.out.println("14");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithClinicalVisitDocTBStatusCount(startDate, endDate);//CohortBuilder.getPtsWithClinicalVisitDocTBStatusCount(startDate, endDate);
            int denominator = 10;//dataQualityService.getPtsWithClinicalVisitCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        else if(type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_DURATION)
        {
            System.out.println("15");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithDocLastARVPickupWithDurationCount();//CohortBuilder.getPtsWithDocLastARVPickupWithDurationCount();
            int denominator = 10;//dataQualityService.getPtsWithDocLastARVPickupCount();//CohortBuilder.getPtsWithDocLastARVPickupCount();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        else if(type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_QUANTITY)
        {
            System.out.println("16");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithDocLastARVPickupWithQtyCount();//CohortBuilder.getPtsWithDocLastARVPickupWithQtyCount();
            int denominator = 10;//dataQualityService.getPtsWithDocLastARVPickupCount();//CohortBuilder.getPtsWithDocLastARVPickupCount();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        else if(type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_REGIMEN)
        {
            System.out.println("17");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithDocLastARVPickupWithRegiminCount();//CohortBuilder.getPtsWithDocLastARVPickupWithRegiminCount();
            int denominator = 10;//dataQualityService.getPtsWithDocLastARVPickupCount();//CohortBuilder.getPtsWithDocLastARVPickupCount();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        else if(type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_DURATION_MORETHAN180DAYS)
        {
            System.out.println("18");
            Map<String, String>dataMap = new HashMap<>();
            
            int numerator = dataQualityService.getPtsWithDocLastARVPickupWithDurationMoreThan180Count();//CohortBuilder.getPtsWithDocLastARVPickupWithDurationMoreThan180Count();
            int denominator = dataQualityService.getPtsWithDocLastARVPickupWithDurationCount();//CohortBuilder.getPtsWithDocLastARVPickupCount();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        
        else if(type == Constants.VIRAL_LOAD_ELIGIBLE_WITH_DOCUMENTED_RESULT)
        {
            System.out.println("19");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getPtsEligibleForVLWithResultCount();//CohortBuilder.getPtsEligibleForVLWithResultCount();
            int denominator = 10;//dataQualityService.getPtsEligibleForVLCount();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        
        else if(type == Constants.VIRAL_LOAD_RESULT_WITH_SAMPLE_COLLECTION_DATE)
        {
            System.out.println("20");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getPtsEligibleForVLWithSampleCollectionCount();
            int denominator = dataQualityService.getPtsWithVLResult();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        else if(type == Constants.SAMPLE_SENT_WITH_SAMPLE_RECEIVED_AT_PCR)
        {
            System.out.println("21");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getPtsEligibleForVLWithSampleReceivedCount();
            int denominator = 10;//dataQualityService.getPtsEligibleForVLWithResultCount();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        else if(type == Constants.CLINIC_VISIT_LAST_6MONTHS_WITH_FUNCTIONAL_STATUS)
        {
            System.out.println("22");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getPtsWithClinicalVisitFunctionalStatusCount(startDate, endDate);
            int denominator = 10;//dataQualityService.getPtsWithClinicalVisitCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        
        else if(type == Constants.STARTED_ART_LAST_6MONTHS_WITH_INITIAL_REGIMEN)
        {
            System.out.println("23");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getPtsOnArtWithInitialRegimen(startDate, endDate);//CohortBuilder.getPtsOnArtWithInitialRegimen(startDate, endDate);
            int denominator = 10;//dataQualityService.getPatientsOnARTCount(startDate, endDate);//CohortBuilder.getPatientsOnARTCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        } 
        
        else if(type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_NEXT_APPOINTMENT_DATE)
        {
            System.out.println("24");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getPtsWithClinicalVisitNextAppDateCount(startDate, endDate);
            int denominator = 10;//dataQualityService.getPtsWithClinicalVisitCount(startDate, endDate);
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }
        
        else if(type == Constants.DOCUMENTED_EXIT_REASON_INACTIVE_COHORT)
        {
            System.out.println("25");
            Map<String, String>dataMap = new HashMap<>();
            int numerator = dataQualityService.getInactivePtsWithReasonCount();
            int denominator = dataQualityService.getInactivePtsCount();
            float percent = (numerator * 100 / denominator);
            dataMap.put("numerator", numerator+"");
            dataMap.put("denominator", denominator+"");
            dataMap.put("percent", percent+"");
            return new JSONObject(dataMap).toString();
        }
        
        return "hi";
        
    }
        
    private int getTotalActivePts()
    {
        int totalActivePts = 0;
        DateTime now = new DateTime();
        System.out.println(dataCache.get("totalActivePts"));
        if(dataCache.containsKey("totalActivePts") && lastUpdated.containsKey("totalActivePts") && lastUpdated.get("totalActivePts").isAfter(now.minusMinutes(1)))
        {
           System.out.println("from cacheee");
           totalActivePts = dataCache.get("totalActivePts");//dataQualityService.getActivePatientCount();//builder.getActivePatientCount(); 
        }else
        {
            totalActivePts = dataQualityService.getActivePatientCount();//builder.getActivePatientCount(); 
            dataCache.put("totalActivePts", totalActivePts);
            lastUpdated.put("totalActivePts", now);
            System.out.println("from dss");
        }
        
        return totalActivePts;
    }
}
