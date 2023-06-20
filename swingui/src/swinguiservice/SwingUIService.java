package swinguiservice;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import numberconversionservice.NumberConversionService;

/**
 * Creates and opens Java Swing screen,
 * Gets two number inputs in font, calculates operation result and show
 * @author yahya yunus küçük
 * @version 1.0
 * @since 2023-06-20
 */
public class SwingUIService extends JFrame{
	private JTextField firstInput;
	private JTextField secondInput;
	private JTextArea result;
	private JLabel sonuc;	
    private static ResourceBundle langResource;
	private NumberConversionService conversionService;
	
	static final List<String> englishNumbers = Arrays.asList("minus", "zero", "one", "two", "three", "four", "five", "six", "seven",
			"eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "hundred",
			"eighteen", "nineteen", "twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety",
			"thousand", "million", "billion", "trillion", "quadrillion", "quintillion", "sextillion", "septillion", "octillion", "nonillion", "decillion");
	
	static final List<String> turkishNumbers = Arrays.asList("sıfır", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz",
			"dokuz", "on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan", "yüz", "bin", "milyon", "milyar",
			"trilyon", "katrilyon", "kentilyon", "sekstilyon", "septilyon", "oktilyon", "nonilyon", "desilyon");
	
	/** @param salary sets the NumberConversionService */
	public void setConversionService(NumberConversionService conversionService) {
        this.conversionService = conversionService;
	}

