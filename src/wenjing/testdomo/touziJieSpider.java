package wenjing.testdomo;


import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;

import wenjing.parsing.util.srcExactor;
import wenjing.parsing.util.timeStampExactor;
import wenjing.testdomo.biodiscoverSpider;
import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.JDBCHelper;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

public class touziJieSpider extends DeepCrawler{

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	
	public touziJieSpider(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
		
		regexRule.addRule("http://.*.pedaily.cn/.*.shtml");
		//http://www.tjdpc.gov.cn/dtzx/ttxw/201505/t20150513_56135.shtml
		
		try {
			jdbcTemplate = JDBCHelper
					.createMysqlTemplate(
							"mysql1",
							"jdbc:mysql://119.29.61.192/kuaiwenbaoDB?useUnicode=true&characterEncoding=utf8",
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
		
		if(url.contains("2015")){
			String time = timeStampExactor.matchDateString(doc.toString());
			if (time.length() > 5) {
				// "yyyy-MM-dd HH:mm:ss";
				time = timeStampExactor.transFromNormalDateToUnixDate(time,
						"yyyy-MM-dd hh:mm");
			}
				
		String src = srcExactor. getSrc("来源：(.*)日期",doc.toString());
	
		
		String picUrl = doc.select("div.news-content>p>img").attr("abs:src");
	

		Elements newsContentElment = doc.select("div.news-content");
		
		StringBuffer newsContentAbc = new StringBuffer();
		
		for (Element logSon : newsContentElment.select("p")){
			
			
			if(logSon.text().contains("一篇")){
				continue;
			}
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		String newsContent = newsContentAbc.toString().replaceAll(" 　　", "\n");
		
		String title = doc.title();
		
		
		
		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);
		String category = "投资界";

		if (time != null && time.length() >= 5 &&newsContent!= null &&newsContent.length()>=10) {

			System.out.println("123");

			if (jdbcTemplate != null) {
				String dbName = getDbName();
				int updates = jdbcTemplate
						.update("insert into "+dbName+" (ts,text,src,newsUrl,imageurl,grabbedTime,title,category) value(?,?,?,?,?,?,?,?)",
								time, newsContent, src, page.getUrl(), picUrl,
								grabbedTime, title,category);
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

	
	private String getDbName() {
		//String DbName = "xinwendbTest";
		String DbName = "xinwendbCaiJing";
		Calendar test = Calendar.getInstance(TimeZone.getDefault());

		int month = test.get(Calendar.MONTH) + 1;
		String monthString = null;
		switch (month) {
		case 1:
			monthString = "01";
			break;
		case 2:
			monthString = "02";
			break;
		case 3:
			monthString = "03";
			break;
		case 4:
			monthString = "04";
			break;
		case 5:
			monthString = "05";
			break;
		case 6:
			monthString = "06";
			break;
		case 7:
			monthString = "07";
			break;
		case 8:
			monthString = "08";
			break;
		case 9:
			monthString = "09";
			break;
		case 10:
			monthString = "10";
			break;
		case 11:
			monthString = "11";
			break;
		case 12:
			monthString = "12";
			break;
		default:
			monthString = "Invalid month";
			break;
		}
		int YEAR = test.get(Calendar.YEAR);
		DbName = DbName + YEAR + monthString+"all";
		
		return DbName;
	}
	
	
	public static void main(String[] args){
		long beginTime = System.currentTimeMillis();
//		touziJieSpider crawler = new touziJieSpider(
//				"//Users/y/Desktop/testWebcollecter/data/touzijieFile");
		touziJieSpider crawler = new touziJieSpider(
				"/home/wenjing/Desktop/caiwutong/touzijieFile");
		crawler.setThreads(5);
	
		
	
		String pageUrl = "http://www.pedaily.cn/";
//		for (int i = 1; i < 20; i++) {
//			crawler.addSeed(pageUrl + i );
//		}
		crawler.addSeed(pageUrl);
		
//		pageUrl = "http://www.tjdpc.gov.cn/dtzx/ttxw/";
//	
//		crawler.addSeed(pageUrl);
//		
//		pageUrl = "http://www.tjdpc.gov.cn/dtzx/xwzs/";
//		
//		crawler.addSeed(pageUrl);
		
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
