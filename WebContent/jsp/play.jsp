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

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>マインドマップ</title>
    <link href="css/demo.css" rel="stylesheet">
    <link href="plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="plugin/jquery-2.0.3.min.js"></script>
    <script src="plugin/underscore-min.js"></script>
    <script src="plugin/jcanvas.min.js"></script>
    <script src="plugin/flipsnap.min.js"></script>
    <script src="plugin/knockout-3.0.0.js"></script>
    <script src="plugin/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/bresto/util.js"></script>
    <script src="js/bresto/node.js"></script>
    <script src="js/bresto/edge.js"></script>
    <script src="js/bresto/canvas.js"></script>
    <script src="js/bresto/fakewebsocket.js"></script>
    <script src="js/bresto/connection.js"></script>
    <script src="js/bresto/system.js"></script>
    <script src="js/main.js"></script>
    <style>
        body {
            -webkit-text-size-adjust: 100%;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="span12">
                <div id="loading"><img src="img/load.gif"></div>
                <canvas class="bresto"></canvas>
            </div>
        </div>
    </div>

    <aside>
        <div id="good">
            <button data-bind="click: onGood, enable: isFormEnabled" type="button" class="btn btn-warning btn-lg">
                <span data-bind="text: good" class="glyphicon glyphicon-star"></span>
            </button>
        </div>

        <div id="footer" class="panel-footer">
            <div class="row">
                <div class="suggestion-viewport span12">
                    <div class="suggestion"  data-bind="foreach: suggestion">
                        <div data-bind="text: data, click: $root.onClickSuggestion" class="item"></div>
                    </div>
                </div>
                <button type="button" class="remove-aside-button close" data-bind="click: hideFooter">&times;</button>
            </div>

            <div class="row">
                <form data-bind="submit: onSubmit">
                    <div class="input-group">
                        <label data-bind="text: from().keyword + ' >>> '" for="to" class="input-group-addon"></label>
                        <input data-bind="value: to, enable: isFormEnabled" type="text" id="to" class="form-control">
                        <span class="input-group-btn">
                            <button data-bind="enable: isFormEnabled" class="btn btn-default" type="submit">
                                <span class="glyphicon glyphicon-circle-arrow-up"></span>
                            </button>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </aside>
</body>
</html>