package Producto;

public class Trufa {

	private String tipo;
	private String precio;

	public Trufa() {
		precio = null;
	}

	public void tipoTrufa(String tipo) {
		this.tipo = tipo;

		switch (tipo) {

		case "Milo":
			precio = "2.000";
			break;
		case "Oreo":
			precio = "1.000";
			break;
		case "Arequipe":
			precio = "5.000";
			break;
		case "Maracuya":
			precio = "2500";
			break;
		case "MiloLight":
			precio = "3.000";
			break;
		case "OreoLight":
			precio = "2.000";
			break;
		case "ArequipeLight":
			precio = "6.000";
			break;
		case "MaracuyaLight":
			precio = "3500";
			break;

		}

	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
}
