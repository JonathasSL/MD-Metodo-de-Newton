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
    		
    		if(i<polinomios.size())
    			if(operadores.get(i) == Operacao.ADICAO)
    				sb.append("+");
    			else if(operadores.get(i) == Operacao.SUBTRACAO)
    				sb.append("-");
    			else if(operadores.get(i) == Operacao.MULTIPLICACAO)
    				sb.append("*");
    			else if(operadores.get(i) == Operacao.DIVISAO)
    				sb.append("/");
    	}
    	
    	return sb.toString();
    }
    
    public UnivariateFunction getFunction() {
    	UnivariateFunction f = new UnivariateFunction() {
			@Override
			public double value(double x) {
				return 2 * Math.pow(x, 5) - 5 * Math.pow(x, 3) + 10;
			}
		};
		return f;
    }
}
