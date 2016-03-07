package wenjing.testdomo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.weiboapi.WeiboCN;
  
/** 
 * 
 * @author hu 
 */  
public class WeiboCrawler extends DeepCrawler{  
	
	
	private boolean firstTimeJudgeNumbers = true;
	private String WeiboNumbers;
	private static int KeyWordWeiboNumber = 0;
	private static int pageNumber = 0;
 
    public WeiboCrawler(String crawlPath,boolean firstTimeJudgeNumbers) throws Exception {  
        super(crawlPath);  
        /*获取新浪微博的cookie，账号密码以明文形式传输，请使用小号*/  
        String cookie=WeiboCN.getSinaCookie("sheepkui@gmail.com", "@Ywj1988");  
        HttpRequesterImpl myRequester=(HttpRequesterImpl) this.getHttpRequester();  
        myRequester.setCookie(cookie);  
        this.firstTimeJudgeNumbers = firstTimeJudgeNumbers; 
    }  
  
    @Override  
    public Links visitAndGetNextLinks(Page page) {  
        /*抽取微博*/  
        Elements weibos=page.getDoc().select("div.c");  
        if(firstTimeJudgeNumbers == true){
        for(Element weibo:weibos){   	
        
        	 WeiboNumbers = weibo.select("span.cmt").text().toString();
        	 
        	 if(WeiboNumbers.startsWith("共")){
        	 String regEx="[^0-9]";   
     		Pattern p = Pattern.compile(regEx);   
     		Matcher m = p.matcher(WeiboNumbers);   
     		//System.out.println( m.replaceAll("").trim());
     		KeyWordWeiboNumber = Integer.parseInt(m.replaceAll("").trim());
     		 System.out.println("KeyWordWeiboNumber"+KeyWordWeiboNumber);
     		break;
        	 }
        	
        }
        firstTimeJudgeNumbers = false;
        }else{
        	 for(Element weibo:weibos){  
        		 System.out.println("11");  
        		 /*
        		  * 大家想对微博数据做什么处理 就在这里做处理
        		  */
            }
        }
        /*如果要爬取评论，这里可以抽取评论页面的URL，返回*/  
        /*
         * 如果只要微博内容 就反回null即可
         */
        return null;  
    }  
      
    public static void main(String[] args) throws Exception{  
    	
    	/*
    	 * 我自己写得拼串得方法，只要输入关键字 以空格分开，开始时间 结束时间
    	 */
    	String weiboUrl = getQueryUrl("邓紫棋 湖南卫视","20150401","20150407");
    	
   
    	
        WeiboCrawler crawler=new WeiboCrawler("/Users/y/Desktop/testWebcollecter/data/weibo",true);  
        //最多线程数量
        crawler.setThreads(3);
        //爬虫种子
        crawler.addSeed(weiboUrl+"1");  
        //爬去深度 1就够了 
        crawler.start(1);  
        
        if(KeyWordWeiboNumber>1000){
        	pageNumber = 100;
        }else if (KeyWordWeiboNumber == 0){
        System.out.println("no weibo found");
        System.exit(0);
        }else{
        	pageNumber = KeyWordWeiboNumber/10 + 1;
        }
        
        WeiboCrawler crawlerSecond=new WeiboCrawler("/Users/y/Desktop/testWebcollecter/data/weibo",false);  
        crawlerSecond.setThreads(3);  
      for(int i=0;i<pageNumber;i++){  
    	  crawlerSecond.addSeed(weiboUrl+i);  
      }  
      crawlerSecond.start(1);
    }

    private static String getQueryUrl(String keywordList, String beginTime,
			String endTime) {
		// TODO Auto-generated method stub
		
		String[] keywordlist = keywordList.split(" ");
		
		StringBuffer abc = new StringBuffer();
		abc.append("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=");
		for(int i = 0;i<keywordlist.length;i++){
			abc.append(keywordlist[i]);
			if(i <( keywordlist.length-1)){
			abc.append("+");
		}
		}
		abc.append("&advancedfilter=1&");
		
		abc.append("starttime="+beginTime);
		abc.append("&endtime="+endTime+"&sort=time&page=");
		String newsQueryUrl = abc.toString();
		
		return newsQueryUrl;
	}	
      
}  