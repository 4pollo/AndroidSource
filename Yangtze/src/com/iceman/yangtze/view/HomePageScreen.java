
package com.iceman.yangtze.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iceman.yangtze.Globe;
import com.iceman.yangtze.R;
import com.iceman.yangtze.Util;
import com.iceman.yangtze.WindowActivity;
import com.iceman.yangtze.net.MyHttpResponse;

public class HomePageScreen extends WindowActivity {
    private GridView mGridView;

    private int[] imgs;

    private String[] items;

    private TextView mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        setContentView(R.layout.homepage_layout);
        mUserInfo = (TextView) findViewById(R.id.user_info);
        mUserInfo.setText(Globe.sId + " " + Globe.sName + " " + Globe.sClassName);
        items = getResources().getStringArray(R.array.home_page_items);
        imgs = new int[] {
                R.drawable.score, R.drawable.search, R.drawable.xuanke, R.drawable.time_table,
                R.drawable.class_score,
                // R.drawable.user_info,
                R.drawable.gonggao, R.drawable.check_update, R.drawable.about
        };
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setAdapter(new GridViewAdapter());
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it;
                switch (position) {
                    case 0:
                        it = new Intent(HomePageScreen.this, ScoreSearchScreen.class);
                        startActivity(it);
                        break;
                    case 1:

                        it = new Intent(HomePageScreen.this, StudentSearchScreen.class);
                        startActivity(it);
                        break;
                    case 2:

                        it = new Intent(HomePageScreen.this, XuanKeInfoScreen.class);
                        startActivity(it);
                        break;
                    case 3:
                        it = new Intent(HomePageScreen.this, TimeTableScreen.class);
                        startActivity(it);
                        break;
                    case 4:

                        it = new Intent(HomePageScreen.this, ClassScoreSearchScreen.class);
                        startActivity(it);
                        break;
                    // case 5:
                    //
                    // it = new Intent(HomePageScreen.this,
                    // StudentInfoEditScreen.class);
                    // startActivity(it);
                    // break;
                    case 5:

                        it = new Intent(HomePageScreen.this, NewsScreen.class);
                        startActivity(it);
                        break;
                    case 6:
                        it = new Intent(HomePageScreen.this, CheckUpdateScreen.class);
                        startActivity(it);
                        break;
                    case 7:

                        it = new Intent(HomePageScreen.this, AboutScreen.class);
                        startActivity(it);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.homepage_gridview_item_layout,
                        null);
            }
            ImageView img = (ImageView) convertView.findViewById(R.id.gridview_img);
            img.setImageResource(imgs[position]);
            TextView text = (TextView) convertView.findViewById(R.id.gridview_text);
            text.setText(items[position]);
            return convertView;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog(0);
            return true;
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog tAlertDialog = Util.createAlertDialog(this, this.getString(R.string.tishi),
                this.getString(R.string.ifwantquit), this.getString(R.string.confirm),
                this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mNetClient.isRunning = false;
                        Globe.clearAll();
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        return tAlertDialog;
    }

    @Override
    public void handResponse(MyHttpResponse myHttpResponse) {

    }
}
