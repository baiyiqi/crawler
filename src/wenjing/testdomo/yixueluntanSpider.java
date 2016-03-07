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

public class yixueluntanSpider   extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	
	public yixueluntanSpider(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
		
		regexRule.addRule("http://cancer.cmt.com.cn/detail/.*");
		
		
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
		
		if(url.contains("detail")){
			System.out.println("123");
			String time =timeStampExactor.matchDateString(doc.toString());
			
		if (time.length() > 5) {
			// "yyyy-MM-dd HH:mm:ss";
			time = timeStampExactor.transFromNormalDateToUnixDate(time,
					"yyyy-MM-dd");
		}
		
		String src = srcExactor.getSrc("来源：(.*)日期",doc.toString());
//		String src = doc.select("span.pull-left.fc999>span.fc999").text();
		
//		if(src!=null){
//		
//			src.replaceAll("&nbsp;&nbsp;&nbsp;", "");
//		}
	
		String picUrl = doc.select("div.y-xin-cot>p>span>img").attr("src");
//		String picPreString = "http://www.360zhyx.com";
//		picUrl = picPreString + picUrl;
		Elements newsContentElment = doc.select("div.y-xin-cot");
		
		StringBuffer newsContentAbc = new StringBuffer();
		
		for (Element logSon : newsContentElment.select("p")){
			
			
			if(logSon.text().contains("上一页")){
				continue;
			}
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		String newsContent = newsContentAbc.toString();

		String title = doc.select("h2.y-title").text();
		
		
		
		System.out.println("here is  the test news infomation");
		System.out.println("time" + time);
		System.out.println("src" + src);
		System.out.println("picurl" + picUrl);
		System.out.println("newsContent" + newsContent);
		System.out.println("URL" + page.getUrl());
		System.out.println("newsTitle" + title);
		System.out.println("end");
		
		
		
		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);

		if (time != null && time.length() >= 5 &&newsContent!= null &&newsContent.length()>=10) {

	

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
		yixueluntanSpider crawler = new yixueluntanSpider(
				"/Users/y/Desktop/testWebcollecter/data/yixueluntanSpider");
		crawler.setThreads(5);
		String pageUrl = "http://cancer.cmt.com.cn/cindex/index/classid/1/subject_id/88/p/1.html";
		crawler.addSeed(pageUrl);
				for (int i = 1; i < 5; i++) {
			crawler.addSeed(pageUrl + i + ".html");
		}
		crawler.addSeed("http://cancer.cmt.com.cn/search/mCRC");
		
		crawler.addSeed("http://cancer.cmt.com.cn/search/%E9%9D%B6%E5%90%91%E6%B2%BB%E7%96%97");
		
		crawler.addSeed("http://cancer.cmt.com.cn/search/%E8%BE%85%E5%8A%A9%E5%8C%96%E7%96%97");
		
		crawler.addSeed("http://cancer.cmt.com.cn/search/%E7%99%8C%E7%97%87%E7%AD%9B%E6%9F%A5");
		crawler.addSeed("http://cancer.cmt.com.cn/search/NSCLC");
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

