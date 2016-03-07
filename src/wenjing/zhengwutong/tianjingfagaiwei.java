package wenjing.zhengwutong;

import java.util.regex.Matcher;
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

public class tianjingfagaiwei extends DeepCrawler{

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	
	public tianjingfagaiwei(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
		
		regexRule.addRule("http://www.tjdpc.gov.cn/dtzx/.*");
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
		
		if(url.contains("2015")){
			String time = timeStampExactor.matchDateString(doc.toString());
			if (time.length() > 5) {
				// "yyyy-MM-dd HH:mm:ss";
				time = timeStampExactor.transFromNormalDateToUnixDate(time,
						"yyyy-MM-dd");
			}
				
		String src = srcExactor. getSrc("来源：(.*)日期",doc.toString());
	
		
		String picUrl = doc.select("div.TRS_Editor>p>img").attr("abs:src");
	

		Elements newsContentElment = doc.select("div.TRS_Editor");
		
		StringBuffer newsContentAbc = new StringBuffer();
		
		for (Element logSon : newsContentElment.select("p")){
			
			
			if(logSon.text().contains("一篇")){
				continue;
			}
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		String newsContent = newsContentAbc.toString().replaceAll(" 　　", "\n");
		
		String title = doc.select("div.nr_3_1").text();
		
		
		
		
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
		tianjingfagaiwei crawler = new tianjingfagaiwei(
				"/Users/y/Desktop/testWebcollecter/data/tianjingfagaiwei");
		crawler.setThreads(5);
	
		
	
		String pageUrl = "http://www.tjdpc.gov.cn/dtzx/tpxw/";
//		for (int i = 1; i < 20; i++) {
//			crawler.addSeed(pageUrl + i );
//		}
		crawler.addSeed(pageUrl);
		
		pageUrl = "http://www.tjdpc.gov.cn/dtzx/ttxw/";
	
		crawler.addSeed(pageUrl);
		
		pageUrl = "http://www.tjdpc.gov.cn/dtzx/xwzs/";
		
		crawler.addSeed(pageUrl);
		
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
