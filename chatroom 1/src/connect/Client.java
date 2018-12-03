package connect;

import gui.Gui_use;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client {
    public static final String IP_ADDR = "localhost";//服务器地址
    public static final int PORT = 12345;//服务器端口号
    private static boolean flag;
    static Socket socket = null;
    static private Random r = new Random();
    static InputStream is;
    static DataInputStream dataInputStream;
    public static Boolean connectToServer() {
        try {
            socket = new Socket(IP_ADDR, PORT);
            System.out.println("已连接到server...");
            new  Thread(Client::receiveMsg).start();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void receiveMsg() {
        try {
            is = socket.getInputStream();
            dataInputStream = new DataInputStream(is);
            String info = null;
            System.out.println("开始接受消息");
            while ((info = dataInputStream.readUTF()) != null  ) {
                if(info.contains("当前在线人数")){
                    Gui_use.showClientCount(info);
                }

                else if(info.contains("已上线") || info.contains("已下线")){
                    Gui_use.showOthersMsg(" [系统消息]: "+info);
                }
                else {
                    Gui_use.showOthersMsg(info);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void exitToServer() {
            System.exit(0);
    }

    public static void sendnewUserToServer(String string) {
        flag = true;
        int count = 0;
        while (flag && count <3) {
            try {
                count++;
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(string);
                flag = false;
                count=0;
            } catch (Exception e) {
                System.out.println("连接服务器失败：" + e.getMessage());
            }
        }
    }


    public static boolean sendToServer(String string) {
        flag = true;
        while (flag ) {
            try {
                //向服务器端发送数据
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(string);
                if ( flag ) {
                    break;
                }
                out.close();
            } catch (Exception e) {
                Gui_use.showOthersMsg(" [系统消息]: 连接异常!");
                flag = false;
                break;
            }
        }
        return flag;
    }
}  
