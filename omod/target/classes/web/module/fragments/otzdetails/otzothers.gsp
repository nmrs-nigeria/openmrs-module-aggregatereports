
    <div class="row" style="width:100% !important">
        <div class="col-sm-12 col-md-7">
            <h3 class="text-center">Proportion of AYPLHIV who have completed OTZ modules</h3>
            <div id="chartTotalCompleted" style="height:650px;"></div>
        </div>
        <div class="col-sm-12 col-md-5">
            <h3 class="text-center">Full Disclosure</h3>
            <div id="chartOTZFullDisc" style="height:650px;"></div>
        </div>
    </div>
    
    <br><br><br>
    <hr style="height:1px; width:100%; border-width:0; color:red; background-color:grey">
    
    <br><br>
    <h3 class="text-center">OUTCOMES</h3>
    <div class="row" style="width:100% !important">
      
        <div class="col-sm-12 col-md-12">
            <h3 class="text-center" style="display: none">Losses from OTZ</h3>
            <div id="chartOTZOutcomesCombo" style="height:500px;"></div>
        </div>
    </div>
    
     <br><br>
     
    <h3 class="text-center" style="display: none">OUTCOMES</h3>
    <div class="row" style="width:100% !important;display: none">
      
        <div class="col-sm-12 col-md-6">
            <h3 class="text-center" style="display: none">Losses from OTZ</h3>
            <div id="chartOTZLosses" style="height:400px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3 class="text-center" style="display: none">Exits from OTZ</h3>
            <div id="chartOTZExits" style="height:400px;"></div>
        </div>
    </div>
    
   
    <div class="row" style="width:100% !important; display: none">
     
        <div class="col-sm-12 col-md-6">
            <h3 class="text-center">Enrolled vs Exits</h3>
            <div id="chartOTZExits2" style="height:600px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3 class="text-center">Enrolled vs Transferred out</h3>
            <div id="chartOTZTransferred" style="height:600px;"></div>
        </div>
    </div>
    
    <!-- MOD Here -->
    <br><br><br>
    <hr style="height:1px; width:100%; border-width:0; color:red; background-color:grey">
    
    <br><br>
    <div class="row" style="width:100% !important">
     
        <div class="col-sm-12 col-md-6">
            <h3 class="text-center">Enrolled in OTZ vs IIT</h3>
            <div id="chartOTZIIT" style="height:600px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3 class="text-center">Enrolled in OTZ vs Transitioned to Adult Care</h3>
            <div id="chartOTZTTAC" style="height:600px;"></div>
        </div>
    </div>

    
 <script type="text/javascript">
 
var jq = jQuery;
function renderCompletedCharts(noEnrolled, noCompleted)
{
    var otzData = new Array();
     otzData.push(
                {
                    title:"",
                    noEnrolled:noEnrolled,
                    noCompleted:noCompleted,
                    percentage:(noCompleted/noEnrolled  * 100).toFixed(1)
                   
                 }

             );
             var legendData = [{color:"#589BD4", title:"# of AYPLHIV  enrolled in OTZ"},
                    {color:"#1ac742", title:"# of AYPLHIV in OTZ who have completed the 7 modules"},  
                   {color:"#bf700f", title:"% of AYPLHIV in OTZ who have completed the 7 modules"},  
                   
                    
             ]
                 var graphArray = [
                    {field: "noEnrolled", color:"#589BD4", type:"column", valueAxis:1, description:"title", title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"enrolled", chartType:"basictable"},
                    {field: "noCompleted", color:"#1ac742", type:"column", valueAxis:1, description:"title", title:"# of AYPLHIV in OTZ who have completed the 7 modules", isOptimum:"completedmodules", chartType:"basictable"},
                    {field: "percentage", color:"#bf700f", type:"line", valueAxis:2, description:"title", title:"% of AYPLHIV in OTZ who have completed the 7 modules", isOptimum:"", chartType:""},
                   ]
          //create chart
          var maxValue = noEnrolled + 5;
          dataQuality_buildBarCharts("# of members", otzData, graphArray, "title", "chartTotalCompleted", "", "none", legendData, true, false, 0, maxValue);
}
function renderFullDisclosure(noEnrolled, noFullDisclosure)
{
       var otzData = new Array();
     otzData.push(
                {
                    title:"",
                    noEnrolled:noEnrolled,
                    noFullDisclosure:noFullDisclosure
                   
                 }

             );
             var legendData = [{color:"#589BD4", title:"# of AYPLHIV  enrolled in OTZ"},
                    {color:"#1ac742", title:"# of AYPLHIV in OTZ with full disclosure"},  
                   
 
             ]
                 var graphArray = [
                    {field: "noEnrolled", color:"#589BD4", type:"column", valueAxis:1, description:"title", title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"enrolled", chartType:"basictable"},
                    {field: "noFullDisclosure", color:"#1ac742", type:"column", valueAxis:1, description:"title", title:"# of AYPLHIV in OTZ with full disclosure", isOptimum:"disclosure", chartType:"disclosure"},
                   ]
                   
          var maxValue = noEnrolled +5;
          //create chart
          dataQuality_buildBarCharts("# of members", otzData, graphArray, "title", "chartOTZFullDisc", "", "none", legendData, true, false, 0, maxValue);
}

