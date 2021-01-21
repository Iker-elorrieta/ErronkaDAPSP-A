package App;

import java.awt.Font;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class AppCliente extends JFrame implements ActionListener, ListSelectionListener {
	private Contenedor arrays;
	
	private JPanel JPnl_Menu;
	private JButton P1_btnMunicipios, P1_btnEspaciosN, P1_btnSalir;
	
	private JPanel JPnl_Lista;
	private String P2_tipoLista;
	private JComboBox<String> P2_cmbxProvincias;
	private JList<String> P2_listLista;
	private JButton P2_btnAtras, P2_btnSalir;
	
	private JPanel JPnl_Datos;
	private String P3_dato;
	private JLabel P3_lblNombre;
	private JTextArea P3_txtInfo;
	private JButton P3_btnHistorico, P2_btnAceptar, P3_btnAtras, P3_btnSalir;
	
	public AppCliente() {
		arrays = new Contenedor();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(null);
		
		frame();
	}
	
	private void frame() {
		PnlMenu();
		getContentPane().add(JPnl_Menu);
		PnlLista();
		getContentPane().add(JPnl_Lista);
//		PnlDatos();
//		getContentPane().add(JPnl_Datos);
	}
	
	private void PnlMenu() {
		JPnl_Menu = new JPanel();
		JPnl_Menu.setBounds(0, 0, 434, 411);
		JPnl_Menu.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPnl_Menu.setLayout(null);
		JPnl_Menu.setVisible(true);
		
		menu();
	}
	
	private void menu() {
		P1_btnMunicipios = new JButton("MUNICIPIOS");
		P1_btnMunicipios.setFont(new Font("Tahoma", Font.BOLD, 24));
		P1_btnMunicipios.setBounds(106, 79, 218, 57);
		P1_btnMunicipios.addActionListener(this);
		JPnl_Menu.add(P1_btnMunicipios);
		
		P1_btnEspaciosN = new JButton("ESPACIOS N.");
		P1_btnEspaciosN.setFont(new Font("Tahoma", Font.BOLD, 24));
		P1_btnEspaciosN.setBounds(106, 196, 218, 57);
		P1_btnEspaciosN.addActionListener(this);
		JPnl_Menu.add(P1_btnEspaciosN);
		
		P1_btnSalir = new JButton("SALIR");
		P1_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P1_btnSalir.setBounds(156, 330, 120, 33);
		P1_btnSalir.addActionListener(this);
		JPnl_Menu.add(P1_btnSalir);
	}
	
	private void PnlLista() {
		JPnl_Lista = new JPanel();
		JPnl_Lista.setBounds(0, 0, 434, 411);
		JPnl_Lista.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPnl_Lista.setLayout(null);
		JPnl_Lista.setVisible(false);
		
		lista();
	}
	
	private void lista() {
		JLabel P2_lblProvincias = new JLabel("Provincias:");
		P2_lblProvincias.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_lblProvincias.setBounds(45, 11, 100, 17);
		JPnl_Lista.add(P2_lblProvincias);
		
		P2_cmbxProvincias = new JComboBox<String>(Util.cmbxProvincias(arrays.getAyProv()));
		P2_cmbxProvincias.setSelectedIndex(0);
		P2_cmbxProvincias.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P2_cmbxProvincias.setMaximumRowCount(3);
		P2_cmbxProvincias.setToolTipText("Selecciona provincia");
		P2_cmbxProvincias.setBounds(45, 46, 157, 31);
		P2_cmbxProvincias.addActionListener(this);
		JPnl_Lista.add(P2_cmbxProvincias);
		
		JLabel P2_lblLista = new JLabel("Municipios:");
		P2_lblLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_lblLista.setBounds(45, 88, 94, 17);
		JPnl_Lista.add(P2_lblLista);
		
		P2_listLista = new JList<String>();
		P2_listLista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P2_listLista.setBounds(45, 116, 342, 227);
		P2_listLista.addListSelectionListener(this);
		
		JScrollPane P2_scrollPane = new JScrollPane(P2_listLista);
		P2_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		P2_scrollPane.setBounds(P2_listLista.getBounds());
		JPnl_Lista.add(P2_scrollPane);
		

		P2_btnAceptar = new JButton("ACEPTAR");
		P2_btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnAceptar.setBounds(264, 48, 123, 31);
		JPnl_Lista.add(P2_btnAceptar);
		
		P2_btnAtras = new JButton("ATRAS");
		P2_btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnAtras.setBounds(45, 364, 106, 25);
		P2_btnAtras.addActionListener(this);
		JPnl_Lista.add(P2_btnAtras);
		
		P2_btnSalir = new JButton("SALIR");
		P2_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnSalir.setBounds(281, 364, 106, 25);
		P2_btnSalir.addActionListener(this);
		JPnl_Lista.add(P2_btnSalir);
	}
	
	private void PnlDatos() {
		JPnl_Datos = new JPanel();
		JPnl_Datos.setBounds(0, 0, 434, 411);
		JPnl_Datos.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPnl_Datos.setLayout(null);
		JPnl_Datos.setVisible(false);
		
		datos();
	}
	
	private void datos() {
		P3_lblNombre = new JLabel("");
		P3_lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		P3_lblNombre.setFont(new Font("Tahoma", Font.BOLD, 24));
		P3_lblNombre.setBounds(10, 23, 414, 29);
		JPnl_Datos.add(P3_lblNombre);
		
		P3_txtInfo = new JTextArea();
		P3_txtInfo.setLineWrap(true);
		P3_txtInfo.setWrapStyleWord(true);
		P3_txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 11));
		P3_txtInfo.setBounds(45, 66, 342, 248);
		
		JScrollPane P3_scrollPane = new JScrollPane(P3_txtInfo);
		P3_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		P3_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		P3_scrollPane.setBounds(P3_txtInfo.getBounds());
		JPnl_Datos.add(P3_scrollPane);
		
		P3_btnHistorico = new JButton("HISTORICO");
		P3_btnHistorico.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnHistorico.setBounds(149, 325, 135, 28);
		P3_btnHistorico.addActionListener(this);
		JPnl_Datos.add(P3_btnHistorico);
		
		P3_btnAtras = new JButton("ATRAS");
		P3_btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnAtras.setBounds(45, 364, 106, 25);
		P3_btnAtras.addActionListener(this);
		JPnl_Datos.add(P3_btnAtras);
		
		P3_btnSalir = new JButton("SALIR");
		P3_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnSalir.setBounds(281, 364, 106, 25);
		P3_btnSalir.addActionListener(this);
		JPnl_Datos.add(P3_btnSalir);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (JPnl_Menu.isVisible()) {
			actionMenu(e);
		} else if (JPnl_Lista.isVisible()) {
			actionLista(e);
		} else if (JPnl_Datos.isVisible()) {
			actionDatos(e);
		}
	}
	
	private void actionMenu(ActionEvent e) {
		if (e.getSource() == P1_btnMunicipios) {
			JPnl_Menu.setVisible(false);
			JPnl_Lista.setVisible(true);
			P2_tipoLista = "municipios";
			P2_cmbxProvincias.setSelectedIndex(0);
			P2_listLista.setListData(Util.lista(arrays.getSf(), arrays.getAyMuni(), P2_tipoLista, P2_cmbxProvincias.getSelectedItem().toString()));
			P2_listLista.ensureIndexIsVisible(0);
			P2_listLista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else if (e.getSource() == P1_btnEspaciosN) {
			JPnl_Menu.setVisible(false);
			JPnl_Lista.setVisible(true);
			P2_tipoLista = "espaciosN";
			P2_cmbxProvincias.setSelectedIndex(0);
			P2_listLista.setListData(Util.lista(arrays.getSf(), arrays.getAyEspN(), P2_tipoLista, P2_cmbxProvincias.getSelectedItem().toString()));
			P2_listLista.ensureIndexIsVisible(0);
			P2_listLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		} else {
			arrays.getSf().close();
			System.exit(0);;
		}
	}
	
	private void actionLista(ActionEvent e) {
		if (e.getSource() == P2_cmbxProvincias) {
			if (P2_tipoLista.equals("municipios")) {
				P2_listLista.setListData(Util.lista(arrays.getSf(), arrays.getAyMuni(), P2_tipoLista, P2_cmbxProvincias.getSelectedItem().toString()));
			} else if (P2_tipoLista.equals("espaciosN")) {
				P2_listLista.setListData(Util.lista(arrays.getSf(), arrays.getAyEspN(), P2_tipoLista, P2_cmbxProvincias.getSelectedItem().toString()));
			}
			P2_listLista.ensureIndexIsVisible(0);
		} else if (e.getSource() == P2_btnAceptar) {
			if (P2_tipoLista.equals("municipios")) {
				List<String> lMuni = P2_listLista.getSelectedValuesList();
				for (int i = 0; i < lMuni.size(); i++) {
					
				}
			} else if (P2_tipoLista.equals("espaciosN")) {
				Datos datos = new Datos(this, P2_listLista.getSelectedValue());
			}
		} else if (e.getSource() == P2_btnAtras) {
			JPnl_Lista.setVisible(false);
			JPnl_Menu.setVisible(true);
			P2_tipoLista = "";
		} else {
			arrays.getSf().close();
			System.exit(0);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JPnl_Lista.setVisible(false);
		JPnl_Datos.setVisible(true);
		P3_dato = P2_listLista.getSelectedValue();
		P3_lblNombre.setText(P3_dato);
		if (P2_tipoLista.equals("municipios")) {
			P3_txtInfo.setText(Util.texto(arrays.getSf(), arrays.getAyMuni(), P2_tipoLista, P3_dato));
		} else if (P2_tipoLista.equals("espaciosN")) {
			P3_txtInfo.setText(Util.texto(arrays.getSf(), arrays.getAyEspN(), P2_tipoLista, P3_dato));
		}
		P3_txtInfo.setCaretPosition(0);
	}
	
	private void actionDatos(ActionEvent e) {
		if (e.getSource() == P3_btnHistorico) {
			
		} else if (e.getSource() == P3_btnAtras) {
			JPnl_Datos.setVisible(false);
			JPnl_Lista.setVisible(true);
			P3_dato = "";
		} else {
			arrays.getSf().close();
			System.exit(0);
		}
	}
	
	public Contenedor getContenedor() {
		return this.arrays;
	}
	
	
	
}
