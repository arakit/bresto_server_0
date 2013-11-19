package jp.crudefox.server.ricoh;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.crudefox.server.ricoh.db.DBKeywordsRelationTable;
import jp.crudefox.server.ricoh.db.DBKeywordsRelationTable.KeywordsRelationRow;
import jp.crudefox.server.ricoh.db.DBProjectTable;
import jp.crudefox.server.ricoh.db.DBProjectTable.ProjectRow;
import jp.crudefox.server.ricoh.util.CFUtil;
import jp.crudefox.server.ricoh.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "キーワード",
		urlPatterns = { "/KeywordsRelations" },
		initParams = {
				@WebInitParam(name = "sid", value = "")
		})
public class KeywordsRelations extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeywordsRelations() {
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


		try{

			String sid = CFUtil.getParam(request, "sid");
//			String keyword = CFUtil.getParam(request, "keyword");
			String project_id = CFUtil.getParam(request, "project_id");

			String _start_time = CFUtil.getParam(request, "start_time");

			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
//			if(TextUtil.isEmpty(keyword)) throw new Exception("non keyword.");
			if(TextUtil.isEmpty(project_id)) throw new Exception("non project_id.");


			Date start_time = !TextUtil.isEmpty(_start_time) ? CFUtil.parseDateTme(_start_time) : null;

	         //データベースへの接続
			Connection cn = Const.getDefaultDBConnection();

//	         DBSessionTable db_st = new DBSessionTable(cn);
//	         SessionRow sr = db_st.getBySessionID(sid);
//
//	         if(sr==null){
//	        	 cn.close();
//	        	 throw new Exception("not login.");
//	         }

			//String project_id = "LPWKfLCkJborUPAggfNIcWtfPPERzAlR";
			//if(project_id == null) project_id = "LPWKfLCkJborUPAggfNIcWtfPPERzAlR";

			DBProjectTable db_pro = new DBProjectTable(cn);
			ProjectRow pr = db_pro.getById(project_id);
			if(pr==null) throw new Exception("not exist project.");


			DBKeywordsRelationTable db_kr = new DBKeywordsRelationTable(cn);
			List<KeywordsRelationRow> krlist = db_kr.getByProjectId(project_id);

			if(start_time!=null){
				for(int i=0;i<krlist.size();i++){
					KeywordsRelationRow krr = krlist.get(i);
					if( krr.modified_time.getTime() >= start_time.getTime() ){

					}else{
						krlist.remove(i);
						i--;
					}
				}
			}

//			DBkeywordsTable db_k = new DBkeywordsTable(cn);
//			List<KeywordsRow> klist = db_k.getByProjectId(project_id);


			PrintWriter pw = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);

	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         ArrayList<LinkedHashMap<String, Object>> bb = new ArrayList<LinkedHashMap<String,Object>>();

	         for(int i=0;i<krlist.size();i++){
	        	 KeywordsRelationRow krr = krlist.get(i);

	        	LinkedHashMap<String, Object> bi = new LinkedHashMap<String, Object>();
	        	bi.put("kid1", krr.kid1);
	        	bi.put("kid2", krr.kid2);
	        	bi.put("modified_time", CFUtil.toDateTimeString(krr.modified_time));

	        	bb.add(bi);
	         }

	         b.put("result", "OK");
	         b.put("project_id", project_id);
	         b.put("data_list", bb);

	         String json = om.writeValueAsString(b);
	         pw.write(json);

	         //接続のクローズ
	         cn.close();
	     }
	     catch(Exception e){
	        e.printStackTrace();

			//response.setContentType("application/json; charset=utf-8");

	        ///response.setStatus( HttpServletResponse.SC_FORBIDDEN );

	        response.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );

	     }

	}

//	private void outputImage(HttpServletResponse response, Blob image) throws IOException, SQLException{
//
//		response.setContentType("image/*");
//
//		if(image==null){
//			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//			return ;
//		}
//
//		ServletOutputStream os = response.getOutputStream();
//		InputStream is = image.getBinaryStream();
//
//		int len=0;
//		byte[] buf = new byte[1024];
//		while((len=is.read(buf))!=-1){
//			os.write(buf,0,len);
//		}
//
//	}
//
//	private void outputString(HttpServletResponse response, String str) throws IOException{
//
//		response.setContentType("text/plain; charset=utf-8");
//
//		if(str==null){
//			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//			return ;
//		}
//
//        PrintWriter pw = response.getWriter();
//        pw.write(str);
//
//	}






}

