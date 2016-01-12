package behaviour;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import agents.Vendedor;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.DataStore;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Negociador extends CyclicBehaviour {
	JTextArea log;
	JLabel label;

	public Negociador(JTextArea log, JLabel label) {
		this.log = log;
		this.label = label;
	}

	@Override
	public void action() {
		HashMap<DFAgentDescription, Double> lucros = new HashMap<>();

		ACLMessage msg = myAgent.receive();

		if (msg != null) {

			if (msg.getPerformative() == msg.REQUEST) {

				String[] parts = msg.getContent().split("-");
				String medicamento = parts[0];

				int quantidade = Integer.parseInt(parts[1]);
				double saldo = Double.parseDouble(parts[2]);

				getDataStore().put("medicamento", medicamento);
				getDataStore().put("quantidade", quantidade);
				getDataStore().put("saldo", saldo);
				getDataStore().put("cliente", msg.getSender());

				DFAgentDescription dfd = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Farmacia");
				dfd.addServices(sd);

				DFAgentDescription[] result;
				try {

					result = DFService.search(myAgent, dfd);
					if (result == null) {

						ACLMessage falhou = new ACLMessage(ACLMessage.FAILURE);
						falhou.addReceiver((AID) getDataStore().get("cliente"));

						myAgent.send(falhou);
						getDataStore().clear();

					} else {

						if (result.length == 0) {
							ACLMessage falhou = new ACLMessage(
									ACLMessage.FAILURE);
							falhou.addReceiver((AID) getDataStore().get(
									"cliente"));

							myAgent.send(falhou);
							getDataStore().clear();
						} else {
							for (int i = 0; i < result.length; i++) {

								log.append(result[i].getName().getLocalName()
										+ " online \n");
								ACLMessage pedido = new ACLMessage(
										ACLMessage.INFORM);
								pedido.addReceiver(result[i].getName());
								pedido.setContent(medicamento);
								myAgent.send(pedido);
								getDataStore().put("lista", result);
							}
						}
					}
				} catch (FIPAException e) {

					e.printStackTrace();
				}

			} else if (msg.getPerformative() == msg.INFORM) {

				String[] parts = msg.getContent().split("-");
				int quantidade = Integer.parseInt(parts[0]);
				double lucro = Double.parseDouble(parts[1]);
				double preco = Double.parseDouble(parts[2]);
				DFAgentDescription[] result = (DFAgentDescription[]) getDataStore()
						.get("lista");

				log.append(msg.getSender().getLocalName() + " tem "
						+ quantidade + " de "
						+ getDataStore().get("medicamento") + " em stock "
						+ " - lucro : " + con(lucro) + "\n");

				getDataStore().put(msg.getSender().getLocalName(),
						msg.getContent());

				if (result.length == getDataStore().size() - 5) {

					for (int i = 0; i < result.length; i++) {

						String[] part = ((String) getDataStore().get(
								result[i].getName().getLocalName())).split("-");
						quantidade = Integer.parseInt(part[0]);
						lucro = Double.parseDouble(part[1]);
						preco = Double.parseDouble(parts[2]);

						if (quantidade >= (int) getDataStore()
								.get("quantidade")) {
							lucros.put(result[i], lucro);

						}
					}

					if ((preco * (int) getDataStore().get("quantidade")) > (double) getDataStore()
							.get("saldo")) {
						ACLMessage falhou = new ACLMessage(ACLMessage.FAILURE);
						falhou.setOntology("Saldo");
						falhou.addReceiver((AID) getDataStore().get("cliente"));

						falhou.setContent("Saldo Insuficiente");
						myAgent.send(falhou);

						getDataStore().clear();
					} else if (lucros.size() > 0) {

						getDataStore().put("vendedor",
								max(lucros).getName().getLocalName());

						log.append("Vendedor escolhido "
								+ max(lucros).getName().getLocalName() + "\n\n");
						ACLMessage pedido = new ACLMessage(ACLMessage.REQUEST);
						AID cliente = (AID) getDataStore().get("cliente");

						pedido.setContent(getDataStore().get("medicamento")
								+ "-" + getDataStore().get("quantidade") + "-"
								+ cliente.getLocalName());
						pedido.addReceiver(max(lucros).getName());
						myAgent.send(pedido);
					} else {

						ACLMessage falhou = new ACLMessage(ACLMessage.FAILURE);
						falhou.addReceiver((AID) getDataStore().get("cliente"));

						falhou.setContent("Quantidade Insuficiente nos Stocks");
						falhou.setOntology("Stock");
						myAgent.send(falhou);

						getDataStore().clear();
					}
				}

			} else if (msg.getPerformative() == ACLMessage.AGREE) {

				String[] parts = msg.getContent().split("-");
				String medicamento = parts[0];
				int quantidade = Integer.parseInt(parts[1]);
				double preco = Double.parseDouble(parts[2]);
				double lucro = Double.parseDouble(parts[3]);

				double lucro_actual = con(Double.parseDouble(label.getText())
						+ lucro)
						* quantidade;
				label.setText(Double.toString(lucro_actual));

				ACLMessage concluido = new ACLMessage(ACLMessage.AGREE);

				concluido.setContent(medicamento + "-" + quantidade + "-"
						+ (preco * quantidade) + "-"
						+ getDataStore().get("vendedor"));

				concluido.addReceiver((AID) getDataStore().get("cliente"));
				myAgent.send(concluido);

				getDataStore().clear();
			} else if (msg.getPerformative() == msg.CANCEL) {
				label.setText("0");
				log.setText(null);
			}
		}

	}

	public DFAgentDescription max(HashMap<DFAgentDescription, Double> passedMap) {
		Set<DFAgentDescription> keys = passedMap.keySet();
		DFAgentDescription resultado = null;
		Iterator i = keys.iterator();
		double max = Double.MIN_VALUE;
		while (i.hasNext()) {
			DFAgentDescription x = (DFAgentDescription) i.next();
			if (passedMap.get(x) > max) {
				max = passedMap.get(x);
				resultado = x;
			}
		}
		return resultado;

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
