<h2>OTZ Dashboard</h2>
    <!--<fieldset>
        <legend>Filters</legend>
        <div class="row">
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
    </fieldset>-->
    <br/>
    
    <div class="row">
        <div class="col-sm-12">
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                  <a class="nav-item nav-link active" id="nav-otzsummary-tab" data-toggle="tab" href="#nav-otzsummary" role="tab" aria-controls="nav-otzsummary" aria-selected="true">OTZ Summary</a>
                  <a class="nav-item nav-link" id="nav-adherence-tab" data-toggle="tab" href="#nav-adherence" role="tab" aria-controls="nav-adherence" aria-selected="false">Adherence</a>
                  <a class="nav-item nav-link" id="nav-vl-tab" data-toggle="tab" href="#nav-vl" role="tab" aria-controls="nav-optimal" aria-selected="false">VL</a>
                  <a class="nav-item nav-link" id="nav-others-tab" data-toggle="tab" href="#nav-others" role="tab" aria-controls="nav-others" aria-selected="false">Others</a>
                </div>
              </nav>
              <div class="tab-content" id="nav-tabContent">
                 <div class="tab-pane fade show active" id="nav-otzsummary" role="tabpanel" aria-labelledby="nav-otzsummary-tab">${ ui.includeFragment("dataquality", "otzdetails/otzdashboardcards") }</div>
                <div class="tab-pane fade" id="nav-adherence" role="tabpanel" aria-labelledby="nav-adherence-tab">${ ui.includeFragment("dataquality", "otzdetails/otzadh") }</div>
                <div class="tab-pane fade" id="nav-vl" role="tabpanel" aria-labelledby="nav-vl-tab">${ ui.includeFragment("dataquality", "otzdetails/otzvl") }</div>
                <div class="tab-pane fade" id="nav-others" role="tabpanel" aria-labelledby="nav-others-tab">${ ui.includeFragment("dataquality", "otzdetails/otzothers") }</div>
              </div>
        </div>
    </div>
    