package MVC_DEMO.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField textOp1;
	public JTextField textOp2;
	public JTextField textResult;
	private JLabel lblOperador;
	private JLabel lblResultado;
	public JButton btnSumar;
	public JButton btnRestar;



	/**
	 * Create the frame.
	 */
	public Vista() {
		setTitle("MVC_Calculadora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textOp1 = new JTextField();
		textOp1.setBounds(98, 26, 100, 20);
		panel.add(textOp1);
		textOp1.setColumns(10);
		
		textOp2 = new JTextField();
		textOp2.setColumns(10);
		textOp2.setBounds(98, 57, 100, 20);
		panel.add(textOp2);
		
		textResult = new JTextField();
		textResult.setColumns(10);
		textResult.setBounds(98, 90, 100, 20);
		panel.add(textResult);
		
		JLabel lblNewLabel = new JLabel("Operador 1:");
		lblNewLabel.setBounds(10, 26, 78, 14);
		panel.add(lblNewLabel);
		
		lblOperador = new JLabel("Operador 2:");
		lblOperador.setBounds(10, 60, 78, 14);
		panel.add(lblOperador);
		
		lblResultado = new JLabel("Resultado: ");
		lblResultado.setBounds(10, 93, 78, 14);
		panel.add(lblResultado);
		
		btnSumar = new JButton("Sumar");
		btnSumar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSumar.setBounds(10, 138, 89, 23);
		panel.add(btnSumar);
		
		btnRestar = new JButton("Restar");
		btnRestar.setBounds(109, 138, 89, 23);
		panel.add(btnRestar);
		setVisible(true);
	}
}
