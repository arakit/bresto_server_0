package jp.crudefox.server.bresto.servlet.html;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.crudefox.server.bresto.Const;
import jp.crudefox.server.bresto.db.DBProjectTable;
import jp.crudefox.server.bresto.db.DBProjectTable.ProjectRow;
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

/**
 * Servlet implementation class API
 */
@WebServlet(name = "bresto.html", urlPatterns = { "/bresto.html" })
public class BreSto extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreSto() {
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


	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String disp = "/jsp/bresto.jsp";
		RequestDispatcher dispatch = request.getRequestDispatcher(disp);

		request.setAttribute(Const.REQ_SELECT_PAGE, "bresto");


		Connection cn = null;
		CFUtil.initMySQLDriver();

		final ArrayList<ProjectRow> project_list = new ArrayList<DBProjectTable.ProjectRow>();
		
		HttpSession ses = request.getSession();
		String user_id = (String) ses.getAttribute(Const.SES_USER_ID);

		try{
			String project_id;
			
			if(TextUtil.isEmpty(user_id))  throw new Exception("not login.");

	         //データベースへの接続
	         cn = Const.getDefaultDBConnection();
	         cn.setAutoCommit(false);

	         DBProjectTable db_pro = new DBProjectTable(cn);
	         List<ProjectRow> pro_list = db_pro.listAll();
	         if(pro_list==null) throw new Exception("not exist project.");

	         for(int i=0;i<pro_list.size();i++){
	        	 project_list.add(pro_list.get(i));
	         }

	         //接続のクローズ
	         cn.commit(); cn.close(); cn = null;

	         //結果
//	         ObjectMapper om = new ObjectMapper();
//	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);
//
//	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
//	         LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();
//
//	         b_data.put("kid", kr.kid);
//	         b_data.put("keyword", kr.keyword);
//	         b_data.put("parent_kid", krr!=null ? krr.kid1 : null);
//
//
//	         b.put("result", "OK");
//	         b.put("data", b_data);
//
//	         String json = om.writeValueAsString(b);


	     }
	     catch(Exception e){
	        e.printStackTrace();

//	         ObjectMapper om = new ObjectMapper();
//	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
//	         b.put("result", "FAILED");
//	         b.put("info", e.getMessage() );
//
//	         String json = om.writeValueAsString(b);
//
//	         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//	         pw.write(json);
	     }


		//データベースと切断
		if(cn!=null){ try { if( !cn.isClosed() ) cn.close(); cn = null;} catch (SQLException e) { e.printStackTrace();}}


		if(!TextUtil.isEmpty(user_id)){
			request.setAttribute(Const.REQ_PROJECT_LIST, project_list);
			dispatch.forward(request, response);			
		}else{
			response.sendRedirect("./signin.html");
		}


	}

}
