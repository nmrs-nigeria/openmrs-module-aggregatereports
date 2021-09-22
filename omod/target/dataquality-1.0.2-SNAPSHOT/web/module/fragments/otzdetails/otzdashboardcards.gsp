
<br/>
    <div class="row" >
        <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-green1 showDetails" href="javascript:void(0);" data-isOptimum="true" data-weight="less_20" data-regimen=""  data-title="Total AYPLHIV enrolled in OTZ"  >
               <div class="row">
                   <div class="col-sm-5"><i class="icon-info  fa fa-users"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="txCurrAyP">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-info" id="optimal_less_20_percent">Tx Curr for AYPLHIVs in facility</span>
            </a>
            
        </div>
        
       <div class="col-sm-4 col-md-8">
            <a class="bootcards-summary-item label-white showDetails" href="javascript:void(0);" data-isOptimum="true" data-weight="less_20" data-regimen=""  data-title="Total AYPLHIV enrolled in OTZ"  >
               <div class="row">
                   <div class="col-sm-5"><i class="icon-success icon-user"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-success" id="totalEnrolledInOTZ">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-success" id="optimal_less_20_percent">Total AYPLHIV enrolled in OTZ</span>
            </a>
            
        </div>
       
    </div>
    <br/>
    
    <div class="row" >
       
         <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-info showDetails" href="javascript:void(0);" data-isOptimum="true" data-weight="bet_20_30" data-regimen="" data-title="Total Male AYPLHIV enrolled in OTZ" >
               <div class="row">
                   <div class="col-sm-5"><i class=" icon-info fa icon-bar-chart"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="aypEnrolled">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-success" id="optimal_20_30_percent">Proportion of AYPLHIVs enrolled in OTZ</span>

            </a>
        </div>
        <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-green2 showDetails" href="javascript:void(0);" data-isOptimum="true" data-weight="bet_20_30" data-regimen="" data-title="Total Male AYPLHIV enrolled in OTZ" >
               <div class="row">
                   <div class="col-sm-5"><i class=" icon-info fa fa-male"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="totalEnrolledInOTZMale">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-warning" id="optimal_20_30_percent">Total Male AYPLHIV enrolled in OTZ</span>

            </a>
        </div>
        <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-success showDetails" href="javascript:void(0);" data-isOptimum="true" data-weight="above_30" data-regimen="" data-title="Total Female AYPLHIV enrolled in OTZ">
               <div class="row">
                   <div class="col-sm-5"><i class=" icon-info fa fa-female"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="totalEnrolledInOTZFemale">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-info" id="optimal_above_30_percent">Total Female AYPLHIV enrolled in OTZ</span>

            </a>
            <!--<h3 class="centertext"><strong > >30kg </strong></h3>-->
        </div>
        
        
    </div>
    
    <br/>
    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-6">
            <h3>Number of AYPLHIV enrolled in OTZ</h3>
            <div id="chartTotalEnrolled" style="height:500px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>AYPLHIV enrolled in OTZ by Sex Distribution</h3>
            <div id="chartTotalEnrolledGender" style="height:500px;"></div>
        </div>
    </div>
    
    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-6">
            <h3>Proportion of  Tx_Curr AYPLHIV enrolled in OTZ</h3>
            <div id="proportionOfTxCurrInOtz" style="height:500px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>AYPLHIV enrolled in OTZ by Age Bands</h3>
            <div id="chartTotalEnrolledAge" style="height:500px;"></div>
        </div>
    </div>
    
 <script type="text/javascript">
 
  
