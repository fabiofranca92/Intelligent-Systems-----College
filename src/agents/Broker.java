package agents;

import interfaces.Broker_Interface;
import interfaces.Cliente_Interface1;

import java.util.Vector;

import behaviour.Negociador;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Broker extends Agent {


	protected void setup(){
		Broker_Interface interf = new Broker_Interface();
		interf.setAgent(this);
		interf.setVisible(true);
		
		
	}
}