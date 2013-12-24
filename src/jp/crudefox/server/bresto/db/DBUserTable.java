package jp.crudefox.server.bresto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUserTable {


	private final static String TABLE_NAME = "user";

	private final static String COL_ID = "user_id";
	private final static String COL_PASS = "user_pass";


	static public class UserRow{
		public String id;
		public String pass;
	}


	Connection mConnection;

	public DBUserTable (Connection cn){
		mConnection = cn;
	}

	public UserRow getById(String id){

		UserRow result = null;

		try {

	        String qry1 = String.format("select * from %s where %s = ?",
	        		TABLE_NAME, COL_ID
	        );

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, id);

	        //問い合わせ
	        ResultSet rs = st.executeQuery();

	        //データの取得
	        //ResultSetMetaData rm = rs.getMetaData();
	        //int cnum = rm.getColumnCount();
	        if(rs.first()){
	        	UserRow row;
	        	row = new UserRow();
	        	row.id = rs.getString(COL_ID);
	        	row.pass = rs.getString(COL_PASS);
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

	public boolean insert(UserRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("insert into %s (%s, %s) values (?, ?)",
	        		TABLE_NAME, COL_ID, COL_PASS
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, row.id);
	        st.setString(2, row.pass);


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