package com.example.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;

public class FileWriteAndRead extends Activity {
	String res = "";
	TextView myTextView;
	String fileName = "yan.txt"; // 文件名字

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		setContentView(view);
		myTextView = new TextView(this);

		try {

			InputStream in = getResources().openRawResource(R.raw.qingshui);

			// 在\Test\res\raw\bbi.txt,

			int length = in.available();

			byte[] buffer = new byte[length];

			in.read(buffer);

			// res = EncodingUtils.getString(buffer, "UTF-8");

			// res = EncodingUtils.getString(buffer, "UNICODE");

			res = EncodingUtils.getString(buffer, "BIG5");

			// 依bbi.txt的编码类型选择合适的编码，如果不调整会乱码

			in.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		myTextView.setText(res);// 把得到的内容显示在TextView上

		// 二、 从asset中获取文件并读取数据（资源文件只能读不能写）

		try {

			InputStream in = getResources().getAssets().open(fileName);

			// \Test\assets\yan.txt这里有这样的文件存在

			int length = in.available();

			byte[] buffer = new byte[length];

			in.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

		} catch (Exception e) {

			e.printStackTrace();

		}

		// 三、
		// 从sdcard中去读文件，首先要把文件通过\android-sdk-windows\tools\adb.exe把本地计算机上的文件copy到sdcard上去，adb.exe
		// push e:/Y.txt /sdcard/, 不可以用adb.exe push e:\Y.txt \sdcard\ 同样：
		// 把仿真器上的文件copy到本地计算机上用： adb pull ./data/data/com.tt/files/Test.txt e:/

		String fileName = "/sdcard/Y.txt";

		// 也可以用String fileName = "mnt/sdcard/Y.txt";

		String res = "";

		try {

			FileInputStream fin = new FileInputStream(fileName);

			// FileInputStream fin = openFileInput(fileName);

			// 用这个就不行了，必须用FileInputStream

			int length = fin.available();

			byte[] buffer = new byte[length];

			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		myTextView.setText(res);

	 
	}

	// 四、 写， 读data/data/目录(相当AP工作目录)上的文件，用openFileOutput

	// 写文件在./data/data/com.tt/files/下面

	public void writeFileData(String fileName, String message) {

		try {

			FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);

			byte[] bytes = message.getBytes();

			fout.write(bytes);

			fout.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

	// -------------------------------------------------------

	// 读文件在./data/data/com.tt/files/下面

	public String readFileData(String fileName) {

		String res = "";

		try {

			FileInputStream fin = openFileInput(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];

			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return res;

	}

	// 五、 写， 读sdcard目录上的文件，要用FileOutputStream， 不能用openFileOutput

	// 写在/mnt/sdcard/目录下面的文件

	public void writeFileSdcard(String fileName, String message) {

		try {

			// FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);

			FileOutputStream fout = new FileOutputStream(fileName);

			byte[] bytes = message.getBytes();

			fout.write(bytes);

			fout.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

	// 读在/mnt/sdcard/目录下面的文件

	public String readFileSdcard(String fileName) {

		String res = "";

		try {

			FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];

			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return res;

	}
}
