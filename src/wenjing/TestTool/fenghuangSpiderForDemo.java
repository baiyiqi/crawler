package wenjing.TestTool;

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

public class fenghuangSpiderForDemo extends DeepCrawler {


	
	public fenghuangSpiderForDemo(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub	
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// TODO Auto-generated method stub

		
		return null;
		
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		fenghuangSpiderForDemo crawler = new fenghuangSpiderForDemo(
				"/Users/y/Desktop/testWebcollecter/data/fenghuangSpiderForDemo");
		
		crawler.addSeed("http://news.ifeng.com/");
		
		

		try {
			crawler.start(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
		
	}

}

