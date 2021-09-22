
<%
ui.includeJavascript("dataquality", "jquery-3.3.1.js")
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
ui.includeCss("dataquality", "myStyle.css")

def id = config.id
%>
<%=ui.resourceLinks()%>
<div class="container">
    
    <h2><%= title %></h2>
    <fieldset>
        <legend>Filters</legend>
        <div class="row">
           <label class="col-sm-6 col-md-2 " ><strong>Start Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="startDate" name="startDate" value="<%= startDate%>"/>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>Start Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="endDate" name="endDate" value="<%= endDate%>"/>
            </div>
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterTb">Filter</button>
            </div>
        </div>
    </fieldset>
    
    <br/>
      <br/>
      <h3><%= subTitle1%></h3>
    <table class="table dataTable invisible">
        <thead>
            <tr>
                <th>S/N</th>
                <th>ART Number</th>
                <th>Gender</th>
                <th>Age</th>
                <th>Age Band</th>
                <td>&nbsp;</td>
               
            </tr>
        </thead>
        <tbody>
           
           
           <% for(int i=0; i<allPtsEnrolledThisPeriod.size(); i++){ %>
           
            <tr>
                <td><%= (i+1)%></td>
                <td><%= allPtsEnrolledThisPeriod.get(i).get("artNumber"); %></td>
                <td><%= allPtsEnrolledThisPeriod.get(i).get("gender"); %></td>
                <td><%= allPtsEnrolledThisPeriod.get(i).get("dob"); %></td>
                <td><%= allPtsEnrolledThisPeriod.get(i).get("ageRange"); %></td>
                <td><a href="/coreapps/clinicianfacing/patient.page?patientId=<%= allPtsEnrolledThisPeriod.get(i).get("patientId") %>" class="button">View Patient Dashboard</a></td>
            </tr>
           <%} %>
           
            
                   
        </tbody>
        
    </table>
    
     
    <br/>
      <br/>
      <h3><%= subTitle2%></h3>
    <table class="table dataTable invisible">
        <thead>
            <tr>
                <th>S/N</th>
                <th>ART Number</th>
                <th>Gender</th>
                <th>Age</th>
                <th>Age Band</th>
                <td>&bsnp;</td>
            </tr>
        </thead>
        <tbody>
           
           
           <% for(int i=0; i<allPtsEnrolledBeforePeriod.size(); i++){ %>
           
            <tr>
                
                <td><%= (i+1)%></td>
                <td><%= allPtsEnrolledBeforePeriod.get(i).get("artNumber"); %></td>
                <td><%= allPtsEnrolledBeforePeriod.get(i).get("gender"); %></td>
                <td><%= allPtsEnrolledBeforePeriod.get(i).get("dob"); %></td>
                <td><%= allPtsEnrolledBeforePeriod.get(i).get("ageRange"); %></td>
                <td><a href="/coreapps/clinicianfacing/patient.page?patientId=<%= allPtsEnrolledBeforePeriod.get(i).get("patientId") %>" class="button">View Patient Dashboard</a></td>
            </tr>
           <%} %>
           
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

<div id="loadingBg">
    <div id="loader" class="center"></div>
</div>
<style>


    
</style>
<script type="text/javascript">
    jq = jQuery;
    
    
      var totalReportCount = 13;    
      var currReportCount = 0;
                 
      var totalPtsOnArt = 0;
      var totalPedsOnArt = 0;
      var totalAdultsTestedPositive = 0;
      var totalPedsTestedPositive = 0;
      var startDate = "";
      var endDate = "";
      jq(document).ready(function(e){
        
      jq(".date").unbind("datepicker");
      jq('.date').datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth:true,
            yearRange: "-30:+0",
            autoclose: true
        });
        
        
      jq(".dataTable").DataTable({
              "initComplete": function( settings, json ) {
                    
                    jq("#loadingBg").remove();
                    jq(".dataTable").removeClass("invisible");
                },
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
      
       // setupDatePickerPositioner();
      
      //ensure that the toggle button is closed
        
        jq(".cqidetails").click(function(e){
            var subSet = jq(this).attr("data-subset");
            window.location = "cqidetail.page?subset="+subSet+"&startDate="+startDate+"&endDate="+endDate;
        })
      
      
        jq("#filterTb").click(function(e){
           var startDate = jq("#startDate").val();
           var endDate = jq("#endDate").val();
           var type = "<%= type%>"
            window.location = "tbdetails.page?type="+type+"&startDate="+startDate+"&endDate="+endDate
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










