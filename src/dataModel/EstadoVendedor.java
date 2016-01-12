package dataModel;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

public class EstadoVendedor {

	HashMap<String, Medicamento> stock = new HashMap<>();
	Random r;
	double pvpBrufen = 3.53;
	double pvpXanax = 2.48;
	double pvpGurosan = 10.35;
	double revenda;

	public EstadoVendedor() {

	}

	public HashMap<String, Medicamento> getStock() {
		return stock;
	}

	public String VerStock() {

		return ("Medicamento : " + stock.get("Brufen").getNome()
				+ " \nQuantidade : " + stock.get("Brufen").getQuantidade()
				+ "\nRevenda : " + stock.get("Brufen").getRevenda()
				+ "\nMedicamento : " + stock.get("Xanax").getNome()
				+ " \nQuantidade : " + stock.get("Xanax").getQuantidade()
				+ "\nRevenda : " + stock.get("Xanax").getRevenda())
				+ "\nMedicamento : "
				+ stock.get("Gurosan").getNome()
				+ "\n Quantidade : "
				+ stock.get("Gurosan").getQuantidade()
				+ "\n Revenda : " + stock.get("Gurosan").getRevenda();

	}

	public void setStock() {

		Medicamento m = new Medicamento();
		r = new Random();

		m.setNome("Brufen");
		m.setPrincipio("Ipubrufeno");

		revenda = 3 + (pvpBrufen - 3) * r.nextDouble();

		m.setRevenda(con(revenda));
		m.setPvp(pvpBrufen);
		m.setQuantidade(5);
		stock.put(m.getNome(), m);

		Medicamento m1 = new Medicamento();
		m1.setNome("Xanax");
		m1.setPrincipio("Alprazolam");
		revenda = 1.75 + (pvpXanax - 1.75) * r.nextDouble();

		m1.setRevenda(con(revenda));
		m1.setPvp(pvpXanax);
		m1.setQuantidade(r.nextInt(5));
		stock.put(m1.getNome(), m1);

		Medicamento m2 = new Medicamento();
		m2.setNome("Gurosan");
		m2.setPrincipio("Cafeina + Glucuronamida + Acido Ascorbico");
		revenda = 9.5 + (pvpGurosan - 9.5) * r.nextDouble();
		m2.setRevenda(con(revenda));
		m2.setPvp(pvpGurosan);
		m2.setQuantidade(r.nextInt(5));
		stock.put(m2.getNome(), m2);

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
