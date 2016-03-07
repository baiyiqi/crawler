package wenjing.testdomo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;

import wenjing.parsing.util.srcExactor;
import wenjing.parsing.util.timeStampExactor;
import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.JDBCHelper;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

public class shengWuGuSpider extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;
	
	static int countingNumber = 0;

	public shengWuGuSpider(String crawlPath) {
		super(crawlPath);
		regexRule.addRule("http://news.bioon.com/article/.*");

		try {
			jdbcTemplate = JDBCHelper
					.createMysqlTemplate(
							"mysql1",
							"jdbc:mysql://129.63.16.39/kuaiwenbaoDB?useUnicode=true&characterEncoding=utf8",
							"root", "wtsql2014", 5, 30);

			/* 创建数据表 */

			System.out.println("连接成功");

		} catch (Exception ex) {
			ex.printStackTrace();
			jdbcTemplate = null;
			System.out
					.println("mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!");
		}

		// TODO Auto-generated constructor stub
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// TODO Auto-generated method stub
		Document doc = page.getDoc();
		String title = doc.title();
//		System.out.println("");
//		System.out.println("URL:" + page.getUrl() + "  标题:" + title);
//		System.out.println("");
		Links nextLinks = new Links();

		String time = timeStampExactor.matchDateString(doc.select(
				"div.title5>p").text());
		if (time.length() > 5) {
			// "yyyy-MM-dd HH:mm:ss";
			time = timeStampExactor.transFromNormalDateToUnixDate(time,
					"yyyy-MM-dd HH:mm");
		}

		String src = srcExactor.getSrc("来源：(.*)20", doc.toString());
		if (src != null && src.contains("20")) {
			src = null;
		}
		String picUrl = doc.select("div.text3>p>img").attr("src");

		Elements newsContentElment = doc.select("div.text3");

		StringBuffer newsContentAbc = new StringBuffer();
		for (Element logSon : newsContentElment.select("p")) {

			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
		}

		String newsContent = newsContentAbc.toString();
		String newsTitle = doc.select("div.title5>h1").text();

		System.out.println("here is  the test news infomation");
		System.out.println("time" + time);
		System.out.println("src" + src);
		System.out.println("picurl" + picUrl);
		System.out.println("newsContent" + newsContent);
		System.out.println("URL" + page.getUrl());
		System.out.println("newsTitle" + newsTitle);
		System.out.println("end");
		
		String urlCategory = "";

		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);

		if (time != null && time.length() >= 5 &&newsContent!= null) {

			System.out.println("123");

			if (jdbcTemplate != null) {
				int updates = jdbcTemplate
						.update("insert into huashengDemo (ts,text,src,newsUrl,imageurl,grabbedTime,title,category) value(?,?,?,?,?,?,?,?)",
								time, newsContent, src, page.getUrl(), picUrl,
								grabbedTime, newsTitle,urlCategory);
				if (updates == 1) {
					System.out.println("mysql插入成功");
				}
				countingNumber++;
			}

		}

		/*
		 * 我们只希望抽取满足正则约束的URL， Links.addAllFromDocument为我们提供了相应的功能
		 */
		nextLinks.addAllFromDocument(doc, regexRule);
		return nextLinks;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		long beginTime = System.currentTimeMillis();
		shengWuGuSpider crawler = new shengWuGuSpider(
				"/Users/y/Desktop/testWebcollecter/data/spder");
		crawler.setThreads(5);
		crawler.addSeed("http://news.bioon.com/BioMedical/index.html");

		String pageUrl = "http://news.bioon.com/BioMedical/list-";
		for (int i = 2; i < 50; i++) {
			crawler.addSeed(pageUrl + i + ".html");
		}

		crawler.addSeed("http://news.bioon.com/pharmacy/index.html");
		pageUrl = "http://news.bioon.com/pharmacy/list-";
		for (int i = 2; i < 50; i++) {
			crawler.addSeed(pageUrl + i + ".html");
		}

		crawler.addSeed("http://news.bioon.com/transMedicine/index.html");
		pageUrl = "http://news.bioon.com/transMedicine/list-";
		for (int i = 2; i < 50; i++) {
			crawler.addSeed(pageUrl + i + ".html");
		}
		crawler.addSeed("http://news.bioon.com/biobank/index.html");
		pageUrl = "http://news.bioon.com/biobank/list-";
		for (int i = 2; i < 50; i++) {
			crawler.addSeed(pageUrl + i + ".html");
		}

		crawler.addSeed("http://news.bioon.com/ad/index.html");
		pageUrl = "http://news.bioon.com/ad/list-";
		for (int i = 2; i < 50; i++) {
			crawler.addSeed(pageUrl + i + ".html");
		}
		crawler.addSeed("http://news.bioon.com/medicaldevices/index.html");
		pageUrl = "http://news.bioon.com/medicaldevices/list-";
		for (int i = 2; i < 50; i++) {
			crawler.addSeed(pageUrl + i + ".html");
		}

		crawler.setResumable(false);
		crawler.start(2);
		long keepTime = System.currentTimeMillis() - beginTime;
		System.out.println("抓取所用时间"+keepTime / 1000);
		System.out.println("抓取不重复条数"+countingNumber);
	}

}
