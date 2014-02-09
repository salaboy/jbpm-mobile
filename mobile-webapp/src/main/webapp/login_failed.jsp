<%
  request.getSession().invalidate();
  String redirectURL = request.getContextPath()  +"/org.kie.mobile.MobileShowcase/KIEMobile.html?message=Login failed: Invalid UserName or Password";
  response.sendRedirect(redirectURL);
%>