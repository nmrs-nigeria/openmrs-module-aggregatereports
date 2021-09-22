<%
ui.includeJavascript("dataquality", "jquery-3.3.1.js")
ui.includeJavascript("dataquality", "jquery.dataTables.min.js")
ui.includeJavascript("dataquality", "datatables.min.js")
ui.includeJavascript("dataquality", "buttons.flash.min.js")
ui.includeJavascript("dataquality", "jszip.min.js")
ui.includeJavascript("dataquality", "pdfmake.min.js")
ui.includeJavascript("dataquality", "vfs_fonts.js")
ui.includeJavascript("dataquality", "buttons.html5.min.js")
ui.includeJavascript("dataquality", "jquery-ui.js")
ui.includeCss("dataquality", "jquery-ui.css")
ui.includeJavascript("dataquality", "buttons.print.min.js")
ui.includeCss("dataquality", "buttons.dataTables.min.css")
ui.includeCss("dataquality", "jquery.dataTables.min.css")

def id = config.id
%>
<%=ui.resourceLinks()%>
<div class="container">
    
    <fieldset>
        <legend>Filters</legend>
        <div class="row">
           <label class="col-sm-6 col-md-2 " ><strong>Start Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="startDate" name="startDate"/>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>End Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="endDate" name="endDate"/>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterCQI">Filter</button>
            </div>
        </div>
    </fieldset>
    <table id="myTable" class="display">
        <thead>
            <tr><th colspan='6' style='text-align: left; font-size: 16px;'>Clinical</th></tr>
        </thead>
        <thead style="font-size: 13px;">
            <tr>
                <th>S/N</th>
                <th>Indicator</th>
                <th>Numerator</th>
                <th>Denominator</th>
                <th>Performance</th>
                <th>Action </th>
            </tr>

        </thead>
        <tbody>
            <tr>
                <td>I</td>
                <td>Proportion of all active patients with a documented educational status </td>
                <td id="activePtsWithDocEduStat" class="loadingContent" ><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalActivePatientsEdu" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentEduStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.ACTIVE_DOCUMENTED_EDUCATIONAL_STATUS_COHORT)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>II</td>
                <td>Proportion of all active patients with a documented marital status </td>
                <td id="activePtsWithDocMarStat" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalActivePatientsMar" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentMarStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.ACTIVE_DOCUMENTED_MARITAL_STATUS_COHORT)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>III</td>
                <td>Proportion of all active patients with a documented occupational status </td>
                <td id="activePtsWithDocOccuStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalActivePatientsOccu" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentOccuStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.ACTIVE_DOCUMENTED_OCCUPATIONAL_STATUS_COHORT)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>IV</td>
                <td>
                   <span class="withNoDate">Proportion of patients newly started on ART in the last 6 months with documented age and/or Date of Birth</span>
                   <span class="withDate hidden">Proportion of patients started on ART between <strong class="startDate"></strong> and <strong class="endDate"></strong> with documented age and/or Date of Birth</span>
                </td>
                <td id="startedOnArtWithDob" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtDob" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtDob" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);"  data-href="dataqualityview.page?type=${ui.format(constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_DOB)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>V</td>
                <td>
                    <span class="withNoDate">Proportion of patients newly started on ART in the last 6 months with documented Sex </span>
                    <span class="withDate hidden">Proportion of patients  started on ART between <strong class="startDate"></strong> and <strong class="endDate"></strong> with documented Sex </span>
                </td>
                <td id="startedOnArtWithGender" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtGender" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtGender" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_SEX)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>VI</td>
                <td>
                    <span class="withNoDate">Proportion of patients newly started on ART in the last 6 months with registered address/LGA of residence</span>
                    <span class="withDate hidden">Proportion of patients  started on ART between <strong class="startDate"></strong> and <strong class="endDate"></strong> with registered address/LGA of residence</span>
                </td>
                <td id="startedOnArtWithAddress" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtAddress" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtAddress" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.NEWLY_STARTED_ON_ART_WITH_DOCUMENTED_LGA)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>VII</td>
                <td>
                    <span class="withNoDate">Proportion of patients newly started on ART in the last 6 months with documented date of HIV diagnosis</span>
                    <span class="withDate hidden">Proportion of patients  started on ART between <strong class="startDate"></strong> and <strong class="endDate"></strong> with documented date of HIV diagnosis</span>
                </td>
                <td id="startedOnArtWithDiagnosisDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtDiagnosisDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtDiagnosisDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_DATECONFIRMED_POSITIVE)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>VIII</td>
                <td>
                    <span class="withNoDate">Proportion of patients newly started on ART in the last 6 months with documented HIV enrollment date</span>
                    <span class="withDate hidden">Proportion of patients  started on ART between <strong class="startDate"></strong> and <strong class="endDate"></strong> months with documented HIV enrollment date</span>

                </td>
                <td id="startedOnArtWithEnrollmentDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtEnrollmentDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtEnrollmentDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_HIVENROLLMENT)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>IX</td>
                <td> 
                    <span class="withNoDate">Number of All patients Newly started on ART in the last 6 months with a documented Drug pickup</span>
                    <span class="withDate hidden">Number of All patients started between <strong class="startDate"></strong> and <strong class="endDate"></strong> with a documented Drug pickup</span>
                </td>
                <td id="startedOnArtWithDrugPickup" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtDrugPickup" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtDrugPickup" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.DOCUMENTED_ART_START_DATE_ARV_PICKUP_COHORT)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>X</td>
                <td>
                     <span class="withNoDate">Proportion of patients newly started on ART in the last 6 months with documented CD4 result </span>
                     <span class="withDate hidden">Proportion of patients started on ART between <strong class="startDate"></strong> and <strong class="endDate"></strong> with documented CD4 result </span>
                </td>
                <td id="startedOnArtWithCd4" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtCd4" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtCd4" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_CD4_COUNT)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>XI</td>
                <td>
                    <span class="withNoDate">Proportion of patients with a clinic visit in the last 6 months that had documented weight</span>
                    <span class="withDate hidden">Proportion of patients with a clinic visit between <strong class="startDate"></strong> and <strong class="endDate"></strong> that had documented weight</span>
                
                </td>
                <td id="clinicVisitWithWeight" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalClinicVisitWeight" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentClinicVisitWeight" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WEIGH)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <!--
                <td></td>
                <td>${ui.format()}</td>
                <td>${ui.format()}</td>
                <td>${ui.format()}</td>
                <td><a href=''>Get List</a></td>
            -->
            <tr>
                <td>XII</td>
                <td>
                    <span class="withNoDate">Proportion of pediatric patients with a clinic visit in the last 6 months that had documented MUAC </span>
                    <span class="withDate hidden">Proportion of pediatric patients with a clinic visit between <strong class="startDate"></strong> and <strong class="endDate dqrDetails"></strong> that had documented MUAC </span>
                </td>
                <td id="clinicVisitWithMuac" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalClinicVisitMuac" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentClinicVisitMuac" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.PEDIATRIC_CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_MUAC)}" class="button dqrDetails"  title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>XIII</td>
                <td>
                    <span class="withNoDate">Proportion of patients with a clinic visit in the last 6 months that had documented WHO clinical stage</span>
                    <span class="withDate hidden">Proportion of patients with a clinic visit between <strong class="startDate"></strong> and <strong class="endDate"></strong> that had documented WHO clinical stage</span>
                    
                </td>
                <td id="clinicVisitWithWho" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalClinicVisitWho" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentClinicVisitWho" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WHO)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>XIV</td>
                <td>
                    <span class="withNoDate">Proportion of patients with a clinic visit in the last 6 months that had documented TB status</span>
                    <span class="withDate hidden">Proportion of patients with a clinic visit between <strong class="startDate"></strong> and <strong class="endDate"></strong> that had documented TB status</span>
                </td>
                <td id="clinicVisitWithTBStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalClinicVisitTBStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentClinicVisitTBStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_TB_STATUS)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>XV</td>
                <td>
                    <span class="withNoDate">Proportion of patients with a clinic visit in the last 6 months that had documented functional status</span>
                    <span class="withDate hidden">Proportion of patients with a clinic visit between <strong class="startDate"></strong> and <strong class="endDate"></strong> that had documented functional status</span>
                </td>
                <td id="clinicVisitWithFunctionalStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalClinicVisitFunctionalStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentClinicVisitFunctionalStatus" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.CLINIC_VISIT_LAST_6MONTHS_WITH_FUNCTIONAL_STATUS)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>XVI</td>
                <td >
                    <span class="withNoDate">Proportion of patients newly started on ART in the last 6 months with initial ART regimen</span>
                    <span class="withDate hidden">Proportion of patients  started on ART <strong class="startDate"></strong> and <strong class="endDate"></strong> with initial ART regimen</span>
                </td>
                <td id="startedOnArtWithInitialRegimen" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalStartedOnArtInitialRegimen" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentStartedArtInitialRegimen" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.STARTED_ART_LAST_6MONTHS_WITH_INITIAL_REGIMEN)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>XVII</td>
                <td>
                    <span class="withNoDate">Proportion of all patients with a clinic visit in the last 6 months that have documented next scheduled appointment date</span>
                    <span class="withDate hidden">Proportion of all patients with a clinic visit between <strong class="startDate"></strong> and <strong class="endDate"></strong> that have documented next scheduled appointment date</span>
                    
                </td>
                <td id="clinicVisitWithNextAppDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalClinicVisitNextAppDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentClinicVisitNextAppDate" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_NEXT_APPOINTMENT_DATE)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            <tr>
                <td>XVIII</td>
                <td>
                    <span class="withNoDate">Proportion of all inactive patients with a documented exit reason</span>
                    <span class="withDate hidden">Proportion of all inactive patients as at <strong class="endDate"></strong> with a documented exit reason</span>
                </td>
                <td id="totalInactiveDocReason" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalInactive" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentInactiveDocReason" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.DOCUMENTED_EXIT_REASON_INACTIVE_COHORT)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>
            </tr>
            
            <tr>
                <th colspan='6'  style='text-align: left; font-size: 16px;'>Pharmacy</th>
            </tr> 
            <tr>
                <th>S/N</th>
                <th>Indicator</th>
                <th>Numerator</th>
                <th>Denominator</th>
                <th>Performance</th>
                <th>Action</th>
            </tr>
            <tr>
                <td>I</td> 
                <td>Proportion of patients with a documented ART regimen quantity in the last drug refill visit</td>
                <td id="totalPickupWithQty" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalPickupQty" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentagePickupQty" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.LAST_ARV_PHARMACY_PICKUP_WITH_QUANTITY)}" class="button dqrDetails" title="View records with issues">View issues</a></td>    
            </tr>
            <tr>
                <td>II</td>
                <td>Proportion of patients with a documented ART regimen duration in the last drug refill visit</td>
                <td id="totalPickupWithDuration" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalPickupDuration" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentagePickupDuration" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.LAST_ARV_PHARMACY_PICKUP_WITH_DURATION)}" class="button dqrDetails" title="View records with issues">View issues</a></td>    
            </tr>
            <tr>
                <td>III</td> 
                <td>Proportion of patients with documented ART regimen in the last drug refill visit</td>
                <td id="totalPickupWithRegimen" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalPickupRegimen" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentagePickupRegimen" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.LAST_ARV_PHARMACY_PICKUP_WITH_REGIMEN)}" class="button dqrDetails" title="View records with issues">View issues</a></td>    
            </tr>
            <tr>
                <td>IV</td>
                <td>Proportion of patients with ART regimen duration not more than six(6) months  in the last drug refill visit</td>
                <td id="totalPickupWithQty180" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalPickupQty180" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentagePickupQty180" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.LAST_ARV_PHARMACY_PICKUP_WITH_DURATION_MORETHAN180DAYS)}" class="button dqrDetails" title="View records with issues">View issues</a></td>    
            </tr>

            <tr>
                <th colspan='6'  style='text-align: left; font-size: 16px;'>Laboratory</th>
            </tr> 
            <tr>
                <th>S/N</th>
                <th>Indicator</th>
                <th>Numerator</th>
                <th>Denominator</th>
                <th>Performance</th>
                <th>Action</th>
            </tr>
            <tr>
                <td>I</td>
                <td>Proportion of eligible patients with documented Viral Load results done in the last one year</td>
                <td id="totalEligibleWithResult" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalEligibleResult" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentEligibleResult" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.VIRAL_LOAD_ELIGIBLE_WITH_DOCUMENTED_RESULT)}" class="button dqrDetails" title="View records with issues">View issues</a></td>       
            </tr>
            <tr>
                <td>II</td>
                <td>Proportion of patients with Viral Load result that had documented specimen collection date </td>
                <td id="totalResultWithResultCollection" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalResultCollection" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentResultCollection" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.VIRAL_LOAD_RESULT_WITH_SAMPLE_COLLECTION_DATE)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>       
            </tr>
            
            <tr>
                <td>III</td> 
                <td>Proportion of patients with Viral load results with a documented date sample was received at the PCR lab</td>
                <td id="totalResultWithResultSent" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="totalResultSent" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td id="percentResultSent" class="loadingContent"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></td>
                <td><a href="javascript:void(0);" data-href="dataqualityview.page?type=${ui.format(constants.SAMPLE_SENT_WITH_SAMPLE_RECEIVED_AT_PCR)}" class="button dqrDetails" title="View records with issues">View Issues</a></td>       
            </tr>



        </tbody>
    </table>
