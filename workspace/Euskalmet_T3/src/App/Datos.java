package App;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import java.awt.Color;

@SuppressWarnings("serial")
public class Datos extends JFrame implements ActionListener {
	
	private AppCliente App;
	private List<List<Object>> arrays;
	
	private JPanel JPnl_Info;
	private String nombre, tipo;
	private ArrayList<String> municipios = new ArrayList<String>();
	private JLabel P1_lblNombre, P1_lblMsg;
	private JTextArea P1_txtInfo;
	private JButton P1_btnHistorico, P1_btnCerrar, P1_btnSalir;
	
	private JPanel JPnl_Historico;
	private JComboBox<String> P2_cmbxEstaciones;
	private JTextArea P2_txtHistorico;
	private JButton P2_btnAtras, P2_btnCerrar, P2_btnSalir;
	
	private boolean top, listo = false;
	
	public Datos(AppCliente App, String nombre, boolean top) {
		this.App = App;
		this.arrays = App.getArrays();
		this.nombre = nombre;
		this.tipo = App.getTipo();
		this.top = top;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		getContentPane().setLayout(null);
		
		frame();
	}
	
	private void frame() {
		PnlInfo();
		getContentPane().add(JPnl_Info);
		PnlHistorico();
		getContentPane().add(JPnl_Historico);
	}
	
	private void PnlInfo() {
		JPnl_Info = new JPanel();
		JPnl_Info.setBounds(0, 0, 684, 461);
		JPnl_Info.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPnl_Info.setLayout(null);
		JPnl_Info.setVisible(true);
		
		info();
	}
	
