package Servidor_Cliente;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaCliente extends JFrame {

	private JPanel contentPane;
	private JLabel lblDato;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCliente frame = new VentanaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
			}
			}
		});
	}  

	/**
	 * Create the frame.
	 */
	public VentanaCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDato = new JLabel("New label");
		lblDato.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDato.setBounds(142, 86, 116, 36);
		contentPane.add(lblDato);
	}
	
	public JLabel getLblDato() {
		return lblDato;
	}
	
}
