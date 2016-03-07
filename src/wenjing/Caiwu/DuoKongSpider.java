package wenjing.Caiwu;

public class DuoKongSpider {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		eastMoney crawler = new eastMoney(
				"/home/wenjing/Desktop/caiwutong/eastMoney");
		
		crawler.addSeed("http://finance.eastmoney.com/yaowen.html");
		
		crawler.setThreads(5);
		crawler.setResumable(false);
		
		
		try {
			crawler.start(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jinrongSpider crawler2 = new jinrongSpider(
				"/home/wenjing/Desktop/caiwutong/jinrongSpider");
		
		crawler2.addSeed("http://opinion.jrj.com.cn/");
		
		crawler2.setThreads(5);
		crawler2.setResumable(false);
		
		
		try {
			crawler2.start(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sinaCaiwuSpider crawler3 = new sinaCaiwuSpider(
				"/home/wenjing/Desktop/caiwutong/sinaCaiWu");
		
		crawler3.addSeed("http://finance.sina.com.cn/stock/");
		
		crawler3.setThreads(5);
		crawler3.setResumable(false);
		
		
		try {
			crawler3.start(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
