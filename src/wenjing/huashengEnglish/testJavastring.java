package wenjing.huashengEnglish;

import wenjing.parsing.util.timeStampExactor;

public class testJavastring {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data = "March 12, 2011  ";
		
		data = data.replace("<br />", "");
		data = data.trim();
		String afterString = null;
		
		if(data.contains("June")){
			afterString = data.replace("June", "07,");
		}else if (data.contains("January")){
			afterString = data.replace("January", "01,");
		}else if (data.contains("February")){
			afterString = data.replace("February", "02,");
		}else if (data.contains("March")){
			afterString = data.replace("March", "03,");
		}else if (data.contains("April")){
			afterString = data.replace("April", "04");
		}else if (data.contains("May")){
			afterString = data.replace("May", "05,");
		}else if (data.contains("July")){
			afterString = data.replace("July", "06,");
		}else if (data.contains("August")){
			afterString = data.replace("August", "08,");
		}else if (data.contains("September")){
			afterString = data.replace("September", "09,");
		}else if (data.contains("October")){
			afterString = data.replace("October", "10,");
		}else if (data.contains("November")){
			afterString = data.replace("November", "11,");
		}else if (data.contains("December")){
			afterString = data.replace("December", "12,");
		}
		System.out.println(afterString.length());
		if(afterString.length()==11){
			afterString=afterString.replaceFirst(" ", "0");
		}
		
		System.out.println(afterString.substring(3, 4));
		
		System.out.println(afterString.charAt(3)+"12");
		
		System.out.println(afterString);
		//yyyy年MM月dd日
		String timeSamp = timeStampExactor.transFromNormalDateToUnixDate(afterString, "MM,dd, yyyy");
		System.out.println(timeSamp);
	}
	
}
