package MVC_Ejercicio18.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField textFieldUsuario;
	public JTextField textFieldContrasenia;
	public JTextField textFieldNombre;
	public JTextField textFieldLocalidad;
	public JTable table;
	public JTable tableResultados;
	public DefaultTableModel modeloTBbl = new DefaultTableModel();
	public JButton btnIniciarSesion;
	public JButton btnModificar;
	public JButton btnBorrar;
	public JButton btnGuardar;
	public JButton btnListar;
	public JButton btnNuevo;

	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Conectar");
		menuBar.add(mnNewMenu);
		
		JMenu mnConectarBD = new JMenu("Conectar ddbb");
		mnNewMenu.add(mnConectarBD);
		
		JMenuItem mntmOracle = new JMenuItem("Oracle");
		mnConectarBD.add(mntmOracle);
		
		JMenuItem mntmSQL = new JMenuItem("MySQL");
		mnConectarBD.add(mntmSQL);
		
		JMenuItem mntmSQLite = new JMenuItem("SQLite");
		mnConectarBD.add(mntmSQLite);
		
		JMenuItem mtnIniciarSesion = new JMenuItem("Iniciar sesión");
		mnNewMenu.add(mtnIniciarSesion);
		
		JMenu mnNewMenu_1 = new JMenu("Salir");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmDesconectar = new JMenuItem("Desconectar");
		mnNewMenu_1.add(mntmDesconectar);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnNewMenu_1.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(36, 61, 238, 180);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(10, 33, 93, 14);
		panel.add(lblNewLabel);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(113, 30, 115, 20);
		panel.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contrasena");
		lblContrasena.setBounds(10, 70, 93, 14);
		panel.add(lblContrasena);
		
		textFieldContrasenia = new JTextField();
		textFieldContrasenia.setColumns(10);
		textFieldContrasenia.setBounds(113, 67, 115, 20);
		panel.add(textFieldContrasenia);
		
		btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setBounds(58, 119, 121, 23);
		panel.add(btnIniciarSesion);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(284, 61, 356, 367);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(30, 52, 93, 14);
		panel_1.add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(133, 49, 115, 20);
		textFieldNombre.setColumns(10);
		panel_1.add(textFieldNombre);
		
		JLabel lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(30, 89, 93, 14);
		panel_1.add(lblLocalidad);
		
		textFieldLocalidad = new JTextField();
		textFieldLocalidad.setBounds(133, 86, 115, 20);
		textFieldLocalidad.setColumns(10);
		panel_1.add(textFieldLocalidad);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(263, 48, 93, 23);
		panel_1.add(btnNuevo);
		
		btnListar = new JButton("Listar");
		btnListar.setBounds(263, 85, 93, 23);
		panel_1.add(btnListar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(263, 127, 93, 23);
		panel_1.add(btnGuardar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(155, 127, 93, 23);
		panel_1.add(btnBorrar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(43, 127, 93, 23);
		panel_1.add(btnModificar);
		
		table = new JTable();
		table.setBounds(155, 296, 61, 0);
		panel_1.add(table);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 161, 336, 195);
		tableResultados = new JTable();
		tableResultados.setBounds(10, 161, 336, 195);
		tableResultados.setModel(modeloTBbl);
		Object[] identificadores = { "Id","Nombre","Localidad"};
		modeloTBbl.setColumnIdentifiers(identificadores);
		scrollPane.setViewportView(tableResultados);
		//panel_1.add(tableResultados);
		
		panel_1.add(scrollPane);
		setVisible(true);
	}
}
