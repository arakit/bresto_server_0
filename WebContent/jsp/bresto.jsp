<%@page import="jp.crudefox.server.bresto.Const"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.crudefox.server.bresto.db.DBProjectTable.ProjectRow"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<%

List<ProjectRow> project_list = (List<ProjectRow>) request.getAttribute(Const.REQ_PROJECT_LIST);





%>

<jsp:include page="header.jsp" />


      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-3 sidebar-offcanvas visible-sm visible-md visible-lg" id="sidebar" role="navigation">
          <div class="list-group">
            <a href="./create.html" class="list-group-item">新規作成</a>
            <a href="./join.html" class="list-group-item">IDから参加</a>
          </div>
        </div><!--/span-->

        <div class="col-xs-12 col-sm-9">

		
		      <!-- Main hero unit for a primary marketing message or call to action -->
		      <div class="jumbotron">
		        <h1>BreSto一覧</h1>
		        <p>ぶれすと図へ参加しよう</p>
		      </div>
		
		      <!-- Example row of columns -->
		
	          <!--  -->
	
	
		      <div class="row">
		        <div class="col-md-10 col-md-offset-1">
	              <div class="btn-group btn-group-lg btn-group-justified">
	                <a class="btn btn-success" href="./create.html" role="button" href="#">新規作成</a>
	                <a class="btn btn-info" href="./join.html" role="button" href="#">IDから参加する</a>
	              </div>
	            </div>
	          </div>
	
	
	          <br />
	
	
	
	          <br />
	
	
	          <div class="page-header">
	           <h1>ぶれすと一覧</h1>
	          </div>
			  <div class="row">
	
			  <% for(ProjectRow e : project_list){ %>
	
			    <div class="col-sm-6 col-md-4">
			      <div class="thumbnail">
			        <img data-src="holder.js/300x200" alt="300x200">
			        <div class="caption">
			          <h3><%= e.name %></h3>
			          <p>作者：<%= ""+e.author_id %></p>
			          <p>ID：<%= ""+e.id %></p>
			          <p>公開URL：<%= ""+e.publish_url %></p>
			          <div>
			            <form action="./api/select_project" method="post">
	                      <input type="hidden" id="project_id" name="project_id" value="<%= e.id %>">
	                      <input type="hidden" id="mode" name="mode" value="navigate">
	                      <button class="btn btn-primary btn-block" type="submit">開く</button>
			            </form>
			            <!-- <a href="./api/play" class="btn btn-primary btn-lg" role="button">開く</a>-->
			            <!-- <a href="#" class="btn btn-default" role="button">キャンセル</a> -->
			          </div>
			        </div>
			      </div>
			    </div>
	
			  <% } %>
	
			  </div>
	
	          <div class="row">
	            <div class="pager">
				  <ul class="pagination">
				    <li><a href="#">&laquo;</a></li>
				    <li><a href="#">1</a></li>
				    <li><a href="#">2</a></li>
				    <li><a href="#">3</a></li>
				    <li><a href="#">4</a></li>
				    <li><a href="#">5</a></li>
				    <li><a href="#">&raquo;</a></li>
				  </ul>
			    </div>
		  	  </div>
	
	
			<hr />
		
		
		<!--
		      <div class="page-header">
		        <h1>Panels</h1>
		      </div>
		      <div class="row">
		        <div class="col-md-4">
		          <div class="panel panel-default">
		            <div class="panel-heading">
		              <h3 class="panel-title">Panel title</h3>
		            </div>
		            <div class="panel-body">
		              Panel content
		            </div>
		          </div>
		          <div class="panel panel-primary">
		            <div class="panel-heading">
		              <h3 class="panel-title">Panel title</h3>
		            </div>
		            <div class="panel-body">
		              Panel content
		            </div>
		          </div>
		        </div><!-- /.col-sm-4
		        <div class="col-md-4">
		          <div class="panel panel-success">
		            <div class="panel-heading">
		              <h3 class="panel-title">Panel title</h3>
		            </div>
		            <div class="panel-body">
		              Panel content
		            </div>
		          </div>
		          <div class="panel panel-info">
		            <div class="panel-heading">
		              <h3 class="panel-title">Panel title</h3>
		            </div>
		            <div class="panel-body">
		              Panel content
		            </div>
		          </div>
		        </div><!-- /.col-sm-4
		        <div class="col-md-4">
		          <div class="panel panel-warning">
		            <div class="panel-heading">
		              <h3 class="panel-title">Panel title</h3>
		            </div>
		            <div class="panel-body">
		              Panel content
		            </div>
		          </div>
		          <div class="panel panel-danger">
		            <div class="panel-heading">
		              <h3 class="panel-title">Panel title</h3>
		            </div>
		            <div class="panel-body">
		              Panel content
		            </div>
		          </div>
		        </div><!-- /.col-sm-4
		      </div>
		      -->
		
		
		   <br />
		
		
		    <!-- Carousel
		    ================================================== -->
		    <div class="row" style="display: none;">
			  <div class="col-md-12 cf-div">
			  	<h2>よく使うブレスト図</h2>
			  	<hr>
			    <div id="myCarousel" class="carousel slide" data-ride="carousel">
			      <!-- Indicators -->
			      <ol class="carousel-indicators">
			        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			        <li data-target="#myCarousel" data-slide-to="1"></li>
			        <li data-target="#myCarousel" data-slide-to="2"></li>
			      </ol>
			      <div class="carousel-inner">
			        <div class="item active">
			          <img src="./img/carousel_test_1.jpg" alt="First slide" width="900" height="500" >
			          <div class="container">
			            <div class="carousel-caption">
			              <h1>ぶれすと！</h1>
			              <p>アイデアを広げよう！</p>
			              <p><a class="btn btn-lg btn-primary" href="#" role="button">ログイン</a></p>
			            </div>
			          </div>
			        </div>
			        <div class="item">
			          <img src="./img/carousel_test_1.jpg" alt="Second slide"  width="900" height="500" >
			          <div class="container">
			            <div class="carousel-caption">
			              <h1>ぶれすと！</h1>
			              <p>アイデアを広げよう！</p>
			              <p><a class="btn btn-lg btn-primary" href="#" role="button">ログイン</a></p>
			            </div>
			          </div>
			        </div>
			        <div class="item">
			          <img data-src="holder.js/900x500/auto/#555:#5a5a5a/text:Third slide" alt="Second slide"  width="900" height="500" >
			          <div class="container">
			            <div class="carousel-caption">
			              <h1>ぶれすと！</h1>
			              <p>アイデアを広げよう！</p>
			              <p><a class="btn btn-lg btn-primary" href="#" role="button">ログイン</a></p>
			            </div>
			          </div>
			        </div>
			      </div>
			      <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
			      <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
			    </div><!-- /.carousel -->
			  </div>
		    </div>


        </div><!--/span-->

        
      </div><!--/row-->

      <hr>






<jsp:include page="footer.jsp" />

