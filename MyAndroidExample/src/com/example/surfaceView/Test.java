package com.example.surfaceView;   
/*  
 * SurfaceView的示例程序  
 * 演示其流程  
 */  
import android.app.Activity;   
import android.content.Context;   
import android.graphics.Canvas;   
import android.graphics.Color;   
import android.graphics.Paint;   
import android.graphics.RectF;   
import android.os.Bundle;   
import android.view.SurfaceHolder;   
import android.view.SurfaceView;   
  
public class Test extends Activity {   
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        setContentView(new MyView(this));   
    }   
       
    //内部类   
    class MyView extends SurfaceView implements SurfaceHolder.Callback{   
  
        SurfaceHolder holder;   
        public MyView(Context context) {   
            super(context);   
            holder = this.getHolder();//获取holder   
            holder.addCallback(this);   
            //setFocusable(true);   
               
        }   
  
        @Override  
        public void surfaceChanged(SurfaceHolder holder, int format, int width,   
                int height) {   
               
        }   
  
        @Override  
        public void surfaceCreated(SurfaceHolder holder) {   
            new Thread(new MyThread()).start();   
        }   
  
        @Override  
        public void surfaceDestroyed(SurfaceHolder holder) {   
               
        }   
           
        //内部类的内部类   
        class MyThread implements Runnable{   
  
            @Override  
            public void run() {   
                Canvas canvas = holder.lockCanvas(null);//获取画布   
                Paint mPaint = new Paint();   
                mPaint.setColor(Color.BLUE);   
                   
                canvas.drawRect(new RectF(40,60,80,80), mPaint);   
                holder.unlockCanvasAndPost(canvas);//解锁画布，提交画好的图像   
                   
            }   
               
        }   
           
    }   
}  
