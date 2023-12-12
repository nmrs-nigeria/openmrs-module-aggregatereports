
<% 
int i=1;


def title = config.title

%>
<div class="container">
    
    <h2>OTZ Report</h2>
   
    <br/>
    
      <br/>
    <table id="tab2export" class="table dataTable">
        <thead>
            
             <tr>
                <th  style="text-align:center" align="middle">Implementing Partner</th>
                <th  style="text-align:center" align="middle">State</th>
                <th  style="text-align:center" align="middle">LGA</th>
                <th  style="text-align:center" align="middle">Facility name</th>
                <th  style="text-align:center" align="middle">Cohort Month (MM/YYYY)</th>
                <th  style="text-align:center" align="middle">Sex</th>
                <th  style="text-align:center" align="middle">Age Band</th>
                <th># of AYPLHIV currently on ART in the supported facility</th>
                <th># of AYPLHIV enrolled in OTZ in the cohort month</th>
                <th># of OTZ members with scheduled drug pick-up appointment in the last six months prior to enrolment on OTZ</th>
                <th># of OTZ members who kept their drug pick-up appointment in the last six months prior to enrolment on OTZ</th>
                <th># of OTZ members with good drug adherence score in the last six months prior to enrolment on OTZ</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result less than 200 c/ml</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result between 200 to less than 1000 c/ml</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result greater than or equal to 1000 c/ml</th>
