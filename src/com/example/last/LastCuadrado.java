package com.example.last;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class LastCuadrado extends GfxObject {

	private Paint paint;

	public LastCuadrado(Bitmap bitmap, float x, float y, double velocity,
			double direction) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		paint = new Paint();
		setVelocity(velocity);
		setDirection(direction);
	}
public float getY(){
	return this.y;
}
@Override
public void move(float screenWidth, float screenHeight) {
	float minX = 0 - bitmap.getWidth() / 2;
	float maxX = screenWidth + bitmap.getWidth() / 2;
	float minY = 0 - bitmap.getHeight() / 2;
	float maxY = screenHeight + bitmap.getHeight() / 2;

	looped = false;
	x += dX;
	y += dY;
	if (x > maxX) {
		x = minX;
		looped = true;
	}
	if (x < minX) {
		x = maxX;
		looped = true;
	}
	if (y > maxY) {
		y = minY;
		looped = true;
		vuelta = true;
	}
	if (y < minY) {
		y = maxY;
		looped = true;
	}

	angle += rotation;
	if (Math.abs(angle) >= 360)
		angle = 0;
}

public void setY(float newY){
	this.y = newY;
}
	public void draw(Canvas canvas) {
		draw(canvas, x, y, paint);
	}
}