function renderLossesChart(totalDead, totalTransferred)
{
    var legendData = [{color:"#A0CEF0", title:"Dead"+" ("+totalDead+")"}, {color:"#B8B8B8", title:"Transferred out"+" ("+totalTransferred+")"},     
             ]
          
    //Total enrolled in OTZ by Sex distribution
     var totalLosses = new Array();
     
     totalLosses.push({title: "Dead", value: totalDead, weightBand:"above_30", regimen:"male", isOptimum:"dead", chartType:"dead" })
     totalLosses.push({title: "Transferred out", value: totalTransferred, weightBand:"above_30", regimen:"female", isOptimum:"false",isOptimum:"transferred", chartType:"transferred"  });
     var colors = ["#A0CEF0", "#B8B8B8", "#eba613"]

      buildPieCharts(totalLosses, "value", "title", "chartOTZLosses", false, false, colors, legendData);

}

function renderExitChart(exited, transitioned, optedOut)
{
      var  legendData = [{color:"#dbc518", title:"Exited"+" ("+exited+")"}, {color:"#0fbf3e", title:"Transitioned"+" ("+transitioned+")"},
                    {color:"#bf700f", title:"Opted out"+" ("+optedOut+")"},      
             ]
          
          //Total enrolled in OTZ by Sex distribution
           var totalLosses = new Array();
           totalLosses.push({title: "Exited", value: exited, weightBand:"above_30", regimen:"male",  isOptimum:"exitedotz", chartType:"exitedotz"  })
           totalLosses.push({title: "Transitioned", value: transitioned, weightBand:"above_30", regimen:"female",isOptimum:"transitioned", chartType:"transitioned"  });
           totalLosses.push({title: "Opted out", value: optedOut, weightBand:"above_30", regimen:"female", isOptimum:"optedout", chartType:"optedout"  });
           var colors = ["#dbc518", "#0fbf3e", "#bf700f"]

           buildPieCharts(totalLosses, "value", "title", "chartOTZExits", false, true, colors, legendData);

}

function renderEnrolledVsExits(totalEnrolled, exits)
{
     var otzData = new Array();
     otzData.push(
                {
                    title:"",
                    noEnrolled:totalEnrolled,
                    noExits:exits
                   
                 }

             );
             var legendData = [{color:"#589BD4", title:"# of AYPLHIV  enrolled in OTZ"},
                    {color:"#B27130", title:"# of AYPLHIV in OTZ who exited OTZ"},  
                        
             ]
                 var graphArray = [
                    {field: "noEnrolled", color:"#589BD4", description:"title", type:"column", valueAxis:1, title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"enrolled", chartType:"basictable"},
                    {field: "noExits", color:"#B27130", description:"title", type:"column", valueAxis:1, title:"# of AYPLHIV in OTZ who exited OTZ", isOptimum:"exitedotz", chartType:"exitedotz"},
                   ]
          //create chart
          dataQuality_buildBarCharts("# of members", otzData, graphArray, "title", "chartOTZExits2", "", "none", legendData, true, false, 0, (totalEnrolled+5));
}

