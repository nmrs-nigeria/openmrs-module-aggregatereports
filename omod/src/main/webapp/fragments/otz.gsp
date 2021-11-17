
<%
/*ui.includeJavascript("dataquality", "jquery-3.3.1.js")
ui.includeJavascript("dataquality", "jquery.dataTables.min.js")
ui.includeJavascript("dataquality", "datatables.min.js")
ui.includeJavascript("dataquality", "buttons.flash.min.js")
ui.includeJavascript("dataquality", "jquery-ui.js")
ui.includeJavascript("dataquality", "jszip.min.js")
ui.includeJavascript("dataquality", "pdfmake.min.js")
ui.includeJavascript("dataquality", "vfs_fonts.js")
ui.includeCss("dataquality", "bootstrap.min.css")
ui.includeJavascript("dataquality", "buttons.html5.min.js")
ui.includeJavascript("dataquality", "buttons.print.min.js")
ui.includeJavascript("dataquality", "datatable.button.min.js")
ui.includeCss("dataquality", "buttons.dataTables.min.css")
ui.includeCss("dataquality", "jquery.dataTables.min.css")
ui.includeCss("dataquality", "myStyle.css")*/

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
def months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"] as String[];

int i=1;
int year = Calendar.getInstance().get(Calendar.YEAR);
%>

<%=ui.resourceLinks()%>
<style>
    .table thead th
    {
        vertical-align: middle !important;
    }
</style>

<div class="container">
    
    <h2>OTZ</h2>
     <fieldset>
        <legend>Filters</legend>
        
        <div class="row">
            <div class="col-sm-6 col-md-3">
                <label>Search By</label>
            </div>
            <div class="col-sm-6 col-md-3">
                <strong><input type="radio" name="cohortType" value="monthly" class="cohortSelector" />Monthly Cohort</strong>
            </div>
            <div class="col-sm-6 col-md-3"> 
                <strong><input type="radio" name="cohortType" value="quarterly" class="cohortSelector" />Quarterly Cohort</strong>
            </div>
            <div class="col-sm-6 col-md-3">
                <strong><input type="radio" name="cohortType"  value="dateRange" class="cohortSelector" />Date Range Cohort</strong>
            </div>
            
        </div>
        
        <div class="row hidden cohortArea" id="monthly">
            
           <label class="col-sm-6 col-md-2 " ><strong>Month</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
                <select name="month" id="month" class="form-control">
                    <option value="">Select Month</option>
                    <% for(int j=0; j<months.length; j++){%>
                        <option value="<%= j+1 %>"><%= months[j] %></option>
                    <% }%>
                </select>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>Year</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
                <select name="monthYear" id="monthYear" class="form-control">
              <% for(int k=year; k>=2000; k--){%>
                 <option value="<%= k %>"><%= k %></option>
                    <% }%>
                </select>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterOTZMonthly">Filter</button>
            </div>
        </div>
        <div class="row hidden cohortArea" id="quarterly">
            
           <label class="col-sm-6 col-md-2 " ><strong>Quarter</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <select name="quarter" id="quarter" class="form-control">
                   <option value="">Select Quarter</option>
                   <option value="1">Quarter 1</option>
                   <option value="2">Quarter 2</option>
                   <option value="3">Quarter 3</option>
                   <option value="4">Quarter 4</option>
                   
                </select>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>Year</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <select name="quarterYear" id="quarterYear" class="form-control">
              <% for(int k=year; k>=2000; k--){%>
                 <option value="<%= k %>">FY <%= k %></option>
                    <% }%>
                </select>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterOTZQuarterly">Filter</button>
            </div>
        </div>
        <div class="row hidden cohortArea" id="dateRange">
            
           <label class="col-sm-6 col-md-2 " ><strong>Start Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="startDate" name="startDate"/>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>End Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="endDate" name="endDate"/>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterOTZ">Filter</button>
            </div>
        </div>
    </fieldset>
    <br/>
    
    <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
          <a class="nav-item nav-link active" id="nav-summary-tab" data-toggle="tab" href="#nav-summary" role="tab" aria-controls="nav-summary" aria-selected="true">OTZ Reports</a>
          <a class="nav-item nav-link" id="nav-optimal-tab" data-toggle="tab" href="#nav-optimal" role="tab" aria-controls="nav-optimal" aria-selected="false">OTZ Dashboard</a>
        </div>
      </nav>
      <div class="tab-content" id="nav-tabContent">
         <div class="tab-pane fade show active" id="nav-summary" role="tabpanel" aria-labelledby="nav-summary-tab">${ ui.includeFragment("dataquality", "otzdetails/otzreport", [title:title]) }</div>
        <div class="tab-pane fade" id="nav-optimal" role="tabpanel" aria-labelledby="nav-optimal-tab">${ ui.includeFragment("dataquality", "otzdetails/otzdashboard", [title:title]) }</div>

      </div>
      
      
      <ul class='custom-menu'>
  <li data-action="first">First thing</li>
  <li data-action="second">Second thing</li>
  <li data-action="third">Third thing</li>
