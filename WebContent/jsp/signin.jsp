<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<jsp:include page="header.jsp" />



   <div class="">
      <form class="form-signin well" action="./api/login"  method="post" >
        <h2 class="form-signin-heading">ログイン</h2>
        <input type="text" class="form-control" placeholder="User ID" required autofocus id="user_id" name="user_id">
        <input type="password" class="form-control" placeholder="Password" required  id="user_password" name="user_password">
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <input type="hidden" id="mode" name="mode" value="navigate">
        <button class="btn btn-primary btn-block" type="submit">ログイン</button>
      </form>
   </div>

<jsp:include page="footer.jsp" />