package wenjing.testdomo;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.JDBCHelper;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

public class spiderModle {
//	public class chinaCancerSpider extends DeepCrawler {

//		RegexRule regexRule = new RegexRule();
//
//		JdbcTemplate jdbcTemplate = null;
//
//		static int countingNumber = 0;
//
//		
//		public chinaCancerSpider(String crawlPath) {
//			super(crawlPath);
//			// TODO Auto-generated constructor stub
//			
//			regexRule.addRule("http://www.iiyi.com/d.*");
//			regexRule.addRule("http://huiyi.iiyi.com/view/trailer/.*");
//			
//			try {
//				jdbcTemplate = JDBCHelper
//						.createMysqlTemplate(
//								"mysql1",
//								"jdbc:mysql://129.63.16.39/kuaiwenbaoDB?useUnicode=true&characterEncoding=utf8",
//								"root", "wtsql2014", 5, 30);
//
//				/* 创建数据表 */
//
//				System.out.println("连接成功");
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				jdbcTemplate = null;
//				System.out
//						.println("mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!");
//			}
//
//			
//		}
//
//		@Override
//		public Links visitAndGetNextLinks(Page page) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		public static void main(String[] args){
//			long beginTime = System.currentTimeMillis();
//			iiyiSpider crawler = new iiyiSpider(
//					"/Users/y/Desktop/testWebcollecter/data/iiyispider");
//			crawler.setThreads(5);
//			
//			
//			crawler.setResumable(false);
//			try {
//				crawler.start(2);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			long keepTime = System.currentTimeMillis() - beginTime;
//			//医视角 以及资讯头条，医学进展，诊疗指南，诊疗科学进展 肿瘤科诊疗指南 肿瘤科医学进展 前10页面
//			System.out.println("抓取所用时间"+keepTime / 1000);
//			System.out.println("抓取不重复条数"+countingNumber);
//			
//		}
//		
//	}

}
