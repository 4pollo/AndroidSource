package com.example.menu;

 

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.R;

public class MenuActivity extends Activity {

    private ListView lv;
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menumain);
    
        //如定义了一个listview控件
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("item", "第一个菜单项");
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("item", "第二个菜单项");
        HashMap<String, String> map3 = new HashMap<String, String>();
        map3.put("item", "第三个菜单项");
        HashMap<String, String> map4 = new HashMap<String, String>();
        map4.put("item", "第四个菜单项");
        HashMap<String, String> map5 = new HashMap<String, String>();
        map5.put("item", "第五个菜单项");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
       
        //初始化该控件
        lv=(ListView)findViewById(R.id.lv_list);
       
        //定义适配器 参数为该显示信息用的布局文件 以及显示信息用的控件，这里是一个textview控件
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item, new String[]{"item"}, new int[]{R.id.tv});
        lv.setAdapter(adapter);
       
        //注册上下文菜单显示用的view
        registerForContextMenu(this.lv);
    }

    //该方法在注册的view被被长按时创建该view的上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //当菜单某个选项被点击时调用该方法
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
       AdapterContextMenuInfo infor = (AdapterContextMenuInfo)item.getMenuInfo();
       System.out.println(infor);
        switch(item.getItemId())
        {
        case R.id.help:
            Toast.makeText(this, "请求帮助", Toast.LENGTH_LONG).show();
            return true;
        case R.id.addnew:
            Toast.makeText(this, "添加新的", Toast.LENGTH_LONG).show();
            return true;
        case R.id.delete:
            Toast.makeText(this, "删除信息", Toast.LENGTH_LONG).show();
            return true;
        case R.id.new_game:
            Toast.makeText(this, "新游戏", Toast.LENGTH_LONG).show();
            return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //当上下文菜单关闭时调用的方法
    @Override
    public void onContextMenuClosed(Menu menu) {
        // TODO Auto-generated method stub
        super.onContextMenuClosed(menu);
    }

}