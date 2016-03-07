package wenjing.huashengEnglish;

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

public class OncoLinkTypeSpider extends DeepCrawler {
	

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;
	
	static int countingNumber = 0;
	private String thiscaegory;
	public OncoLinkTypeSpider(String crawlPath,String category) {
		super(crawlPath);
		this.thiscaegory = category;
		
		regexRule.addRule("http://www.oncolink.org/coping/coping.cfm.*");
		regexRule.addRule("http://www.oncolink.org/coping/article.*");
		regexRule.addRule("-http://www.oncolink.org/coping/article.*#cancerDrug.*");
		//joken rules
		/*
		 * /www.oncolink.org/types/types.cfm?c=8#cancerDrugModal_E
2015-05-19 18:59:18 INFO cn.edu.hfut.dmic.webcollector.fetcher.Fetcher  - fetch http://www.oncolink.org/types/types.cfm?c=8#cancerDrugModal_G
2015-05-19 18:59:18 INFO cn.edu.hfut.dmic.webcollector.fetcher.Fetcher  - fetch http://www.oncolink.org/types/types.cfm?c=8#cancerDrugModal_C
		 */
//		regexRule.addRule("http://www.oncolink.org/treatment/.*");
//		regexRule.addRule("http://huiyi.iiyi.com/view/trailer/.*");
		
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

	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// TODO Auto-generated method stub
		
		Document doc = page.getDoc();
		String url = page.getUrl();
		
		//news kind parsing
		if(url.contains("article")){
		String time = srcExactor.getSrc("Last Modified:(.*)</p",doc.toString());
		if (time.length() > 5) {
			// "yyyy-MM-dd HH:mm:ss";
			time = monthTran(time);
		}

		String src = "oncolink";
	
		String picUrl = "";

		Elements newsContentElment = doc.select("#subContent");
		
		StringBuffer newsContentAbc = new StringBuffer();
		for (Element logSon : newsContentElment.select("p")){
			
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		
		
		String newsContent = newsContentAbc.toString();
		
		String newsTitle = doc.select("div.span9 > h1").text();
		
		
	
		
		System.out.println("here is  the test news infomation");
		System.out.println("time" + time);
//		System.out.println("src" + src);
//		System.out.println("picurl" + picUrl);
		System.out.println("newsContent" + newsContent);
		System.out.println("URL" + page.getUrl());
		System.out.println("newsTitle" + newsTitle);
		System.out.println("end");
		
		String urlCategory = thiscaegory;
		countingNumber++;
		System.out.println("抓取条数"+countingNumber);
		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);

		if (time != null && time.length() >= 5 &&newsContent!= null) {

			System.out.println("123");

			if (jdbcTemplate != null) {
				int updates = jdbcTemplate
						.update("insert into huashengDemoEnglish (ts,text,src,newsUrl,imageurl,grabbedTime,title,category) value(?,?,?,?,?,?,?,?)",
								time, newsContent, src, page.getUrl(), picUrl,
								grabbedTime, newsTitle,urlCategory);
				if (updates == 1) {
					System.out.println("mysql插入成功");
				}
				countingNumber++;
			}

		}
		return null;
		}
		
		
		Links nextLinks = new Links();
		nextLinks.addAllFromDocument(doc, regexRule);
		return nextLinks;
	}
	public static String monthTran(String data){
	
		
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
	
		if(afterString.length()==11){
			afterString=afterString.replaceFirst(" ", "0");
		}
		
	
		//yyyy年MM月dd日
		String timeSamp = timeStampExactor.transFromNormalDateToUnixDate(afterString, "MM,dd, yyyy");
		return timeSamp;
	}
	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		OncoLinkTypeSpider crawler = new OncoLinkTypeSpider(
				"/Users/y/Desktop/testWebcollecter/data/OncoLinkTypeSpider","Healthcare Professionals");
		crawler.setThreads(8);
	
		
		
//		String pageUrl = "http://www.oncolink.org/types/index.cfm";
//		String pageUrl = "http://www.oncolink.org/treatment/index.cfm";
//		http://www.oncolink.org/risk/index.cfm
//			String pageUrl = "http://www.oncolink.org/risk/index.cfm";
		String pageUrl = "http://www.oncolink.org/coping/index.cfm";

		crawler.addSeed(pageUrl);
		
		
	
		crawler.setResumable(false);
		try {
			crawler.start(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long keepTime = System.currentTimeMillis() - beginTime;
		
		System.out.println("抓取所用时间"+keepTime / 1000);
		System.out.println("抓取条数"+countingNumber);
		System.out.println("抓取所用时间"+keepTime / 1000);
		System.out.println("抓取条数"+countingNumber);
		System.out.println("抓取所用时间"+keepTime / 1000);
		System.out.println("抓取条数"+countingNumber);
		
	}
}
