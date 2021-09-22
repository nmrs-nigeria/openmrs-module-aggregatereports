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

%>
<%=ui.resourceLinks()%>
<div class="container">
    <h3>${ui.format(title)}</h3>
    
    <fieldset>
        <legend>Filters</legend>
        <form action="" type="GET">
            <input type="hidden" name="type" id="type" value="<%= type %>" />
            <div class="row">
               <label class="col-sm-6 col-md-2 " ><strong>Start Date</strong></label>
                <div class="col-sm-6 col-md-3" style="position:relative">
                   <input type="text" class="form-control date" id="startDate" name="startDate" value="<%=startDate %>"/>
                </div>

                <label class="col-sm-6 col-md-2 "><strong>End Date</strong></label>
                <div class="col-sm-6 col-md-3" style="position:relative">
                   <input type="text" class="form-control date" id="endDate" name="endDate" value="<%=endDate %>"/>
                </div>


                <div class="col-sm-6 col-md-2">
                    <button class="submit" id="filterCQI">Filter</button>
                </div>
            </div>
        </form>
    </fieldset>
    <br/>
    <table id="myTable" class="display dataTable">
       
        <thead style="font-size: 13px;">
            <tr>
                <th>S/N</th>
                <th>ART No.</th>
                -<th>First Name</th>
                <th>Last Name</th>
                <th>Edit Record </th>
            </tr>

        </thead>
        <tbody>
           <% for(int i=0; i<data.size(); i++){%>
               <tr>
                   <td>${ui.format(i+1)}</td>
                  <td>${ui.format(data.get(i).get("patientIdentifier"))}</td>  
                  <td>${ui.format(data.get(i).get("firstName"))}</td>  
                  <td>${ui.format(data.get(i).get("lastName"))}</td>
                  <td><a href="/<%= ui.contextPath();%>${ui.format(data.get(i).get("link"))}" class="button" title="Fix issue">Fix Issue</a></td>  
                  
               </tr>
           <% } %>
                

        </tbody>
    </table>
</div>




<script>
    jq = jQuery;

    jq(document).ready(function() {
    
    jq('.date').datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth:true,
            yearRange: "-30:+0",
            autoclose: true
        });
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
                        title:'<%= title; %>',
                        exportOptions: {
                            columns: [0,1,2,3]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'excel',
                        title:'<%= title; %>',
                        exportOptions: {
                            columns: [0,1,2,3]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'pdf',
                        title:'<%= title; %>',
                        exportOptions: {
                            columns: [0,1,2,3]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'print',
                        title:'<%= title; %>',
                        exportOptions: {
                            columns: [0,1,2,3]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    //'pdf', 
                    //'print'
                ]

                });
    } );
</script>
<style>
    
    .dt-buttons{
    float: right;
    }
    #apps{
    margin-bottom: 60px;
    }
    #myTable_filter{
        width:50% !important;
    }#myTable_filter input {
        height:30px !important;
    }
    #myTable {
    width: 100%;
    margin-left: 0%;
    }
    .buttons-html5{
    text-decoration: none;
    margin-left: 5px;
    margin-bottom: 10px;
    text-align: center;
    border-radius: 3px;
    background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #5b57a6), color-stop(100%, #5b57a6));
    background: -webkit-linear-gradient(top, #5b57a6, #5b57a6);
    background: -moz-linear-gradient(top, #5b57a6, #5b57a6);
    background: -o-linear-gradient(top, #5b57a6, #5b57a6);
    background: -ms-linear-gradient(top, #5b57a6, #5b57a6);
    background: linear-gradient(top, #5b57a6, #5b57a6);
    background-color: #5b57a6;
    border: #5b57a6 1px solid;
    padding: 8px 20px;
    display: inline-block;
    line-height: 1.2em;
    color: white;
    cursor: pointer;
    min-width: 0;
    max-width: 300px;
    text-decoration: none;
    padding: 5px 6px;
    min-width: 70px;
    font-size: 0.9em;
    }

    #myTable_paginate{
    display: flex-inline;
    }
    #myTable_paginate li {
    margin:2px;
    padding:3px;
    text-decoration: none;
    text-align: center;
    border-radius: 3px;
    background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #5b57a6), color-stop(100%, #5b57a6));
    background: -webkit-linear-gradient(top, #5b57a6, #5b57a6);
    background: -moz-linear-gradient(top, #5b57a6, #5b57a6);
    background: -o-linear-gradient(top, #5b57a6, #5b57a6);
    background: -ms-linear-gradient(top, #5b57a6, #5b57a6);
    background: linear-gradient(top, #5b57a6, #5b57a6);
    background-color: #5b57a6;
    border: #5b57a6 1px solid;
    display: inline-block;
    color: white;
    cursor: pointer;
    width:auto;
    }

    #myTable_paginate li a{
    color:white;
    } 
</style>
<script type="text/javascript">
    jq = jQuery;
    var cohortAjaxUrl = '${ ui.actionLink("getCohortCounts") }';
    
     
   
</script>










