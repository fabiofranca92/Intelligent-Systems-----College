package behaviour;

import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.JTextArea;

import dataModel.EstadoVendedor;
import dataModel.Medicamento;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Informar extends CyclicBehaviour {
	HashMap<String, Medicamento> stock;
	EstadoVendedor estadoVendedor;
	JTextArea log;
	JTextArea vendas;

	public Informar(HashMap<String, Medicamento> stock, EstadoVendedor ev,
			JTextArea log, JTextArea vendas) {
		this.estadoVendedor = ev;
		this.stock = stock;
		this.log = log;
		this.vendas = vendas;

	}

	@Override
	public void action() {
		log.setText(estadoVendedor.VerStock());
		ACLMessage msg = myAgent.receive();

		if (msg != null) {

			if (msg.getPerformative() == msg.INFORM) {
				log.setText(null);
				String medicamento = msg.getContent();

				ACLMessage resposta = msg.createReply();
				msg.setPerformative(msg.INFORM);
				// resposta.addReceiver(new AID("Broker", AID.ISLOCALNAME));
				double lucro = stock.get(medicamento).getPvp()
						- stock.get(medicamento).getRevenda();
				resposta.setContent((Integer.toString(stock.get(medicamento)
						.getQuantidade()) + "-" + Double.toString(lucro) + "-" + stock
						.get(medicamento).getPvp()));

				myAgent.send(resposta);
				log.setText(estadoVendedor.VerStock()
						+ "\n Informacoes Enviadas ao Broker");

			} else if (msg.getPerformative() == msg.REQUEST) {
				String[] parts = msg.getContent().split("-");
				String medicamento = parts[0];
				int quantidade = Integer.parseInt(parts[1]);
				String cliente = parts[2];

				stock.get(medicamento).setQuantidade(
						stock.get(medicamento).getQuantidade() - quantidade);

				vendas.append("Vendeu " + quantidade + " de " + medicamento
						+ "\nao " + cliente + "\n");

				ACLMessage resposta = new ACLMessage(ACLMessage.AGREE);
				resposta.addReceiver(new AID("Broker", AID.ISLOCALNAME));
				double lucro = stock.get(medicamento).getPvp()
						- stock.get(medicamento).getRevenda();
				resposta.setContent(medicamento + "-" + quantidade + "-"
						+ stock.get(medicamento).getPvp() + "-" + lucro);
				myAgent.send(resposta);

				log.setText(estadoVendedor.VerStock());

			}

		}

	}

	public static double con(double precoDouble) {
		DecimalFormat fmt = new DecimalFormat("0.00");
		String string = fmt.format(precoDouble);
		String[] part = string.split("[,]");
		String string2 = part[0] + "." + part[1];
		double preco = Double.parseDouble(string2);
		return preco;
	}
}
