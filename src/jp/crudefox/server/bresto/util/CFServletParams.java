package jp.crudefox.server.bresto.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class CFServletParams {


	public static class UploadFile{
		public File tmpfile;
		public File filename;
	}



	private final HashMap<String, List<Object>> mParams = new HashMap<String, List<Object>>();



	public CFServletParams(HttpServlet s, HttpServletRequest req, HttpServletResponse resp, File tmpdir) throws MimeTypeParseException{
		String contentType = req.getContentType();
		MimeType mimeType = contentType!=null ? new MimeType( contentType ) : null;
		if(mimeType!=null && mimeType.match("multipart/form-data")){
			initParamsFromMultiPartFormData(s, req, mimeType, tmpdir);
		}else{
			initParamsFromReqParam(s, req);
		}
	}

	public void putAdd(String key,Object val){
		List<Object> vals = mParams.get(key);
		if( vals!=null ){
			vals.add(val);
		}else{
			vals = new ArrayList<Object>();
			vals.add(val);
			mParams.put(key, vals);
		}
	}

	public Set<String> getNames(){
		return mParams.keySet();
	}

	public boolean hasName(String name){
		return mParams.containsKey(name);
	}

	public Object getParam(String key, int index){
		List<Object> vals = mParams.get(key);
		if(vals==null) return null;
		if(index>=vals.size()) return null;
		return vals.get(index);
	}
	public Object[] getParamValues(String key){
		List<Object> vals = mParams.get(key);
		if(vals==null) return new Object[0];
		return vals.toArray(new Object[0]);
	}

	public String getStringParam(String key, int index){
		Object val = getParam(key, index);
		if( val instanceof String ) return (String) val;
		else return null;
	}
	public String getStringParam(String key){
		return getStringParam(key, 0);
	}

	public UploadFile getFileParam(String key, int index){
		Object val = getParam(key, index);
		if( val instanceof UploadFile ) return (UploadFile) val;
		else return null;
	}
	public UploadFile getFileParam(String key){
		return getFileParam(key, 0);
	}

	private void initParamsFromReqParam(HttpServlet servlet, HttpServletRequest req){
		Map<String, String[]> map = req.getParameterMap();
		for(Entry<String, String[]> e : map.entrySet()){
			String[] vals1 = e.getValue();
			for(int i=0;i<vals1.length;i++){
				String val2 = CFUtil.decodeStringFrom8859_1( vals1[i] );
				if( val2 == null ) val2 = vals1[i];
				putAdd(e.getKey(), val2);
			}
		}
	}

	private void initParamsFromMultiPartFormData(HttpServlet servlet, HttpServletRequest req, MimeType multi_mt, File tmpdir){

		//HashMap<String, List<Object>> params = mParams;

		//(1)アップロードファイルを格納するPATHを取得
		if(!tmpdir.isAbsolute()){
			tmpdir = new File(  servlet.getServletContext().getRealPath(tmpdir.getPath()) );
			System.out.println(tmpdir.getPath());
		}
		if( !tmpdir.exists() ){
			tmpdir.mkdir();
		}

	    //(2)ServletFileUploadオブジェクトを生成
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    ServletFileUpload upload = new ServletFileUpload(factory);

	    //(3)アップロードする際の基準値を設定
	    factory.setSizeThreshold(1024);
	    upload.setSizeMax(-1);
	    //upload.setHeaderEncoding("Windows-31J");



	    try {
	    	//(4)ファイルデータ(FileItemオブジェクト)を取得し、
	    	//   Listオブジェクトとして返す
	    	List<FileItem> list = upload.parseRequest(req);

	    	//(5)ファイルデータ(FileItemオブジェクト)を順に処理
	    	Iterator<FileItem> iterator = list.iterator();
	    	while(iterator.hasNext()){
	    		FileItem fItem = (FileItem)iterator.next();
	    		String key = fItem.getFieldName();

    			System.out.println("key="+key);

	    		//(6)ファイルデータの場合、if内を実行
	    		if(!(fItem.isFormField())){

	    			UploadFile upfile = new UploadFile();

	    			//(7)ファイルデータのファイル名(PATH名含む)を取得
	    			String fileName = fItem.getName();
	    			System.out.println("fileName="+fileName);

	    			if(!TextUtil.isEmpty(fileName)){
	    				upfile.filename = new File(fileName);
	    				upfile.tmpfile = makeNewTmpFile(tmpdir);

	    				fItem.write(upfile.tmpfile);

	    				putAdd(key, upfile);
	    			}
	    		}else{
	    			//フォームデータ
	    			String ct = fItem.getContentType();
	    			//System.out.println(key+" "+ct);
	    			MimeType mt = ct!=null ? new MimeType( ct ) : null;
	    			String charset = mt!=null ? mt.getParameter("charset") : null;
	    			if(charset==null) charset = multi_mt.getParameter("charset");
	    			//System.out.println(key+" "+charset);
	    			//String val = charset!=null ? CFUtil.decodeStringFrom( fItem.getString(), charset) : null;
	    			String val = charset!=null ? fItem.getString(charset) : null;
	    			//if(val==null) val = CFUtil.decodeStringFrom8859_1(val);
	    			if(val==null) val = fItem.getString("utf-8");
	    			if(val==null) val = fItem.getString();
	    			if(val!=null) putAdd(key, val);
	    		}
	    	}
	    }catch (FileUploadException e) {
	    	e.printStackTrace();
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
	    //(10)upFile.htmlページに戻る
	    //res.sendRedirect("upFile.html");

	  }

	protected File makeNewTmpFile(File dir){
		File result = null;
		long time = System.currentTimeMillis();
//		for(int i=0;i<1000;i++){
//			result = new File(dir , String.format("%019d", time));
//			result.createTempFile(prefix, dir);
//		}
		String name = String.format("tmp%019d_", time);
		try {
			result = File.createTempFile(name, ".png", dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
