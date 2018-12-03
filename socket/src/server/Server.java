package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 12345;//�����Ķ˿ں�
    private static int count = 0;//�ܵ�½����
    private static int onlinecount = 0;//��ǰ��������
    static Socket[] client = new Socket[20];
    static boolean[] clientOnline = new boolean[20];
    static String[] clientname = new String[20];
    static boolean[] getClentinf = new boolean[20];
    public void init() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = null;

            while (true) {
                // һ���ж���, ���ʾ��������ͻ��˻��������
                client[count] = serverSocket.accept();

                // �����������
                HandlerThread serverThread = new  HandlerThread(client[count],count);
                serverThread.start();
                count++;
                onlinecount++;
//                System.out.println("���û����ߣ���ǰ����������"+onlinecount);
            }
        } catch (Exception e) {
            System.out.println("�������쳣: " + e.getMessage());
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
                // ��ȡ�ͻ�������
                while (true) {

                    input = new DataInputStream(socket.getInputStream());
                    clientInputStr = input.readUTF();

                    if(!getClentinf[theId]) {
                        //���������û����͵�ǰ�������������������˵�����
                        getClentinf[theId] = true;
                        out[theId] = new DataOutputStream(client[theId].getOutputStream());
                        out[theId].writeUTF("��ǰ����������" + onlinecount + "  \n");
                        for (int j = 0; j < count ; j++) {
                            if(clientOnline[j] && j != theId)
                            //�������û�����
                            out[theId].writeUTF(clientname[j]);
                        }
                    }

                    if(clientname[theId] == null){
                        clientname[theId] = " "+clientInputStr+" ";
                        System.out.println("���û�"+clientname[theId]+"���ߣ���ǰ����������"+onlinecount);
                        clientOnline[theId] = true;
                        /**
                         * �����������û����������ߵ��û����Ʋ�������������ʾ++
                        */
                         for(int i = 0;i <count;i++) {

                            if(clientOnline[i] && i != theId) {
                                out[i] = new DataOutputStream(client[i].getOutputStream());
                                //�������û�����
                                out[i].writeUTF(clientname[theId]+"������~~\n");
                                out[i].writeUTF("��ǰ����������"+onlinecount + "\n");
                            }
                        }

                    }
                    else {
                        System.out.print(clientInputStr);
                        //���������������û�������Ϣ��

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
                System.out.println("�û�" + (theId+1)+clientname[theId] + "�˳�");
                clientOnline[theId] = false;
                onlinecount--;
                try {
                    for (int i = 0; i < count; i++) {

                        if ( clientOnline[i] && i != theId ) {
                            out[i] = new DataOutputStream(client[i].getOutputStream());
                            //�������û�����
                            out[i].writeUTF(clientname[theId]+"������...\n");
                            out[i].writeUTF("��ǰ����������"+onlinecount + "\n");
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
