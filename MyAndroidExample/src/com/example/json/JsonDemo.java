package com.example.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JsonDemo extends Activity {
    /*
     * 解析JSON的例子，str保存的是JSON代码，解析后的数据在LogCat里输出 
    */
	
	String TAG = "Json message";
	String str=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout view =  new LinearLayout(this);
        setContentView(view);
        view.setOrientation(1);
        TextView text= new TextView(this);
        detectJSON();
        text.setText("解析json字符串-----"+str);
        view.addView(text);
        
    }
    
    private void detectJSON() {
    	 str = "{"+
    	
		  "\"日期\" : \"2011-06-06\","+

		  //Like 是 JSONObject
		  "\"Like\" : {"+
		    "\"Name\" : \"加内特\","+
		    "\"Height\" : \"2.11cm\","+ 
		    "\"Age\" : 35"+
		  "},"+
		
		  //LikeList 就是一个 JSONObject
		  "\"LikeList\":" +
			  "{\"List\": " +
			  "["+
			  	  //这里也是JSONObject
				  "{"+
				    "\"Name\" : \"Rose\","+
				    "\"Height\" : \"190cm\","+ 
				    "\"Age\" : 23"+
				  "},"+
				  //这里也是JSONObject
				  "{"+
				    "\"Name\" : \"科比\","+
				    "\"Height\" : \"198cm\","+ 
				    "\"Age\" : 33"+
				  "}"+
			  "]"+
		      "}"+
	      "}";
    	
    	try {
			JSONObject dataJson = new JSONObject(str);
			Log.d(TAG, dataJson.getString("日期"));
			
			JSONObject nbaJson = dataJson.getJSONObject("Like");
		
			Log.d(TAG, nbaJson.getString("Name"));
			Log.d(TAG, nbaJson.getString("Height"));
			Log.d(TAG, nbaJson.get("Age").toString());
			
			JSONObject listJson = dataJson.getJSONObject("LikeList");
			JSONArray arrayJson = listJson.getJSONArray("List");
			
			for(int i=0;i<arrayJson.length();i++) {
				
				JSONObject tempJson = arrayJson.optJSONObject(i);
				
				Log.d(TAG, tempJson.getString("Name"));
				Log.d(TAG, tempJson.getString("Height"));
				Log.d(TAG, tempJson.getString("Age").toString());	
			}
			
			
		} catch (JSONException e) {
			System.out.println("Something wrong...");
			e.printStackTrace();
		}
    }
}