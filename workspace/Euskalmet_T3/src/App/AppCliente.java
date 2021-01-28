package App;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import Servidor_Cliente.Cliente;

@SuppressWarnings("serial")
public class AppCliente extends JFrame implements ActionListener {
	
	private Cliente hC;
	
	private List<List<Object>> arrays;
	/* Contenido de objecto 'arrays': 
		- arrays.get(0) -> Provincias.
		- arrays.get(1) -> Municipios de Bizkaia.
		- arrays.get(2) -> Municipios de Gipuzkoa.
		- arrays.get(3) -> Municipios de Araba.
		- arrays.get(4) -> Espacios Naturales de Bizkaia.
		- arrays.get(5) -> Espacios Naturales de Gipuzkoa.
		- arrays.get(6) -> Espacios Naturales de Araba.
		- arrays.get(7) -> Historico por Municipios.
	 */
	
	private JPanel JPnl_Menu;
	private JButton P1_btnMunicipios, P1_btnEspaciosN, P1_btnSalir;
	
	private JPanel JPnl_Lista;
	private String P2_tipoLista;
	private JLabel P2_lblProvincias, P2_lblLista;
	private JComboBox<String> P2_cmbxFiltros, P2_cmbxProvincias;
	private JList<String> P2_listLista;
	private JButton P2_btnAceptar, P2_btnAtras, P2_btnSalir;
	
	private ArrayList<Datos> ayDatos = new ArrayList<Datos>();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppCliente frame = new AppCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public AppCliente() {
		hC = new Cliente();
		hC.start();
		arrays = hC.getResultado();
		
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
		JLabel P2_lblFiltros = new JLabel("Filtros:");
		P2_lblFiltros.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_lblFiltros.setBounds(45, 23, 100, 17);
		JPnl_Lista.add(P2_lblFiltros);
		
		P2_cmbxFiltros = new JComboBox<String>();
		P2_cmbxFiltros.setToolTipText("Selecciona filtro");
		P2_cmbxFiltros.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P2_cmbxFiltros.setBounds(45, 48, 157, 31);
		P2_cmbxFiltros.addActionListener(this);
		JPnl_Lista.add(P2_cmbxFiltros);
		
		P2_lblProvincias = new JLabel("Provincias:");
		P2_lblProvincias.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_lblProvincias.setBounds(230, 20, 100, 17);
		JPnl_Lista.add(P2_lblProvincias);
		
		P2_cmbxProvincias = new JComboBox<String>(Util.cmbxProvincias(arrays.get(0)));
		P2_cmbxProvincias.setSelectedIndex(0);
		P2_cmbxProvincias.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P2_cmbxProvincias.setMaximumRowCount(3);
		P2_cmbxProvincias.setToolTipText("Selecciona provincia");
		P2_cmbxProvincias.setBounds(230, 48, 157, 31);
		P2_cmbxProvincias.addActionListener(this);
		JPnl_Lista.add(P2_cmbxProvincias);
		
		P2_lblLista = new JLabel();
		P2_lblLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_lblLista.setBounds(45, 88, 342, 17);
		JPnl_Lista.add(P2_lblLista);
		
		P2_listLista = new JList<String>();
		P2_listLista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P2_listLista.setBounds(45, 116, 342, 227);
		
		JScrollPane P2_scrollPane = new JScrollPane(P2_listLista);
		P2_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		P2_scrollPane.setBounds(P2_listLista.getBounds());
		JPnl_Lista.add(P2_scrollPane);
		
		P2_btnAceptar = new JButton("ACEPTAR");
		P2_btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnAceptar.setBounds(161, 361, 110, 31);
		P2_btnAceptar.addActionListener(this);
		JPnl_Lista.add(P2_btnAceptar);
		
		P2_btnAtras = new JButton("ATRAS");
		P2_btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnAtras.setBounds(45, 364, 94, 25);
		P2_btnAtras.addActionListener(this);
		JPnl_Lista.add(P2_btnAtras);
		
		P2_btnSalir = new JButton("SALIR");
		P2_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P2_btnSalir.setBounds(294, 364, 93, 25);
		P2_btnSalir.addActionListener(this);
		JPnl_Lista.add(P2_btnSalir);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (JPnl_Menu.isVisible()) {
			actionMenu(e);
		} else if (JPnl_Lista.isVisible()) {
			actionLista(e);
		}
	}
	
