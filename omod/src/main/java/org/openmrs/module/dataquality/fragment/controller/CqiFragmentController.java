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
import org.openmrs.module.dataquality.api.dao.PharmacyDao;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 * @author lordmaul
 */
public class CqiFragmentController {
	
	//HTSService dataQualityService = Context.getService(HTSService.class);
	HTSDao htsDao = new HTSDao();
	
	ARTDao artDao = new ARTDao();
	
	LabDao labDao = new LabDao();
	
	PharmacyDao pharmacyDao = new PharmacyDao();
	
	public void controller(FragmentModel model, @SpringBean("userService") UserService service) {
		model.addAttribute("testing", "test");
		Database.initConnection();
	}
	
	/****************************************************** ajax methods ****************************************************************/
	
	//Percentage of Adult clients tested HIV positive
	public String getAdultClientsTestedPositive(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                //Database.initConnection();
               
                System.out.println("start date time"+startDateTime);
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		
		int adultsTested = htsDao.getAllAdultPatientsTestedForHIV(startDate, endDate);
		//int adultsTestedPositive = htsDao.getAllAdultPatientsTestedPositieForHIV(startDate, endDate);
                
                
                System.out.println("adultsTested: "+adultsTested);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("totalAdultsTested",  adultsTested+"");
		//dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
		return new JSONObject(dataMap).toString();
		
	}
	
	//Percentage of ped clients tested HIV positive
	public String getPedClientsTestedPositive(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                //Database.initConnection();
               
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		
		int pedsTested = htsDao.getAllPedPatientsTestedForHIV(startDate, endDate);
		//int pedsTestedPositive = htsDao.getAllPedPatientsTestedPositiveForHIV(startDate, endDate);
                
                
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("totalPedsTested",  pedsTested+"");
		//dataMap.put("totalPedsTestedPositive",  pedsTestedPositive+"");
		return new JSONObject(dataMap).toString();
		
	}
	
	//Percentage of ped clients tested HIV positive
	public String getAdultsStartedOnArt(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int adultsStartedOnArt = artDao.getAdultsStartedOnArt(startDate, endDate);


            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("adultsStartedOnArt",  adultsStartedOnArt+"");
            return new JSONObject(dataMap).toString();	
	}
	
	//percentage of peds started on art
	public String getPedsStartedOnArt(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int pedsStartedOnArt = artDao.getPedsStartedOnArt(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("pedsStartedOnArt",  pedsStartedOnArt+"");
            return new JSONObject(dataMap).toString();	
	}
	
	//percentage of peds started on art
	public String getTotalPatientsReceivingART(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPatientsReceiving = artDao.getNoPatientsReceivingARV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPatientsReceiving",  totalPatientsReceiving+"");
            return new JSONObject(dataMap).toString();	
	}
	
	//percentage of peds started on art
	public String getARTPtsWithVLRequest6Months(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPtsVL6Months = artDao.getARVPtsWithVLRequest6Months(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPtsVL6Months",  totalPtsVL6Months+"");
            return new JSONObject(dataMap).toString();	
	}
	
	//percentage of peds started on art
	public String getARTPtsWithVLRequest7Months(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPtsVL7Months = artDao.getARVPtsWithVLRequest7Months(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPtsVL7Months",  totalPtsVL7Months+"");
            return new JSONObject(dataMap).toString();	
	}
	
	//percentage of pts with first suppresset vl
	public String getPtsWithSuppressedFirstVl(HttpServletRequest request){
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();
            System.out.println("starting to");
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPtsWithSuppressedVl = labDao.getTotalPtsWithSuppressedFirstVl(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPtsWithSuppressedVl",  totalPtsWithSuppressedVl+"");
            return new JSONObject(dataMap).toString();	
	}
	
	//percentage of ped pts with first suppresset vl
	public String getPedPtsWithSuppressedFirstVl(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();
            System.out.println("starting to");
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPtsWithSuppressedVl = labDao.getTotalPedPtsWithSuppressedFirstVl(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPedPtsWithSuppressedVl",  totalPtsWithSuppressedVl+"");
            return new JSONObject(dataMap).toString();	
	}
	
	public String getTotalAdultPLHIVOfferedIndexTesting(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalAdultPlhivOfferedIndexTesting = htsDao.getTotalAdultsOfferedIndexTesting(startDate, endDate);
            int totalAdultPlhiv = htsDao.getAllAdultPatientsTestedPositiveForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalAdultPlhivOfferedIndexTesting",  totalAdultPlhivOfferedIndexTesting+"");
            dataMap.put("totalAdultsTestedPositive",  totalAdultPlhiv+"");
            return new JSONObject(dataMap).toString();	
	}
	
	public String getTotalPedPLHIVOfferedIndexTesting(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPedPlhivOfferedIndexTesting = htsDao.getTotalPedsOfferedIndexTesting(startDate, endDate);
            int totalPedPlhiv = htsDao.getAllPedPatientsTestedPositiveForHIV(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPedPlhivOfferedIndexTesting",  totalPedPlhivOfferedIndexTesting+"");
            dataMap.put("totalPedsTestedPositive",  totalPedPlhiv+"");
            return new JSONObject(dataMap).toString();	
	}
	
	public String getTotalPtsWithMissedAppointment(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPtsWithMissedAppointment = pharmacyDao.getPtsWithMissedAppointment(startDate, endDate);
            int totalPtsWithAppointment = pharmacyDao.getPtsWithAppointment(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPtsWithMissedAppointment",  totalPtsWithMissedAppointment+"");
            dataMap.put("totalPtsWithAppointment",  totalPtsWithAppointment+"");
            return new JSONObject(dataMap).toString();	
	}
	
	public String getTotalPedPtsWithMissedAppointment(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");

            int totalPedPtsWithMissedAppointment = pharmacyDao.getPedPtsWithAppointment(startDate, endDate);
            int totalPedPtsWithAppointment = pharmacyDao.getPedPtsWithAppointment(startDate, endDate);

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("totalPedPtsWithMissedAppointment",  totalPedPtsWithMissedAppointment+"");
            dataMap.put("totalPedPtsWithAppointment",  totalPedPtsWithAppointment+"");
            return new JSONObject(dataMap).toString();	
	}
}
