package jp.crudefox.server.bresto;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;


//接続リクエストとWebSocket通信の仲立ちを行うサーブレット

@WebServlet(
		description = "ソケット通信",
		urlPatterns = { "/socket_node_edge_jetty" },
		initParams = {
				@WebInitParam(name = "sid", value = "")
		})
public class SocketNodeEdge_Jetty extends WebSocketServlet{


	// クライアントとの接続を管理するリスト
	private static ConcurrentLinkedQueue<MySocket> sSocketQueue =
			new ConcurrentLinkedQueue<MySocket>();

	// 接続リクエストを受けたとき、WebSocket通信を処理するWebSocketインターフェイス実装クラスを返す
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		System.out.println("doWebSocketConnect");
		return new MySocket();
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doGet(req, resp);
		doRest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doPost(req, resp);
		doRest(req, resp);
	}

	private void doRest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doRest");
	}





	// WebSocket通信を処理するWebSocketインターフェイス実装クラス
	class MySocket implements WebSocket.OnTextMessage{

		// このインスタンスが処理する、クライアントとの接続
		private Connection mConnection;

		// WebSocket通信開始時のコールバック関数
		@Override
		public void onOpen(Connection connection){
			this.mConnection = connection;
			// 開始された接続をリストに追加
			sSocketQueue.add(this);
		}

		// WebSocket通信切断時のコールバック関数
		@Override
		public void onClose(int closeCode , String message){
			// 切断された接続をリストから削除
			sSocketQueue.remove(this);
		}

		// テキストメッセージ受信時のコールバック関数
		@Override
		public void onMessage(String data){
			// リストの中のすべての接続に、受信したデータを再配信
			for(MySocket socket : sSocketQueue){
				try{
					socket.mConnection.sendMessage(data);
				} catch (IOException e) {
					// エラーになった接続をリストから削除
					sSocketQueue.remove(socket);
					e.printStackTrace();
				}
			}
		}
	}



}