<%@page import="jp.crudefox.server.bresto.Const"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.crudefox.server.bresto.db.DBProjectTable.ProjectRow"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<%

%>

<jsp:include page="header.jsp" />


      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>新規作成</h1>
        <p>ぶれすと図を新しく作成します。</p>
      </div>

      <!-- Example row of columns -->

          <!--  -->

          <br />


          <div class="page-header">
           <h1>新規作成</h1>
          </div>
          
		  <div class="row">

		    <div class="col-sm-12 col-md-6 col-md-offset-3">
		      <div class="thumbnail">
		        <img data-src="holder.js/300x200" alt="300x200">
		        <div class="caption">
		        
			      <!-- enctype="multipart/form-data" -->
		          <form class="" method="post" action="api/create_project"  enctype="multipart/form-data" >
	
		            <fieldset>
		              <legend>新規作成</legend>
		              
		              <div class="form-group">
		                <label class="col-xs-3" for="project_name">タイトル</label>
		                <div class="input-group col-xs-9">
		                  <input class="form-control input-xlarge" type="text" id="project_name" name="project_name" >
		                </div>
		              </div>
		              
		              <div class="form-group">
		                <label class="col-xs-3" for="width">幅</label>
		                <div class="input-group col-xs-9">
						  <input type="text" class="form-control input-xlarge" placeholder="例)500" id="width" name="width">
						  <span class="input-group-addon">px</span>
		                </div>
		              </div>
		              
		              <div class="form-group">
		                <label class="col-xs-3" for="height">高さ</label>
		                <div class="input-group col-xs-9">
						  <input type="text" class="form-control input-xlarge" placeholder="例)500" id="height" name="height">
						  <span class="input-group-addon">px</span>
		                </div>
		              </div>	              
		              
	                  <input type="hidden" name="mode"  value="navigate">
	                  
         		      <div class="form-group btn-group-lg">
		                  <button type="submit" class="btn btn-success col-xs-6">作成</button>
		                  <button type="reset" class="btn btn-default col-xs-6">キャンセル</button>
		              </div>
		              
		            </fieldset>
		          </form>

		        </div>
		      </div>
		    </div>


		  </div>


		  <hr />



<jsp:include page="footer.jsp" />

