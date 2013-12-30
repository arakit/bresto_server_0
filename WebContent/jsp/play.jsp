<%@page import="jp.crudefox.server.bresto.Const"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<%

HttpSession ses = request.getSession();
String project_id = (String) ses.getAttribute(Const.SES_PROJECT_ID);

%>

<jsp:include page="header.jsp" />

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1><%= ""+project_id %></h1>
      </div>


<jsp:include page="footer.jsp" />