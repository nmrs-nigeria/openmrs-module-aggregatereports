
    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-6">
            <h3>Proportion of AYPLHIV who have completed the 7 modules</h3>
            <div id="chartTotalCompleted" style="height:650px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>Losses from OTZ</h3>
            <div id="chartOTZLosses" style="height:650px;"></div>
        </div>
    </div>
    
    <div class="row" style="width:110% !important">
      
        <div class="col-sm-12 col-md-6">
            <h3>Exits from OTZ</h3>
            <div id="chartOTZExits" style="height:500px;"></div>
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
                    noCompleted:noCompleted
                   
                 }

             );
             var legendData = [{color:"#589BD4", title:"# of AYPLHIV  enrolled in OTZ"},
                    {color:"#1ac742", title:"# of AYPLHIV in OTZ who have completed the 7 modules"},  
                   
                    
             ]
                 var graphArray = [
                    {field: "noEnrolled", color:"#589BD4", description:"title", title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"totalInOtz", chartType:"otz"},
                    {field: "noCompleted", color:"#1ac742", description:"title", title:"# of AYPLHIV in OTZ who have completed the 7 modules", isOptimum:"totalInOtz", chartType:"otz"},
                   ]
          //create chart
          buildBarCharts("# of members", otzData, graphArray, "title", "chartTotalCompleted", "", "none", legendData, true, false);
}

function renderLossesChart(totalDead, totalTransferred, totalLTFU)
{
    var legendData = [{color:"#A0CEF0", title:"Dead"}, {color:"#B8B8B8", title:"Transferred out"},
                    {color:"#eba613", title:"IIT"},      
             ]
          
    //Total enrolled in OTZ by Sex distribution
     var totalLosses = new Array();
     totalLosses.push({title: "Dead", value: totalDead, weightBand:"above_30", regimen:"male", isOptimum:"false" })
     totalLosses.push({title: "Transferred out", value: totalTransferred, weightBand:"above_30", regimen:"female", isOptimum:"false" });
     totalLosses.push({title: "IIT", value: totalLTFU, weightBand:"above_30", regimen:"female", isOptimum:"false" });
     var colors = ["#A0CEF0", "#B8B8B8", "#eba613"]

      buildPieCharts(totalLosses, "value", "title", "chartOTZLosses", false, false, colors, legendData);

}

function renderExitChart(exited, transitioned, optedOut)
{
      var  legendData = [{color:"#dbc518", title:"Exited"}, {color:"#0fbf3e", title:"Transitioned"},
                    {color:"#bf700f", title:"Opted out"},      
             ]
          
          //Total enrolled in OTZ by Sex distribution
           var totalLosses = new Array();
           totalLosses.push({title: "Exited", value: exited, weightBand:"above_30", regimen:"male", isOptimum:"false" })
           totalLosses.push({title: "Transitioned", value: transitioned, weightBand:"above_30", regimen:"female", isOptimum:"false" });
           totalLosses.push({title: "Opted out", value: optedOut, weightBand:"above_30", regimen:"female", isOptimum:"false" });
           var colors = ["#dbc518", "#0fbf3e", "#bf700f"]

           buildPieCharts(totalLosses, "value", "title", "chartOTZExits", false, true, colors, legendData);

}


jq(document).ready(function(){
    

});
 </script>
    