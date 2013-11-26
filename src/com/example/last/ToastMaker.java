package com.example.last;

import android.widget.Toast;

public class ToastMaker {
	public static LastGameActivity gameActivity;

	public static void setGameActivity(LastGameActivity game) {
		ToastMaker.gameActivity = game;
	}

	public static LastGameActivity getGameActivity() {
		return ToastMaker.gameActivity;
	}

	public static void makeToast(final String msg) {

		gameActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(gameActivity.getBaseContext(), msg,
						Toast.LENGTH_LONG).show();
			}
		});
	}
}
