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
import jp.crudefox.server.bresto.db.DBKeywordsRelationTable;
import jp.crudefox.server.bresto.db.DBKeywordsRelationTable.KeywordsRelationRow;
import jp.crudefox.server.bresto.db.DBProjectTable;
import jp.crudefox.server.bresto.db.DBProjectTable.ProjectRow;
import jp.crudefox.server.bresto.db.DBkeywordsTable;
import jp.crudefox.server.bresto.db.DBkeywordsTable.KeywordsRow;
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "キーワード候補を",
		urlPatterns = { "/ConnectKeyword" },
		initParams = {
				@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "keyword", value = ""),
				@WebInitParam(name = "from_kid", value = ""),
				@WebInitParam(name = "to_keyword", value = "")
		})
public class ConnectKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectKeyword() {
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
			String _from_kid = CFUtil.getParam(request, "from_kid");
			String to_keyword = CFUtil.getParam(request, "to_keyword");
			String project_id = CFUtil.getParam(request, "project_id");

			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
			if(TextUtil.isEmpty(_from_kid)) throw new Exception("non kid.");
			if(TextUtil.isEmpty(to_keyword)) throw new Exception("to keyword.");
			if(TextUtil.isEmpty(project_id)) throw new Exception("non project_id.");


			int from_kid = Integer.parseInt(_from_kid);

	         //データベースへの接続
			Connection cn = Const.getDefaultDBConnection();

//	         DBSessionTable db_st = new DBSessionTable(cn);
//	         SessionRow sr = db_st.getBySessionID(sid);
//
//	         if(sr==null){
//	        	 cn.close();
//	        	 throw new Exception("not login.");
//	         }

	         DBProjectTable db_pro = new DBProjectTable(cn);
	         ProjectRow pr = db_pro.getById(project_id);
	         if(pr==null) throw new Exception("not exist project.");

	         DBkeywordsTable db_k = new DBkeywordsTable(cn);

	         KeywordsRow k1 = db_k.getById(project_id, from_kid);
	         if(k1==null) throw new Exception("not exist keyword.");

	         KeywordsRow k2 = new KeywordsRow();
	         k2.project_id = project_id;
	         k2.keyword = to_keyword;
	         k2 = db_k.insertByAutoIncrement(k2);

	         DBKeywordsRelationTable db_kr = new DBKeywordsRelationTable(cn);

	         KeywordsRelationRow krr = new KeywordsRelationRow();
	         krr.kid1 = k1.kid;
	         krr.kid2 = k2.kid;
	         krr.project_id = k2.project_id;
	         krr.modified_time = null;
	         boolean suc = db_kr.insert(krr);

	         if(!suc) throw new Exception("can not insert.");


	         PrintWriter pw = response.getWriter();
	         response.setStatus(HttpServletResponse.SC_OK);

	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();

	         b.put("result", "OK");

	         b.put("kid1", krr.kid1);
	         b.put("keyword1", k1.keyword );

	         b.put("kid2", krr.kid2);
	         b.put("keyword2", k2.keyword );

	         b.put("project_id", krr.project_id);

	         String json = om.writeValueAsString(b);
	         pw.write(json);

	         //接続のクローズ
	         //cn.close();
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

