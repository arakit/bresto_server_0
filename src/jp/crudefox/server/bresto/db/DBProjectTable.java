package jp.crudefox.server.bresto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.crudefox.server.bresto.util.Secure;
import jp.crudefox.server.bresto.util.TextUtil;

public class DBProjectTable {


	private final static String TABLE_NAME = "project";

	private final static String COL_ID = "project_id";
	private final static String COL_NAME = "project_name";
	private final static String COL_AUTHOR_ID = "author_id";
	private final static String COL_PULISH_URL = "publish_url";


	static public class ProjectRow{
		public String id;
		public String name;
		public String author_id;
		public String publish_url;
	}


	Connection mConnection;

	public DBProjectTable (Connection cn){
		mConnection = cn;
	}

	public ProjectRow getById(String id){



		ProjectRow result = null;

		try {

	        //問い合わせの準備
	        //DatabaseMetaData dm = mConnection.getMetaData();
	        //ResultSet tb = dm.getTables(null, null, TABLE_NAME, null);

	       // Statement st = mConnection.createStatement();

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
	        	ProjectRow row;
	        	row = new ProjectRow();
	        	row.id = rs.getString(COL_ID);
	        	row.name = rs.getString(COL_NAME);
	        	row.author_id = rs.getString(COL_AUTHOR_ID);
	        	row.publish_url = rs.getString(COL_PULISH_URL);
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

	public List<ProjectRow> listByAuthor(String author_id){

		List<ProjectRow> result = null;

		try {

	        //問い合わせの準備
	        String qry1 = String.format("select * from %s where %s = ?",
	        		TABLE_NAME, COL_AUTHOR_ID
	        );

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, author_id);

	        //問い合わせ
	        ResultSet rs = st.executeQuery();

	        //データの取得
	        //ResultSetMetaData rm = rs.getMetaData();
	        //int cnum = rm.getColumnCount();

	        ArrayList<ProjectRow> list = new ArrayList<ProjectRow>();

	        while(rs.next()){
	        	ProjectRow row;
	        	row = new ProjectRow();
	        	row.id = rs.getString(COL_ID);
	        	row.name = rs.getString(COL_NAME);
	        	row.author_id = rs.getString(COL_AUTHOR_ID);
	        	row.publish_url = rs.getString(COL_PULISH_URL);
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


	public ProjectRow insertNew(String project_name, String author_id){

		if(TextUtil.isEmpty(project_name)) return null;
		if(TextUtil.isEmpty(author_id)) return null;

		ProjectRow pr = new ProjectRow();
		pr.author_id = author_id;
		pr.name = project_name;


		boolean succes = false;
		for(int i=0;i<100;i++){
			pr.id = Secure.createRandomAlphabet(32);
			if( insert(pr) ){
				succes = true;
				break;
			}
		}

		if(succes) return pr;
		else return null;

	}


	public boolean insert(ProjectRow row){

		boolean result = false;

		try {

	        String qry1 = String.format("insert into %s (%s, %s, %s, %s) values (?, ?, ?, ?)",
	        		TABLE_NAME, COL_ID, COL_NAME, COL_AUTHOR_ID, COL_PULISH_URL
	        		);

	        PreparedStatement st = mConnection.prepareStatement(qry1);
	        st.setString(1, row.id);
	        st.setString(2, row.name);
	        st.setString(3, row.author_id);
	        st.setString(4, row.publish_url);


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