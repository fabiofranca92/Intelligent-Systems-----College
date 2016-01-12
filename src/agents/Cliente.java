package agents;

import interfaces.Cliente_Interface1;

import dataModel.EstadoCliente;
import jade.core.Agent;

public class Cliente extends Agent {

	private EstadoCliente estadoCliente;
	double saldo;

	protected void setup() {

		estadoCliente = new EstadoCliente();
		super.setup();

		Cliente_Interface1 interf = new Cliente_Interface1(estadoCliente);
		interf.setAgent(this);
		interf.setVisible(true);

	}

}
