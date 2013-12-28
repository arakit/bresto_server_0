package jp.crudefox.server.bresto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.crudefox.server.bresto.Const;
import jp.crudefox.server.bresto.db.DBSessionTable;
import jp.crudefox.server.bresto.db.DBSessionTable.SessionRow;
import jp.crudefox.server.bresto.db.DBUserTable;
import jp.crudefox.server.bresto.db.DBUserTable.UserRow;
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "ログインするため",
		urlPatterns = { "/login" },
		initParams = {
				@WebInitParam(name = "user_id", value = ""),
				@WebInitParam(name = "user_pass", value = "")
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

		CFUtil.initMySQLDriver();

		response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

		try{

			String id = CFUtil.getParam(request,"user_id");
			String pass = CFUtil.getParam(request,"user_pass");

			if(TextUtil.isEmpty(id)) throw new Exception("non id.");
			if(TextUtil.isEmpty(pass)) throw new Exception("non password.");


			if(id.length()<4) throw new Exception("length of id is 4 or more. ");
			if(pass.length()<4) throw new Exception("length of password is 4 or more. ");

			if(id.length()<4) throw new Exception("length of id is 32 or less. ");
			if(pass.length()<4) throw new Exception("length of password is 32 or less. ");


	         //データベースへの接続
	         Connection cn = Const.getDefaultDBConnection();

	         DBUserTable at = new DBUserTable(cn);

	         UserRow ar = at.getById(id);

	         if(ar==null){
	        	 cn.close();
	        	 throw new Exception("not exist account");
	         }
	         if( !pass.equals( ar.pass ) ){
	        	 cn.close();
	        	 throw new Exception("incorrect password");
	         }


//	         pw.write("login succes : "+ ar.id + ", "+ ar.name + ", "+ar.password );
//	         pw.write("あいうえお。");

	         //String sid = Secure.createRandomAlphabet(32);
	         DBSessionTable db_st = new DBSessionTable(cn);
	         SessionRow sr = db_st.insertNew(id);

	         if(sr==null){
	        	 cn.close();
	        	 throw new Exception("not create session");
	         }

	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
//	         ResultBean bean = new ResultBean();
//	         bean.result = "OK";
//	         bean.id = ar.id;
//	         bean.name = ar.name;
//	         bean.token = "sfadsadwsad";
	         b.put("result", "OK");
	         b.put("user_id", ar.id);
	         b.put("user_name", "名無し"+ar.id);
	         b.put("sid", sr.sid);

	         String json = om.writeValueAsString(b);
	         pw.write(json);

	         //接続のクローズ
	         cn.close();
	     }
	     catch(Exception e){
	        e.printStackTrace();

	         ObjectMapper om = new ObjectMapper();
	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         b.put("result", "FAILED");
	         b.put("info", e.getMessage() );

	         String json = om.writeValueAsString(b);
	         pw.write(json);
	     }



		//response.getWriter().write("Hello, World!");

	}






}

