package wenjing.testdomo;

public class grabAllhuaSheng {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long beginTime = System.currentTimeMillis();
//		aiweiyixuewangSpider aiweiyixuewangcrawler = new aiweiyixuewangSpider(
//				"/Users/y/Desktop/testWebcollecter/data/aiwei");
		aiweiyixuewangSpider aiweiyixuewangcrawler = new aiweiyixuewangSpider(
				"/home/wenjing/Desktop/huasheng/data/aiwei");
		aiweiyixuewangcrawler
				.addSeed("http://www.elseviermed.cn/news/international/1-0");
		String pageUrl = "http://www.elseviermed.cn/news/international/";
		for (int i = 2; i < 10; i++) {
			aiweiyixuewangcrawler.addSeed(pageUrl + i + "-0");
		}

		pageUrl = "http://www.elseviermed.cn/news/local/";
		for (int i = 1; i < 10; i++) {
			aiweiyixuewangcrawler.addSeed(pageUrl + i + "-0");
		}

		pageUrl = "http://www.elseviermed.cn/news/DrugNews/";
		for (int i = 1; i < 10; i++) {
			aiweiyixuewangcrawler.addSeed(pageUrl + i + "-0");
		}

		pageUrl = "http://www.elseviermed.cn/news/MedicationSafety/";
		for (int i = 1; i < 10; i++) {
			aiweiyixuewangcrawler.addSeed(pageUrl + i + "-0");
		}

