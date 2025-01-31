package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JButton btnGeneraXml;
	public JButton btnDatosDB;


	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnDatosDB = new JButton("Almacena datos en DB");
		btnDatosDB.setBounds(98, 62, 246, 32);
		contentPane.add(btnDatosDB);
		
		btnGeneraXml = new JButton("Genera XML");
		btnGeneraXml.setBounds(98, 119, 246, 32);
		contentPane.add(btnGeneraXml);
		setVisible(true);
		
	}
}
