<%@ page contentType="text/html;charset=utf-8" %>
<%-- JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- <jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<jsp:include page="header.jsp" />


      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h1>BreSto API Version 0.1</h1>
        <p>APIのテストページ</p>
      </div>

      <!-- Example row of columns -->

	      <!-- API ログイン  -->
	      <!--
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>ログイン</h2>
	          <p>ログインしてセションIDを取得します。</p>

	          <h3>URL</h3>
	          <p>/Login</p>

	          <h3>パラメータ</h3>
	          <table class="table table-striped table-bordered table-hover">
	          	<thead><tr><th>名前</th><th>説明</th></tr></thead>
	          	<tbody>
	              <tr>
	                <td>user_id</td>
	                <td>ユーザのID,半角英数字</td>
	              </tr>
	            </tbody>
	          </table>

	          <h3>リクエストサンプル</h3>
	          <table  class="table table-striped table-bordered table-hover">
	          <tbody>
	              <tr>
	                <td>GET</td>
	                <td><code>http://192.168.1.107:8080/TunaCanServer0/Login?user_id=user0&user_pass=0000</code></td>
	              </tr>
	            </tbody>
	          </table>
	          <pre>
{
  "result":"OK",
  "user_id":"user0",
  "user_name":"ユーザー0",
  "sid":"fzGvJQXIiSdVIIFikUdsILKVANcfyVDM"
}
	          </pre>
	        </div>
	      </div>
	      -->
          <!-- api -->

          </br>

	      <!-- 22222  -->
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>WebSocket通信開始</h2>
	          <p>WebSocket通信開始</p>

	          <button type="submit" class="btn btn-primary" onclick='onTest001Click()'>さぁ！</button>


	        </div>
	      </div>

          <!--  -->

          <br />

	      <!-- AddNode  -->
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>AddNode</h2>
	          <p>AddNode</p>

	          <h3>パラメータ</h3>
	          <table class="table table-striped table-bordered table-hover">
	          	<thead><tr><th>名前</th><th>説明</th></tr></thead>
	          	<tbody>
	              <tr>
	                <td>keyword</td>
	                <td>キーワード</td>
	              </tr>
	            </tbody>
	          </table>



		      <!-- enctype="multipart/form-data" -->
	          <form class="form-horizontal well" method="post" action="api/add_node"  enctype="multipart/form-data" >
	            <fieldset>
	              <legend>AddNode</legend>

	              <div class="control-group">
	                <label class="control-label" for="keyword">keyword</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="keyword" name="keyword" >
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <div class="control-group">
	                <label class="control-label" for="parent_kid">parent_kid</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="parent_kid" name="parent_kid" >
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <div class="form-actions">
	                <button type="submit" class="btn btn-primary">送信</button>
	                <button type="reset" class="btn">キャンセル</button>
	              </div>
	            </fieldset>
	          </form>

	        </div>
	      </div>

          <!-- AddNode -->

          <br />

	      <!-- RemoveNode  -->
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>RemoveNode</h2>
	          <p>RemoveNode</p>

	          <h3>パラメータ</h3>
	          <table class="table table-striped table-bordered table-hover">
	          	<thead><tr><th>名前</th><th>説明</th></tr></thead>
	          	<tbody>
	              <tr>
	                <td>kid</td>
	                <td>キーワードID</td>
	              </tr>
	            </tbody>
	          </table>



		      <!-- enctype="multipart/form-data" -->
	          <form class="form-horizontal well" method="post" action="api/remove_node"  enctype="multipart/form-data" >
	            <fieldset>
	              <legend>RemoveNode</legend>

	              <div class="control-group">
	                <label class="control-label" for="kid">kid</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="kid" name="kid" >
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <div class="form-actions">
	                <button type="submit" class="btn btn-primary">送信</button>
	                <button type="reset" class="btn">キャンセル</button>
	              </div>
	            </fieldset>
	          </form>

	        </div>
	      </div>

          <!-- RemoveNode -->
          
          <br />          

	      <!-- SetGood  -->
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>SetGood</h2>
	          <p>SetGood</p>

	          <h3>パラメータ</h3>
	          <table class="table table-striped table-bordered table-hover">
	          	<thead><tr><th>名前</th><th>説明</th></tr></thead>
	          	<tbody>
	              <tr>
	                <td>kid</td>
	                <td>キーワードID</td>
	              </tr>
	            </tbody>
	          </table>



		      <!-- enctype="multipart/form-data" -->
	          <form class="form-horizontal well" method="post" action="api/set_good"  enctype="multipart/form-data" >
	            <fieldset>
	              <legend>SetGood</legend>

	              <div class="control-group">
	                <label class="control-label" for="kid">kid</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="kid" name="kid" >
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

	              <div class="form-actions">
	                <button type="submit" class="btn btn-primary">送信</button>
	                <button type="reset" class="btn">キャンセル</button>
	              </div>
	            </fieldset>
	          </form>

	        </div>
	      </div>

          <!-- SetGood -->

          <br />

	      <!-- SelectProject  -->
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>SelectProject</h2>
	          <p>SelectProject</p>

	          <h3>パラメータ</h3>
	          <table class="table table-striped table-bordered table-hover">
	          	<thead><tr><th>名前</th><th>説明</th></tr></thead>
	          	<tbody>
	              <tr>
	                <td>project_id</td>
	                <td>プロジェクトID</td>
	              </tr>
	            </tbody>
	          </table>



		      <!-- enctype="multipart/form-data" -->
	          <form class="form-horizontal well" method="post" action="api/select_project"  enctype="multipart/form-data" >
	            <fieldset>
	              <legend>SelectProject</legend>

	              <div class="control-group">
	                <label class="control-label" for="project_id">project_id</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="project_id" name="project_id" >
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <div class="form-actions">
	                <button type="submit" class="btn btn-primary">送信</button>
	                <button type="reset" class="btn">キャンセル</button>
	              </div>
	            </fieldset>
	          </form>

	        </div>
	      </div>

          <!-- SelectProject -->

          <br />

	      <!-- Login  -->
	      <div class="row">
	        <div class="span10 offset1 cf-div">
	          <h2>Login</h2>
	          <p>Login</p>

	          <h3>パラメータ</h3>
	          <table class="table table-striped table-bordered table-hover">
	          	<thead><tr><th>名前</th><th>説明</th></tr></thead>
	          	<tbody>
	              <tr>
	                <td>user_id</td>
	                <td>ユーザーID</td>
	              </tr>
	            </tbody>
	          </table>



		      <!-- enctype="multipart/form-data" -->
	          <form class="form-horizontal well" method="post" action="api/login"  enctype="multipart/form-data" >
	            <fieldset>
	              <legend>Login</legend>

	              <div class="control-group">
	                <label class="control-label" for="user_id">user_id</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="user_id" name="user_id" >
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <div class="control-group">
	                <label class="control-label" for="user_password">user_password</label>
	                <div class="controls">
	                  <input type="text" class="input-xlarge" id="user_password" name="user_password" >
	                  <p class="help-block"></p>
	                </div>
	              </div>

	              <div class="form-actions">
	                <button type="submit" class="btn btn-primary">送信</button>
	                <button type="reset" class="btn">キャンセル</button>
	              </div>
	            </fieldset>
	          </form>

	        </div>
	      </div>

          <!-- Login -->


<jsp:include page="footer.jsp" />
