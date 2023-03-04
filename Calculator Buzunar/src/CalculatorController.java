import java.awt.event.*;
import javax.swing.*;

public class CalculatorController implements ActionListener {
	private CalculatorModel c_model;
	private CalculatorView c_view;
	private static int isDecimal = 0;
	private static int sign = 1;
	private static int showResult = 1;

	CalculatorController(CalculatorModel model, CalculatorView view)
	{
		c_model = model;
		c_view = view;

		c_view.jOn.addActionListener(this);
		c_view.jOff.addActionListener(this);
		for(int i = 0;i < 10;i++) {
			c_view.numbers[i].addActionListener(this);
		}
		for(int i = 0;i < 19;i++) {
			c_view.functions[i].addActionListener(this);
		}
		c_view.jOff.doClick();
	}

	public void actionPerformed(ActionEvent e)
	{
		int lt = c_view.term.getText().length();

		for(int i = 0;i < 10;i++) {
			if(e.getSource() == c_view.numbers[i] && lt < 20)
			{
				for(int j = 0;j < 19;j++) {
					c_view.functions[j].setEnabled(true);
				}
				if(showResult == 1)
				{
					PrepareCurrentOp(c_view);
					lt = c_view.term.getText().length();
					c_model.reset();
				}
				if(lt == 1 && c_view.term.getText().charAt(0) == '0') {
					c_view.term.setText(c_view.term.getText().substring(1, lt)+String.valueOf(i));
				}
				else {
					c_view.term.setText(c_view.term.getText()+String.valueOf(i));
				}
			}
		}
		if(e.getSource() == c_view.jOn)
		{
			c_view.jOn.setEnabled(false);
			c_view.jOff.setEnabled(true);
			for(int i = 0;i < 10;i++) {
				c_view.numbers[i].setEnabled(true);
			}
			for(int i = 0;i < 19; i++) {
				c_view.functions[i].setEnabled(true);
			}
		}
		if(e.getSource() == c_view.jOff)
		{
			c_view.jOn.setEnabled(true);
			c_view.jOff.setEnabled(false);
			for(int i = 0;i < 10;i++) {
				c_view.numbers[i].setEnabled(false);
			}
			for(int i = 0;i < 19;i++) {
				c_view.functions[i].setEnabled(false);
			}
			PrepareCurrentOp(c_view);
			c_model = new CalculatorModel();
		}
		if(e.getSource() == c_view.MemClear)
		{
			c_model.SetMemory("0");
			showResult = 1;
		}
		if(e.getSource() == c_view.MemRecall)
		{
			c_view.term.setText(c_model.GetMemory());
			showResult = 1;
		}
		if(e.getSource() == c_view.MemAdd)
		{
			c_model.ModifyMemory(c_view.term);
			showResult = 1;
		}
		if(e.getSource() == c_view.MemSub)
		{
			c_model.InvertSign(c_view.term);
			c_model.ModifyMemory(c_view.term);
			showResult = 1;
		}
		if(e.getSource() == c_view.MemStore)
		{
			c_model.SetMemory(c_view.term.getText());
			showResult = 1;
		}
		if(e.getSource() == c_view.Percent)
		{
			if(lt > 0) {
				c_view.term.setText(c_model.CalcPercent(c_view.term));
			}
			showResult = 1;
		}
		if(e.getSource() == c_view.ClearEntry) {
			c_view.term.setText("");
		}
		if(e.getSource() == c_view.Clear)
		{
			PrepareCurrentOp(c_view);
			c_model.reset();
		}
		if(e.getSource() == c_view.ErrToLeft)
		{
			if(lt > 0) {
				c_view.term.setText(c_view.term.getText().substring(0, lt - 1));
			}
		}
		if(e.getSource() == c_view.Inverted)
		{
			if(lt > 0) {
				try {
					c_view.term.setText(c_model.InvertNr(c_view.term));
				}
				catch(ArithmeticException z)
				{
					c_view.term.setText("Nu se poate \u00eemp\u0103r\u021bi la zero");
					for(int i = 0;i < 19;i++) {
						c_view.functions[i].setEnabled(false);
					}
				}
			}
			showResult = 1;
		}
		if(e.getSource() == c_view.Square)
		{
			if(lt > 0) {
				c_view.term.setText(c_model.CalcSquare(c_view.term));
			}
			showResult = 1;
		}
		if(e.getSource() == c_view.SquareRoot)
		{
			if(lt > 0) {
				c_view.term.setText(c_model.CalcSquareRoot(c_view.term));
			}
			showResult = 1;
		}
		if(e.getSource() == c_view.Division)
		{
			if(sign == -1 && lt > 0) {
				c_model.InvertSign(c_view.term);
			}
			if(lt == 0) {
				c_model.reset();
			}
			c_model.Division(c_view.expr, c_view.term);
			UpdateExpr(c_view.expr, c_view.term, "\u00f7");
			sign = 1;
		}
		if(e.getSource() == c_view.Multiply)
		{
			if(sign == -1 && lt > 0) {
				c_model.InvertSign(c_view.term);
			}
			if(lt == 0) {
				c_model.reset();
			}
			c_model.Multiply(c_view.expr, c_view.term);
			UpdateExpr(c_view.expr, c_view.term, "\u00d7");
			sign = 1;
		}
		if(e.getSource() == c_view.Sub)
		{
			if(sign == -1 && lt > 0) {
				c_model.InvertSign(c_view.term);
			}
			if(lt > 0) {
				c_model.AddRezultat(c_view.expr, c_view.term);
			}
			UpdateExpr(c_view.expr, c_view.term, "-");
			sign = -1;
		}
		if(e.getSource() == c_view.Add)
		{
			if(sign == -1 && lt > 0) {
				c_model.InvertSign(c_view.term);
			}
			if(lt > 0) {
				c_model.AddRezultat(c_view.expr, c_view.term);
			}
			UpdateExpr(c_view.expr, c_view.term, "+");
			sign = 1;
		}
		if(e.getSource() == c_view.InvertSign)
		{
			if(lt > 0) {
				c_model.InvertSign(c_view.term);
			}
		}
		if(e.getSource() == c_view.Decimal) {
			if(lt > 0 && isDecimal == 0)
			{
				c_view.term.setText(c_view.term.getText()+".");
				isDecimal = 1;
			}
		}
		if(e.getSource() == c_view.Equals)
		{
			if(lt > 0)
			{
				if(sign == -1) {
					c_model.InvertSign(c_view.term);
				}
				if(showResult == 0)
				{
					try
					{
						c_model.AddRezultat(c_view.expr, c_view.term);
						c_view.term.setText(c_model.GetRez());
					}
					catch(ArithmeticException z)
					{
						c_view.term.setText("Nu se poate \u00eemp\u0103r\u021bi la zero");
						for(int i = 0;i < 19;i++) {
							c_view.functions[i].setEnabled(false);
						}
					}
					c_view.expr.setText("");
					showResult = 1;
				}
				else
				{
					c_model.Equals(c_view.expr, c_view.term);
					c_view.term.setText(c_model.GetRez());
					c_view.expr.setText("");
				}
			}
			c_model.resetResult();
			sign = 1;
		}
	}

	private void UpdateExpr(JTextField expr, JTextField term, String string)
	{
		c_view.WriteExpr(expr, term, string);
		isDecimal = 0;
		showResult = 0;
	}

	private void PrepareCurrentOp(CalculatorView c_view2)
	{
		c_view2.expr.setText("");
		c_view2.term.setText("");
		showResult = 0;
	}

}
