import java.io.*;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.logging.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.*;
import org.apache.commons.httpclient.methods.*;


class LinguisticAnalyzer{
  private StringBuffer review_buffer;
  private String analysis_result;
  private HashMap<String,Integer> word_map;
  private String[] pos;
  private String[] neg;  
  public LinguisticAnalyzer(){
    review_buffer = new StringBuffer();
    analysis_result = new String("Neutral");
    pos = new String[]{"dazzling","brilliant","phenomenal","excellent","fantastic","gripping","mesmerizing","riveting","spectacular","cool","awesome","thrilling","badass","moving","exciting","good","wonderful","not bad","nice","fantastic","love","useful","well explained","worth","worthy","great"};
    neg = new String[]{"suck","terrible","awful","hideous","bad","boring","stupid","worst","stupid","waste","\\?","sad","sadly","ridicious","terribly","hard","worse","lack","poorly"};
    word_map = new HashMap<String,Integer>();
    for(String word:pos){
      word_map.put(word,1);
    }
    for(String word:neg){
      word_map.put(word,-1);
    }
  }
  public String GetReview(){
    String review = new String(review_buffer);
    return review;
  }

  public String GetResult(){
    return analysis_result;
  }

  public int SentimentAnalyzer(){
    String review = new String(review_buffer);
    int count=0; 
    for(String word:pos){
      Pattern p = Pattern.compile(word,Pattern.CASE_INSENSITIVE);
      Matcher m = p.matcher(review);
      while(m.find())
        count++;
    }
    for(String word:neg){
      Pattern p = Pattern.compile(word,Pattern.CASE_INSENSITIVE);
      Matcher m = p.matcher(review);
      while(m.find())
        count--;
    }
    return count;
  }
  public void  ReviewDownload(String input_url) throws Exception{
    int status = this.ReviewDownload_helper(input_url);
    int count = 5;
    while( ((status == -1)||(status >=500))&&(count>=0) ){
      status = this.ReviewDownload_helper(input_url);
      count--;
    }
  }
  public int ReviewDownload_helper(String input_url) throws Exception{
    review_buffer.delete(0,review_buffer.length());
    HttpClient httpclient = new HttpClient();
    GetMethod getmethod = new GetMethod(input_url);
    DefaultMethodRetryHandler retryhandler = new DefaultMethodRetryHandler();
    retryhandler.setRequestSentRetryEnabled(false);
    retryhandler.setRetryCount(5);
    getmethod.setMethodRetryHandler(retryhandler);
    getmethod.addRequestHeader("Accept", "text/html");
    int statusCode=0;
    try{
      statusCode = httpclient.executeMethod(getmethod);
    }
    catch(Exception e){
      getmethod.releaseConnection();
      return -1;
    }
    if (statusCode>500){
      return 503;
    }
    BufferedReader reviewstream = new BufferedReader(new InputStreamReader(getmethod.getResponseBodyAsStream())); 
    String str = new String();
    String review_url = new String();
    Pattern p = Pattern.compile("<a href=\"(.*?)\".*target=.*>See all .*? customer reviews...</a>");
    boolean flag = false;
    while((str=reviewstream.readLine())!=null){
      Matcher m = p.matcher(str);
      if(m.find()){
        flag = true;
        review_url = new String(m.group(1));
        break;  
      }
    }
    getmethod.releaseConnection();
    if(!flag)
      return -1;
    HttpClient httpclient1 = new HttpClient(); 
    GetMethod getmethod1 = new GetMethod(review_url);
    getmethod1.setMethodRetryHandler(retryhandler);
    getmethod1.addRequestHeader("Accept", "text/html");
    int statusCode1;
    try{
      statusCode1 = httpclient1.executeMethod(getmethod1);
    }
    catch(Exception e){
      getmethod1.releaseConnection();
      return -1;
    }
    System.out.println(statusCode1);
    if (statusCode1>=500){
      return 503;
    }
    reviewstream = new BufferedReader(new InputStreamReader(getmethod1.getResponseBodyAsStream()));
    Pattern p1 = Pattern.compile("<span class=\"a-size-base review-text\">(.*?)</span>");
    while((str=reviewstream.readLine())!=null){
      Matcher m = p1.matcher(str);
      if(m.find()){
        review_buffer.append(m.group(1));
        review_buffer.append("\n");
        review_buffer.append("\n");
     }
    }
    getmethod1.releaseConnection();
    return statusCode1;
  }

};


/*
public class SimpleClient{
  public static void main(String[] args) throws Exception {
    LinguisticAnalyzer analyzer = new LinguisticAnalyzer();
    analyzer.ReviewDownload("http://www.amazon.com/reviews/iframe?akid=AKIAI3OXGOGWKURY554Q&alinkCode=sp1&asin=0133943038&atag=smartbuy002-20&exp=2016-02-24T19%3A28%3A56Z&v=2&sig=fryIZJ%2FMLEmBNK2TwK%2F6%2FhDAY1qaOzh4JlxR0C1%2B%2FbA%3D");
    System.out.println(analyzer.SentimentAnalyzer());
  }
};*/