function renderEnrolledVsTransferred(totalEnrolled, transferred)
{
     var otzData = new Array();
     otzData.push(
                {
                    title:"",
                    noEnrolled:totalEnrolled,
                    transferred:transferred
                   
                 }

             );
             var legendData = [{color:"#589BD4", title:"# of AYPLHIV  enrolled in OTZ"},
                    {color:"#B27130", title:"# of AYPLHIV in OTZ who were transferred out"},  
                        
             ]
                 var graphArray = [
                    {field: "noEnrolled", color:"#589BD4", description:"title", type:"column", valueAxis:1, title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"enrolled", chartType:"basictable"},
                    {field: "transferred", color:"#B27130", description:"title", type:"column", valueAxis:1, title:"# of AYPLHIV in OTZ who were transferred out", isOptimum:"transferred", chartType:"transferred"},
                   ]
          //create chart
          dataQuality_buildBarCharts("# of members", otzData, graphArray, "title", "chartOTZTransferred", "", "none", legendData, true, false, );
}

// MOD Here
function renderEnrolledVsIIT(totalEnrolled, totalLTFU)
{
     var otzData = new Array();
     otzData.push(
                {
                    title:"",
                    noEnrolled:totalEnrolled,
                    tLTFU:totalLTFU
                   
                 }

             );
             var legendData = [{color:"#589BD4", title:"# of AYPLHIV  enrolled in OTZ"},
                    {color:"#eba613", title:"# IIT"},  
                        
             ]
                 var graphArray = [
                    {field: "noEnrolled", color:"#589BD4", description:"title", type:"column", valueAxis:1, title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"enrolled", chartType:"basictable"},
                    {field: "tLTFU", color:"#eba613", description:"title", type:"column", valueAxis:1, title:"# IIT", isOptimum:"iit", chartType:"transferred"},
                   ]
          //create chart
          
           var min = dataQuality_calculateMin(totalEnrolled, totalLTFU);
           var max = dataQuality_calculateMax(totalEnrolled, totalLTFU)
          dataQuality_buildBarCharts("# of members", otzData, graphArray, "title", "chartOTZIIT", "", "none", legendData, true, false, min, max );
}

// MOD Here 2
function renderEnrolledVsTTAC(totalEnrolled, transitioned)
{
     var otzData = new Array();
     otzData.push(
                {
                    title:"",
                    noEnrolled:totalEnrolled,
                    tTransitioned:transitioned
                   
                 }

             );
             var legendData = [{color:"#589BD4", title:"# of AYPLHIV  enrolled in OTZ"},
                    {color:"#0fbf3e", title:"# Transitioned to Adult Care"},  
                        
             ]
                 var graphArray = [
                    {field: "noEnrolled", color:"#589BD4", description:"title", type:"column", valueAxis:1, title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"enrolled", chartType:"basictable"},
                    {field: "tTransitioned", color:"#0fbf3e", description:"title", type:"column", valueAxis:1, title:"# Transitioned to Adult Care", isOptimum:"allPatTO", chartType:"transferred"},
                   ]
          //create chart
          dataQuality_buildBarCharts("# of members", otzData, graphArray, "title", "chartOTZTTAC", "", "none", legendData, true, false, );
}

//MOD Here 3
function renderAllOUTCOMES(totalDead, totalTransferred, exited, optedOut)
{
    var legendData = [{color:"#A0CEF0", title:"Dead"+" ("+totalDead+")"}, {color:"#B8B8B8", title:"Transferred out"+" ("+totalTransferred+")"}, 
                      {color:"#dbc518", title:"Exited"+" ("+exited+")"}, {color:"#bf700f", title:"Opted out"+" ("+optedOut+")"}
             ]
          
    //Total enrolled in OTZ by Sex distribution
     var totalLosses = new Array();
     
     totalLosses.push({title: "Dead", value: totalDead, weightBand:"above_30", regimen:"male", isOptimum:"dead", chartType:"dead" })
     totalLosses.push({title: "Transferred out", value: totalTransferred, weightBand:"above_30", regimen:"female", isOptimum:"false",isOptimum:"transferred", chartType:"transferred"  })
     totalLosses.push({title: "Exited", value: exited, weightBand:"above_30", regimen:"male",  isOptimum:"exitedotz", chartType:"exitedotz"  })
     totalLosses.push({title: "Opted out", value: optedOut, weightBand:"above_30", regimen:"female", isOptimum:"optedout", chartType:"optedout"  });
     var colors = ["#A0CEF0", "#B8B8B8", "#dbc518", "#bf700f"]

      buildPieCharts(totalLosses, "value", "title", "chartOTZOutcomesCombo", false, false, colors, legendData);

}

jq(document).ready(function(){
    

});
 </script>
    