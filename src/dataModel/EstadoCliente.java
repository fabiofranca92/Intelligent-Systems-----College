package dataModel;

import java.util.HashMap;


import javax.swing.JTextArea;

public class EstadoCliente {

	public double saldo = 0;
	HashMap<String,Integer> carrinho;
	int quantidade;
	String medicamento;
	String encomenda;
	JTextArea listaestadoClientes;
	
	public JTextArea getListaestadoClientes() {
		return listaestadoClientes;
	}

	public void setListaestadoClientes(JTextArea listaestadoClientes) {
		this.listaestadoClientes = listaestadoClientes;
	}

	public String getEncomenda() {
		return encomenda;
	}

	public void setEncomenda(String encomenda) {
		this.encomenda = encomenda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
	this.quantidade = quantidade;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String med) {
		this.medicamento = med;
	}


	
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		
		this.saldo = saldo;
	}



	
	
	public EstadoCliente(){
	
	}
}
