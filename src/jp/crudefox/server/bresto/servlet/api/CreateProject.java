package jp.crudefox.server.bresto.servlet.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
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
		description = "作成",
		urlPatterns = { "/api/create_project" },
		initParams = {
				//@WebInitParam(name = "sid", value = ""),
		})
public class CreateProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProject() {
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
			//String project_id = params.getStringParam("project_id");
			String project_name = params.getStringParam("project_name");
			String _width = params.getStringParam("width");
			String _height = params.getStringParam("height");

//			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
//			if(TextUtil.isEmpty(project_id)) throw new Exception("non project id.");
//			if(TextUtil.isEmpty(parent_kid)) throw new Exception("non project name");
			if(TextUtil.isEmpty(project_name)) throw new Exception("non project_name.");

			int width, height;
			if( TextUtil.isEmpty( _width ) ) width = 500;
			else width = Integer.parseInt(_width);
			if( width < 100 ) throw new Exception("can not parent_kid < 100");
			
			if( TextUtil.isEmpty( _height ) ) height = 500;
			else height = Integer.parseInt(_height);
			if( height < 100 ) throw new Exception("can not parent_kid < 100");
			
			
			String user_id;

			HttpSession ses = request.getSession();
			user_id = (String) ses.getAttribute(Const.SES_USER_ID);

			if(TextUtil.isEmpty(user_id))  throw new Exception("not login.");

	         //データベースへの接続
	         cn = Const.getDefaultDBConnection();
	         cn.setAutoCommit(false);

	         DBProjectTable db_pro = new DBProjectTable(cn);
	         ProjectRow pr = db_pro.insertNew(project_name, user_id);
	         if(pr==null) throw new Exception("failed insert new.");


	         //接続のクローズ
	         cn.commit(); cn.close(); cn = null;
	         
	         if("navigate".equals(mode)){
			        response.sendRedirect("../bresto.html");
	         }else{
		         //結果
		         ObjectMapper om = new ObjectMapper();
		         om.configure(SerializationFeature.INDENT_OUTPUT  , true);
	
		         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
		         LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();
	
		         b_data.put("project_id", pr.id);
		         b_data.put("project_name", pr.name);
	
	
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
		        response.sendRedirect("../create.html");
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