	private void actionMenu(ActionEvent e) {
		if (e.getSource() == P1_btnMunicipios) {
			JPnl_Menu.setVisible(false);
			JPnl_Lista.setVisible(true);
			P2_tipoLista = "municipios";
			String[] filtros = {"Provincias"};
			P2_cmbxFiltros.setModel(new DefaultComboBoxModel<String>(filtros));
			P2_cmbxFiltros.setSelectedIndex(0);
			P2_cmbxProvincias.setSelectedIndex(0);
			P2_lblLista.setText("Municipios:");
			P2_listLista.setListData(Util.lista(arrays.get(1), P2_tipoLista));
			P2_listLista.ensureIndexIsVisible(0);
			P2_listLista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else if (e.getSource() == P1_btnEspaciosN) {
			JPnl_Menu.setVisible(false);
			JPnl_Lista.setVisible(true);
			P2_tipoLista = "espaciosN";
			String[] filtros = {"Provincias","Playas"};
			P2_cmbxFiltros.setModel(new DefaultComboBoxModel<String>(filtros));
			P2_cmbxFiltros.setSelectedIndex(0);
			P2_cmbxProvincias.setSelectedIndex(0);
			P2_cmbxFiltros.addItem("Playas");
			P2_lblLista.setText("Espacios naturales:");
			P2_listLista.setListData(Util.lista(arrays.get(4), P2_tipoLista));
			P2_listLista.ensureIndexIsVisible(0);
			P2_listLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		} else {
			System.exit(0);
		}
	}
	
	private void actionLista(ActionEvent e) {
		if (e.getSource() == P2_cmbxFiltros) {
			if (P2_tipoLista.equals("espaciosN")) {
				if (P2_cmbxProvincias.getSelectedItem().toString().equals("Bizkaia")) {
					P2_listLista.setListData(Util.lista(arrays.get(4), P2_tipoLista));
				} else if (P2_cmbxProvincias.getSelectedItem().toString().equals("Gipuzkoa")) {
					P2_listLista.setListData(Util.lista(arrays.get(5), P2_tipoLista));
				} else if (P2_cmbxProvincias.getSelectedItem().toString().equals("Araba")) {
					P2_listLista.setListData(Util.lista(arrays.get(6), P2_tipoLista));
				}
			}
			
			P2_listLista.ensureIndexIsVisible(0);
		} else if (e.getSource() == P2_cmbxProvincias) {
			if (P2_tipoLista.equals("municipios")) {
				if (P2_cmbxProvincias.getSelectedItem().toString().equals("Bizkaia")) {
					P2_listLista.setListData(Util.lista(arrays.get(1), P2_tipoLista));
				} else if (P2_cmbxProvincias.getSelectedItem().toString().equals("Gipuzkoa")) {
					P2_listLista.setListData(Util.lista(arrays.get(2), P2_tipoLista));
				} else if (P2_cmbxProvincias.getSelectedItem().toString().equals("Araba")) {
					P2_listLista.setListData(Util.lista(arrays.get(3), P2_tipoLista));
				}
			} else if (P2_tipoLista.equals("espaciosN")) {
				if (P2_cmbxProvincias.getSelectedItem().toString().equals("Bizkaia")) {
					P2_listLista.setListData(Util.lista(arrays.get(4), P2_tipoLista));
				} else if (P2_cmbxProvincias.getSelectedItem().toString().equals("Gipuzkoa")) {
					P2_listLista.setListData(Util.lista(arrays.get(5), P2_tipoLista));
				} else if (P2_cmbxProvincias.getSelectedItem().toString().equals("Araba")) {
					P2_listLista.setListData(Util.lista(arrays.get(6), P2_tipoLista));
				}
			}
			
			P2_listLista.ensureIndexIsVisible(0);
		} else if (e.getSource() == P2_btnAceptar) {
			this.setVisible(false);
			
			if (P2_tipoLista.equals("municipios")) {
				List<String> lMuni = P2_listLista.getSelectedValuesList();
				for (int i = 0; i < lMuni.size(); i++) {
					Datos datos = new Datos(this, lMuni.get(i));
					datos.setVisible(true);
					ayDatos.add(datos);
				}
			} else if (P2_tipoLista.equals("espaciosN")) {
				Datos datos = new Datos(this, P2_listLista.getSelectedValue());
				datos.setVisible(true);
				ayDatos.add(datos);
			}
		} else if (e.getSource() == P2_btnAtras) {
			JPnl_Lista.setVisible(false);
			JPnl_Menu.setVisible(true);
			P2_tipoLista = "";
		} else {
			System.exit(0);
		}
	}
	
	public List<List<Object>> getArrays() {
		return arrays;
	}
	
	public String getTipo() {
		return P2_tipoLista;
	}
	
	public String getProv() {
		return P2_cmbxProvincias.getSelectedItem().toString();
	}
	
	public ArrayList<Datos> getAyDatos() {
		return ayDatos;
	}
}
