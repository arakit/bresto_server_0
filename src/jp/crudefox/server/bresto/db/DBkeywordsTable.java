package jp.crudefox.server.bresto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBkeywordsTable {


	private final static String TABLE_NAME = "keywords";

	private final static String COL_ID = "kid";
	private final static String COL_KEYWORD = "keyword";
	private final static String COL_PROJECT_ID = "project_id";



	static public class KeywordsRow{
		public int kid;
		public String keyword;
		
		public int x;
		public int y;
		public int w;
		public int h;
		
		public String project_id;
	}


	Connection mConnection;

	public DBkeywordsTable (Connection cn){
		mConnection = cn;
	}

	public KeywordsRow getById(String project_id,int id){

		KeywordsRow result = null;

		try {

	        String qry1 = String.format("select * from %s where %s = ? and %s = ?",
	        		TABLE_NAME, COL_PROJECT_ID, COL_ID
	        );

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, project_id);
	        st.setInt(2, id);

	        //問い合わせ
	        ResultSet rs = st.executeQuery();

	        //データの取得
	        //ResultSetMetaData rm = rs.getMetaData();
	        //int cnum = rm.getColumnCount();
	        if(rs.first()){
	        	KeywordsRow row;
	        	row = new KeywordsRow();
	        	row.kid = rs.getInt(COL_ID);
	        	row.keyword = rs.getString(COL_KEYWORD);
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



	public List<KeywordsRow> getByProjectId(String project_id){

		List<KeywordsRow> result = null;

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

	        List<KeywordsRow> list = new ArrayList<KeywordsRow>();

	        while(rs.next()){
	        	KeywordsRow row;
	        	row = new KeywordsRow();
	        	row.kid = rs.getInt(COL_ID);
	        	row.keyword = rs.getString(COL_KEYWORD);
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




	public KeywordsRow insertByAutoIncrement(KeywordsRow row){

//		if(TextUtil.isEmpty(keyword)) return null;
//
//
//		KeywordsRow kr = new KeywordsRow();
//		kr.project_id = project_id;
//		kr.keyword = keyword;
//		kr.kid = null;
//
//		boolean succes = false;
////		for(int i=0;i<100;i++){
////			kr.kid = Secure.randomInt(Integer.MAX_VALUE);
////			if( insert(kr) ){
////				succes = true;
////				break;
////			}
////		}
//
//		succes = insert(kr);
//
//
//		if(succes) return kr;
//		else return null;



//		boolean result = false;

		try {

	        String qry1 = String.format("insert into %s (%s, %s) values (?, ?)",
	        		TABLE_NAME, COL_KEYWORD, COL_PROJECT_ID
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1, PreparedStatement.RETURN_GENERATED_KEYS);
	        //st.setString(1, row.kid!=null ? ""+row.kid : null );
	        st.setString(1, row.keyword);
	        st.setString(2, row.project_id);


	        int affected = st.executeUpdate();

	        if( !( affected>=1 ) ) throw new Exception();

	        ResultSet rs = st.getGeneratedKeys();
	        if( rs!=null && rs.first() ){
	        	row.kid = rs.getInt(1);
	        }else{
	        	throw new Exception();
	        }

	        //接続のクローズ
	        st.close();

	        return row;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}


	public boolean delete(int kid, String project_id){

		try {

	        String qry1 = String.format("delete from %s where %s = ? and %s = ?",
	        		TABLE_NAME, COL_ID, COL_PROJECT_ID
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setInt(1, kid);
	        st.setString(2, project_id);


	        int affected = st.executeUpdate();

	        if( !( affected>=1 ) ) throw new Exception();

	        //接続のクローズ
	        st.close();

	        return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}




//	public boolean insert(KeywordsRow row){
//
//		boolean result = false;
//
//		try {
//
//	        String qry1 = String.format("insert into %s (%s, %s, %s) values (?, ?, ?)",
//	        		TABLE_NAME, COL_ID, COL_KEYWORD, COL_PROJECT_ID
//	        		);
//
//	        PreparedStatement st = mConnection.prepareStatement(qry1, PreparedStatement.RETURN_GENERATED_KEYS);
//	        //st.setString(1, row.kid!=null ? ""+row.kid : null );
//	        st.setString(2, row.keyword);
//	        st.setString(3, row.project_id);
//
//	        int affected = st.executeUpdate();
//
//	        if( !( affected>=1 ) ) throw new Exception();
//
//	        ResultSet rs = st.getGeneratedKeys();
//	        if( rs!=null && rs.first() ){
//	        	row.kid = rs.getInt(1);
//	        }
//
//
//	        //接続のクローズ
//	        st.close();
//
//	        return result;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//
//	}


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