<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<jsp:include page="header.jsp" />


	<div class="row">
	  <div class="span1"></div>
	  <div class="span10 columns">
		  <div id="myCarousel" class="carousel slide" >
		    <!-- Carousel items -->
		    <div class="carousel-inner">
		      <!-- item1 -->
		      <div class="active item">
		        <img src="./img/carousel_test_1.jpg" alt="" width="870" height="500">
		      </div>
		      <!-- item2 -->
		      <div class="item">
		        <img src="./img/carousel_test_2.jpg" alt="" width="870" height="500">
		      </div>
		      <!-- item3 -->
		      <div class="item">
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


      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h1>ぶれすと！</h1>
        <p>アイデアを広げたいでしょ！！</p>
        <p><a href="#" class="btn btn-info btn-large">稼働中 &raquo;</a></p>
      </div>


      
      

      <!-- Example row of columns -->
      <div class="row">
        <div class="span4">
          <h2>稼働中</h2>
          <p>サーバー稼働しています。</p>
          <p><a class="btn" href="#">詳細を見る &raquo;</a></p>
        </div>
        <div class="span4">
          <h2>----</h2>
          <p>----</p>
          <p><a class="btn" href="#">詳細を見る &raquo;</a></p>
        </div>
        <div class="span4">
          <h2>----</h2>
          <p>----</p>
          <p><a class="btn" href="api.html">詳細を見る &raquo;</a></p>
        </div>
      </div>
      
      
      
      
      <!-- START THE FEATURETTES -->

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7 span7">
          <h2 class="featurette-heading">アイデアの発散<span class="text-muted"></span></h2>
          <p class="lead">アイデアの発散で困ったことはありませんか？</p>
        </div>
        <div class="col-md-5 span5">
          <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        </div>
      </div>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-5 span5">
          <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        </div>
        <div class="col-md-7 span7">
          <h2 class="featurette-heading">アイデアの共有</span></h2>
          <p class="lead">リアルタイムに同期しながら、マインド図を作成します。</p>
        </div>
      </div>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7 span7">
          <h2 class="featurette-heading">最後に...<span class="text-muted">いいものをつくろう。</span></h2>
          <p class="lead">いいものをつくるには、いいアイデアが必要です。</p>
        </div>
        <div class="col-md-5 span5">
          <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        </div>
      </div>

      <hr class="featurette-divider">

      <!-- /END THE FEATURETTES -->
      
      

<jsp:include page="footer.jsp" />