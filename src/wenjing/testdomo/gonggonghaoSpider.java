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

public class gonggonghaoSpider extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	public gonggonghaoSpider(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub

		regexRule.addRule("http://www.ebiotrade.com/newsf/read.*");

		
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
		
		System.out.println(url);
		System.out.println(doc.toString());
		
//		System.out.println("here is  the test news infomation");
//		System.out.println("time" + time);
//		System.out.println("src" + src);
//		System.out.println("picurl" + picUrl);
//		System.out.println("newsContent" + newsContent);
//		System.out.println("URL" + page.getUrl());
//		System.out.println("newsTitle" + newsTitle);
//		System.out.println("end");
//		
//		long m = System.currentTimeMillis() / 1000;
//
//		String grabbedTime = String.valueOf(m);
//
//		if (time != null && time.length() >= 5 &&newsContent!= null) {
//
//			System.out.println("123");
//
//			if (jdbcTemplate != null) {
//				int updates = jdbcTemplate
//						.update("insert into huashengDemo (ts,text,src,newsUrl,imageurl,grabbedTime,title) value(?,?,?,?,?,?,?)",
//								time, newsContent, src, page.getUrl(), picUrl,
//								grabbedTime, newsTitle);
//				if (updates == 1) {
//					System.out.println("mysql插入成功");
//				}
//				countingNumber++;
//			}
//
//		}
		
		
		
		
		Links nextLinks = new Links();
		nextLinks.addAllFromDocument(doc, regexRule);
		return nextLinks;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long beginTime = System.currentTimeMillis();
		gonggonghaoSpider crawler = new gonggonghaoSpider(
				"/Users/y/Desktop/testWebcollecter/data/gonggonghaoSpider");
		crawler.setThreads(5);
		
		crawler.addSeed("http://www.ebiotrade.com/newsf/list.asp?boardid=0");
		
		
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