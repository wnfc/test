package days;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DaysServer{	
public static void main(String[] args) throws ParseException{		
		try{			
			ServerSocket server = new ServerSocket(6161);	//可以用命令行 netstat -an 命令查看监听端口			
			System.out.println("服务器已启动……");			
			Socket toClient = server.accept ();		
									
			DataInputStream datain = 
					new DataInputStream(toClient.getInputStream ());			
			DataOutputStream dataout = 
					new DataOutputStream(toClient.getOutputStream ());			
			while(true){				
				String  day1 = datain.readUTF();				
				System.out.println("客户输入的第一个日期： " + day1);		
				Date date1;
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(day1);
				
				String  day2 = datain.readUTF();				
				System.out.println("客户输入的第二个日期： " + day2);		
				Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(day2);
				
				int days = (int) ((date2.getTime() - date1.getTime())/(24*60*60*1000));
		
				dataout.writeInt(days);
				dataout.flush ();				
				System.out.println ("两个日期的间隔天数： " + days);
			}			
		}catch(IOException e){			
			System.out.println(e.getMessage ());
		}
	}
}
