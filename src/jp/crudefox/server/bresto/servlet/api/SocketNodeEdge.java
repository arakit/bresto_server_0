package jp.crudefox.server.bresto.servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.crudefox.server.bresto.Const;
import jp.crudefox.server.bresto.Project;
import jp.crudefox.server.bresto.util.TextUtil;
import jp.swkoubou.bresto.graph.Edge;
import jp.swkoubou.bresto.graph.Node;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;


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


	//private static final String VSESION = "2";

	private static final long serialVersionUID = 1L;

	//private final ArrayList<MyMessageInbound> mList = new ArrayList<MyMessageInbound>();

	private final HashMap<String, Project> mProjects = new LinkedHashMap<String, Project>();


	@Override
	public StreamInbound createWebSocketInbound(String protocol, HttpServletRequest req){
		System.out.println("createWebSocketInbound");
		StreamInbound ret = null;

		HttpSession ses = req.getSession();
		String project_id = (String) ses.getAttribute(Const.SES_PROJECT_ID);
		String user_id = (String) ses.getAttribute(Const.SES_USER_ID);
		Project pro = null;
		boolean is_first = false;

		if(TextUtil.isEmpty(user_id)) return null;
		if(TextUtil.isEmpty(project_id)) return null;

		synchronized (mProjects) {
			pro = mProjects.get(project_id);
			if(pro == null){
				is_first = true;
				pro = new Project();
				mProjects.put(project_id, pro);
			}
		}
		if(pro!=null){
			if(is_first) pro.init(project_id);
			ret = pro.createWebSocketInbound(protocol, req);
		}

		return ret;
	}


	@Override
	protected String selectSubProtocol(List<String> subProtocols) {
		// TODO 自動生成されたメソッド・スタブ
		String ret = super.selectSubProtocol(subProtocols);
//		String sp = "bresto_json";
//
//		String ret = null;
//		System.out.print("selectSubProtocol... ");
//		if(subProtocols!=null){
//			for(String s : subProtocols){
//				System.out.println(" "+s+",");
//				if(sp.equalsIgnoreCase(s)) ret = sp;
//			}
//		}else{
//			ret = sp;
//		}
//		System.out.println("select = "+ret);
		return ret;
	}






//
//	/*
//	 * 接続ソケット
//	 */
//	private class MyMessageInbound extends MessageInbound{
//		WsOutbound mmOutB;
//
//		@Override
//		public void onOpen(WsOutbound out){
//			try {
//				System.out.println("Open Client.");
//				this.mmOutB = out;
//				synchronized (mList) {
//					mList.add(MyMessageInbound.this);
//				}
//
//
////				String data = makeJson(kw);
////				//System.out.println(""+data);
////				if(data!=null){
////					out.writeTextMessage(CharBuffer.wrap(data));
////				}
////				addNode(null);
//
//				sample();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void onClose(int status){
//			System.out.println("Close Client.");
//			synchronized (mList) {
//				mList.remove(MyMessageInbound.this);
//			}
//
//		}
//
//
//		public boolean writeText(String str){
//			if(mmOutB==null) return false;
//			try {
//				mmOutB.writeTextMessage(CharBuffer.wrap(str));
//				return true;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return false;
//			}
//		}
//
//		@Override
//		public void onTextMessage(CharBuffer cb) throws IOException{
//			System.out.println("Accept Message : "+ cb);
////			for(MyMessageInbound mmib: mList){
////				CharBuffer buffer = CharBuffer.wrap(cb);
////				mmib.myoutbound.writeTextMessage(buffer);
////				mmib.myoutbound.flush();
////			}
//		}
//
//		@Override
//		public void onBinaryMessage(ByteBuffer bb) throws IOException{
//
//		}
//	}








	public void addNodeAndEdge(String project_id, Node kw, Edge kr){
		//if(mMainThread==null) return ;
		Project pro = null;
		synchronized (mProjects) {
			pro = mProjects.get(project_id);
		}
		if(pro == null) return ;
		pro.addNodeAndEdge(kw, kr);
	}

//	public void addNode(KeywordsRow kw){
//		if(mMainThread==null) return ;
//		//mMainThread.addNode(kw, true);
//	}
//
//	public void addEdge(KeywordsRelationRow kr){
//		if(mMainThread==null) return ;
//		//mMainThread.addEdge(kr, true);
//	}





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

