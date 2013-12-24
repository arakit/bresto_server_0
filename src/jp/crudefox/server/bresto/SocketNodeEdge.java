package jp.crudefox.server.bresto;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.crudefox.server.bresto.Suggest.Sug;
import jp.crudefox.server.bresto.db.DBKeywordsRelationTable.KeywordsRelationRow;
import jp.crudefox.server.bresto.db.DBkeywordsTable.KeywordsRow;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


//接続リクエストとWebSocket通信の仲立ちを行うサーブレット

@WebServlet(
		description = "ソケット通信",
		urlPatterns = { "/api/socket_node_edge" },
		initParams = {
				@WebInitParam(name = "sid", value = "")
		})
public class SocketNodeEdge extends WebSocketServlet{

	private static SocketNodeEdge sInstance;

	public static SocketNodeEdge getInstance(){
		return sInstance;
	}


	private static final String VSESION = "2";

	private static final long serialVersionUID = 1L;

	private final ArrayList<MyMessageInbound> mList = new ArrayList<MyMessageInbound>();







	@Override
	public StreamInbound createWebSocketInbound(String protocol, HttpServletRequest req){
		System.out.println("createWebSocketInbound");
		return new MyMessageInbound();
	}


	@Override
	protected String selectSubProtocol(List<String> subProtocols) {
		// TODO 自動生成されたメソッド・スタブ
		//String ret = super.selectSubProtocol(subProtocols);
		String sp = "bresto_json";

		String ret = null;
		System.out.print("selectSubProtocol... ");
		if(subProtocols!=null){
			for(String s : subProtocols){
				System.out.println(" "+s+",");
				if(sp.equalsIgnoreCase(s)) ret = sp;
			}
		}else{
			ret = sp;
		}
		System.out.println("select = "+ret);
		return ret;
	}







	/*
	 * 接続ソケット
	 */
	private class MyMessageInbound extends MessageInbound{
		WsOutbound mmOutB;

