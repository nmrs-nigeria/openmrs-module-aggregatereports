/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.fragment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.openmrs.api.UserService;
import org.openmrs.module.dataquality.api.dao.ARTDao;
import org.openmrs.module.dataquality.api.dao.Database;
import org.openmrs.module.dataquality.api.dao.HTSDao;
import org.openmrs.module.dataquality.api.dao.LabDao;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 * @author lordmaul
 */
public class TbdetailsFragmentController {
	
	//HTSService dataQualityService = Context.getService(HTSService.class);
	HTSDao htsDao = new HTSDao();
	
	ARTDao artDao = new ARTDao();
	
	LabDao labDao = new LabDao();
	
	public void controller(FragmentModel model, HttpServletRequest request) {
		model.addAttribute("testing", "test");
		Database.initConnection();
		DateTime startDateTime = new DateTime("1990-01-01");
		DateTime endDateTime = new DateTime(new Date());
		int type = Integer.parseInt(request.getParameter("type"));
		if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
			
			startDateTime = new DateTime(request.getParameter("startDate"));
			endDateTime = new DateTime(request.getParameter("endDate"));
		}
		
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		
		String subTitle1 = "Newly diagnosed PLHIV and enrolled in care and treatment (first visit during the reporting period)";
		String subTitle2 = " PLHIV on ART before the reporting period (follow up visits) ";
		model.addAttribute("subTitle1", subTitle1);
		model.addAttribute("subTitle2", subTitle2);
		
		if (type == 1) {
			String title = "# Newly enrolled PLHIV ";
			List<Map<String, String>> allPtsEnrolledThisPeriod = artDao.getAllPtsStartedOnArt(startDate, endDate, false);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = artDao.getAllPtsStartedOnArt(startDate, endDate, true);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
			
		} else if (type == 2) {
			String title = "# Screened (symptoms)";
			
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllScreenedPatients(startDate, endDate, false);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllScreenedPatients(startDate, endDate, true);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 3) {
			String title = "# Presumptive TB ";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsPresumptiveTb(startDate, endDate, false);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsPresumptiveTb(startDate, endDate, true);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		}
		
