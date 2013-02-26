package sd.web.app.server;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;


public class Rest {
	public static final int HTTP_OK = 200;

   public Rest(){}

   public String doPut(String theText){
    	
    	int one = (int)theText.charAt(0);
    	int two = (int)theText.charAt(1);
    	int three = (int)theText.charAt(2);
    	//String three = c;
		final String PUTText = "<request><installationConfig><keypadBeepFreq>"+one+"</keypadBeepFreq><keypadBeepDuration>"+two+"</keypadBeepDuration><keypadBeepVolume>"+three+"</keypadBeepVolume><petWeight>5</petWeight></installationConfig></request>";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        
        try {
        	  HttpHost targetHost = new HttpHost("dev-gw-xbn-1.its.sec.intra");
              
        	httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(AuthScope.ANY),
                    new UsernamePasswordCredentials("APP/mcintapp", "123456"));
        	
            AuthCache authCache = new BasicAuthCache();
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(targetHost, basicAuth);
            BasicHttpContext localcontext = new BasicHttpContext();
            localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
            
            HttpPut httpPut = new HttpPut("/xbn/2/installation/00005778/config");
            
			StringEntity entity = new StringEntity(PUTText, "UTF-8");
			
			entity.setContentType("application/xml");
			
			httpPut.setEntity(entity);
            HttpResponse response = httpclient.execute(targetHost, httpPut, localcontext);
            httpPut.getRequestLine();
          
            	System.out.println("Skickar: '"+theText+"'----------------------------");
             System.out.println(
                		response.getStatusLine()
                	);
                          	System.out.println("------------------------------------Klart!");


                entity.getContentLength();
                //}
                EntityUtils.consume(entity);
 
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }

		return "OK";
   }
    }

