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

@SuppressWarnings("serial")
public class Datos extends JFrame implements ActionListener {
	
	private AppCliente App;
	private Contenedor arrays;
	
	private JPanel JPnl_Info;
	private String P3_dato, P3_tipoDato;
	private JLabel P3_lblNombre;
	private JTextArea P3_txtInfo;
	private JButton P3_btnHistorico, P3_btnCerrar, P3_btnSalir;
	
	public Datos(AppCliente App, String P3_dato, String P3_tipoDato) {
		this.App = App;
		arrays = this.App.getContenedor();
		this.P3_dato = P3_dato;
		this.P3_tipoDato = P3_tipoDato;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		getContentPane().setLayout(null);
		
		frame();
	}
	
	private void frame() {
		PnlInfo();
		getContentPane().add(JPnl_Info);
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
		P3_lblNombre.setBounds(53, 23, 572, 29);
		JPnl_Info.add(P3_lblNombre);
		
		P3_txtInfo = new JTextArea();
		P3_txtInfo.setLineWrap(true);
		P3_txtInfo.setWrapStyleWord(true);
		P3_txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 11));
		P3_txtInfo.setBounds(45, 66, 342, 248);
		
		if (P3_tipoDato.equals("municipios")) {
			P3_txtInfo.setText(Util.texto(arrays.getSf(), P3_tipoDato, P3_dato));
		} else if (P3_tipoDato.equals("espaciosN")) {
			P3_txtInfo.setText(Util.texto(arrays.getSf(), P3_tipoDato, P3_dato));
		}
		P3_txtInfo.setCaretPosition(0);
		
		JScrollPane P3_scrollPane = new JScrollPane(P3_txtInfo);
		P3_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		P3_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		P3_scrollPane.setBounds(new Rectangle(53, 66, 572, 332));
		JPnl_Info.add(P3_scrollPane);
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == P3_btnHistorico) {
			
		} else if (e.getSource() == P3_btnCerrar) {
			App.getAyDatos().remove(this);
			this.dispose();
		} else {
			arrays.getSf().close();
			System.exit(0);
		}
	}
	
}
