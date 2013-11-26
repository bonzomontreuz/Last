package com.example.last;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class LastStageHandler {
	public ArrayList<LastCuadrado> cuadrados;
	private Paint paint;
	public boolean collided = false;
	private Resources resources;
	private LastNave nave;
	int ultcolB;
	int ultcolC;
	private float ultY = 1000;
	private int ultSeg = 0;
	Context context;
	private int ultSegB = 0;
	private float scrHeight;
	private float scrWidth;
	private long tiempo;
	private long tiempoParaB;
	private int numC;
	private int puntaje = 0;
	private GameStatus gameStatus;

	private ArrayList<Integer> posibles;
	private ArrayList<Float> vectX;
	private ArrayList<LastBoost> vBoosts;
	private int[] vboostModes = new int[4];

	private int boostMode;
	private final int MAX_VELOCITY_BOOST = 0;
	private final int LIFE_BOOST = 1;
	private final int MIN_VELOCITY_BOOST = 2;
	private final int KILL_ALL_BOOST = 3;

	public int getPuntaje() {
		return puntaje;
	}

	private int random_index(int length) {
		return (int) (Math.random() * (length));
	}

	public boolean isCollided() {
		return collided;
	}

	public LastStageHandler(Resources resources, int level, float screenWidth,
			float screenHeight, GameStatus gameStatus) {
		
		
		paint = new Paint();

		posibles = new ArrayList<Integer>();//vector con cantidades posibles de cuadrados
		vectX = new ArrayList<Float>();// vector con ubicaciones posibles para los cuadrados

		this.gameStatus = gameStatus;
		scrWidth = screenWidth;

		
		gameStatus.vidas = 1;

		cuadrados = new ArrayList<LastCuadrado>();//vector de cuadrados
		vBoosts = new ArrayList<LastBoost>();//vector de boosts

		scrHeight = screenHeight;
		this.resources = resources;

		nave = new LastNave(BitmapFactory.decodeResource(resources,
				R.drawable.ship30), screenWidth / 2, scrHeight / 2
				+ (scrHeight / 3) + (scrHeight / 10), 0, 0);

		vboostModes[0] = MAX_VELOCITY_BOOST;
		vboostModes[1] = LIFE_BOOST;
		vboostModes[2] = MIN_VELOCITY_BOOST;
		vboostModes[3] = KILL_ALL_BOOST;

		numC = (int) scrWidth / (BitmapFactory.decodeResource(resources, R.drawable.cuadrado).getWidth() + 5);//numero de cuadrados
		int divisiones = (int) scrWidth / numC;//numero de divisiones

		for (int i = 0; i < numC; i++) {
			posibles.add(i + 1);//añade al vector las cantidades
			vectX.add((float) divisiones * (float) i);//añade las diferentes posiciones
		}
	}

	public void addBoost() {//Nuevo Boost!
		float x, y;
		double[] vectVelocity = { 10, 15, 20, 25 };//velocidades que puede tener
		float[] vectY = { (float) (scrHeight * -0.1) };
		x = vectX.get(random_index(vectX.size()));
		y = vectY[random_index(vectY.length)];
		double velocity = (double) vectVelocity[random_index(vectVelocity.length)];

		vBoosts.add(new LastBoost(BitmapFactory.decodeResource(resources,
				R.drawable.boost), x, y, velocity, 0));
		
	}

	public void add(int posibles) {//Nuevos Cuadrados!

		for (int i = 0; i < posibles; i++) {//cuantos cuadrados
			float x, y;
			double[] vectVelocity = { 10, 15, 20, 25 };//velocidades
			float[] vectY = { (float) (scrHeight * -0.1) };
			x = vectX.get(random_index(vectX.size()));//ubicacion
			y = vectY[random_index(vectY.length)];
			double velocity = (double) vectVelocity[random_index(vectVelocity.length)];
			
			cuadrados.add(new LastCuadrado(BitmapFactory.decodeResource(
					resources, R.drawable.cuadrado), x, y, velocity, 0));
		}
	}
	
	public void gato(int posibles){
		for (int i = 0; i <= posibles;i++){
			float y = (float) (scrHeight * -0.1);
			float x = vectX.get(i);
			double velocity = 25;
			
			cuadrados.add(new LastCuadrado(BitmapFactory.decodeResource(
					resources, R.drawable.cuadrado), x, y, velocity, 0));
		}
	}

	public void update(float screenWidth, float screenHeight, int mode,
			int ult_x, int mover, final int DOWN) {
		try {

			tiempo = gameStatus.getPassedLevelTime();
			tiempoParaB = gameStatus.getPassedLevelTime();

			if (tiempo % 1 == 0 && tiempo != ultSeg) {// cada 1 segundo
				add(posibles.get(random_index(posibles.size())));//nuevos cuadrados!
				ultY = 1000;
				ultSeg = (int) tiempo;

			}

			if (tiempoParaB % 3 == 0 && tiempoParaB != ultSegB) {//cada 3 segundos
				addBoost();//un nuevo boost
				ultSegB = (int) tiempo;
			}

			Iterator<LastCuadrado> cuadradosIterator;
			cuadradosIterator = cuadrados.iterator();

			Iterator<LastBoost> vBoostsIterator;
			vBoostsIterator = vBoosts.iterator();

			int anterior = 0;

			while (vBoostsIterator.hasNext()) {//mientras que haya un boost o mas
				LastBoost boost;
				boost = vBoostsIterator.next();
				boost.y += boost.getVelocity();//avanzar

				if ((nave.x - (nave.bitmap.getWidth() / 2)) <= (boost.x + (boost.bitmap//colision!
						.getWidth() / 2))
						&& (nave.x + (nave.bitmap.getWidth() / 2)) >= (boost.x - (boost.bitmap
								.getWidth() / 2))
						&& nave.y + (nave.bitmap.getHeight() / 2) >= (boost.y - (boost.bitmap
								.getHeight() / 2))
						&& nave.y - (nave.bitmap.getHeight() / 2) <= (boost.y + (boost.bitmap
								.getHeight() / 2))) {

					boostMode = random_index(vboostModes.length);//que boost ejecutara

					switch (boostMode) {
					case (MAX_VELOCITY_BOOST): {
						for (LastCuadrado cuadrado : cuadrados) {
							cuadrado.setVelocity(cuadrado.getVelocity() * 2);
						}
						ToastMaker.makeToast("MAS RAPIDO");
						break;
					}
					case (KILL_ALL_BOOST): {
						while (cuadradosIterator.hasNext()) {
							LastCuadrado cuadrado;
							cuadrado = cuadradosIterator.next();
							cuadradosIterator.remove();
						}
						ToastMaker.makeToast("CHAU CUADRADOS");
						break;

					}
					case (MIN_VELOCITY_BOOST): {
						for (LastCuadrado cuadrado : cuadrados) {
							cuadrado.setVelocity(cuadrado.getVelocity() / 2);
						}
						ToastMaker.makeToast("MAS LENTO");
						break;
					}
					case (LIFE_BOOST): {
						gameStatus.vidas += 1;
						ToastMaker.makeToast( gameStatus.vidas + " vidas, usalas");
						break;
					}

					}
					vBoostsIterator.remove();//elimina boost
				}
			}

			while (cuadradosIterator.hasNext()) {//mientras que el vector tenga otro cuadrado
				LastCuadrado cuadrado;
				anterior++;//contador de cuadrados
				cuadrado = cuadradosIterator.next();
				cuadrado.y += cuadrado.getVelocity();

				if ((nave.x - (nave.bitmap.getWidth() / 2)) <= (cuadrado.x + (cuadrado.bitmap//colision!
						.getWidth() / 2))
						&& (nave.x + (nave.bitmap.getWidth() / 2)) >= (cuadrado.x - (cuadrado.bitmap
								.getWidth() / 2))
						&& nave.y + (nave.bitmap.getHeight() / 2) >= (cuadrado.y - (cuadrado.bitmap
								.getHeight() / 2))
						&& nave.y - (nave.bitmap.getHeight() / 2) <= (cuadrado.y + (cuadrado.bitmap
								.getHeight() / 2))) {
					ultcolC = (int) tiempo;
					cuadradosIterator.remove();
					
					gameStatus.setVidas(gameStatus.vidas - 1);
					
					if (gameStatus.getVidas() <= 0) {
						collided = true;
					}
				}

				if (cuadrado.y >= scrHeight) {//Si el cuadrado llego al final de la pantalla
					cuadradosIterator.remove();//Elimina cuadrado
					puntaje++;

				}

			}
			cuadrados.trimToSize();//Acomodar el vector
			if (!cuadradosIterator.hasNext()) {//si llego al final del vector
				cuadradosIterator = cuadrados.iterator();//vuelve a empezar
			}

			if (anterior == 0) {//si no hay mas cuadrados
				add(posibles.get(random_index(posibles.size())));//que se creen mas
				ultY = 1000;
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		if (mover == DOWN) {// si la pantalla es todada

			if (ult_x <= screenWidth / 2)//si se toco del lado izquierdo
				nave.x -= 30;//mover hacia la izquierda
			else//sino
				nave.x += 30;//mover hacia la derecha
			nave.move(screenWidth, screenHeight);//si te vas de la pantalla regresaras por el otro lado
		}
		
		if (gameStatus.getVidas() == 9){
			ToastMaker.makeToast( "no me gustan mucho los gatos...");
			gato((int) (numC));
		}
		
		
	}

	public void draw(Canvas canvas) {

		nave.draw(canvas);

		for (LastCuadrado cuadrado : cuadrados) {//dibujar cuadrados
			cuadrado.draw(canvas, cuadrado.x, cuadrado.y, paint);
		}

		for (LastBoost boost : vBoosts) {//dibujar boost
			boost.draw(canvas, boost.x, boost.y, paint);
		}
	}
}
