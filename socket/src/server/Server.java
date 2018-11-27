package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 12345;//监听的端口号
    private static int count = 0;

    public void init() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = null;

            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                Socket client = serverSocket.accept();
                // 处理这次连接
                HandlerThread serverThread = new  HandlerThread(client);
                serverThread.start();
                count++;
                System.out.println("客户端"+count+"上线");
//                InetAddress address = socket.getInetAddress();
//                System.out.println("当前客户端的ip为："+address.getHostAddress());
            }
        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }

        System.out.println("用户" + count + "退出");
    }

    private static class HandlerThread extends Thread implements Runnable {

        private Socket socket = null;

        public HandlerThread(Socket client) {
            this.socket = client;
//            new Thread(this).start();
        }

        public void run() {
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            PrintWriter pw = null;
            try {
                // 读取客户端数据
                while (true) {
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException

                    System.out.print(clientInputStr);
                }

                // 向客户端回复信息
//                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//                System.out.print("请输入:\t");
                // 发送键盘输入的一行
//                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
//                out.writeUTF(s);

//                out.close();
//                input.close();
                /**--------------------------------------------------------*/
//                is = socket.getInputStream();
//                isr = new InputStreamReader(is);
//                br = new BufferedReader(isr);
//                String info = null;
//                while ((info = br.readLine()) != null) {
//                    System.out.println("The Client says:" + isr);
//                }
//                socket.shutdownInput();
//                os = socket.getOutputStream();
//                pw = new PrintWriter(os);
//                pw.write("welcome!");
//                pw.flush();


            } catch (Exception e) {
                System.out.println("服务器 run 异常: " + e.getMessage());
            } finally {
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (Exception e) {
//                        socket = null;
//                        System.out.println("服务端 finally 异常:" + e.getMessage());
//                    }
//                }
                try {
                    if ( pw != null )
                        pw.close();
                    if ( os != null )
                        os.close();
                    if ( br != null )
                        br.close();
                    if ( isr != null )
                        isr.close();
                    if ( is != null )
                        is.close();
                    if ( socket != null )
                        socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

