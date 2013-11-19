package jp.crudefox.server.ricoh.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jp.crudefox.server.ricoh.util.Secure;
import jp.crudefox.server.ricoh.util.TextUtil;

public class DBChildTable {


	private final static String TABLE_NAME = "child";

	private final static String COL_ID = "child_id";
	private final static String COL_PROJECT_ID = "project_id";


	static public class ChildRow{
		public String id;
		public String project_id;
	}


	Connection mConnection;

	public DBChildTable (Connection cn){
		mConnection = cn;
	}

	public ChildRow getById(String id){

		ChildRow result = null;

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
	        	ChildRow row;
	        	row = new ChildRow();
	        	row.id = rs.getString(COL_ID);
	        	row.project_id = rs.getString(COL_PROJECT_ID);
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

	public ChildRow insertNew(String project_id){

		if(TextUtil.isEmpty(project_id)) return null;

		ChildRow cr = new ChildRow();
		cr.project_id = project_id;

		boolean succes = false;
		for(int i=0;i<100;i++){
			cr.id = Secure.createRandomAlphabet(32);
			if( insert(cr) ){
				succes = true;
				break;
			}
		}

		if(succes) return cr;
		else return null;

	}


	public boolean insert(ChildRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("insert into %s (%s, %s) values (?, ?)",
	        		TABLE_NAME, COL_ID, COL_PROJECT_ID
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, row.id);
	        st.setString(2, row.project_id);


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