		@Override
		public void onOpen(WsOutbound out){
			try {
				System.out.println("Open Client.");
				this.mmOutB = out;
				synchronized (mList) {
					mList.add(MyMessageInbound.this);
				}


//				String data = makeJson(kw);
//				//System.out.println(""+data);
//				if(data!=null){
//					out.writeTextMessage(CharBuffer.wrap(data));
//				}
//				addNode(null);

				sample();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onClose(int status){
			System.out.println("Close Client.");
			synchronized (mList) {
				mList.remove(MyMessageInbound.this);
			}

		}


		public boolean writeText(String str){
			if(mmOutB==null) return false;
			try {
				mmOutB.writeTextMessage(CharBuffer.wrap(str));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public void onTextMessage(CharBuffer cb) throws IOException{
			System.out.println("Accept Message : "+ cb);
//			for(MyMessageInbound mmib: mList){
//				CharBuffer buffer = CharBuffer.wrap(cb);
//				mmib.myoutbound.writeTextMessage(buffer);
//				mmib.myoutbound.flush();
//			}
		}

		@Override
		public void onBinaryMessage(ByteBuffer bb) throws IOException{

		}
	}











	/*
	 * JSONファイル作成
	 */

	private String makeJson(ArrayList<KeywordsRow> kws, ArrayList<KeywordsRelationRow> krs){

		try{
			//データベースへの接続
			//Connection cn = Const.getDefaultDBConnection();

			//	         DBSessionTable db_st = new DBSessionTable(cn);
			//	         SessionRow sr = db_st.getBySessionID(sid);
			//
			//	         if(sr==null){
			//	        	 cn.close();
			//	        	 throw new Exception("not login.");
			//	         }

			//			if(project_id == null) project_id = "LPWKfLCkJborUPAggfNIcWtfPPERzAlR";


			//DBProjectTable db_pro = new DBProjectTable(cn);
			//ProjectRow pr = db_pro.getById(project_id);
			//if(pr==null) throw new Exception("not exist project.");


			//DBkeywordsTable db_k = new DBkeywordsTable(cn);
			//List<KeywordsRow> klist = db_k.getByProjectId(project_id);
			List<KeywordsRow> klist = new ArrayList<KeywordsRow>();
			List<KeywordsRelationRow> kr_list = new ArrayList<KeywordsRelationRow>();
//			KeywordsRow r;

			for(int i=0;i<kws.size();i++){
//				r = new KeywordsRow();
//				r.kid = ;
//				r.keyword = "Windows";
//				r.project_id = "aaaa";
//				klist.add(r);

				klist.add(kws.get(i));
			}

			for(int i=0;i<krs.size();i++){
				kr_list.add(krs.get(i));
			}

			//PrintWriter pw = response.getWriter();
			//response.setStatus(HttpServletResponse.SC_OK);

			ObjectMapper om = new ObjectMapper();
			om.configure(SerializationFeature.INDENT_OUTPUT  , true);

			LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();
			LinkedHashMap<String,LinkedHashMap<String, Object>> b_node = new LinkedHashMap<String, LinkedHashMap<String,Object>>();
			ArrayList<LinkedHashMap<String, Object>> b_edge = new ArrayList<LinkedHashMap<String,Object>>();

			for(int i=0;i<klist.size();i++){
				KeywordsRow kr = klist.get(i);

				LinkedHashMap<String, Object> bi = new LinkedHashMap<String, Object>();
				bi.put("kid", Integer.valueOf(kr.kid) );
				bi.put("keyword", kr.keyword);
				bi.put("x", Integer.valueOf(kr.x) );
				bi.put("y", Integer.valueOf(kr.y) );
				bi.put("w", Integer.valueOf(kr.w) );
				bi.put("h", Integer.valueOf(kr.h) );

				b_node.put(""+kr.kid, bi);
			}

			for(int i=0;i<kr_list.size();i++){
				KeywordsRelationRow kr = kr_list.get(i);

				LinkedHashMap<String, Object> bi = new LinkedHashMap<String, Object>();
				bi.put("from_kid",Integer.valueOf(kr.kid1) );
				bi.put("to_kid", Integer.valueOf(kr.kid2) );

				b_edge.add(bi);
			}

			Date now = new Date();

			//b.put("result", "OK");
			//b.put("project_id", project_id);
			b_data.put("node", b_node);
			b_data.put("edge", b_edge);

			b.put("type", "node_edge");
			b.put("version", VSESION);
			b.put("time", ""+now);
			b.put("data", b_data);

			String json = om.writeValueAsString(b);
			//pw.write(json);

			//接続のクローズ
			//cn.close();

			return json;
		}
		catch(Exception e){
			e.printStackTrace();

			//response.setContentType("application/json; charset=utf-8");

			///response.setStatus( HttpServletResponse.SC_FORBIDDEN );

			//response.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );

			return null;
		}

	}


	/*
	 * JSONファイル作成
	 */

	private String makeSuggestJson(KeywordsRow kr, List<Sug> sugs){

		try{

			ObjectMapper om = new ObjectMapper();
			om.configure(SerializationFeature.INDENT_OUTPUT  , true);

			LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();
			//LinkedHashMap<String,LinkedHashMap<String, Object>> b_node = new LinkedHashMap<String, LinkedHashMap<String,Object>>();
			ArrayList<LinkedHashMap<String, Object>> b_suggest = new ArrayList<LinkedHashMap<String,Object>>();
			//LinkedHashMap<String,LinkedHashMap<String, Object>> b_suggest = new LinkedHashMap<String,LinkedHashMap<String,Object>>();

			for(int i=0;i<sugs.size();i++){
				Sug sug = sugs.get(i);

				LinkedHashMap<String, Object> bi = new LinkedHashMap<String, Object>();
				bi.put("data", sug.data);
				bi.put("src", sug.src);
				bi.put("type", sug.type);

				b_suggest.add(bi);
			}

			Date now = new Date();

			b_data.put("kid", Integer.valueOf(kr.kid) );
			b_data.put("keyword", ""+kr.keyword);
			b_data.put("suggest", b_suggest);

			b.put("type", "suggest");
			b.put("version", VSESION);
			b.put("time", ""+now);
			b.put("data", b_data);

			String json = om.writeValueAsString(b);
			//pw.write(json);

			//接続のクローズ
			//cn.close();

			return json;
		}
		catch(Exception e){
			e.printStackTrace();

			return null;
		}

	}




	public void addNodeAndEdge(KeywordsRow kw, KeywordsRelationRow kr){
		if(mMainThread==null) return ;
		//mMainThread.addNode(kw, false);
		//mMainThread.addEdge(kr, true);
	}

	public void addNode(KeywordsRow kw){
		if(mMainThread==null) return ;
		//mMainThread.addNode(kw, true);
	}

	public void addEdge(KeywordsRelationRow kr){
		if(mMainThread==null) return ;
		//mMainThread.addEdge(kr, true);
	}



	private void sample(){

		String[] arr = new String[]{
				"Windows",
				"Linux",
				"りんご",
				"ごりら",
				"ラッパ",
				"ぱんつ",
				"積み木",
				"きつね",
				"たぬきうどん"
		};

		ArrayList<KeywordsRow> node = new ArrayList<KeywordsRow>();
		ArrayList<KeywordsRelationRow> edge = new ArrayList<KeywordsRelationRow>();

		for(int i=0;i<arr.length;i++){
			KeywordsRow kr = new KeywordsRow();
			kr.kid = 1000 + i;//(int)( Math.random() * Integer.MAX_VALUE );
			kr. keyword = arr[i];
			kr.w = 100;
			kr.h = 100;
			kr.x = (int)( Math.random() * 400);
			kr.y = (int)( Math.random() * 400);
			node.add(kr);

			if(i!=0){
				KeywordsRelationRow krr = new KeywordsRelationRow();
				krr.kid1 = node.get(i-1).kid;
				krr.kid2 = kr.kid;
				krr.modified_time = new Date();
				krr.project_id = "p1";
				edge.add(krr);
			}
		}


		if(mMainThread!=null){
			mMainThread.addNode(node, false);
			mMainThread.addEdge(edge, false);
			mMainThread.go();
		}
	}

	private static class SugTasked{
		KeywordsRow kr;
		List<Sug> sugs;
	}


	private ConcurrentLinkedQueue<KeywordsRow> mSuggestTaskQue = new ConcurrentLinkedQueue<KeywordsRow>();

	//サジェストタスク消費
	private void execSuggest(ArrayList<KeywordsRow> krs){

		for(int i=0;i<krs.size();i++){
			KeywordsRow kr = krs.get(i);
			List<Sug> sugres;
			 try {
				sugres = Suggest.suggestByGoogle(kr.keyword);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			if( sugres == null) continue;

			SugTasked st = new SugTasked();
			st.kr = kr;
			st.sugs = sugres;

			if(mMainThread!=null){
				ArrayList<SugTasked> sts = new ArrayList<SocketNodeEdge.SugTasked>();
				sts.add(st);
				mMainThread.addSug(sts, false);
			}
		}

	}



	private MainThread mMainThread;

	private synchronized void startMainThread(){
		if(mMainThread!=null) return ;

		System.out.println("startMainThread");

		mMainThread = new MainThread();
		mMainThread.start();

	}
	private synchronized void stopMainThread(){
		if(mMainThread==null) return ;

		System.out.println("stopMainThread start");

		mMainThread.cancel();
		mMainThread = null;

		System.out.println("stopMainThread stop");
	}



	private class MainThread extends Thread {

		boolean mmIsCanceld = false;

		ArrayList<KeywordsRow> mmAddKeyword = new ArrayList<KeywordsRow>();
		ArrayList<KeywordsRelationRow> mmAddEdge = new ArrayList<KeywordsRelationRow>();
		ArrayList<SugTasked> mmAddSug = new ArrayList<SugTasked>();

		@Override
		public void run() {
			super.run();

			int roop_count = 0;
			while(!mmIsCanceld){

				System.out.println("roop_count = "+roop_count);

				if(roop_count==0){

				}else{
					sample();
				}

				{
					ArrayList<KeywordsRow> sks = new ArrayList<KeywordsRow>();
					KeywordsRow skr;
					for(int i=0;i<2;i++){
						skr = mSuggestTaskQue.poll();
						if(skr!=null){
							sks.add(skr);
						}
						if(mSuggestTaskQue.size()==0) break;
					}
					if(sks.size()>0){
						execSuggest(sks);
					}
				}


				ArrayList<KeywordsRow> kws = new ArrayList<KeywordsRow>();
				synchronized (mmAddKeyword) {
					for(int i=0;i<mmAddKeyword.size();i++){
						kws.add(mmAddKeyword.get(i));
					}
					mmAddKeyword.clear();
				}
				ArrayList<KeywordsRelationRow> krs = new ArrayList<KeywordsRelationRow>();
				synchronized (mmAddEdge) {
					for(int i=0;i<mmAddEdge.size();i++){
						krs.add(mmAddEdge.get(i));
					}
					mmAddEdge.clear();
				}
				ArrayList<SugTasked> sugs = new ArrayList<SugTasked>();
				synchronized (mmAddSug) {
					for(int i=0;i<mmAddSug.size();i++){
						sugs.add(mmAddSug.get(i));
					}
					mmAddSug.clear();
				}

				synchronized (mList) {
					//MyMessageInbound[] mi_arr = mList.toArray(new MyMessageInbound[1]);
					ArrayList<MyMessageInbound> mi_list = new ArrayList<MyMessageInbound>();
					try{
						for(int i=0;i<mList.size();i++) mi_list.add(mList.get(i));
					}catch(Exception e){ e.printStackTrace(); }

					for(MyMessageInbound mi : mi_list){
						if(mi==null) continue;

						//Node Edge
						try {
							String data = makeJson(kws, krs);
							//System.out.println(""+data);
							if(data!=null){
								mi.writeText(data);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						//Suggest
						for(SugTasked se : sugs){
							try {
								String data = makeSuggestJson(se.kr, se.sugs);
								//System.out.println(""+data);
								if(data!=null){
									mi.writeText(data);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
				}



				System.out.println("sleep start");
				try {
					sleep(1000*3);
				} catch (Exception e) {
					System.out.println("sleep interrput");
				}
				System.out.println("sleep end");

				roop_count++;

			}

			System.out.println("end main thread.");

		}

		public void go(){
			if( MainThread.this.isInterrupted() ){
				MainThread.this.interrupt();
			}
		}


		public void addNode(List<KeywordsRow> kw, boolean interput){
			if(kw!=null){
				synchronized (mmAddKeyword) {
					for(KeywordsRow e : kw){
						mmAddKeyword.add(e);
						mSuggestTaskQue.add(e);
					}
				}

			}
			if(interput) go();
		}
		public void addEdge(List<KeywordsRelationRow> kr, boolean interput){
			if(kr!=null){
				synchronized (mmAddEdge) {
					for(KeywordsRelationRow e : kr){
						mmAddEdge.add(e);
					}
				}
			}
			if(interput) go();
		}
		public void addSug(List<SugTasked> sug, boolean interput){
			if(sug!=null){
				synchronized (mmAddSug) {
					for(SugTasked e : sug){
						mmAddSug.add(e);
					}
				}
			}
			if(interput) go();
		}


		public void cancel(){
			if(mmIsCanceld) return ;
			System.out.println("cancel start");
			mmIsCanceld = true;
			MainThread.this.interrupt();
			try {
				MainThread.this.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("cancel end");
		}


	}
















	/*
	 * ここから下はあんまり関係ない。
	 *
	 *
	 */



	@Override
	public void init() throws ServletException {
		System.out.println("init()");
		super.init();
		onInit();
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init(config)");
		super.init(config);
		onInit();
	}
	private void onInit(){
		sInstance = this;
		startMainThread();
	}


	@Override
	public void destroy() {
		System.out.println("destroy()");
		super.destroy();
		stopMainThread();
		sInstance = null;
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet.");
		super.doGet(req, resp);
		//super.doGet(req, resp);
		//			doRest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost.");
		super.doPost(req, resp);
		//super.doPost(req, resp);
		//			System.out.println("doPost.");
		//			doRest(req, resp);
	}

	private void doRest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doRest");

		//CFUtil.initMySQLDriver();

		//response.setContentType("application/json; charset=utf-8");
		response.setContentType("text/plain; charset=utf-8");

		try{

			//				String sid = CFUtil.getParam(request, "sid");
			//
			//				if(TextUtil.isEmpty(sid)) throw new Exception("non sid");


			//データベースへの接続
			//				Connection cn = Const.getDefaultDBConnection();

			//		         DBSessionTable db_st = new DBSessionTable(cn);
			//		         SessionRow sr = db_st.getBySessionID(sid);
			//
			//		         if(sr==null){
			//		        	 cn.close();
			//		        	 throw new Exception("not login.");
			//		         }

			//				if(project_id == null) project_id = "LPWKfLCkJborUPAggfNIcWtfPPERzAlR";


			PrintWriter pw = response.getWriter();

			pw.write("これはrestではなく、webcocketですっ");


		}
		catch(Exception e){
			e.printStackTrace();

			//response.setContentType("application/json; charset=utf-8");

			///response.setStatus( HttpServletResponse.SC_FORBIDDEN );

			response.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );

		}
	}




}