</ul>
  <div class="modal fade" id="detailsModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document" >
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalTitle">Details</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <div class="row">
              <div class="col-sm-12 table-responsive " id="tableContainer">
                <div class=" ">
                    <table class="dataTable table ">
                        <thead>
                            <tr>
                                <th>S/N</th><th>Patient Name</th> <th>Age</th> <th>Weight</th> <th>Weight band</th>
                            </tr>
                        </thead>
                        <tbody id="detailsArea">

                        </tbody>
                    </table>
              </div>
              </div>
              
          </div>
          
         
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

        
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/amcharts.js"></script>
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/serial.js"></script>
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/pie.js" type="text/javascript"></script>
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/amstock.js" type="text/javascript"></script>

    <script type="text/javascript">
    
    jq = jQuery;
    
    var lastDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
   
    
      var totalReportCount = 13;    
      var currReportCount = 0;
                 
      var totalPtsOnArt = 0;
      var totalPedsOnArt = 0;
      var totalAdultsTestedPositive = 0;
      var totalPedsTestedPositive = 0;
      var totalTransferred = 0;
      var totalCompleted7 = 0;
      var totalOptedOut = 0;
      var totalTransitioned = 0;
      var totalExited = 0;
      var totalLTFU = 0;
      var totalDead = 0;
      var startDate = "";
      var endDate = "";
      var indicatorDescription = {};
      jq(document).ready(function(e){
      
      jq.get("/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/otz_info.json", function(data){
            indicatorDescription = data;
        })
      
        jq(".otzInfo").click(function(e){
        
            var key = jq(this).attr("data-key");
            var data = indicatorDescription[key];

            var title = data["title"];
            var description = data["description"];
            var source = data["source"];
            var calculation = data["calculation"];
            jq("#otzIndicatorTitle").html(title)
            jq("#otzIndicatorDescription").html(description)
            jq("#otzIndicatorSource").html(source)
            jq("#otzIndicatorCalculation").html(calculation)
        })
        jq(".cohortSelector").change(function(){
            var selVal = jq(this).val();
            jq(".cohortArea").addClass("hidden");
            jq("#"+selVal).removeClass("hidden");
        });
      
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

          jq(".otzDetails").click(function(e){
              startDate = jq("#startDate").val();
              endDate = jq("#endDate").val();
              var subSet = jq(this).attr("data-subset");
              var type = jq(this).attr("data-type");
              window.open("otzdetails.page?type="+type+"&subset="+subSet+"&startDate="+startDate+"&endDate="+endDate, "_blank");
          })
          
          
        jq("#filterOTZQuarterly").click(function(){
            var quarter = jq("#quarter").val() ;
            var year = jq("#quarterYear").val();
           
            var startDateEndDate = getDateRangeForQuarter(quarter, year)
            
            startDate = startDateEndDate[0];
            endDate = startDateEndDate[1];
            

            
            getOTZData();
           
        
        });

        
        
        jq("#filterOTZMonthly").click(function(){
            var month = jq("#month").val() ;
            var year = jq("#monthYear").val();
            var lastDay = lastDays[month-1];
            
            if(month == 2 && leapYear(year))
            {
                lastDay = 29;
            }
            
            var monthString = (month > 9) ? month : "0"+month;
            startDate = year+"-"+monthString+"-"+"01"
            endDate = year+"-"+monthString+"-"+lastDay;
            
            getOTZData();
           
        
        });
          
          
        jq("#filterOTZ").click(function(e){
            startDate = jq("#startDate").val();
            endDate = jq("#endDate").val();
            
            getOTZData();
            
            
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
    
     var datatableObj ;
    function createDatatable()
    {
    
    
     datatableObj = jq(".dataTable").DataTable({
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
                        title:'<%= title; %>',
                        exportOptions: {
                            columns: [0,1,2]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'excel',
                        title:'<%= title; %>',
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'pdf',
                        title:'<%= title; %>',
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'print',
                        title:'<%= title; %>',
                        //messageTop: '<%= title; %>'
                    },
                    //'pdf', 
                    //'print'
                ]

                });
       } 
       createDatatable();
       
       
   function getOTZData()
   {
   
             currReportCount = 0;
            //show the progress area
            jq("#progressArea").removeClass("hidden");
            
            jq(".loadingView").html('<img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" />');
            
           
            
            myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getAllEnrolledInOTZ") }').then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledM10To14").html(male1014)
                jq("#totalEnrolledM15To19").html(male1519)
                jq("#totalEnrolledM20To24").html(male2024)
                 jq("#totalEnrolledF10To14").html(female1014)
                jq("#totalEnrolledF15To19").html(female1519);
                jq("#totalEnrolledF20To24").html(female2024)
                
               
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledTotal").html(total)
                
                
                //lets set some cards
                setCardValues(data);;
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getAllFullDisc") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalFullDiscM10To14").html(male1014)
                jq("#totalFullDiscM15To19").html(male1519)
                jq("#totalFullDiscM20To24").html(male2024)
                 jq("#totalFullDiscF10To14").html(female1014)
                jq("#totalFullDiscF15To19").html(female1519);
                jq("#totalFullDiscF20To24").html(female2024)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalFullDiscTotal").html(total)
                console.log("total disclosed", total);
                renderFullDisclosure(totalEnrolled, total);
                //lets set some cards
                setCardValues(data);;
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithScheduledPickup6MonthsBefore") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledWithApp6MtPriorM10To14").html(male1014)
                jq("#totalEnrolledWithApp6MtPriorM15To19").html(male1519)
                jq("#totalEnrolledWithApp6MtPriorM20To24").html(male2024)
                 jq("#totalEnrolledWithApp6MtPriorF10To14").html(female1014)
                jq("#totalEnrolledWithApp6MtPriorF15To19").html(female1519);
                jq("#totalEnrolledWithApp6MtPriorF20To24").html(female2024)
                
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledWithApp6MtPriorTotal").html(total)
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledKeptAppPriorM10To14").html(male1014)
                jq("#totalEnrolledKeptAppPriorM15To19").html(male1519)
                jq("#totalEnrolledKeptAppPriorM20To24").html(male2024)
                 jq("#totalEnrolledKeptAppPriorF10To14").html(female1014)
                jq("#totalEnrolledKeptAppPriorF15To19").html(female1519);
                jq("#totalEnrolledKeptAppPriorF20To24").html(female2024);
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledKeptAppPriorTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithGoodAdhScore6MonthsBefore") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledGoodAdhPriorM10To14").html(male1014)
                jq("#totalEnrolledGoodAdhPriorM15To19").html(male1519)
                jq("#totalEnrolledGoodAdhPriorM20To24").html(male2024)
                 jq("#totalEnrolledGoodAdhPriorF10To14").html(female1014)
                jq("#totalEnrolledGoodAdhPriorF15To19").html(female1519);
                jq("#totalEnrolledGoodAdhPriorF20To24").html(female2024)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledGoodAdhPriorTotal").html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBefore") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResultM10To14").html(male1014)
                jq("#totalEnrolledBaselineResultM15To19").html(male1519)
                jq("#totalEnrolledBaselineResultM20To24").html(male2024)
                jq("#totalEnrolledBaselineResultF10To14").html(female1014)
                jq("#totalEnrolledBaselineResultF15To19").html(female1519);
                jq("#totalEnrolledBaselineResultF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResultTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBeforeAndBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResultBelow200M10To14").html(male1014)
                jq("#totalEnrolledBaselineResultBelow200M15To19").html(male1519)
                jq("#totalEnrolledBaselineResultBelow200M20To24").html(male2024)
                jq("#totalEnrolledBaselineResultBelow200F10To14").html(female1014)
                jq("#totalEnrolledBaselineResultBelow200F15To19").html(female1519);
                jq("#totalEnrolledBaselineResultBelow200F20To24").html(female2024);
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResultBelow200Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResult200To1000M10To14").html(male1014)
                jq("#totalEnrolledBaselineResult200To1000M15To19").html(male1519)
                jq("#totalEnrolledBaselineResult200To1000M20To24").html(male2024)
                jq("#totalEnrolledBaselineResult200To1000F10To14").html(female1014)
                jq("#totalEnrolledBaselineResult200To1000F15To19").html(female1519);
                jq("#totalEnrolledBaselineResult200To1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResult200To1000Total").html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResultAbove1000M10To14").html(male1014)
                jq("#totalEnrolledBaselineResultAbove1000M15To19").html(male1519)
                jq("#totalEnrolledBaselineResultAbove1000M20To24").html(male2024)
                jq("#totalEnrolledBaselineResultAbove1000F10To14").html(female1014)
                jq("#totalEnrolledBaselineResultAbove1000F15To19").html(female1519);
                jq("#totalEnrolledBaselineResultAbove1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResultAbove1000Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBefore") }');
            })

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResult6MtM10To14").html(male1014)
                jq("#totalEnrolledBaselineResult6MtM15To19").html(male1519)
                jq("#totalEnrolledBaselineResult6MtM20To24").html(male2024)
                jq("#totalEnrolledBaselineResult6MtF10To14").html(female1014)
                jq("#totalEnrolledBaselineResult6MtF15To19").html(female1519);
                jq("#totalEnrolledBaselineResult6MtF20To24").html(female2024)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResult6MtTotal").html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBeforeAndBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResult6MtBelow200M10To14").html(male1014)
                jq("#totalEnrolledBaselineResult6MtBelow200M15To19").html(male1519)
                jq("#totalEnrolledBaselineResult6MtBelow200M20To24").html(male2024)
                jq("#totalEnrolledBaselineResult6MtBelow200F10To14").html(female1014)
                jq("#totalEnrolledBaselineResult6MtBelow200F15To19").html(female1519);
                jq("#totalEnrolledBaselineResult6MtBelow200F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResult6MtBelow200Total").html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResult6MtBt200To1000M10To14").html(male1014)
                jq("#totalEnrolledBaselineResult6MtBt200To1000M15To19").html(male1519)
                jq("#totalEnrolledBaselineResult6MtBt200To1000M20To24").html(male2024)
                jq("#totalEnrolledBaselineResult6MtBt200To1000F10To14").html(female1014)
                jq("#totalEnrolledBaselineResult6MtBt200To1000F15To19").html(female1519);
                jq("#totalEnrolledBaselineResult6MtBt200To1000F20To24").html(female2024)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResult6MtBt200To1000Total").html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000M10To14").html(male1014)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000M15To19").html(male1519)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000M20To24").html(male2024)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000F10To14").html(female1014)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000F15To19").html(female1519);
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEligibleForMonthZeroVL") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledEligibleMtZM10To14").html(male1014)
                jq("#totalEnrolledEligibleMtZM15To19").html(male1519)
                jq("#totalEnrolledEligibleMtZM20To24").html(male2024)
                jq("#totalEnrolledEligibleMtZF10To14").html(female1014)
                jq("#totalEnrolledEligibleMtZF15To19").html(female1519);
                jq("#totalEnrolledEligibleMtZF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledEligibleMtZTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledEligibleMtZSampleM10To14").html(male1014)
                jq("#totalEnrolledEligibleMtZSampleM15To19").html(male1519)
                jq("#totalEnrolledEligibleMtZSampleM20To24").html(male2024)
                jq("#totalEnrolledEligibleMtZSampleF10To14").html(female1014)
                jq("#totalEnrolledEligibleMtZSampleF15To19").html(female1519);
                jq("#totalEnrolledEligibleMtZSampleF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledEligibleMtZSampleTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledB1000MZ200M10To14").html(male1014)
                jq("#totalEnrolledB1000MZ200M15To19").html(male1519)
                jq("#totalEnrolledB1000MZ200M20To24").html(male2024)
                jq("#totalEnrolledB1000MZ200F10To14").html(female1014)
                jq("#totalEnrolledB1000MZ200F15To19").html(female1519);
                jq("#totalEnrolledB1000MZ200F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledB1000MZ200Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledB1000MZ200To1000M10To14").html(male1014)
                jq("#totalEnrolledB1000MZ200To1000M15To19").html(male1519)
                jq("#totalEnrolledB1000MZ200To1000M20To24").html(male2024)
                jq("#totalEnrolledB1000MZ200To1000F10To14").html(female1014)
                jq("#totalEnrolledB1000MZ200To1000F15To19").html(female1519);
                jq("#totalEnrolledB1000MZ200To1000F20To24").html(female2024)
                
                
                 var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledB1000MZ200To1000Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000") }');
            })

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#totalEnrolledB1000MZ1000M10To14").html(male1014)
                jq("#totalEnrolledB1000MZ1000M15To19").html(male1519)
                jq("#totalEnrolledB1000MZ1000M20To24").html(male2024)
                jq("#totalEnrolledB1000MZ1000F10To14").html(female1014)
                jq("#totalEnrolledB1000MZ1000F15To19").html(female1519);
                jq("#totalEnrolledB1000MZ1000F20To24").html(female2024)
                
                 var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#totalEnrolledB1000MZ1000Total").html(total)

                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithScheduledPickupAfter") }');
            })


            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#scheduledPickupFUM10To14").html(male1014)
                jq("#scheduledPickupFUM15To19").html(male1519)
                jq("#scheduledPickupFUM20To24").html(male2024)
                jq("#scheduledPickupFUF10To14").html(female1014)
                jq("#scheduledPickupFUF15To19").html(female1519);
                jq("#scheduledPickupFUF20To24").html(female2024)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#scheduledPickupFUTotal").html(total)
                
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWhoKeptScheduledPickupAfter") }');
            })

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#scheduledKeptPickupFUM10To14").html(male1014)
                jq("#scheduledKeptPickupFUM15To19").html(male1519)
                jq("#scheduledKeptPickupFUM20To24").html(male2024)
                jq("#scheduledKeptPickupFUF10To14").html(female1014)
                jq("#scheduledKeptPickupFUF15To19").html(female1519);
                jq("#scheduledKeptPickupFUF20To24").html(female2024);
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#scheduledKeptPickupFUTotal").html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithGoodAdhScoreAfter") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#goodAdhFUM10To14").html(male1014)
                jq("#goodAdhFUM15To19").html(male1519)
                jq("#goodAdhFUM20To24").html(male2024)
                jq("#goodAdhFUF10To14").html(female1014)
                jq("#goodAdhFUF15To19").html(female1519);
                jq("#goodAdhFUF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#goodAdhFUTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledEligibleForVL") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#eligibleFUM10To14").html(male1014)
                jq("#eligibleFUM15To19").html(male1519)
                jq("#eligibleFUM20To24").html(male2024)
                jq("#eligibleFUF10To14").html(female1014)
                jq("#eligibleFUF15To19").html(female1519);
                jq("#eligibleFUF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#eligibleFUTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTaken") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#samplesTakenM10To14").html(male1014)
                jq("#samplesTakenM15To19").html(male1519)
                jq("#samplesTakenM20To24").html(male2024)
                jq("#samplesTakenF10To14").html(female1014)
                jq("#samplesTakenF15To19").html(female1519);
                jq("#samplesTakenF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#samplesTakenTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResult") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#samplesTakenResultM10To14").html(male1014)
                jq("#samplesTakenResultM15To19").html(male1519)
                jq("#samplesTakenResultM20To24").html(male2024)
                jq("#samplesTakenResultF10To14").html(female1014)
                jq("#samplesTakenResultF15To19").html(female1519);
                jq("#samplesTakenResultF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#samplesTakenResultTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResultBelow200") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#samplesTakenResult200M10To14").html(male1014)
                jq("#samplesTakenResult200M15To19").html(male1519)
                jq("#samplesTakenResult200M20To24").html(male2024)
                jq("#samplesTakenResult200F10To14").html(female1014)
                jq("#samplesTakenResult200F15To19").html(female1519);
                jq("#samplesTakenResult200F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#samplesTakenResult200Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove200Below1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#samplesTakenResult200To1000M10To14").html(male1014)
                jq("#samplesTakenResult200To1000M15To19").html(male1519)
                jq("#samplesTakenResult200To1000M20To24").html(male2024)
                jq("#samplesTakenResult200To1000F10To14").html(female1014)
                jq("#samplesTakenResult200To1000F15To19").html(female1519);
                jq("#samplesTakenResult200To1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#samplesTakenResult200To1000Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#samplesTakenResult1000M10To14").html(male1014)
                jq("#samplesTakenResult1000M15To19").html(male1519)
                jq("#samplesTakenResult1000M20To24").html(male2024)
                jq("#samplesTakenResult1000F10To14").html(female1014)
                jq("#samplesTakenResult1000F15To19").html(female1519);
                jq("#samplesTakenResult1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#samplesTakenResult1000Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12Months") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12MtM10To14").html(male1014)
                jq("#resultPast12MtM15To19").html(male1519)
                jq("#resultPast12MtM20To24").html(male2024)
                jq("#resultPast12MtF10To14").html(female1014)
                jq("#resultPast12MtF15To19").html(female1519);
                jq("#resultPast12MtF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12MtTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt200M10To14").html(male1014)
                jq("#resultPast12Mt200M15To19").html(male1519)
                jq("#resultPast12Mt200M20To24").html(male2024)
                jq("#resultPast12Mt200F10To14").html(female1014)
                jq("#resultPast12Mt200F15To19").html(female1519);
                jq("#resultPast12Mt200F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt200Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove200Below1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt200To100M10To14").html(male1014)
                jq("#resultPast12Mt200To100M15To19").html(male1519)
                jq("#resultPast12Mt200To100M20To24").html(male2024)
                jq("#resultPast12Mt200To100F10To14").html(female1014)
                jq("#resultPast12Mt200To100F15To19").html(female1519);
                jq("#resultPast12Mt200To100F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt200To100Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000") }');
            })
                

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt1000M10To14").html(male1014)
                jq("#resultPast12Mt1000M15To19").html(male1519)
                jq("#resultPast12Mt1000M20To24").html(male2024)
                jq("#resultPast12Mt1000F10To14").html(female1014)
                jq("#resultPast12Mt1000F15To19").html(female1519);
                jq("#resultPast12Mt1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt1000Total").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000CompletedEAC") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt1000EACM10To14").html(male1014)
                jq("#resultPast12Mt1000EACM15To19").html(male1519)
                jq("#resultPast12Mt1000EACM20To24").html(male2024)
                jq("#resultPast12Mt1000EACF10To14").html(female1014)
                jq("#resultPast12Mt1000EACF15To19").html(female1519);
                jq("#resultPast12Mt1000EACF20To24").html(female2024)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt1000EACTotal").html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt1000RepeatM10To14").html(male1014)
                jq("#resultPast12Mt1000RepeatM15To19").html(male1519)
                jq("#resultPast12Mt1000RepeatM20To24").html(male2024)
                jq("#resultPast12Mt1000RepeatF10To14").html(female1014)
                jq("#resultPast12Mt1000RepeatF15To19").html(female1519);
                jq("#resultPast12Mt1000RepeatF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt1000RepeatTotal").html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt1000Repeat200M10To14").html(male1014)
                jq("#resultPast12Mt1000Repeat200M15To19").html(male1519)
                jq("#resultPast12Mt1000Repeat200M20To24").html(male2024)
                jq("#resultPast12Mt1000Repeat200F10To14").html(female1014)
                jq("#resultPast12Mt1000Repeat200F15To19").html(female1519);
                jq("#resultPast12Mt1000Repeat200F20To24").html(female2024)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt1000Repeat200Total").html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt1000Repeat200To1000M10To14").html(male1014)
                jq("#resultPast12Mt1000Repeat200To1000M15To19").html(male1519)
                jq("#resultPast12Mt1000Repeat200To1000M20To24").html(male2024)
                jq("#resultPast12Mt1000Repeat200To1000F10To14").html(female1014)
                jq("#resultPast12Mt1000Repeat200To1000F15To19").html(female1519);
                jq("#resultPast12Mt1000Repeat200To1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt1000Repeat200To1000Total").html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#resultPast12Mt1000Repeat1000M10To14").html(male1014)
                jq("#resultPast12Mt1000Repeat1000M15To19").html(male1519)
                jq("#resultPast12Mt1000Repeat1000M20To24").html(male2024)
                jq("#resultPast12Mt1000Repeat1000F10To14").html(female1014)
                jq("#resultPast12Mt1000Repeat1000F15To19").html(female1519);
                jq("#resultPast12Mt1000Repeat1000F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#resultPast12Mt1000Repeat1000Total").html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithSwitchTo2ndLine") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#switchTo2ndM10To14").html(male1014)
                jq("#switchTo2ndM15To19").html(male1519)
                jq("#switchTo2ndM20To24").html(male2024)
                jq("#switchTo2ndF10To14").html(female1014)
                jq("#switchTo2ndF15To19").html(female1519);
                jq("#switchTo2ndF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#switchTo2ndTotal").html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledWithSwitchTo3rdLine") }');
            })


             .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#switchTo3rdM10To14").html(male1014)
                jq("#switchTo3rdM15To19").html(male1519)
                jq("#switchTo3rdM20To24").html(male2024)
                jq("#switchTo3rdF10To14").html(female1014)
                jq("#switchTo3rdF15To19").html(female1519);
                jq("#switchTo3rdF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#switchTo3rdTotal").html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalAYPLHIVEnrolledInOTZWhoComplete7") }');
            }).then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#completed7M10To14").html(male1014)
                jq("#completed7M15To19").html(male1519)
                jq("#completed7M20To24").html(male2024)
                jq("#completed7F10To14").html(female1014)
                jq("#completed7F15To19").html(female1519);
                jq("#completed7F20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#completed7Total").html(total)
                
                totalCompleted7 = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024) ;
                console.log("total Completed", totalCompleted7);
                renderCompletedCharts(totalEnrolled, totalCompleted7);
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledAndTransferredOutAfter") }');
            })

         .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#transferredOutM10To14").html(male1014)
                jq("#transferredOutM15To19").html(male1519)
                jq("#transferredOutM20To24").html(male2024)
                jq("#transferredOutF10To14").html(female1014)
                jq("#transferredOutF15To19").html(female1519);
                jq("#transferredOutF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#transferredOutTotal").html(total)
                
                totalTransferred =  new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024) ;
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledAndLTFUAfter") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#ltfuM10To14").html(male1014)
                jq("#ltfuM15To19").html(male1519)
                jq("#ltfuM20To24").html(male2024)
                jq("#ltfuF10To14").html(female1014)
                jq("#ltfuF15To19").html(female1519);
                jq("#ltfuF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#ltfuTotal").html(total)
                
                var totalLTFU = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024) ;
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledAndDiedAfter") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#deadM10To14").html(male1014)
                jq("#deadM15To19").html(male1519)
                jq("#deadM20To24").html(male2024)
                jq("#deadF10To14").html(female1014)
                jq("#deadF15To19").html(female1519);
                jq("#deadF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#deadTotal").html(total)
                
                totalDead = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024) ;
                
                renderLossesChart(totalDead, totalTransferred, totalLTFU);
                
                renderEnrolledVsTransferred(totalEnrolled, totalTransferred);
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledAndOptedOutAfter") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#optedOutM10To14").html(male1014)
                jq("#optedOutM15To19").html(male1519)
                jq("#optedOutM20To24").html(male2024)
                jq("#optedOutF10To14").html(female1014)
                jq("#optedOutF15To19").html(female1519);
                jq("#optedOutF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#optedOutTotal").html(total)
                
                totalOptedOut = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024) ;
                
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledAndTransitionedAfter") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#transitionedM10To14").html(male1014)
                jq("#transitionedM15To19").html(male1519)
                jq("#transitionedM20To24").html(male2024)
                jq("#transitionedF10To14").html(female1014)
                jq("#transitionedF15To19").html(female1519);
                jq("#transitionedF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#transitionedTotal").html(total)
                
                totalTransitioned = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024) ;;
                return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEnrolledAndExitedAfter") }');
            })

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"];
                
                jq("#exitedM10To14").html(male1014)
                jq("#exitedM15To19").html(male1519)
                jq("#exitedM20To24").html(male2024)
                jq("#exitedF10To14").html(female1014)
                jq("#exitedF15To19").html(female1519);
                jq("#exitedF20To24").html(female2024)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024);
                jq("#exitedTotal").html(total)
                
                totalExited = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(female1014) + new Number(female1519) + new Number(female2024) ;
                renderExitChart(totalExited, totalTransitioned, totalOptedOut);
                
                renderEnrolledVsExits(totalEnrolled, totalExited);
                
                console.log("completed", datatableObj);
                //datatableObj.destroy();
                //datatableObj.draw();
                datatableObj.rows().invalidate().draw()
               
                //return  myAjax({startDate:startDate, endDate:endDate}, '${ ui.actionLink("getTotalEligibleForMonthZeroVL") }');
            })
   }
</script>








