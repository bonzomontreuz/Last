package com.example.last;

import android.app.Activity;
import android.os.Bundle;

public class LastGameActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.lastgame);
		ToastMaker.setGameActivity(this);
	}
}
