package interfaces;

import java.awt.EventQueue;

import dataModel.EstadoCliente;
import jade.core.AID;

import jade.lang.acl.ACLMessage;
import jade.core.Agent;

import behaviour.EsperaCliente;
import behaviour.IntroduzirSaldo;
import behaviour.PedeArtigo;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JSeparator;

import javax.swing.JTextArea;

public class Cliente_Interface1 extends JFrame {

	public String medestadoCliente;
	public int quantidade;
	double saldo;
	private static Agent agent;
	static String encomenda;
	String med1;
	int quantS;
	int quant;

	static EstadoCliente estadoCliente;

	// JTextArea listaestadoClientes;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {

					Cliente_Interface1 guiFrame = new Cliente_Interface1(
							estadoCliente);
					guiFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Cliente_Interface1(final EstadoCliente estadoCliente) {

		JFrame guiFrame = new JFrame();

		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Compra de Medicamentos");
		guiFrame.setSize(425, 425);

		// JOptionPane.showMessageDialog(null, "Seu saldo e " + saldo);

		// Options for the JComboBox
		final String[] medOptions = { "Xanax", "Brufen", "Gurosan" };
		final String[] numestadoCliente = { "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10" };

		// The first JPanel contains a JLabel and JCombobox
		final JPanel comboPanel = new JPanel();
		comboPanel.setLayout(null);
		// Second JPanel

		final JPanel comboPanel1 = new JPanel();
		comboPanel1.setBounds(6, 49, 413, 358);
		comboPanel.add(comboPanel1);
		JLabel comboLb1 = new JLabel("Quantidade a Comprar");
		comboLb1.setBounds(22, 55, 145, 16);

		final JComboBox num = new JComboBox(numestadoCliente);
		num.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String quantS = (String) num.getSelectedItem();
				quant = Integer.parseInt(quantS);
				estadoCliente.setQuantidade(quant);
				System.out.println(estadoCliente.getQuantidade());
			}
		});

		final JTextArea consola = new JTextArea();
		consola.setEditable(false);
		consola.setLineWrap(true);
		consola.setBounds(62, 125, 120, 16);
		JScrollPane scrollpane = new JScrollPane(consola);
		scrollpane.setBounds(22, 122, 242, 183);
		comboPanel1.add(scrollpane);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(12, 112, 382, 16);
		comboPanel1.add(separator_3);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 317, 382, 12);
		comboPanel1.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(6, 6, 26, 314);
		comboPanel1.add(separator_2);

		// JLabel saldo1 = new JLabel("Saldo: " +estadoCliente.getSaldo()+" €");
		// saldo1.setBounds(296, 10, 104, 17);
		// saldo1.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		// comboPanel.add(saldo1);
		guiFrame.getContentPane().add(comboPanel, BorderLayout.CENTER);

		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(387, 0, 20, 329);
		comboPanel1.add(separator_4);

		// make sure the JFrame is visible

		num.setBounds(192, 51, 72, 27);
		comboPanel1.setLayout(null);
		comboPanel1.add(comboLb1);
		comboPanel1.add(num);

		JLabel comboLbl = new JLabel("Nome do Medicamento:");
		comboLbl.setBounds(18, 83, 162, 17);
		comboPanel1.add(comboLbl);
		comboLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 13));

		final JComboBox med = new JComboBox(medOptions);
		med.setBounds(192, 81, 183, 27);
		comboPanel1.add(med);

		JButton btnSaldo = new JButton("€");
		btnSaldo.setBounds(22, 6, 117, 29);
		comboPanel1.add(btnSaldo);

		final JTextArea text_saldo = new JTextArea();
		text_saldo.setBounds(190, 6, 155, 27);
		comboPanel1.add(text_saldo);

		JLabel lblestadoClientedoAFarmcia = new JLabel("Saldo Disponível");
		lblestadoClientedoAFarmcia.setBounds(18, 21, 145, 16);
		comboPanel.add(lblestadoClientedoAFarmcia);

		JSeparator separator = new JSeparator();
		separator.setBounds(18, 49, 378, 12);
		comboPanel.add(separator);

		final JLabel saldo_actual = new JLabel("0");
		saldo_actual.setBounds(178, 21, 61, 16);
		comboPanel.add(saldo_actual);

		JButton btnNewButton = new JButton("Comprar");

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				encomenda = estadoCliente.getMedicamento() + "-"
						+ estadoCliente.getQuantidade() + "-"
						+ saldo_actual.getText();

				System.out.println(encomenda);

				agent.addBehaviour(new PedeArtigo(encomenda));
				agent.addBehaviour(new EsperaCliente(saldo_actual,
						estadoCliente, consola));

			}
		});

		btnNewButton.setBounds(293, 120, 90, 29);
		comboPanel1.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("PVP  :\n\n");
		lblNewLabel.setBounds(273, 164, 72, 17);
		comboPanel1.add(lblNewLabel);

		JLabel lblXana = new JLabel("Xana 2.48 €");
		lblXana.setBounds(273, 193, 102, 16);
		comboPanel1.add(lblXana);

		JLabel lblBrufen = new JLabel("Brufen 2.53€");
		lblBrufen.setBounds(273, 221, 102, 16);
		comboPanel1.add(lblBrufen);

		JLabel lblGurosan = new JLabel("Gurosan 10.35€");
		lblGurosan.setBounds(273, 249, 102, 16);
		comboPanel1.add(lblGurosan);

		btnSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saldo = Double.parseDouble(text_saldo.getText())
						+ Double.parseDouble(saldo_actual.getText());
				agent.addBehaviour(new IntroduzirSaldo(estadoCliente, saldo));
				saldo_actual.setText(Double.toString(saldo));

			}
		});
		med.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				String med1 = (String) med.getSelectedItem();
				estadoCliente.setMedicamento(med1);
				System.out.println(estadoCliente.getMedicamento());
			}
		});

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ACLMessage reset = new ACLMessage(ACLMessage.CANCEL);
				reset.addReceiver(new AID("Broker", AID.ISLOCALNAME));
				agent.send(reset);
				consola.setText(null);
				saldo_actual.setText("0");

			}
		});
		btnReset.setBounds(279, 16, 117, 29);
		comboPanel.add(btnReset);

		guiFrame.setVisible(true);
	}
}