//		ArrayList<KeywordsRow> mmAddKeyword = new ArrayList<KeywordsRow>();
//		ArrayList<KeywordsRelationRow> mmAddEdge = new ArrayList<KeywordsRelationRow>();
//		ArrayList<SugTasked> mmAddSug = new ArrayList<SugTasked>();

		@Override
		public void run() {
			super.run();

			int roop_count = 0;
			while(!mmIsCanceld){

				System.out.println("roop_count = "+roop_count + " ***");
//
//				if(roop_count==0){
//
//				}else{
//					sample();
//				}
//
//				{
//					ArrayList<KeywordsRow> sks = new ArrayList<KeywordsRow>();
//					KeywordsRow skr;
//					for(int i=0;i<2;i++){
//						skr = mSuggestTaskQue.poll();
//						if(skr!=null){
//							sks.add(skr);
//						}
//						if(mSuggestTaskQue.size()==0) break;
//					}
//					if(sks.size()>0){
//						execSuggest(sks);
//					}
//				}
//
//
//				ArrayList<KeywordsRow> kws = new ArrayList<KeywordsRow>();
//				synchronized (mmAddKeyword) {
//					for(int i=0;i<mmAddKeyword.size();i++){
//						kws.add(mmAddKeyword.get(i));
//					}
//					mmAddKeyword.clear();
//				}
//				ArrayList<KeywordsRelationRow> krs = new ArrayList<KeywordsRelationRow>();
//				synchronized (mmAddEdge) {
//					for(int i=0;i<mmAddEdge.size();i++){
//						krs.add(mmAddEdge.get(i));
//					}
//					mmAddEdge.clear();
//				}
//				ArrayList<SugTasked> sugs = new ArrayList<SugTasked>();
//				synchronized (mmAddSug) {
//					for(int i=0;i<mmAddSug.size();i++){
//						sugs.add(mmAddSug.get(i));
//					}
//					mmAddSug.clear();
//				}
//
//				synchronized (mList) {
//					//MyMessageInbound[] mi_arr = mList.toArray(new MyMessageInbound[1]);
//					ArrayList<MyMessageInbound> mi_list = new ArrayList<MyMessageInbound>();
//					try{
//						for(int i=0;i<mList.size();i++) mi_list.add(mList.get(i));
//					}catch(Exception e){ e.printStackTrace(); }
//
//					for(MyMessageInbound mi : mi_list){
//						if(mi==null) continue;
//
//						//Node Edge
//						try {
//							String data = makeJson(kws, krs);
//							//System.out.println(""+data);
//							if(data!=null){
//								mi.writeText(data);
//							}
//
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//						//Suggest
//						for(SugTasked se : sugs){
//							try {
//								String data = makeSuggestJson(se.kr, se.sugs);
//								//System.out.println(""+data);
//								if(data!=null){
//									mi.writeText(data);
//								}
//
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//
//					}
//				}



				System.out.println("sleep start ***");
				try {
					sleep(1000*10);
				} catch (Exception e) {
					System.out.println("sleep interrput ***");
				}
				System.out.println("sleep end ***");

				roop_count++;

			}

			System.out.println("end main thread. ***");

		}

		public void go(){
			if( MainThread.this.isInterrupted() ){
				MainThread.this.interrupt();
			}
		}


//		public void addNode(List<KeywordsRow> kw, boolean interput){
//			if(kw!=null){
//				synchronized (mmAddKeyword) {
//					for(KeywordsRow e : kw){
//						mmAddKeyword.add(e);
//						mSuggestTaskQue.add(e);
//					}
//				}
//
//			}
//			if(interput) go();
//		}
//		public void addEdge(List<KeywordsRelationRow> kr, boolean interput){
//			if(kr!=null){
//				synchronized (mmAddEdge) {
//					for(KeywordsRelationRow e : kr){
//						mmAddEdge.add(e);
//					}
//				}
//			}
//			if(interput) go();
//		}
//		public void addSug(List<SugTasked> sug, boolean interput){
//			if(sug!=null){
//				synchronized (mmAddSug) {
//					for(SugTasked e : sug){
//						mmAddSug.add(e);
//					}
//				}
//			}
//			if(interput) go();
//		}


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
		//startMainThread();
	}


	@Override
	public void destroy() {
		System.out.println("destroy()");
		super.destroy();

		synchronized (mProjects) {
			for(Entry<String, Project> e: mProjects.entrySet()){
				e.getValue().destroy();
			}
		}

		//stopMainThread();
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