package Procesamiento;

import Comunicacion.Comunicacion;

public class Logica {

	private Comunicacion com;
	
	public Logica() {
		com = new Comunicacion();
		com.start();
	}
}
