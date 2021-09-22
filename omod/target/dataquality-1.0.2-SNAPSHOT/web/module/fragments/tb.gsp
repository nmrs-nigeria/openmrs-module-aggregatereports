
<%
ui.includeJavascript("dataquality", "jquery-3.3.1.js")
ui.includeJavascript("dataquality", "jquery.dataTables.min.js")
ui.includeJavascript("dataquality", "datatables.min.js")
ui.includeJavascript("dataquality", "buttons.flash.min.js")
ui.includeJavascript("dataquality", "bootstrap.min.js")
//ui.includeJavascript("dataquality", "bootstrap-datepicker.js")
ui.includeJavascript("dataquality", "jquery-ui.js")
ui.includeCss("dataquality", "bootstrap.min.css")
ui.includeCss("dataquality", "jquery-ui.css")
//ui.includeCss("dataquality", "bootstrap-datepicker.css")
ui.includeJavascript("dataquality", "jszip.min.js")
ui.includeJavascript("dataquality", "pdfmake.min.js")
ui.includeJavascript("dataquality", "vfs_fonts.js")
ui.includeJavascript("dataquality", "buttons.html5.min.js")
ui.includeJavascript("dataquality", "buttons.print.min.js")
ui.includeCss("dataquality", "buttons.dataTables.min.css")
ui.includeCss("dataquality", "jquery.dataTables.min.css")
ui.includeCss("dataquality", "myStyle.css")

def id = config.id
%>
<%=ui.resourceLinks()%>
<div class="container">
    
    <h2>TB</h2>
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
                <button class="button" id="filterTB">Filter</button>
            </div>
        </div>
    </fieldset>
    <br/>
    
      <br/>
    <table class="table">
        <thead>
            <tr>
               
                <th>Data Elements/Indicators</th>
                <th>Newly diagnosed PLHIV and enrolled in care and treatment (first visit during the reporting period)</th>
                <th> PLHIV on ART before the reporting period (follow up visits)</th>
                <th>Total</th>
               <th></th>
            </tr>
        </thead>
        <tbody>
           
            <tr> 
                <td># Newly enrolled PLHIV</td>
                <td id="enrolledThisPeriod" class="loadingView">-</td>
                <td id="enrolledBeforePeriod" class="loadingView">-</td>
                <td id="totalEnrolled" class="loadingView">-</td>
               
                <td>
                   <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="1"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Screened (symptoms)</td>
                <td  id="enrolledThisPeriodScreened" class="loadingView">-</td>
                <td  id="enrolledBeforePeriodScreened" class="loadingView">-</td>
                <td  id="totalEnrolledScreened" class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="2"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Presumptive TB</td>
                <td id="totalPresumptiveTBThisPeriod" class="loadingView">-</td>
                <td id="totalPresumptiveTBBeforePeriod" class="loadingView">-</td>
                <td id="totalPresumptiveTB" class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="3"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Diagnosed TB (GeneXpert, CXR, clinical, others)</td>
                <td  id="totalConfirmedTBThisPeriod" class="loadingView">-</td>
                <td  id="totalConfirmedTBBeforePeriod" class="loadingView">-</td>
                <td  id="totalConfirmedTB" class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="4"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># GeneXpert evaluated </td>
                <td id="totalEvaluatedWithGeneXpertThisPeriod" class="loadingView">-</td>
                <td id="totalEvaluatedWithGeneXpertBeforePeriod" class="loadingView">-</td>
                <td id="totalEvaluatedWithGeneXpert" class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="5"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># GeneXpert diagnosed TB</td>
                <td id="totalDiagnosedWithGeneXpertThisPeriod" class="loadingView">-</td>
                <td id="totalDiagnosedWithGeneXpertBeforePeriod" class="loadingView">-</td>
                <td id="totalDiagnosedWithGeneXpert" class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="6"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Chest x-ray evaluated </td>
                <td id="totalEvaluatedWithChestXRayThisPeriod"  class="loadingView">-</td>
                <td id="totalEvaluatedWithChestXRayBeforePeriod" class="loadingView">-</td>
                <td id="totalEvaluatedWithChestXRay" class="loadingView">-</td>
               
                <td>
                   <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="7"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Chest x-ray diagnosed TB </td>
                <td  id="totalDiagnosedWithChestXRayThisPeriod" class="loadingView">-</td>
                <td  id="totalDiagnosedWithChestXRayBeforePeriod" class="loadingView">-</td>
                <td  id="totalDiagnosedWithChestXRay" class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="8"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Clinically evaluated </td>
                <td  id="totalEvaluatedWithCultureThisPeriod" class="loadingView">-</td>
                <td  id="totalEvaluatedWithCultureBeforePeriod" class="loadingView">-</td>
                <td  id="totalEvaluatedWithCulture" class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="9"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Clinically diagnosed TB</td>
                <td  id="totalDiagnosedWithCultureThisPeriod" class="loadingView">-</td>
                <td  id="totalDiagnosedWithCultureBeforePeriod" class="loadingView">-</td>
                <td  id="totalDiagnosedWithCulture" class="loadingView">-</td>
               
                <td>
                   <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="10"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Commenced on TB treatment</td>
                <td id="totalPtsOnTBTreatmentThisPeriod" class="loadingView">-</td>
                <td id="totalPtsOnTBTreatmentBeforePeriod" class="loadingView">-</td>
                <td id="totalPtsOnTBTreatment" class="loadingView">-</td>
               
                <td>
                   <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="11"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Eligible for TPT</td>
                <td id="totalEligibleForIPTThisPeriod"  class="loadingView">-</td>
                <td id="totalEligibleForIPTBeforePeriod" class="loadingView">-</td>
                <td id="totalEligibleForIPT" class="loadingView">-</td>
               
               <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="12"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td># Commenced on TPT</td>
                <td id="totalStartedIPTThisPeriod"   class="loadingView">-</td>
                <td  id="totalStartedIPTBeforePeriod"  class="loadingView">-</td>
                <td  id="totalStartedIPT"  class="loadingView">-</td>
               
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item tbDetails" href="javascript:void(0);"  data-type="13"  >Details </a>

                                    </li>
                                    
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            
            
                   
        </tbody>
        
    </table>
