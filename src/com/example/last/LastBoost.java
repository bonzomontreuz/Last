package com.example.last;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class LastBoost extends GfxObject {
	private Paint paint;

	public LastBoost(Bitmap bitmap, float x, float y, double velocity,
			double direction) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		paint = new Paint();
		setVelocity(velocity);
		setDirection(direction);
	}


	public void draw(Canvas canvas) {
		draw(canvas, x, y, paint);
	}
}