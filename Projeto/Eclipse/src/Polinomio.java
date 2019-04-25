import java.util.StringTokenizer;

public class Polinomio {
	private String polStr;
	private double multiplicador;
	private double potencia;
	private boolean hasPotency;
	private boolean noVariable;

	public Polinomio(String polinomio) {
		this.polStr = polinomio;

		//Se tem potencia
		if(polStr.contains("x^")) {
			this.hasPotency = false;
			StringTokenizer str = new StringTokenizer(polStr,"x^");

			if(!polStr.startsWith("x^")) {
				this.multiplicador = Double.parseDouble(str.nextToken());
				this.potencia = Double.parseDouble(str.nextToken());
			}else {
				this.multiplicador = 1;
				this.potencia = Double.parseDouble(str.nextToken());
			}
		}else {
			//Se nao tem potencia
			if(!polStr.contains("x^")) {
				this.hasPotency = true;
				if(polStr.contains("x")) {
					StringTokenizer str1 = new StringTokenizer(polStr,"x");
					this.multiplicador = Double.parseDouble(str1.nextToken());
					this.potencia = 1;
				}else {
					this.multiplicador = Double.parseDouble(polStr);
					this.potencia = 0; //0 para explicitar que n�o tem variavel
					noVariable = true;
				}
			}
		}
	}

	public String toString() {
		if(hasPotency)
			return String.valueOf(multiplicador) + "x";
		else
			return String.valueOf(multiplicador) + "x^" + String.valueOf(potencia);
	}

	public String univariateString() {
		StringBuilder sb = new StringBuilder();
		if(hasPotency) {
			sb.append(multiplicador).append(" * Math.pow(x, 1)");
		}
		else {
			sb.append(multiplicador).append(" * Math.pow(x, "+potencia+")");
		}
		return sb.toString();
	}

	public double getResultado(Double x) {
		if(!hasPotency)
			return multiplicador;
		else
			return multiplicador * Math.pow(x, potencia);
	}
}
