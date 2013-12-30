package jp.crudefox.server.bresto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.crudefox.server.bresto.util.CFUtil;

public class DBKeywordsRelationTable {


	private final static String TABLE_NAME = "keywords_relation";

	private final static String COL_ID_1 = "kid1";
	private final static String COL_ID_2 = "kid2";
	private final static String COL_MODIFIED_TIME = "modified_time";
	private final static String COL_PROJECT_ID = "project_id";



	static public class KeywordsRelationRow{
		public int kid1;
		public int kid2;
		public Date modified_time;
		public String project_id;
	}


	Connection mConnection;

	public DBKeywordsRelationTable (Connection cn){
		mConnection = cn;
	}

	public List<KeywordsRelationRow> getByProjectId(String project_id){

		List<KeywordsRelationRow> result = null;

		try {

	        String qry1 = String.format("select * from %s where %s = ?",
	        		TABLE_NAME, COL_PROJECT_ID
	        );

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, project_id);

	        //問い合わせ
	        ResultSet rs = st.executeQuery();

	        //データの取得
	        //ResultSetMetaData rm = rs.getMetaData();
	        //int cnum = rm.getColumnCount();

	        ArrayList<KeywordsRelationRow> list = new ArrayList<DBKeywordsRelationTable.KeywordsRelationRow>();

	        while(rs.next()){

	        	KeywordsRelationRow row;
	        	row = new KeywordsRelationRow();
	        	row.kid1 = rs.getInt(COL_ID_1);
	        	row.kid2 = rs.getInt(COL_ID_2);
	        	row.modified_time = CFUtil.parseDateTme( rs.getString(COL_MODIFIED_TIME) );
	        	row.project_id = rs.getString(COL_PROJECT_ID);

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


	public boolean insert(KeywordsRelationRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("insert into %s (%s, %s, %s, %s) values (?, ?, ?, ?)",
	        		TABLE_NAME, COL_ID_1, COL_ID_2, COL_MODIFIED_TIME, COL_PROJECT_ID
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setInt(1, row.kid1);
	        st.setInt(2, row.kid2);
	        st.setString(3, row.modified_time!=null ? CFUtil.toDateTimeString( row.modified_time ) : null );
	        st.setString(4, row.project_id);


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




	public boolean delete(KeywordsRelationRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("delete from %s where %s = ? and %s = ?",
	        		TABLE_NAME, COL_ID_1, COL_ID_2
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setInt(1, row.kid1);
	        st.setInt(2, row.kid2);


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