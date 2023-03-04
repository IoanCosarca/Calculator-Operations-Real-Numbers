import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
class CalculatorView extends JFrame {
	protected JRadioButton jOn 		= new JRadioButton("ON");
	protected JRadioButton jOff 	= new JRadioButton("OFF");
	protected JTextField expr		= new JTextField();
	protected JTextField term		= new JTextField();
	
	protected JButton MemClear 		= new JButton("MC");
	protected JButton MemRecall 	= new JButton("MR");
	protected JButton MemAdd 		= new JButton("M+");
	protected JButton MemSub 		= new JButton("M-");
	protected JButton MemStore 		= new JButton("MS");
	protected JButton Percent 		= new JButton("%");
	protected JButton ClearEntry 	= new JButton("CE");
	protected JButton Clear 		= new JButton("C");
	protected JButton ErrToLeft 	= new JButton("\u232B");
	protected JButton Inverted 		= new JButton("\u215fx");
	protected JButton Square 		= new JButton("x\u00b2");
	protected JButton SquareRoot 	= new JButton("\u00b2\u221ax");
	protected JButton Division 		= new JButton("\u00f7");
	protected JButton Multiply 		= new JButton("\u00d7");
	protected JButton Sub 			= new JButton("-");
	protected JButton Add 			= new JButton("+");
	protected JButton InvertSign 	= new JButton("+/-");
	protected JButton Decimal 		= new JButton(".");
	protected JButton Equals 		= new JButton("=");
	
	protected JButton[] numbers 	= new JButton[10];
	protected JButton[] functions 	= new JButton[19];
	
	Font FontBtn = new Font("", Font.BOLD, 25);
	Font FontRadio = new Font("Arial", Font.ITALIC, 20);
	
	CalculatorView()
	{
		JFrame content = new JFrame("Calculator");
		content.setSize(550, 650);
		content.setLocationRelativeTo(null);	//centreaza aplicatia pe ecran
		content.setResizable(false);
		content.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content.setTitle("Calculator");
		content.setLayout(null);
		
		jOn.setBounds(0, 0, 55, 50);
		jOn.setFont(FontRadio);
		jOff.setBounds(65, 0, 65, 50);
		jOff.setFont(FontRadio);
		ButtonGroup group = new ButtonGroup();
		group.add(jOn);
		group.add(jOff);		
		content.add(jOn);
		content.add(jOff);
		
		expr.setBounds(150, 10, 380, 45);
		expr.setHorizontalAlignment(SwingConstants.RIGHT);
		expr.setEditable(false);
		expr.setFont(new Font("", Font.PLAIN, 20));
		content.add(expr);
		
		term.setBounds(5, 55, 525, 55);
		term.setHorizontalAlignment(SwingConstants.RIGHT);	//aliniere text expresie la dreapta
		term.setEditable(false);
		term.setFont(new Font("", Font.BOLD, 40));
		content.add(term);
		
		functions[0]  = MemClear;
		functions[1]  = MemRecall;
		functions[2]  = MemAdd;
		functions[3]  = MemSub;
		functions[4]  = MemStore;
		functions[5]  = Percent;
		functions[6]  = ClearEntry;
		functions[7]  = Clear;
		functions[8]  = ErrToLeft;
		functions[9] = Inverted;
		functions[10] = Square;
		functions[11] = SquareRoot;
		functions[12] = Division;
		functions[13] = Multiply;
		functions[14] = Sub;
		functions[15] = Add;
		functions[16] = InvertSign;
		functions[17] = Decimal;
		functions[18] = Equals;
		
		for(int i = 0;i < 19;i++)
		{
			functions[i].setFont(FontBtn);
			functions[i].setFocusable(false);
		}
		for(int i = 0;i < 10;i++)
		{
			numbers[i] = new JButton(String.valueOf(i));
			numbers[i].setFont(FontBtn);
			numbers[i].setFocusable(false);
		}
		MemClear.setBounds(5, 125, 105, 75);
		MemRecall.setBounds(110, 125, 105, 75);
		MemAdd.setBounds(215, 125, 105, 75);
		MemSub.setBounds(320, 125, 105, 75);
		MemStore.setBounds(425, 125, 105, 75);
		content.add(MemClear);
		content.add(MemRecall);
		content.add(MemAdd);
		content.add(MemSub);
		content.add(MemStore);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 200, 525, 405);
		panel.setLayout(new GridLayout(6, 4));
		
		panel.add(functions[5]);
		panel.add(functions[6]);
		panel.add(functions[7]);
		panel.add(functions[8]);
		panel.add(functions[9]);
		panel.add(functions[10]);
		panel.add(functions[11]);
		panel.add(functions[12]);
		panel.add(numbers[7]);
		panel.add(numbers[8]);
		panel.add(numbers[9]);
		panel.add(functions[13]);
		panel.add(numbers[4]);
		panel.add(numbers[5]);
		panel.add(numbers[6]);
		panel.add(functions[14]);
		panel.add(numbers[1]);
		panel.add(numbers[2]);
		panel.add(numbers[3]);
		panel.add(functions[15]);
		panel.add(functions[16]);
		panel.add(numbers[0]);
		panel.add(functions[17]);
		panel.add(functions[18]);
		
		content.add(panel);
		content.setVisible(true);
	}

	public void WriteExpr(JTextField expr2, JTextField term2, String string)
	{
		int le = expr2.getText().length();
		int lt = term2.getText().length();
		if(lt > 0) {
			if(le > 0)
			{
				if(expr2.getText().charAt(le - 1) == '-')
				{
					if(term2.getText().charAt(0) == '-') {
						expr2.setText(expr2.getText().substring(0, le - 1)+term2.getText()+string);
					}
					else expr2.setText(expr2.getText().substring(0, le - 1)+"+"+term2.getText()+string);
				}
				else if(expr2.getText().charAt(le - 1) == '+' && term2.getText().charAt(0) == '-') {
					expr2.setText(expr2.getText().substring(0, le - 1)+term2.getText()+string);
				}
				else expr2.setText(expr2.getText()+term2.getText()+string);
			}
			else expr2.setText(expr2.getText()+term2.getText()+string);
		}
		else {
			if(le > 0) {
				if(expr2.getText().charAt(le - 1) == '-' || expr2.getText().charAt(le - 1) == '+') {
					expr2.setText(expr2.getText().substring(0, le - 1)+string);
				}
				else expr2.setText(expr2.getText().substring(0, le)+string);
			}
			else expr2.setText(expr2.getText()+"");
		}
		term2.setText("");
	}

}
