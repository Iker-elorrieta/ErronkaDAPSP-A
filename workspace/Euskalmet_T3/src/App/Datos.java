package App;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import java.awt.Color;

@SuppressWarnings("serial")
public class Datos extends JFrame implements ActionListener {
	
	private AppCliente App;
	private Contenedor arrays;
	
	private JPanel JPnl_Info;
	private String P3_dato, tipo, prov;
	private JLabel P3_lblNombre, P3_lblMsg;
	private JTextArea P3_txtInfo;
	private JButton P3_btnHistorico, P3_btnCerrar, P3_btnSalir;
	
	private JPanel JPnl_Historico;
	private JComboBox<String> P4_cmbxEstaciones;
	private JTextArea P4_txtHistorico;
	private JButton P4_btnAtras, P4_btnCerrar, P4_btnSalir;
	
	public Datos(AppCliente App, String P3_dato) {
		this.App = App;
		this.arrays = App.getContenedor();
		this.P3_dato = P3_dato;
		this.tipo = App.getTipo();
		this.prov = App.getProv();
		
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
		P3_lblNombre = new JLabel(P3_dato);
		P3_lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		P3_lblNombre.setFont(new Font("Tahoma", Font.BOLD, 24));
		P3_lblNombre.setBounds(53, 11, 572, 29);
		JPnl_Info.add(P3_lblNombre);
		
		P3_txtInfo = new JTextArea();
		P3_txtInfo.setLineWrap(true);
		P3_txtInfo.setWrapStyleWord(true);
		P3_txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 11));
		P3_txtInfo.setBounds(45, 66, 342, 248);
		setTxtInfo();
		
		JScrollPane P3_scrollPane = new JScrollPane(P3_txtInfo);
		P3_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		P3_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		P3_scrollPane.setBounds(new Rectangle(53, 51, 572, 318));
		JPnl_Info.add(P3_scrollPane);
		
		P3_lblMsg = new JLabel("");
		P3_lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		P3_lblMsg.setForeground(Color.RED);
		P3_lblMsg.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_lblMsg.setBounds(157, 380, 364, 21);
		JPnl_Info.add(P3_lblMsg);
		
		P3_btnHistorico = new JButton("HISTORICO");
		P3_btnHistorico.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnHistorico.setBounds(271, 412, 135, 28);
		P3_btnHistorico.addActionListener(this);
		JPnl_Info.add(P3_btnHistorico);
		
		P3_btnCerrar = new JButton("CERRAR");
		P3_btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnCerrar.setBounds(53, 414, 106, 25);
		P3_btnCerrar.addActionListener(this);
		JPnl_Info.add(P3_btnCerrar);
		
		P3_btnSalir = new JButton("SALIR");
		P3_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnSalir.setBounds(519, 414, 106, 25);
		P3_btnSalir.addActionListener(this);
		JPnl_Info.add(P3_btnSalir);
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
		P4_cmbxEstaciones = new JComboBox<String>(Util.cmbxEstaciones(arrays.getAyMuniCAire(), P3_dato));
		if (P4_cmbxEstaciones.getItemCount() != 0) {
			P4_cmbxEstaciones.setSelectedIndex(0);
		}
		P4_cmbxEstaciones.setBounds(145, 21, 385, 34);
		P4_cmbxEstaciones.addActionListener(this);
		JPnl_Historico.add(P4_cmbxEstaciones);
		
		P4_txtHistorico = new JTextArea();
		P4_txtHistorico.setLineWrap(true);
		P4_txtHistorico.setWrapStyleWord(true);
		P4_txtHistorico.setFont(new Font("Monospaced", Font.PLAIN, 11));
		P4_txtHistorico.setBounds(45, 66, 342, 248);
		setTxtInfo();
		
		JScrollPane P4_scrollPane = new JScrollPane(P4_txtHistorico);
		P4_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		P4_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		P4_scrollPane.setBounds(new Rectangle(53, 66, 572, 332));
		JPnl_Historico.add(P4_scrollPane);
		
		P4_btnAtras = new JButton("ATRAS");
		P4_btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		P4_btnAtras.setBounds(53, 414, 106, 25);
		P4_btnAtras.addActionListener(this);
		JPnl_Historico.add(P4_btnAtras);
		
		P4_btnCerrar = new JButton("CERRAR");
		P4_btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		P4_btnCerrar.setBounds(285, 414, 106, 25);
		P4_btnCerrar.addActionListener(this);
		JPnl_Historico.add(P4_btnCerrar);
		
		P4_btnSalir = new JButton("SALIR");
		P4_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P4_btnSalir.setBounds(519, 414, 106, 25);
		P4_btnSalir.addActionListener(this);
		JPnl_Historico.add(P4_btnSalir);
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
		if (e.getSource() == P3_btnHistorico) {
			if (P4_cmbxEstaciones.getItemCount() != 0) {
				JPnl_Info.setVisible(false);
				JPnl_Historico.setVisible(true);
				P4_txtHistorico.setText(Util.historico(arrays.getAyMuniCAire(), P4_cmbxEstaciones.getSelectedItem().toString()));
				P4_txtHistorico.setCaretPosition(0);
			} else {
				P3_lblMsg.setText(">> Histórico no disponible <<");
			}
		} else if (e.getSource() == P3_btnCerrar) {
			App.getAyDatos().remove(this);
			if (App.getAyDatos().size() == 0) {
				App.setVisible(true);
			}
			this.dispose();
		} else {
			System.exit(0);
		}
	}
	
	private void actionHistorico(ActionEvent e) {
		if (e.getSource() == P4_cmbxEstaciones) {
			P4_txtHistorico.setText(Util.historico(arrays.getAyMuniCAire(), P4_cmbxEstaciones.getSelectedItem().toString()));
			P4_txtHistorico.setCaretPosition(0);
		} else if (e.getSource() == P4_btnAtras) {
			JPnl_Info.setVisible(true);
			JPnl_Historico.setVisible(false);
		} else if (e.getSource() == P4_btnCerrar) {
			App.getAyDatos().remove(this);
			if (App.getAyDatos().size() == 0) {
				App.setVisible(true);
			}
			this.dispose();
		} else {
			System.exit(0);
		}
	}
	
	private void setTxtInfo() {
		if (tipo.equals("municipios")) {
			if (prov.equals("Bizkaia")) {
				P3_txtInfo.setText(Util.texto(arrays.getAyMuniBizkaia(), tipo));
			} else if (prov.equals("Gipuzkoa")) {
				P3_txtInfo.setText(Util.texto(arrays.getAyMuniGipuzkoa(), tipo));
			} else if (prov.equals("Araba")) {
				P3_txtInfo.setText(Util.texto(arrays.getAyMuniAraba(), tipo));
			}
		} else if (tipo.equals("espaciosN")) {
			if (prov.equals("Bizkaia")) {
				P3_txtInfo.setText(Util.texto(arrays.getAyEspNBizkaia(), tipo));
			} else if (prov.equals("Gipuzkoa")) {
				P3_txtInfo.setText(Util.texto(arrays.getAyEspNGipuzkoa(), tipo));
			} else if (prov.equals("Araba")) {
				P3_txtInfo.setText(Util.texto(arrays.getAyEspNAraba(), tipo));
			}
		}
		P3_txtInfo.setCaretPosition(0);
	}
	
}
