
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
    
    <h2>CQI</h2>
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
    <br/>
    <div class="row hidden" id="progressAre">
       
      <div class="col-sm-11">
        <div class="progress">
          <div class="progress-bar " id="progressBar" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
        </div>
       </div>
       <div class="col-sm-1">
           <label class="label label-success" id="progressText">0%</label>
       </div>
    </div>
    
      <br/>
    <table class="table">
        <thead>
            <tr>
                <th valign="middle">95-95-95</th>
                <th>Analytic Modules</th>
                <th>Analytic Description</th>
                <th>Numerator</th>
                <th>Denominator</th>
                <th>Percentage</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td rowspan="4">1st 95</td>
                <td rowspan="4" >HTS</td>
                <td>Percentage of new adult PLHIV offered index testing</td>
                <td id="totalAdultPlhivOfferedIndexTesting" class="loadingView">-</td>
                <td id="totalAdultPlhivIndex" class="loadingView">-</td>
                <td id="percentAdultPlhivIndex" class="loadingView">-</td>
                <td>
                   
                  <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="adult_plhiv"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="1" data-type="1">View Numerator</a>

                                    </li>

                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="1" data-type="2">View others</a>

                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
            
                </td>
            </tr>
            <tr>
                <td>Percentage of new pediatric PLHIV offered index testing</td>
                <td id="totalPedPlhivOfferedIndexTesting" class="loadingView">-</td>
                <td id="totalPedPlhivIndex" class="loadingView">-</td>
                <td id="percentPedPlhivIndex" class="loadingView">-</td>
                <td>
                    
                    
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="ped_plhiv"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="2" data-type="1">View Numerator</a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="2" data-type="2">View others</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
            </tr>
             <tr>
                
                <td>Percentage of Adult clients tested HIV positive</td>
                <td id="totalAdultsTestedPositive" class="loadingView">-</td>
                <td id="totalAdultsTested" class="loadingView">-</td>
                <td id="percentageAdultsPositive" class="loadingView">-</td>
                <td>
                
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="adult_pos"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="3" data-type="1">View Numerator</a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="3" data-type="2">View others</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                
                </td>
            </tr>
             <tr> 
                <td>Percentage of Paediatrics  clients tested HIV positive</td>
                <td id="totalPedsTestedPositive" class="loadingView">-</td>
                <td id="totalPedsTested" class="loadingView">-</td>
                <td id="percentagePedsPositive" class="loadingView">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="ped_pos"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="4" data-type="1">View Numerator</a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="4" data-type="2">View others</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                   </td>
            </tr>
            
            <tr>
                <td rowspan="5">2nd 95</td>
                <td rowspan="5" >ART</td>
                <td>Number of Adult patients started on Antiretroviral therapy (ART)</td>
                <td id="adultsStartedOnArt" class="loadingView">-</td>
                <td>-</td>
                <td>-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="adult_art"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="5" data-type="1">View Numerator</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                   
                </td>
            </tr>
            <tr> 
                <td>Number of Paediatric patients started on Antiretroviral therapy (ART)</td>
                <td id="pedsStartedOnArt" class="loadingView">-</td>
                <td>-</td>
                <td>-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="ped_art" data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="6" data-type="1">View Numerator</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                 </td>
            </tr>
            
            <tr> 
                <td>Number of adults and children currently receiving Antiretroviral therapy (ART)</td>
                <td id="totalPatientsReceiving" class="loadingView">-</td>
                <td>-</td>
                <td>-</td>
                <td>
                
                <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="adult_child_art" data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="7" data-type="1" data-type="1">View Numerator</a></li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Percentage of Paediatric patients with missed appointment</td>
                <td id="totalPedPtsWithMissedAppointment" class="loadingView">-</td>
                <td id="totalPedPtsWithAppointment" class="loadingView">-</td>
                <td id="percentPedPtsAppointment" class="loadingView">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="missed_app_ped"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="13" data-type="1">View Numerator</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                </td>
            </tr>
            
            
            
            <tr>
                
                <td>Percentage of patients with missed appointment</td>
                <td id="totalPtsWithMissedAppointment" class="loadingView">-</td>
                <td id="totalPtsWithAppointment" class="loadingView">-</td>
                <td id="percentPtsAppointment" class="loadingView">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="missed_app"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="12" data-type="1">View Numerator</a>

                                    </li>
                                    
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                </td>
            </tr>
            <tr> 
                <td rowspan="4">3rd 95</td>
                <td rowspan="4" >Clinical Care</td>
                <td>Percentage of monthly cohort ART new patients with a viral load (VL) request at six (6) months after commencing ART</td>
                <td id="totalPtsVL6Months" class="loadingView">-</td>
                <td id="totalPts6Months" class="loadingView">-</td>
                <td id="percentVL6Months" class="loadingView">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="new_vl_request_6" data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="8" data-type="1">View Numerator</a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="8" data-type="2">View others</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            
            
            <tr> 
                <td>Percentage of monthly cohort of new ART patients with a viral load (VL) result by month seven (7) after commencing ART</td>
                <td id="totalPtsVL7Months" class="loadingView">-</td>
                <td id="totalPts7Months" class="loadingView">-</td>
                <td id="percentVL7Months" class="loadingView">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="new_vl_request_7" data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="9" data-type="1">View Numerator</a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="9" data-type="2">View others</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                </td>
            </tr>
            
            <tr> 
                <td>Percentage of monthly cohort of new ART patients with suppressed first viral load (VL) result</td>
                <td id="totalPtsWithSuppressedVl" class="loadingView">-</td>
                <td id="totalPatientsS" class="loadingView">-</td>
                <td id="percentSuppressedVL" class="loadingView">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="new_art_vl_supp"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="10" data-type="1">View Numerator</a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="10" data-type="2">View others</a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td>Percentage of monthly cohort of new paediatric ART patients with suppressed first viral load (VL) result</td>
                <td id="totalPedPtsWithSuppressedVl" class="loadingView">-</td>
                <td id="totalPedPatientsS" class="loadingView">-</td>
                <td id="percentPedSuppressedVL" class="loadingView">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item getInformation" href="#" data-key="new_ped_art_vl_supp"  data-toggle="modal"  data-target="#descriptionModal">View Description </a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="11" data-type="1">View Numerator</a>

                                    </li>
                                    <li><a class="dropdown-item cqidetails" href="javascript:void(0);" data-subset="11" data-type="2">View others</a>

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
    var information = {};
    information["adult_plhiv"] = {description: "Percentage of new adult PLHIV offered index testing", 
                                        dataElements:"1. HTS Adult client ID on HIV risk assessment form<br/>2. HTS client ID on HIV test result<br/>3. Testing modality",
                                      numerator:"Number of new adult PLHIV offered index testing",
                                      denominator:"Number of identified adult HIV positive persons", minimumExpectation:"100%"
                                   }
                                   
     information["ped_plhiv"] = {description: "Percentage of new pediatric PLHIV offered index testing", 
                                        dataElements:"1. HTS Paediatric client ID on HIV risk assessment form <br/>2. HTS client ID on HIV test result <br/>3. Testing modality",
                                      numerator:"Number of new pediatric PLHIV offered index testing",
                                      denominator:"Number of identified pediatric HIV positive persons", minimumExpectation:"100%"
                                   }
    information["adult_pos"] = {description: "Percentage of Adult clients tested HIV positive", 
                                        dataElements:"1. HTS client ID on HIV test result <br/> 2. HIV positive result <br/>3. Testing modality",
                                      numerator:"Number of Adult clients tested HIV positive",
                                      denominator:"Number of  Adult clients tested for HIV", minimumExpectation:""
                                   }
    information["ped_pos"] = {description: "Percentage of Paediatric  clients tested HIV positive", 
                                        dataElements:"1. HTS client ID on HIV test result <br/>2. HIV positive result<br/> 3. Testing modality",
                                      numerator:"Number of Paediatric clients tested HIV positive",
                                      denominator:"Number Paediatric clients tested for HIV", minimumExpectation:""
                                   }

    information["adult_art"] = {description: "Number of Adult patients started on antiretroviral therapy (ART)", 
                                        dataElements:"1. ART Patient ID<br/> 2. First pharmacy pick up date (grouped into months)<br/> 3. Age band and sex",
                                      numerator:"Number of Adult  patients started on antiretroviral therapy (ART)",
                                      denominator:"", minimumExpectation:"100%"
                                   }
    information["ped_art"] = {description: "Number of Paediatric patients started on antiretroviral therapy (ART)", 
                                        dataElements:"1. ART Paediatric Patient ID<br/> 2. First pharmacy pick up date (grouped into months)<br/> 3. Age band and sex",
                                      numerator:"Number  of Paediatric  patients started on antiretroviral therapy (ART)",
                                      denominator:"", minimumExpectation:"100%"
                                   }
    information["adult_child_art"] = {description: "Number of adults and children currently receiving antiretroviral therapy (ART)", 
                                        dataElements:"1. ART Patient ID<br/> 2. Last pharmacy pick up date (grouped into months)<br/> 3. Duration of ARV refill (in days)<br/> 4. Dead status <br/>5. Transfer out status<br/> 6. Age band and sex",
                                      numerator:"Number of adults and children currently receiving antiretroviral therapy (ART)",
                                      denominator:"", minimumExpectation:"95%"
                                   }
                                   
    information["new_vl_request_6"] = {description: "Percentage of monthly cohort ART new patients with a viral load (VL) request at six (6) months after commencing ART", 
                                        dataElements:"1. ART Patient ID<br/> 2. First pharmacy pick up date (grouped into monthly cohorts)<br/> 3. VL request date<br/> 4. Age band and sex",
                                      numerator:"Monthly cohort of new ART patients that had a viral load (VL) request at six (6) months after commencing ART",
                                      denominator:"Monthly cohort of new ART patients", minimumExpectation:"100%"
                                   }
     information["new_vl_request_7"] = {description: "Percentage of monthly cohort of new ART patients with a viral load (VL) result by month seven (7) after commencing ART", 
                                        dataElements:"1. ART Patient ID<br/> 2. First pharmacy pick up date (grouped into monthly cohorts)<br/> 3. VL request date<br/> 4. Age band and sex",
                                      numerator:"Monthly cohort of new ART patients that had a viral load (VL) request at seven (7) months after commencing ART",
                                      denominator:"Monthly cohort of new ART patients", minimumExpectation:"100%"
                                   }
    information["new_art_vl_supp"] = {description: "Percentage of monthly cohort of new ART patients with suppressed first viral load (VL) result", 
                                        dataElements:"1. ART Patient ID<br/> 2. First pharmacy pick up date (grouped into monthly cohorts)<br/> 3. VL result date<br/> 4. VL result (<1,000 copies/ml; ≥ 1,000 copies)<br/> 5. Age band and sex",
                                      numerator:"Monthly cohort of new ART patients with suppressed first viral load (VL) result",
                                      denominator:"Monthly cohort of new ART patients", minimumExpectation:"95%"
                                   }
                                   
    information["new_ped_art_vl_supp"] = {description: "Percentage of monthly cohort of new paediatric ART patients with suppressed first viral load (VL) result", 
                                        dataElements:"1. ART Patient ID<br/> 2. First pharmacy pick up date (grouped into monthly cohorts)<br/> 3. VL result date<br/> 4. VL result (<1,000 copies/ml; ≥ 1,000 copies)<br/> 5. Age band and sex",
                                      numerator:"Monthly cohort of new Paediatric ART patients with suppressed first viral load (VL) result",
                                      denominator:"Monthly cohort of new Paediatric ART patients", minimumExpectation:"95%"
                                   }
    information["missed_app"] = {description: "Percentage of Paediatric patients with missed appointment", 
                                        dataElements:"1. Pharmacy appointment date in a month<br/> 2. Missed pharmacy appointment in a month<br/> 3.  Track monthly over one year<br/> 4. Patients with documentation of death, transferred out, stopped treatment",
                                      numerator:"Number of patients with missed appointment in a month not returned to treatment at any point in time  ",
                                      denominator:"Cohort of patients scheduled for a clinic visit in the month under review", minimumExpectation:"100%"
                                   }
                                   
    information["missed_app_ped"] = {description: "Percentage of Paediatric patients with missed appointment", 
                                        dataElements:"1. Pharmacy appointment date in a month<br/> 2. Missed pharmacy appointment in a month<br/> 3.  Track monthly over one year<br/> 4. Patients with documentation of death, transferred out, stopped treatment",
                                      numerator:"Number ofPaediatric  patients with missed appointment in a month not returned to treatment at any point in time  ",
                                      denominator:"Cohort of Paediatric patients scheduled for a clinic visit in the month under review", minimumExpectation:"100%"
                                   }
   
    
      var totalReportCount = 13;    
      var currReportCount = 0;
                 
      var totalPtsOnArt = 0;
      var totalPedsOnArt = 0;
      var totalAdultsTestedPositive = 0;
      var totalPedsTestedPositive = 0;
      var startDate = "";
      var endDate = "";
      jq(document).ready(function(e){
        
      jq('.date').datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth:true,
            yearRange: "-30:+0",
            autoclose: true
        });
        
       // setupDatePickerPositioner();
      
      //ensure that the toggle button is closed
        
        jq(".cqidetails").click(function(e){
            startDate = jq("#startDate").val();
             endDate = jq("#endDate").val();
            var subSet = jq(this).attr("data-subset");
            var type = jq(this).attr("data-type");
            window.open("cqidetail.page?type="+type+"&subset="+subSet+"&startDate="+startDate+"&endDate="+endDate, "_blank");
        })
      
      
        jq("#filterCQI").click(function(e){
            currReportCount = 0;
            //show the progress area
            jq("#progressArea").removeClass("hidden");
            
            jq(".loadingView").html('<img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" />');
             startDate = jq("#startDate").val();
             endDate = jq("#endDate").val();
            
            myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalAdultPLHIVOfferedIndexTesting") }').then(function(response){
            
                var data = JSON.parse(response);
                var totalAdultPlhivOfferedIndexTesting = data["totalAdultPlhivOfferedIndexTesting"];
                totalAdultsTestedPositive = data["totalAdultsTestedPositive"];
                var percentRequest = ( totalAdultPlhivOfferedIndexTesting/totalAdultsTestedPositive * 100).toFixed(1);
                
                jq("#totalAdultPlhivOfferedIndexTesting").html(totalAdultPlhivOfferedIndexTesting);
                jq("#totalAdultPlhivIndex").html(totalAdultsTestedPositive);
                jq("#percentAdultPlhivIndex").html(percentRequest+"%");
                updateProgress();
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalPedPLHIVOfferedIndexTesting") }');
            })
           .then(function(response){
               var data = JSON.parse(response);
                var totalPedPlhivOfferedIndexTesting = data["totalPedPlhivOfferedIndexTesting"];
                totalPedsTestedPositive = data["totalPedsTestedPositive"];
                var percentRequest = ( totalPedPlhivOfferedIndexTesting/totalPedsTestedPositive * 100).toFixed(1);
                
                jq("#totalPedPlhivOfferedIndexTesting").html(totalPedPlhivOfferedIndexTesting);
                jq("#totalPedPlhivIndex").html(totalPedsTestedPositive);
                jq("#percentPedPlhivIndex").html(percentRequest+"%");
                updateProgress();
                
               return myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getAdultClientsTestedPositive") }')
                
            })
            //get all adults tested positive
            .then(function(response){
                var data = JSON.parse(response);
                //totalAdultsTestedPositive = data["totalAdultsTestedPositive"];
                var totalAdultsTested = data["totalAdultsTested"];
                var percentageAdultsPositive = (totalAdultsTestedPositive/totalAdultsTested * 100).toFixed(1);;
                
                jq("#totalAdultsTestedPositive").html(totalAdultsTestedPositive);
                jq("#totalAdultsTested").html(totalAdultsTested);
                jq("#percentageAdultsPositive").html(percentageAdultsPositive+"%");
                
                //incrementProgress
                updateProgress();
                
                //get all peds tested positive
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPedClientsTestedPositive") }');
                
                
            }).then(function(response){
                var data = JSON.parse(response);
                //totalPedsTestedPositive = data["totalPedsTestedPositive"];
                var totalPedsTested = data["totalPedsTested"];
                var percentagePedsPositive = (totalPedsTestedPositive/totalPedsTested * 100).toFixed(1);;
                
                jq("#totalPedsTestedPositive").html(totalPedsTestedPositive);
                jq("#totalPedsTested").html(totalPedsTested);
                jq("#percentagePedsPositive").html(percentagePedsPositive+"%");
                //get all adults started on art
                updateProgress();
                return myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getAdultsStartedOnArt") }');
            }).then(function(response){
                var data = JSON.parse(response);
                var adultsStartedOnArt = data["adultsStartedOnArt"];
                totalPtsOnArt = new Number(adultsStartedOnArt);
                jq("#adultsStartedOnArt").html(adultsStartedOnArt);
                updateProgress();
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPedsStartedOnArt") }');
            })
            //get all adults started on art
           .then(function(response){
                var data = JSON.parse(response);
                
                var pedsStartedOnArt = data["pedsStartedOnArt"];
                totalPedsOnArt = new Number(pedsStartedOnArt);
                totalPtsOnArt = new Number(pedsStartedOnArt) + totalPtsOnArt;
                jq("#pedsStartedOnArt").html(pedsStartedOnArt);
                updateProgress();
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalPatientsReceivingART") }');
            })
            .then(function(response){
                var data = JSON.parse(response);
                var totalPatientsReceiving = data["totalPatientsReceiving"];
                
                jq("#totalPatientsReceiving").html(totalPatientsReceiving);
                updateProgress();
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getARTPtsWithVLRequest6Months") }');
            })
            .then(function(response){
                var data = JSON.parse(response);
                var totalPtsVL6Months = data["totalPtsVL6Months"];
                
                var percentRequest = ( totalPtsVL6Months/totalPtsOnArt * 100).toFixed(1);
                
                
                jq("#totalPtsVL6Months").html(totalPtsVL6Months);
                jq("#totalPts6Months").html(totalPtsOnArt);
                jq("#percentVL6Months").html(percentRequest+"%");
                updateProgress();
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getARTPtsWithVLRequest7Months") }');
            })
            .then(function(response){
                var data = JSON.parse(response);
                var totalPtsVL7Months = data["totalPtsVL7Months"];
                
                var percentRequest = ( totalPtsVL7Months/totalPtsOnArt * 100).toFixed(1);
                
                
                jq("#totalPtsVL7Months").html(totalPtsVL7Months);
                jq("#totalPts7Months").html(totalPtsOnArt);
                jq("#percentVL7Months").html(percentRequest+"%");
                updateProgress();
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPtsWithSuppressedFirstVl") }');
                
            })
            
            .then(function(response){
                console.log(response);
                var data = JSON.parse(response);
                var totalPtsWithSuppressedVl = data["totalPtsWithSuppressedVl"];
                
                var percentRequest = ( totalPtsWithSuppressedVl/totalPtsOnArt * 100).toFixed(1);
                
                
                jq("#totalPtsWithSuppressedVl").html(totalPtsWithSuppressedVl);
                jq("#totalPatientsS").html(totalPtsOnArt);
                jq("#percentSuppressedVL").html(percentRequest+"%");
                updateProgress();
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getPedPtsWithSuppressedFirstVl") }');
                
            })
            .then(function(response){
                console.log(response);
                var data = JSON.parse(response);
                var totalPedPtsWithSuppressedVl = data["totalPedPtsWithSuppressedVl"];
                
                var percentRequest = ( totalPedPtsWithSuppressedVl/totalPedsOnArt * 100).toFixed(1);
                
                
                jq("#totalPedPtsWithSuppressedVl").html(totalPedPtsWithSuppressedVl);
                jq("#totalPedPatientsS").html(totalPedsOnArt);
                jq("#percentPedSuppressedVL").html(percentRequest+"%");
                updateProgress();
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalPtsWithMissedAppointment") }');
                
            })
            
            .then(function(response){
                console.log(response);
                var data = JSON.parse(response);
                var totalPtsWithMissedAppointment = data["totalPtsWithMissedAppointment"];
                var totalPtsWithAppointment = data["totalPtsWithAppointment"];
                
                var percentRequest = ( totalPtsWithMissedAppointment/totalPtsWithAppointment * 100).toFixed(1);
                
                
                jq("#totalPtsWithMissedAppointment").html(totalPtsWithMissedAppointment);
                jq("#totalPtsWithAppointment").html(totalPtsWithAppointment);
                jq("#percentPtsAppointment").html(percentRequest+"%");
                //updateProgress();
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalPedPtsWithMissedAppointment") }');
                
            })
            .then(function(response){
                console.log(response);
                var data = JSON.parse(response);
                var totalPedPtsWithMissedAppointment = data["totalPedPtsWithMissedAppointment"];
                var totalPedPtsWithAppointment = data["totalPedPtsWithAppointment"];
                
                var percentRequest = ( totalPedPtsWithMissedAppointment/totalPedPtsWithAppointment * 100).toFixed(1);
                
                
                jq("#totalPedPtsWithMissedAppointment").html(totalPedPtsWithMissedAppointment);
                jq("#totalPedPtsWithAppointment").html(totalPedPtsWithAppointment);
                jq("#percentPedPtsAppointment").html(percentRequest+"%");
                //updateProgress();
               // return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalPedPtsWithMissedAppointment") }');
                
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