		aiweiyixuewangcrawler.setThreads(5);
		aiweiyixuewangcrawler.setResumable(false);
		try {
			aiweiyixuewangcrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		biodiscoverSpider biodiscovercrawler = new biodiscoverSpider(
//				"/Users/y/Desktop/testWebcollecter/data/biodiscoverSpider");
		biodiscoverSpider biodiscovercrawler = new biodiscoverSpider(
				"/home/wenjing/Desktop/huasheng/data/biodiscoverSpider");
		biodiscovercrawler.setThreads(5);

		pageUrl = "http://www.biodiscover.com/news.html?1=1&page=";
		for (int i = 1; i < 20; i++) {
			biodiscovercrawler.addSeed(pageUrl + i);
		}

		biodiscovercrawler.setResumable(false);
		try {
			biodiscovercrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		cexuchinaSpider cexuchinacrawler = new cexuchinaSpider(
//				"/Users/y/Desktop/testWebcollecter/data/cexuchinaSpider");
		cexuchinaSpider cexuchinacrawler = new cexuchinaSpider(
				"/home/wenjing/Desktop/huasheng/data/cexuchinaSpider");
		
		cexuchinacrawler.setThreads(5);

		cexuchinacrawler.addSeed("http://seq.cn/portal.php?mod=list&catid=16");
		pageUrl = "http://seq.cn/portal.php?mod=list&catid=1&page=";
		for (int i = 1; i < 10; i++) {
			cexuchinacrawler.addSeed(pageUrl + i);
		}

		cexuchinacrawler.setResumable(false);
		try {
			cexuchinacrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		chinaCancerSpider chinaCancercrawler = new chinaCancerSpider(
				"/Users/y/Desktop/testWebcollecter/data/chinacancerspider");
		chinaCancercrawler.setThreads(5);

		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=1");
	
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=246");
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=249");
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=423");
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=435");
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=456");
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=596");
		
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=614");
		
		chinaCancercrawler
				.addSeed("http://www.131.org.cn/article/list.php?catid=660");
		chinaCancercrawler.setResumable(false);

		try {
			chinaCancercrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		dingxiangyuanSpider dingxiangyuancrawler = new dingxiangyuanSpider(
//				"/Users/y/Desktop/testWebcollecter/data/dingxiangyuanSpider");
		
		dingxiangyuanSpider dingxiangyuancrawler = new dingxiangyuanSpider(
				"/home/wenjing/Desktop/huasheng/data/dingxiangyuanSpider");
		
		dingxiangyuancrawler.setThreads(5);

		pageUrl = "http://oncol.dxy.cn/tag/news/p-";
		for (int i = 1; i < 10; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/review/p-";
		for (int i = 1; i < 10; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/flashpoint/p-";
		for (int i = 1; i < 10; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/melanoma/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}
		pageUrl = "http://oncol.dxy.cn/tag/stomach-cancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/colon-cancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}
		pageUrl = "http://oncol.dxy.cn/tag/stomach-cancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/colon-cancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}
		pageUrl = "http://oncol.dxy.cn/tag/esophagealcancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/lung-cancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/brest-cancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}
		pageUrl = "http://oncol.dxy.cn/tag/liver-cancer/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://oncol.dxy.cn/tag/leukemia/p-";
		for (int i = 1; i < 5; i++) {
			dingxiangyuancrawler.addSeed(pageUrl + i + ".html");
		}
		dingxiangyuancrawler.setResumable(false);
		try {
			dingxiangyuancrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		iiyiSpider iiyicrawler = new iiyiSpider(
//				"/Users/y/Desktop/testWebcollecter/data/iiyispider");
		iiyiSpider iiyicrawler = new iiyiSpider(
				"/home/wenjing/Desktop/huasheng/data/iiyispider");
		iiyicrawler.setThreads(5);
		iiyicrawler.addSeed("http://www.iiyi.com/l-40-1.html");
		pageUrl = "http://www.iiyi.com/l-40-";
		for (int i = 2; i < 10; i++) {
			iiyicrawler.addSeed(pageUrl + i + ".html");
		}

		;
		pageUrl = "http://www.iiyi.com/l-34-";
		for (int i = 2; i < 10; i++) {
			iiyicrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://www.iiyi.com/l-34-";
		for (int i = 2; i < 10; i++) {
			iiyicrawler.addSeed(pageUrl + i + ".html");
		}

		pageUrl = "http://www.iiyi.com/l-430-";
		for (int i = 2; i < 10; i++) {
			iiyicrawler.addSeed(pageUrl + i + ".html");
		}
		pageUrl = "http://www.iiyi.com/l-443-";
		for (int i = 2; i < 10; i++) {
			iiyicrawler.addSeed(pageUrl + i + ".html");
		}
		pageUrl = "http://www.iiyi.com/l-432-";
		for (int i = 2; i < 10; i++) {
			iiyicrawler.addSeed(pageUrl + i + ".html");
		}
		

		// meeting seed
		// String pageUrl = "http://huiyi.iiyi.com/trailer/index/";
		// for (int i = 1; i < 10; i++) {
		// crawler.addSeed(pageUrl + i + ".html");
		// }
		//

		iiyicrawler.setResumable(false);
		try {
			iiyicrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		JCOspider JCOcrawler = new JCOspider(
//				"/Users/y/Desktop/testWebcollecter/data/JCOspider");

		JCOspider JCOcrawler = new JCOspider(
				"/home/wenjing/Desktop/huasheng/data/JCOspider");
		
		JCOcrawler.setThreads(5);

		pageUrl = "http://www.medsci.cn/article/list.do?s_id=64&page=";
		for (int i = 1; i < 10; i++) {
			JCOcrawler.addSeed(pageUrl + i);
		}

		pageUrl = "http://www.medsci.cn/article/list.do?s_id=58&page=";
		for (int i = 1; i < 10; i++) {
			JCOcrawler.addSeed(pageUrl + i);
		}
		JCOcrawler.setResumable(false);
		try {
			JCOcrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		shengWuGuSpider shengWuGucrawler = new shengWuGuSpider(
//				"/Users/y/Desktop/testWebcollecter/data/spder");
		
		shengWuGuSpider shengWuGucrawler = new shengWuGuSpider(
				"/home/wenjing/Desktop/huasheng/data/spder");
		shengWuGucrawler.setThreads(5);
		shengWuGucrawler.addSeed("http://news.bioon.com/BioMedical/index.html");

		pageUrl = "http://news.bioon.com/BioMedical/list-";
		for (int i = 2; i < 50; i++) {
			shengWuGucrawler.addSeed(pageUrl + i + ".html");
		}
		
		shengWuGucrawler.addSeed("http://news.bioon.com/biobank/index.html");
		pageUrl = "http://news.bioon.com/biobank/list-";
		for (int i = 2; i < 50; i++) {
			shengWuGucrawler.addSeed(pageUrl + i + ".html");
		}

		shengWuGucrawler.addSeed("http://news.bioon.com/ad/index.html");
		pageUrl = "http://news.bioon.com/ad/list-";
		for (int i = 2; i < 50; i++) {
			shengWuGucrawler.addSeed(pageUrl + i + ".html");
		}
		shengWuGucrawler
				.addSeed("http://news.bioon.com/medicaldevices/index.html");
		pageUrl = "http://news.bioon.com/medicaldevices/list-";
		for (int i = 2; i < 50; i++) {
			shengWuGucrawler.addSeed(pageUrl + i + ".html");
		}

		shengWuGucrawler.setResumable(false);
		try {
			shengWuGucrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		shengWuTongSpider shengWuTongcrawler = new shengWuTongSpider(
//				"/Users/y/Desktop/testWebcollecter/data/shengWuTongSpider");
		shengWuTongSpider shengWuTongcrawler = new shengWuTongSpider(
				"/home/wenjing/Desktop/huasheng/data/shengWuTongSpider");
		shengWuTongcrawler.setThreads(5);
		shengWuTongcrawler
				.addSeed("http://www.ebiotrade.com/newsf/list.asp?boardid=0");
		pageUrl = "http://www.ebiotrade.com/newsf/list.asp?boardid=0&page=";
		for (int i = 2; i < 10; i++) {
			shengWuTongcrawler.addSeed(pageUrl + i);
		}
		;

		shengWuTongcrawler.setResumable(false);
		try {
			shengWuTongcrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		yixueluntanSpider yixueluntancrawler = new yixueluntanSpider(
//				"/Users/y/Desktop/testWebcollecter/data/yixueluntanSpider");
		
		yixueluntanSpider yixueluntancrawler = new yixueluntanSpider(
				"/home/wenjing/Desktop/huasheng/data/yixueluntanSpider");
		
		yixueluntancrawler.setThreads(5);
		pageUrl = "http://cancer.cmt.com.cn/cindex/index/classid/1/subject_id/88/p/1.html";
		yixueluntancrawler.addSeed(pageUrl);
		for (int i = 1; i < 5; i++) {
			yixueluntancrawler.addSeed(pageUrl + i + ".html");
		}
		yixueluntancrawler.addSeed("http://cancer.cmt.com.cn/search/mCRC");

		yixueluntancrawler
				.addSeed("http://cancer.cmt.com.cn/search/%E9%9D%B6%E5%90%91%E6%B2%BB%E7%96%97");

		yixueluntancrawler
				.addSeed("http://cancer.cmt.com.cn/search/%E8%BE%85%E5%8A%A9%E5%8C%96%E7%96%97");

		yixueluntancrawler
				.addSeed("http://cancer.cmt.com.cn/search/%E7%99%8C%E7%97%87%E7%AD%9B%E6%9F%A5");
		yixueluntancrawler.addSeed("http://cancer.cmt.com.cn/search/NSCLC");
		yixueluntancrawler.setResumable(false);
		try {
			yixueluntancrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		zhangshangyiliaoSpider zhangshangyiliaocrawler = new zhangshangyiliaoSpider(
//				"/Users/y/Desktop/testWebcollecter/data/zhangshangyiliaoSpider");
		
		zhangshangyiliaoSpider zhangshangyiliaocrawler = new zhangshangyiliaoSpider(
				"/home/wenjing/Desktop/huasheng/data/zhangshangyiliaoSpider");
		
		zhangshangyiliaocrawler.setThreads(5);
		
//		crawler.addSeed("http://www.doctorpda.cn/oncol/news/?p=");
		 pageUrl = "http://www.doctorpda.cn/oncol/news/?p=";
		for (int i = 1; i < 10; i++) {
			zhangshangyiliaocrawler.addSeed(pageUrl + i );
		}
		
		zhangshangyiliaocrawler.setResumable(false);
		try {
			zhangshangyiliaocrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		zhuanhuayixueSpider zhuanhuayixuecrawler = new zhuanhuayixueSpider(
//				"/Users/y/Desktop/testWebcollecter/data/zhuanhuayixueSpider");
		zhuanhuayixueSpider zhuanhuayixuecrawler = new zhuanhuayixueSpider(
				"/home/wenjing/Desktop/huasheng/data/zhuanhuayixueSpider");
		zhuanhuayixuecrawler.setThreads(5);
	
		
	
		 pageUrl = "http://www.360zhyx.com/home-category-index-cid-1-tid-10-p-";
		for (int i = 1; i < 10; i++) {
			zhuanhuayixuecrawler.addSeed(pageUrl + i +".shtml");
		}
		
		zhuanhuayixuecrawler.setResumable(false);
		try {
			zhuanhuayixuecrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long keepTime = System.currentTimeMillis() - beginTime;
		System.out.println("抓取所用时间" + keepTime / 1000);

	}

}
