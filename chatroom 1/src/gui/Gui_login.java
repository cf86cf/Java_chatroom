package gui;
import connect.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Gui_login extends JFrame implements ActionListener{
    //窗口
    JFrame jf_login = new JFrame("Java聊天室");
    //标签
    JLabel  user = new JLabel("账号 : ");
    JLabel  password = new JLabel("密码 : ");

    JButton b_login = new JButton("登陆");
    JButton b_register = new JButton("注册");

    JTextField userField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    ImageIcon icon = new ImageIcon("src/icon/login.png");
    JLabel  jicon = new JLabel(icon);
    public Gui_login()
    {
        passwordField.setEchoChar('*');
        //图标

        icon.setImage(icon.getImage().getScaledInstance(30, 30, 10));

        user.setFont(new java.awt.Font("Dialog", 0, 15));
        password.setFont(new java.awt.Font("Dialog", 0, 15));

        user.setBounds(80,50,80,30);
        password.setBounds(80,85,80,30);
        b_register.setBounds(100,130,70,30);
        b_login.setBounds(230,130,70,30);
        //x + 50     y + 4    height -7
        userField.setBounds(130,54,160,23);
        passwordField.setBounds(130,89,160,23);

        jicon.setBounds(110,10,140,40);

        b_register.addActionListener(this);
        b_login.addActionListener(this);

        jf_login.add(user);
        jf_login.add(password);

        jf_login.add(userField);
        jf_login.add(passwordField);
        jf_login.add(b_login);
        jf_login.add(b_register);
        jf_login.add(jicon);

        jf_login.setLayout(null);
        jf_login.setVisible(true);        // 设置窗体可视
        jf_login.setSize(400, 230);        // 设置窗体大小
        jf_login.setResizable(false);
        jf_login.setLocation(500,300);
        jf_login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == b_login)
        {
            String userinput = userField.getText();
            String pswinput = new String(passwordField.getPassword());

            if(userinput.length() < 2 || pswinput.length() < 6 || pswinput.length() > 20)
            {
                JOptionPane.showMessageDialog(null, "账号或密码输入错误！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            else {
                //获取psw
                User user = new User();
                String rightpsw = user.getPsw(userinput);
                //密码正确->成功登陆
                if( pswinput.equals(rightpsw))
                {
                    Gui_use use = new Gui_use(userinput);
                    jf_login.setVisible(false);
                }
                //密码错误
                else
                    JOptionPane.showMessageDialog(null, "账号或密码输入错误！", "提示",JOptionPane.ERROR_MESSAGE);
            }

        }

        if(e.getSource() == b_register)
        {
            Gui_register register = new Gui_register();
            jf_login.setVisible(false);
        }
    }
}
