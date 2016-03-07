package wenjing.huasheng.category;

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

public class dingxiangyuanCategory extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	static int countingNumber = 0;

	
	private String thiscaegory;

	public dingxiangyuanCategory(String crawlPath,String category) {
		super(crawlPath);
		this.thiscaegory = category;
		
		regexRule.addRule("http://oncol.dxy.cn/article/.*");
		
		
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
		
		if(url.contains("article")){
		String time = timeStampExactor.matchDateString(doc.toString());
		if (time.length() > 5) {
			// "yyyy-MM-dd HH:mm:ss";
			time = timeStampExactor.transFromNormalDateToUnixDate(time,
					"yyyy-MM-dd HH:mm");
		}
		
		String src = doc.select("span.it>a").text();
	
		String picUrl = doc.select("div.x_detail1>p>a>img").attr("src");

		Elements newsContentElment = doc.select("div.x_detail1");
		
		StringBuffer newsContentAbc = new StringBuffer();
		for (Element logSon : newsContentElment.select("p")){
			
			if(logSon.text().contains("一篇")){
				continue;
			}
			newsContentAbc.append(logSon.text());
			newsContentAbc.append("\n");
			}
		
		
		String newsContent = newsContentAbc.toString();
		
		String title = doc.select("div.x_box13>h1").text();
		
		String urlCategory = thiscaegory;
		
		
		
		long m = System.currentTimeMillis() / 1000;

		String grabbedTime = String.valueOf(m);

		if (time != null && time.length() >= 5 &&newsContent!= null &&newsContent.length()>=10) {

			System.out.println("123");

			if (jdbcTemplate != null) {
				int updates = jdbcTemplate
						.update("insert into huashengDemo (ts,text,src,newsUrl,imageurl,grabbedTime,title,category) value(?,?,?,?,?,?,?,?)",
								time, newsContent, src, page.getUrl(), picUrl,
								grabbedTime, title,urlCategory);
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

}
