/**
 * UTF-8编码
 */

package gui;

import connect.*;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Gui_use extends JFrame implements ActionListener{
    String user;
    static JLayeredPane layeredPane;
    static JLabel usersbox = new JLabel("当前在线用户：");
    JPanel jPanel =new JPanel();
    JPanel tianxianpanel =new JPanel();
    JLabel jLable;
    JLabel giflabel;
    ImageIcon image;
    ImageIcon tianxian;
    static JFrame jf_use = new JFrame();
    JButton b_back = new JButton("退出");
    JButton b_send = new JButton("发送");
    static JTextPane communicatepanel = new JTextPane();
    JTextPane inputField = new JTextPane();
    static JPanel onlineUserBox = new JPanel();
    static StyledDocument inputmsg = communicatepanel.getStyledDocument();
    String textinput ;
    Client client ;
    boolean flag_sendToFrame = false;
    boolean flag_sendTOServer = false;
    boolean flag_connectToServer = false;


    public Gui_use(String name)
    {
        //连接服务器并向服务器发送用户信息
        this.user = name;
        try {
            flag_connectToServer = client.connectToServer();
            client.sendnewUserToServer(user);

        }catch (Exception e){
            e.printStackTrace();
        }

        //background-image
        jf_use.setTitle("Java聊天室--"+user);
        layeredPane=new JLayeredPane();
        image=new ImageIcon("src/icon/bcgimg.jpg");
        jPanel.setBounds(0,-5,750,550);
        jLable=new JLabel(image);
        jPanel.add(jLable);
        layeredPane.add(jPanel,JLayeredPane.DEFAULT_LAYER);

        tianxian = new ImageIcon("src/icon/天线宝宝.gif");
        giflabel = new JLabel(tianxian);
        tianxianpanel.add(giflabel);
        tianxianpanel.setBounds(560,80,180,170);
        layeredPane.add(tianxianpanel,JLayeredPane.DRAG_LAYER);


        //button
        b_back.setBounds(660,430,70,30);
        b_send.setBounds(560,430,70,30);
        layeredPane.add(b_back,JLayeredPane.MODAL_LAYER);
        layeredPane.add(b_send,JLayeredPane.MODAL_LAYER);
        b_back.addActionListener(this);
        b_send.addActionListener(this);
        this.getRootPane().setDefaultButton(b_send);


        //TextPanel

        inputField.setBounds(5,360,550,150);
        communicatepanel.setFont(new Font("Consolas",Font.PLAIN, 16));
        inputField.setFont(new Font("Consolas",Font.PLAIN, 16));
        layeredPane.add(inputField,JLayeredPane.MODAL_LAYER);
        communicatepanel.setEditable(false);
        communicatepanel.setText(" [系统消息]: 欢迎" + name + "进入聊天室...\n");

        //滚动条
        JScrollPane jsp = new JScrollPane(communicatepanel);
        jsp.setBounds(5,5,550,350);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        layeredPane.add(jsp,JLayeredPane.MODAL_LAYER);

        //online-user
        onlineUserBox.setBounds(560,5,180,40);
        usersbox.setFont(new Font("黑体",Font.PLAIN, 16));
        onlineUserBox.add(usersbox);
        layeredPane.add(onlineUserBox,JLayeredPane.DRAG_LAYER);

        jf_use.setLayeredPane(layeredPane);
        jf_use.setResizable(false);
        jf_use.setSize(750,550);
        jf_use.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf_use.setLocation(300,200);
        jf_use.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b_back) {

            client.exitToServer();
            jf_use.setVisible(false);
            System.exit(0);
        }
        if(e.getSource() == b_send){
            //客户端

            textinput = inputField.getText();
            if ( textinput.length() > 0 ) {
                if(!flag_connectToServer) {
                    try {
                        flag_connectToServer = client.connectToServer();
                        client.sendnewUserToServer(user);
                    } catch (Exception cone) {
                        cone.printStackTrace();
                        flag_connectToServer = false;
                    }
                }
                sendMsg(textinput);
                if ( flag_sendToFrame&&flag_sendTOServer ) {
                        System.out.println("发送消息成功！");
                        flag_sendToFrame = false;
                        flag_sendTOServer = false;
                }
                else {
                    JOptionPane.showMessageDialog(null, "发送消息失败！", "提示", JOptionPane.ERROR_MESSAGE);
                }

            }
            else JOptionPane.showMessageDialog(null, "消息内容为空！", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void showOthersMsg(String text){
        try {
            inputmsg.insertString(inputmsg.getLength(), text, null);
            communicatepanel.validate();
            communicatepanel.repaint();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void showClientCount(String  str){
        usersbox.setText(str);
    }



    public void sendMsg(String text){
        if (text.contains("\n") || text.contains("\t")){
                text = text.replaceAll("\n"," ");
                text = text.replaceAll("\t"," ");
        }
        String msg = " [" + user + "]: " + text + "\n";
        flag_sendTOServer= client.sendToServer(msg);

        if(flag_sendTOServer) {
            try {
                inputmsg.insertString(inputmsg.getLength(), msg, null);
                communicatepanel.validate();
                communicatepanel.repaint();
                inputField.setText("");
                flag_sendToFrame = true;
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }


}



