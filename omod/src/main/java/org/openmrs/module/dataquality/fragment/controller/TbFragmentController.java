/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.fragment.controller;

import java.util.Date;
import java.util.HashMap;
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
public class TbFragmentController {
	
	//HTSService dataQualityService = Context.getService(HTSService.class);
	HTSDao htsDao = new HTSDao();
	
	ARTDao artDao = new ARTDao();
	
	LabDao labDao = new LabDao();
	
	public void controller(FragmentModel model, @SpringBean("userService") UserService service) {
		model.addAttribute("testing", "test");
		Database.initConnection();
	}
	
	/****************************************************** ajax methods ****************************************************************/
	
	//Percentage of Adult clients tested HIV positive
	public String getAllNewlyEnrolled(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalEnrolledThisPeriod = artDao.getPtsStartedOnArt(startDate, endDate, false);
            int totalEnrolledBeforePeriod = artDao.getPtsStartedOnArt(startDate, endDate, true);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            System.out.println("enrolledThisPeriod: "+totalEnrolledThisPeriod);
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("enrolledThisPeriod",  totalEnrolledThisPeriod+"");
            dataMap.put("totalEnrolledBeforePeriod",  totalEnrolledBeforePeriod+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();
		
	}
	
	//Percentage of Adult clients tested HIV positive
	public String getTotalScreened(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

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

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

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

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalConfirmedTBThisPeriod = labDao.getTotalPtsConfirmedTb(startDate, endDate, false);
            int totalConfirmedTBBeforePeriod = labDao.getTotalPtsConfirmedTb(startDate, endDate, true);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalConfirmedTBThisPeriod",  totalConfirmedTBThisPeriod+"");
            dataMap.put("totalConfirmedTBBeforePeriod",  totalConfirmedTBBeforePeriod+"");
            return new JSONObject(dataMap).toString();
		
	}
	
	//total of pts confirmed tb
	public String getTotalGeneXpertEvaluatedTB(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalEvaluatedWithGeneXpertThisPeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, false, "genexpert", 0);
            int totalEvaluatedWithGeneXpertBeforePeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, true, "genexpert", 0);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalEvaluatedWithGeneXpertThisPeriod",  totalEvaluatedWithGeneXpertThisPeriod+"");
            dataMap.put("totalEvaluatedWithGeneXpertBeforePeriod",  totalEvaluatedWithGeneXpertBeforePeriod+"");
            return new JSONObject(dataMap).toString();
		
	}
	
	//total of pts confirmed tb
	public String getTotalGeneXpertDiagnosedTB(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalDiagnosedWithGeneXpertThisPeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, false, "genexpert", 1);
            int totalDiagnosedWithGeneXpertBeforePeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, true, "genexpert", 1);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalDiagnosedWithGeneXpertThisPeriod",  totalDiagnosedWithGeneXpertThisPeriod+"");
            dataMap.put("totalDiagnosedWithGeneXpertBeforePeriod",  totalDiagnosedWithGeneXpertBeforePeriod+"");
            return new JSONObject(dataMap).toString();
		
    }
	
	//total of pts confirmed tb
	public String getTotalChestXRayEvaluatedTB(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
        //Database.initConnection();

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

        int totalEvaluatedWithChestXRayThisPeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, false, "chestxray", 0);
        int totalEvaluatedWithChestXRayBeforePeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, true, "chestxray", 0);
        //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("totalEvaluatedWithChestXRayThisPeriod",  totalEvaluatedWithChestXRayThisPeriod+"");
        dataMap.put("totalEvaluatedWithChestXRayBeforePeriod",  totalEvaluatedWithChestXRayBeforePeriod+"");
        return new JSONObject(dataMap).toString();

    }
	
	//total of pts confirmed tb
	public String getTotalChestXRayDiagnosedTB(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalDiagnosedWithChestXRayThisPeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, false, "chestxray", 1);
            int totalDiagnosedWithChestXRayBeforePeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, true, "chestxray", 1);
            //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalDiagnosedWithChestXRayThisPeriod",  totalDiagnosedWithChestXRayThisPeriod+"");
            dataMap.put("totalDiagnosedWithChestXRayBeforePeriod",  totalDiagnosedWithChestXRayBeforePeriod+"");
            return new JSONObject(dataMap).toString();
		
    }
	
	//total of pts confirmed tb
	public String getTotalCultureEvaluatedTB(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
        //Database.initConnection();

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

        int totalEvaluatedWithCultureThisPeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, false, "culture", 0);
        int totalEvaluatedWithCultureBeforePeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, true, "culture", 0);
        //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("totalEvaluatedWithCultureThisPeriod",  totalEvaluatedWithCultureThisPeriod+"");
        dataMap.put("totalEvaluatedWithCultureBeforePeriod",  totalEvaluatedWithCultureBeforePeriod+"");
        return new JSONObject(dataMap).toString();

    }
	
	//total of pts confirmed tb
	public String getTotalCultureDiagnosedTB(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
        //Database.initConnection();

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

        int totalDiagnosedWithCultureThisPeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, false, "culture", 1);
        int totalDiagnosedWithCultureBeforePeriod = labDao.getTotalPtsDiagnoseddForTb(startDate, endDate, true, "culture", 1);
        //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("totalDiagnosedWithCultureThisPeriod",  totalDiagnosedWithCultureThisPeriod+"");
        dataMap.put("totalDiagnosedWithCultureBeforePeriod",  totalDiagnosedWithCultureBeforePeriod+"");
        return new JSONObject(dataMap).toString();

    }
	
	//total of pts confirmed tb
	public String getTotalOnTBTreatment(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
        //Database.initConnection();

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

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

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

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

        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

        int totalStartedIPTThisPeriod = labDao.getTotalPtsStartedOnIPT(startDate, endDate, false);
        int totalStartedIPTBeforePeriod = labDao.getTotalPtsStartedOnIPT(startDate, endDate, true);
        //int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("totalStartedIPTThisPeriod",  totalStartedIPTThisPeriod+"");
        dataMap.put("totalStartedIPTBeforePeriod",  totalStartedIPTBeforePeriod+"");
        return new JSONObject(dataMap).toString();

    }
}
