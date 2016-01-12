package agents;

import interfaces.Cliente_Interface1;
import interfaces.Vendedor_Interface;
import behaviour.Informar;
import dataModel.EstadoVendedor;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Vendedor extends Agent {

	private EstadoVendedor estadoVendedor;

	protected void setup() {

		estadoVendedor = new EstadoVendedor();

		System.out.println("Vendedor Inicializado");
		estadoVendedor.setStock();

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Farmacia");
		sd.setName(getLocalName() + "Farmacia");
		dfd.addServices(sd);

		System.out.println(estadoVendedor.VerStock());
		Vendedor_Interface interf = new Vendedor_Interface(estadoVendedor,
				this.getLocalName());
		interf.setAgent(this);
		interf.setVisible(true);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

	}

	protected void takeDown() {

		try {
			DFService.deregister(this);
		} catch (Exception e) {
		}

	}
}
