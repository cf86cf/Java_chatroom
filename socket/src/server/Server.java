package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 12345;//�����Ķ˿ں�
    private static int count = 0;

    public void init() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = null;

            while (true) {
                // һ���ж���, ���ʾ��������ͻ��˻��������
                Socket client = serverSocket.accept();
                // �����������
                HandlerThread serverThread = new  HandlerThread(client);
                serverThread.start();
                count++;
                System.out.println("�ͻ���"+count+"����");
//                InetAddress address = socket.getInetAddress();
//                System.out.println("��ǰ�ͻ��˵�ipΪ��"+address.getHostAddress());
            }
        } catch (Exception e) {
            System.out.println("�������쳣: " + e.getMessage());
        }

        System.out.println("�û�" + count + "�˳�");
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
                // ��ȡ�ͻ�������
                while (true) {
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    String clientInputStr = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException

                    System.out.print(clientInputStr);
                }

                // ��ͻ��˻ظ���Ϣ
//                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//                System.out.print("������:\t");
                // ���ͼ��������һ��
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
                System.out.println("������ run �쳣: " + e.getMessage());
            } finally {
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (Exception e) {
//                        socket = null;
//                        System.out.println("����� finally �쳣:" + e.getMessage());
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

