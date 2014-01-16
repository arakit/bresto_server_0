<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>



    </div> <!-- /container -->

    <hr>

    <footer>
       <p>&copy; chikara, syun, ryusuke 2013</p>
    </footer>



    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <!--<script src="../assets/js/jquery.js"></script>-->
    <script src="bootstrap3/js/bootstrap.min.js"></script>
    <script src="bootstrap3/js/holder.js"></script>


    <script type="text/javascript">
      function onTest001Click(s) {
    	//$('#'+s).attr('checked', !$('#'+s).attr('checked') );

    	    var host = ""+location.host;
    	
    	  //var connection = new WebSocket('ws://bresto.cloudapp.net:8080/BreStoServer0/api/socket_node_edge');
    	  //var connection = new WebSocket('ws://localhost:8080/BreStoServer0/api/socket_node_edge');
    	  //var connection = new WebSocket('ws://192.168.1.117:8080/BreStoServer0/api/socket_node_edge');
    	  var connection = new WebSocket('ws://'+host+'/BreStoServer0/api/socket_node_edge');

    	  //var connection = new WebSocket('ws://localhost:8080/BreStoServer0/api/socket_node_edge', ['bresto_json']);

    	  //var connection = new WebSocket('ws://bresto.cloudapp.net:8080/BreStoServer0/api/socket_node_edge', ['bresto_json']);
    	  //var connection = new WebSocket('wss://bresto.cloudapp.net:8080/BreStoServer0/api/socket_node_edge', ['bresto_json']);
    	  //var connection = new WebSocket('wss://192.168.1.146:8080/BreStoServer0/api/socket_node_edge', ['bresto_json']);
    	  //var connection = new WebSocket('ws://localhost:8080/BreStoServer0/api/socket_node_edge', ['bresto_json']);
    	  //var connection = new WebSocket('ws://192.168.1.112:8080/BreStoServer0/api/socket_node_edge', ['bresto_json']);
    	  //var connection = new WebSocket('ws://192.168.1.146:8080/BreStoServer0/api/socket_node_edge', ['bresto_json']);


  		// Log errors
  		connection.onerror = function (error) {
  		  console.log('WebSocket Error ' + error);
  		};

  		// Log messages from the server
  		connection.onmessage = function (e) {
  		  console.log('Server: ' + e.data);
  		};

    	connection.onopen = function () {
    		 connection.send('Hello!'); // Send the message 'Ping' to the server
    	};


      }
	</script>

	<script type="text/javascript">



	</script>



  </body>
</html>
