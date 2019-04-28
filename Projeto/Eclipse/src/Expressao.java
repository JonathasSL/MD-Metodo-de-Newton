import java.util.ArrayList;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class Expressao {
	private ArrayList<Polinomio> polinomios = new ArrayList<>();
	private ArrayList<Operacao> operadores = new ArrayList<>();

	public Expressao(Polinomio polinomio,Operacao operador) {
		this.polinomios.add(polinomio);
		this.operadores.add(operador);
	}

	public Expressao() {
		// TODO Auto-generated constructor stub
	}

	public void add(Polinomio polinomio,Operacao operador) {
		this.polinomios.add(polinomio);
		this.operadores.add(operador);
	}

	public void addPolinomio(Polinomio p) {
		polinomios.add(p);
	}
	public void addOperador(Operacao o) {
		operadores.add(o);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for(int i=0; i<polinomios.size(); i++) {
			sb.append(polinomios.get(i));
			//    		System.out.println(i);
			try {
				if(i<polinomios.size())
					if(operadores.get(i) == Operacao.ADICAO)
						sb.append("+");
					else if(operadores.get(i) == Operacao.SUBTRACAO)
						sb.append("-");
					else if(operadores.get(i) == Operacao.MULTIPLICACAO)
						sb.append("*");
					else if(operadores.get(i) == Operacao.DIVISAO)
						sb.append("/");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Index Out Of Bounds - operador("+i+")\n" +e.getMessage());
			}
		}
		return sb.toString();
	}

	public String toUnivariateString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<polinomios.size(); i++) {
			sb.append(polinomios.get(i).univariateString());

			try {
				if(i<polinomios.size()-1)
					if(operadores.get(i) == Operacao.ADICAO)
						sb.append("+");
					else if(operadores.get(i) == Operacao.SUBTRACAO)
						sb.append("-");
					else if(operadores.get(i) == Operacao.MULTIPLICACAO)
						sb.append("*");
					else if(operadores.get(i) == Operacao.DIVISAO)
						sb.append("/");
			}catch(IndexOutOfBoundsException e) {
//				System.out.println("polinomio "+polinomios.size()+" operadores "+operadores.size());
//				System.out.println("i "+i);
//				System.out.println("toUnivariateString\n"/*+e.getMessage()*/);
				e.printStackTrace();
			}
		}
//		RuntimeCompiler r = new RuntimeCompiler();
		return sb.toString();
	}

	public UnivariateFunction getFunction() {
		UnivariateFunction f = new UnivariateFunction() {
			@Override
			public double value(double x) {
				double resultado=0;
				boolean temPrioridade = false;
				int pos=0 ;
				for(int i=0; i<operadores.size() && !temPrioridade ;i++) {
					if(operadores.get(i) == Operacao.MULTIPLICACAO) {
						pos=i;
						temPrioridade = true;
						resultado = polinomios.get(pos).getResultado(x) * polinomios.get(pos+1).getResultado(x);
					}else if(operadores.get(i) == Operacao.DIVISAO) {
						pos=i;
						temPrioridade = true;
						resultado = polinomios.get(pos).getResultado(x) / polinomios.get(pos+1).getResultado(x);
					}
				}
				return resultado /*1 * Math.pow(x, 3.0) + 4 * Math.pow(x, 2.0) + 1 * Math.pow(x, 1.0) + 27*/;
			}
		};
		return f;
	}
}
