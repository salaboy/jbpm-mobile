<%
  final String queryString = request.getQueryString();
  final String redirectURL = "org.kie.mobile.MobileShowcase/KIEMobile.html" + (queryString == null ? "" : "?" + queryString);
  response.sendRedirect(redirectURL);
%>
