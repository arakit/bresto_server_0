package jp.crudefox.server.bresto.servlet.api;

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
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "プロフィール",
		urlPatterns = { "/SetProfile" },
		initParams = {
				@WebInitParam(name = "sid", value = "")
		})
public class SetProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetProfile() {
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
			String key = CFUtil.getParam(request,"key");

			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
			if(TextUtil.isEmpty(key)) throw new Exception("non keys");


	         //データベースへの接続
	         Connection cn = Const.getDefaultDBConnection();

	         DBSessionTable db_st = new DBSessionTable(cn);
	         SessionRow sr = db_st.getBySessionID(sid);

	         if(sr==null){
	        	 cn.close();
	        	 throw new Exception("not login.");
	         }

//	         //
//	         DBProfileTable db_pro = new DBProfileTable(cn);
//	         ProfileRow pr = db_pro.getById(sr.user_id);
//
//	         ObjectMapper om = new ObjectMapper();
//	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
//	         b.put("result", "OK");
//
////        	if("user_icon".equals(key)){
////        		b.put("user_icon", );
////        	}
//
//
//	         ArrayList<LinkedHashMap<String, Object>> bb = new ArrayList<LinkedHashMap<String,Object>>();
//
//
//	         DBFrendsTable db_fd = new DBFrendsTable(cn);
//	         DBUserTable db_acc = new DBUserTable(cn);
//
//	         List<FrendsRow> f_list = db_fd.getFrendsListByID(sr.user_id);
//
//	         for(int i=0;i<f_list.size();i++){
//	        	FrendsRow row = f_list.get(i);
//	        	AccountRow frend_acc_row  = db_acc.getById(row.frend_id);
//
//	        	LinkedHashMap<String, Object> bi = new LinkedHashMap<String, Object>();
//	        	bi.put("frend_id", row.frend_id);
//
//	        	if(frend_acc_row!=null){
//		        	bi.put("frend_name", frend_acc_row.name);
//	        	}
//
//	        	bb.add(bi);
//	         }
//
//	         b.put("frends_list", bb);
//
//	         String json = om.writeValueAsString(b);
//	         pw.write(json);

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

