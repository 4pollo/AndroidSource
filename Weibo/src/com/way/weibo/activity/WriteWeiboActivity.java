package com.way.weibo.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.way.util.DefaultUserUtil;
import com.way.util.TextUtil;
import com.way.util.WeiboUtil;
import com.way.util.db.UserDB;

/**
 * 发表微博界面
 * 
 * @author way
 * 
 */
public class WriteWeiboActivity extends MyActivity implements OnClickListener {
	private DefaultUserUtil userUtil;
	private UserDB userDB;
	private Drawable headPic;
	private Dialog dialog;
	private List<Map<String, Integer>> gridViewItems;
	private Button back, send;
	private ImageView head;
	private EditText weiboEditText;
	private TextView weiboNumber;
	private int number = 140;
	private Button picBtn, atBtn, topicBtn, faceBtn, geoBtn;
	private int[] faceIds = new int[105];// 表情id数组

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_write_message);
		userUtil = new DefaultUserUtil(this);
		userDB = new UserDB(this);
		headPic = UserDB.getUserByuserId(userUtil.getUserId(),
				userDB.GetUserList(true)).getUserIcon();
		initView();// 初始化view
	}

	private void initView() {
		back = (Button) findViewById(R.id.home_write_back_btn);
		send = (Button) findViewById(R.id.home_write_send_btn);
		head = (ImageView) findViewById(R.id.home_write_head);
		head.setImageDrawable(headPic);
		weiboNumber = (TextView) findViewById(R.id.home_write_number);
		weiboEditText = (EditText) findViewById(R.id.home_write_weibo_edit);
		picBtn = (Button) findViewById(R.id.home_write_pic_btn);
		atBtn = (Button) findViewById(R.id.home_write_at_btn);
		topicBtn = (Button) findViewById(R.id.home_write_topic_btn);
		faceBtn = (Button) findViewById(R.id.home_write_face_btn);
		geoBtn = (Button) findViewById(R.id.home_write_geo_btn);
		back.setOnClickListener(this);
		send.setOnClickListener(this);
		head.setOnClickListener(this);
		picBtn.setOnClickListener(this);
		atBtn.setOnClickListener(this);
		topicBtn.setOnClickListener(this);
		faceBtn.setOnClickListener(this);
		geoBtn.setOnClickListener(this);
		initListener();
	}

	/**
	 * 计算微博内容的长度 1个汉字 == 两个英文字母所占的长度 标点符号区分英文和中文
	 * 
	 * @param c
	 *            所要统计的字符序列
	 * @return 返回字符序列计算的长度
	 */

	public long calculateWeiboLength(CharSequence c) {

		double len = 0;
		for (int i = 0; i < c.length(); i++) {
			int temp = (int) c.charAt(i);
			if (temp > 0 && temp < 127) {
				len += 0.5;
			} else {
				len++;
			}
		}
		return Math.round(len);
	}

	/**
	 * 编辑框字符长度监听
	 */
	private void initListener() {
		weiboEditText.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;// 光标起始位置
			private int selectionEnd;// 光标结束位置

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				temp = s;
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int num = (int) (number - calculateWeiboLength(s));
				weiboNumber.setText(num + " X");
				selectionStart = weiboEditText.getSelectionStart();
				selectionEnd = weiboEditText.getSelectionEnd();
				// String str = weiboEditText.getText().toString();
				// int length = str.length();
				if (temp.length() > number) {
					s.delete(selectionStart - 1, selectionEnd);// 超过了长度就删除
					int tempSelection = selectionEnd;
					weiboEditText.setText(s);
					weiboEditText.setSelection(tempSelection);// 设置光标在最后
				}
			}
		});
	}

	/**
	 * 创建一个表情选择对话框
	 */
	private void createFaceDialog() {
		dialog = new Dialog(this, R.style.dialog);// 自定义风格的对话框
		GridView gridView = createGridView();// 获取装表情的gridView
		dialog.setContentView(gridView);
		Window window = dialog.getWindow();

		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();// 屏幕的宽度
		int heigth = display.getHeight();// 屏幕高度

		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = width;
		lp.height = (int) (0.4 * heigth);
		// lp.x = 0;
		// lp.y = 200;
		dialog.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, Integer> map = gridViewItems.get(position);
				// 下面是显示表情字符串
				int drawableId = map.get("face");
				String faceStr = TextUtil.drawableIdToFaceName.get(drawableId);
				faceStr = "/" + faceStr;
				weiboEditText.append(faceStr);
				dialog.dismiss();
				// 下面是显示表情图片
				// Bitmap face = BitmapFactory.decodeResource(getResources(),
				// map.get("face"));
				// ImageSpan imageSpan = new ImageSpan(face,
				// ImageSpan.ALIGN_BASELINE);
				// String str = null;
				// if ((position + 1) < 10) {
				// str = "h00" + (position + 1);
				// } else if ((position + 1) < 100) {
				// str = "h0" + (position + 1);
				// } else {
				// str = "h" + (position + 1);
				// }
				// SpannableString spannableString = new SpannableString(str);
				// spannableString.setSpan(imageSpan, 0, 4,
				// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				// weiboEditText.append(spannableString);
				// dialog.dismiss();
			}
		});
	}

	/**
	 * 创建表情的GridView
	 * 
	 * @return GridView
	 */
	private GridView createGridView() {
		GridView gridView = new GridView(this);// 实例化一个GridView
		gridViewItems = new ArrayList<Map<String, Integer>>();
		DecimalFormat df = new DecimalFormat("000");// 格式化数字
		for (int i = 0; i < faceIds.length; i++) {
			try {
				String formatStr = "h" + df.format(i + 1);
				int resourceId = R.drawable.class.getDeclaredField(formatStr)
						.getInt(this);
				faceIds[i] = resourceId;
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, Integer> gridViewItem = new HashMap<String, Integer>();
			gridViewItem.put("face", faceIds[i]);
			gridViewItems.add(gridViewItem);
		}
		gridView.setAdapter(new FaceAdapter(this, gridViewItems));
		gridView.setNumColumns(5);
		gridView.setBackgroundColor(Color.rgb(214, 211, 214));
		gridView.setHorizontalSpacing(1);
		gridView.setVerticalSpacing(1);
		gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		gridView.setGravity(Gravity.CENTER);
		return gridView;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_write_back_btn:
			finish();
			break;
		case R.id.home_write_send_btn:
			new WriteWeiboTask().execute(new String[] { weiboEditText.getText()
					.toString() });
			break;
		case R.id.home_write_head:
			Toast.makeText(this, "我不喜欢别人摸我的头", Toast.LENGTH_SHORT).show();
			break;
		case R.id.home_write_pic_btn:

			break;
		case R.id.home_write_at_btn:

			break;
		case R.id.home_write_topic_btn:

			break;
		case R.id.home_write_face_btn:
			createFaceDialog();
			break;
		case R.id.home_write_geo_btn:

			break;
		default:
			break;
		}
	}

	/**
	 * 异步任务发布微博
	 * 
	 * @author way
	 * 
	 */
	public class WriteWeiboTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return WeiboUtil.getInstance().sendWeibo(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject data = new JSONObject(result).getJSONObject("data");// 解析json信息
				if (data.getString("msg").equals("ok")) {
					Toast.makeText(getApplicationContext(), "发表成功",
							Toast.LENGTH_SHORT).show();
					weiboEditText.setText("");
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "发表失败",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 表情适配器
	 * 
	 * @author way
	 * 
	 */
	class FaceAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<Map<String, Integer>> gridViewItems;

		public FaceAdapter(Context context,
				List<Map<String, Integer>> gridViewItems) {
			super();
			this.context = context;
			this.gridViewItems = gridViewItems;
			this.inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return gridViewItems.size();
		}

		public Object getItem(int position) {
			return gridViewItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			Map<String, Integer> map = gridViewItems.get(position);
			int faceId = (Integer) map.get("face");
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.write_face_gridview_item, null);
			}
			ImageView image = (ImageView) convertView
					.findViewById(R.id.write_face);
			image.setImageDrawable(context.getResources().getDrawable(faceId));
			return convertView;
		}
	}

	@Override
	public void isNetAvailable(boolean isWork) {
		// TODO Auto-generated method stub
		if (!isWork) {
			Toast.makeText(getApplicationContext(), "网络连接断开",
					Toast.LENGTH_SHORT).show();
		}
	}
}