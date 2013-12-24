package jp.crudefox.server.bresto.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jp.crudefox.server.bresto.util.Secure;
import jp.crudefox.server.bresto.util.TextUtil;


public class DBSessionTable {


	private final static String TABLE_NAME = "session";

	private final static String COL_SID = "sid";
	private final static String COL_USER_ID = "user_id";
	private final static String COL_MODIFIED_TIME = "modified_time";


	static public class SessionRow{
		public String sid;
		public String user_id;
		public Date modified_time;
	}


	Connection mConnection;

	public DBSessionTable (Connection cn){
		mConnection = cn;
	}

	public SessionRow getBySessionID(String sid){

		SessionRow result = null;

		try {

	        //問い合わせの準備
	        //DatabaseMetaData dm = mConnection.getMetaData();
	        //ResultSet tb = dm.getTables(null, null, TABLE_NAME, null);

	       // Statement st = mConnection.createStatement();

	        String qry1 = String.format("select * from %s where %s = ?",
	        		TABLE_NAME, COL_SID
	        );

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, sid);

	        //問い合わせ
	        ResultSet rs = st.executeQuery();

	        //データの取得
	        //ResultSetMetaData rm = rs.getMetaData();
	        //int cnum = rm.getColumnCount();
	        if(rs.first()){
	        	SessionRow row;
	        	row = new SessionRow();
	        	row.sid = rs.getString(COL_SID);
	        	row.user_id = rs.getString(COL_USER_ID);
	        	row.modified_time = rs.getDate(COL_MODIFIED_TIME);
	        	result = row;
	        }

	        //接続のクローズ
	        rs.close();
	        st.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public SessionRow insertNew(String user_id){

		if(TextUtil.isEmpty(user_id)) return null;

		SessionRow sr = new SessionRow();
		sr.modified_time = sr.modified_time;
		sr.user_id = user_id;

		boolean succes = false;
		for(int i=0;i<100;i++){
			sr.sid = Secure.createRandomAlphabet(32);
			if( insert(sr) ){
				succes = true;
				break;
			}
		}

		if(succes) return sr;
		else return null;

	}

	public boolean insert(SessionRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("insert into %s (%s, %s) values (?, ?)",
	        		TABLE_NAME, COL_SID, COL_USER_ID
	        		);


	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, row.sid);
	        st.setString(2, row.user_id);


//	        Calendar cal = Calendar.getInstance();
//	        cal.setTime(row.create);
//	        st.setString(3, CFUtil.convertCalendar2MySQLDATETIMEString(cal));
//	        cal.setTime(row.modified);
//	        st.setString(4, CFUtil.convertCalendar2MySQLDATETIMEString(cal));


	        int affected = st.executeUpdate();

	        result = affected>=1 ;

	        //接続のクローズ
	        st.close();

	        return result;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public boolean updateModified(String sid){

		boolean result = false;

		try {

	        String qry1 = String.format("update %s set %s = ? where %s = ?",
	        		TABLE_NAME, COL_MODIFIED_TIME, COL_SID
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setDate(1, null);
	        st.setString(2, sid);


	        int affected = st.executeUpdate();

	        result = affected>=1 ;

	        //接続のクローズ
	        st.close();

	        return result;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}


}






/*


		try {

	        //問い合わせの準備
	        DatabaseMetaData dm = mConnection.getMetaData();
	        ResultSet tb = dm.getTables(null, null, TABLE_NAME, null);

	        Statement st = mConnection.createStatement();


	        String qry1 = "CREATE TABLE 車表(番号 int, 名前 varchar(50))";
	        String[] qry2 = {"INSERT INTO 車表 VALUES (2, '乗用車')",
	                         "INSERT INTO 車表 VALUES (3, 'オープンカー')",
	                         "INSERT INTO 車表 VALUES (4, 'トラック')"};
	        String qry3 = "SELECT * FROM 車表";

	        if(!tb.next()){
	           st.executeUpdate(qry1);
	           for(int i=0; i<qry2.length; i++){
	              st.executeUpdate(qry2[i]);
	           }
	        }

	        //問い合わせ
	        ResultSet rs = st.executeQuery(qry3);

	        //データの取得
	        ResultSetMetaData rm = rs.getMetaData();
	        int cnum = rm.getColumnCount();
	        while(rs.next()){
	           for(int i=1; i<=cnum; i++){
	               System.out.print(rm.getColumnName(i) +  ":"+ rs.getObject(i) + "  ");
	           }
	           System.out.println("");
	        }

	        //接続のクローズ
	        rs.close();
	        st.close();
	        //cn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}


*/