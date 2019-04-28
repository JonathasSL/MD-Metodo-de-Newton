import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.exception.NoBracketingException;
import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class CMain {

	//------VARIABLES------
	static ArrayList<Object> checkIf = new ArrayList<Object>();
	static String Cderivada = new String();
	static String resp, resp1, str, str1;
	static ScriptEngineManager mgr = new ScriptEngineManager();
	static ScriptEngine engine = mgr.getEngineByName("JavaScript");
	static BrentSolver solver = new BrentSolver();
	static UnivariateFunction f = new UnivariateFunction() {
		@Override
		public double value(double x) {
			//return 103*Math.pow(x, 5.0)-5*Math.pow(x, 4.0)-5*Math.pow(x,3.0)-5*Math.pow(x, 2.0)-5*x-105;
			//return 1 * Math.pow(x, 2.0) + 3 * Math.pow(x, 1.0) - 10;
			//return 2 * Math.pow(x, 5) - 5 * Math.pow(x, 3) + 10;
			//return 4 * Math.pow(x, 2.0) - 4 * Math.pow(x, 1.0) + 1;
			//return 1 * Math.pow(x, 3.0) + 4 * Math.pow(x, 2.0) + 1 * Math.pow(x, 1.0) + 27;
			//return 1*Math.pow(x, 2)-5*Math.pow(x, 1)+6;
			return 5 * Math.pow(x, 2) + 15 * Math.pow(x, 1);
		}
	};

	public CMain() {
		
	}
	
	public CMain(UnivariateFunction f, String str) {
		CMain.f = f;
		CMain.str = str;
		CMain.str1 = str;
	}
	
	public static String getStr() {
		return str;
	}

	public static void setStr(String str) {
		CMain.str = str;
	}

	public static String getStr1() {
		return str1;
	}

	public static void setStr1(String str1) {
		CMain.str1 = str1;
	}

	public static UnivariateFunction getF() {
		return f;
	}

	public static void setF(UnivariateFunction f) {
		CMain.f = f;
	}

	//--------METHOD FOR CONVERTING A STRING TO A MODEL DERIVADOR FUNCTION ACCEPTS-------
	static String convertString(String str) {
		for (int z = 0; z < 6; z++) {
			str = str.replaceAll("Math.pow(([^<]*))", "$1");
		}
		str = str.replace("(", "").replace(")", "").replace(",", "^").replace(" ", "");
		return str;
	}
	
	static String reConvertString(String str) {
		str = str.replaceAll("x\\^(\\d+(?:\\.\\d+)?)", "Math.pow(x, $1)");
		System.out.println("O ULTIMATO: "+str);
		return str;
	}
	
	
	//---------FUNCTION THAT ACTUALLY ISOLATE ROOTS, NEWTON RAPHSON METHOD----------
	static void solveit(UnivariateFunction f) throws Exception {
		double intervalStart = -10;
		double intervalSize = 0.1;
		System.out.println(str);
		Cderivada = convertString(str);
		System.out.println(Cderivada);
		Cderivada = derivador(Cderivada);
		System.out.println("c"+Cderivada);
		resp1 = Cderivada;
		resp1=reConvertString(resp1);
		System.out.println("STRING" + resp1);
		str1 = str;
		//LOOP
		while (intervalStart <= 10) {
		
			double fOne = replaceXAndCalculate(str, intervalStart);
			double fTwo = replaceXAndCalculate(str, intervalStart+intervalSize);
			//System.out.println("f1 e f2: " + fOne + fTwo);
			double x0, x1 = 0;

			//------CONDITIONS-------
			if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) == 0) {
				double firstResult = solver.solve(100, f, intervalStart, intervalStart + intervalSize);
				System.out.printf("raiz confirmada! x = %.8f\n", firstResult);
				checkIf.add(firstResult);
			} 
			if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) < 0) {
				double secondResult = solver.solve(100, f, intervalStart, intervalStart + intervalSize);
				/*x0 = fOne < fTwo ? fOne : fTwo;
				double fx0 = replaceXAndCalculate(str1, x0);
				double fdx0 = replaceXAndCalculate(resp1, x0);
				double xn = x0;
				while(Math.abs(xn) > 0.00000001) {
					x1 = x0-(fx0/fdx0);
					xn = Math.abs(x1-x0);
					x0 = x1;
					fx0 = replaceXAndCalculate(str1, x0);
					fdx0 = replaceXAndCalculate(resp1, x0);
				}*/
				System.out.println("sr"+secondResult);
				//System.out.printf("Raiz aproximada pelo método de Newton = %.8f\n", x0);
				checkIf.add(secondResult);
			} 
			if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) != 0
					&& fOne * fTwo == 0) {
				System.out.println("Intervalo sem raiz");
			}
			try {
				if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) > 0
						&& fOne * fTwo < 0) {
					double fourthResult = solver.solve(100, f, intervalStart, intervalStart + intervalSize);
					System.out.println("x = " + fourthResult);
					checkIf.add(fourthResult);
				}
			} catch(NoBracketingException e) {
				//double larg = intervalStart + intervalSize;
				//System.err.println("Não há variação de sinal no intervalo [" + intervalStart + ", " + larg + "].");
				x0 = fOne < fTwo ? fOne : fTwo;
				double fx0 = replaceXAndCalculate(str1, x0);
				double fdx0 = replaceXAndCalculate(resp1, x0);
				double xn = x0;
				while(Math.abs(xn) > 0.00000001) {
					x1 = x0-(fx0/fdx0);
					xn = Math.abs(x1-x0);
					x0 = x1;
					fx0 = replaceXAndCalculate(str1, x0);
					fdx0 = replaceXAndCalculate(resp1, x0);
				}
				System.out.printf("Provavel raiz. Newton  a x = %2.8f", x0);
				checkIf.add(x0);
			}
			//-------END CONDITIONS----------

			intervalStart += intervalSize;
		}
	}

	static String derivador(String resp) {
		DJep j = new DJep();
		j.addStandardConstants();
		j.addStandardFunctions();
		j.addComplex();
		j.setAllowUndeclared(true);
		j.setAllowAssignment(true);
		j.setImplicitMul(true);
		j.addStandardDiffRules();
		try {
			Node node = j.parse(Cderivada);
			Node diff = j.differentiate(node, "x");
			Node simp = j.simplify(diff);
			resp = j.toString(simp);
			System.out.println(resp);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	static double replaceXAndCalculate(String formula, double x) throws ScriptException {
		Pattern pattern = Pattern.compile("[x]");
		Matcher matcher = pattern.matcher(formula);
		StringBuilder builder = new StringBuilder();
		int i = 0;
		while (matcher.find()) {

			String replacement = Double.toString(x);
			builder.append(formula.substring(i, matcher.start()));

			builder.append(replacement);
			i = matcher.end();
		}
		builder.append(formula.substring(i, formula.length()));
		String calculate = engine.eval(builder.toString()).toString();
		double result = Double.parseDouble(calculate);
		return result;
	}

	public static void main(String[] args) {
		CMain executar = new CMain(f, "5 * Math.pow(x, 2) + 15 * Math.pow(x, 1)");
		try {
			CMain.solveit(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("checa"+checkIf.toString());

	}
}
