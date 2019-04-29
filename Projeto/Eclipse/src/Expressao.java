import java.util.ArrayList;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class Expressao {
	private ArrayList<Polinomio> polinomios = new ArrayList<>();
	private ArrayList<Operacao> operadores = new ArrayList<>();
	public static String toString;

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
		toString();
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
				if(i<polinomios.size() && operadores.size()!=0)
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
//		System.out.println("sb "+sb.toString());
		this.toString = sb.toString();
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
//				double resultado=0;
//				boolean temPrioridade = false;
//				int pos=0 ;
//				for(int i=0; i<operadores.size() && !temPrioridade ;i++) {
//					if(operadores.get(i) == Operacao.MULTIPLICACAO) {
//						pos=i;
//						temPrioridade = true;
//						resultado = polinomios.get(pos).getResultado(x) * polinomios.get(pos+1).getResultado(x);
//					}else if(operadores.get(i) == Operacao.DIVISAO) {
//						pos=i;
//						temPrioridade = true;
//						resultado = polinomios.get(pos).getResultado(x) / polinomios.get(pos+1).getResultado(x);
//					}else if(operadores.get(i) == Operacao.ADICAO) {
//						pos=i;
//					}
//				}
//				return 1 * Math.pow(x, 3.0) + 4 * Math.pow(x, 2.0) + 1 * Math.pow(x, 1.0) + 27;
//				System.out.println("Teste "+Expressao.toString.replaceAll("x", String.valueOf(x)));
				
				return this.eval( Expressao.toString.replaceAll("x", String.valueOf(x) ) );
			}
			
			public double eval(final String str) {
			    return new Object() {
			        int position = -1, charr;

			        void nextChar() {
			            charr = (++position < str.length()) ? str.charAt(position) : -1;
			        }
			        
			        //Testa se o caracter na posição é igual a caracter esperado
			        boolean eat(int charToEat) {
			            while (charr == ' ') nextChar();
			            if (charr == charToEat) {
			                nextChar();
			                return true;
			            }
			            return false;
			        }

			        double parse() {
//			        	str.replaceAll("x",String.valueOf(d));
			            nextChar();
			            double x = parseExpression();
			            if (position < str.length()) throw new RuntimeException("Unexpected: " + (char)charr);
			            return x;
			        }

			        // Grammar:
			        // expression = term | expression `+` term | expression `-` term
			        // term = factor | term `*` factor | term `/` factor
			        // factor = `+` factor | `-` factor | `(` expression `)`
			        //        | number | functionName factor | factor `^` factor

			        //
			        double parseExpression() {
			            double x = parseTerm();
			            for (;;) {
			                if      (eat('+')) x += parseTerm(); // addition
			                else if (eat('-')) x -= parseTerm(); // subtraction
			                else return x;
			            }
			        }

			        double parseTerm() {
			            double x = parseFactor();
			            for (;;) {
			                if      (eat('*')) x *= parseFactor(); // multiplication
			                else if (eat('/')) x /= parseFactor(); // division
			                else return x;
			            }
			        }

			        double parseFactor() {
			            if (eat('+')) return parseFactor(); // unary plus
			            if (eat('-')) return -parseFactor(); // unary minus

			            double x;
			            int startPos = this.position;
			            if (eat('(')) { // parentheses
			                x = parseExpression();
			                eat(')');
			            } else if ((charr >= '0' && charr <= '9') || charr == '.') { // numbers
			                while ((charr >= '0' && charr <= '9') || charr == '.') nextChar();
			                x = Double.parseDouble(str.substring(startPos, this.position));
			            } else if (charr >= 'a' && charr <= 'z') { // functions
			                while (charr >= 'a' && charr <= 'z') nextChar();
			                String func = str.substring(startPos, this.position);
			                x = parseFactor();
			                if (func.equals("sqrt")) x = Math.sqrt(x);
			                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
			                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
			                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
			                else throw new RuntimeException("Unknown function: " + func);
			            } else {
			                throw new RuntimeException("Unexpected: " + (char)charr);
			            }

			            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

				            return x;
			        }
			    }.parse();
			}
		};
		return f;
	}


}
