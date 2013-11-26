package com.example.last;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class LastActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.last, menu);
		return true;
	}
	public void start(View v){
  	  Intent intent = new Intent(this, LastGameActivity.class);
  	  startActivity(intent);
	}
	
    public void howto(View v) {
  	  Toast.makeText(this, "You clicked on Howto!", Toast.LENGTH_LONG).show();
  	  Intent intent = new Intent(this, HowTo.class);
  	  startActivity(intent);
  	}
}
