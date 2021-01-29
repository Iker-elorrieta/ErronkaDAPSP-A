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
	/* Contenido de objeto 'arrays': 
		- arrays.get(0) -> Todas las Provincias.
		- arrays.get(1) -> Municipios de Bizkaia.
		- arrays.get(2) -> Municipios de Gipuzkoa.
		- arrays.get(3) -> Municipios de Araba.
		- arrays.get(4) -> Espacios Naturales de Bizkaia.
		- arrays.get(5) -> Espacios Naturales de Gipuzkoa.
		- arrays.get(6) -> Espacios Naturales de Araba.
		- arrays.get(7) -> Historico por Municipios.
		- arrays.get(8) -> Todos los Espacios Naturales.
		- arrays.get(9) -> Todos los Municipios.
	 */
	
	private ArrayList<ArrayList<Object>> visitasMuni;
	private ArrayList<ArrayList<Object>> visitasEspN;
	
	private JPanel JPnl_Menu;
	private JButton P1_btnMunicipios, P1_btnEspaciosN, P1_btnTop, P1_btnSalir;
	
	private JPanel JPnl_Lista;
	private String P2_tipoLista = "municipios";
	private JLabel P2_lblProvincias, P2_lblLista;
	private JComboBox<String> P2_cmbxFiltros, P2_cmbxProvincias;
	private JList<String> P2_listLista;
	private JButton P2_btnAceptar, P2_btnAtras, P2_btnSalir;
	
	private ArrayList<Datos> ayDatos = new ArrayList<Datos>();
	
	private JPanel JPnl_Top;
	private JLabel P3_lblLista;
	private JComboBox<String> P3_cmbxFiltros, P3_cmbxTop;
	private JList<String> P3_listLista;
	private JButton P3_btnAtras, P3_btnSalir;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppCliente frame = new AppCliente();
					frame.setVisible(true);
					System.out.println(" [App] >> Aplicación iniciada. \n");
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
		
		visitasMuni = Util.visitas(arrays.get(9), "municipios");
		visitasEspN = Util.visitas(arrays.get(8), "espaciosN");
		
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
		PnlTop();
		getContentPane().add(JPnl_Top);
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
		P1_btnMunicipios.setBounds(106, 29, 218, 57);
		P1_btnMunicipios.addActionListener(this);
		JPnl_Menu.add(P1_btnMunicipios);

		P1_btnEspaciosN = new JButton("ESPACIOS N.");
		P1_btnEspaciosN.setFont(new Font("Tahoma", Font.BOLD, 24));
		P1_btnEspaciosN.setBounds(106, 120, 218, 57);
		P1_btnEspaciosN.addActionListener(this);
		JPnl_Menu.add(P1_btnEspaciosN);

		P1_btnTop = new JButton("TOP");
		P1_btnTop.setFont(new Font("Tahoma", Font.BOLD, 24));
		P1_btnTop.setBounds(106, 215, 218, 57);
		P1_btnTop.addActionListener(this);
		JPnl_Menu.add(P1_btnTop);

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
	
	private void PnlTop() {
		JPnl_Top = new JPanel();
		JPnl_Top.setBounds(0, 0, 434, 411);
		JPnl_Top.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPnl_Top.setLayout(null);
		JPnl_Top.setVisible(false);

		top();
	}
	
	private void top() {
		JLabel P3_lblFiltros = new JLabel("Filtros:");
		P3_lblFiltros.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_lblFiltros.setBounds(45, 23, 100, 17);
		JPnl_Top.add(P3_lblFiltros);

		String[] filtros = {"Municipios","Espacios Naturales"};
		P3_cmbxFiltros = new JComboBox<String>(filtros);
		P3_cmbxFiltros.setSelectedIndex(0);
		P3_cmbxFiltros.setToolTipText("Selecciona filtro");
		P3_cmbxFiltros.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P3_cmbxFiltros.setBounds(45, 48, 157, 31);
		P3_cmbxFiltros.addActionListener(this);
		JPnl_Top.add(P3_cmbxFiltros);

		JLabel P3_lblTop = new JLabel("TOP:");
		P3_lblTop.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_lblTop.setBounds(230, 23, 100, 17);
		JPnl_Top.add(P3_lblTop);

		String[] top = {"TOP 5","TOP 10","TOP 20"};
		P3_cmbxTop = new JComboBox<String>(top);
		P3_cmbxTop.setSelectedIndex(0);
		P3_cmbxTop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P3_cmbxTop.setMaximumRowCount(3);
		P3_cmbxTop.setToolTipText("Selecciona top");
		P3_cmbxTop.setBounds(230, 48, 157, 31);
		P3_cmbxTop.addActionListener(this);
		JPnl_Top.add(P3_cmbxTop);

		P3_lblLista = new JLabel();
		P3_lblLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_lblLista.setBounds(45, 88, 342, 17);
		JPnl_Top.add(P3_lblLista);

		P3_listLista = new JList<String>();
		P3_listLista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P3_listLista.setBounds(45, 116, 342, 227);

		JScrollPane P3_scrollPane = new JScrollPane(P3_listLista);
		P3_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		P3_scrollPane.setBounds(P3_listLista.getBounds());
		JPnl_Top.add(P3_scrollPane);

		P3_btnAtras = new JButton("ATRAS");
		P3_btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnAtras.setBounds(45, 364, 94, 25);
		P3_btnAtras.addActionListener(this);
		JPnl_Top.add(P3_btnAtras);

		P3_btnSalir = new JButton("SALIR");
		P3_btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		P3_btnSalir.setBounds(294, 364, 93, 25);
		P3_btnSalir.addActionListener(this);
		JPnl_Top.add(P3_btnSalir);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (JPnl_Menu.isVisible()) {
			actionMenu(e);
		} else if (JPnl_Lista.isVisible()) {
			actionLista(e);
		} else if (JPnl_Top.isVisible()) {
			actionTop(e);
		}
	}
	
	private void actionMenu(ActionEvent e) {
		if (e.getSource() == P1_btnMunicipios) {
			JPnl_Menu.setVisible(false);
			JPnl_Lista.setVisible(true);

			String[] filtros = {"Provincias"};
			P2_cmbxFiltros.setModel(new DefaultComboBoxModel<String>(filtros));
			P2_cmbxFiltros.setSelectedIndex(0);

			P2_cmbxProvincias.setSelectedIndex(0);

			P2_tipoLista = "municipios";
			P2_lblLista.setText("Municipios:");
			P2_listLista.setListData(Util.lista(arrays.get(1), P2_tipoLista));
			P2_listLista.ensureIndexIsVisible(0);
			P2_listLista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else if (e.getSource() == P1_btnEspaciosN) {
			JPnl_Menu.setVisible(false);
			JPnl_Lista.setVisible(true);

			String[] filtros = {"Provincias","Playas"};
			P2_cmbxFiltros.setModel(new DefaultComboBoxModel<String>(filtros));
			P2_cmbxFiltros.setSelectedIndex(0);

			P2_cmbxProvincias.setSelectedIndex(0);

			P2_tipoLista = "espaciosN";
			P2_lblLista.setText("Espacios naturales:");
			P2_listLista.setListData(Util.lista(arrays.get(4), P2_tipoLista));
			P2_listLista.ensureIndexIsVisible(0);
			P2_listLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		} else {
			salir();
		}
	}
	
	private void actionLista(ActionEvent e) {
		if (e.getSource() == P2_cmbxFiltros) {
			if (P2_tipoLista.equals("municipios")) {
				P2_cmbxProvincias.setSelectedIndex(0);
				P2_listLista.setListData(Util.lista(arrays.get(1), P2_tipoLista));
			} else if (P2_tipoLista.equals("espaciosN")) {
				if (P2_cmbxFiltros.getSelectedItem().toString().equals("Provincias")) {
					P2_lblProvincias.setVisible(true);
					P2_cmbxProvincias.setVisible(true);
					P2_cmbxProvincias.setSelectedIndex(0);
					P2_listLista.setListData(Util.lista(arrays.get(4), P2_tipoLista));
				} else if (P2_cmbxFiltros.getSelectedItem().toString().equals("Playas")) {
					P2_lblProvincias.setVisible(false);
					P2_cmbxProvincias.setVisible(false);
					P2_listLista.setListData(Util.lista(arrays.get(8), "Playas"));
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
					for (int j = 0; j < visitasMuni.size(); j++) {
						if (((String) visitasMuni.get(j).get(0)).equals(lMuni.get(i))) {
							visitasMuni.get(j).set(1, ((int) visitasMuni.get(j).get(1)) + 1);
							break;
						}
					}
					Datos datos = new Datos(this, lMuni.get(i));
					datos.setVisible(true);
					ayDatos.add(datos);
				}
			} else if (P2_tipoLista.equals("espaciosN")) {
				for (int j = 0; j < visitasEspN.size(); j++) {
					if (((String) visitasEspN.get(j).get(0)).equals(P2_listLista.getSelectedValue())) {
						visitasEspN.get(j).set(1, ((int) visitasEspN.get(j).get(1)) + 1);
						break;
					}
				}
				Datos datos = new Datos(this, P2_listLista.getSelectedValue());
				datos.setVisible(true);
				ayDatos.add(datos);
			}
		} else if (e.getSource() == P2_btnAtras) {
			JPnl_Lista.setVisible(false);
			JPnl_Menu.setVisible(true);
			P2_tipoLista = "";
		} else {
			salir();
		}
	}
	
	private void actionTop(ActionEvent e) {
		if (e.getSource() == P3_cmbxFiltros) {
			if (P3_cmbxFiltros.getSelectedItem().toString().equals("Municipios")) {
				
			} else if (P3_cmbxFiltros.getSelectedItem().toString().equals("Espacios Naturales")) {
				
			}

			P3_listLista.ensureIndexIsVisible(0);
		} else if (e.getSource() == P3_cmbxTop) {
			

			P3_listLista.ensureIndexIsVisible(0);
		} else if (e.getSource() == P2_btnAtras) {
			JPnl_Lista.setVisible(false);
			JPnl_Menu.setVisible(true);
			P2_tipoLista = "";
		} else {
			salir();
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
	
	public void salir() {
		hC.cerrar();
		System.out.println(" [App] >> Aplicación finalizada.");
		System.exit(0);
	}
	
}
