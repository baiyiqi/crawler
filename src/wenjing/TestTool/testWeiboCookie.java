package wenjing.TestTool;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class testWeiboCookie {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 StringBuilder sb = new StringBuilder();
	        HtmlUnitDriver driver = new HtmlUnitDriver();
	        driver.setJavascriptEnabled(true);
	        driver.get("http://login.weibo.cn/login/?ns=1&revalid=2&backURL=http%3A%2F%2Fweibo.cn%2F&backTitle=%CE%A2%B2%A9&vt=");

	        WebElement mobile = driver.findElementByCssSelector("input[name=mobile]");
	        mobile.sendKeys("18930339163");
	        WebElement pass = driver.findElementByCssSelector("input[name^=password]");
	        pass.sendKeys("@Ywj1988");
	        WebElement rem = driver.findElementByCssSelector("input[name=remember]");
	        rem.click();
	        WebElement submit = driver.findElementByCssSelector("input[name=submit]");
	        submit.click();

	        Set<Cookie> cookieSet = driver.manage().getCookies();
	        driver.close();
	        for (Cookie cookie : cookieSet) {
	            sb.append(cookie.getName()+"="+cookie.getValue()+";");
	        }
	        String result=sb.toString();
	        System.out.print(result);
	        if(result.contains("gsid_CTandWM")){
	            System.out.print("HAHA");
	        }else{
	        	 System.out.print("WUWU");
	        }
	}

}
