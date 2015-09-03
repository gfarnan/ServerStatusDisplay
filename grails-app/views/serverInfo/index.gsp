<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="test"/>
<title>Touchpoint Servers</title>
</head>
<body>
  <div class="body">
  		<table border="1" style="width:100%">
	        	<tr>
					<th align="left">Server Name</th>
					<th align="left">Status</th>
					<th align="left">Os</th>
					<th align="left">Version</th>
					<th align="left">Owner</th>
					<th align="left">Oracle</th>
					<th align="left">Jboss</th>
					<th align="left">Java</th>
				</tr>					
	        <g:each in="${servers}" var="server" status="i">
	        	<tr>
					<td>${server.host}</td>
					<td><g:fieldValue bean="${server}" field="status"/></td>
					<td><g:fieldValue bean="${server}" field="os"/></td>
					<td><g:fieldValue bean="${server}" field="swVersion"/></td>
					<td><g:fieldValue bean="${server}" field="owner"/></td>
					<td><g:fieldValue bean="${server}" field="oracle"/></td>
					<td><g:fieldValue bean="${server}" field="jboss"/></td>
					<td><g:fieldValue bean="${server}" field="java"/></td>
				</tr>					
	        </g:each>
		</table>	  
  </div>
</body>
</html>