</div>



<!-- Modal -->
<div class="modal fade" id="descriptionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalTitle">Description</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Analytic Description</strong></div>
              <div class="col-sm-12 col-md-8"><p id="description"></p></div>
              <hr class="niceDarkHr"/>
          </div>
          
          <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Data Elements</strong></div>
              <div class="col-sm-12 col-md-8"><p id="dataElements"></p></div>
              <hr class="niceDarkHr"/>
          </div>
          <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Numerator</strong></div>
              <div class="col-sm-12 col-md-8"><p id="numerator"></p></div>
              <hr class="niceDarkHr"/>
          </div>
           <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Denominator</strong></div>
              <div class="col-sm-12 col-md-8"><p id="denominator"></p></div>
              <hr class="niceDarkHr"/>
          </div>
           <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Minimum Expectation</strong></div>
              <div class="col-sm-12 col-md-8"><p id="minimumExpectation"></p></div>
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


<style>


    
</style>
<script type="text/javascript">
    jq = jQuery;
    
    
      var totalReportCount = 13;    
      var currReportCount = 0;
                 
      var totalPtsOnArt = 0;
      var totalPedsOnArt = 0;
      var totalAdultsTestedPositive = 0;
      var totalPedsTestedPositive = 0;
      var startDate = "";
      var endDate = "";
      jq(document).ready(function(e){
        
      jq(".date").unbind("datepicker");
      jq('.date').datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth:true,
            yearRange: "-30:+0",
            autoclose: true
        });
        
       // setupDatePickerPositioner();
      
      //ensure that the toggle button is closed
        
        jq(".tbDetails").click(function(e){
      
            startDate = jq("#startDate").val();
             endDate = jq("#endDate").val();
            var type = jq(this).attr("data-type");
            window.open("tbdetails.page?type="+type+"&startDate="+startDate+"&endDate="+endDate, "_blank");
        })
      
        
        
      
        jq("#filterTB").click(function(e){
       
            currReportCount = 0;
            //show the progress area
            jq("#progressArea").removeClass("hidden");
            
            jq(".loadingView").html('<img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" />');
             startDate = jq("#startDate").val();
             endDate = jq("#endDate").val();
            
            myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getAllNewlyEnrolled") }').then(function(response){
            
                var data = JSON.parse(response);
                var enrolledThisPeriod = data["enrolledThisPeriod"];
                totalEnrolledBeforePeriod = data["totalEnrolledBeforePeriod"];
                var total = new Number(enrolledThisPeriod) + new Number(totalEnrolledBeforePeriod);
                
                jq("#enrolledThisPeriod").html(enrolledThisPeriod);
                jq("#enrolledBeforePeriod").html(totalEnrolledBeforePeriod);
                jq("#totalEnrolled").html(total);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalScreened") }');
            }).then(function(response){
            
                var data = JSON.parse(response);
                var enrolledThisPeriodScreened = data["totalEnrolledThisPeriodScreened"];
                var totalEnrolledBeforePeriodScreened = data["totalEnrolledBeforePeriodScreened"];
                var total = new Number(enrolledThisPeriodScreened) + new Number(totalEnrolledBeforePeriodScreened);
                
                jq("#enrolledThisPeriodScreened").html(enrolledThisPeriodScreened);
                jq("#enrolledBeforePeriodScreened").html(totalEnrolledBeforePeriodScreened);
                jq("#totalEnrolledScreened").html(total);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalOnPresumptiveTB") }');
            })
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalOnPresumptiveTBThisPeriod = data["totalOnPresumptiveTBThisPeriod"];
                var totalOnPresumptiveTBBeforePeriod = data["totalOnPresumptiveTBBeforePeriod"];
                var totalOnPresumptiveTB = new Number(totalOnPresumptiveTBThisPeriod) + new Number(totalOnPresumptiveTBBeforePeriod);
                
                jq("#totalPresumptiveTBThisPeriod").html(totalOnPresumptiveTBThisPeriod);
                jq("#totalPresumptiveTBBeforePeriod").html(totalOnPresumptiveTBBeforePeriod);
                jq("#totalPresumptiveTB").html(totalOnPresumptiveTB);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalOnConfirmedTB") }');
            })

            .then(function(response){
            
                var data = JSON.parse(response);
                var totalConfirmedTBThisPeriod = data["totalConfirmedTBThisPeriod"];
                var totalConfirmedTBBeforePeriod = data["totalConfirmedTBBeforePeriod"];
                var totalConfirmedTB = new Number(totalConfirmedTBThisPeriod) + new Number(totalConfirmedTBBeforePeriod);
                
                jq("#totalConfirmedTBThisPeriod").html(totalConfirmedTBThisPeriod);
                jq("#totalConfirmedTBBeforePeriod").html(totalConfirmedTBBeforePeriod);
                jq("#totalConfirmedTB").html(totalConfirmedTB);

                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalGeneXpertEvaluatedTB") }');
            })
            
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalEvaluatedWithGeneXpertThisPeriod = data["totalEvaluatedWithGeneXpertThisPeriod"];
                var totalEvaluatedWithGeneXpertBeforePeriod = data["totalEvaluatedWithGeneXpertBeforePeriod"];
                var totalEvaluatedWithGeneXpert = new Number(totalEvaluatedWithGeneXpertThisPeriod) + new Number(totalEvaluatedWithGeneXpertBeforePeriod);
                
                jq("#totalEvaluatedWithGeneXpertThisPeriod").html(totalEvaluatedWithGeneXpertThisPeriod);
                jq("#totalEvaluatedWithGeneXpertBeforePeriod").html(totalEvaluatedWithGeneXpertBeforePeriod);
                jq("#totalEvaluatedWithGeneXpert").html(totalEvaluatedWithGeneXpert);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalGeneXpertDiagnosedTB") }');
            })
            
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalDiagnosedWithGeneXpertThisPeriod = data["totalDiagnosedWithGeneXpertThisPeriod"];
                var totalDiagnosedWithGeneXpertBeforePeriod = data["totalDiagnosedWithGeneXpertBeforePeriod"];
                var totalDiagnosedWithGeneXpert = new Number(totalDiagnosedWithGeneXpertThisPeriod) + new Number(totalDiagnosedWithGeneXpertBeforePeriod);
                
                jq("#totalDiagnosedWithGeneXpertThisPeriod").html(totalDiagnosedWithGeneXpertThisPeriod);
                jq("#totalDiagnosedWithGeneXpertBeforePeriod").html(totalDiagnosedWithGeneXpertBeforePeriod);
                jq("#totalDiagnosedWithGeneXpert").html(totalDiagnosedWithGeneXpert);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalChestXRayEvaluatedTB") }');
            })
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalEvaluatedWithChestXRayThisPeriod = data["totalEvaluatedWithChestXRayThisPeriod"];
                var totalEvaluatedWithChestXRayBeforePeriod = data["totalEvaluatedWithChestXRayBeforePeriod"];
                var totalEvaluatedWithChestXRay = new Number(totalEvaluatedWithChestXRayThisPeriod) + new Number(totalEvaluatedWithChestXRayBeforePeriod);
                
                jq("#totalEvaluatedWithChestXRayThisPeriod").html(totalEvaluatedWithChestXRayThisPeriod);
                jq("#totalEvaluatedWithChestXRayBeforePeriod").html(totalEvaluatedWithChestXRayBeforePeriod);
                jq("#totalEvaluatedWithChestXRay").html(totalEvaluatedWithChestXRay);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalChestXRayDiagnosedTB") }');
            })
            
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalDiagnosedWithChestXRayThisPeriod = data["totalDiagnosedWithChestXRayThisPeriod"];
                var totalDiagnosedWithChestXRayBeforePeriod = data["totalDiagnosedWithChestXRayBeforePeriod"];
                var totalDiagnosedWithChestXRay = new Number(totalDiagnosedWithChestXRayThisPeriod) + new Number(totalDiagnosedWithChestXRayBeforePeriod);
                
                jq("#totalDiagnosedWithChestXRayThisPeriod").html(totalDiagnosedWithChestXRayThisPeriod);
                jq("#totalDiagnosedWithChestXRayBeforePeriod").html(totalDiagnosedWithChestXRayBeforePeriod);
                jq("#totalDiagnosedWithChestXRay").html(totalDiagnosedWithChestXRay);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalCultureEvaluatedTB") }');
            })
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalEvaluatedWithCultureThisPeriod = data["totalEvaluatedWithCultureThisPeriod"];
                var totalEvaluatedWithCultureBeforePeriod = data["totalEvaluatedWithCultureBeforePeriod"];
                var totalEvaluatedWithCulture = new Number(totalDiagnosedWithCultureThisPeriod) + new Number(totalEvaluatedWithCultureBeforePeriod);
                
                jq("#totalEvaluatedWithCultureThisPeriod").html(totalEvaluatedWithCultureThisPeriod);
                jq("#totalEvaluatedWithCultureBeforePeriod").html(totalEvaluatedWithCultureBeforePeriod);
                jq("#totalEvaluatedWithCulture").html(totalEvaluatedWithCulture);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalCultureDiagnosedTB") }');
            })
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalDiagnosedWithCultureThisPeriod = data["totalDiagnosedWithCultureThisPeriod"];
                var totalDiagnosedWithCultureBeforePeriod = data["totalDiagnosedWithCultureBeforePeriod"];
                var totalDiagnosedWithCulture = new Number(totalDiagnosedWithCultureThisPeriod) + new Number(totalDiagnosedWithCultureBeforePeriod);
                
                jq("#totalDiagnosedWithCultureThisPeriod").html(totalDiagnosedWithCultureThisPeriod);
                jq("#totalDiagnosedWithCultureBeforePeriod").html(totalDiagnosedWithCultureBeforePeriod);
                jq("#totalDiagnosedWithCulture").html(totalDiagnosedWithCulture);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalOnTBTreatment") }');
            })
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalPtsOnTBTreatmentThisPeriod = data["totalPtsOnTBTreatmentThisPeriod"];
                var totalPtsOnTBTreatmentBeforePeriod = data["totalPtsOnTBTreatmentBeforePeriod"];
                var totalPtsOnTBTreatment = new Number(totalPtsOnTBTreatmentThisPeriod) + new Number(totalPtsOnTBTreatmentBeforePeriod);
                
                jq("#totalPtsOnTBTreatmentThisPeriod").html(totalPtsOnTBTreatmentThisPeriod);
                jq("#totalPtsOnTBTreatmentBeforePeriod").html(totalPtsOnTBTreatmentBeforePeriod);
                jq("#totalPtsOnTBTreatment").html(totalPtsOnTBTreatment);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEligibleForIPT") }');
            })

            .then(function(response){
            
                var data = JSON.parse(response);
                var totalEligibleForIPTThisPeriod = data["totalEligibleForIPTThisPeriod"];
                var totalEligibleForIPTBeforePeriod = data["totalEligibleForIPTBeforePeriod"];
                var totalEligibleForIPT = new Number(totalEligibleForIPTThisPeriod) + new Number(totalEligibleForIPTBeforePeriod);
                
                jq("#totalEligibleForIPTThisPeriod").html(totalEligibleForIPTThisPeriod);
                jq("#totalEligibleForIPTBeforePeriod").html(totalEligibleForIPTBeforePeriod);
                jq("#totalEligibleForIPT").html(totalEligibleForIPT);
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalStartedOnIPT") }');
            })
            .then(function(response){
            
                var data = JSON.parse(response);
                var totalStartedIPTThisPeriod = data["totalStartedIPTThisPeriod"];
                var totalStartedIPTBeforePeriod = data["totalStartedIPTBeforePeriod"];
                var totalStartedIPT = new Number(totalStartedIPTThisPeriod) + new Number(totalStartedIPTBeforePeriod);
                
                jq("#totalStartedIPTThisPeriod").html(totalStartedIPTThisPeriod);
                jq("#totalStartedIPTBeforePeriod").html(totalStartedIPTBeforePeriod);
                jq("#totalStartedIPT").html(totalStartedIPT);
                
                //return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalScreened") }');
            })
            
            
           
            
            
        });
      
        jq(".getInformation").click(function(e){
              var key = jq(this).attr("data-key");//get the key
              
              //get information
              var description = information[key]["description"];
              var dataElements = information[key]["dataElements"]
              var numerator = information[key]["numerator"]
              var denominator = information[key]["denominator"]
              var minimumExpectation = information[key]["minimumExpectation"]
              
              
              //add information
              jq("#description").html(description);
              jq("#dataElements").html(dataElements);
              jq("#numerator").html(numerator);
              jq("#denominator").html(denominator);
              jq("#minimumExpectation").html(minimumExpectation);
              
        
          });
      });
      
        
      
    function updateProgress()
    {   
       currReportCount++;
       
       var progress = (currReportCount/totalReportCount * 100).toFixed(1);
       
       jq("#progressBar").css("width", progress+"%");
       jq("#progressBar").attr("aria-valuenow", progress);
       jq("#progressText").html(progress+"% complete");
       
    }
</script>










