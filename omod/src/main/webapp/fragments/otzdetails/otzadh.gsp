<h3>Adherence Charts</h3>


    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-6">
            <h3>Drug Pickup appointment</h3>
            <div id="chartDrugAppointment" style="height:650px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>Drug Adherence Score</h3>
            <div id="chartAdherenceScore" style="height:650px;"></div>
        </div>
    </div>
    
<script type="text/javascript">
 
var jq = jQuery;


function buildAdhCharts(drugPickupData, adherenceData, appointmentMin, appointmentMax, adhMin, adhMax )
{
    var legendData = [{color:"#589BD4", title:"# of OTZ members with scheduled drug pickup appointment in the last 6 months"},
                    {color:"#A1a1a1", title:"# of OTZ members who kept their appointment"},     
                    {color:"#bf6c19", title:"% who kept drug pickup appointment"}     
             ]
    var graphArray = [
       {field: "withAppointment", valueAxis:1, type:"column", color:"#589BD4", description:"title", title:"# of OTZ members with scheduled drug pickup appointment in the last 6 months", isOptimum:"pickupapp", chartType:"adhotz"},
       {field: "keptAppointment", valueAxis:1, type:"column", color:"#A1a1a1", description:"title", title:"# of OTZ members who kept their appointment", isOptimum:"keptapp", chartType:"adhotz"},
        {field: "percentage", type:"line", valueAxis:2,  color:"#bf6c19", description:"title", title:"% who kept drug pickup appointment", isOptimum:"totalInOtz", chartType:"otz"},
      ]
               //create chart
    dataQuality_buildBarCharts("# of members", drugPickupData, graphArray, "title", "chartDrugAppointment", "", "none", legendData, true, false, appointmentMin, appointmentMax);
    
    
      var legendData = [{color:"#589BD4", title:"# of OTZ members who picked up drug"},
                    {color:"#A1a1a1", title:"# of OTZ members with good adherence Score"},     
                    {color:"#bf6c19", title:"% with good drug adherence score"}     
             ]
            var graphArray2 = [
               {field: "noPickedupDrug", color:"#589BD4",  valueAxis:1, type:"column", description:"title", title:"# of OTZ members who picked up drug", isOptimum:"keptapp", chartType:"adhotz"},
               {field: "noWithGoodScore", color:"#A1a1a1",   valueAxis:1, type:"column", description:"title", title:"# of OTZ members with good adherence Score", isOptimum:"goodscore", chartType:"adhotz"},
               {field: "percentage", type:"line", valueAxis:2,  color:"#bf6c19", description:"title", title:"% with good drug adherence score", isOptimum:"totalInOtz", chartType:"otz"},
              ]
               //create chart
  dataQuality_buildBarCharts("# of members", adherenceData, graphArray2, "title", "chartAdherenceScore", "", "none", legendData, true, false, adhMin, adhMax);
}
jq(document).ready(function(){


});
 </script>
    