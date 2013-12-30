<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<jsp:include page="header.jsp" />


      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h1>BreSto一覧</h1>
        <p>ぶれすと図へ参加しよう</p>
      </div>

      <!-- Example row of columns -->


	      <!-- 22222  -->
	      <!--
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>WebSocket通信開始</h2>
	          <p>WebSocket通信開始</p>

	          <button type="submit" class="btn btn-primary" onclick='onTest001Click()'>さぁ！</button>


	        </div>
	      </div>
	      -->

          <!--  -->

          <br />

	      <!-- SetGood  -->
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>ぶれすと図</h2>
	          <p>マインドマップを開きます。</p>

              <!--
	          <h3>プロジェクトへ参加する。</h3>
	          <table class="table table-striped table-bordered table-hover">
	          	<thead><tr><th>名前</th><th>説明</th></tr></thead>
	          	<tbody>
	              <tr>
	                <td>kid</td>
	                <td>キーワードID</td>
	              </tr>
	            </tbody>
	          </table>
	          -->


		      <!-- enctype="multipart/form-data" -->
	          <form class="form-horizontal well" method="post" action="api/select_project"  enctype="multipart/form-data" >
	            <fieldset>
	              <legend>ぶれすと！</legend>

	              <div class="control-group">
	                <label class="control-label" for="project_id">project_id</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="project_id" name="project_id" >
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <div class="control-group">
	                <label class="control-label" for="value">value</label>
	                <div class="controls">
	                  <select id="value" name="value">
	                    <option value="true">true</option>
	                    <option value="false">false</option>
	                  </select>
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <input type="hidden" id="mode" name="mode" value="navigate" />

	              <div class="form-actions">
	                <button type="submit" class="btn btn-primary">開く</button>
	                <!--<button type="reset" class="btn">キャンセル</button>-->
	              </div>
	            </fieldset>
	          </form>

	        </div>
	      </div>

          <!-- SetGood -->

          <br />


      <div class="page-header">
        <h1>Panels</h1>
      </div>
      <div class="row">
        <div class="span4">
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
        </div><!-- /.col-sm-4 -->
        <div class="span4">
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
        </div><!-- /.col-sm-4 -->
        <div class="span4">
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
        </div><!-- /.col-sm-4 -->
      </div>          



          <br />


	<div class="row">
	  <div class="span1"></div>
	  <div class="span10 columns cf-div">
	      <h2>よく使うブレスト図</h2>
	      <hr />
		  <div id="myCarousel" class="carousel slide" >
		    <!-- Carousel items -->
		    <div class="carousel-inner">
		      <!-- item1 -->
		      <div class="active item">
		        <h3>マップ1</h3>
		        <a class="btn pull-right"  href="#">開く</a>
		        <img src="./img/carousel_test_1.jpg" alt="" width="870" height="500">
		      </div>
		      <!-- item2 -->
		      <div class="item">
		        <h3>マップ2</h3>
		        <a class="btn"  href="#">開く</a>
		        <img src="./img/carousel_test_2.jpg" alt="" width="870" height="500">
		      </div>
		      <!-- item3 -->
		      <div class="item">
		        <h3>マップ3</h3>
		        <a class="btn"  href="#">開く</a>
		        <img src="./img/carousel_test_3.jpg" alt="" width="870" height="500">
		      </div>
		    </div>
		    <!-- Carousel nav -->
		    <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
		    <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
	      </div>
      </div>
      <div class="span1"></div>
    </div>








<jsp:include page="footer.jsp" />

