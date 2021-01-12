import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

	private final int PUERTO = 5000;
	private final String IP = "127.0.0.1";
	private String resultado ="";

	public void iniciar() {
		Socket cliente = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream salida = null;
		try {
			cliente = new Socket(IP, PUERTO);
			System.out.println("Conexi�n realizada con servidor");
			entrada = new ObjectInputStream(cliente.getInputStream());
			salida = new ObjectOutputStream (cliente.getOutputStream());
			
			salida.writeObject("SELECT nombre from usuario");
			
			resultado = entrada.readObject().toString();
			
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			try {
				if (cliente != null)
					cliente.close();
				if (entrada != null)
					entrada.close();
				if (salida != null)
					salida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Fin cliente");
		}
	}
	public static void main(String[] args) {
		Cliente c1 = new Cliente();
		c1.iniciar();
	}
	public String getResultado() {
		return resultado;
	}
}
