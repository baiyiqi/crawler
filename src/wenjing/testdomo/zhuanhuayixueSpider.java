package wenjing.testdomo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class zhuanhuayixueSpider extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	
	public zhuanhuayixueSpider(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
		
		regexRule.addRule("http://www.360zhyx.com/home.*rid.*");
		
		
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
		
		if(url.contains("home")){
			String time = doc.select("span.r-10").text();
			
			if(time.contains("天")){
				 
				Pattern p = Pattern.compile("(\\d+)");   
				Matcher m = p.matcher(time);   
			    String dayCount = "0";
				while(m.find())
		        {               
					dayCount = m.group(1);
		            System.out.println(dayCount+"day");
		            
		        }  
				
				long currentTime = System.currentTimeMillis();
				long newsTime = currentTime/1000 - Integer.parseInt(dayCount)*24*60*60;
				time = Long.toString(newsTime);
			}else if (time.contains("小时")){
				Pattern p = Pattern.compile("(\\d+)");   
				Matcher m = p.matcher(time);   
			    String hourCount = "0";
				while(m.find())
		        {               
					hourCount = m.group(1);
		            System.out.println(hourCount+"hour");
		        }  
				long currentTime = System.currentTimeMillis();
				long newsTime = currentTime/1000 - Integer.parseInt(hourCount)*60*60;
				time = Long.toString(newsTime);
			}else {
				time = timeStampExactor.matchDateString(doc.toString());
				String transPatten = "yyyy-MM-dd";
				time =timeStampExactor.transFromNormalDateToUnixDate(time,transPatten);
				
			}
		
			String src = doc.select("span.pull-left.fc999>span.fc999").text();
	
		
			String picUrl = doc.select("div.view-content.t-35.news-view.clearfix>p>span>img").attr("src");
			String picPreString = "http://www.360zhyx.com";
			picUrl = picPreString + picUrl;
		Elements newsContentElment = doc.select("div.view-content.t-35.news-view.clearfix");
		
		StringBuffer newsContentAbc = new StringBuffer();
		
		for (Element logSon : newsContentElment.select("span")){
			
			
			if(logSon.text().contains("一篇")){
				continue;
			}
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		String newsContent = newsContentAbc.toString();
		
		String title = doc.select("h1.pull-left").text();
		
		
		
		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);

		if (time != null && time.length() >= 5 &&newsContent!= null &&newsContent.length()>=10) {

			System.out.println("123");

			if (jdbcTemplate != null) {
				int updates = jdbcTemplate
						.update("insert into huashengDemo (ts,text,src,newsUrl,imageurl,grabbedTime,title) value(?,?,?,?,?,?,?)",
								time, newsContent, src, page.getUrl(), picUrl,
								grabbedTime, title);
				if (updates == 1) {
					System.out.println("mysql插入成功");
				}
				countingNumber++;
			}
		
		}
		
		}
		
		Links nextLinks = new Links();
		nextLinks.addAllFromDocument(doc, regexRule);
		return nextLinks;
	}

	public static void main(String[] args){
		long beginTime = System.currentTimeMillis();
		zhuanhuayixueSpider crawler = new zhuanhuayixueSpider(
				"/Users/y/Desktop/testWebcollecter/data/zhuanhuayixueSpider");
		crawler.setThreads(5);
	
		
	
		String pageUrl = "http://www.360zhyx.com/home-category-index-cid-1-tid-6-p-";
		for (int i = 1; i < 10; i++) {
			crawler.addSeed(pageUrl + i +".shtml");
		}
		
		 pageUrl = "http://www.360zhyx.com/home-category-index-cid-1-tid-4-p-";
		for (int i = 1; i < 10; i++) {
			crawler.addSeed(pageUrl + i +".shtml");
		}
		crawler.setResumable(false);
		try {
			crawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long keepTime = System.currentTimeMillis() - beginTime;
		//医视角 以及资讯头条，医学进展，诊疗指南，诊疗科学进展 肿瘤科诊疗指南 肿瘤科医学进展 前10页面
		System.out.println("抓取所用时间"+keepTime / 1000);
		System.out.println("抓取不重复条数"+countingNumber);
		
	}

}
