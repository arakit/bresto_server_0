package jp.crudefox.server.ricoh;

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

import jp.crudefox.server.ricoh.db.DBProjectTable;
import jp.crudefox.server.ricoh.db.DBProjectTable.ProjectRow;
import jp.crudefox.server.ricoh.db.DBSessionTable;
import jp.crudefox.server.ricoh.db.DBSessionTable.SessionRow;
import jp.crudefox.server.ricoh.db.DBUserTable;
import jp.crudefox.server.ricoh.db.DBUserTable.UserRow;
import jp.crudefox.server.ricoh.util.CFUtil;
import jp.crudefox.server.ricoh.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "プロジェクト作成",
		urlPatterns = { "/CreateProject" },
		initParams = {
				@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "project_name", value = "")
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

		CFUtil.initMySQLDriver();

		response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

		try{

			String sid = CFUtil.getParam(request,"sid");
			String project_name = CFUtil.getParam(request,"project_name");

			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
			if(TextUtil.isEmpty(project_name)) throw new Exception("non project name");


	         //データベースへの接続
	         Connection cn = Const.getDefaultDBConnection();

	         DBSessionTable db_s = new DBSessionTable(cn);
	         SessionRow sr = db_s.getBySessionID(sid);

	         if(sr==null) throw new Exception("non login");

	         DBUserTable at = new DBUserTable(cn);
	         UserRow ur = at.getById(sr.user_id);

	         DBProjectTable db_pro = new DBProjectTable(cn);
	         ProjectRow pr = db_pro.insertNew(project_name, ur.id);

	         if(pr==null) throw new Exception("can not created project.");

	         //接続のクローズ
	         cn.close();

	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         b.put("result", "OK");
	         b.put("project_id", pr.id);

	         String json = om.writeValueAsString(b);
	         pw.write(json);
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

