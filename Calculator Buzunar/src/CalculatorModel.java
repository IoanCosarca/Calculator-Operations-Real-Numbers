import java.math.BigDecimal;
import javax.swing.JTextField;
import java.math.*;

public class CalculatorModel {
	
	static final String INIT_VAL = "0";
	private BigDecimal rez;
	private BigDecimal rezpart;
	private BigDecimal a, b;
	private static String memory;
	private char op = '0';
	private int rezpartformat = 0;
	MathContext mc = new MathContext(15);
	
	CalculatorModel()
	{
		memory = "0";
		reset();
	}
	
	protected void reset()
	{
		rezpart = new BigDecimal(INIT_VAL);
		rezpartformat = 0;
		resetResult();
	}

	protected void resetResult() {
		rez = new BigDecimal(INIT_VAL);
	}

	public BigDecimal getTerm(JTextField term)
	{
		try {
			a = new BigDecimal(String.valueOf(term.getText()));
		}
		catch(NumberFormatException e) {;}
		return a;
	}
	
	private void FormRezpart()
	{
		switch(op)
		{
			case '+':
				rezpart = rezpart.add(a);
				break;
			case '*':
				rezpart = rezpart.multiply(a);
				break;
			case '/':
				rezpart = rezpart.divide(a, mc);
				break;
		}
	}
	
	private void calc_a(JTextField expr, JTextField term)
	{
		try {
			a = new BigDecimal(String.valueOf(term.getText()));
		}
		catch(NumberFormatException e) {
			expr.setText(expr.getText().substring(0, expr.getText().length() - 1));
		}
	}

	public void AddRezultat(JTextField expr, JTextField term)
	{
		calc_a(expr, term);
		FormRezpart();
		if(rezpartformat == 0)
		{
			rez = rez.add(a);
			rezpart = rez;
			op = '+';
		}
		else
		{
			rez = rez.add(rezpart);
			rezpartformat = 0;
		}
	}
	
	private void calc_expr(JTextField expr, JTextField term)
	{
		calc_a(expr, term);
		if(rezpartformat == 0)
		{
			rezpartformat = 1;
			rezpart = a;
		}
		else FormRezpart();
	}

	public void Multiply(JTextField expr, JTextField term)
	{
		calc_expr(expr, term);
		op = '*';
	}

	public void Division(JTextField expr, JTextField term)
	{
		calc_expr(expr, term);
		op = '/';
	}

	public String CalcPercent(JTextField term)
	{
		a = getTerm(term);
		a = a.divide(new BigDecimal("100"), mc);
		return String.valueOf(a);
	}

	public void InvertSign(JTextField term)
	{
		a = getTerm(term);
		a = a.multiply(new BigDecimal("-1"));
		term.setText(String.valueOf(a));
	}

	public String InvertNr(JTextField term)
	{
		a = getTerm(term);
		a = a.pow(-1, mc);
		return String.valueOf(a);
	}

	public String CalcSquare(JTextField term)
	{
		a = getTerm(term);
		a = a.pow(2, mc);
		return String.valueOf(a);
	}

	public String CalcSquareRoot(JTextField term)
	{
		a = getTerm(term);
		a = a.sqrt(mc);
		return String.valueOf(a);
	}
	
	public void Equals(JTextField expr, JTextField term)
	{
		if(expr.getText().length() == 0)
		{
			FormRezpart();
			rez = rez.add(rezpart);
		}
		else AddRezultat(expr, term);
	}

	public String GetRez()
	{
		String ret = String.format("%g", rez);
		return ret;
	}

	public void SetMemory(String string) {
		memory = string;
	}

	public String GetMemory() {
		return memory;
	}

	public void ModifyMemory(JTextField term)
	{
		a = getTerm(term);
		b = new BigDecimal(String.valueOf(memory));
		a = a.add(b);
		memory = String.valueOf(a);
	}
	
}
