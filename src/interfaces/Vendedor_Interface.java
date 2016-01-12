package interfaces;

import jade.core.Agent;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;

import behaviour.Informar;
import dataModel.EstadoVendedor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vendedor_Interface extends JFrame {
	static EstadoVendedor estadoVendedor;
	static String nome;
	Agent agent;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vendedor_Interface frame = new Vendedor_Interface(estadoVendedor,nome);
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
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Vendedor_Interface(final EstadoVendedor estadoVendedor, String nome) {
		this.estadoVendedor = estadoVendedor;
		this.nome = nome;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(6, 54, 248, 208);
		panel.add(textArea);
		
		JLabel lblVendedor = new JLabel(nome);
		lblVendedor.setBounds(20, 17, 140, 16);
		panel.add(lblVendedor);
		
		
		
		final JTextArea consola = new JTextArea(); 
        consola.setEditable(false); 
        consola.setLineWrap(true); 
        consola.setBounds(266, 54, 168, 208); 
        JScrollPane scrollpane = new JScrollPane(consola); 
        scrollpane.setBounds(266, 51, 172, 213); 
        panel.add(scrollpane); 
		
		
		JButton btnLigar = new JButton("Ligar");
		btnLigar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agent.addBehaviour(new Informar(estadoVendedor.getStock(), estadoVendedor,textArea,consola));
				
			}
		});
		btnLigar.setBounds(156, 12, 117, 29);
		panel.add(btnLigar);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				estadoVendedor.setStock();
				consola.setText(null);
				
			}
		});
		btnNewButton.setBounds(287, 13, 117, 29);
		panel.add(btnNewButton);
	
		
	}

}
