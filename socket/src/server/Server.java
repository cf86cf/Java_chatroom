package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 12345;//监听的端口号
    private static int count = 0;//总登陆人数
    private static int onlinecount = 0;//当前在线人数
    static Socket[] client = new Socket[20];
    static boolean[] clientOnline = new boolean[20];
    static String[] clientname = new String[20];
    static boolean[] getClentinf = new boolean[20];
    public void init() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = null;

            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                client[count] = serverSocket.accept();

                // 处理这次连接
                HandlerThread serverThread = new  HandlerThread(client[count],count);
                serverThread.start();
                count++;
                onlinecount++;
//                System.out.println("新用户上线，当前在线人数："+onlinecount);
            }
        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }
    }

    private static class HandlerThread extends Thread implements Runnable {

        private Socket socket = null;
        private int theId;
        public HandlerThread(Socket client,int num) {
            theId = num;
            socket = new Socket();
            this.socket = client;
        }

        public void run() {

            DataOutputStream out[] = new DataOutputStream[20];
            DataInputStream input = null;
            String clientInputStr = null;
            getClentinf[theId] = false;
            clientname[theId] = null;
            try {
                // 读取客户端数据
                while (true) {

                    input = new DataInputStream(socket.getInputStream());
                    clientInputStr = input.readUTF();

                    if(!getClentinf[theId]) {
                        //给新上线用户发送当前在线人数及所有在线人的名称
                        getClentinf[theId] = true;
                        out[theId] = new DataOutputStream(client[theId].getOutputStream());
                        out[theId].writeUTF("当前在线人数：" + onlinecount + "  \n");
                        for (int j = 0; j < count ; j++) {
                            if(clientOnline[j] && j != theId)
                            //发送新用户名称
                            out[theId].writeUTF(clientname[j]);
                        }
                    }

                    if(clientname[theId] == null){
                        clientname[theId] = " "+clientInputStr+" ";
                        System.out.println("新用户"+clientname[theId]+"上线，当前在线人数："+onlinecount);
                        clientOnline[theId] = true;
                        /**
                         * 给其他所有用户发送新上线的用户名称并且在线人数显示++
                        */
                         for(int i = 0;i <count;i++) {

                            if(clientOnline[i] && i != theId) {
                                out[i] = new DataOutputStream(client[i].getOutputStream());
                                //发送新用户名称
                                out[i].writeUTF(clientname[theId]+"已上线~~\n");
                                out[i].writeUTF("当前在线人数："+onlinecount + "\n");
                            }
                        }

                    }
                    else {
                        System.out.print(clientInputStr);
                        //向其他所有在线用户发送信息；

                        for(int i = 0;i <count;i++) {
                            System.out.println(clientOnline[i]);
                            if(clientOnline[i] && i != theId) {
                                out[i] = new DataOutputStream(client[i].getOutputStream());

                                out[i].writeUTF(clientInputStr);

                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("用户" + (theId+1)+clientname[theId] + "退出");
                clientOnline[theId] = false;
                onlinecount--;
                try {
                    for (int i = 0; i < count; i++) {

                        if ( clientOnline[i] && i != theId ) {
                            out[i] = new DataOutputStream(client[i].getOutputStream());
                            //发送新用户名称
                            out[i].writeUTF(clientname[theId]+"已下线...\n");
                            out[i].writeUTF("当前在线人数："+onlinecount + "\n");
                        }

                    }
                }catch (Exception final_e){
                    final_e.printStackTrace();
                }
                try {
                    if(input!= null)
                    input.close();

                    if ( socket != null ) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
