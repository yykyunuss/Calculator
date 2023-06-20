package numberconversionserviceimpl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import numberconversionservice.NumberConversionService;

/**
 * Converts number-to-word and word-to-number
 * @author yahya yunus küçük
 * @version 1.0
 * @since 2023-06-20
 */
public class NumberConversionServiceImpl implements NumberConversionService{
	
	List<String> oneDigitsTUR = Arrays.asList("", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz");
	List<String> twoDigitsTUR = Arrays.asList("on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan");
	List<String> powersTUR = Arrays.asList("bin", "milyon", "milyar", "trilyon", "katrilyon",
			"kentilyon", "sekstilyon", "septilyon", "oktilyon", "nonilyon", "desilyon");
	
	List<String> oneDigitsENG = Arrays.asList("", "one", "two", "three", "four", "five", "six", "seven", "eight",
			"nine");
	List<String> tenToTwenty = Arrays.asList("", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen");
	List<String> twoDigitsENG = Arrays.asList("ten", "twenty", "thirty", "fourty", "fifthy", "sixty", "seventy", "eighty", "ninety");
	List<String> powersENG = Arrays.asList("thousand", "million",
			"billion", "trillion", "quadrillion", "quintillion", "sextillion", "septillion", "octillion", "nonillion", "decillion");
	
	/**
	 * Gets string type number
	 * Convert String to String Array
	 * Iterates the array and converts every word to a digit
	 *  
	 * @param numberString is String type number
	 * @return sum is BigInteger type result
	 */
	@Override
	public BigInteger convertToNumber(String numberString) {
		if(numberString.equals("sıfır"))
			return BigInteger.valueOf(0);
		
		String[] wordArray = numberString.split("\\s+");		
		BigInteger base = BigInteger.valueOf(10);			
		BigInteger result = BigInteger.valueOf(0); 	
		BigInteger sum = BigInteger.valueOf(0); 
			
		boolean isNegative = false;
			
		for(int i=0; i<wordArray.length; i++) {
			if(wordArray[i].equals("yüz")) {
				if(result.intValue() == 0)
					result = BigInteger.valueOf(100);
				else
					result = result.multiply(BigInteger.valueOf(100L));
			}
			else if(wordArray[i].equals("bin")) {
				if(result.intValue() == 0)
					result = BigInteger.valueOf(1000);
				else
					result = result.multiply(BigInteger.valueOf(1000L));
				sum = sum.add(result);
				result = BigInteger.valueOf(0);
			}
			else {
				if(powersTUR.contains(wordArray[i])) {
					result = result.multiply(new BigInteger("" + base.pow(3*(powersTUR.indexOf(wordArray[i])+1))));
					sum = sum.add(result);
					result = BigInteger.valueOf(0);
				}
				else if(wordArray[i].equals("eksi")) {
					isNegative = true;
					continue;
				}
				else
					result = result.add(BigInteger.valueOf(getOneAndTwoDigits(wordArray[i])));
			}	
		}
		return isNegative ? sum.add(result).multiply(BigInteger.valueOf(-1)) : sum.add(result);
	}

	/**
	 * Gets numeric type Turkish input 
	 * Iterates number digits and in each iteration find word type Turkish translation of the digit
	 * Append translation to a String Builder
	 * Then, returns builder
	 * 
	 * @param number is numeric type input
	 * @return builder.toString()
	 */
	@Override
	public String convertToTurkishWord(BigInteger number) {
		if(number.longValue() == 0)
			return "sıfır";
		
		StringBuilder builder = new StringBuilder();	
	
		boolean isNegative = false;
		if(!(number.abs().equals(number))) {
			isNegative = true;
			number = number.abs();
		}
		BigInteger base = BigInteger.valueOf(10);	
		int len = number.toString().length();
		BigInteger blockNum = BigInteger.valueOf(0);
		
		for(int i=len; i>0; i--) {
			BigInteger division = number.divide(base.pow(i-1));
 
			if(i%3==0) {
				if(division.intValue()==1)
					builder.append(" yüz ");
				else if(division.intValue()==0) {}
				else
					builder.append(oneDigitsTUR.get(division.intValue()) + " yüz ");
				blockNum = blockNum.add(division.multiply(BigInteger.valueOf(100)));
			}
			else if(i%3==2) {
				if(division.intValue()!=0)
					builder.append(twoDigitsTUR.get(division.intValue()-1) + " ");
				blockNum = blockNum.add(division.multiply(BigInteger.valueOf(10)));
			}
			else {	
				blockNum = blockNum.add(division);
				if(blockNum.intValue()==0) {
					blockNum = BigInteger.valueOf(0);
					continue;
				}
				if(i==1)
					builder.append(oneDigitsTUR.get(division.intValue()) + "");
				else if(i==4) {
					if(blockNum.intValue()==1 && division.intValue()==1)
						builder.append(" bin ");
					else
						builder.append(oneDigitsTUR.get(division.intValue()) + " bin ");
				}
				else
					builder.append(oneDigitsTUR.get(division.intValue()) + " " + powersTUR.get(i/3-1) + " ");
				blockNum = BigInteger.valueOf(0);
			}
			number = number.mod(base.pow(i-1));
		}
		
		return isNegative ? "eksi " + builder.toString() : builder.toString();
	}

	/**
	 * Gets numeric type input 
	 * Iterates number digits and in each iteration find word type English translation of the digit
	 * Append translation to a String Builder
	 * Then, returns builder
	 * 
	 * @param number is numeric type input
	 * @return builder.toString()
	 */
	@Override
	public String convertToEnglishWord(BigInteger number) {
		if(number.longValue() == 0)
			return "zero";
		
		StringBuilder builder = new StringBuilder();	
	
		boolean isNegative = false;
		if(!(number.abs().equals(number))) {
			isNegative = true;
			number = number.abs();
		}
		BigInteger base = BigInteger.valueOf(10);	
		int len = number.toString().length();
		BigInteger blockNum = BigInteger.valueOf(0);
		
		boolean isBetweenTenAndTwenty = false;
		for(int i=len; i>0; i--) {
			BigInteger division = number.divide(base.pow(i-1));
			if(i%3==0) {
				if(division.intValue()==0) {}
				else
					builder.append(oneDigitsENG.get(division.intValue()) + " hundred ");
				blockNum = blockNum.add(division.multiply(BigInteger.valueOf(100)));
			}
			else if(i%3==2) {
				if(division.intValue()!=0)
					builder.append(twoDigitsENG.get(division.intValue()-1) + " ");
				if(division.intValue()==1)
					isBetweenTenAndTwenty=true;
				blockNum = blockNum.add(division.multiply(BigInteger.valueOf(10)));
			}
			else {	
				blockNum = blockNum.add(division);
				if(blockNum.intValue()==0) {
					blockNum = BigInteger.valueOf(0);
					continue;
				}
				if(isBetweenTenAndTwenty) {
					if(number.intValue()!=0) {
						builder.replace(builder.lastIndexOf("ten"), builder.length(), "");
						builder.append(" " + tenToTwenty.get(division.intValue()) + " ");
					}
					if(i==1){
						//builder.append(" ten ");
					}
					else if(i==4)
						builder.append(" thousand ");
					else
						builder.append(" " + powersENG.get(i/3-1) + " ");
					isBetweenTenAndTwenty=false;
					number = number.mod(base.pow(i-1));
					continue;
				}
				if(i==1)
					builder.append(oneDigitsENG.get(division.intValue()) + "");
				else if(i==4)
					builder.append(oneDigitsENG.get(division.intValue()) + " thousand ");
				else
					builder.append(oneDigitsENG.get(division.intValue()) + " " + powersENG.get(i/3-1) + " ");
				blockNum = BigInteger.valueOf(0);
			}
			number = number.mod(base.pow(i-1));
		}	
		return isNegative ? "minus " + builder.toString() : builder.toString();
	}
	
	public long getOneAndTwoDigits(String word) {
		switch(word) {
		case "sıfır":
			return 0;
		case "bir":
			return 1;
		case "iki":
			return 2;
		case "üç":
			return 3;
		case "dört":
			return 4;
		case "beş":
			return 5;
		case "altı":
			return 6;
		case "yedi":
			return 7;
		case "sekiz":
			return 8;
		case "dokuz":
			return 9;
		case "on":
			return 10;
		case "yirmi":
			return 20;
		case "otuz":
			return 30;
		case "kırk":
			return 40;
		case "elli":
			return 50;
		case "altmış":
			return 60;
		case "yetmiş":
			return 70;
		case "seksen":
			return 80;
		case "doksan":
			return 90;
		default:
			return 0;
		}
	}
}