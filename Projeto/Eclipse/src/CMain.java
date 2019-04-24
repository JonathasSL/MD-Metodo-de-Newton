import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
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

	static ArrayList<Object> checkIf = new ArrayList<Object>();
	static String Cderivada = new String();
	static String resp, resp1;
	static ScriptEngineManager mgr = new ScriptEngineManager();
	static ScriptEngine engine = mgr.getEngineByName("JavaScript");
	static Pattern pattern = Pattern.compile("[x]");

	private void solveit() throws Exception {
		BrentSolver solver = new BrentSolver();
		UnivariateFunction f = new UnivariateFunction() {
			@Override
			public double value(double x) {
				//Double dy = null;
				// return 103*Math.pow(x, 5.0)-5*Math.pow(x, 4.0)-5*Math.pow(x,
				// 3.0)-5*Math.pow(x, 2.0)-5*x-105;
				//return 1 * Math.pow(x, 2.0) + 3 * Math.pow(x, 1.0) - 10;
				return 2 * Math.pow(x, 5) - 5 * Math.pow(x, 3) + 10;
			}
		};
		

		String str = "2 * Math.pow(x, 4) - 5 * Math.pow(x, 3) + 10";
		str = str.replaceAll("Math.pow(([^<]*))", "$1");
		str = str.replaceAll("Math.pow(([^<]*))", "$1");
		str = str.replaceAll("Math.pow(([^<]*))", "$1");
		str = str.replaceAll("Math.pow(([^<]*))", "$1");
		str = str.replaceAll("Math.pow(([^<]*))", "$1");
		str = str.replaceAll("Math.pow(([^<]*))", "$1");
		str = str.replace("(", "").replace(")", "").replace(",", "^").replace(" ", "");

		System.out.println("str: " + str);
		double intervalStart = -10;
		double intervalSize = 0.2;
		Cderivada = str;
		derivador();
		resp1 = resp;
		while (intervalStart <= 10) {

			Matcher matcher = pattern.matcher(resp);
			StringBuilder builder = new StringBuilder();
			int i = 0;
			while (matcher.find()) {

				String replacement = Double.toString(intervalStart);
				builder.append(resp.substring(i, matcher.start()));

				builder.append(replacement);
				i = matcher.end();
			}
			builder.append(resp.substring(i, resp.length()));
			// System.out.println(builder.toString());
			String f1 = engine.eval(builder.toString()).toString();
			// System.out.println("f1: " + f1 + "resp: " + resp);
			double fOne = Double.parseDouble(f1);

			//

			Matcher matcher1 = pattern.matcher(resp1);
			StringBuilder builder1 = new StringBuilder();
			i = 0;
			while (matcher1.find()) {

				String replacement = Double.toString(intervalStart + intervalSize);
				builder1.append(resp1.substring(i, matcher1.start()));

				builder1.append(replacement);
				i = matcher1.end();
			}
			builder1.append(resp1.substring(i, resp1.length()));
			// System.out.println(builder1.toString());
			String f2 = engine.eval(builder1.toString()).toString();
			// System.out.println("f2: " + f2 + "resp1: " + resp1);
			double fTwo = Double.parseDouble(f2);

			if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) == 0) {
				double firstResult = solver.solve(100, f, intervalStart, intervalStart + intervalSize);
				System.out.printf("raiz confirmada! x = %2.8f\n", firstResult);
				checkIf.add(firstResult);
			} if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) < 0) {
				double secondResult = solver.solve(100, f, intervalStart, intervalStart + intervalSize);
				System.out.printf("Provável raiz. x = %2.8f\n", secondResult);
				checkIf.add(secondResult);
			} if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) != 0
					&& fOne * fTwo == 0) {
				System.out.println("Intervalo sem raiz");
			}
			if (Math.signum(f.value(intervalStart)) * Math.signum(f.value(intervalStart + intervalSize)) > 0
					&& fOne * fTwo > 0) {
				double fourthResult = solver.solve(100, f, intervalStart, intervalStart + intervalSize);
				System.out.println("x = " + fourthResult);
				checkIf.add(fourthResult);
			}

			intervalStart += intervalSize;
		}
	}

	static void derivador() throws ScriptException {
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
	}

	public static void main(String[] args) {
		try {
			new CMain().solveit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