</div>




<style>
    .dt-buttons{
    float: right;
    }
    #apps{
    margin-bottom: 60px;
    }
    #myTable {
    width: 90%;
    margin-left: 5%;
    }
    .buttons-html5{
    text-decoration: none;
    margin-left: 5px;
    margin-bottom: 10px;
    text-align: center;
    border-radius: 3px;
    background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #5b57a6), color-stop(100%, #5b57a6));
    background: -webkit-linear-gradient(top, #5b57a6, #5b57a6);
    background: -moz-linear-gradient(top, #5b57a6, #5b57a6);
    background: -o-linear-gradient(top, #5b57a6, #5b57a6);
    background: -ms-linear-gradient(top, #5b57a6, #5b57a6);
    background: linear-gradient(top, #5b57a6, #5b57a6);
    background-color: #5b57a6;
    border: #5b57a6 1px solid;
    padding: 8px 20px;
    display: inline-block;
    line-height: 1.2em;
    color: white;
    cursor: pointer;
    min-width: 0;
    max-width: 300px;
    text-decoration: none;
    padding: 5px 6px;
    min-width: 70px;
    font-size: 0.9em;
    }

    #myTable_paginate{
    display: flex-inline;
    }
    #myTable_paginate li {
    margin:2px;
    padding:3px;
    text-decoration: none;
    text-align: center;
    border-radius: 3px;
    background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #5b57a6), color-stop(100%, #5b57a6));
    background: -webkit-linear-gradient(top, #5b57a6, #5b57a6);
    background: -moz-linear-gradient(top, #5b57a6, #5b57a6);
    background: -o-linear-gradient(top, #5b57a6, #5b57a6);
    background: -ms-linear-gradient(top, #5b57a6, #5b57a6);
    background: linear-gradient(top, #5b57a6, #5b57a6);
    background-color: #5b57a6;
    border: #5b57a6 1px solid;
    display: inline-block;
    color: white;
    cursor: pointer;
    width:auto;
    }

    #myTable_paginate li a{
        color:white;
    } 
</style>
<script type="text/javascript">
    jq = jQuery;
    var cohortAjaxUrl = '${ ui.actionLink("getCohortCounts") }';
    var totalActivePatients = 0;
    var totalARTPatients = 0;
    var totalPatientsClinicVisit = 0;
    var totalPtsWithDocARVPickup = 0;
    var totalPtsEligibleForVl = 0;
    var totalPtsWithVl = 0;
   // getCohorts(cohortAjaxUrl);
   //educational status
   jq(".dqrDetails").click(function(){
        var url = jq(this).attr("data-href");
        var startDate = jq("#startDate").val();
        var endDate = jq("#endDate").val();
        //window.location = url+"&startDate="+startDate+"&endDate="+endDate;
        window.open(url+"&startDate="+startDate+"&endDate="+endDate, "_blank");
        
   });
   
   jq('.date').datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth:true,
            yearRange: "-30:+0",
            autoclose: true
        });
        
   jq("#filterCQI").click(function(){
        
        var startDate = jq("#startDate").val();
        var endDate = jq("#endDate").val();
        if(startDate == "" || endDate == "")
        {
            alert("Please select start and end date");
        }else{
             jq(".loadingContent").html('<img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" />');
             jq(".startDate").html(startDate)
             jq(".endDate").html(endDate);
             jq(".withDate").removeClass("hidden");
             jq(".withNoDate").addClass("hidden");
             filterRecords();
             
        }
        
        
        
        
   });
        
        
  filterRecords();
        
   function filterRecords()
   {    
   
          var startDate = jq("#startDate").val();
            var endDate = jq("#endDate").val();
   
        myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getActivePatientsWithDocumentEducationalStaus") }').then(function(response){

            
             var data = JSON.parse(response);
             var totalPtsWithEducationalStatus = data["totalPtsWithEducationalStatus"];
             var totalActivePatients = data["totalActivePatients"];
             var percent = (totalActivePatients > 0) ? ( totalPtsWithEducationalStatus/totalActivePatients * 100).toFixed(1) + "%" :"-";

             jq("#activePtsWithDocEduStat").html(totalPtsWithEducationalStatus);
             jq("#totalActivePatientsEdu").html(totalActivePatients);
             
             jq("#percentEduStatus").html(percent);
            

             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getActivePatientsWithDocumentMaritalStaus") }');
         }).then(function(response){

           
             var data = JSON.parse(response);
             var totalPtsWithMaritalStatus = data["totalPtsWithMaritalStatus"];
             var totalActivePatients = data["totalActivePatients"];
             var percent = (totalActivePatients > 0) ? ( totalPtsWithMaritalStatus/totalActivePatients * 100).toFixed(1) + "%" : "-";

             jq("#activePtsWithDocMarStat").html(totalPtsWithMaritalStatus);
             jq("#totalActivePatientsMar").html(totalActivePatients);
             jq("#percentMarStatus").html(percent);
            

             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getActivePatientsWithDocumentOccupationalStaus") }');
         })
         
         .then(function(response){

             var data = JSON.parse(response);
             var totalPtsWithOccupationalStatus = data["totalPtsWithOccupationalStatus"];
             var totalActivePatients = data["totalActivePatients"];
             var percent = (totalActivePatients > 0) ? ( totalPtsWithOccupationalStatus/totalActivePatients * 100).toFixed(1) + "%" : "-" ;

             jq("#activePtsWithDocOccuStatus").html(totalPtsWithOccupationalStatus);
             jq("#totalActivePatientsOccu").html(totalActivePatients);
             jq("#percentOccuStatus").html(percent);

             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithDocDob") }');
         })
         .then(function(response){

             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithDob = data["totalPtsStartedOnArtWithDob"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent = (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithDob/totalPtsStartedOnArt * 100).toFixed(1) + "%" : "-";

             jq("#startedOnArtWithDob").html(totalPtsStartedOnArtWithDob);
             jq("#totalStartedOnArtDob").html(totalPtsStartedOnArt);
             jq("#percentStartedArtDob").html(percent);
            

             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithDocGender") }');
         })
         
          .then(function(response){

             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithGender = data["totalPtsStartedOnArtWithGender"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent = (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithGender/totalPtsStartedOnArt * 100).toFixed(1) + "%" : "-";

             jq("#startedOnArtWithGender").html(totalPtsStartedOnArtWithGender);
             jq("#totalStartedOnArtGender").html(totalPtsStartedOnArt);
             jq("#percentStartedArtGender").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithDocAddress") }');
         })
         .then(function(response){

             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithAddress = data["totalPtsStartedOnArtWithAddress"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent = (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithAddress/totalPtsStartedOnArt * 100).toFixed(1) + "%" : "-";

             jq("#startedOnArtWithAddress").html(totalPtsStartedOnArtWithAddress);
             jq("#totalStartedOnArtAddress").html(totalPtsStartedOnArt);
             jq("#percentStartedArtAddress").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithDocHivDiagnosisDate") }');
         })
         
         .then(function(response){

             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithHivDiagnosisDate = data["totalPtsStartedOnArtWithHivDiagnosisDate"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent =  (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithHivDiagnosisDate/totalPtsStartedOnArt * 100).toFixed(1) +"%" : "-";

             jq("#startedOnArtWithDiagnosisDate").html(totalPtsStartedOnArtWithHivDiagnosisDate);
             jq("#totalStartedOnArtDiagnosisDate").html(totalPtsStartedOnArt);
             jq("#percentStartedArtDiagnosisDate").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithDocHivEnrollmentDate") }');
         })
         .then(function(response){

             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithHivEnrollmentDate = data["totalPtsStartedOnArtWithHivEnrollmentDate"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent = (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithHivEnrollmentDate/totalPtsStartedOnArt * 100).toFixed(1) +"%" : "-";

             jq("#startedOnArtWithEnrollmentDate").html(totalPtsStartedOnArtWithHivEnrollmentDate);
             jq("#totalStartedOnArtEnrollmentDate").html(totalPtsStartedOnArt);
             jq("#percentStartedArtEnrollmentDate").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithDocDrugPickup") }');
         })
         
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithDrugPickup = data["totalPtsStartedOnArtWithDrugPickup"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent = (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithDrugPickup/totalPtsStartedOnArt * 100).toFixed(1) : "-";

             jq("#startedOnArtWithDrugPickup").html(totalPtsStartedOnArtWithDrugPickup);
             jq("#totalStartedOnArtDrugPickup").html(totalPtsStartedOnArt);
             jq("#percentStartedArtDrugPickup").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithDocCd4") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithCd4 = data["totalPtsStartedOnArtWithCd4"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent = (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithCd4/totalPtsStartedOnArt * 100).toFixed(1) +"%" : "-";

             jq("#startedOnArtWithCd4").html(totalPtsStartedOnArtWithCd4);
             jq("#totalStartedOnArtCd4").html(totalPtsStartedOnArt);
             jq("#percentStartedArtCd4").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsClinicVisitDocWeight") }');
         })
         
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsClinicVisitDocWeight = data["totalPtsClinicVisitDocWeight"];
             var totalPtsClinicVisit = data["totalPtsClinicVisit"];
             var percent = (totalPtsClinicVisit > 0) ? ( totalPtsClinicVisitDocWeight/totalPtsClinicVisit * 100).toFixed(1) + "%": "-";

             jq("#clinicVisitWithWeight").html(totalPtsClinicVisitDocWeight);
             jq("#totalClinicVisitWeight").html(totalPtsClinicVisit);
             jq("#percentClinicVisitWeight").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsClinicVisitDocMuac") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsClinicVisitDocMuac = data["totalPtsClinicVisitDocMuac"];
             var totalPtsClinicVisit = data["totalPtsClinicVisit"];
             var percent = (totalPtsClinicVisit > 0) ?  ( totalPtsClinicVisitDocMuac/totalPtsClinicVisit * 100).toFixed(1) +"%" : "-";

             jq("#clinicVisitWithMuac").html(totalPtsClinicVisitDocMuac);
             jq("#totalClinicVisitMuac").html(totalPtsClinicVisit);
             jq("#percentClinicVisitMuac").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsClinicVisitDocWhoStage") }');
         })
         
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsClinicVisitDocWhoStage = data["totalPtsClinicVisitDocWhoStage"];
             var totalPtsClinicVisit = data["totalPtsClinicVisit"];
             var percent = (totalPtsClinicVisit > 0) ? ( totalPtsClinicVisitDocWhoStage/totalPtsClinicVisit * 100).toFixed(1) + "%" : "-";

             jq("#clinicVisitWithWho").html(totalPtsClinicVisitDocWhoStage);
             jq("#totalClinicVisitWho").html(totalPtsClinicVisit);
             jq("#percentClinicVisitWho").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsClinicVisitDocTBStatus") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsClinicVisitDocTBStatus = data["totalPtsClinicVisitDocTBStatus"];
             var totalPtsClinicVisit = data["totalPtsClinicVisit"];
             var percent = (totalPtsClinicVisit > 0) ? ( totalPtsClinicVisitDocTBStatus/totalPtsClinicVisit * 100).toFixed(1) + "%" :"-";

             jq("#clinicVisitWithTBStatus").html(totalPtsClinicVisitDocTBStatus);
             jq("#totalClinicVisitTBStatus").html(totalPtsClinicVisit);
             jq("#percentClinicVisitTBStatus").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsClinicVisitDocFunctionalStatus") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsClinicVisitDocFunctionalStatus = data["totalPtsClinicVisitDocFunctionalStatus"];
             var totalPtsClinicVisit = data["totalPtsClinicVisit"];
             var percent = (totalPtsClinicVisit > 0) ? ( totalPtsClinicVisitDocFunctionalStatus/totalPtsClinicVisit * 100).toFixed(1) + "%" : "-";

             jq("#clinicVisitWithFunctionalStatus").html(totalPtsClinicVisitDocFunctionalStatus);
             jq("#totalClinicVisitFunctionalStatus").html(totalPtsClinicVisit);
             jq("#percentClinicVisitFunctionalStatus").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithInitialRegimen") }');
         })
         .then(function(response){

             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsStartedOnArtWithInitialRegimen = data["totalPtsStartedOnArtWithInitialRegimen"];
             var totalPtsStartedOnArt = data["totalPtsStartedOnArt"];
             var percent = (totalPtsStartedOnArt > 0) ? ( totalPtsStartedOnArtWithInitialRegimen/totalPtsStartedOnArt * 100).toFixed(1) + "%" : "-";

             jq("#startedOnArtWithInitialRegimen").html(totalPtsStartedOnArtWithInitialRegimen);
             jq("#totalStartedOnArtInitialRegimen").html(totalPtsStartedOnArt);
             jq("#percentStartedArtInitialRegimen").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsClinicVisitDocNextAppDate") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsClinicVisitDocNextAppDate = data["totalPtsClinicVisitDocNextAppDate"];
             var totalPtsClinicVisit = data["totalPtsClinicVisit"];
             var percent = (totalPtsClinicVisit > 0) ?  ( totalPtsClinicVisitDocNextAppDate/totalPtsClinicVisit * 100).toFixed(1) + "%" : "-";

             jq("#clinicVisitWithNextAppDate").html(totalPtsClinicVisitDocNextAppDate);
             jq("#totalClinicVisitNextAppDate").html(totalPtsClinicVisit);
             jq("#percentClinicVisitNextAppDate").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getInactivePtsWithDocReason") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalInactivePtsWithReason = data["totalInactivePtsWithReason"];
             var totalInactivePts = data["totalInactivePts"];
             var percent = (totalInactivePts > 0) ? ( totalInactivePtsWithReason/totalInactivePts * 100).toFixed(1) + "%" : "-";

             jq("#totalInactiveDocReason").html(totalInactivePtsWithReason);
             jq("#totalInactive").html(totalInactivePts);
             jq("#percentInactiveDocReason").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsWithDrugQuantity") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPickupWithQuantity = data["totalPickupWithQuantity"];
             var totalPickup = data["totalPickup"];
             var percent = (totalPickup > 0) ?  ( totalPickupWithQuantity/totalPickup * 100).toFixed(1) +"%" : "-";
             jq("#totalPickupWithQty").html(totalPickupWithQuantity);
             jq("#totalPickupQty").html(totalPickup);
             jq("#percentagePickupQty").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsWithDrugDuration") }');
         }).then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPickupWithDuration = data["totalPickupWithDuration"];
             var totalPickup = data["totalPickup"];
             var percent = (totalPickup > 0) ? ( totalPickupWithDuration/totalPickup * 100).toFixed(1) + "%":"-";
             jq("#totalPickupWithDuration").html(totalPickupWithDuration);
             jq("#totalPickupDuration").html(totalPickup);
             jq("#percentagePickupDuration").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsWithDrugRegimen") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPickupWithRegimen = data["totalPickupWithRegimen"];
             var totalPickup = data["totalPickup"];
             var percent = (totalPickup > 0) ? ( totalPickupWithRegimen/totalPickup * 100).toFixed(1) + "%" : "-";
             jq("#totalPickupWithRegimen").html(totalPickupWithRegimen);
             jq("#totalPickupRegimen").html(totalPickup);
             jq("#percentagePickupRegimen").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsWithDrugPickupQtyLessThan180") }');
         })
         
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPickupWithQtyLessThan180 = data["totalPickupWithQtyLessThan180"];
             var totalPickup = data["totalPickup"];
             var percent = (totalPickup > 0) ? ( totalPickupWithQtyLessThan180/totalPickup * 100).toFixed(1) + "%" : "-";
             jq("#totalPickupWithQty180").html(totalPickupWithQtyLessThan180);
             jq("#totalPickupQty180").html(totalPickup);
             jq("#percentagePickupQty180").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsEligibleForVLWithResult") }');
         })
         
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsEligibleForVlWithResult = data["totalPtsEligibleForVlWithResult"];
             var totalPtsEligibleForVl = data["totalPtsEligibleForVl"];
             var percent = (totalPtsEligibleForVl > 0) ? ( totalPtsEligibleForVlWithResult/totalPtsEligibleForVl * 100).toFixed(1) + "%" : "-";
             jq("#totalEligibleWithResult").html(totalPtsEligibleForVlWithResult);
             jq("#totalEligibleResult").html(totalPtsEligibleForVl);
             jq("#percentEligibleResult").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsWithResultAndSampleCollectionDate") }');
         })
         
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsWithVlResultAndSampleCollectionDate = data["totalPtsWithVlResultAndSampleCollectionDate"];
             var totalPtsWithResult = data["totalPtsWithResult"];
             var percent = (totalPtsWithResult > 0) ? ( totalPtsWithVlResultAndSampleCollectionDate/totalPtsWithResult * 100).toFixed(1) + "%" : "-";
             jq("#totalResultWithResultCollection").html(totalPtsWithVlResultAndSampleCollectionDate);
             jq("#totalResultCollection").html(totalPtsWithResult);
             jq("#percentResultCollection").html(percent);
             
             return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsWithResultAndSampleReceivedDate") }');
         })
         .then(function(response){
             console.log("response", response);
             var data = JSON.parse(response);
             var totalPtsWithVlResultAndReceivedDate = data["totalPtsWithVlResultAndReceivedDate"];
             var totalPtsWithResult = data["totalPtsWithResult"];
             var percent = (totalPtsWithResult > 0) ? ( totalPtsWithVlResultAndReceivedDate/totalPtsWithResult * 100).toFixed(1) + "%" :"-";
             jq("#totalResultWithResultSent").html(totalPtsWithVlResultAndReceivedDate);
             jq("#totalResultSent").html(totalPtsWithResult);
             jq("#percentResultSent").html(percent+"%");
             
             //return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsStartedOnARTWithInitialRegimen") }');
         })
         
      }
   
       
    
       
       
     
     
  
     
    
    
     
    
  
       
   
</script>



