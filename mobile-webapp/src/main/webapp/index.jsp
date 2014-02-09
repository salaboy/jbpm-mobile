<%
  final String queryString = request.getQueryString();
  final String redirectURL = request.getContextPath()  +"org.kie.mobile.MobileShowcase/KIEMobile.html?"+(queryString==null?"":queryString);
  response.sendRedirect(redirectURL);
%>