    public void initiliazeUI() {
    	langResource = ResourceBundle.getBundle("messages", Locale.getDefault());
        new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 350);
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 3, 5, 5));
        JLabel firstNum = new JLabel("İlk Sayı");
        firstNum.setHorizontalAlignment(JLabel.CENTER);
        firstNum.setBounds(100, 100, 300, 30);
        firstNum.setFont(new Font("Arial", Font.BOLD, 30));
        
        JLabel secondNum = new JLabel("İkinci Sayı");
        secondNum.setHorizontalAlignment(JLabel.CENTER);
        secondNum.setBounds(100, 100, 300, 30);
        secondNum.setFont(new Font("Arial", Font.BOLD, 30));
        
        firstInput = new JTextField();
        firstInput.setHorizontalAlignment(JTextField.CENTER);
        firstInput.setBounds(100, 100, 300, 30);
        firstInput.setFont(new Font("Arial", Font.PLAIN, 30));

        secondInput = new JTextField();
        secondInput.setHorizontalAlignment(JTextField.CENTER);
        secondInput.setBounds(100, 100, 300, 30);
        secondInput.setFont(new Font("Arial", Font.PLAIN, 30));
     
        sonuc = new JLabel("Sonuç");
        sonuc.setHorizontalAlignment(JLabel.CENTER);
        sonuc.setBounds(100, 100, 300, 30);
        sonuc.setFont(new Font("Arial", Font.BOLD, 30));
        
        result = new JTextArea();
        result.setBounds(100, 100, 300, 30);
        result.setFont(new Font("Arial", Font.PLAIN, 30));
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        
        inputPanel.add(firstNum);
        inputPanel.add(firstInput);
        inputPanel.add(secondNum);
        inputPanel.add(secondInput);
        inputPanel.add(sonuc);
        inputPanel.add(result);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton topla = new JButton(langResource.getString("topla"));
        JButton cikar = new JButton(langResource.getString("çıkar"));
        JButton carp = new JButton(langResource.getString("çarp"));
        JButton bol = new JButton(langResource.getString("böl"));

        ActionListener additionListener = new AdditionListener();
        topla.addActionListener(additionListener);
        
        ActionListener subtractionListener = new SubtractionListener();
        cikar.addActionListener(subtractionListener);
        
        ActionListener multiplicationListener = new MultiplicationListener();
        carp.addActionListener(multiplicationListener);
        
        ActionListener divisionListener = new DivisionListener();
        bol.addActionListener(divisionListener);
   
        buttonPanel.add(topla);
        buttonPanel.add(cikar);
        buttonPanel.add(carp);
        buttonPanel.add(bol);  
        add(inputPanel, BorderLayout.CENTER); 
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * checks whether the input is Turkish or English by looking at its first word.
     * @param s is a number input in the screen.
     * @return true or false
     */
    public boolean isEnglish(String s) { 
    	String[] wordArray = s.split("\\s+");
    	for(int i=0; i<wordArray.length; i++)
    		if(!englishNumbers.contains(wordArray[i]))
    			return false;
    	return true;
    }
    
    public void closeUI() {
        dispose();
    }
    
    /**
     * Error checking method.
     * @param s is number input
     * @return true or false
     */
    public boolean isInputValid(String s) {
    	String[] wordArray = s.split("\\s+");
    	if(wordArray.length==0)
    		return false;
    	for(int i=0; i<wordArray.length; i++)
    		if(!englishNumbers.contains(wordArray[i]) && !turkishNumbers.contains(wordArray[i])
    				&&!wordArray[i].equals("eksi") && !wordArray[i].equals("minus"))
    			return false;
    	return true;
    }
    
    /**
     * Helper method for ActionListener classes
     * If number input is English, we should check Turkish translations of words.
     * And we send Turkish translations to convertToNumber method
     * @param input is number input
     * @return numeric value of the word.
     */
    public BigInteger resultOfConversionToNumber(String input) {
    	String[] wordArray = input.split("\\s+");	
		StringBuilder builder = new StringBuilder();
		
		for(int i=0; i<wordArray.length; i++)
			builder.append(langResource.getString(wordArray[i]) + " ");

		BigInteger n1 = conversionService.convertToNumber(builder.toString());  
    	return n1;
    }
    
    /**
     * Below, there are 4 ActionListener classes
     * In these classes, first we check input is correct
     * Gets two inputs and send conversion methods
     * Then, calculates the result of returning numbers 
     * Send result to convert-to-word method
     * Finally, sets the returning word to the screen
     */
    public class AdditionListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(!isInputValid(firstInput.getText()) || !isInputValid(secondInput.getText())) {
    			JOptionPane.showMessageDialog(null, "Yanlış input", "Hata", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		if(isEnglish(firstInput.getText()) && isEnglish(secondInput.getText())) {
    			langResource = ResourceBundle.getBundle("messages", new Locale("tr"));
    			
    			BigInteger n1 = resultOfConversionToNumber(firstInput.getText());
    			BigInteger n2 = resultOfConversionToNumber(secondInput.getText());
    			
    			BigInteger sum = n1.add(n2);      		
        		String s = conversionService.convertToEnglishWord(sum);
        		result.setText(s);
    		}     		
    		else {
    			BigInteger n1 = conversionService.convertToNumber(firstInput.getText());
        		BigInteger n2 = conversionService.convertToNumber(secondInput.getText());
        		BigInteger sum = n1.add(n2);      		
            	String s = conversionService.convertToTurkishWord(sum);
            	result.setText(s);
    		}
    	}
    }
    
    public class SubtractionListener implements ActionListener{ // action listener for subtraction(-)
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(!isInputValid(firstInput.getText()) || !isInputValid(secondInput.getText())) {
    			JOptionPane.showMessageDialog(null, "Yanlış input", "Hata", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		if(isEnglish(firstInput.getText()) && isEnglish(secondInput.getText())) {
    			langResource = ResourceBundle.getBundle("messages", new Locale("tr"));
    			
    			BigInteger n1 = resultOfConversionToNumber(firstInput.getText());
    			BigInteger n2 = resultOfConversionToNumber(secondInput.getText());
    			
    			BigInteger subtract = n1.subtract(n2);      		
        		String s = conversionService.convertToEnglishWord(subtract);

        		result.setText(s);
    		}     		
    		else {
    			BigInteger n1 = conversionService.convertToNumber(firstInput.getText());
        		BigInteger n2 = conversionService.convertToNumber(secondInput.getText());
        		BigInteger subtract = n1.subtract(n2);      		
        		String s = conversionService.convertToTurkishWord(subtract);
            	result.setText(s);
    		}
    	}
    }
    
    public class MultiplicationListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {	
    		if(!isInputValid(firstInput.getText()) || !isInputValid(secondInput.getText())) {
    			JOptionPane.showMessageDialog(null, "Yanlış input", "Hata", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		if(isEnglish(firstInput.getText()) && isEnglish(secondInput.getText())) {
    			langResource = ResourceBundle.getBundle("messages", new Locale("tr"));
    			
    			BigInteger n1 = resultOfConversionToNumber(firstInput.getText());
    			BigInteger n2 = resultOfConversionToNumber(secondInput.getText());
    			
    			BigInteger multiply = n1.multiply(n2);      		
        		String s = conversionService.convertToEnglishWord(multiply);

        		result.setText(s);
    		}     		
    		else {
    			BigInteger n1 = conversionService.convertToNumber(firstInput.getText());
        		BigInteger n2 = conversionService.convertToNumber(secondInput.getText());
        		BigInteger multiply = n1.multiply(n2);
            	String s = conversionService.convertToTurkishWord(multiply);
            	result.setText(s);
    		}
    	}
    }
    
    public class DivisionListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {	
    		if(!isInputValid(firstInput.getText()) || !isInputValid(secondInput.getText())) {
    			JOptionPane.showMessageDialog(null, "Yanlış input", "Hata", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		try {
    			if(isEnglish(firstInput.getText()) && isEnglish(secondInput.getText())) {
    				langResource = ResourceBundle.getBundle("messages", new Locale("tr"));
    			
    				BigInteger n1 = resultOfConversionToNumber(firstInput.getText());
    				BigInteger n2 = resultOfConversionToNumber(secondInput.getText());
    			
    				BigInteger division = BigInteger.valueOf(0);	
    				division = n1.divide(n2);
    				String s = conversionService.convertToEnglishWord(division);
    				result.setText(s);
    			}     		
    			else {
    				BigInteger n1 = conversionService.convertToNumber(firstInput.getText());
    				BigInteger n2 = conversionService.convertToNumber(secondInput.getText());
    				
    				BigInteger division = BigInteger.valueOf(0);
    				division = n1.divide(n2);	
    				String s = conversionService.convertToTurkishWord(division);
    				result.setText(s);
    			}
    		}catch(ArithmeticException exception) { // exception for divide by zero case
    			JOptionPane.showMessageDialog(null, "0'a bölme işlemi yapılamaz!", "Bölme hatası", JOptionPane.ERROR_MESSAGE);
        		exception.getMessage();
    		}
    	}
    }	
}