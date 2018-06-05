package days;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;

public class MultiDaysServer{	
	
public  static int count = 0 ;


public static void main(String[] args) throws ParseException{	
		  
		try{			
			ServerSocket server = new ServerSocket(6161);	//可以用命令行 netstat -an 命令查看监听端口			
			System.out.println("服务器已启动……");	
			while (true) {
				Socket toClient = server.accept ();					
				new MultiDaysServer().new Days_Thread(toClient).start();  //内部类的实例化写法
				//new Days_Thread(toClient).start();  //外部类的实例化写法
				
				System.out.println("当前用户数：" + ++count);			
			}
		
		}catch(IOException e){			
			System.out.println(e.getMessage ());
		}
	}




	//Days_Thread：内部类，与客户端通信的线程
	class Days_Thread extends Thread {
		Socket toClient;
		private DataInputStream datain;
		private DataOutputStream dataout;
		Days_Thread(Socket toClient) {
			this.toClient = toClient;			
		}
		
		
		// 从1个客户读取两个日期，将天数计算结果发给该客户
		public void run() {
			
			//生成32位UUID随机值				
			 String uuid = UUID.randomUUID().toString().replace("-", "");			
			try {
				datain = 
						new DataInputStream(toClient.getInputStream ());			
				dataout = 
						new DataOutputStream(toClient.getOutputStream ());
				 			
				
				while(true){				
					String  day1 = datain.readUTF();				
					System.out.println("客户"+ uuid +"输入的第一个日期： " + day1);		
					Date date1;
					date1 = new SimpleDateFormat("yyyy-MM-dd").parse(day1);
					
					String  day2 = datain.readUTF();				
					System.out.println("客户" + uuid + "输入的第二个日期： " + day2);		
					Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(day2);
					
					int days = (int) ((date2.getTime() - date1.getTime())/(24*60*60*1000));
			
					dataout.writeInt(days);
					dataout.flush ();
					System.out.println ("客户" + uuid + "两个日期的间隔天数： " + days);
				}	
			} catch (Exception e) {
			} finally {
				try {				
					if (datain != null)
						datain.close();
					if (dataout != null)
						dataout.close();
					if (toClient != null)
						toClient.close();
					count--;
					System.out.println("客户"+ uuid +"已离开..." + "当前用户数：" + count);
					
				} catch (Exception e) {
				}
			}
		}
	}
	
	
	// Manager类：管理连接客户的套接字
	/*	class Manager extends Vector<Socket> {
			void addSocket(Socket toClient) {
				super.add(toClient);
			}
			void removeSocket(Socket toClient) {
				super.remove(toClient);
			}
			// 给所有客户机传送msg
			synchronized void sendToAll(String msg) {
				PrintWriter writer = null;
				Socket socket;
				for (int i = 0; i < this.size(); i++) 
				{ // 循环实现找到每一个客户连接，并发送消息
					socket = this.get(i);
					//获取第i个连接客户机的套接字
					try {
						writer = 
				new PrintWriter(socket.getOutputStream(), true);
						writer.println(msg);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			// 发送当前访问人数给客户机
			synchronized void sendClientNum() {
				String info = "当前连接数： " + this.size();
				System.out.println(info);
				sendToAll(info);
			}
		}*/

}


