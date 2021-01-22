package Servidor_Cliente;

import java.util.List;

import App.AppCliente;

public class Cliente extends Thread {
	
	private AppCliente frame;
	private HiloCliente hC;
	
	public void run() {
		frame = new AppCliente();
		frame.setVisible(true);
		hC = new HiloCliente(frame);
		hC.run();
		
		while (hC.isAlive()) {
			List<Object> lista = hC.getResultado();
			if (lista != null) {
				frame.setLista(lista);
			}
		}
	}
	
}
