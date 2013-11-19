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
import jp.crudefox.server.ricoh.db.DBkeywordsTable;
import jp.crudefox.server.ricoh.db.DBkeywordsTable.KeywordsRow;
import jp.crudefox.server.ricoh.util.CFUtil;
import jp.crudefox.server.ricoh.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "プロジェクト作成",
		urlPatterns = { "/CreateKeyword" },
		initParams = {
				//@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "project_id", value = ""),
				@WebInitParam(name = "parent_kid", value = ""),
				@WebInitParam(name = "keyword", value = "")
		})
public class CreateKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateKeyword() {
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
			String project_id = CFUtil.getParam(request,"project_id");
			//String parent_kid = CFUtil.getParam(request,"parent_kid");
			String keyword = CFUtil.getParam(request,"keyword");

			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
			if(TextUtil.isEmpty(project_id)) throw new Exception("non project id.");
//			if(TextUtil.isEmpty(parent_kid)) throw new Exception("non project name");
			if(TextUtil.isEmpty(keyword)) throw new Exception("non keyword.");


	         //データベースへの接続
	         Connection cn = Const.getDefaultDBConnection();

	         DBProjectTable db_pro = new DBProjectTable(cn);
	         ProjectRow pr = db_pro.getById(project_id);
	         if(pr==null) throw new Exception("not exist project.");

	         DBkeywordsTable db_k = new DBkeywordsTable(cn);

//	         KeywordsRow k1 = db_k.getById(project_id, Integer.parseInt(parent_kid));
//	         if(k1==null) throw new Exception("not exist k1.");

	         KeywordsRow kr = new KeywordsRow();
	         kr.project_id = project_id;
	         kr.keyword = keyword;
	         kr = db_k.insertByAutoIncrement(kr);

	         if(kr==null) throw new Exception("失敗");

//	         DBKeywordsRelationTable db_kr = new DBKeywordsRelationTable(cn);
//	         KeywordsRelationRow krr = new KeywordsRelationRow();
//	         krr.kid1 = Integer.parseInt( parent_kid );
//	         krr.kid2 = kr.kid;
//	         krr.modified_time = null;
//	         krr.project_id = project_id;
//
//	         if(!db_kr.insert(krr)) throw new Exception("失敗");


//	         DBSessionTable db_s = new DBSessionTable(cn);
//	         SessionRow sr = db_s.getBySessionID(sid);
//
//	         if(sr==null) throw new Exception("non login");
//
//	         DBUserTable at = new DBUserTable(cn);
//	         UserRow ur = at.getById(sr.user_id);
//
//	         DBProjectTable db_pro = new DBProjectTable(cn);
//	         ProjectRow pr = db_pro.insertNew(project_name, ur.id);
//
//	         if(pr==null) throw new Exception("can not created project.");

	         //接続のクローズ
	         cn.close();

	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         b.put("result", "OK");
	         b.put("kid", kr.kid);
	         b.put("keyword", kr.keyword);

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

