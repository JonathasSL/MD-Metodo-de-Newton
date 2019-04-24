import java.util.StringTokenizer;

public class Polinomio {
	private String polStr;
	private double multiplicador;
	private double potencia;
	private boolean monomio;

	public Polinomio(String polinomio) {
		this.polStr = polinomio;

		//Se tem potencia
		if(polStr.contains("x^")) {
			this.monomio = false;
			StringTokenizer str = new StringTokenizer(polStr,"x^");

			if(!polStr.startsWith("x^")) {
				this.multiplicador = Double.parseDouble(str.nextToken());
				this.potencia = Double.parseDouble(str.nextToken());
			}else {
				this.multiplicador = 0;
				this.potencia = Double.parseDouble(str.nextToken());
			}
		}else {
			//Se nao tem potencia
			if(!polStr.contains("x^")) {
				this.monomio = true;
				this.multiplicador = Double.parseDouble(polinomio);
			}
		}
	}

	public String toString() {
		if(monomio)
			return String.valueOf(multiplicador) + "x";
		else
			return String.valueOf(multiplicador) + "x^" + String.valueOf(potencia);
	}
	
	public String univariateString() {
		StringBuilder sb = new StringBuilder();
		return null;
	}
}