		else if (type == 4) {
			String title = "# Diagnosed TB (GeneXpert, CXR, clinical, others) ";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsConfirmedTb(startDate, endDate, false);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsConfirmedTb(startDate, endDate, true);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 5) {
			String title = "# GeneXpert evaluated ";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, false,
			    "genexpert", 0);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, true,
			    "genexpert", 0);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 6) {
			String title = "# GeneXpert diagnosed TB";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, false,
			    "genexpert", 1);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, true,
			    "genexpert", 1);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 7) {
			String title = "# Chest x-ray evaluated ";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, false,
			    "chestxray", 0);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, true,
			    "chestxray", 0);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 8) {
			String title = "# Chest x-ray evaluated ";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, false,
			    "chestxray", 1);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, true,
			    "chestxray", 1);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 9) {
			String title = "# Clinically evaluated ";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, false,
			    "culture", 0);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, true,
			    "culture", 0);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 10) {
			String title = "# Clinically diagnosed TB";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, false,
			    "culture", 1);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsDiagnoseddForTb(startDate, endDate, true,
			    "culture", 1);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 11) {
			String title = "# Commenced on TB treatment";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsOnTbTreatment(startDate, endDate, false);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsOnTbTreatment(startDate, endDate, true);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 12) {
			String title = "# Eligible for TPT";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsEligibleForIPT(startDate, endDate, false);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsEligibleForIPT(startDate, endDate, true);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		} else if (type == 13) {
			String title = "# Commenced on TPT";
			List<Map<String, String>> allPtsEnrolledThisPeriod = labDao.getAllPtsStartedOnIPT(startDate, endDate, false);
			List<Map<String, String>> allPtsEnrolledBeforePeriod = labDao.getAllPtsStartedOnIPT(startDate, endDate, true);
			model.addAttribute("allPtsEnrolledThisPeriod", allPtsEnrolledThisPeriod);
			model.addAttribute("allPtsEnrolledBeforePeriod", allPtsEnrolledBeforePeriod);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
		}
		
		model.addAttribute("type", type);
		
		//Database.initConnection();
		
		//int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);
		
	}
	
	/****************************************************** ajax methods ****************************************************************/
	
	//Percentage of Adult clients tested HIV positive
	public String getTotalScreened(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");

            int totalEnrolledThisPeriodScreened = labDao.getTotalScreenedPatients(startDate, endDate, false);
            int totalEnrolledBeforePeriodScreened = labDao.getTotalScreenedPatients(startDate, endDate, true);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalEnrolledThisPeriodScreened",  totalEnrolledThisPeriodScreened+"");
            dataMap.put("totalEnrolledBeforePeriodScreened",  totalEnrolledBeforePeriodScreened+"");
            return new JSONObject(dataMap).toString();
		
	}
	
	//total of pts presumptive tb
	public String getTotalOnPresumptiveTB(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");

            int totalOnPresumptiveTBThisPeriod = labDao.getTotalPtsPresumptiveTb(startDate, endDate, false);
            int totalOnPresumptiveTBBeforePeriod = labDao.getTotalScreenedPatients(startDate, endDate, true);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalOnPresumptiveTBThisPeriod",  totalOnPresumptiveTBThisPeriod+"");
            dataMap.put("totalOnPresumptiveTBBeforePeriod",  totalOnPresumptiveTBBeforePeriod+"");
            return new JSONObject(dataMap).toString();
		
	}
	
	//total of pts confirmed tb
	public String getTotalOnConfirmedTB(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");

            int totalConfirmedTBThisPeriod = labDao.getTotalPtsConfirmedTb(startDate, endDate, false);
            int totalConfirmedTBBeforePeriod = labDao.getTotalPtsConfirmedTb(startDate, endDate, true);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalConfirmedTBThisPeriod",  totalConfirmedTBThisPeriod+"");
            dataMap.put("totalConfirmedTBBeforePeriod",  totalConfirmedTBBeforePeriod+"");
            return new JSONObject(dataMap).toString();
		
	}
	
	//total of pts confirmed tb
	public String getTotalOnTBTreatment(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
        //Database.initConnection();

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");

        int totalPtsOnTBTreatmentThisPeriod = labDao.getTotalPtsOnTbTreatment(startDate, endDate, false);
        int totalPtsOnTBTreatmentBeforePeriod = labDao.getTotalPtsOnTbTreatment(startDate, endDate, true);
        //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("totalPtsOnTBTreatmentThisPeriod",  totalPtsOnTBTreatmentThisPeriod+"");
        dataMap.put("totalPtsOnTBTreatmentBeforePeriod",  totalPtsOnTBTreatmentBeforePeriod+"");
        return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEligibleForIPT(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
        //Database.initConnection();

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");

        int totalEligibleForIPTThisPeriod = labDao.getTotalPtsEligibleForIPT(startDate, endDate, false);
        int totalEligibleForIPTBeforePeriod = labDao.getTotalPtsEligibleForIPT(startDate, endDate, true);
        //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("totalEligibleForIPTThisPeriod",  totalEligibleForIPTThisPeriod+"");
        dataMap.put("totalEligibleForIPTBeforePeriod",  totalEligibleForIPTBeforePeriod+"");
        return new JSONObject(dataMap).toString();

    }
	
	public String getTotalStartedOnIPT(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
        //Database.initConnection();

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm");

        int totalStartedIPTThisPeriod = labDao.getTotalPtsStartedOnIPT(startDate, endDate, false);
        int totalStartedIPTBeforePeriod = labDao.getTotalPtsStartedOnIPT(startDate, endDate, true);
        //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("totalStartedIPTThisPeriod",  totalStartedIPTThisPeriod+"");
        dataMap.put("totalStartedIPTBeforePeriod",  totalStartedIPTBeforePeriod+"");
        return new JSONObject(dataMap).toString();

    }
}
