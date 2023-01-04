import javax.swing.*; //frames, buttons, text fields, panels
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.concurrent.*;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* The Java ActionListener is notified whenever you click on the button or menu item.
It is notified against ActionEvent.
The ActionListener interface is found in java.awt.event package.
It has only one method: actionPerformed().*/
public class Calculator implements ActionListener{
	
	//variables for displaying calculator window
	BigDecimal round2; //for rounding solution
	Random rand = new Random(); //generates rand number for bab's polynomial's
	double x1, x2; //factored coefficients for babe mode
	int A, B, C, D, a, b, c; //polynomial coefficients for babe mode
	int correctCount = 0, negCount = 0; //checks if both X values are found in babe mode
	JFrame frame, instructions; //provides a window on the screen
	JTextArea textArea; //text area for instructions window
	JTextField textfield; //allows the editing of a single line of text
	JButton[] numButtons = new JButton[10]; //holds all the number buttons
	JButton[] funcButtons = new JButton[14]; //holds all the function buttons (add,sub, etc)
	//variable names for all function buttons
	JButton addButt, subButt, mulButt, divButt, decButt,
	equButt, delButt, clrButt, negButt, expButt, rootButt,
	lnButt, logButt, BabeButt; 
	String string; //used in actionListener
	boolean clrCheck = false, babeCheck = false, firstPress = false, findCo = true; //checks if equal button is pressed for when expButt is used
	JPanel panel; //holds separate buttons and organizes them on the window
	
	Font taeedFont = new Font("Arial", Font.BOLD, 15); //font for calculator window display
	Font insFont = new Font("Monospaced", Font.PLAIN, 15); //font for instructions window
	
	//variables for doing calculations
	double num1=0, num2=0, result=0;
	char operator; //for add, sub, mul, div
	
	Calculator(){
		
		//initialize instructions window
		instructions = new JFrame("Instructions for Taeed's Calculator");
		instructions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instructions.setSize(450, 380);
		instructions.setLayout(null);
		textArea = new JTextArea();
		textArea.setBounds(10, 10, 430, 360);
		textArea.setFont(insFont);
		textArea.setEditable(false);
		textArea.setText("      Welcome to Taeed's Calculator!\n "
				+ "Here is how to use it. This calculator is\n"
				+ "like any other calculator with a special\n"
				+ " button, the BABE button! Made for\n"
				+ "Atusa Shokri. It can add, subtract, multiply,\n"
				+ "divide, use exponents, take roots and logs.\n"
				+ "However, if you press the Babe button, the\n"
				+ "calculator will generate a polynomial and\n"
				+ "ask you to solve for x. Just type in one\n"
				+ "solution for x and click equal to see if you\n"
				+ "were right. Then do the same for the second x\n"
				+ "value. Keep clicking the babe button to\n"
				+ "generate more problems. Enjoy solving and\n"
				+ "factoring polynomials! Answers are rounded to\n"
				+ "two decimal places.");
		
		//initialize calculator window
		frame = new JFrame("Taeed's Calculator");  //initializes the frame (window)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //window closes when pressing exit button
		frame.setSize(420, 550); //size of window
		frame.setLayout(null); //using no layout manager. LayoutManager interface* and determines the size and position of the components within a container
		
		//initialize the calculator display
		textfield = new JTextField();//initialize text field
		textfield.setBounds(50, 10, 300, 50); //make size and location of text field
		textfield.setFont(taeedFont); // font of text field
		textfield.setEditable(false);//makes it so that user cannot type into the text field
		
		//initialize the function buttons and give them text to display
		addButt = new JButton("+"); 
		subButt = new JButton("-");
		mulButt = new JButton("*");
		divButt = new JButton("/");
		decButt = new JButton(".");
		equButt = new JButton("=");
		delButt = new JButton("delete");
		clrButt = new JButton("clr");
		negButt = new JButton("(-)");
		expButt = new JButton("^");
		rootButt = new JButton("root");
		lnButt = new JButton("ln");
		logButt = new JButton("log");
		BabeButt = new JButton("Babe Button");
		
		//put function variables in the function button array
		funcButtons[0] = addButt;
		funcButtons[1] = subButt;
		funcButtons[2] = mulButt;
		funcButtons[3] = divButt;
		funcButtons[4] = decButt;
		funcButtons[5] = equButt;
		funcButtons[6] = delButt;
		funcButtons[7] = clrButt;
		funcButtons[8] = negButt;
		funcButtons[9] = expButt;
		funcButtons[10] = rootButt;
		funcButtons[11] = lnButt;
		funcButtons[12] = logButt;
		funcButtons[13] = BabeButt;
		
		//giving each button ability to be clicked and used 
		for(int i = 0; i < 14; i++) {
			funcButtons[i].addActionListener(this);
			funcButtons[i].setFont(taeedFont);
			funcButtons[i].setFocusable(true); //outline around button when hovering over it
		}
		
		//initializing the number buttons
		for(int i = 0; i < 10; i++) {
			numButtons[i] = new JButton(String.valueOf(i));
			numButtons[i].addActionListener(this);
			numButtons[i].setFont(taeedFont);
			numButtons[i].setFocusable(false);
		}
		
		//bounds of neg, del, clr
		negButt.setBounds(50, 390, 100, 50);
		delButt.setBounds(150, 390, 100, 50);
		clrButt.setBounds(250, 390, 100, 50);
		BabeButt.setBounds(125, 445, 150, 50);
		
		//panel for bounds of numbers, add, sub, mul, div, exp
		panel = new JPanel();
		panel.setBounds(50, 65, 300, 320);
		panel.setLayout(new GridLayout(5,4,5,5));
		
		//making the buttons appear on the panel grid
		panel.add(numButtons[7]);
		panel.add(numButtons[8]);
		panel.add(numButtons[9]);
		panel.add(funcButtons[0]);
		panel.add(numButtons[4]);
		panel.add(numButtons[5]);
		panel.add(numButtons[6]);
		panel.add(funcButtons[1]);
		panel.add(numButtons[1]);
		panel.add(numButtons[2]);
		panel.add(numButtons[3]);
		panel.add(funcButtons[2]);
		panel.add(numButtons[0]);
		panel.add(funcButtons[4]);
		panel.add(funcButtons[5]);
		panel.add(funcButtons[3]);
		panel.add(funcButtons[9]);
		panel.add(funcButtons[10]);
		panel.add(funcButtons[11]);
		panel.add(funcButtons[12]);
		
		//making the various objects appear in the frame
		frame.add(BabeButt);
		frame.add(negButt);
		frame.add(panel);
		frame.add(delButt);
		frame.add(clrButt);
		frame.add(textfield); //adds text field to frame
		frame.setVisible(true);
		
		//setting up instructions window making things display
		instructions.setVisible(true);
		instructions.add(textArea);
	}
	
