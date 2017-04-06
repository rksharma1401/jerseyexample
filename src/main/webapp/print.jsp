<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p>
<%
 
for (Enumeration<String> enumeration = request.getAttributeNames(); enumeration.hasMoreElements();) {
    String attributeName = enumeration.nextElement();
    Object attribute = request.getAttribute(attributeName);
    out.println(attributeName + " -> " + attribute.getClass().getName() + ":" + attribute.toString());
}
 
%>
</p>

<p>
<%
 
for (Enumeration<String> enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
    String paramName = enumeration.nextElement();
    Object param = request.getParameter(paramName);
    out.println(paramName + " -> " + param.getClass().getName() + ":" + param.toString());
}
 
%>
</p>
</body>
</html>