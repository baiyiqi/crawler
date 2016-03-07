package wenjing.TestTool;

public class testJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			String testUrl = "http://www.ebiotrade.com/newsf/read.asp?page=2015417172100945";
			
			int newsNumber = testUrl.indexOf("2015");
			
			String newsNumberString = testUrl.substring(newsNumber);
			
			String RealString = "http://www.ebiotrade.com/newsf/"+"2015-"+newsNumberString.charAt(4) + "/"+newsNumberString+".htm";
			
			System.out.println(newsNumberString);
			System.out.println(RealString);
	}

}
