
    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-6">
            <h3>Proportion of AYPLHIV who have completed the 7 modules</h3>
            <div id="chartTotalCompleted" style="height:650px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>Full Disclosure</h3>
            <div id="chartOTZFullDisc" style="height:650px;"></div>
        </div>
    </div>
    
    <br/>
    <br/>
    <div class="row" style="width:110% !important">
      
        <div class="col-sm-12 col-md-6">
            <h3>Losses from OTZ</h3>
            <div id="chartOTZLosses" style="height:500px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>Exits from OTZ</h3>
            <div id="chartOTZExits" style="height:500px;"></div>
        </div>
    </div>
    <br/>
    <br/>
    <div class="row" style="width:110% !important">
     
        <div class="col-sm-12 col-md-6">
            <h3>Enrolled VS Exits</h3>
            <div id="chartOTZExits2" style="height:600px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>Enrolled Vs Transferred out</h3>
            <div id="chartOTZTransferred" style="height:600px;"></div>
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
                    {field: "noEnrolled", color:"#589BD4", type:"column", valueAxis:1, description:"title", title:"# of AYPLHIV  enrolled in OTZ", isOptimum:"enrolled", chartType:"basictable"},
                    {field: "noCompleted", color:"#1ac742", type:"column", valueAxis:1, description:"title", title:"# of AYPLHIV in OTZ who have completed the 7 modules", isOptimum:"completedmodules", chartType:"basictable"},
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

function renderLossesChart(totalDead, totalTransferred, totalLTFU)
{
    var legendData = [{color:"#A0CEF0", title:"Dead"}, {color:"#B8B8B8", title:"Transferred out"},
                    {color:"#eba613", title:"IIT"},      
             ]
          
    //Total enrolled in OTZ by Sex distribution
     var totalLosses = new Array();
     
     totalLosses.push({title: "Dead", value: totalDead, weightBand:"above_30", regimen:"male", isOptimum:"dead", chartType:"dead" })
     totalLosses.push({title: "Transferred out", value: totalTransferred, weightBand:"above_30", regimen:"female", isOptimum:"false",isOptimum:"transferred", chartType:"transferred"  });
     totalLosses.push({title: "IIT", value: totalLTFU, weightBand:"above_30", regimen:"female", isOptimum:"false", isOptimum:"iit", chartType:"iit"  });
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


jq(document).ready(function(){
    

});
 </script>
    