package com.example.last;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GfxObject {

	protected Bitmap bitmap;
	protected float x;
	protected float y;
	private double velocity = 0;
	private double direction = 0;
	protected double dX = 0;
	protected double dY = 0;
	protected int rotation = 0;
	protected int angle = 0;
	protected boolean vuelta = false;
	protected boolean collided = false;

	public boolean isCollided() {
		return collided;
	}
	public boolean isVuelteado() {
		return vuelta;
	}
	public void setVuelteado(boolean estado) {
		vuelta = estado;
	}
	public void setX(float equis) {
		x = equis;
	}
	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public double getVelocity() {
		return velocity;
	}

	public double getDirection() {
		return direction;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
		calculateDXDY();
	}

	protected boolean looped = false;

	public boolean isLooped() {
		return looped;
	}

	public void setDirection(double direction) {
		this.direction = direction;
		if (this.direction > 360)
			this.direction -= 360;
		if (this.direction < 0)
			this.direction += 360;
		calculateDXDY();
	}

	private void calculateDXDY() {

		double radians = Math.toRadians(direction - 90);
		dX = Math.cos(radians) * velocity;
		dY = Math.sin(radians) * velocity;
	}

	public void move(float screenWidth, float screenHeight) {
		float minX = 0 - bitmap.getWidth() ;
		float maxX = screenWidth + bitmap.getWidth();
		float minY = 0 - bitmap.getHeight();
		float maxY = screenHeight + bitmap.getHeight();

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

	public void draw(Canvas canvas, float x, float y, Paint paint) {
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.rotate(angle, x, y);

		canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2,
				y - bitmap.getHeight() / 2, paint);

		canvas.restore();
	}

	public void addVelocity(double increment, int angle) {
		double radians = Math.toRadians(angle - 90);
		dX += Math.cos(radians) * increment;
		dY += Math.sin(radians) * increment;
	}

}