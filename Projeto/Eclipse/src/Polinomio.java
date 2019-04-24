import java.util.StringTokenizer;

public class Polinomio {
	private String polStr;
	private double multiplicador;
	private double potencia;
	private boolean pot;

	public Polinomio(String polinomio) {
		this.polStr = polinomio;

		//Se tem potencia
		if(polStr.contains("x^")) {
			this.pot = false;
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
				this.pot = true;
				this.multiplicador = Double.parseDouble(polinomio);
			}
		}
	}

	public String toString() {
		if(pot)
			return String.valueOf(multiplicador) + "x";
		else
			return String.valueOf(multiplicador) + "x^" + String.valueOf(potencia);
	}
	
	public String univariateString() {
		StringBuilder sb = new StringBuilder();
		if(pot) {
			sb.append(multiplicador).append(" * Math.pow(x, 1)");
		}
		else {
			sb.append(multiplicador).append(" * Math.pow(x, potencia)");
		}
			
			
		return sb.toString();
	}
}
