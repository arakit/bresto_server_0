<%@page import="jp.crudefox.server.bresto.util.TextUtil"%>
<%@page import="jp.crudefox.server.bresto.Const"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<%
    class Head{
		String key;
		String title;
		String url;
		String class_name;
		public Head(String key, String title, String url, String class_name){
			this.key = key;
			this.title = title;
			this.url = url;
			this.class_name = class_name;

		}
    }
	ArrayList<Head> heads = new ArrayList<Head>();
	heads.add(new Head("index", "ホーム", "index.html", ""));
	heads.add(new Head("bresto", "ぶれすと図", "bresto.html", ""));
	heads.add(new Head("about", "About", "about.html", ""));
	heads.add(new Head("api", "APIのテスト", "api.html", ""));

	String selp = (String) request.getAttribute(Const.REQ_SELECT_PAGE);

	for(Head e : heads){
		if( e.key.equals(selp) ) e.class_name = "active";
	}

	HttpSession ses = request.getSession();
	String user_id = (String) ses.getAttribute(Const.SES_USER_ID);

	boolean is_logined = !TextUtil.isEmpty(user_id);


%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>ぶれすと！</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="bootstrap3/css/bootstrap.css" rel="stylesheet">
    <link href="crudefox/css/crudefox.css" rel="stylesheet">


    <style type="text/css">
      body {

        padding-top: 60px;
        padding-bottom: 40px;

      }
    </style>
    <!-- <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">-->
    <!-- <link rel="stylesheet" href="bootstrap/css/bootstrap-switch.css" />-->

    <!-- Custom styles for this template -->
    <link href="crudefox/css/signin.css" rel="stylesheet">
    <link href="crudefox/css/carousel.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <!--
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
    -->
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar">a</span>
            <span class="icon-bar">b</span>
            <span class="icon-bar">c</span>
          </button>
          <a class="navbar-brand" href="./">ぶれすと！</a>
        </div>
        <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
            <% for(Head e : heads){ %>
            <li class="<%= e.class_name %>"><a href="<%= e.url %>"><%= e.title %></a></li>
            <% } %>
            <!--
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
            -->
          </ul>


          <% if(!is_logined){ %>
          <form class="navbar-form navbar-right" role="form" action="" method="post">
            <input type="hidden" name="mode" id="navigate" />
            <a href="./signin.html" class="btn btn-success">ログイン</a>
          </form>
          <% }else{ %>
          <form class="navbar-form navbar-right" role="form" action="./api/logout" method="post">
            <a href="#"><%= user_id %></a>
            <input type="hidden" name="mode" value="navigate" />
            <button type="submit" class="btn btn-success">ログアウト</button>
          </form>
          <% } %>



        </div><!--/.navbar-collapse -->
      </div>
    </div>





    <!-- container -->
    <div class="container">


