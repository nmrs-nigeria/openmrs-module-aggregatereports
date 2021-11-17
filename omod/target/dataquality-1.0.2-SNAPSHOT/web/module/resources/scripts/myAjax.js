/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



//for some reason, the date picker does not appear directly below the input box. 
//This function will reposition it.
function setupDatePickerPositioner()
{
    /*jq(".date").unbind("click");
    jq(".date").click(function(e){
        var leftPosition = jq(this).offset().left;
        jq(".datepicker-dropdown").css("left", leftPosition+"px");
    })*/
   
}

function leapYear(year)
{
    return (year % 100 === 0) ? (year % 400 === 0) : (year % 4 === 0);
}

function getCohorts(type, cohortAjaxUrl, callback)
{
    jq = jQuery;
   /* jq.ajax({
        url:cohortAjaxUrl,
        type:"GET",
        data:{state:"start"},
        success:function(response){
            console.log(response);
        },
        error:function(xhr, status, error){
             console.log(error);
        }
    })*/
    jq.getJSON(cohortAjaxUrl,
            {
              type: type,
             
            }, function(data){
                callback(data);
            }, function(xhr, status, error){
                console.log(error);
            })
        
}


function getCohorts2(type, cohortAjaxUrl)
{
    let myPromise = new Promise(function(resolve, reject) {
    // "Producing Code" (May take some time)

      jq = jQuery;
   
        jq.getJSON(cohortAjaxUrl,
            {
              type: type,
             
            }, function(data){
                resolve(data)
            }, function(xhr, status, error){
                console.log(error);
                reject(error)
         })
         
    });
    
    return myPromise;
          
}

function myAjax(data, cohortAjaxUrl)
{
    let myPromise = new Promise(function(resolve, reject) {
    // "Producing Code" (May take some time)

      jq = jQuery;
   
        jq.getJSON(cohortAjaxUrl,
            
        data, function(data){
                resolve(data)
            }, function(xhr, status, error){
                console.log(error);
                reject(error)
         })
         
    });
    
    return myPromise;
          
}

function buildBarCharts(title, chartData, graphArray, categoryField, chartDiv, postFix, stackType, legendData, enableCategoryLabels=true, is3d=false, max=0, min=0)
{
    var chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = categoryField;
    //chart.color = "#333333";
    //chart.fontSize = 14;
    //chart.startDuration = 1;
    chart.plotAreaBorderAlpha = 0.2;
    // the following two lines makes chart 3D
    
    if(is3d == true){
        chart.angle = 30;
        chart.depth3D = 20;
    }

    chart.startDuration = 1;

    // AXESchartData2chartDiv
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.gridAlpha = 0.2;
    categoryAxis.gridPosition = "start";
    categoryAxis.gridColor = "#ffffff";
    categoryAxis.axisColor = "#333333";
    categoryAxis.axisAlpha = 0;
    categoryAxis.dashLength = 5;
    categoryAxis.labelsEnabled = enableCategoryLabels;

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.stackType = stackType; // This line makes chart 3D stacked (columns are placed one behind another)
    valueAxis.gridAlpha = 0.2;
    valueAxis.gridColor = "#333333";
    valueAxis.axisColor = "#333333";
    valueAxis.axisAlpha = 0;
    valueAxis.dashLength = 5;
    valueAxis.title = title;
    valueAxis.align = "left"
    valueAxis.titleColor = "#333333";
    valueAxis.unit = "";
     if(max >0){
        valueAxis.minimum = min;
        valueAxis.maximum = max;
        valueAxis.strictMinMax = true;
    }
    
    chart.addValueAxis(valueAxis);
    
    
    var valueAxis2 = new AmCharts.ValueAxis();
    valueAxis2.stackType = stackType; // This line makes chart 3D stacked (columns are placed one behind another)
    valueAxis2.gridAlpha = 0.2;
    valueAxis2.gridColor = "#333333";
    valueAxis2.axisColor = "#333333";
    valueAxis2.axisAlpha = 0;
    valueAxis2.dashLength = 5;
    valueAxis2.align = "right"
    valueAxis2.title = "testing";
    valueAxis2.titleColor = "#333333";
    valueAxis2.unit = "";
    
    chart.addValueAxis(valueAxis2);


    // CURSOR
    var chartCursor = new AmCharts.ChartCursor();
    chartCursor.cursorAlpha = 0;
    chartCursor.zoomable = true;
    chartCursor.categoryBalloonEnabled = false;
    chart.addChartCursor(chartCursor);

    chart.categoryAxis.labelRotation = 60;
    
    for(var i=0; i<graphArray.length; i++)
    {
                        
        var graph1 = new AmCharts.AmGraph();
        graph1.title = graphArray[i]["title"];
        graph1.weightBand = graphArray[i]["weightBand"];
        graph1.regimen = graphArray[i]["regimen"];
        graph1.isOptimum = graphArray[i]["isOptimum"];
         graph1.chartType = graphArray[i]["chartType"];
        graph1.valueField = graphArray[i]["field"];
        var description = graphArray[i]["description"];
        graph1.type = "column";
        
        graph1.lineAlpha = 0;
        graph1.labelText = "[[value]]"+postFix;
        graph1.regimenId = 1;
        if(typeof graphArray[i]["color"] == "undefined"){
            graph1.colorField = "color";
        }else{
            graph1.lineColor = graphArray[i]["color"];
        }
        
        //
        graph1.fillAlphas = 1;
        graph1.addListener("clickGraphItem", graphClickHandler)
        graph1.balloonText = graphArray[i]["title"]+" [["+description+"]]<br><span style='font-size:14px;'>[[value]]</span>"//graphArray[i]["description"]+" [[category]]: <b>[[value]]</b>"+postFix;
        chart.addGraph(graph1);
    }
    // LEGEND
    if(legendData.length > 0)
    {
        var legend = new AmCharts.AmLegend();
    
        legend.borderAlpha = 0.2;
        legend.labelWidth = 800;
        legend.labelWidth = 350;
        legend.data =  legendData//[{title: "One", color: "#3366CC"},{title: "Two", color: "#FFCC33"}];
        legend.horizontalGap = 2;
        chart.addLegend(legend);
    }

   chart.addListener("clickGraphItem", graphClickHandler)
   chart.write(chartDiv);
}


