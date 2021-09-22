<%


def patients = config.patients
def startDate = config.startDate;
def endDate = config.endDate;
def title = config.title;
def Misc = config.Misc;
%>
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
                <th>Pepfar ID</th>
                <th>Gender</th>
                <th>Age</th>
                 <th>Date Enrolled</th>
                <!--<th>Age Range</th>-->
                
        </thead>
            <tbody>
                <% 
                 int sn = 1;
                for(int i=0; i<patients.size(); i++)
                {
                    if(patients.get(i).getAge() >= 11 && patients.get(i).getAge() <= 24)
                    {
                %>
                    <tr>
                        <td><%= sn++ %></td>
                        <td><%= patients.get(i).getPepfarId()%></td>
                        <td><%= patients.get(i).getGender()%></td>
                        <td><%= patients.get(i).getAge()%></td>
                        <td><%= patients.get(i).getEnrollmentDate()%></td>
                        
                    </tr>
                <%}
              }   
                %>
            </tbody>
        </table>
        
    
    </div>
