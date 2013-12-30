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
import jp.crudefox.server.bresto.db.DBUserTable;
import jp.crudefox.server.bresto.db.DBUserTable.UserRow;
import jp.crudefox.server.bresto.util.CFServletParams;
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "ログイン",
		urlPatterns = { "/api/login" },
		initParams = {
				//@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "user_id", value = ""),
				@WebInitParam(name = "user_password", value = "")
		})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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

			String user_id = params.getStringParam("user_id");
			String user_password = params.getStringParam("user_password");

			if(user_id==null) throw new Exception("non user_id.");
			if(user_password==null) throw new Exception("non user_password.");


			HttpSession ses = request.getSession();

	         //データベースへの接続
	         cn = Const.getDefaultDBConnection();
	         cn.setAutoCommit(false);

	         DBUserTable tb_u = new DBUserTable(cn);
	         UserRow user_row = tb_u.getById(user_id);
	         if(user_row==null) throw new Exception("not exist user.");

	         if( !user_row.pass.equals(user_password) ) throw new Exception("correct password.");

	         ses.setAttribute(Const.SES_USER_ID, user_row.id);

	         //接続のクローズ
	         cn.close(); cn = null;

	         //結果
	         if("navigate".equals(mode)){
		        response.sendRedirect("../index.html");
	         }else{
		         ObjectMapper om = new ObjectMapper();
		         om.configure(SerializationFeature.INDENT_OUTPUT  , true);
	
		         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
		         LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();
	
		         b_data.put("user_id", user_row.id);
		         b_data.put("session_id", ses.getId());
	
	
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
		        response.sendRedirect("../signin.html");
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

