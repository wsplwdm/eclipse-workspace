import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delay {
	public void readline(String in, String out , long ms , int framerate) throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader(in)); 
		System.out.println(in);
		String strLine;
    	
    	Pattern datePatt = Pattern.compile( "(\\{)(\\d*)(\\})(\\{)(\\d*)(\\})");
    	
    	while ((strLine = br.readLine()) != null){
    		Matcher m = datePatt.matcher(strLine);
    	}
		
	}
	public void delay(const char in, const char out,int delay, int fps) {
		
	}


}
