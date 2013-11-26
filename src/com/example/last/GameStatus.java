package com.example.last;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameStatus {
    private Paint textPaint;
    private int level;
    public int vidas = 1;
    private long levelStartTime;
    private long passedLevelTime;
    private long passedLevelTimeLastLevel;
    private float smallTextSize = 25;
    private float mediumTextSize = 30;
    private float largeTextSize = 50;

    private static final long GRACE_PERIOD = 5000000000L;

    public GameStatus() {
        textPaint = new Paint();
        textPaint.setColor(Color.LTGRAY);
    }

    public void updateSurfaceDimensions(int width, int height) {
        smallTextSize = width / (320 / 25);
        mediumTextSize = width / (320 / 30);
        largeTextSize = width / (320 / 50);
    }

    public int getLevel() {
        return level;
    }
    
    public int getVidas(){
    	return vidas;
    }
    
    public void setVidas(int vidas){
    	this.vidas = vidas;
    }
    
    public void setPassedLevelTimeLastLevel(long time){
    	passedLevelTimeLastLevel = time;
    }
    public void setLevel(int level) {
        this.level = level;
        passedLevelTimeLastLevel = passedLevelTime;
        levelStartTime = System.nanoTime() + GRACE_PERIOD;
    }

    public long getPassedLevelTime() {
        return passedLevelTime;
    }

    public void update() {
        passedLevelTime = (System.nanoTime() - levelStartTime) / 1000000000L;
    }

    public void draw(Canvas canvas, float screenHeight, float screenWidth) {
    	canvas.drawText("Vidas = " + vidas, (float) (screenWidth * 0.18),(float) (screenHeight * 0.10), textPaint);
    }

    public void drawGameOverScreen(Canvas canvas, float screenHeight,
            float screenWidth, int puntaje, int record) {
    	
    	
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(largeTextSize);
        canvas.drawText("GAME OVER", screenWidth / 2,
                (float) (screenHeight * 0.30), textPaint);
        textPaint.setTextSize(smallTextSize);
        canvas.drawText("Tu puntaje fue de " + puntaje, screenWidth / 2,
                (float) (screenHeight * 0.40), textPaint);
        canvas.drawText("Presiona 'Atrás' para ir", screenWidth / 2,
                (float) (screenHeight * 0.55), textPaint);
        canvas.drawText("al menú principal", screenWidth / 2,
                (float) (screenHeight * 0.65), textPaint);
        canvas.drawText(">>Reintentar<<", screenWidth / 2,
                (float) (screenHeight * 0.80), textPaint);
        canvas.drawText("Tu Record es de " + record, screenWidth / 2,
                (float) (screenHeight * 0.75), textPaint);
        
    }

}