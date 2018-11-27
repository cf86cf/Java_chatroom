/**
 * GBK 编码
 */


package connect;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client {
	public static final String IP_ADDR = "localhost";//服务器地址 
	public static final int PORT = 12345;//服务器端口号
	private static boolean flag ;
	static Socket socket = null;
	static  private Random r = new Random();
	public static Boolean connectToServer(){
		try{
			socket = new Socket(IP_ADDR,PORT);
			System.out.println("connect to server");

			return  true;
		}catch (Exception e){
			System.out.println(e.getMessage());
			return  false;
		}
	}
	public static boolean exitToServer(){
		try {
			socket.close();
			return  true;
		} catch (IOException e) {
			e.printStackTrace();
			return  false;
		}
	}

	/**
	 * //获取输出流，向服务端发送数据
	 * 			OutputStream os = socket.getOutputStream();
	 * 			PrintWriter pw = new PrintWriter(os);
	 * 			long name = Math.abs(r.nextLong());
	 * 			long pass = Math.abs(r.nextLong());
	 * 			pw.write("username"+name+"pass"+pass);
	 * 			pw.flush();
	 * 			socket.shutdownOutput();
	 *
	 * 			//获取输入流，读取服务端的响应信息
	 * 			InputStream is = socket.getInputStream();
	 * 			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 * 			String info = null;
	 * 			while ((info = br.readLine())!= null){
	 * 				System.out.println("The server says:" + info);
	 *                        }
	 * 			br.close();
	 * 			is.close();
	 * 			pw.close();
	 * 			os.close();
	 * @param string
	 * @return
	 */


    public static boolean sendToServer(String string) {
		flag =true;
        while (flag) {
        	try {

        		//创建一个流套接字并将其连接到指定主机上的指定端口号
	            //读取服务器端数据  
//	            DataInputStream input = new DataInputStream(socket.getInputStream());
	            //向服务器端发送数据

	            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//	            System.out.print("请输入: \t");

//	            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
	            out.writeUTF(string);

	            //服务器反馈的信息
//	            String ret = input.readUTF();
//	            System.out.println("服务器端返回过来的是: " + ret);
	            // 如接收到 "OK" 则断开连接  
//	            if ("OK".equals(ret)) {
				if(flag){
	                Thread.sleep(500);
	                break;
	            }
	            out.close();
//	            input.close();
        	} catch (Exception e) {
        		System.out.println("客户端异常: " + e.getMessage());
        		flag = false;
        		break;
        	}


        }
        return flag;
    }
}  
