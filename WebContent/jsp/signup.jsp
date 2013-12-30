<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<jsp:include page="header.jsp" />


   <div class="">
      <form class="form-signin well" action="./api/create_user"  method="post" >
        <h2 class="form-signin-heading">新規作成</h2>
        <input type="text" class="form-control" placeholder="User ID" required autofocus id="user_id" name="user_id">
        <input type="password" class="form-control" placeholder="Password" required  id="user_password" name="user_password">
        <input type="password" class="form-control" placeholder="Password" required  id="user_password2" name="user_password2">
        <input type="hidden" id="mode" name="mode" value="navigate">
        <button class="btn btn-danger btn-block" type="submit">新規作成</button>
      </form>
   </div>

<jsp:include page="footer.jsp" />