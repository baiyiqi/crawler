package wenjing.huasheng.category;

import cn.edu.hfut.dmic.webcollector.util.RegexRule;
import wenjing.testdomo.aiweiyixuewangSpider;

public class runHuaShengCategory {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		long beginTime = System.currentTimeMillis();
		
//		aiaiyiCategory aiweiyixuewangcrawler = new aiaiyiCategory("/Users/y/Desktop/testWebcollecter/data/iiyi","临床用药");
		
		aiaiyiCategory aiweiyixuewangcrawler = new aiaiyiCategory("/home/wenjing/Desktop/huasheng/data/iiyi","临床用药");
		
		//root url better to be list url
		String pageUrl = "http://www.iiyi.com/l-445-";
		for (int i = 2; i < 10; i++) {
			aiweiyixuewangcrawler.addSeed(pageUrl + i + ".html");
		}

		aiweiyixuewangcrawler.setThreads(5);
		aiweiyixuewangcrawler.setResumable(false);
		try {
			aiweiyixuewangcrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		chinaCancerCategory chinaCancer = new chinaCancerCategory("/Users/y/Desktop/testWebcollecter/data/chinaCancer","转化研究 产业政策");
		chinaCancerCategory chinaCancer = new chinaCancerCategory("/home/wenjing/Desktop/huasheng/data/chinaCancer","转化研究 产业政策");

		
		chinaCancer.addSeed("http://www.131.org.cn/article/list.php?catid=41");
		chinaCancer.setThreads(5);
		chinaCancer.setResumable(false);
		try {
			chinaCancer.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		chinaCancer = new chinaCancerCategory(
//				"/Users/y/Desktop/testWebcollecter/data/chinaCancer", "医患交流");
		
		chinaCancer = new chinaCancerCategory(
				"/home/wenjing/Desktop/huasheng/data/chinaCancer", "医患交流");
		
		chinaCancer.addSeed("http://www.131.org.cn/article/list.php?catid=87");
		chinaCancer.setThreads(5);
		chinaCancer.setResumable(false);
		try {
			chinaCancer.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		chinaCancer = new chinaCancerCategory(
//				"/Users/y/Desktop/testWebcollecter/data/chinaCancer", "临床用药");
		
		chinaCancer = new chinaCancerCategory(
				"/home/wenjing/Desktop/huasheng/data/chinaCancer", "临床用药");
		
		
		
		chinaCancer.addSeed("http://www.131.org.cn/article/list.php?catid=612");
		chinaCancer.setThreads(5);
		chinaCancer.setResumable(false);
		try {
			chinaCancer.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		chinaCancer = new chinaCancerCategory(
//				"/Users/y/Desktop/testWebcollecter/data/chinaCancer",
//				"会议报道 临床用药");
		
		chinaCancer = new chinaCancerCategory(
				"/home/wenjing/Desktop/huasheng/data/chinaCancer",
				"会议报道 临床用药");
		
		
		chinaCancer.addSeed("http://www.131.org.cn/article/list.php?catid=659");
		chinaCancer.setThreads(5);
		chinaCancer.setResumable(false);
		try {
			chinaCancer.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		dingxiangyuanCategory dingxiangyuan = new dingxiangyuanCategory("/Users/y/Desktop/testWebcollecter/data/dingxiangyuanCategory","临床用药");
		
		dingxiangyuanCategory dingxiangyuan = new dingxiangyuanCategory("/home/wenjing/Desktop/huasheng/data/dingxiangyuanCategory","临床用药");

		
		pageUrl = "http://oncol.dxy.cn/tag/guideline/p-";
		for (int i = 1; i < 10; i++) {
			dingxiangyuan.addSeed(pageUrl + i + ".html");
		}
		dingxiangyuan.setThreads(5);
		dingxiangyuan.setResumable(false);
		try {
			dingxiangyuan.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		dingxiangyuan = new dingxiangyuanCategory("/Users/y/Desktop/testWebcollecter/data/dingxiangyuanCategory","会议报道");
		
		dingxiangyuan = new dingxiangyuanCategory("/home/wenjing/Desktop/huasheng/data/dingxiangyuanCategory","会议报道");

		
		pageUrl = "http://oncol.dxy.cn/tag/asco2014/p-";
		for (int i = 1; i < 10; i++) {
			dingxiangyuan.addSeed(pageUrl + i + ".html");
		}
		dingxiangyuan.setThreads(5);
		dingxiangyuan.setResumable(false);
		try {
			dingxiangyuan.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		shengwuguCategory shengwuguCategoryCrawler = new shengwuguCategory("/Users/y/Desktop/testWebcollecter/data/shengwuguCategory","新药特药");
		
		shengwuguCategory shengwuguCategoryCrawler = new shengwuguCategory("/home/wenjing/Desktop/huasheng/data/shengwuguCategory","新药特药");
		pageUrl = "http://news.bioon.com/pharmacy/list-";
		for (int i = 1; i < 20; i++) {
			shengwuguCategoryCrawler.addSeed(pageUrl + i + ".html");
		}
		shengwuguCategoryCrawler.setThreads(5);
		shengwuguCategoryCrawler.setResumable(false);
		try {
			shengwuguCategoryCrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		shengwuguCategoryCrawler = new shengwuguCategory("/Users/y/Desktop/testWebcollecter/data/shengwuguCategory","转化研究");
		shengwuguCategoryCrawler = new shengwuguCategory("/home/wenjing/Desktop/huasheng/data/shengwuguCategory","转化研究");

		pageUrl = "http://news.bioon.com/biology/list-";
		for (int i = 1; i < 20; i++) {
			shengwuguCategoryCrawler.addSeed(pageUrl + i + ".html");
		}
		pageUrl = "http://news.bioon.com/transMedicine/list-";
		for (int i = 1; i < 20; i++) {
			shengwuguCategoryCrawler.addSeed(pageUrl + i + ".html");
		}
		shengwuguCategoryCrawler.setThreads(5);
		shengwuguCategoryCrawler.setResumable(false);
		
		try {
			shengwuguCategoryCrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		zhuanhaunyiliaoCategory zhuanhaunyiliaoCategoryCrawler = new zhuanhaunyiliaoCategory("/Users/y/Desktop/testWebcollecter/data/zhuanhaunyiliaoCategory","新药特药");
		
		zhuanhaunyiliaoCategory zhuanhaunyiliaoCategoryCrawler = new zhuanhaunyiliaoCategory("/home/wenjing/Desktop/huasheng/data/zhuanhaunyiliaoCategory","新药特药");
		 pageUrl = "http://www.360zhyx.com/home-category-index-cid-1-tid-6-p-";
		for (int i = 1; i < 10; i++) {
			zhuanhaunyiliaoCategoryCrawler.addSeed(pageUrl + i +".shtml");
		}
		zhuanhaunyiliaoCategoryCrawler.setThreads(5);
		zhuanhaunyiliaoCategoryCrawler.setResumable(false);
		try {
			zhuanhaunyiliaoCategoryCrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

//		zhuanhaunyiliaoCategoryCrawler = new zhuanhaunyiliaoCategory("/Users/y/Desktop/testWebcollecter/data/zhuanhaunyiliaoCategory","医患交流");
		zhuanhaunyiliaoCategoryCrawler = new zhuanhaunyiliaoCategory("/home/wenjing/Desktop/huasheng/data/zhuanhaunyiliaoCategory","医患交流");

		pageUrl = "http://www.360zhyx.com/home-category-index-cid-1-tid-4-p-";
			for (int i = 1; i < 10; i++) {
				zhuanhaunyiliaoCategoryCrawler.addSeed(pageUrl + i +".shtml");
			}
		zhuanhaunyiliaoCategoryCrawler.setThreads(5);
		zhuanhaunyiliaoCategoryCrawler.setResumable(false);
		
		try {
			zhuanhaunyiliaoCategoryCrawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long keepTime = System.currentTimeMillis() - beginTime;
		System.out.println("抓取所用时间" + keepTime / 1000);
	}

}
