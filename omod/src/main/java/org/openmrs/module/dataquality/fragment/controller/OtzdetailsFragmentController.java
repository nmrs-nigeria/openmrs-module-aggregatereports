/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.fragment.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.openmrs.module.dataquality.Misc;
import org.openmrs.module.dataquality.OTZPatient;
import org.openmrs.module.dataquality.api.dao.ARTDao;
import org.openmrs.module.dataquality.api.dao.HTSDao;
import org.openmrs.module.dataquality.api.dao.LabDao;
import org.openmrs.module.dataquality.api.dao.OTZDao;
import org.openmrs.module.dataquality.api.dao.PharmacyDao;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 * @author lordmaul
 */
public class OtzdetailsFragmentController {
	
	public void controller(FragmentModel model, HttpServletRequest request) {
		int subSet = Integer.parseInt(request.getParameter("subset"));
		int type = Integer.parseInt(request.getParameter("type"));
		
		DateTime startDateTime = new DateTime();
		DateTime endDateTime = new DateTime();
		
		if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
			startDateTime = new DateTime(request.getParameter("startDate"));
			endDateTime = new DateTime(request.getParameter("endDate"));
		} else {
			startDateTime = new DateTime("1990-01-01");
			endDateTime = new DateTime(new Date());
		}
		
		DateTime sixMonthsAgo = endDateTime.minusMonths(6);
		//Database.initConnection();
		
		String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
		
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		
		OTZDao otzDao = new OTZDao();
		
		model.addAttribute("subset", subSet);
		
