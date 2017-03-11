package Comunicacion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;


import Memoria.Usuario;
import Producto.Trufa;

public class Comunicacion extends Thread{

	private DatagramSocket socket;

	private LinkedList<Usuario> usuarios;

	private int puerto;
	private Trufa trufa;
	private Mensaje msj = null;
	private DatagramPacket pack;
	private boolean agregarUsuario = false;
	
	public Comunicacion() {
		puerto = 5100;
		trufa = new Trufa();
		usuarios = new LinkedList<Usuario>();

		try {
			socket = new DatagramSocket(puerto);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		while (true) {
			try {
				recibir();
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void enviar(String o, int puerto, InetAddress direccion) {
		byte[] datos = o.getBytes();

		try {
			DatagramPacket enviar = new DatagramPacket(datos, datos.length, direccion, puerto);
			socket.send(enviar);
			pack = null;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void recibir() {
		byte[] buzon = new byte[1024];

		pack = new DatagramPacket(buzon, buzon.length);

		try {
			System.out.println("esperando");
			socket.receive(pack);
			int purtoTemporal = pack.getPort();
			InetAddress direccionTemporal = pack.getAddress();
			System.out.println("recibi");
			
			if (pack.getData() != null) {
				msj = deserializar(pack.getData());
				System.out.println(msj.getTipo());
			}

			if (msj.getTipo().equals("Registro")) {
				if (usuarios.size() < 1) {
					agregarUsuario = true;
				}else{
					agregarUsuario = false;
				}
				int temp = -1;
				for (int i = 0; i < usuarios.size(); i++) {
					Usuario u = usuarios.get(i);

					if (u.getUsuario().equals(msj.getNombre())) {
						temp = i;
						agregarUsuario = false;
						System.out.println("Ya estoy");
					} else if (!u.getUsuario().equals(msj.getNombre()) && temp == -1) {
						agregarUsuario = true;
						System.out.println("soy nuevo");
					}
				}
				if (agregarUsuario == true) {
					usuarios.add(new Usuario(msj.getNombre(), msj.getContra()));
					agregarUsuario = false;
				}
				System.out.println("cantidadUsuarios: " + usuarios.size());

			}
			
			if (msj.getTipo().equals("Ingreso")) {
				if (usuarios.size()<1) {
					enviar("false", purtoTemporal, direccionTemporal);
					System.out.println("no hay usuarios registrados");
				}
				for (Usuario u : usuarios) {
					if (u.getUsuario().equals(msj.getNombre()) && u.getContra().equals(msj.getContra())) {
						enviar("true", purtoTemporal, direccionTemporal);
						System.out.println("Registrado");
					} else {
						enviar("false", purtoTemporal, direccionTemporal);
						System.out.println("No registrado");
					}
				}
			}
			
			trufa.tipoTrufa(msj.getTipo());
			System.out.println("mi trufa es" + " " + msj.getTipo());
			

			if (trufa.getPrecio() != null) {
				System.out.println("precio es" + " " + trufa.getPrecio());
				enviar(trufa.getPrecio(), purtoTemporal, direccionTemporal);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Mensaje deserializar(byte[] bytes) {
		ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
		Mensaje aux = null;
		try {
			ObjectInputStream is = new ObjectInputStream(byteArray);
			aux = (Mensaje) is.readObject();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aux;
	}
}
