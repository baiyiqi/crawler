package wenjing.zhengwutong;

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

public class TianjingPolice extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	
	public TianjingPolice(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
		
		regexRule.addRule("http://www.tjgaj.gov.cn/web/.*");
		//http://www.tjdpc.gov.cn/dtzx/ttxw/201505/t20150513_56135.shtml
		
		try {
			jdbcTemplate = JDBCHelper
					.createMysqlTemplate(
							"mysql1",
							"jdbc:mysql://119.29.61.192/zhengwutong?useUnicode=true&characterEncoding=utf8",
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
			String time = timeStampExactor.matchDateString(doc.toString());
			if (time.length() > 5) {
				// "yyyy-MM-dd HH:mm:ss";
				time = timeStampExactor.transFromNormalDateToUnixDate(time,
						"yyyy年MM月dd日");
			}
				
		String src = "天津公安民生服务平台";
	//#articleContent > p:nth-child(2) > a > img
		
		String picUrl = doc.select("div#articleContent > p > a > img").attr("abs:src");
	

		Elements newsContentElment = doc.select("div#articleContent");
		
		StringBuffer newsContentAbc = new StringBuffer();
		
		for (Element logSon : newsContentElment.select("p")){
			
			
			if(logSon.text().contains("一篇")){
				continue;
			}
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		String newsContent = newsContentAbc.toString();
		
		String title = doc.title();
		
		
		
		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);

		if (time != null && time.length() >= 5 &&newsContent!= null &&newsContent.length()>=10) {

			System.out.println("123");

			if (jdbcTemplate != null) {
				int updates = jdbcTemplate
						.update("insert into tianjingNews (ts,text,src,newsUrl,imageurl,grabbedTime,title) value(?,?,?,?,?,?,?)",
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
		TianjingPolice crawler = new TianjingPolice(
				"/Users/y/Desktop/testWebcollecter/data/TianjingPolice");
		crawler.setThreads(5);
	
		
	
		String pageUrl = "http://www.tjgaj.gov.cn/web/xwpd_jwdt.html";
//		for (int i = 1; i < 20; i++) {
//			crawler.addSeed(pageUrl + i );
//		}
		crawler.addSeed(pageUrl);
		
//		pageUrl = "http://www.tjcoc.gov.cn/html/shangwuyaowen/2.html";
//	
//		crawler.addSeed(pageUrl);
//		
//		pageUrl = "http://www.tjcoc.gov.cn/html/shangwuyaowen/3.html";
//		
//		crawler.addSeed(pageUrl);
//		
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
