package numberconversionservice;

import java.math.BigInteger;

public interface NumberConversionService {
	
	 public BigInteger convertToNumber(String numberString);
	 public String convertToTurkishWord(BigInteger number);
	 public String convertToEnglishWord(BigInteger number);
	 
}
