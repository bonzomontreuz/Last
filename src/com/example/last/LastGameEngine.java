package com.example.last;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

public class LastGameEngine {

	public float screenWidth;
	public int ult_y = 0;
	private int level = 5;
	public int ran = 0;
	public int ult_x = 0;
	public int ant_y = 0;
	public int ant_x = 0;
	private int puntaje = 0;
	private int record = 0;
	public int numNave = 0;
	public boolean collided = false;
	private SharedPreferences sharedPref = null;
	public float screenHeight;
	private Paint blackPaint;
	private Resources resources;
	private Paint textPaint;
	private GameStatus gameStatus;
	public final int DOWN = 0;
	public final int UP = 1;
	public int mover = UP;
	private final int WAITING_FOR_SURFACE = 0;
	public int mode = WAITING_FOR_SURFACE;
	private final int RUNNING = 1;
	private final int GAME_OVER = 2;

	public ArrayList<LastCuadrado> cuadrados;
	public LastStageHandler handler;

	public void Init(Context context) {

		sharedPref = context.getSharedPreferences("key_niveles",
				Context.MODE_PRIVATE);

		record = sharedPref.getInt("record", 0);

		resources = context.getResources();
		blackPaint = new Paint();
		blackPaint.setColor(Color.BLACK);
		blackPaint.setStyle(Style.FILL);
		gameStatus = new GameStatus();

		textPaint = new Paint();
		textPaint.setColor(Color.LTGRAY);
		textPaint.setTextSize(40);

	}

	public void onDestroy() {
		try {
		} catch (Exception e) {
		}
	}

	public void setSurfaceDimensions(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		gameStatus.updateSurfaceDimensions((int) screenWidth,
				(int) screenHeight);
		handler = new LastStageHandler(resources, level, screenWidth,
				screenHeight, gameStatus);
		gameStatus.setLevel(1);
		mode = RUNNING;
	}

	@SuppressLint("SimpleDateFormat")
	public void Update() {
		switch (mode) {
		case (WAITING_FOR_SURFACE): {
			break;// no hago nada porque no tengo pantalla :3
		}
		case (RUNNING): {
			gameStatus.update();
			handler.update(screenWidth, screenHeight, mode, ult_x, mover, DOWN);

			if (handler.isCollided()) {
				puntaje = handler.getPuntaje();
				mode = GAME_OVER;
			}
			break;
		}
		case (GAME_OVER): {
			if (record < puntaje) {
				record = puntaje;
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("record", puntaje);
				editor.commit();
			}

			if (ult_y >= ((screenHeight * 0.80) - 25)
					&& ult_y <= ((screenHeight * 0.80) + 25)) {

				handler = new LastStageHandler(resources, level, screenWidth,
						screenHeight, gameStatus);
				gameStatus.setLevel(1);

				mode = RUNNING;

			}
			break;
		}
		}
	}

	public void Draw(Canvas canvas) {

		switch (mode) {
		case (WAITING_FOR_SURFACE): {
			break;// no hago nada porque no tengo pantalla :3
		}
		case (RUNNING): {
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),
					blackPaint);
			handler.draw(canvas);
			gameStatus.draw(canvas, screenHeight, screenWidth);
			break;
		}
		case (GAME_OVER): {
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),
					blackPaint);

			gameStatus.drawGameOverScreen(canvas, screenHeight, screenWidth,
					puntaje, record);
			ult_y = 0;
			break;
		}
		}
	}

}
