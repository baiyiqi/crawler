package wenjing.huasheng.category;

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

public class aiaiyiCategory extends DeepCrawler {
	

		RegexRule regexRule = new RegexRule();

		JdbcTemplate jdbcTemplate = null;
		
		static int countingNumber = 0;
		private String thiscaegory;
		public aiaiyiCategory(String crawlPath,String category) {
			super(crawlPath);
			this.thiscaegory = category;
//          //url patten
			regexRule.addRule("http://www.iiyi.com/d.*");
//			regexRule.addRule("http://huiyi.iiyi.com/view/trailer/.*");
			
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
			String time = timeStampExactor.matchDateString(doc.toString());
			if (time.length() > 5) {
				// "yyyy-MM-dd HH:mm:ss";
				time = timeStampExactor.transFromNormalDateToUnixDate(time,
						"yyyy-MM-dd HH:mm");
			}

			String src = srcExactor. getSrc("来源：(.*)<",doc.toString());
		
			String picUrl = doc.select("div.b-mhcon>div>img").attr("src");

//			Elements newsContentElment = doc.select("div.b-mhcon");
            Elements newsContentElment = doc.select("div.dArt");
			
			StringBuffer newsContentAbc = new StringBuffer();
			for (Element logSon : newsContentElment.select("p")){
				
				newsContentAbc.append(logSon.text());
				newsContentAbc.append("\n");
				}
			
			
			String newsContent = newsContentAbc.toString();
			
			String newsTitle = doc.select("div.b-mh-hdcon>h2").text();

            String catergory = doc.select("div.dPosition > a");
			
			
			//meeting parsing
//			String time = timeStampExactor.matchDateString(doc.toString());
//			if (time.length() > 5) {
//				// "yyyy-MM-dd HH:mm:ss";
//				time = timeStampExactor.transFromNormalDateToUnixDate(time,
//						"yyyy年MM月dd日");
//			}
//			String src = srcExactor. getSrc("来源：(.*)<",doc.toString());
//			
//			String picUrl = null;
//			
//			String newsTitle = doc.select("div.y-big-title>h3").text();
//			Elements newsContentElment = doc.select("div.y-main-content");
//			StringBuffer newsContentAbc = new StringBuffer();
//			for (Element logSon : newsContentElment.select("span")){
//				
//				newsContentAbc.append(logSon.text());
	//
//				}
//			
//			
//			String newsContent = newsContentAbc.toString();
			
			System.out.println("here is  the test news infomation");
			System.out.println("time" + time);
			System.out.println("src" + src);
			System.out.println("picurl" + picUrl);
			System.out.println("newsContent" + newsContent);
			System.out.println("URL" + page.getUrl());
			System.out.println("newsTitle" + newsTitle);
			System.out.println("end");
			
			String urlCategory = thiscaegory;
			
			
			long m = System.currentTimeMillis() / 1000;

			String grabbedTime = String.valueOf(m);

			if (time != null && time.length() >= 5 &&newsContent!= null) {

				System.out.println("123");

				if (jdbcTemplate != null) {
					int updates = jdbcTemplate
							.update("insert into huashengDemo (ts,text,src,newsUrl,imageurl,grabbedTime,title,category) value(?,?,?,?,?,?,?,?)",
									time, newsContent, src, page.getUrl(), picUrl,
									grabbedTime, newsTitle,urlCategory);
					if (updates == 1) {
						System.out.println("mysql插入成功");
					}
					countingNumber++;
				}

			}
			
			
			
			
			Links nextLinks = new Links();
			nextLinks.addAllFromDocument(doc, regexRule);
			return nextLinks;
		}
	
}
