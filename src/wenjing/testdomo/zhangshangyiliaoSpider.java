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

public class zhangshangyiliaoSpider extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	
	public zhangshangyiliaoSpider(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
		
		regexRule.addRule("http://www.doctorpda.cn/news/.*");
		
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
		
		if(url.contains("news")){
			String time = timeStampExactor.matchDateString(doc.toString());
			if (time.length() > 5) {
				// "yyyy-MM-dd HH:mm:ss";
				time = timeStampExactor.transFromNormalDateToUnixDate(time,
						"yyyy-MM-dd");
			}
			
			String src = srcExactor.getSrc("来源：(.*)<",doc.toString());
		
			String picUrl = doc.select("div.x_detail1>p>img").attr("src");

			Elements newsContentElment = doc.select("div.huiyi_left_content");
			
			StringBuffer newsContentAbc = new StringBuffer();
			for (Element logSon : newsContentElment.select("p")){
				
				if(logSon.text().contains("一篇")){
					continue;
				}
				newsContentAbc.append(logSon.text());
				newsContentAbc.append("\n");
				}
			
			
			String newsContent = newsContentAbc.toString();
			
			String title = doc.select("div.list_biaoti>h3").text();
			
			System.out.println("src"+src);
			System.out.println("time"+time);
			System.out.println("url"+picUrl);			
			System.out.println(newsContent);
			System.out.println("title"+title);
			
			
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
		zhangshangyiliaoSpider crawler = new zhangshangyiliaoSpider(
				"/Users/y/Desktop/testWebcollecter/data/zhangshangyiliaoSpider");
		crawler.setThreads(5);
		
//		crawler.addSeed("http://www.doctorpda.cn/oncol/news/?p=");
		String pageUrl = "http://www.doctorpda.cn/oncol/news/?p=";
		for (int i = 1; i < 10; i++) {
			crawler.addSeed(pageUrl + i );
		}
		
		crawler.setResumable(false);
		try {
			crawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long keepTime = System.currentTimeMillis() - beginTime;
		
		System.out.println("抓取所用时间"+keepTime / 1000);
		System.out.println("抓取不重复条数"+countingNumber);
		
	}

}
