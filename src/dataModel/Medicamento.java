package dataModel;

public class Medicamento {
	public String nome;
	private String principio;
	private double pvp;
	private double revenda;
	private int quantidade;
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPrincipio() {
		return principio;
	}
	
	public void setPrincipio(String principio) {
		this.principio = principio;
	}
	public double getPvp() {
		return pvp;
	}
	
	public void setPvp(double pvp) {
		this.pvp = pvp;
	}
	public double getRevenda() {
		return revenda;
	}
	
	public void setRevenda(double revenda) {
		this.revenda = revenda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
}
