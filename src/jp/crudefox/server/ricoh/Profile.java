package jp.crudefox.server.ricoh;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.crudefox.server.ricoh.db.DBSessionTable;
import jp.crudefox.server.ricoh.db.DBSessionTable.SessionRow;
import jp.crudefox.server.ricoh.util.CFUtil;
import jp.crudefox.server.ricoh.util.TextUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "プロフィール",
		urlPatterns = { "/Profile" },
		initParams = {
				@WebInitParam(name = "sid", value = "")
		})
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
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


		try{

			String sid = CFUtil.getParam(request, "sid");
			String key = CFUtil.getParam(request, "key");

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
//
//	         if("user_icon".equals(key)){
//	        	 outputImage(response, pr.icon);
//	         }
//	         else if("user_background".equals(key)){
//	        	 outputImage(response, pr.background);
//	         }
//	         else if("user_introduction".equals(key)){
//	        	 outputString(response, pr.introduction);
//	         }
//	         else if("user_birthday".equals(key)){
//	        	 outputString(response, ""+pr.birthday);
//	         }
//	         else if("user_gender".equals(key)){
//	        	 outputString(response, pr.gender);
//	         }



	         //接続のクローズ
	         cn.close();
	     }
	     catch(Exception e){
	        e.printStackTrace();

			//response.setContentType("application/json; charset=utf-8");

	        ///response.setStatus( HttpServletResponse.SC_FORBIDDEN );

	        response.sendError( HttpServletResponse.SC_FORBIDDEN );

	     }

	}

	private void outputImage(HttpServletResponse response, Blob image) throws IOException, SQLException{

		response.setContentType("image/*");

		if(image==null){
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return ;
		}

		ServletOutputStream os = response.getOutputStream();
		InputStream is = image.getBinaryStream();

		int len=0;
		byte[] buf = new byte[1024];
		while((len=is.read(buf))!=-1){
			os.write(buf,0,len);
		}

	}

	private void outputString(HttpServletResponse response, String str) throws IOException{

		response.setContentType("text/plain; charset=utf-8");

		if(str==null){
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return ;
		}

        PrintWriter pw = response.getWriter();
        pw.write(str);

	}






}