function dataQuality_calculateMin(max, min)
{
    var newMin = min - ((max + min)/2 );
    return (newMin >=0)?  newMin : 0;
}

function dataQuality_calculateMax(max, min)
{
    
    return max + (max * 0.2);
}


function dataQuality_buildBarCharts(title, chartData, graphArray, categoryField, chartDiv, postFix, stackType, legendData, enableCategoryLabels=true, is3d=false, min=0, max=0)
{
    var chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = categoryField;
    //chart.color = "#333333";
    //chart.fontSize = 14;
    //chart.startDuration = 1;
    chart.plotAreaBorderAlpha = 0.2;
    // the following two lines makes chart 3D
    
    if(is3d == true){
        chart.angle = 30;
        chart.depth3D = 20;
    }
    
    chart.startDuration = 1;
    // AXESchartData2chartDiv
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.gridAlpha = 0.2;
    categoryAxis.gridPosition = "start";
    categoryAxis.gridColor = "#ffffff";
    categoryAxis.axisColor = "#333333";
    categoryAxis.axisAlpha = 0;
    categoryAxis.dashLength = 5;
    categoryAxis.labelsEnabled = enableCategoryLabels;

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.stackType = stackType; // This line makes chart 3D stacked (columns are placed one behind another)
    valueAxis.gridAlpha = 0.2;
    valueAxis.gridColor = "#333333";
    valueAxis.axisColor = "#333333";
    valueAxis.axisAlpha = 0;
    valueAxis.dashLength = 5;
    valueAxis.title = title;
    valueAxis.position = "left"
    valueAxis.titleColor = "#333333";
    valueAxis.unit = "";
    
    if(max >0){
        valueAxis.minimum = min;
        valueAxis.maximum = max;
        valueAxis.strictMinMax = true;
    }
    chart.addValueAxis(valueAxis);
    
    var valueAxis2 = new AmCharts.ValueAxis();
    valueAxis2.stackType = stackType; // This line makes chart 3D stacked (columns are placed one behind another)
    valueAxis2.gridAlpha = 0.2;
    valueAxis2.gridColor = "#333333";
    valueAxis2.axisColor = "#333333";
    valueAxis2.axisAlpha = 0;
    valueAxis2.dashLength = 5;
    valueAxis2.position = "right"
    valueAxis2.title = "Percentage";
    valueAxis2.titleColor = "#333333";
    valueAxis2.unit = "";
    valueAxis2.minimum = 0;
    valueAxis2.maximum = 120;
    valueAxis2.strictMinMax = true;
    
    chart.addValueAxis(valueAxis2);
    

    // CURSOR
    var chartCursor = new AmCharts.ChartCursor();
    chartCursor.cursorAlpha = 0;
    chartCursor.zoomable = true;
    chartCursor.categoryBalloonEnabled = false;
    chart.addChartCursor(chartCursor);

    chart.categoryAxis.labelRotation = 60;
    
    for(var i=0; i<graphArray.length; i++)
    {
        
                        
        var graph1 = new AmCharts.AmGraph();
        
        graph1.title = graphArray[i]["title"];
        graph1.weightBand = graphArray[i]["weightBand"];
        graph1.regimen = graphArray[i]["regimen"];
        graph1.isOptimum = graphArray[i]["isOptimum"];
        graph1.chartType = graphArray[i]["chartType"];
        graph1.valueField = graphArray[i]["field"];
        graph1.labelColor = "#ffffff";
        var description = graphArray[i]["description"];
        graph1.type = graphArray[i]["type"];
        
        
        if(typeof graphArray[i]["newStack"] != "undefined")
        {

             graph1.newStack = graphArray[i]["newStack"];
        }
        
        
        if(graphArray[i]["type"] == "column")
        {
            graph1.fillAlphas = 1;
            graph1.lineAlpha = 0;
            graph1.labelText = "[[value]]"+postFix;
             graph1.balloonText = graphArray[i]["title"]+" [["+description+"]]<br><span style='font-size:14px;'>[[value]]</span>"//graphArray[i]["description"]+" [[category]]: <b>[[value]]</b>"+postFix;
        }
        else{
            graph1.bulletBorderAlpha = 1;
            graph1.bulletBorderThickness = 2;
            graph1.lineThickness = 2;
            graph1.bullet = "round";
            graph1.bulletSize = 8;
            graph1.labelText = "[[value]] %";
             graph1.balloonText = graphArray[i]["title"]+" [["+description+"]]<br><span style='font-size:14px;'>[[value]]%</span>"//graphArray[i]["description"]+" [[category]]: <b>[[value]]</b>"+postFix;
        }
        //graph1.periodValue = graphArray[i]["title"]+" [["+description+"]]<br><span style='font-size:14px;'>[[value]]</span>";
        
         
        
        
        graph1.regimenId = 1;
        if(typeof graphArray[i]["color"] == "undefined"){
            graph1.colorField = "color";
        }else{
            graph1.lineColor = graphArray[i]["color"];
        }
        
        
        if(graphArray[i]["valueAxis"] == 1){
            graph1.valueAxis = valueAxis;
        }
        else{
            graph1.valueAxis = valueAxis2;
        }
        
        
        
        //
        
        graph1.addListener("clickGraphItem", graphClickHandler)
       
        chart.addGraph(graph1);
    }
    // LEGEND
    if(legendData.length > 0)
    {
        var legend = new AmCharts.AmLegend();
    
        legend.borderAlpha = 0.2;
        legend.labelWidth = 800;
        legend.labelWidth = 350;
        legend.data =  legendData//[{title: "One", color: "#3366CC"},{title: "Two", color: "#FFCC33"}];
        legend.horizontalGap = 2;
        legend.verticalGap = 20;
        chart.addLegend(legend);
    }

   chart.addListener("clickGraphItem", graphClickHandler)
   chart.write(chartDiv);
}

