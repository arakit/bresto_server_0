package jp.crudefox.server.bresto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBGoodTable {


	private final static String TABLE_NAME = "good";

	private final static String COL_PROJECT_ID = "project_id";
	private final static String COL_KID = "kid";
	private final static String COL_USER_ID = "user_id";


	static public class GoodRow{
		public String project_id;
		public int kid;
		public String user_id;
	}


	Connection mConnection;

	public DBGoodTable (Connection cn){
		mConnection = cn;
	}
	
	
	private GoodRow getByRow(ResultSet rs) throws SQLException{
    	GoodRow row;
    	row = new GoodRow();
    	row.project_id = rs.getString(COL_PROJECT_ID);
    	row.kid = rs.getInt(COL_KID);
    	row.user_id = rs.getString(COL_USER_ID);
    	return row;
	}

	public List<GoodRow> getByKId(String project_id, int kid){

		List<GoodRow> result = null;

		try {

	        String qry1 = String.format("select * from %s where %s = ? and %s = ?",
	        		TABLE_NAME, COL_PROJECT_ID, COL_KID
	        );

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, project_id);
	        st.setInt(2, kid);

	        //問い合わせ
	        ResultSet rs = st.executeQuery();

	        //データの取得
	        //ResultSetMetaData rm = rs.getMetaData();
	        //int cnum = rm.getColumnCount();

	        ArrayList<GoodRow> list = new ArrayList<GoodRow>();

	        while(rs.next()){
	        	GoodRow row = getByRow(rs);
	        	list.add(row);
	        }

	        result = list;

	        //接続のクローズ
	        rs.close();
	        st.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public GoodRow getByProjectAndKidAndUserId(String project_id, int kid, String user_id){

		GoodRow result = null;

		try {

	        String qry1 = String.format("select * from %s where %s = ? and %s = ? and %s = ?",
	        		TABLE_NAME, COL_PROJECT_ID, COL_KID, COL_USER_ID
	        );

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, project_id);
	        st.setInt(2, kid);
	        st.setString(3, user_id);

	        //問い合わせ
	        ResultSet rs = st.executeQuery();

	        //データの取得
	        //ResultSetMetaData rm = rs.getMetaData();
	        //int cnum = rm.getColumnCount();
	        
	        if( rs.first() ){
	        	result = getByRow(rs);
	        }

	        //接続のクローズ
	        rs.close();
	        st.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	



//	public KeywordsRelationRow insertNew(String project_id, int kid1, int kid2){
//
//		if(TextUtil.isEmpty(project_id)) return null;
////		if(TextUtil.isEmpty(kid1)) return null;
////		if(TextUtil.isEmpty(kid2)) return null;
//
//
//		KeywordsRelationRow kr = new KeywordsRelationRow();
//		kr.project_id = project_id;
//		kr.kid1 = kid1;
//		kr.kid2 = kid2;
//
//
////		boolean succes = false;
////		for(int i=0;i<100;i++){
////			kr.kid = Secure.randomInt(Integer.MAX_VALUE);
////			if( insert(kr) ){
////				succes = true;
////				break;
////			}
////		}
//
//		if(succes) return kr;
//		else return null;
//
//	}


	public boolean insert(GoodRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("insert into %s (%s, %s, %s) values (?, ?, ?)",
	        		TABLE_NAME, COL_PROJECT_ID, COL_KID,COL_USER_ID
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, row.project_id);
	        st.setInt(2, row.kid);
	        st.setString(3, row.user_id);


	        int affected = st.executeUpdate();

	        result = affected>=1 ;

	        //接続のクローズ
	        st.close();

	        return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("result = "+result);
		}

		return result;

	}




	public boolean delete(GoodRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("delete from %s where %s = ? and %s = ? and %s = ?",
	        		TABLE_NAME, COL_PROJECT_ID, COL_KID, COL_USER_ID
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, row.project_id);
	        st.setInt(2, row.kid);
	        st.setString(3, row.user_id);


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