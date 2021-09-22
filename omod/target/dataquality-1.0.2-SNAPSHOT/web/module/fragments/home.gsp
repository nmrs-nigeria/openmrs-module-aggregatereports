<%
ui.includeJavascript("dataquality", "jquery-3.3.1.js")
ui.includeJavascript("dataquality", "jquery.dataTables.min.js")
ui.includeJavascript("dataquality", "datatables.min.js")
ui.includeJavascript("dataquality", "buttons.flash.min.js")
ui.includeJavascript("dataquality", "jszip.min.js")
ui.includeJavascript("dataquality", "pdfmake.min.js")
ui.includeJavascript("dataquality", "vfs_fonts.js")
ui.includeJavascript("dataquality", "buttons.html5.min.js")
ui.includeJavascript("dataquality", "buttons.print.min.js")

ui.includeCss("dataquality", "buttons.dataTables.min.css")
ui.includeCss("dataquality", "jquery.dataTables.min.css")



def id = config.id
%>
<link rel="stylesheet" href="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/font-awesome/css/font-awesome.min.css"/>
<%=ui.resourceLinks()%>
<div class="container">
    <h2>Aggregate Reports</h2>
    <div class="row">
        <label class="col-sm-4 col-md-2 "><strong>Dataset</strong></label>
        <div class="col-sm-4 col-md-4">
            <select name="dataset" id="dataset" class="form-control">
                <option value="">Select Dataset</option>
                <option value="DQR">DQR</option>
                <option value="CQI">CQI</option>
                <option value="TB">TB</option>
                <option value="OTZ">OTZ</option>
            </select>
        </div>
        <div class="col-sm-3 col-md-3">
            <button class="button" id="pullDataSet">Get Dataset</button>
        </div>
    </div>

    <div id="resultArea">

    </div>
</div>




<script>
    jq = jQuery;

    jq(document).ready(function() {
        jq('#myTable').DataTable( {
        dom: 'Bfrtip',
        buttons: [
        'copy', 'csv', 'excel', 'pdf', 'print'
        ]
        } );



        //wire the 'pull data set' button
        jq("#pullDataSet").click(function(e){

            jq("#resultArea").html('<div class="center"><img width="50" src="${ ui.resourceLink("dataquality", "images/loading.gif") }" /></div>');
            var dataset = jq("#dataset").val();

            if(dataset == "DQR")
            {
                jq( "#resultArea" ).load( "dqr.page" );
            }
            else if(dataset == "CQI"){
                jq( "#resultArea" ).load( "cqi.page" );
            }
            else if(dataset == "TB"){
                jq( "#resultArea" ).load( "tb.page" );
            }else if(dataset == "OTZ")
            {
                jq( "#resultArea" ).load( "otz.page" );
            }
            else{
                jq( "#resultArea" ).html(""); 
            }


        });
    });
</script>

<script type="text/javascript">
    jq = jQuery;


</script>