function buildPieCharts(chartData, valueField, titleField, divId, is3d=false, isDonut=false, colors, legendData = new Array())
{
    var chart = new AmCharts.AmPieChart();
    chart.dataProvider = chartData;
    chart.titleField = titleField;
    chart.valueField = valueField;
    chart.outlineColor = "#FFFFFF";
    chart.outlineAlpha = 18;
    //chart
    chart.outlineThickness = 2;
    if(isDonut){
        chart.innerRadius = "70%";
    }
    chart.radius = 100;
    chart.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>";
    // this makes the chart 3D
    if(is3d == true){
        chart.depth3D = 15;
        chart.angle = 30;
    }
    if(colors.length == 0){
        chart.colors = ["#FF0F00", "#FF6600", "#FF9E01", "#FCD202", "#F8FF01", "#B0DE09", "#04D215", "#0D8ECF", "#0D52D1", "#2A0CD0", "#8A0CCF", "#CD0D74", "#754DEB", "#DDDDDD", "#999999", "#333333", "#000000", "#57032A", "#CA9726", "#990000", "#4B0C25"]
    }
    else{
        chart.colors = colors;
    }
    
     if(legendData.length > 0)
    {
        var legend = new AmCharts.AmLegend();
    
        legend.borderAlpha = 0.2;
        legend.data =  legendData;//[{title: "One", color: "#3366CC"},{title: "Two", color: "#FFCC33"}];
        legend.horizontalGap = 10;
        legend.labelWidth = 800;
        legend.labelWidth = 350;
        chart.addLegend(legend);
    }
    
    chart.addListener("clickSlice", pieClickHandler);
    // WRITE
    chart.write(divId);
}


function graphClickHandler(event)
{
    var jq = jQuery;
    var isOptimum = event.graph.isOptimum;
    var chartType = event.graph.chartType;
    
    
    var category = event.item.category;
   
    
    
    
    if(chartType == "vlotz"){
        dataquality_renderOTZData(isOptimum, category, "Line list");
    }
    
    else if(chartType == "adhotz")
    {
        dataquality_renderAdhOTZData(isOptimum, category, "Line list");
    }
    else if(isOptimum == "eac")
    {
        renderEACPatientDetails(event.item.category, "EAC");
    }
    else if(isOptimum == "cd4")
    {
        //console.log(event.item.category);
        renderCD4PatientDetails(event.item.category, "CD4+ Cell Count");
    }
    else if(isOptimum == "completedmodules")
    {
        dataquality_renderCompletedModulesOTZData(isOptimum, category, "Line list");
    }
    else if(isOptimum == "disclosure")
    {
        dataquality_renderFullDisclosureOTZData(isOptimum, category, "Line list");
    }
    else if(chartType == "basictable")
    {
        dataquality_renderBasicOTZData(isOptimum, category, "Line list");
    }
    else if(chartType == "exitedotz")
    {
        dataquality_renderExitedOTZData(isOptimum, category, "Line list");
    }
    else if(chartType == "transferred")
    {
        dataquality_renderTransferredOTZData(isOptimum, category, "Line list");
    }
    
 }

