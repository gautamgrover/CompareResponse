import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;


public class TestClass {

	private TrustManager[] getTrustingManager() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                // Do nothing
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                // Do nothing
            }

        } };
        return trustAllCerts;
    }
	
	void compareResponse(File f1, File f2) throws FileNotFoundException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
		
		
		
		FileReader fr1 = new FileReader(f1);
		FileReader fr2 = new FileReader(f2);
		
		BufferedReader br1 = new BufferedReader(fr1);
		BufferedReader br2 = new BufferedReader(fr2);
		
		String s1 = "";
		String s2 = "";
		int count = 1;
		boolean flag = true;
		try {
			while((s1=br1.readLine()) != null) {
				s2 = br2.readLine();
//				System.out.println("s1 :"+s1+" and s2 :"+s2);
				HttpClient client1 = new DefaultHttpClient();
				/*SSLContext sc = SSLContext.getInstance("SSL");
		        sc.init(null, getTrustingManager(), new java.security.SecureRandom());

		        SSLSocketFactory socketFactory = new SSLSocketFactory(sc);
		        Scheme sch = new Scheme("https", 443, socketFactory);
		        client1.getConnectionManager().getSchemeRegistry().register(sch);*/
				HttpGet req1 = new HttpGet(s1);
				HttpResponse res1 = client1.execute(req1);
				BufferedReader brd1 = new BufferedReader(new InputStreamReader(res1.getEntity().getContent()));
				String buff1, buff2;
				String line1="";String line2="";
				while((buff1 = brd1.readLine()) != null) {
//					System.out.println(buff1);
					line1 = line1+buff1;
				}
				
				HttpGet req2 = new HttpGet(s2);
				HttpResponse res2 = client1.execute(req2);
				BufferedReader brd2 = new BufferedReader(new InputStreamReader(res2.getEntity().getContent()));
				while((buff2 = brd2.readLine()) != null) {
//					System.out.println(buff2);
					line2 = line2+buff2;
				}
				if(line1.equals(line2)) {
					System.out.println(s1+" equals "+s2);
				} else {
					flag = false;
					System.out.println(s1+" not equals "+s2);
				}
				count++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(flag);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
		// TODO Auto-generated method stub
		
		File f1 = new File("src/RequestFiles/f1.txt");
		File f2 = new File("src/RequestFiles/f2.txt");
		
		TestClass tc = new TestClass();
		tc.compareResponse(f1, f2);

	}

}
