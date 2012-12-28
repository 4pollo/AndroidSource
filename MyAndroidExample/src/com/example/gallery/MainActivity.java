package com.example.gallery;

import java.util.ArrayList;

import com.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

 

 

public class MainActivity  extends Activity implements OnItemSelectedListener{
	private CustomGallery gallery;
	private UniversityAdapter pAdapter;
	private ArrayList<University> universityList = new ArrayList<University>();

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallerymain);
		
		
		init();
	}

	private void init() {
		gallery =(CustomGallery)findViewById(R.id.gallery);
		pAdapter = new UniversityAdapter(MainActivity.this, universityList);// 初始化我们自定义的Adapter
		University p = new University();
		p.setName("电子科技大学");
		universityList.add(p);
		University p1 = new University();
		p1.setName("清华大学");
		universityList.add(p1);
		University p2 = new University();
		p2.setName("北京大学");
		universityList.add(p2);
		University p3 = new University();
		p3.setName("家里顿大学");
		universityList.add(p3);
		University p4 = new University();
		p4.setName("蹲家里大学");
		universityList.add(p4);
		 
		gallery.setAdapter(pAdapter);// 设置Gallery显示的内容
		gallery.setSelection(Integer.MAX_VALUE / 2);
		// 通过setSelection()
											// 可以设置当前选中的元素，这里我们将其设置在中间
		gallery.setOnItemSelectedListener(this);// 这里对Item项进行监听，以实现刷新显示的效果
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		pAdapter.notifyDataSetChanged(arg2);// arg2会返回当前选中项的位置，调用此方法，通知更新
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
}