	public static void main(String[] args) {
	
		Calculator calc = new Calculator();
	}
	
	// implements actionPerformed() from ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//checks if number button is pressed and concatonates a point to text field
		for (int i = 0; i < 10; i++) {
			if(e.getSource() == numButtons[i]) {
				if (babeCheck == true && firstPress == true) {
					textfield.setText("");
					firstPress = false;
				}
				if (clrCheck == true) {
					textfield.setText("");
					clrCheck = false;
				}
				textfield.setText(textfield.getText().concat(String.valueOf(i)));
			}
		}
		
		//checks if decimal button is pressed and concatonates a point to text field
		if(e.getSource() == decButt) {
			string = textfield.getText();
			for(int i = 0; i < string.length(); i++) { 
				if(string.charAt(i) == '.') {
					return;
				}
			}
			textfield.setText(textfield.getText().concat("."));
		}
		
		//checks if add button is pressed and gives text in text field to num1
		if(e.getSource() == addButt) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '+';
			textfield.setText("");
		}
		
		//checks if sub button is pressed and gives text in text field to num1
		if(e.getSource() == subButt) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '-';
			textfield.setText("");
		}
		
		//checks if sub button is pressed and gives text in text field to num1
		if(e.getSource() == mulButt) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '*';
			textfield.setText("");
		}
		
		//checks if sub button is pressed and gives text in text field to num1
		if(e.getSource() == divButt) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '/';
			textfield.setText("");
		}
		
		//checks if exponent button is pressed
		if(e.getSource() == expButt) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '^';
			textfield.setText("");
		}
		
		//checks if root button is pressed
		if(e.getSource() == rootButt) {
			num1 = Double.parseDouble(textfield.getText());
			operator = 'r';
			textfield.setText("");
		}
		
		if(e.getSource() == lnButt) {
			num1 = Double.parseDouble(textfield.getText());
			num1 = Math.log(num1);
			textfield.setText(String.valueOf(num1));
		}
		
		if(e.getSource() == logButt) {
			num1 = Double.parseDouble(textfield.getText());
			num1 = Math.log10(num1);
			textfield.setText(String.valueOf(num1));
		}
		
		//checks if equal button is pressed and gives text in text field to num2
		if(e.getSource() == equButt) {
			num2 = Double.parseDouble(textfield.getText());
			//determines what math operation to perform on num1 and num2
			//put all operation calculations here 
			switch(operator) {
			case '+':
				result = num1 + num2;
				break;
			case '-':
				result = num1 - num2;
				break;
			case '*':
				result = num1 * num2;
				break;
			case '/':
				result = num1 / num2;
				break;
			case '^':
				result = num1;
				for(int i = 0; i < num2 - 1; i++) {
					result = result * num1;
				}
				break;
			case 'r':
				result = Math.pow(num1, 1 / num2);
				break;
			default:
				textfield.setText("ERROR");
			}
			//updates text field to result
			textfield.setText(String.valueOf(result));
			//sets num1 to result in case we want go use result in another calculation
			num1 = result;
			clrCheck = true;
			//checks which mode calculator is in and sets accuracy based on that
			if (babeCheck == true) {
				round2 = new BigDecimal(result);
				round2 = round2.setScale(2, RoundingMode.HALF_UP);
				result = round2.doubleValue();
			}
			//when in babe mode the equal button operates below
			if (babeCheck == true && correctCount < 2) {
				if (num2 == x1 || num2 == x2 || result == x1 || result == x2) { //checks if user input a correct solution
					textfield.setText("That's Right Babe!");
					correctCount++;
					}
				else {
					textfield.setText("Try again.");
				}
				firstPress = true;
			}
			if (babeCheck == true && correctCount >= 2) {
				textfield.setText("You found all the solutions!");
				babeCheck = false;
				correctCount = 0;
				firstPress = true;
			}
		}
		
		//checks if clear button is pressed and sets text field to emty space
		if(e.getSource() == clrButt) {
			textfield.setText("");
			babeCheck = false;
		}
		
		//checks if delete button is pressed
		if(e.getSource() == delButt) {
			string = textfield.getText(); //stores text from text field in string
			textfield.setText(""); //clears the text field
			for(int i = 0; i < string.length()-1; i++) { //loops through the length of the string minus one and rewrites it
				textfield.setText(textfield.getText()+string.charAt(i));
			}
		}
		
		//checks if negative buttoned is pressed
		if(e.getSource() == negButt) {
			double negNum = Double.parseDouble(textfield.getText())*(-1);
			textfield.setText(String.valueOf(negNum));
		}
		
		//Checks if Babe Button is pressed
		if(e.getSource() == BabeButt) {
			//If Babe button is pressed twice ends babe mode
			if (babeCheck == true) {
				babeCheck = false;
				firstPress = true;
				textfield.setText("Exited Babe Mode");
				return;
			}
			//randomly generate factored form coefficients
			A = rand.nextInt(9) + 1; // add 1 so that never equal to 0
			B = rand.nextInt(9) + 1;
			C = rand.nextInt(9) + 1;
			D = rand.nextInt(9) + 1;
			//changes which factored coefficient are negative
			if (negCount == 1) {
				A = (-1)*A;
			}
			else if (negCount == 2) {
				A = (-1)*A;
				C = (-1)*C;
			}
			else if (negCount == 3) {
				B = (-1)*B;
			}
			else if (negCount == 4) {
				B = (-1)*B;
				D = (-1)*D;
			}
			else if (negCount == 5) {
				C = (-1)*C;
			}
			else if (negCount == 6) {
				D = (-1)*D;
			}
			else if (negCount > 6) {
				negCount= 0;
			}
			negCount++;
			//calculates solution
			x1 = (-1*(double)B)/(double)A;
			x2 = (-1*(double)D)/(double)C;
			round2 = new BigDecimal(x1);
			round2 = round2.setScale(2, RoundingMode.HALF_UP);
			x1 = round2.doubleValue();
			round2 = new BigDecimal(x2);
			round2 = round2.setScale(2, RoundingMode.HALF_UP);
			x2 = round2.doubleValue();
			//checking answers
			System.out.println("A: " + A + "B: " + B + "C: " + C + "D: " + D);
			System.out.println(x1 + "  " + x2);
			System.out.println(negCount - 1);
			//creating polynomial coefficients
			a = A*C;
			b = A*D + B*C;
			c = B*D;
			//writes question in text field
			textfield.setText("Solve for x:    "+a+"x^2 + "+b+"x + "+c+" = 0");
			babeCheck = true;
			firstPress = true;
		}
	}
}