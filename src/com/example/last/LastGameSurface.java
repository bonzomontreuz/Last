package com.example.last;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LastGameSurface extends SurfaceView implements
		SurfaceHolder.Callback {

	LastGameEngine gameEngine;
	SurfaceHolder surfaceHolder;
	Context context;

	private GameThread gameThread;

	public LastGameSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		InitView();
	}

	public LastGameSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		InitView();
	}

	void InitView() {
		SurfaceHolder surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		gameEngine = new LastGameEngine();
		gameEngine.Init(context);
		gameThread = new GameThread(surfaceHolder, context, new Handler(),
				gameEngine);
		setFocusable(true);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		boolean retry = true;
		gameThread.state = GameThread.PAUSED;
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (gameThread.state == GameThread.PAUSED) {
			gameThread = new GameThread(getHolder(), context, new Handler(),
					gameEngine);
			gameThread.start();
		} else {
			gameThread.start();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		gameEngine.setSurfaceDimensions(width, height);
	}

	public boolean onTouchEvent(MotionEvent motionEvent) {
		final int action = motionEvent.getAction();

		switch (action & MotionEvent.ACTION_MASK) {

		case MotionEvent.ACTION_DOWN: {
			final float x = motionEvent.getX();
			final float y = motionEvent.getY();

			gameEngine.ult_x = (int) x;
			gameEngine.ult_y = (int) y;
			gameEngine.mover = gameEngine.DOWN;

			break;
		}


		case MotionEvent.ACTION_UP: {
			gameEngine.mover = gameEngine.UP;
			break;
		}

		}
		return true;
	}
}