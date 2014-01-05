package jp.crudefox.server.bresto.servlet.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.crudefox.server.bresto.Const;
import jp.crudefox.server.bresto.db.DBProjectTable;
import jp.crudefox.server.bresto.db.DBProjectTable.ProjectRow;
import jp.crudefox.server.bresto.util.CFServletParams;
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "プロジェクト作成",
		urlPatterns = { "/api/select_project" },
		initParams = {
				//@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "project_id", value = "")
		})
public class SelectProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectProject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doProc(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doProc(request, response);

	}

	private void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		Connection cn = null;
		CFUtil.initMySQLDriver();

		CFServletParams params = new CFServletParams(this, request, response, new File(Const.DEFAULT_UPFILES_NAME));
		String mode = params.getStringParam("mode");
		if(TextUtil.isEmpty(mode)) mode = "api";

		response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

		try{
			String project_id = params.getStringParam("project_id");

			if(project_id==null) throw new Exception("non project_id.");


			HttpSession ses = request.getSession();

			String user_id = (String) ses.getAttribute(Const.SES_USER_ID);
			if(TextUtil.isEmpty(user_id)) throw new Exception("not login.");

	         //データベースへの接続
	         cn = Const.getDefaultDBConnection();
	         cn.setAutoCommit(false);


	         DBProjectTable db_pro = new DBProjectTable(cn);
	         ProjectRow pr = null;
	         if(!TextUtil.isEmpty(project_id)){
		         pr = db_pro.getById(project_id);
		         if(pr==null) throw new Exception("not exist project.");
		         ses.setAttribute(Const.SES_PROJECT_ID, pr.id);
	         }else{
		         ses.setAttribute(Const.SES_PROJECT_ID, null);
	         }


	         //接続のクローズ
	         cn.close(); cn = null;

	         //結果
	         if("navigate".equals(mode)){
			        response.sendRedirect("../play.html");
	         }else{
		         ObjectMapper om = new ObjectMapper();
		         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

		         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
		         LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();

		         if(pr!=null){
			         b_data.put("project_id", pr.id);
			         b_data.put("project_name", pr.name);
		         }else{
			         b_data.put("project_id", null);
			         b_data.put("project_name", null);
		         }


		         b.put("result", "OK");
		         b.put("data", b_data);

		         String json = om.writeValueAsString(b);

		         response.setStatus(HttpServletResponse.SC_OK);
		         pw.write(json);
	         }


	     }
	     catch(Exception e){
	        e.printStackTrace();

	         if("navigate".equals(mode)){
			        response.sendRedirect("../bresto.html");
	         }else{
		         ObjectMapper om = new ObjectMapper();
		         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
		         b.put("result", "FAILED");
		         b.put("info", e.getMessage() );

		         String json = om.writeValueAsString(b);

		         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		         pw.write(json);
	         }
	     }


		//データベースと切断
		if(cn!=null){ try { if( !cn.isClosed() ) cn.close(); cn = null;} catch (SQLException e) { e.printStackTrace();}}

	}






}

