<% 
int i=1;


def title = config.title

%>
<div class="container">
    
    <h2>OTZ Report</h2>
   
    <br/>
    
      <br/>
    <table class="table dataTable">
        <thead>
            <!--<tr>
               
                <th rowspan="2" valign="center">S/N</th>
                <th  rowspan="2" style="text-align:center" align="middle">Indicator</th>
                <th colspan="3"  style="text-align:center">Male</th>
                <th colspan="3"  style="text-align:center">Female</th>
                <th rowspan="2"  style="text-align:center">Action</th>
            </tr>
            -->
             <tr>
                <th valign="center">S/N</th>
                <th  style="text-align:center" align="middle">Indicator</th>
                <th>Male <10</th>
                <th>Male 10-14</th>
                <th>Male 15-19</th>
                <th>Male 20-24</th>
                <th>Male above 24</th>
                <th>Female <10</th>
                <th>Female 10-14</th>
                <th>Female 15-19</th>
                <th>Female 20-24</th>
                <th>Female above 24</th>
                <th>Total</th>
                <th  style="text-align:center">Action</th>

             </tr>
            
        </thead>
        <tbody>
            <tr>
                <td><%= i++%></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month <a href="javascript:void(0);" class="otzInfo" data-key="total_enrolled"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4"  id="totalEnrolledMBelow10">-</td>
                <td class="loadingView num4"  id="totalEnrolledM10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledM15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledM20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledMabove24">-</td>
                <td class="loadingView num4"  id="totalEnrolledFBelow10">-</td>
                <td class="loadingView num4"  id="totalEnrolledF10To14">-</td>
                <td class="loadingView num4"  id="totalEnrolledF15To19">-</td>
                <td class="loadingView num4"  id="totalEnrolledF20To24">-</td>
                <td class="loadingView num4"  id="totalEnrolledFabove24">-</td>
                <td class="loadingView num4"  id="totalEnrolledTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="1" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            
            <tr>
                <td><%= i++%></td>
                <td># of OTZ members with scheduled drug pick-up appointment in the last six months prior to enrollment on OTZ  <a href="javascript:void(0);" class="otzInfo" data-key="total_pickup_app"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorMBelow10">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorM10To14">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorM15To19">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorM20To24">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorMabove24">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorFBelow10">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorF10To14">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorF15To19">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorF20To24">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorFabove24">-</td>
                <td class="loadingView num4" id="totalEnrolledWithApp6MtPriorTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="2" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of OTZ members who kept their drug pick-up appointment in the last six months prior to enrollment on OTZ <a href="javascript:void(0);" class="otzInfo" data-key="total_kept_app"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorMBelow10">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorM10To14">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorM15To19">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorM20To24">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorMabove24">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorFBelow10">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorF10To14">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorF15To19">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorF20To24">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorFabove24">-</td>
                <td class="loadingView num4" id="totalEnrolledKeptAppPriorTotal">-</td>
                
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="3" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of OTZ members with good drug adherence score in the last six months prior to enrollment on OTZ <a href="javascript:void(0);" class="otzInfo" data-key="total_good_adh"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4" id="totalEnrolledGoodAdhPriorMBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorM10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorM15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorM20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorMabove24">-</td>
                <td class="loadingView num4" id="totalEnrolledGoodAdhPriorFBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorF10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorF15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorF20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorFabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledGoodAdhPriorTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="4" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td> # of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrollment into OTZ <a href="javascript:void(0);" class="otzInfo" data-key="total_baseline_vl"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4" id="totalEnrolledBaselineResultMBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultM10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultM15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultM20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultMabove24">-</td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultFBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultF10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultF15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultF20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultFabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="5" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrollment into OTZ and VL result 0-49 c/ml <a href="javascript:void(0);" class="otzInfo" data-key="total_baseline_vl_200"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4" id="totalEnrolledBaselineResultBelow200MBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200Mabove24">-</td>
                <td class="loadingView num4" id="totalEnrolledBaselineResultBelow200FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultBelow200Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="6" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td> # of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrollment into OTZ and VL result 50-999 c/ml <a href="javascript:void(0);" class="otzInfo" data-key="total_baseline_vl_200_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a> </td>
                <td class="loadingView num4" id="totalEnrolledBaselineResult200To1000MBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000Mabove24">-</td>
                
                <td class="loadingView num4" id="totalEnrolledBaselineResult200To1000FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult200To1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="7" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr><tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrollment into OTZ and VL result  1000 and above c/ml <a href="javascript:void(0);" class="otzInfo" data-key="total_baseline_vl_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4" id="totalEnrolledBaselineResultAbove1000MBelow10">-</td> 
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000Mabove24">-</td>
                
                <td class="loadingView num4" id="totalEnrolledBaselineResultAbove1000FBelow10">-</td> 
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResultAbove1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="8" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with VL results at baseline within the last 6 months at enrollment into OTZ <a href="javascript:void(0);" class="otzInfo" data-key="total_vl_result"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                 <td class="loadingView num4" id="totalEnrolledBaselineResult6MtMBelow10">-</td> 
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtM10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtM15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtM20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtMabove24">-</td>
                
                <td class="loadingView num4" id="totalEnrolledBaselineResult6MtFBelow10">-</td> 
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtF10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtF15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtF20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtFabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="9" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrollment into OTZ and VL 0-49 c/ml <a href="javascript:void(0);" class="otzInfo" data-key="total_vl_result_200"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4" id="totalEnrolledBaselineResult6MtBelow200MBelow10">-</td> 
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200Mabove24">-</td>
                
                <td class="loadingView num4" id="totalEnrolledBaselineResult6MtBelow200FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow200Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="10" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL result 50-999 c/ml 
                    <a href="javascript:void(0);" class="otzInfo" data-key="total_vl_result_200_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000MBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBt200To1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="11" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrollment into OTZ and VL 1000 and above c/ml <a href="javascript:void(0);" class="otzInfo" data-key="total_vl_result_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000MBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="12" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ without baseline VL results or with baseline VL result less than 1000 c/ml and eligible for month zero VL sample collection <a href="javascript:void(0);" class="otzInfo" data-key="total_eligible_month_zero"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a> </td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZMBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZM10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZM15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZM20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZMabove24">-</td>
                
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZFBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZF10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZF15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZF20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZFabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="13" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ enrolled in the cohort month and eligible for month zero VL sample collection whose VL samples were taken (at month zero) 
                    <a href="javascript:void(0);" class="otzInfo" data-key="total_eligible_with_sample_taken"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a> </td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleMBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleM10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleM15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleM20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleMabove24">-</td>
                
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleFBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleF10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleF15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleF20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleFabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledEligibleMtZSampleTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="14" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is  0-49 c/ml 
                <a href="javascript:void(0);" class="otzInfo" data-key="total_baseline1000_result200"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a>
                </td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200MBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200Mabove24">-</td>
                
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="15" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with baseline VL results <1000 c/ml whose VL result for sample collected at month zero is 50-999 c/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_baseline1000_result_200_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000MBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ200To1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="16" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with baseline VL results <1000 c/ml  whose VL result for sample collected at month zero is 1000 and above c/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_baseline1000_result_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000MBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000M10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000M15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000M20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000FBelow10">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000F10To14">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000F15To19">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000F20To24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000Fabove24">-</td>
                <td class="loadingView num3"  id="totalEnrolledB1000MZ1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="17" data-type="1" href="javascript:void(0);" >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of OTZ members with scheduled drug pick-up appointment in the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_pickup_app_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="scheduledPickupFUMBelow10">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUM10To14">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUM15To19">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUM20To24">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUMabove24">-</td>
                
                <td class="loadingView num3"  id="scheduledPickupFUFBelow10">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUF10To14">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUF15To19">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUF20To24">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUFabove24">-</td>
                <td class="loadingView num3"  id="scheduledPickupFUTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="18" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
           
            <tr>
                <td><%= i++ %></td>
                <td># of OTZ members who kept their drug pick-up appointment in the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_kept_app_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUMBelow10">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUM10To14">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUM15To19">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUM20To24">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUMabove24">-</td>
                
                <td class="loadingView num3"  id="scheduledKeptPickupFUFBelow10">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUF10To14">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUF15To19">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUF20To24">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUFabove24">-</td>
                <td class="loadingView num3"  id="scheduledKeptPickupFUTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="19" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of OTZ members with good drug adherence score in the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_good_score_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="goodAdhFUMBelow10">-</td>
                <td class="loadingView num3"  id="goodAdhFUM10To14">-</td>
                <td class="loadingView num3"  id="goodAdhFUM15To19">-</td>
                <td class="loadingView num3"  id="goodAdhFUM20To24">-</td>
                <td class="loadingView num3"  id="goodAdhFUMabove24">-</td>
                
                <td class="loadingView num3"  id="goodAdhFUFBelow10">-</td>
                <td class="loadingView num3"  id="goodAdhFUF10To14">-</td>
                <td class="loadingView num3"  id="goodAdhFUF15To19">-</td>
                <td class="loadingView num3"  id="goodAdhFUF20To24">-</td>
                <td class="loadingView num3"  id="goodAdhFUFabove24">-</td>
                <td class="loadingView num3"  id="goodAdhFUTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="20" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ who were eligible for routine VL test during the follow up period i.e. No VL result for the 6-month period prior to the beginning of the reporting period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_eligle_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="eligibleFUMBelow10">-</td>
                <td class="loadingView num3"  id="eligibleFUM10To14">-</td>
                <td class="loadingView num3"  id="eligibleFUM15To19">-</td>
                <td class="loadingView num3"  id="eligibleFUM20To24">-</td>
                <td class="loadingView num3"  id="eligibleFUMabove24">-</td>
                
                <td class="loadingView num3"  id="eligibleFUFBelow10">-</td>
                <td class="loadingView num3"  id="eligibleFUF10To14">-</td>
                <td class="loadingView num3"  id="eligibleFUF15To19">-</td>
                <td class="loadingView num3"  id="eligibleFUF20To24">-</td>
                <td class="loadingView num3"  id="eligibleFUFabove24">-</td>
                <td class="loadingView num3"  id="eligibleFUTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="21" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ whose samples were taken for routine VL test
                <a href="javascript:void(0);" class="otzInfo" data-key="total_sample_taken_routine"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="samplesTakenMBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenM10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenM15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenM20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenMabove24">-</td>
                
                <td class="loadingView num3"  id="samplesTakenFBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenF10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenF15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenF20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenFabove24">-</td>
                <td class="loadingView num3"  id="samplesTakenTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="22" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_sample_taken_with_result_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="samplesTakenResultMBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResultM10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResultM15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResultM20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResultMabove24">-</td>
                
                <td class="loadingView num3"  id="samplesTakenResultFBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResultF10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResultF15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResultF20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResultFabove24">-</td>
                <td class="loadingView num3"  id="samplesTakenResultTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="23" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 0-49 copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_followup_200"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="samplesTakenResult200MBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200M10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200M15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200M20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200Mabove24">-</td>
                
                <td class="loadingView num3"  id="samplesTakenResult200FBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200F10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200F15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200F20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200Fabove24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="24" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 50-999 copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_followup_200_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000MBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000M10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000M15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000M20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="samplesTakenResult200To1000FBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000F10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000F15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000F20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000Fabove24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult200To1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="25" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period from 1000 and above copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_followup_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a>
                </td>
                <td class="loadingView num3"  id="samplesTakenResult1000MBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000M10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000M15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000M20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="samplesTakenResult1000FBelow10">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000F10To14">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000F15To19">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000F20To24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000Fabove24">-</td>
                <td class="loadingView num3"  id="samplesTakenResult1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="26" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months
                <a href="javascript:void(0);" class="otzInfo" data-key="total_with_result"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12MtMBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12MtM10To14">-</td>
                <td class="loadingView num3"  id="resultPast12MtM15To19">-</td>
                <td class="loadingView num3"  id="resultPast12MtM20To24">-</td>
                <td class="loadingView num3"  id="resultPast12MtMabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12MtFBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12MtF10To14">-</td>
                <td class="loadingView num3"  id="resultPast12MtF15To19">-</td>
                <td class="loadingView num3"  id="resultPast12MtF20To24">-</td>
                <td class="loadingView num3"  id="resultPast12MtFabove24">-</td>
                <td class="loadingView num3"  id="resultPast12MtTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="27" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months between 0-49 copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_with_result_200"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12Mt200MBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200M10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200M15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200M20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200Mabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt200FBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200F10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200F15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200F20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200Fabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="28" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months is between 50-999 copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_with_result_200_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12Mt200To100MBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100M10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100M15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100M20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100Mabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt200To100FBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100F10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100F15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100F20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100Fabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt200To100Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="29" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months from 1000 and above copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_with_result_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12Mt1000MBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000M10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000M15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000M20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt1000FBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000F10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000F15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000F20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Fabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="30" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months from 1000 and above copies/ml and completed EAC
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_1000_eac"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACMBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACM10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACM15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACM20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACMabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt1000EACFBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACF10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACF15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACF20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACFabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000EACTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="31" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months from 1000 and above copies/ml who have repeat VL result
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_1000_repeat"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatMBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatM10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatM15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatM20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatMabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatFBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatF10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatF15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatF20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatFabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000RepeatTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="32" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months from 1000 and above copies/ml whose repeat VL result is between 0-49 copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_1000_repeat_200"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200MBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200M10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200M15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200M20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200Mabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200FBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200F10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200F15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200F20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200Fabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="33" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months from 1000 and above copies/ml whose repeat VL result is between 50-999 copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_1000_repeat_200_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000MBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000M10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000M15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000M20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000FBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000F10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000F15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000F20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000Fabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat200To1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="34" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months from 1000 and above copies/ml whose repeat VL result is from 1000 and above copies/ml
                <a href="javascript:void(0);" class="otzInfo" data-key="total_result_1000_repeat_1000"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                 <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000MBelow10">-</td>
                 <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000M10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000M15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000M20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000Mabove24">-</td>
                
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000FBelow10">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000F10To14">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000F15To19">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000F20To24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000Fabove24">-</td>
                <td class="loadingView num3"  id="resultPast12Mt1000Repeat1000Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="35" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># switched to second line ART 
                    <a href="javascript:void(0);" class="otzInfo" data-key="total_switched_2_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="switchTo2ndMBelow10">-</td>
                <td class="loadingView num3"  id="switchTo2ndM10To14">-</td>
                <td class="loadingView num3"  id="switchTo2ndM15To19">-</td>
                <td class="loadingView num3"  id="switchTo2ndM20To24">-</td>
                <td class="loadingView num3"  id="switchTo2ndMabove24">-</td>
                
                <td class="loadingView num3"  id="switchTo2ndFBelow10">-</td>
                <td class="loadingView num3"  id="switchTo2ndF10To14">-</td>
                <td class="loadingView num3"  id="switchTo2ndF15To19">-</td>
                <td class="loadingView num3"  id="switchTo2ndF20To24">-</td>
                <td class="loadingView num3"  id="switchTo2ndFabove24">-</td>
                <td class="loadingView num3"  id="switchTo2ndTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="36" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># switched to third line ART
                <a href="javascript:void(0);" class="otzInfo" data-key="total_switched_3_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="switchTo3rdMBelow10">-</td>
                <td class="loadingView num3"  id="switchTo3rdM10To14">-</td>
                <td class="loadingView num3"  id="switchTo3rdM15To19">-</td>
                <td class="loadingView num3"  id="switchTo3rdM20To24">-</td>
                <td class="loadingView num3"  id="switchTo3rdMabove24">-</td>
                
                <td class="loadingView num3"  id="switchTo3rdFBelow10">-</td>
                <td class="loadingView num3"  id="switchTo3rdF10To14">-</td>
                <td class="loadingView num3"  id="switchTo3rdF15To19">-</td>
                <td class="loadingView num3"  id="switchTo3rdF20To24">-</td>
                <td class="loadingView num3"  id="switchTo3rdFabove24">-</td>
                <td class="loadingView num3"  id="switchTo3rdTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="37" data-type="1" href="javascript:void(0);" >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># of OTZ members who have completed the 7 AYP modules
                <a href="javascript:void(0);" class="otzInfo" data-key="total_completed_7"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="completed7MBelow10">-</td>
                <td class="loadingView num3"  id="completed7M10To14">-</td>
                <td class="loadingView num3"  id="completed7M15To19">-</td>
                <td class="loadingView num3"  id="completed7M20To24">-</td>
                <td class="loadingView num3"  id="completed7Mabove24">-</td>
                
                <td class="loadingView num3"  id="completed7FBelow10">-</td>
                <td class="loadingView num3"  id="completed7F10To14">-</td>
                <td class="loadingView num3"  id="completed7F15To19">-</td>
                <td class="loadingView num3"  id="completed7F20To24">-</td>
                <td class="loadingView num3"  id="completed7Fabove24">-</td>
                <td class="loadingView num3"  id="completed7Total">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="38" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># transferred out during the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_transferredout_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="transferredOutMBelow10">-</td>
                <td class="loadingView num3"  id="transferredOutM10To14">-</td>
                <td class="loadingView num3"  id="transferredOutM15To19">-</td>
                <td class="loadingView num3"  id="transferredOutM20To24">-</td>
                <td class="loadingView num3"  id="transferredOutMabove24">-</td>
                
                <td class="loadingView num3"  id="transferredOutFBelow10">-</td>
                <td class="loadingView num3"  id="transferredOutF10To14">-</td>
                <td class="loadingView num3"  id="transferredOutF15To19">-</td>
                <td class="loadingView num3"  id="transferredOutF20To24">-</td>
                <td class="loadingView num3"  id="transferredOutFabove24">-</td>
                <td class="loadingView num3"  id="transferredOutTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="39" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr><tr>
                <td><%= i++ %></td>
                <td># Lost to follow up during the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_ltfu_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="ltfuMBelow10">-</td>
                <td class="loadingView num3"  id="ltfuM10To14">-</td>
                <td class="loadingView num3"  id="ltfuM15To19">-</td>
                <td class="loadingView num3"  id="ltfuM20To24">-</td>
                <td class="loadingView num3"  id="ltfuMabove24">-</td>
                
                <td class="loadingView num3"  id="ltfuFBelow10">-</td>
                <td class="loadingView num3"  id="ltfuF10To14">-</td>
                <td class="loadingView num3"  id="ltfuF15To19">-</td>
                <td class="loadingView num3"  id="ltfuF20To24">-</td>
                <td class="loadingView num3"  id="ltfuFabove24">-</td>
                <td class="loadingView num3"  id="ltfuTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="40" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># reported dead during the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_dead_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="deadMBelow10">-</td>
                <td class="loadingView num3"  id="deadM10To14">-</td>
                <td class="loadingView num3"  id="deadM15To19">-</td>
                <td class="loadingView num3"  id="deadM20To24">-</td>
                <td class="loadingView num3"  id="deadMabove24">-</td>
                
                <td class="loadingView num3"  id="deadFBelow10">-</td>
                <td class="loadingView num3"  id="deadF10To14">-</td>
                <td class="loadingView num3"  id="deadF15To19">-</td>
                <td class="loadingView num3"  id="deadF20To24">-</td>
                <td class="loadingView num3"  id="deadFabove24">-</td>
                <td class="loadingView num3"  id="deadTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="41" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># that opted out of OTZ during the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_opted_out_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="optedOutMBelow10">-</td>
                <td class="loadingView num3"  id="optedOutM10To14">-</td>
                <td class="loadingView num3"  id="optedOutM15To19">-</td>
                <td class="loadingView num3"  id="optedOutM20To24">-</td>
                <td class="loadingView num3"  id="optedOutMabove24">-</td>
                
                <td class="loadingView num3"  id="optedOutFBelow10">-</td>
                <td class="loadingView num3"  id="optedOutF10To14">-</td>
                <td class="loadingView num3"  id="optedOutF15To19">-</td>
                <td class="loadingView num3"  id="optedOutF20To24">-</td>
                <td class="loadingView num3"  id="optedOutFabove24">-</td>
                <td class="loadingView num3"  id="optedOutTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="42" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># aged 20-24 years and transitioned to adult care during the follow up  period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_transitioned_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="transitionedMBelow10">-</td>
                 <td class="loadingView num3"  id="transitionedM10To14">-</td>
                <td class="loadingView num3"  id="transitionedM15To19">-</td>
                <td class="loadingView num3"  id="transitionedM20To24">-</td>
                <td class="loadingView num3"  id="transitionedMabove24">-</td>
                
                <td class="loadingView num3"  id="transitionedFBelow10">-</td>
                <td class="loadingView num3"  id="transitionedF10To14">-</td>
                <td class="loadingView num3"  id="transitionedF15To19">-</td>
                <td class="loadingView num3"  id="transitionedF20To24">-</td>
                <td class="loadingView num3"  id="transitionedFabove24">-</td>
                <td class="loadingView num3"  id="transitionedTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="43" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++ %></td>
                <td># that exited OTZ during the follow up period
                <a href="javascript:void(0);" class="otzInfo" data-key="total_exited_followup"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num3"  id="exitedMBelow10">-</td>
                <td class="loadingView num3"  id="exitedM10To14">-</td>
                <td class="loadingView num3"  id="exitedM15To19">-</td>
                <td class="loadingView num3"  id="exitedM20To24">-</td>
                <td class="loadingView num3"  id="exitedMabove24">-</td>
                
                <td class="loadingView num3"  id="exitedFBelow10">-</td>
                <td class="loadingView num3"  id="exitedF10To14">-</td>
                <td class="loadingView num3"  id="exitedF15To19">-</td>
                <td class="loadingView num3"  id="exitedF20To24">-</td>
                <td class="loadingView num3"  id="exitedFabove24">-</td>
                <td class="loadingView num3"  id="exitedTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="44" data-type="1" href="javascript:void(0);"  >View List </a>

                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%= i++%></td>
                <td># of AYPLHIV enrolled in OTZ in the cohort month with full disclosure<a href="javascript:void(0);" class="otzInfo" data-key="total_full_disclosure"  data-toggle="modal"  data-target="#descriptionModal"><i class=" fa-2x icon-question-sign"></i></a></td>
                <td class="loadingView num4"  id="totalFullDiscMBelow10">-</td>
                <td class="loadingView num4"  id="totalFullDiscM10To14">-</td>
                <td class="loadingView num4"  id="totalFullDiscM15To19">-</td>
                <td class="loadingView num4"  id="totalFullDiscM20To24">-</td>
                <td class="loadingView num4"  id="totalFullDiscMabove24">-</td>
                
                <td class="loadingView num4"  id="totalFullDiscFBelow10">-</td>
                <td class="loadingView num4"  id="totalFullDiscF10To14">-</td>
                <td class="loadingView num4"  id="totalFullDiscF15To19">-</td>
                <td class="loadingView num4"  id="totalFullDiscF20To24">-</td>
                <td class="loadingView num4"  id="totalFullDiscFabove24">-</td>
                <td class="loadingView num4"  id="totalFullDiscTotal">-</td>
                <td>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="btn-group">
                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"> <span data-bind="label">Action</span>&nbsp;<span class="caret"></span>

                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item otzDetails" data-subset="45" data-type="1" href="javascript:void(0);"  >View List </a>

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
    <div class="modal-content" style="width:70%; margin:0 auto;">
      <div class="modal-header">
        <h5 class="modal-title" id="modalTitle">Indicator Description</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <div class="row">
              <div class="col-sm-12 col-md-4"><strong >title</strong></div>
              <div class="col-sm-12 col-md-8"><p id="otzIndicatorTitle"></p></div>
              <hr class="niceDarkHr"/>
          </div>
          
          <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Description</strong></div>
              <div class="col-sm-12 col-md-8"><p id="otzIndicatorDescription"></p></div>
              <hr class="niceDarkHr"/>
          </div>
          <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Source</strong></div>
              <div class="col-sm-12 col-md-8"><p id="otzIndicatorSource"></p></div>
              <hr class="niceDarkHr"/>
          </div>
           <div class="row">
              <div class="col-sm-12 col-md-4"><strong >Calculation</strong></div>
              <div class="col-sm-12 col-md-8"><p id="otzIndicatorCalculation"></p></div>
              <hr class="niceDarkHr"/>
          </div>
         
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

