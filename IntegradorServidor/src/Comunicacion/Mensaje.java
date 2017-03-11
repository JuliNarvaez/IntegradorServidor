package Comunicacion;

import java.io.Serializable;

public class Mensaje implements Serializable{

	private String tipo;
	private String nombre, contra;
	static final long serialVersionUID = 42L;

	public Mensaje(String tipo, String nombre, String contra) {
		this.tipo = tipo;
		this.contra = contra;
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getContra() {
		return contra;
	}
}
