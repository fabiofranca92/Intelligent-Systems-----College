package behaviour;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import dataModel.EstadoCliente;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class EsperaCliente extends CyclicBehaviour {

	public EstadoCliente estadoCliente;
	JTextArea lista;
	JLabel label;

	public EsperaCliente(JLabel label, EstadoCliente estadoCliente,
			JTextArea lista) {
		this.estadoCliente = estadoCliente;
		this.lista = lista;
		this.label = label;

	}

	@Override
	public void action() {

		ACLMessage msg = myAgent.receive();

		if (msg != null) {

			if (msg.getPerformative() == ACLMessage.FAILURE) {

				if (msg.getOntology() != null) {

					if (msg.getOntology().equals("Stock")) {

						lista.append(msg.getContent() + "\n");
					} else if (msg.getOntology().equals("Saldo")) {

						lista.append(msg.getContent() + "\n");

					}
				} else {
					lista.append("Nao ha vendedores ativos\n");
				}

			} else if (msg.getPerformative() == ACLMessage.AGREE) {

				String[] parts = msg.getContent().split("-");
				String medicamento = parts[0];
				int quantidade = Integer.parseInt(parts[1]);
				double gasto = Double.parseDouble(parts[2]);
				String vendedor = parts[3];

				double saldoFinal = estadoCliente.getSaldo() - gasto;
				label.setText(Double.toString(saldoFinal));
				estadoCliente.setSaldo(saldoFinal);

				lista.append(quantidade + " unidades de " + medicamento
						+ " ao " + vendedor + "\n");

			}

		}

	}

}
