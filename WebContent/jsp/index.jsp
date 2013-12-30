<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<jsp:include page="header.jsp" />



    <!-- Carousel
    ================================================== -->
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
              <p><a class="btn btn-lg btn-danger" href="signup.html" role="button">新規登録</a></p>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="./img/carousel_test_1.jpg" alt="Second slide"  width="900" height="500" >
          <div class="container">
            <div class="carousel-caption">
              <h1>ぶれすと！</h1>
              <p>アイデアを広げよう！</p>
              <p><a class="btn btn-lg btn-primary" href="signup.html" role="button">新規登録</a></p>
            </div>
          </div>
        </div>
        <div class="item">
          <img data-src="holder.js/900x500/auto/#555:#5a5a5a/text:Third slide" alt="Second slide"  width="900" height="500" >
          <div class="container">
            <div class="carousel-caption">
              <h1>ぶれすと！</h1>
              <p>アイデアを広げよう！</p>
              <p><a class="btn btn-lg btn-primary" href="signup.html" role="button">新規登録</a></p>
            </div>
          </div>
        </div>        
      </div>
      <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
    </div><!-- /.carousel -->





      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>ぶれすと！</h1>
        <p>アイデアを広げたいでしょ！！</p>
        <p><a href="#" class="btn btn-info btn-large">稼働中 &raquo;</a></p>
      </div>


      
      

      <!-- Example row of columns -->
      <div class="row">
        <div class="col-md-4">
          <h2>稼働中</h2>
          <p>サーバー稼働しています。</p>
          <p><a class="btn btn-default" href="#"  role="button">詳細を見る &raquo;</a></p>
        </div>
        <div class="col-md-4">
          <h2>----</h2>
          <p>----</p>
          <p><a class="btn btn-default" href="#">詳細を見る &raquo;</a></p>
        </div>
        <div class="col-md-4">
          <h2>----</h2>
          <p>----</p>
          <p><a class="btn" href="api.html">詳細を見る &raquo;</a></p>
        </div>
      </div>
      
      
      
      
      <!-- START THE FEATURETTES -->

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7">
          <h2 class="featurette-heading">アイデアの発散<span class="text-muted"></span></h2>
          <p class="lead">アイデアの発散で困ったことはありませんか？</p>
        </div>
        <div class="col-md-5">
          <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        </div>
      </div>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-5">
          <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        </div>
        <div class="col-md-7">
          <h2 class="featurette-heading">アイデアの共有</span></h2>
          <p class="lead">リアルタイムに同期しながら、マインド図を作成します。</p>
        </div>
      </div>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7">
          <h2 class="featurette-heading">最後に...<span class="text-muted">いいものをつくろう。</span></h2>
          <p class="lead">いいものをつくるには、いいアイデアが必要です。</p>
        </div>
        <div class="col-md-5">
          <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        </div>
      </div>

      <hr class="featurette-divider">

      <!-- /END THE FEATURETTES -->
      
      

<jsp:include page="footer.jsp" />