var jq = jQuery; 
var totalEnrolled = 0;
var totalTxCurr = 0;
var totalMale = 0;
var totalFemale = 0;
var total1014 = 0;
var total1519 = 0;
var total2024 = 0;
 
 function setCardValues(data)
 {
    var male1014 = data["male10To14"];
    var male1519 = data["male15To19"];
    var male2024 = data["male20To24"];
    var female1014 = data["female10To14"];
    var female1519 = data["female15To19"];
    var female2024 = data["female20To24"];
    
    var totalMale = parseFloat(male1014) + parseFloat(male1519) + parseFloat(male2024);
    var totalFemale = parseFloat(female1014) + parseFloat(female1519) + parseFloat(female2024);
    totalEnrolled = totalMale + totalFemale;
    jq("#totalEnrolledInOTZ").html(totalEnrolled);
    jq("#totalEnrolledInOTZFemale").html(totalFemale);
    jq("#totalEnrolledInOTZMale").html(totalMale);
    
    getDashboardValues();
    getVlData();
    
 }
 


 function getDashboardValues(){
    
    myAjax({startDate:startDate, endDate:endDate}, "otz/getTxCurr.action").then(function(response){
        var data = JSON.parse(response);
        totalTxCurr = data.length;
        jq("#txCurrAyP").html(totalTxCurr);
        
        var proportionEnrolled = (totalEnrolled/totalTxCurr) * 100;
        jq("#aypEnrolled").html(proportionEnrolled.toFixed(1)+"%");
        
        
        return  myAjax({startDate:startDate, endDate:endDate}, "otz/getAYPLHIVEnrolled.action");

    }).then(function(response){
        
        totalMale = 0;
        totalFemale = 0;
        total1014 = 0;
        total1519 = 0;
        total2024 = 0;
         var data = JSON.parse(response);
         
         //lets do some work on the browser
         var quarters = data["quarters"]["myHashMap"];
         
         var patients = data["patients"];
         
         for(var i=0; i<patients.length; i++)
         {
             var quarter = dqr_getFiscalQuarter(patients[i]["enrollmentDate"]);
             quarters[quarter]++;
             if(patients[i]["age"] >= 10 && patients[i]["age"] <=24){
                if(patients[i]["gender"] == "Male" || patients[i]["gender"] == "M" )
                {
                   totalMale++;
                }
                else if(patients[i]["gender"] == "Female" || patients[i]["gender"] == "F" ){
                   totalFemale++;
                }
                
                if(patients[i]["age"] >= 10 && patients[i]["age"] <=14){
                    total1014++;
                    
                }
                if(patients[i]["age"] >= 15 && patients[i]["age"] <=19){
                    total1519++;
                    
                }
                if(patients[i]["age"] >= 20 && patients[i]["age"] <=24){
                    total2024++;
                    
                }
             }
         }
         
        var lastQuarter = "";
        var otzData = new Array();
        console.log(quarters);
        for(var key in quarters)
        {
            if(quarters.hasOwnProperty(key))
            {
                otzData.push(
                    {
                        quarter:key.toUpperCase(),
                        totalCount:quarters[key],
                        color:"#589BD4"
                     })
                //lastQuarter = key.toUpperCase();
            }
            
        }
        lastQuarter =  dqr_getFiscalQuarter(endDate).toUpperCase();
        
        /*var legendData = [{color:"#e35b17", title:"Patient with VL >=1000 cp/ml"}, {color:"#edc009", title:"Patient with VL between 200 - 999 cp/ml"},
                        {color:"#23ba58", title:"Patient with VL <200 cp/ml"},      
                 ]*/
        var legendData = [];


                     var graphArray = [
                        {field: "totalCount", description:"quarter", title:"Total AYPLHIV in OTZ", isOptimum:"totalInOtz", chartType:"otz"},
                       ]
                   //create chart
        buildBarCharts("# of members", otzData, graphArray, "quarter", "chartTotalEnrolled", "", "none", legendData, true, false);
        

        
        
        var totalByGender = new Array();
        totalByGender.push({title: "Male", value: totalMale, weightBand:"above_30", regimen:"male", isOptimum:"false" })
        totalByGender.push({title: "Female", value: totalFemale, weightBand:"above_30", regimen:"female", isOptimum:"false" });
        var colors = ["#A0CEF0", "#a903fc"]

         buildPieCharts(totalByGender, "value", "title", "chartTotalEnrolledGender", false, true, colors);



         var otzTxData = new Array();
         otzTxData.push(
             {
                 quarter:lastQuarter,
                 txCurr:totalTxCurr,
                 txCurrEnrolled:totalEnrolled
              }

          );
          
          legendData = [{color:"#63A6E2", title:"# of AYPLHIV currently on ART"}, {color:"#FB8537", title:"# of  AYPLHIV enrolled in OTZ"}      
                 ]


              var graphArray2 = [
                 {field: "txCurr", description:"quarter", title:"# of AYPLHIV currently on ART", isOptimum:"totalInOtz", chartType:"otz", color:"#63A6E2"},
                 {field: "txCurrEnrolled", description:"quarter", title:"# of  AYPLHIV enrolled in OTZ", isOptimum:"totalInOtz", chartType:"otz", color:"#FB8537"},
                ]
            //create chart
       buildBarCharts("# of members", otzTxData, graphArray2, "quarter", "proportionOfTxCurrInOtz", "", "none", legendData, true, false);

       
       //Total enrolled in OTZ by Sex distribution
               


               legendData = [{color:"#A0CEF0", title:"10-14"}, {color:"#d6d6c9", title:"15-19"},
                        {color:"#e0dd96", title:"20-24"},      
                 ]
              var totalByAge = new Array();
              totalByAge.push({title: "10-14", value: total1014, weightBand:"above_30", regimen:"10_14", isOptimum:"false" })
              totalByAge.push({title: "15-19", value: total1519, weightBand:"above_30", regimen:"15_19", isOptimum:"false" });
              totalByAge.push({title: "20-24", value: total2024, weightBand:"above_30", regimen:"20_24", isOptimum:"false" });
              var colors = ["#A0CEF0", "#d6d6c9", "#e0dd96"]

              buildPieCharts(totalByAge, "value", "title", "chartTotalEnrolledAge", false, false, colors, legendData);
        
    });

              

 } 


 </script>
    