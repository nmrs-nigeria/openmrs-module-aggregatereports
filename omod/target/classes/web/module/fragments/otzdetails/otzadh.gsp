<h3>VL Charts</h3>


    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-6">
            <h3>Drug Pickup appointment</h3>
            <div id="chartDrugAppointment" style="height:500px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>Medication Adherence Score</h3>
            <div id="chartAdherenceScore" style="height:500px;"></div>
        </div>
    </div>
    
<script type="text/javascript">
 
var jq = jQuery;


function buildAdhCharts(drugPickupData, adherenceData)
{
    var legendData = [];
    var graphArray = [
       {field: "withAppointment", color:"#589BD4", description:"title", title:"# of OTZ members with scheduled drug pickup appointment in the last 6 months", isOptimum:"totalInOtz", chartType:"otz"},
       {field: "keptAppointment", color:"#A1a1a1", description:"title", title:"# of OTZ members who kept their appointment", isOptimum:"totalInOtz", chartType:"otz"},
      ]
               //create chart
    buildBarCharts("# of members", drugPickupData, graphArray, "title", "chartDrugAppointment", "", "none", legendData, true, false);
    
    
     var legendData = [];
            var graphArray2 = [
               {field: "noPickedupDrug", color:"#589BD4", description:"title", title:"# of OTZ members who picked up drug", isOptimum:"totalInOtz", chartType:"otz"},
               {field: "noWithGoodScore", color:"#A1a1a1", description:"title", title:"# of OTZ members with good adherence Score", isOptimum:"totalInOtz", chartType:"otz"},
              ]
               //create chart
          buildBarCharts("# of members", adherenceData, graphArray2, "title", "chartAdherenceScore", "", "none", legendData, true, false);
}
jq(document).ready(function(){


});
 </script>
    