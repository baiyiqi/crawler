package wenjing.Caiwu;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;

import wenjing.parsing.util.timeStampExactor;
import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.JDBCHelper;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

public class eastMoney extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;
	
	public eastMoney(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
		
		//http://finance.sina.com.cn/stock/s/20150917/010923268489.shtml
		regexRule.addRule("http://finance.eastmoney.com/news/.*.html");
		
		
		try {
			jdbcTemplate = JDBCHelper
					.createMysqlTemplate(
							"mysql1",
							"jdbc:mysql://119.29.61.192/caiwutong?useUnicode=true&characterEncoding=utf8",
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
		
		
		
		//if(url.contains("http://news.ifeng.com/a/")){
			
		String time = timeStampExactor.matchDateString(doc.toString());
		if (time.length() > 5) {
			// "yyyy-MM-dd HH:mm:ss";
			time = timeStampExactor.transFromNormalDateToUnixDate(time,
					"yyyy-MM-dd HH:mm");
		}
		String src = doc.select("span.ss03").text();
	
		String picUrl = doc.select("p.detailPic>img").attr("src");

		Elements newsContentElment = doc.select("div#ContentBody");
		
		StringBuffer newsContentAbc = new StringBuffer();
		
		for (Element logSon : newsContentElment.select("p")){
			
			
			if(logSon.text().contains("上一页")){
				continue;
			}
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		String newsContent = newsContentAbc.toString();

		String newsTitle = doc.select("div.newText.new>h1").text();
		
		
		System.out.println("here is  the test news infomation");
		System.out.println("time" + time);
		System.out.println("src" + src);
		System.out.println("picurl" + picUrl);
		System.out.println("newsContent" + newsContent);
		System.out.println("URL" + page.getUrl());
		System.out.println("newsTitle" + newsTitle);
		System.out.println("end");
		
//		countingNumber++;
		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);

		if (time != null && time.length() >= 5 &&newsContent!= null) {

			System.out.println("123");

			if (jdbcTemplate != null) {
				int updates = jdbcTemplate
						.update("insert into stockAnalysis (timeStamp,newsContent,src,url,grabbedTime,title) value(?,?,?,?,?,?)",
								time, newsContent, src, page.getUrl(),
								grabbedTime, newsTitle);
				if (updates == 1) {
					System.out.println("mysql插入成功");
				}
				countingNumber++;
			}

		}
		
		//}
		
		
		Links nextLinks = new Links();
		nextLinks.addAllFromDocument(doc, regexRule);
		return nextLinks;
		
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long beginTime = System.currentTimeMillis();
		eastMoney crawler = new eastMoney(
				"/Users/y/Desktop/testWebcollecter/data/eastMoney");
		
		crawler.addSeed("http://finance.eastmoney.com/yaowen.html");
		
		crawler.setThreads(5);
		crawler.setResumable(false);
		
		
		try {
			crawler.start(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long keepTime = System.currentTimeMillis() - beginTime;

		System.out.println("抓取所用时间"+keepTime / 1000);
		System.out.println("抓取不重复条数"+countingNumber);
		crawler.jdbcTemplate = null;
	}

}
