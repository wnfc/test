package days;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DaysClient{
public static void main(String[] args){		
		try{			
			Socket client = new Socket("127.0.0.1",6161);			
			DataInputStream datain = 
					new DataInputStream(client.getInputStream ());			
			DataOutputStream dataout = 
					new DataOutputStream(client.getOutputStream ());
			while(true){
				
				BufferedReader br = 
				new BufferedReader(new InputStreamReader(System.in));	
				
				System.out.println("请输入第一个日期（xxxx-xx-xx）：");
				String day1 = br.readLine ();				
				dataout.writeUTF (day1);
				dataout.flush ();				
				
				System.out.println("请输入第二个日期（xxxx-xx-xx）：");
				String day2 = br.readLine ();				
				dataout.writeUTF (day2);
				dataout.flush ();	
				
				int days= datain.readInt ();
				System.out.println("服务器返回  两个日期间隔的天数： " + days);				
			}			
		}catch(IOException e){			
			System.out.println(e.getMessage ());		
		}		
	}
}