		if (subSet == 1) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 2) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithScheduledPickup6MonthsBefore(startDate, endDate,
			    sixMonths);
			model.addAttribute("title",
			    "# of OTZ members with scheduled drug pick-up appointment in the last six months prior to enrollment on OTZ ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of OTZ members with scheduled drug pick-up appointment in the last six months prior to enrollment on OTZ ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 3) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore(startDate, endDate,
			    sixMonths);
			model.addAttribute("title",
			    "# of OTZ members who kept their drug pick-up appointment in the last six months prior to enrolment on OTZ ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of OTZ members who kept their drug pick-up appointment in the last six months prior to enrolment on OTZ ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 4) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithGoodAdhScore6MonthsBefore(startDate, endDate,
			    sixMonths);
			model.addAttribute("title",
			    "# of OTZ members with good drug adherence score in the last six months prior to enrolment on OTZ ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of OTZ members with good drug adherence score in the last six months prior to enrolment on OTZ ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 5) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBefore(startDate, endDate, sixMonths);
			model.addAttribute("title",
			    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ  ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ  ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 6) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBelow200(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result less than 200 c/ml  ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result less than 200 c/ml  ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 7) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result between 200 to less than 1000 c/ml  ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result between 200 to less than 1000 c/ml  ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 8) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000(startDate,
			    endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result greater than or equal to 1000 c/ml th ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result greater than or equal to 1000 c/ml  ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 9) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBefore(startDate, endDate, sixMonths);
			model.addAttribute("title",
			    " # of AYPLHIV in OTZ with VL results at baseline within the last 6 months at enrollment into OTZ ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    " # of AYPLHIV in OTZ with VL results at baseline within the last 6 months at enrollment into OTZ ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 10) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBelow200(startDate, endDate,
			    sixMonths);
			model.addAttribute("title",
			    "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrollment into OTZ and VL less than 200 c/ml( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrollment into OTZ and VL less than 200 c/ml ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 11) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL result is between 200 to less than 1000 c/ml( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL result is between 200 to less than 1000 c/ml("
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 12) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrollment into OTZ and VL greater than or equal to 1000 c/ml ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrollment into OTZ and VL greater than or equal to 1000 c/ml ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 13) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEligibleForMonthZeroVL(startDate, endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ without baseline VL results or with baseline VL result less than 1000 c/ml and eligible for month zero VL sample collection  ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ without baseline VL results or with baseline VL result less than 1000 c/ml and eligible for month zero VL sample collection  ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 14) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ enrolled in the cohort month and eligible for month zero VL sample collection whose VL samples were taken (at month zero) ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ enrolled in the cohort month and eligible for month zero VL sample collection whose VL samples were taken (at month zero)( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 15) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is less than 200 c/ml  ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is less than 200 c/ml ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 16) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is between 200 to less than 1000 c/ml  ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is between 200 to less than 1000 c/ml   ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 17) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is greater than or equal to 1000 c/ml ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is greater than or equal to 1000 c/ml  ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 18) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithScheduledPickupAfter(startDate, endDate, sixMonths);
			model.addAttribute("title",
			    "# of OTZ members with scheduled drug pick-up appointment in the follow up period ( " + startDate + " and "
			            + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of OTZ members with scheduled drug pick-up appointment in the follow up period ( " + startDate
				            + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 19) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWhoKeptScheduledPickupAfter(startDate, endDate, sixMonths);
			model.addAttribute("title",
			    "# of OTZ members who kept their drug pick-up appointment in the follow up period ( " + startDate + " and "
			            + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of OTZ members who kept their drug pick-up appointment in the follow up period ( " + startDate
				            + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 20) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithGoodAdhScoreAfter(startDate, endDate, sixMonths);
			model.addAttribute("title", "# of OTZ members with good drug adherence score in the follow up period ( "
			        + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of OTZ members with good drug adherence score in the follow up period ( "
				        + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 21) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVL(startDate, endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ who were eligible for routine VL test during the follow up period i.e. No VL result for the 6-month period prior to the beginning of the reporting period ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ who were eligible for routine VL test during the follow up period i.e. No VL result for the 6-month period prior to the beginning of the reporting period ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 22) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao
			        .getTotalEnrolledEligibleForVLWithSampleTaken(startDate, endDate, sixMonths);
			model.addAttribute("title", "# of AYPLHIV in OTZ whose samples were taken for routine VL test ( " + startDate
			        + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV in OTZ whose samples were taken for routine VL test( " + startDate
				        + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 23) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResult(startDate, endDate,
			    sixMonths);
			model.addAttribute("title",
			    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 24) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultBelow200(startDate,
			    endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period less than 200 copies/ml ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period less than 200 copies/ml ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 25) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove200Below1000(
			    startDate, endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 200 to less than 1000 copies/ml ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 200 to less than 1000 copies/ml ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 26) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove1000(startDate,
			    endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period greater than or equal to 1000 copies/ml  ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period greater than or equal to 1000 copies/ml  ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 27) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12Months(startDate, endDate, sixMonths);
			model.addAttribute("title",
			    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months ( " + startDate
			            + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 28) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultBelow200(startDate, endDate,
			    sixMonths);
			model.addAttribute("title",
			    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months less than 200 copies/ml ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute("title",
				    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months less than 200 copies/ml( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 29) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove200Below1000(startDate,
			    endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months is between 200 to less than 1000 copies/ml ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months is between 200 to less than 1000 copies/ml ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 30) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000(startDate, endDate,
			    sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 31) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000CompletedEAC(startDate,
			    endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml and completed EAC ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml and completed EAC ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 32) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl(startDate,
			    endDate, sixMonths);
			model.addAttribute(
			    "title",
			    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml who have repeat VL result ( "
			            + startDate + " and " + endDate + ")");
			if (type == 2) {
				model.addAttribute(
				    "title",
				    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml who have repeat VL result ( "
				            + startDate + " and " + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 33) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 34) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 35) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 36) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 37) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 37) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 39) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 40) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 41) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 42) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 43) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 44) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 45) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		if (subSet == 46) {//Percentage of new adult PLHIV offered index testing
			//lets get the data
			List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
			model.addAttribute("title", "# of AYPLHIV enrolled in OTZ in the cohort month ( " + startDate + " and "
			        + endDate + ")");
			if (type == 2) {
				model.addAttribute("title", "# of AYPLHIV not enrolled in OTZ in the cohort month ( " + startDate + " and "
				        + endDate + ")");
			}
			model.addAttribute("patients", allPatients);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("Misc", Misc.class);
		}
		
	}
}
