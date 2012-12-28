package com.example.xml;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.R;

public class PraseResult extends Activity {
	
	private ListView list;
	private List<PersonInfo> personInfo;
	private PersonInfo person;
	//private DomPrase dom;
	//private Object o;
	
	//private String[] personName = new String[]{"liming","likui","lifei"};
	//private String[] personAge = new String[]{"12","13","14"};
	
	private class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return personInfo.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			person = personInfo.get(position);
			Log.e("mytag","name:"+person.getName());
			LayoutInflater inflater = LayoutInflater
					.from(PraseResult.this);
			View xml_list = inflater.inflate(R.layout.xml_show, null);
			TextView tvName = (TextView) xml_list.findViewById(R.id.personName);
			tvName.setText(person.getName());
			TextView tvAge = (TextView) xml_list.findViewById(R.id.personAge);
			tvAge.setText(person.getAge());
			return xml_list;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prase_result);
		
		initPersonInfo();
		
		list = (ListView) findViewById(R.id.xmllist);
		list.setAdapter(new ListAdapter());
	}
	
	private void initPersonInfo(){
		InputStream inStream = getClass().getClassLoader().getResourceAsStream("PersonInfo.xml");
		//dom = new DomPrase();
		Class c;
		Method m;
		Object o;
		try {
			c = Class.forName(getClazz());
			o = c.newInstance();
			m = c.getMethod("getPersons", InputStream.class);
			personInfo = (List<PersonInfo>)m.invoke(o, inStream);
			Log.e("mytag","size:"+personInfo.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getClazz(){
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String clazzName = bundle.getString("clazz");
		return clazzName;
	}
	
}
