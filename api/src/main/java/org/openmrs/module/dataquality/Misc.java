/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;

/**
 * @author lordmaul
 */
public class Misc {
	
	public static String getEducationalStatus(int obsConceptId) {
		String educationalStatus = "";
		switch (obsConceptId) {
			case 1107:
				educationalStatus = "No Education";
				break;
			case 1713:
				educationalStatus = "Primary Education";
				break;
			case 1714:
				educationalStatus = "Secondary Education";
				break;
			case 160292:
				educationalStatus = "Tetiary Education";
				break;
			case 5622:
				educationalStatus = "Other";
				break;
		
		}
		
		return educationalStatus;
	}
	
	public static JSONObject getQuartersBetweenDates(String sDate, String eDate) {
		JSONObject quarters = new JSONObject();
		//
		DateTime startDate = new DateTime(sDate);
		DateTime endDate = new DateTime(eDate);
		DateTime tempDate = startDate;
		try {
			do {
				
				int year = tempDate.getYear();
				int month = tempDate.getMonthOfYear();
				int quarter = Misc.getFiscalQuarter(month);
				if (quarter == 1) {
					year++;
				}
				
				quarters.put("fy" + year + "q" + quarter, 0 + "");
				//add a month to temp date
				tempDate = tempDate.plusMonths(1);
				
			} while (tempDate.isBefore(endDate));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return quarters;
	}
	
	public static int getFiscalQuarter(int month) {
		if (month >= 1 && month <= 3) {
			return 2;
		} else if (month >= 4 && month <= 6) {
			return 3;
		} else if (month >= 7 && month <= 9) {
			return 4;
		} else if (month >= 10 && month <= 12) {
			return 1;
		}
		
		return 0;
	}
	
	public static int getCalendarQuarter(int month) {
		if (month >= 1 && month <= 3) {
			return 1;
		} else if (month >= 4 && month <= 6) {
			return 2;
		} else if (month >= 7 && month <= 9) {
			return 3;
		} else if (month >= 10 && month <= 12) {
			return 4;
		}
		
		return 0;
	}
	
	public static String getOccupationalStatus(int obsConceptId) {
		String occupationalStatus = "";
		switch (obsConceptId) {
			case 1542:
				occupationalStatus = "No Unemployed";
				break;
			case 1540:
				occupationalStatus = "Employed";
				break;
			case 159465:
				occupationalStatus = "Student";
				break;
			case 159461:
				occupationalStatus = "Retired";
				break;
			case 1067:
				occupationalStatus = "Unknown";
				break;
		}
		return occupationalStatus;
	}
	
	public static String getCivilStatus(int obsConceptId) {
		String civilStatus = "";
		switch (obsConceptId) {
			case 1057:
				civilStatus = "	Never married ";
				break;
			case 5555:
				civilStatus = "	Married";
				break;
			case 1058:
				civilStatus = "Divorced";
				break;
			case 1056:
				civilStatus = "Separated";
				break;
			case 1060:
				civilStatus = "Living with partner";
				break;
			case 1059:
				civilStatus = "Widowed";
				break;
		}
		
		return civilStatus;
	}
	
	public static String getHIVStatus(int obsConceptId) {
		String hivStatus = "";
		switch (obsConceptId) {
			case 703:
				hivStatus = "Positive";
				break;
			case 664:
				hivStatus = "Negative";
				break;
		
		}
		return hivStatus;
	}
	
	public static String getYesNo(int obsConceptId) {
		String yesNo = "";
		switch (obsConceptId) {
			case 1066:
				yesNo = "No";
				break;
			case 1065:
				yesNo = "Yes";
				break;
		}
		
		return yesNo;
	}
	
	public static String getMuac(int obsConceptId) {
		String muac = "";
		switch (obsConceptId) {
			case 165932:
				muac = "Overweight";
				break;
			case 165933:
				muac = "Underweight";
				break;
			case 165934:
				muac = "Normal";
				break;
		}
		
		return muac;
	}
	
	public static String getTBStatus(int obsConceptId) {
		String tbStatus = "";
		switch (obsConceptId) {
		
			case 1660:
				tbStatus = "No signs";
				break;
			case 142177:
				tbStatus = "Presumptive TB";
				break;
			case 166042:
				tbStatus = "IPT";
				break;
			case 1661:
				tbStatus = "Confirmed TB";
				break;
			case 1662:
				tbStatus = "TB Treatment";
				break;
		}
		
		return tbStatus;
	}
	
	//get testing modalities
	public static String getSetting(int obsConceptId, String otherModality) {
		String settingStatus = "";
		switch (obsConceptId) {
		
			case 160539:
				settingStatus = "Voluntary counseling and testing program ";
				break;
			case 160529:
				settingStatus = "Tuberculosis Visit";
				break;
			case 160546:
				settingStatus = "Sexually transmitted infection program/clinic";
				break;
			case 5271:
				settingStatus = "FAMILY PLANNING";
				break;
			case 160542:
				settingStatus = "Outpatient department";
				break;
			case 161629:
				settingStatus = "Observation ward";
				break;
			case 160545:
				settingStatus = "Outreach program ";
				break;
			case 165838:
				settingStatus = "Standalone HTS";
				break;
			case 166135:
				settingStatus = "Community ART";
				break;
			
			case 5622:
				settingStatus = " Other - " + otherModality;
				break;
		}
		
		return settingStatus;
	}
	
	public static String getFunctionalStatus(int obsConceptId) {
		String functionalStatus = "";
		switch (obsConceptId) {
			case 159468:
				functionalStatus = "Physically able to work";
				break;
			case 162752:
				functionalStatus = "Bedridden";
				break;
			case 160026:
				functionalStatus = "Patient ambulatory";
				break;
		}
		return functionalStatus;
	}
	
	public static String getWHOStageStatus(int obsConceptId) {
		String whoStage = "";
		switch (obsConceptId) {
			case 1204:
				whoStage = "1";
				break;
			case 1205:
				whoStage = "2";
				break;
			case 1206:
				whoStage = "3";
				break;
			case 1207:
				whoStage = "4";
				break;
		}
		return whoStage;
	}
	
	public static String isVLOrdered(int value) {
		if (value == 2) {
			return "True";
		} else {
			return "False";
		}
	}
	
	public static String getAge(String dobString, String relativeTo) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		
		int age = 0;
		try {
			Date dobDate = dateFormat.parse(dobString);
			Date relateToDate = dateFormat.parse(relativeTo);
			Calendar now = Calendar.getInstance();
			Calendar dob = Calendar.getInstance();
			dob.setTime(dobDate);
			now.setTime(relateToDate);
			if (dob.after(now)) {
				return "Invalid age";
			}
			int year1 = now.get(Calendar.YEAR);
			int year2 = dob.get(Calendar.YEAR);
			age = year1 - year2;
			int month1 = now.get(Calendar.MONTH);
			int month2 = dob.get(Calendar.MONTH);
			if (month2 > month1) {
				age--;
			} else if (month1 == month2) {
				int day1 = now.get(Calendar.DAY_OF_MONTH);
				int day2 = dob.get(Calendar.DAY_OF_MONTH);
				if (day2 > day1) {
					age--;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return age + "";
	}
	
	public static String getAgeRange(String age) {
		try {
			int ageVal = Integer.parseInt(age);
			String ageRange = "";
			if (ageVal <= 0) {
				ageRange = "<1";
			} else if (ageVal > 0 && ageVal < 5) {
				ageRange = "1-4";
			} else if (ageVal >= 5 && ageVal < 10) {
				ageRange = "5-9";
			} else if (ageVal >= 10 && ageVal < 15) {
				ageRange = "10-14";
			} else if (ageVal >= 15 && ageVal < 20) {
				ageRange = "15-19";
			} else if (ageVal >= 20 && ageVal < 25) {
				ageRange = "20-24";
			} else if (ageVal >= 25 && ageVal < 30) {
				ageRange = "25-29";
			} else if (ageVal >= 30 && ageVal < 35) {
				ageRange = "30-34";
			} else if (ageVal >= 35 && ageVal < 40) {
				ageRange = "35-39";
			} else if (ageVal >= 40 && ageVal < 45) {
				ageRange = "40-44";
			} else if (ageVal >= 45 && ageVal < 50) {
				ageRange = "45-49";
			} else if (ageVal >= 50) {
				ageRange = "50+";
			}
			
			return ageRange;
		}
		catch (Exception e) {
			return "";
		}
		
	}
	
	public static String getGenexpertStatus(int obsConceptId) {
		String genexpertStatus = "";
		switch (obsConceptId) {
			case 164096:
				genexpertStatus = "PTB Negative";
				break;
			case 165974:
				genexpertStatus = "PTB positive MTB not detected";
				break;
			case 165973:
				genexpertStatus = "PTB positive MTB detected";
				break;
		}
		return genexpertStatus;
	}
	
	public static String getChestXrayStatus(int obsConceptId) {
		String chestXrayStatus = "";
		switch (obsConceptId) {
			case 165971:
				chestXrayStatus = "Suggestive";
				break;
			case 165970:
				chestXrayStatus = "Non Suggestive";
				break;
		}
		return chestXrayStatus;
	}
	
	public static String getRegimenLine(int obsConceptId) {
		String regimenLine = "";
		switch (obsConceptId) {
			case 164507:
				regimenLine = "Child 1st line ARV regimen";
				break;
			case 164514:
				regimenLine = " Child 2nd line ARV regimen";
				break;
			case 165703:
				regimenLine = "	Child 3rd Line ARV Regimens";
				break;
			case 164506:
				regimenLine = "Adult 1st line ARV regimen";
				break;
			case 164513:
				regimenLine = "	Adult 2nd line ARV regimen";
				break;
			case 165702:
				regimenLine = " Adult 3rd Line ARV Regimens";
				break;
		}
		
		return regimenLine;
	}
	
	/* public static String getRegimen(int obsConceptId)
	 {
	     String regimen = "";

	     Map<Integer, String> regimenMap = new HashMap<>();
	     regimenMap.put(817, "ABC-3TC-AZT");
	     regimenMap.put(165526, "ABC-3TC-DDI");
	     regimenMap.put(165691, "ABC-3TC-DTG");
	     regimenMap.put(162563, "ABC-3TC-EFV");
	     regimenMap.put(162199, "ABC-3TC-NVP");
	     regimenMap.put(166181, "ABC-3TC-TDF");
	     regimenMap.put(165692, "ABC-FTC-DTG");
	     regimenMap.put(166179, "ABC-FTC-EFV");
	     regimenMap.put(165690, "ABC-FTC-NVP");
	     regimenMap.put(166187, "AZT-3TC-DTG");
	     regimenMap.put(160124, "AZT-3TC-EFV");
	     regimenMap.put(1652, "AZT-3TC-NVP");
	     regimenMap.put(166185, "AZT-TDF-NVP");
	     regimenMap.put(166183, "D4T-3TC-ABC");
	     regimenMap.put(160104, "D4T-3TC-EFV");
	     regimenMap.put(166186, "DDI-3TC-EFV ");
	     regimenMap.put(165525, "DDI-3TC-NVP");
	     regimenMap.put(165522, "TDF-3TC-AZT");
	     regimenMap.put(165681, "TDF-3TC-DTG");
	     regimenMap.put(164505, "TDF-3TC-EFV");
	     regimenMap.put(162565, "TDF-3TC-NVP");
	     regimenMap.put(165682, "TDF-FTC-DTG");
	     regimenMap.put(104565, "TDF-FTC-EFV");
	     regimenMap.put(164854, "TDF-FTC-NVP");
	     regimenMap.put(817, "ABC-3TC-AZT");
	     regimenMap.put(817, "ABC-3TC-AZT");
	     regimenMap.put(817, "ABC-3TC-AZT");
	     
	     
	     return regimen;
	 }*/
}
