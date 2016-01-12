package behaviour;

import dataModel.EstadoCliente;
import jade.core.behaviours.OneShotBehaviour;

public class IntroduzirSaldo extends  OneShotBehaviour {

	double saldo;
	EstadoCliente estadoCliente;
	public IntroduzirSaldo(EstadoCliente estadoCliente ,double saldo){
		this.estadoCliente = estadoCliente;
		this.saldo = saldo;
	
	}
	

	@Override
	public void action() {

		estadoCliente.setSaldo(saldo);
	

}
}