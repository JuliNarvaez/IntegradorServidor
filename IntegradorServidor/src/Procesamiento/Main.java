package Procesamiento;

import processing.core.PApplet;

public class Main extends PApplet {
	
	Logica log;

	public static void main(String[] args) {
		PApplet.main("Procesamiento.Main");
	}

	@Override
	public void settings() {
		size(200,200);
	}

	@Override
	public void setup() {
		log = new Logica();
	}

	@Override
	public void draw() {
		background(255);
	}
}