<th># of AYPLHIV in OTZ with VL results at baseline within the last 6 months at enrolment into OTZ</th>
<th># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL less than 200 c/ml</th>
<th># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL result is between 200 to less than 1000 c/ml</th>
<th># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL greater than or equal to 1000 c/ml</th>
<th># of AYPLHIV in OTZ without baseline VL results or with baseline VL result less than 1000 c/ml and eligible for month zero VL sample collection</th>
<th># of AYPLHIV in OTZ enrolled in the cohort month and eligible for month zero VL sample collection whose VL samples were taken (at month zero)</th>
<th># of AYPLHIV in OTZ with baseline VL results less than 1000 c/ml whose VL result for sample collected at month zero is less than 200 c/ml</th>
<th># of AYPLHIV in OTZ with baseline VL results less 1000 c/ml whose VL result for sample collected at month zero is betwen 200 to less than 1000 c/ml</th>
<th># of AYPLHIV in OTZ with baseline VL results less 1000 c/ml  whose VL result for sample collected at month zero is greater than or equal to 1000 c/ml</th>
<th>Follow up</th>
<th># of OTZ members with scheduled drug pick-up appointment in the follow up period</th>
<th># of OTZ members who kept their drug pick-up appointment in the follow up period</th>
<th># of OTZ members with good drug adherence score in the follow up period</th>
<th># of AYPLHIV in OTZ who were eligible for routine VL test during the follow up period i.e. No VL result for the 6-month period prior to the beginning of the reporting period</th>
<th># of AYPLHIV in OTZ whose samples were taken for routine VL test</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period less than 200 copies/ml</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 200 to less than 1000 copies/ml</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period greater than or equal to 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months</th>
<th># of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months less than 200 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months is between 200 to less than 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml and completed EAC</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml who have repeat VL result</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is less than 200 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is between 200 to less than 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is greater than or equal to 1000 copies/ml</th>
<th># switched to second line ART</th>
<th># switched to third line ART</th>
<th># of OTZ members who have completed the 7 AYP modules</th>
<th># transferred out during the follow up period</th>
<th># Lost to follow up during the follow up period</th>
<th># reported dead during the follow up period</th>
<th># that opted out of OTZ during the follow up period</th>
<th># aged 20-24 years and transitioned to adult care during the follow up  period</th>
<th># that exited OTZ during the follow up period</th>
</tr>
            
        </thead>
        <tbody>
            <tr>
                <td class="loadingView num4"  id="parner_NameM10To14"></td>
                <td class="loadingView num4"  id="facilityStateM10To14"></td>
                <td class="loadingView num4"  id="facilityLGAM10To14"></td>
                <td class="loadingView num4"  id="facilityNameM10To14"></td>
                <td class="loadingView num4"  id="monthYearrM10To14"></td>
                <td  style="text-align:center" align="middle">Male</td>
                <td  style="text-align:center" align="middle">10-14 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000M10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000M10To14">-</td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUM10To14">-</td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUM10To14">-</td>
                <td class="loadingView num4"  id="goodAdhFUM10To14">-</td>
                <td class="loadingView num4"  id="eligibleFUM10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenM10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResultM10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200M10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000M10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResult1000M10To14">-</td>
                <td class="loadingView num4"  id="resultPast12MtM10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200M10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200To100M10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000M10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACM10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatM10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200M10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000M10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000M10To14">-</td>
                <td class="loadingView num4"  id="switchTo2ndM10To14">-</td>
                <td class="loadingView num4"  id="switchTo3rdM10To14">-</td>
                <td class="loadingView num4"  id="completed7M10To14">-</td>
                <td class="loadingView num4"  id="transferredOutM10To14">-</td>
                <td class="loadingView num4"  id="ltfuM10To14">-</td>
                <td class="loadingView num4"  id="deadM10To14">-</td>
                <td class="loadingView num4"  id="optedOutM10To14">-</td>
                <td class="loadingView num4"  id="transitionedM10To14">-</td>
                <td class="loadingView num4"  id="exitedM10To14">-</td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameM15To19"></td>
                <td class="loadingView num4"  id="facilityStateM15To19"></td>
                <td class="loadingView num4"  id="facilityLGAM15To19"></td>
                <td class="loadingView num4"  id="facilityNameM15To19"></td>
                <td class="loadingView num4"  id="monthYearrM15To19"></td>
                <td  style="text-align:center" align="middle">Male</td>
                <td  style="text-align:center" align="middle">15-19 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000M15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000M15To19">-</td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUM15To19">-</td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUM15To19">-</td>
                <td class="loadingView num4"  id="goodAdhFUM15To19">-</td>
                <td class="loadingView num4"  id="eligibleFUM15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenM15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResultM15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200M15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000M15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResult1000M15To19">-</td>
                <td class="loadingView num4"  id="resultPast12MtM15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200M15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200To100M15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000M15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACM15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatM15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200M15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000M15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000M15To19">-</td>
                <td class="loadingView num4"  id="switchTo2ndM15To19">-</td>
                <td class="loadingView num4"  id="switchTo3rdM15To19">-</td>
                <td class="loadingView num4"  id="completed7M15To19">-</td>
                <td class="loadingView num4"  id="transferredOutM15To19">-</td>
                <td class="loadingView num4"  id="ltfuM15To19">-</td>
                <td class="loadingView num4"  id="deadM15To19">-</td>
                <td class="loadingView num4"  id="optedOutM15To19">-</td>
                <td class="loadingView num4"  id="transitionedM15To19">-</td>
                <td class="loadingView num4"  id="exitedM15To19">-</td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameM20To24"></td>
                <td class="loadingView num4"  id="facilityStateM20To24"></td>
                <td class="loadingView num4"  id="facilityLGAM20To24"></td>
                <td class="loadingView num4"  id="facilityNameM20To24"></td>
                <td class="loadingView num4"  id="monthYearrM20To24"></td>
                <td  style="text-align:center" align="middle">Male</td>
                <td  style="text-align:center" align="middle">20-24 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000M20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000M20To24">-</td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUM20To24">-</td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUM20To24">-</td>
                <td class="loadingView num4"  id="goodAdhFUM20To24">-</td>
                <td class="loadingView num4"  id="eligibleFUM20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenM20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResultM20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200M20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000M20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult1000M20To24">-</td>
                <td class="loadingView num4"  id="resultPast12MtM20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200M20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200To100M20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000M20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACM20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatM20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200M20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000M20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000M20To24">-</td>
                <td class="loadingView num4"  id="switchTo2ndM20To24">-</td>
                <td class="loadingView num4"  id="switchTo3rdM20To24">-</td>
                <td class="loadingView num4"  id="completed7M20To24">-</td>
                <td class="loadingView num4"  id="transferredOutM20To24">-</td>
                <td class="loadingView num4"  id="ltfuM20To24">-</td>
                <td class="loadingView num4"  id="deadM20To24">-</td>
                <td class="loadingView num4"  id="optedOutM20To24">-</td>
                <td class="loadingView num4"  id="transitionedM20To24">-</td>
                <td class="loadingView num4"  id="exitedM20To24">-</td>
            </tr>
            
            <tr>
                <td class="loadingView num4"  id="parner_NameF10To14"></td>
                <td class="loadingView num4"  id="facilityStateF10To14"></td>
                <td class="loadingView num4"  id="facilityLGAF10To14"></td>
                <td class="loadingView num4"  id="facilityNameF10To14"></td>
                <td class="loadingView num4"  id="monthYearrF10To14"></td>
                <td  style="text-align:center" align="middle">Female</td>
                <td  style="text-align:center" align="middle">10-14 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F10To14">-</td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUF10To14">-</td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF10To14">-</td>
                <td class="loadingView num4"  id="goodAdhFUF10To14">-</td>
                <td class="loadingView num4"  id="eligibleFUF10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenF10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResultF10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200F10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F10To14">-</td>
                <td class="loadingView num4"  id="samplesTakenResult1000F10To14">-</td>
                <td class="loadingView num4"  id="resultPast12MtF10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200F10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000F10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F10To14">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F10To14">-</td>
                <td class="loadingView num4"  id="switchTo2ndF10To14">-</td>
                <td class="loadingView num4"  id="switchTo3rdF10To14">-</td>
                <td class="loadingView num4"  id="completed7F10To14">-</td>
                <td class="loadingView num4"  id="transferredOutF10To14">-</td>
                <td class="loadingView num4"  id="ltfuF10To14">-</td>
                <td class="loadingView num4"  id="deadF10To14">-</td>
                <td class="loadingView num4"  id="optedOutF10To14">-</td>
                <td class="loadingView num4"  id="transitionedF10To14">-</td>
                <td class="loadingView num4"  id="exitedF10To14">-</td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameF15To19"></td>
                <td class="loadingView num4"  id="facilityStateF15To19"></td>
                <td class="loadingView num4"  id="facilityLGAF15To19"></td>
                <td class="loadingView num4"  id="facilityNameF15To19"></td>
                <td class="loadingView num4"  id="monthYearrF15To19"></td>
                <td  style="text-align:center" align="middle">Female</td>
                <td  style="text-align:center" align="middle">15-19 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F15To19">-</td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUF15To19">-</td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF15To19">-</td>
                <td class="loadingView num4"  id="goodAdhFUF15To19">-</td>
                <td class="loadingView num4"  id="eligibleFUF15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenF15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResultF15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200F15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F15To19">-</td>
                <td class="loadingView num4"  id="samplesTakenResult1000F15To19">-</td>
                <td class="loadingView num4"  id="resultPast12MtF15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200F15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000F15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F15To19">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F15To19">-</td>
                <td class="loadingView num4"  id="switchTo2ndF15To19">-</td>
                <td class="loadingView num4"  id="switchTo3rdF15To19">-</td>
                <td class="loadingView num4"  id="completed7F15To19">-</td>
                <td class="loadingView num4"  id="transferredOutF15To19">-</td>
                <td class="loadingView num4"  id="ltfuF15To19">-</td>
                <td class="loadingView num4"  id="deadF15To19">-</td>
                <td class="loadingView num4"  id="optedOutF15To19">-</td>
                <td class="loadingView num4"  id="transitionedF15To19">-</td>
                <td class="loadingView num4"  id="exitedF15To19">-</td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameF20To24"></td>
                <td class="loadingView num4"  id="facilityStateF20To24"></td>
                <td class="loadingView num4"  id="facilityLGAF20To24"></td>
                <td class="loadingView num4"  id="facilityNameF20To24"></td>
                <td class="loadingView num4"  id="monthYearrF20To24"></td>
                <td  style="text-align:center" align="middle">Female</td>
                <td  style="text-align:center" align="middle">20-24 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F20To24">-</td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUF20To24">-</td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF20To24">-</td>
                <td class="loadingView num4"  id="goodAdhFUF20To24">-</td>
                <td class="loadingView num4"  id="eligibleFUF20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenF20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResultF20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200F20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult1000F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12MtF20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F20To24">-</td>
                <td class="loadingView num4"  id="switchTo2ndF20To24">-</td>
                <td class="loadingView num4"  id="switchTo3rdF20To24">-</td>
                <td class="loadingView num4"  id="completed7F20To24">-</td>
                <td class="loadingView num4"  id="transferredOutF20To24">-</td>
                <td class="loadingView num4"  id="ltfuF20To24">-</td>
                <td class="loadingView num4"  id="deadF20To24">-</td>
                <td class="loadingView num4"  id="optedOutF20To24">-</td>
                <td class="loadingView num4"  id="transitionedF20To24">-</td>
                <td class="loadingView num4"  id="exitedF20To24">-</td>
            </tr>
            
            <tr>
                <td class="loadingView num4"  id="parner_NameF20To24ended">ended</td>
                <td class="loadingView num4"  id="facilityStateF20To24"></td>
                <td class="loadingView num4"  id="facilityLGAF20To24"></td>
                <td class="loadingView num4"  id="facilityNameF20To24"></td>
                <td class="loadingView num4"  id="monthYearrF20To24"></td>
                <td  style="text-align:center" align="middle">Female</td>
                <td  style="text-align:center" align="middle">20-24 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F20To24">-</td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUF20To24">-</td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF20To24">-</td>
                <td class="loadingView num4"  id="goodAdhFUF20To24">-</td>
                <td class="loadingView num4"  id="eligibleFUF20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenF20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResultF20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200F20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F20To24">-</td>
                <td class="loadingView num4"  id="samplesTakenResult1000F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12MtF20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F20To24">-</td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F20To24">-</td>
                <td class="loadingView num4"  id="switchTo2ndF20To24">-</td>
                <td class="loadingView num4"  id="switchTo3rdF20To24">-</td>
                <td class="loadingView num4"  id="completed7F20To24">-</td>
                <td class="loadingView num4"  id="transferredOutF20To24">-</td>
                <td class="loadingView num4"  id="ltfuF20To24">-</td>
                <td class="loadingView num4"  id="deadF20To24">-</td>
                <td class="loadingView num4"  id="optedOutF20To24">-</td>
                <td class="loadingView num4"  id="transitionedF20To24">-</td>
                <td class="loadingView num4"  id="exitedF20To24">-</td>
            </tr>
            
            
        </tbody>
        
    </table>
</div>