function pieClickHandler(event)
{
    var jq = jQuery;
   
    var isOptimum = event.dataItem.dataContext.isOptimum
    var chartType = event.dataItem.dataContext.chartType
    var category = event.dataItem.dataContext.category;
    var title = event.dataItem.dataContext.title;
   
   if(chartType == "vlotz"){
        dataquality_renderOTZData(isOptimum, category, "Line list");
   }
   else if(chartType == "transferred")
   {
       dataquality_renderTransferredOTZData(isOptimum, category, "Line list");
   }
   else if(chartType == "exitedotz")
   {
       dataquality_renderExitedOTZData(isOptimum, category, "Line list");
   }
   
}

var jq = jQuery;
jq(document).ready(function(){

    
})



function dataquality_renderOTZData(isOptimum, category,  title)
{
    jq("#tableContainer").html('<table class="dataTable2"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Gender</th><th nowrap>ART Start date</th><th nowrap>Date of Birth</th><th nowrap>Age</th><th nowrap>Date Enrolled in OTZ</th> <th nowrap>Sample Collection Date</th> <th nowrap>VL Result</th><th>Action</th> </tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    
    
    console.log(category, isOptimum);
    var fullData = new Array();
    if(isOptimum == "eligible6mts")
    {
        fullData = patientsEligibleObj[category];
    }
    else if(isOptimum == "sampleTaken6t")
    {
        fullData = patientsWithSampleObj[category];
    }else if(isOptimum == "withResult")
    {
        fullData = patientsWithResultObj[category];
    }else if(isOptimum == "undetectable6Months")
    {
        fullData = patientsUndetectablePast6MonthsObj[category];
    }
    else if(isOptimum == "undetectable12Months")
    {
        fullData = patientsUndetectablePast12MonthsObj[category];
    }
    else if(isOptimum == "llv6Months")
    {
        fullData = patientsLLVPast6MonthsObj[category];
    }
    else if(isOptimum == "llv12Months")
    {
        fullData = patientsLLVPast12MonthsObj[category];
    }
    else if(isOptimum == "withResult12Months")
    {
        fullData = patientsWithResultPast12MonthObj[category];
    }
    else if(isOptimum == "suppressed12Months")
    {
        fullData = patientsSuppressedPast12MonthsObj[category];
    }else if(isOptimum == "tx_curr")
    {
        fullData = txCurr;
    }
    else if(isOptimum == "enrolled")
    {
        fullData = enrolledPatients;
    }
    else if(isOptimum == "maleEnrolled")
    {
         fullData = maleEnrolled;
    }
    else if(isOptimum == "femaleEnrolled")
    {
        fullData = femaleEnrolled;
    }
    else if(isOptimum == "total10_14")
    {
        fullData = patients10_14;
    }
     else if(isOptimum == "total15_19")
    {
        fullData = patients15_19;
    }
     else if(isOptimum == "total20_24")
    {
        fullData = patients20_24;
    }
    
    
    
    var html = "";
   
    for(var i=0; i<fullData.length; i++)
    {  
        var patientData = fullData[i];
        var patientId = patientData["patientId"];
        html += "<tr>"; 
        html += "<td >"+(i+1)+"</td>";
        html += "<td>"+patientData["pepfarId"]+"</td>";
         html += "<td>"+patientData["gender"]+"</td>";
        //html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
        html += "<td>"+patientData["artStartDate"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["dob"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["age"]+"</td>";
        html += "<td>"+( (patientData["enrollmentDate"] == null) ?"-": patientData["enrollmentDate"].substring(0, 10) )+"</td>";
        html += "<td>"+((patientData["sampleCollectionDate"] == null) ? "N/A" : patientData["sampleCollectionDate"].substring(0, 10))+"</td>";
        html += "<td>"+((patientData["viralLoad"] == null) ? "N/A": patientData["viralLoad"])+"</td>";
        html += "<td><a class='button' target='_blank' href='/coreapps/clinicianfacing/patient.page?patientId="+patientId+"'>Patient Dashboard<a></td>"
        
        html += "</tr>";
   
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable2(title);
}


function dataquality_renderBasicOTZData(isOptimum, category,  title)
{
    jq("#tableContainer").html('<table class="dataTable2"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Gender</th><th nowrap>ART Start date</th><th nowrap>Date of Birth</th><th nowrap>Age</th><th nowrap>Date Enrolled in OTZ</th> <th>Action</th> </tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    
    
    console.log(category, isOptimum);
    var fullData = new Array();
   if(isOptimum == "enrolled")
    {
        fullData = enrolledPatients;
    }
    else if(isOptimum == "completedmodules")
    {
         fullData = completed7Modules;
    }
    else if(isOptimum == "femaleEnrolled")
    {
        fullData = femaleEnrolled;
    }
    else if(isOptimum == "total10_14")
    {
        fullData = patients10_14;
    }
     else if(isOptimum == "total15_19")
    {
        fullData = patients15_19;
    }
     else if(isOptimum == "total20_24")
    {
        fullData = patients20_24;
    }
    
    
    
    var html = "";
   
    for(var i=0; i<fullData.length; i++)
    {  
        var patientData = fullData[i];
        var patientId = patientData["patientId"];
        html += "<tr>"; 
        html += "<td >"+(i+1)+"</td>";
        html += "<td>"+patientData["pepfarId"]+"</td>";
         html += "<td>"+patientData["gender"]+"</td>";
        //html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
        html += "<td>"+patientData["artStartDate"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["dob"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["age"]+"</td>";
        html += "<td>"+( (patientData["enrollmentDate"] == null) ?"-": patientData["enrollmentDate"].substring(0, 10) )+"</td>";
        html += "<td><a class='button' target='_blank' href='/coreapps/clinicianfacing/patient.page?patientId="+patientId+"'>Patient Dashboard<a></td>"
        
        html += "</tr>";
   
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable2(title);
}


function dataquality_renderCompletedModulesOTZData(isOptimum, category,  title)
{
    jq("#tableContainer").html('<table class="dataTable2"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Gender</th><th nowrap>ART Start date</th><th nowrap>Date of Birth</th><th nowrap>Age</th><th nowrap>Date Enrolled in OTZ</th><th>Positive Living Date</th><th>Treatment Literacy Date</th><th>Adolescents Participation Date</th><th>Leadership Training Date</th><th>Peer-to-Peer Mentorship Date</th><th>Role of OTZ in 95-95-95 Date</th><th>OTZ Champion Orientation Date</th> <th>Action</th> </tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    
    
    console.log(category, isOptimum);
    var fullData = new Array();
   if(isOptimum == "completedmodules")
    {
         fullData = completed7Modules;
    }
    
    
    var html = "";
   
    for(var i=0; i<fullData.length; i++)
    {  
        var patientData = fullData[i];
        var patientId = patientData["patientId"];
        html += "<tr>"; 
        html += "<td >"+(i+1)+"</td>";
        html += "<td>"+patientData["pepfarId"]+"</td>";
         html += "<td>"+patientData["gender"]+"</td>";
        //html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
        html += "<td>"+patientData["artStartDate"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["dob"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["age"]+"</td>";
        html += "<td>"+( (patientData["enrollmentDate"] == null) ?"-": patientData["enrollmentDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["positiveLivingDate"] == null) ?"-": patientData["positiveLivingDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["treatmentLiteracyDate"] == null) ?"-": patientData["treatmentLiteracyDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["adolescentsParticipationDate"] == null) ?"-": patientData["adolescentsParticipationDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["leadershipTrainingDate"] == null) ?"-": patientData["leadershipTrainingDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["peerMentorshipDate"] == null) ?"-": patientData["peerMentorshipDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["roleOf95Date"] == null) ?"-": patientData["roleOf95Date"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["otzChampionOrientationDate"] == null) ?"-": patientData["otzChampionOrientationDate"].substring(0, 10) )+"</td>";
        html += "<td><a class='button' target='_blank' href='/coreapps/clinicianfacing/patient.page?patientId="+patientId+"'>Patient Dashboard<a></td>"
        
        html += "</tr>";
   
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable2(title);
}

function dataquality_renderFullDisclosureOTZData(isOptimum, category,  title)
{
    jq("#tableContainer").html('<table class="dataTable2"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Gender</th><th nowrap>ART Start date</th><th nowrap>Date of Birth</th><th nowrap>Age</th><th nowrap>Date Enrolled in OTZ</th><th nowrap>Full disclosure date</th> <th>Action</th> </tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    
    
    console.log(category, isOptimum);
    var fullData = new Array();
   if(isOptimum == "disclosure")
    {
         fullData = patientsFullDisclosure;
    }
    
    
    var html = "";
   
    for(var i=0; i<fullData.length; i++)
    {  
        var patientData = fullData[i];
        var patientId = patientData["patientId"];
        html += "<tr>"; 
        html += "<td >"+(i+1)+"</td>";
        html += "<td>"+patientData["pepfarId"]+"</td>";
         html += "<td>"+patientData["gender"]+"</td>";
        //html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
        html += "<td>"+patientData["artStartDate"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["dob"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["age"]+"</td>";
        html += "<td>"+( (patientData["enrollmentDate"] == null) ?"-": patientData["enrollmentDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["fullDisclosureDate"] == null) ?"-": patientData["fullDisclosureDate"].substring(0, 10) )+"</td>";
        html += "<td><a class='button' target='_blank' href='/coreapps/clinicianfacing/patient.page?patientId="+patientId+"'>Patient Dashboard<a></td>"
        
        html += "</tr>";
   
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable2(title);
}

function dataquality_renderExitedOTZData(isOptimum, category,  title)
{
    jq("#tableContainer").html('<table class="dataTable2"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Gender</th><th nowrap>ART Start date</th><th nowrap>Date of Birth</th><th nowrap>Age</th><th nowrap>Date Enrolled in OTZ</th><th nowrap>Date Exited</th> <th>Action</th> </tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    
    
    console.log(category, isOptimum);
    var fullData = new Array();
   if(isOptimum == "exitedotz")
    {
         fullData = patientsExited;
    }
    
    
    var html = "";
   
    for(var i=0; i<fullData.length; i++)
    {  
        var patientData = fullData[i];
        var patientId = patientData["patientId"];
        html += "<tr>"; 
        html += "<td >"+(i+1)+"</td>";
        html += "<td>"+patientData["pepfarId"]+"</td>";
         html += "<td>"+patientData["gender"]+"</td>";
        //html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
        html += "<td>"+patientData["artStartDate"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["dob"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["age"]+"</td>";
        html += "<td>"+( (patientData["enrollmentDate"] == null) ?"-": patientData["enrollmentDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["outcomeDate"] == null) ?"-": patientData["outcomeDate"].substring(0, 10) )+"</td>";
        html += "<td><a class='button' target='_blank' href='/coreapps/clinicianfacing/patient.page?patientId="+patientId+"'>Patient Dashboard<a></td>"
        
        html += "</tr>";
   
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable2(title);
}

function dataquality_renderTransferredOTZData(isOptimum, category,  title)
{
    jq("#tableContainer").html('<table class="dataTable2"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Gender</th><th nowrap>ART Start date</th><th nowrap>Date of Birth</th><th nowrap>Age</th><th nowrap>Date Enrolled in OTZ</th><th nowrap>Date Transferred</th> <th>Action</th> </tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    
    
    console.log(category, isOptimum);
    var fullData = new Array();
   if(isOptimum == "transferred")
    {
         fullData = allPatientsTransferred;
    }
    
    
    var html = "";
   
    for(var i=0; i<fullData.length; i++)
    {  
        var patientData = fullData[i];
        var patientId = patientData["patientId"];
        html += "<tr>"; 
        html += "<td >"+(i+1)+"</td>";
        html += "<td>"+patientData["pepfarId"]+"</td>";
         html += "<td>"+patientData["gender"]+"</td>";
        //html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
        html += "<td>"+patientData["artStartDate"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["dob"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["age"]+"</td>";
        html += "<td>"+( (patientData["enrollmentDate"] == null) ?"-": patientData["enrollmentDate"].substring(0, 10) )+"</td>";
        html += "<td>"+( (patientData["outcomeDate"] == null) ?"-": patientData["outcomeDate"].substring(0, 10) )+"</td>";
        html += "<td><a class='button' target='_blank' href='/coreapps/clinicianfacing/patient.page?patientId="+patientId+"'>Patient Dashboard<a></td>"
        
        html += "</tr>";
   
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable2(title);
}

function dataquality_renderAdhOTZData(isOptimum, category,  title)
{
    jq("#tableContainer").html('<table class="dataTable2"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Gender</th><th nowrap>ART Start date</th><th nowrap>Date of Birth</th><th nowrap>Age</th><th nowrap>Date Enrolled in OTZ</th> <th nowrap>Next Appointment Date</th> <th nowrap>Pickup Date</th><th>Action</th> </tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    
    
    console.log(category, isOptimum);
    var fullData = new Array();
    if(isOptimum == "pickupapp")
    {
        fullData = allPatientsScheduledObj[category];
    }
    else if(isOptimum == "keptapp")
    {
        fullData = allPatientsKeptObj[category];
    }
    else if(isOptimum == "goodscore")
    {
        fullData = allPatientsGoodScoreObj[category];
        
    }
    
    
    var html = "";
   
    for(var i=0; i<fullData.length; i++)
    {  
        var patientData = fullData[i];
        var patientId = patientData["patientId"];
        html += "<tr>"; 
        html += "<td >"+(i+1)+"</td>";
        html += "<td>"+patientData["pepfarId"]+"</td>";
         html += "<td>"+patientData["gender"]+"</td>";
        //html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
        html += "<td>"+patientData["artStartDate"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["dob"].substring(0, 10)+"</td>";
        html += "<td>"+patientData["age"]+"</td>";
        html += "<td>"+( (patientData["enrollmentDate"] == null) ?"-": patientData["enrollmentDate"].substring(0, 10) )+"</td>";
        html += "<td>"+((patientData["nextAppointmentDate"] == null) ? "N/A": patientData["nextAppointmentDate"]).substring(0, 10)+"</td>";
        html += "<td>"+((patientData["lastPickupDate"] == null) ? "N/A" : patientData["lastPickupDate"].substring(0, 10))+"</td>";
        
        html += "<td><a class='button' target='_blank' href='/coreapps/clinicianfacing/patient.page?patientId="+patientId+"'>Patient Dashboard<a></td>"
        
        html += "</tr>";
   
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable2(title);
}


function renderPatientDetails(isOptimum, weight, regimen, title)
{
    jq("#tableContainer").html('<table class="dataTable"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Patient Name</th> <th nowrap>Regimen</th><th nowrap>Regimen Line</th><th nowrap>Drug Strength</th><th nowrap>Drug Dosage</th> <th nowrap>Age</th> <th nowrap>Weight</th> <th nowrap>Weight band</th> <th nowrap>Weight Duration</th></tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    for(var i=0; i<fullData.length; i++)
    {  
        var isOptimumTrue = (isOptimum == "true") ? true : false;
        if(isOptimumRegimen(fullData[i]["regimenConceptId"]) === isOptimumTrue && getWeightBand(fullData[i]["patientWeight"]) == weight)
        {//there is a match
            if(regimen == "" || regimen == null){
                html += buildPatientHtml(sn, fullData[i], weight);
                sn++;
            }
            else if(regimen == "regimen_"+fullData[i]["regimenConceptId"])
            {
                html += buildPatientHtml(sn, fullData[i], weight);
                sn++;
            }else if(regimen == "regimen_0" && getOptimalType(fullData[i]["regimenConceptId"]) == -1)
            {
                 html += buildPatientHtml(sn, fullData[i], weight);
                 sn++;
            }
            
            
        } 
        else{
            
        }
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable(title);
}

function renderVlPatientDetails(vlCount, title)
{
    jq("#tableContainer").html('<table class="dataTable"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Patient Name</th> <th nowrap>Current VL</th><th nowrap>Sample Collection Date</th></tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    for(var i=0; i<vlData.length; i++)
    {  
        if(vlCount == ">=1000 cp/ml" && parseFloat(vlData[i]["vlResult"]) >= 1000 )
        {//there is a match
           html +=  buildVlPatientHtml(sn, vlData[i], "action");
           sn++;
        } else if(vlCount == "200 - 999 cp/ml" && parseFloat(vlData[i]["vlResult"]) > 200  && parseFloat(vlData[i]["vlResult"]) < 1000 )
        {
           html +=  buildVlPatientHtml(sn, vlData[i], "action");
           sn++;
        }
        else if(vlCount == "<200 cp/ml" && parseFloat(vlData[i]["vlResult"]) < 200)
        {
            html += buildVlPatientHtml(sn, vlData[i], "action");
            sn++;
        }
        
        
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable(title);
}

function renderEACPatientDetails(eacType, title)
{
    jq("#tableContainer").html('<table class="dataTable"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Patient Name</th> <th nowrap>Previous VL</th><th>Current VL</th></tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    //console.log(eacData);
    for(var i=0; i<eacData.length; i++)
    {  
        
        var eacDate = Date.parse(eacData[i]["encounterDate"]);
        if(eacType == "receivingEAC" && (eacDate + (30 * 24 * 60 * 60 * 1000) < new Date().getTime()) )
        {//there is a match
           
           html +=  buildEACPatientHtml(sn, eacData[i], "action1");
           sn++;
        } else if(eacType == "receivedEACVLS" && eacData[i]["secondToLastVl"]<1000)
        {
           html +=  buildEACPatientHtml(sn, eacData[i], "action2");
           sn++;
        }
        else if(eacType == "receivedEACVLE" && eacData[i]["secondToLastVl"] >  eacData[i]["vlResult"])
        {
           html +=  buildEACPatientHtml(sn, eacData[i], "action2");
           sn++;
        }
        
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable(title);
}

function renderCD4PatientDetails(vlCount, title)
{
    jq("#tableContainer").html('<table class="dataTable"><thead><tr><th nowrap>S/N</th><th nowrap>Pepfar ID</th><th nowrap>Patient Name</th> <th nowrap>Current CD4+ Cell Count</th><th nowrap>Visit Date</th></tr></thead><tbody id="detailsArea"></tbody></table>');
    
    jq("#detailsModal").modal("show");
    var html = "";
    var sn = 1;
    console.log(vlCount);
    for(var i=0; i<cd4Data.length; i++)
    {  
        if(vlCount == "# of patients with CD4+ cell count <200" && parseFloat(cd4Data[i]["cd4Count"]) < 200 )
        {//there is a match
           console.log(cd4Data[i]);
           html +=  buildCD4PatientHtml(sn, cd4Data[i], "action1");
           sn++;
        } else if(vlCount == "# of patients with CD4+ cell count >200" && parseFloat(cd4Data[i]["cd4Count"]) > 200)
        {
           html +=  buildCD4PatientHtml(sn, cd4Data[i], "action2");
           sn++;
        }
        
        
        
    }
    //reInitializeDatatable(title);
    jq("#detailsArea").html(html);
    
    //initialize datatable
   reInitializeDatatable(title);
}

function reInitializeDatatable(title)
{
   
    jq(".dataTable").DataTable({
        
             pageLength: 100,
             "lengthMenu": [[50, 100, 250, 500, -1], [50, 100, 250, 500, "All"]],
             "columnDefs": [
                { "searchable": false, "targets": [0] }  // Disable search on first and last columns
              ],
              dom: 'lfBrtip',
                buttons: [
                    'copy', 
                   // 'csv', 
                    {
                        extend: 'csv',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'excel',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'pdf',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'print',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    //'pdf', 
                    //'print'
                ]

                });
}


function reInitializeDatatable2(title)
{
  
    jq(".dataTable2").DataTable({
        
             pageLength: 100,
             "lengthMenu": [[50, 100, 250, 500, -1], [50, 100, 250, 500, "All"]],
             "columnDefs": [
                { "searchable": false, "targets": [0] }  // Disable search on first and last columns
              ],
              dom: 'lfBrtip',
                buttons: [
                    'copy', 
                   // 'csv', 
                    {
                        extend: 'csv',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'excel',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'pdf',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'print',
                        title:title,
                        exportOptions: {
                            columns: [0,1,2,3,4]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    //'pdf', 
                    //'print'
                ]

                });
}


 
function buildVlPatientHtml(i, patientData, action)
{
    var html = "";
    html += "<tr>"; 
    html += "<td >"+(i)+"</td>";
    html += "<td>"+patientData["pepfarId"]+"</td>";
    html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
    html += "<td>"+patientData["vlResult"]+"</td>";
    html += "<td>"+patientData["sampleCollectionDate"]+"</td>";
    //html += "<td>"+action+"</td>";
   
    html += "</tr>";
    return html;
}

function buildEACPatientHtml(i, patientData, action)
{
    var html = "";
    html += "<tr>"; 
    html += "<td >"+(i)+"</td>";
    html += "<td>"+patientData["pepfarId"]+"</td>";
    html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
    html += "<td>"+patientData["vlResult"]+"</td>";
    html += "<td>"+patientData["secondToLastVl"]+"</td>";
    //html += "<td>"+action+"</td>";
   
    html += "</tr>";
    return html;
}

function buildCD4PatientHtml(i, patientData, action)
{
    var html = "";
    html += "<tr>"; 
    html += "<td >"+(i)+"</td>";
    html += "<td>"+patientData["pepfarId"]+"</td>";
    html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
    html += "<td>"+patientData["cd4Count"]+"</td>";
    html += "<td>"+patientData["encounterDate"]+"</td>";
    //html += "<td>"+action+"</td>";
   
    html += "</tr>";
    return html;
}

function buildPatientHtml(i, patientData, weight)
{
    
    var html = "";
    html += "<tr>"; 
    html += "<td >"+(i)+"</td>";
    html += "<td>"+patientData["pepfarId"]+"</td>";
    html += "<td>"+patientData["firstName"]+" "+patientData["lastName"]+"</td>";
    html += "<td>"+patientData["regimenLine"]+"</td>";
    html += "<td>"+patientData["regimenLine2"]+"</td>";
    html += "<td>"+patientData["drugStrength"]+"</td>";
    html += "<td>"+patientData["drugDosage"]+"</td>";
    html += "<td>"+patientData["patientAge"]+"</td>";
    html += "<td>"+patientData["patientWeight"]+"</td>";
    var weightBandHtml = "";
    if(weight == "less_20")
    {
        weightBandHtml = "< 20kg";
    }else if(weight == "bet_20_30"){
        weightBandHtml = "20-30kg"
    }else{
         weightBandHtml = ">30kg"
    }
    html += "<td>"+weightBandHtml+"</td>";
    html += "<td>"+getMonthsAgo(patientData["weightDate"])+" months</td>";
    html += "</tr>";
    return html;
}

function getMonthsAgo(date)
{
    var d = new Date(Date.parse(date));
    var now = new Date();
   
    var months = monthDiff(d, now);
    return months;
    
}

function monthDiff(d1, d2) {
    var months;
    months = (d2.getFullYear() - d1.getFullYear()) * 12;
    months -= d1.getMonth();
    months += d2.getMonth();
    return months <= 0 ? 0 : months;
}

function isOptimumRegimen(conceptId)
{
    if([162200, 165691, 165681, 166092, 164512, 162201].includes(conceptId))
    {
        return true;
    }
    else{
        return false;
    }
}


function getOptimalType(conceptId)
{
    if([162200, 165691, 165681, 166092, 164512, 162201].includes(conceptId))
    {
        return 1;
    }
    else if([162561, 164505, 162199, 162563, 1652].includes(conceptId)){
        return 0;
    }
    else {
        return -1;
    }
}

function getDateRangeForQuarter(quarter, year)
{
    var startDate = "";
    var endDate = "";
    if(quarter == 1)
    {
        year = year -1;
        startDate = year+"-10-01";
        endDate = year+"-12-31";
    }else if(quarter == 2)
    {
         startDate = year+"-01-01";
        endDate = year+"-03-31";
    }
    else if(quarter == 3)
    {
        
         startDate = year+"-04-01";
        endDate = year+"-06-30";
    }
    else if(quarter == 4)
    {
         startDate = year+"-07-01";
        endDate = year+"-09-30";
    }
    
    console.log(year);
    return [startDate, endDate];
}


function dqr_getFiscalQuarter(date)//date should be in yyyy-mm-dd format
{
    var dateComponents = date.split("-");
    var month = parseInt(dateComponents[1]);
    var monthComp = "";
     if (month >= 1 && month <= 3) {
          monthComp =  "2";
    } else if (month >= 4 && month <= 6) {
             monthComp =  "3";
    } else if (month >= 7 && month <= 9) {
          monthComp =  "4";
    } else if (month >= 10 && month <= 12) {
           monthComp =  "1";
    }
    
    
   
    
    var quarter = "fy" + dateComponents[0] + "q" + monthComp;
   return quarter;
}



function  getAgeBand(age) {//
    var ageBand = "";
    if (age < 20) {
        ageBand = "less_20";
    } else if (age >= 20 && age <= 30) {
        ageBand = "bet_20_30";
    } else if(age > 30){
        ageBand = "above_30";
    }
    return ageBand;
}
function  getWeightBand(age) {//this is actually weight band
    var ageBand = "";
    if (age < 20) {
        ageBand = "less_20";
    } else if (age >= 20 && age <= 30) {
        ageBand = "bet_20_30";
    } else if(age > 30){
        ageBand = "above_30";
    }
    return ageBand;
}

