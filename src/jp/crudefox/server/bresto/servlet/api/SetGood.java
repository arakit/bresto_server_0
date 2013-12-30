package jp.crudefox.server.bresto.servlet.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.crudefox.server.bresto.Const;
import jp.crudefox.server.bresto.db.DBGoodTable;
import jp.crudefox.server.bresto.db.DBGoodTable.GoodRow;
import jp.crudefox.server.bresto.db.DBkeywordsTable;
import jp.crudefox.server.bresto.db.DBkeywordsTable.KeywordsRow;
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
		urlPatterns = { "/api/set_good" },
		initParams = {
				//@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "project_id", value = ""),
				@WebInitParam(name = "kid", value = ""),
				@WebInitParam(name = "value", value = "")
		})
public class SetGood extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetGood() {
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

		response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

		try{

			CFServletParams params = new CFServletParams(this, request, response, new File(Const.DEFAULT_UPFILES_NAME));
			String _kid = params.getStringParam("kid");
			String _value = params.getStringParam("value");

//			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
//			if(TextUtil.isEmpty(project_id)) throw new Exception("non project id.");
//			if(TextUtil.isEmpty(parent_kid)) throw new Exception("non project name");
			if(TextUtil.isEmpty(_value)) throw new Exception("non value.");

			int kid;
			kid = Integer.parseInt(_kid);
			if( kid < 0 ) throw new Exception("can not kid < 0");

			boolean value = Boolean.valueOf(_value);
			
			String project_id, user_id;

			HttpSession ses = request.getSession();
			project_id = (String) ses.getAttribute(Const.SES_PROJECT_ID);
			user_id = (String) ses.getAttribute(Const.SES_USER_ID);

			if(TextUtil.isEmpty(project_id))  throw new Exception("not select project.");
			if(TextUtil.isEmpty(user_id))  throw new Exception("not login.");

	         //データベースへの接続
	         cn = Const.getDefaultDBConnection();
	         cn.setAutoCommit(false);

//	         DBProjectTable db_pro = new DBProjectTable(cn);
//	         ProjectRow pr = db_pro.getById(project_id);
//	         if(pr==null) throw new Exception("not exist project.");

	         DBkeywordsTable db_k = new DBkeywordsTable(cn);
	         KeywordsRow kr = db_k.getById(project_id, kid);
	         if(kr==null) throw new Exception("not exist keyword.");


	         DBGoodTable db_g = new DBGoodTable(cn);

        	 GoodRow gr = db_g.getByProjectAndKidAndUserId(project_id, kid, user_id);
	         if(value){
	        	 //goodしたい
	        	 if(gr==null) {
	        		 //Goodしてないとき。 Goodする
	        		 gr = new GoodRow();
	        		 gr.project_id = project_id;
	        		 gr.kid = kid;
	        		 gr.user_id = user_id;
	        		 if( !db_g.insert(gr) ) throw new Exception("failed insert.");
	        	 }
	         }else{
	        	 //un good.したい
	        	 if(gr!=null){
	        		 gr = new GoodRow();
	        		 gr.project_id = project_id;
	        		 gr.kid = kid;
	        		 gr.user_id = user_id;
	        		 if( !db_g.delete(gr) ) throw new Exception("failed delete.");
	        	 }
	         }

	         List<GoodRow> good_list = db_g.getByKId(project_id, kid);
    		 if( good_list==null ) throw new Exception("failed get good list.");


	         SocketNodeEdge nd = SocketNodeEdge.getInstance();
	         if(nd!=null){
	        	 System.out.println("goodしたい");
	        	 nd.addNodeAndEdge(project_id, Const.toNode(kr, good_list.size()), null);
	         }

	         //接続のクローズ
	         cn.commit(); cn.close(); cn = null;

	         //結果
	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();

	         b_data.put("kid", kr.kid);
	         b_data.put("keyword", kr.keyword);
	         b_data.put("value",value);
	         b_data.put("user_id", user_id);
	         b_data.put("good_number", good_list.size());


	         b.put("result", "OK");
	         b.put("data", b_data);

	         String json = om.writeValueAsString(b);

	         response.setStatus(HttpServletResponse.SC_OK);
	         pw.write(json);


	     }
	     catch(Exception e){
	        e.printStackTrace();

	         ObjectMapper om = new ObjectMapper();
	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         b.put("result", "FAILED");
	         b.put("info", e.getMessage() );

	         String json = om.writeValueAsString(b);
	         
	         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	         pw.write(json);
	     }


		//データベースと切断
		if(cn!=null){ try { if( !cn.isClosed() ) cn.close(); cn = null;} catch (SQLException e) { e.printStackTrace();}}

	}






}

