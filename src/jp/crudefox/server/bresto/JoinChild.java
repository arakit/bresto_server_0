package jp.crudefox.server.bresto;

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

import jp.crudefox.server.bresto.db.DBChildTable;
import jp.crudefox.server.bresto.db.DBProjectTable;
import jp.crudefox.server.bresto.db.DBChildTable.ChildRow;
import jp.crudefox.server.bresto.db.DBProjectTable.ProjectRow;
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "子ユーザがプロジェクトへ参加",
		urlPatterns = { "/JoinChild" },
		initParams = {
				@WebInitParam(name = "project_id", value = "")
		})
public class JoinChild extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinChild() {
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

			String project_id = CFUtil.getParam(request,"project_id");

			if(TextUtil.isEmpty(project_id)) throw new Exception("non project id.");


	         //データベースへの接続
	         Connection cn = Const.getDefaultDBConnection();

	         DBProjectTable db_pro = new DBProjectTable(cn);
	         ProjectRow pr = db_pro.getById(project_id);

	         if(pr==null) throw new Exception("not exist project.");

	         DBChildTable db_child = new DBChildTable(cn);
	         ChildRow cr = db_child.insertNew(project_id);

	         if(cr==null) throw new Exception("can not regster child in project.");


	         ObjectMapper om = new ObjectMapper();

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         b.put("result", "OK");
	         b.put("child_id", cr.id);

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

