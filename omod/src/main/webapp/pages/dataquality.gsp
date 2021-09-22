<% 

ui.decorateWith("appui", "standardEmrPage") %>
<%
ui.includeJavascript("dataquality", "myAjax.js")
ui.includeJavascript("dataquality", "popper.js")
ui.includeJavascript("dataquality", "bootstrap.min.js")
ui.includeCss("dataquality", "bootstrap.min.css")
ui.includeCss("dataquality", "myStyle.css")

%>

${ ui.includeFragment("dataquality", "home") }
<% 


%>




