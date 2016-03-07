package wenjing.parsing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class srcExactor {
	public static String getSrc(String pattern,String Src){
		//String regEx="来源：(.*)20";   
		Pattern p = Pattern.compile(pattern);   
		Matcher m = p.matcher(Src);   
	    while(m.find())
        {

              return m.group(1);
            
        }  
	    return null;
	}
}
