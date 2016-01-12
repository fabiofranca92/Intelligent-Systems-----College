package behaviour;

import java.util.Scanner;

import agents.Cliente;
import dataModel.EstadoCliente;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class PedeArtigo extends OneShotBehaviour {

	String encomenda;

	ACLMessage pedido;

	public PedeArtigo(String encomenda) {

		this.encomenda = encomenda;

	}

	@Override
	public void action() {

		String[] parts = encomenda.split("-");
		String medicamento = parts[0];
		int quantidade = Integer.parseInt(parts[1]);
		double saldo = Double.parseDouble(parts[2]);

		if (medicamento.equals("null")) {
			medicamento = "Xanax";
		}

		if (quantidade == 0) {
			quantidade = 1;
		}

		encomenda = medicamento + "-" + quantidade + "-" + saldo;
		pedido = new ACLMessage(ACLMessage.REQUEST);
		pedido.addReceiver(new AID("Broker", AID.ISLOCALNAME));
		pedido.setContent(encomenda);
		myAgent.send(pedido);

	}

}
