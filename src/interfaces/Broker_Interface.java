package interfaces;

import jade.core.Agent;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import behaviour.Negociador;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Broker_Interface extends JFrame {

	private JPanel contentPane;
	private static Agent agent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Broker_Interface frame = new Broker_Interface();
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

	public Broker_Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("BROKER");
		lblNewLabel.setBounds(24, 6, 72, 34);
		panel.add(lblNewLabel);

		final JTextArea consola = new JTextArea();
		consola.setEditable(false);
		consola.setLineWrap(true);
		consola.setBounds(17, 42, 417, 210);
		JScrollPane scrollpane = new JScrollPane(consola);
		scrollpane.setBounds(17, 39, 421, 215);
		panel.add(scrollpane);
		JLabel lblLucro = new JLabel("Lucro :");
		lblLucro.setBounds(111, 15, 61, 16);
		panel.add(lblLucro);

		final JLabel label = new JLabel("0");
		label.setBounds(169, 15, 61, 16);
		panel.add(label);

		JLabel label_1 = new JLabel("\u20AC");
		label_1.setBounds(219, 15, 61, 16);
		panel.add(label_1);

		JButton btnLigar = new JButton("Ligar");
		btnLigar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consola.setText("Broker Iniciado");
				agent.addBehaviour(new Negociador(consola, label));
			}
		});
		btnLigar.setBounds(292, 10, 117, 29);
		panel.add(btnLigar);
	}
}