	private void info() {
		P1_lblNombre = new JLabel(nombre);
		P1_lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		P1_lblNombre.setFont(new Font("Tahoma", Font.BOLD, 24));
		P1_lblNombre.setBounds(53, 11, 572, 29);
		JPnl_Info.add(P1_lblNombre);
		
		P1_txtInfo = new JTextArea();
		P1_txtInfo.setLineWrap(true);
		P1_txtInfo.setWrapStyleWord(true);
		P1_txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 11));
		P1_txtInfo.setBounds(45, 66, 342, 248);
		setTxtInfo();
		
		JScrollPane P1_scrollPane = new JScrollPane(P1_txtInfo);
		P1_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		P1_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		P1_scrollPane.setBounds(new Rectangle(53, 51, 572, 318));
		JPnl_Info.add(P1_scrollPane);
		
		P1_lblMsg = new JLabel("");
		P1_lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		P1_lblMsg.setForeground(Color.RED);
		P1_lblMsg.setFont(new Font("Tahoma", Font.BOLD, 14));
		P1_lblMsg.setBounds(157, 380, 364, 21);
		JPnl_Info.add(P1_lblMsg);
		
		P1_btnHistorico = new JButton("HISTORICO");
		P1_btnHistorico.setFont(new Font("Tahoma", Font.BOLD, 14));
		P1_btnHistorico.setBounds(271, 412, 135, 28);
		P1_btnHistorico.addActionListener(this);
		JPnl_Info.add(P1_btnHistorico);
		
		P1_btnCerrar = new JButton("CERRAR");
		P1_btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		P1_btnCerrar.setBounds(53, 414, 106, 25);
		P1_btnCerrar.addActionListener(this);
		JPnl_Info.add(P1_btnCerrar);
		
		P1_btnSalir = new JButton("SALIR");
		P1_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P1_btnSalir.setBounds(519, 414, 106, 25);
		P1_btnSalir.addActionListener(this);
		JPnl_Info.add(P1_btnSalir);
	}
	
	private void PnlHistorico() {
		JPnl_Historico = new JPanel();
		JPnl_Historico.setBounds(0, 0, 684, 461);
		JPnl_Historico.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPnl_Historico.setLayout(null);
		JPnl_Historico.setVisible(false);
		
		historico();
	}
	
	private void historico() {
		P2_cmbxEstaciones = new JComboBox<String>(Util.cmbxEstaciones(arrays.get(7), municipios, this));
		if (P2_cmbxEstaciones.getItemCount() != 0) {
			P2_cmbxEstaciones.setSelectedIndex(0);
		}
		P2_cmbxEstaciones.setBounds(145, 21, 385, 34);
		P2_cmbxEstaciones.addActionListener(this);
		JPnl_Historico.add(P2_cmbxEstaciones);
		
		P2_txtHistorico = new JTextArea();
		P2_txtHistorico.setLineWrap(true);
		P2_txtHistorico.setWrapStyleWord(true);
		P2_txtHistorico.setFont(new Font("Monospaced", Font.PLAIN, 11));
		P2_txtHistorico.setBounds(45, 66, 342, 248);
		
		JScrollPane P2_scrollPane = new JScrollPane(P2_txtHistorico);
		P2_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		P2_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		P2_scrollPane.setBounds(new Rectangle(53, 66, 572, 332));
		JPnl_Historico.add(P2_scrollPane);
		
		P2_btnAtras = new JButton("ATRAS");
		P2_btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnAtras.setBounds(53, 414, 106, 25);
		P2_btnAtras.addActionListener(this);
		JPnl_Historico.add(P2_btnAtras);
		
		P2_btnCerrar = new JButton("CERRAR");
		P2_btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnCerrar.setBounds(285, 414, 106, 25);
		P2_btnCerrar.addActionListener(this);
		JPnl_Historico.add(P2_btnCerrar);
		
		P2_btnSalir = new JButton("SALIR");
		P2_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnSalir.setBounds(519, 414, 106, 25);
		P2_btnSalir.addActionListener(this);
		JPnl_Historico.add(P2_btnSalir);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (JPnl_Info.isVisible()) {
			actionInfo(e);
		} else if (JPnl_Historico.isVisible()) {
			actionHistorico(e);
		}
	}
	
	private void actionInfo(ActionEvent e) {
		if (e.getSource() == P1_btnHistorico) {
			if (listo) {
				if (P2_cmbxEstaciones.getItemCount() != 0) {
					JPnl_Info.setVisible(false);
					JPnl_Historico.setVisible(true);
					P2_txtHistorico.setText(Util.historico(arrays.get(7), P2_cmbxEstaciones.getSelectedItem().toString()));
					P2_txtHistorico.setCaretPosition(0);
				} else {
					P1_lblMsg.setText(">> Histórico no disponible <<");
					new Timer(1000, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							P1_lblMsg.setVisible(false);
						}}).start();
				}
			} else {
				P1_lblMsg.setText(">> Datos aún no cargados <<");
				new Timer(1000, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						P1_lblMsg.setVisible(false);
					}}).start();
			}
		} else if (e.getSource() == P1_btnCerrar) {
			if (top) {
				App.actualizarTop();
			}
			App.getAyDatos().remove(this);
			if (App.getAyDatos().size() == 0) {
				App.setVisible(true);
			}
			
			this.dispose();
		} else {
			App.salir();
		}
	}
	
	private void actionHistorico(ActionEvent e) {
		if (e.getSource() == P2_cmbxEstaciones) {
			P2_txtHistorico.setText(Util.historico(arrays.get(7), P2_cmbxEstaciones.getSelectedItem().toString()));
			P2_txtHistorico.setCaretPosition(0);
		} else if (e.getSource() == P2_btnAtras) {
			JPnl_Info.setVisible(true);
			JPnl_Historico.setVisible(false);
		} else if (e.getSource() == P2_btnCerrar) {
			if (top) {
				App.actualizarTop();
			}
			App.getAyDatos().remove(this);
			if (App.getAyDatos().size() == 0) {
				App.setVisible(true);
			}
			
			this.dispose();
		} else {
			App.salir();
		}
	}
	
	private void setTxtInfo() {
		if (tipo.equals("municipios")) {
			P1_txtInfo.setText(Util.texto(arrays.get(9), tipo, nombre));
			municipios.add(nombre);
		} else if (tipo.equals("espaciosN")) {
			P1_txtInfo.setText(Util.texto(arrays.get(8), tipo, nombre));
			municipios = Util.muniEspN(arrays.get(8), nombre);
		}
		
		P1_txtInfo.setCaretPosition(0);
	}
	
	public void listo() {
		listo = true;
	}
	
}
