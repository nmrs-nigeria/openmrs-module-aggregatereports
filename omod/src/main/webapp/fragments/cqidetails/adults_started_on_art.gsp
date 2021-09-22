<%


def patients = config.patients
def startDate = config.startDate;
def endDate = config.endDate;
%>
<strong>Note that the age is as at the ART start date</strong>
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
                <button class="button" id="filterCQI">Filter</button>
            </div>
        </div>
    </fieldset>
    <br/>
<div class="table-responsive">
        <table class="table-bordered dataTable" width="100%">
            <thead>
                <th>S/N</th>
                <th>ART Number</th>
                <th>Gender</th>
                <th>ART Start date</th>
                <th>First pickup date</th>
                <th>Age</th>
                <th>Age Range</th>
                
        </thead>
            <tbody>
                <% for(int i=0; i<patients.size(); i++)
                { %>
                    <tr>
                        <td><%= (i+1)%></td>
                        <td><%= patients.get(i).get("artNumber")%></td>
                        <td><%= patients.get(i).get("gender")%></td>
                        <td><%= patients.get(i).get("artStartDate")%></td>
                        <td><%= patients.get(i).get("firstPickup")%></td>
                        <td><%= patients.get(i).get("dob")%></td>
                        <td><%= patients.get(i).get("ageRange")%></td>
    
                    </tr>
                <%}
                %>
            </tbody>
        </table>
        
    
    </div>
