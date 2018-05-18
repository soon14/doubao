package com.yljt.cascadingmenu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.xinspace.csevent.R;
import com.xinspace.csevent.app.CoresunApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DBManager {
    private final int BUFFER_SIZE = 1024;
    public static final String DB_NAME = "city_cn.s3db";
//    public static final String PACKAGE_NAME = "com.com.com.yljt.cascadingmenu";
    public static final String PACKAGE_NAME = CoresunApp.instance.getPackageName();//应用包名
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;
    private SQLiteDatabase database;
    private Context context;
    private File file=null;
    
    DBManager(Context context) {
        this.context = context;
    }
 
    public void openDatabase() {
    	
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }
    public SQLiteDatabase getDatabase(){
    	return this.database;
    }
 
    private SQLiteDatabase openDatabase(String dbfile) {
        try {
        	file = new File(dbfile);
            if (!file.exists()) {
            	InputStream is = context.getResources().openRawResource(R.raw.city);
            	if(is!=null){
            	}else{
            	}
            	FileOutputStream fos = new FileOutputStream(dbfile);
            	if(is!=null){
            	}else{
            	}
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count =is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                	fos.flush();
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return database;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
        }
        return null;
    }
    public void closeDatabase() {
    	if(this.database!=null)
    		this.database.close();
    }
}