package jp.crudefox.server.ricoh.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class CFUtil {

	public static String convertCalendar2MySQLDATETIMEString(Calendar cal)
	{
		if(cal == null)
		{
			return "1000-01-01 00:00:00";
		}

		DecimalFormat int2df = new DecimalFormat("00");
		StringBuffer sb = new StringBuffer();
		sb.append(cal.get(Calendar.YEAR));
		sb.append("-");
		sb.append(int2df.format(cal.get(Calendar.MONTH) + 1));
		sb.append("-");
		sb.append(int2df.format(cal.get(Calendar.DAY_OF_MONTH)));
		sb.append(" ");
		sb.append(int2df.format(cal.get(Calendar.HOUR_OF_DAY)));
		sb.append(":");
		sb.append(int2df.format(cal.get(Calendar.MINUTE)));
		sb.append(":");
		sb.append(int2df.format(cal.get(Calendar.SECOND)));

		return sb.toString();
	}


	public static final void initMySQLDriver(){
		try {
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
			  //com.mysql.jdbc.Driver d = new com.mysql.jdbc.Driver();

		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static String getParam(HttpServletRequest request, String key){
		String val = request.getParameter(key);
		String val2 = decodeStringFrom8859_1(val);
		return val2;
	}
	public static String[] getParamValues(HttpServletRequest request, String key){
		String[] vals = request.getParameterValues(key);
		if(vals==null) return null;
		String[] vals2 = new String[vals.length];
		for(int i=0;i<vals.length;i++){
			vals2[i] = decodeStringFrom8859_1(vals[i]);
		}
		return vals2;
	}

	public static String decodeStringFrom8859_1(String str){
	    try {
	      byte[] byteData = str.getBytes("ISO-8859-1");
	      str = new String(byteData, "UTF-8");
	    }catch(Exception e){
	      return null;
	    }

	    return str;
	}
	
	
	
	private static final SimpleDateFormat sDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static final Date parseDateTme(String str){
		try {
			return sDateTimeFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static final Date parseDate(String str){
		try {
			return sDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String toDateString(Date date){
		try {
			return sDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static final String toDateString(long date){
		return toDateString(new java.util.Date(date));
	}

	public static final String toDateTimeString(long date){
		return toDateTimeString(new java.util.Date(date));
	}
	public static final String toDateTimeString(Date date){
		try {
			return sDateTimeFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	


//	public static JSONObject postDataReturnJson(String sUrl, ArrayList<NameValuePair> params){
//
//		String data = postData(sUrl, params);
//		if(TextUtils.isEmpty(data)) return null;
//		try {
//			JSONObject json = new JSONObject(data);
//			return json;
//		} catch (JSONException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public static String postData(String sUrl, ArrayList<NameValuePair> params) {

		HttpClient httpClient = new DefaultHttpClient();
		String sReturn = null;

		try {

			HttpPost httpPost = new HttpPost(sUrl);
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpParams httpparams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpparams, 1000*3);
			HttpConnectionParams.setSoTimeout(httpparams, 1000*10);


			if(true){
				HttpResponse objResponse = httpClient.execute(httpPost);

				if (objResponse.getStatusLine().getStatusCode() < 400){
					InputStream objStream = objResponse.getEntity().getContent();
					InputStreamReader objReader = new InputStreamReader(objStream);
					BufferedReader objBuf = new BufferedReader(objReader);
					StringBuilder objJson = new StringBuilder();
					String sLine;
					while((sLine = objBuf.readLine()) != null){
						objJson.append(sLine);
					}
					sReturn = objJson.toString();
					objStream.close();
				}
			}
//			else{
//				httpClient.execute(httpPost);
//				return null;
//			}
		} catch (IOException e) {
			return null;
		}
			return sReturn;
		}

		public static String getData(String sUrl,int connect_time, int so_time) {
			HttpClient objHttp = new DefaultHttpClient();
			HttpParams params = objHttp.getParams();
			HttpConnectionParams.setConnectionTimeout(params, connect_time); //接続のタイムアウト
			HttpConnectionParams.setSoTimeout(params, so_time); //データ取得のタイムアウト
			String sReturn = null;
		try {

			HttpGet objGet = new HttpGet(sUrl);
			HttpResponse objResponse = objHttp.execute(objGet);
			if (objResponse.getStatusLine().getStatusCode() < 400){
				InputStream objStream = objResponse.getEntity().getContent();

				InputStreamReader objReader = new InputStreamReader(objStream);
				BufferedReader objBuf = new BufferedReader(objReader);
				StringBuilder objJson = new StringBuilder();
				String sLine;

				while((sLine = objBuf.readLine()) != null){
					objJson.append(sLine);
				}
				sReturn = objJson.toString();
				objStream.close();
			}
		} catch (IOException e) {
			return null;
		}
		return sReturn;
	}



	public static byte[] getBytes(String sUrl, int connect_time, int so_time) {


			HttpClient objHttp = new DefaultHttpClient();
			HttpParams params = objHttp.getParams();
			HttpConnectionParams.setConnectionTimeout(params, connect_time); //接続のタイムアウト
			HttpConnectionParams.setSoTimeout(params, so_time); //データ取得のタイムアウト
			byte[] sReturn = null;
		try {

			HttpGet objGet = new HttpGet(sUrl);
			HttpResponse objResponse = objHttp.execute(objGet);
			if (objResponse.getStatusLine().getStatusCode() < 400){
				InputStream objStream = objResponse.getEntity().getContent();
				ByteArrayOutputStream os = new ByteArrayOutputStream();

				int len;
				byte[] buf = new byte[1024];
				while((len=objStream.read(buf)) != -1){
					os.write(buf, 0, len);
				}
				sReturn = os.toByteArray();

				objStream.close();
			}
		} catch (IOException e) {
			return null;
		}
		return sReturn;
	}



}
