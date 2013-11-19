package jp.crudefox.server.ricoh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.activation.MimeType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.crudefox.server.ricoh.util.CFUtil;
import jp.crudefox.server.ricoh.util.TextUtil;

import org.apache.http.client.utils.URIBuilder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "キーワード候補を",
		urlPatterns = { "/SuggestKeywords" },
		initParams = {
				@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "keyword", value = "")
		})
public class SuggestKeywords extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuggestKeywords() {
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

//			String sid = CFUtil.getParam(request, "sid");
			String keyword = CFUtil.getParam(request, "keyword");

			//if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
			if(TextUtil.isEmpty(keyword)) throw new Exception("non keyword.");

//	         //データベースへの接続
//			Connection cn = Const.getDefaultDBConnection();
//
//	         DBSessionTable db_st = new DBSessionTable(cn);
//	         SessionRow sr = db_st.getBySessionID(sid);
//
//	         if(sr==null){
//	        	 cn.close();
//	        	 throw new Exception("not login.");
//	         }

			//http://www.google.com/complete/search?hl=ja&output=xml&q=hello

			URIBuilder ub = new URIBuilder("http://www.google.com/complete/search");
			ub.addParameter("hl", "ja");
			ub.addParameter("output", "xml");
			ub.addParameter("q", ""+keyword);

//			String data = CFUtil.getData(ub.toString(),1000,1000);
//			if(TextUtil.isEmpty(data)) throw new Exception("err suggested.");

			// ファイルから読み込む場合
            // Document doc = new SAXBuilder().build(new File("sample.xml"));


			
            // URLにアクセスし、XMLドキュメントを取得
            URL accessURL = new URL(ub.toString());
            
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ccproxy2.kanagawa-it.ac.jp", 10080));
            URLConnection con = accessURL.openConnection(proxy);
//            URLConnection con = accessURL.openConnection();

            MimeType mt = new MimeType(con.getContentType());
            System.out.println(mt.getParameter("charset"));
            BufferedReader r = new BufferedReader( new InputStreamReader(con.getInputStream(), mt.getParameter("charset")) );

//            String str;
//            while((str=r.readLine())!=null){
//            	System.out.println(str);
//            }

            Document doc = new SAXBuilder().build(r);

//            // 取得したDoucmnetを標準出力に書き出す
//            BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(System.out));
//            XMLOutputter out = new XMLOutputter();
//            out.output(doc, bout);

            Element root = doc.getRootElement();
			List<Element> cslist = root.getChildren("CompleteSuggestion");

			PrintWriter pw = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);

	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         ArrayList<LinkedHashMap<String, Object>> bb = new ArrayList<LinkedHashMap<String,Object>>();

	         HashSet<String> sugmap = new HashSet<String>();

	         for(int i=0;i<cslist.size();i++){
	        	Element comp = cslist.get(i);
	        	Element sug = comp.getChild("suggestion");
	        	String sug_data = sug.getAttributeValue("data");
	        	System.out.println(sug_data);

	        	String[] sugsp = sug_data.split(" ");

	        	for(int j=0;j<sugsp.length;j++){
	        		String sstr = sugsp[j];
	        		if(sstr.equalsIgnoreCase(keyword)) continue;
	        		if(sugmap.contains(sstr)) continue;

		        	LinkedHashMap<String, Object> bi = new LinkedHashMap<String, Object>();
		        	bi.put("i", bb.size()+1);
		        	bi.put("data", sstr);

		        	bb.add(bi);

		        	sugmap.add(sstr);
	        	}
	         }

	         b.put("result", "OK");
	         b.put("data_list", bb);

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

