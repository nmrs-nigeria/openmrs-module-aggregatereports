import java.util.Date;
//import org.openmrs.module.dataquality.api.CohortBuilder;
//import org.openmrs.module.dataquality.api.dao.Database;
import org.joda.time.DateTime;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author lordmaul
 */
public class MainTest {
	
	public static void main(String[] args) {
		
		DateTime endDateTime = new DateTime(new Date());
		DateTime startDateTime = endDateTime.minusMonths(10006);
		
		//System.out.println(CohortBuilder.getPtsWithClinicalVisitDocWeightCount(
		//startDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm"), endDateTime.toString("yyyy'-'MM'-'dd' 'HH':'mm")));
		//System.out.println(CohortBuilder.getActivePatientDocumentedEducationStatusCount());
		
	}
	
}
