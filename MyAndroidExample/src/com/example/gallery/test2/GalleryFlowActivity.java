package com.example.gallery.test2;

 

import android.app.Activity;
import android.os.Bundle;

import com.example.R;

public class GalleryFlowActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.layout_gallery);
        
        Integer[] images = { R.drawable.tu5503_29, R.drawable.tu5503_4,
                R.drawable.tu5503_5, R.drawable.tu5503_6, R.drawable.tu5503_7,
                };
        
        ImageAdapter adapter = new ImageAdapter(this, images);
        adapter.createReflectedImages();

        GalleryFlow galleryFlow = (GalleryFlow) findViewById(R.id.Gallery01);
        galleryFlow.setAdapter(adapter);
        
}
}