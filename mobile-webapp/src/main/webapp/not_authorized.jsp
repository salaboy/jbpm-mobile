<%
  request.getSession().invalidate();
  String redirectURL = request.getContextPath()  +"/org.kie.mobile.MobileShowcase/KIEMobile.html?message=Login failed: Not Authorized";
  response.sendRedirect(redirectURL);
%>