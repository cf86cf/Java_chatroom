package gui;

import mail.*;
import connect.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;


public class Gui_register extends JFrame implements ActionListener{
    //窗口
    JFrame jf_register = new JFrame("注册");
    //标签
    JLabel  user = new JLabel("账号 : ");
    JLabel  password1 = new JLabel("请输入密码: ");
    JLabel  password2 = new JLabel("请再次输入密码: ");
    JLabel  mail = new JLabel("请输入验证邮箱: ");
    JLabel  verification = new JLabel("请输入验证码: ");


    JButton b_back = new JButton("返回");
    JButton b_register = new JButton("注册");
    JButton b_verification = new JButton("验证");

    JTextField userField = new JTextField();
    JPasswordField passwordField1 = new JPasswordField();
    JPasswordField passwordField2 = new JPasswordField();
    JTextField mailField = new JTextField();
    JTextField verificationField = new JTextField();
    ImageIcon icon = new ImageIcon("src/icon/login.png");
    JLabel  jicon = new JLabel(icon);

    String confirm_data = null;

    String username = null;
    String psw1 = null;
    String psw2 = null;
    String Email = null;
    String confirm = null;

    boolean flag = false;
    User check = new User();

    public Gui_register()
    {
        passwordField1.setEchoChar('*');
        passwordField2.setEchoChar('*');
        //图标

        icon.setImage(icon.getImage().getScaledInstance(30, 30, 10));

        user.setFont(new java.awt.Font("Dialog", 0, 15));
        password1.setFont(new java.awt.Font("Dialog", 0, 15));
        password2.setFont(new java.awt.Font("Dialog", 0, 15));
        mail.setFont(new java.awt.Font("Dialog", 0, 15));
        verification.setFont(new java.awt.Font("Dialog", 0, 15));

        user.setBounds(60,50,80,30);
        password1.setBounds(60,110,160,30);
        password2.setBounds(60,170,160,30);
        mail.setBounds(60,230,160,30);
        verification.setBounds(140,280,120,30);
        b_back.setBounds(60,345,70,30);
        b_register.setBounds(150,345,70,30);
        b_verification.setBounds(60,295,70,30);
        //x + 60     y + 4    height -7
        userField.setBounds(60,75,160,23);
        passwordField1.setBounds(60,135,160,23);
        passwordField2.setBounds(60,195,160,23);
        mailField.setBounds(60,255,160,23);
        verificationField.setBounds(140,305,80,23);

        jicon.setBounds(80,10,140,40);

        b_register.addActionListener(this);
        b_back.addActionListener(this);
        b_verification.addActionListener(this);

        jf_register.add(user);
        jf_register.add(password1);
        jf_register.add(password2);
        jf_register.add(mail);
        jf_register.add(verification);


        jf_register.add(userField);
        jf_register.add(passwordField1);
        jf_register.add(passwordField2);
        jf_register.add(mailField);
        jf_register.add(verificationField);

        jf_register.add(b_back);
        jf_register.add(b_register);
        jf_register.add(b_verification);
        jf_register.add(jicon);

        jf_register.setLayout(null);
        jf_register.setVisible(true);        // 设置窗体可视
        jf_register.setSize(300, 450);        // 设置窗体大小
        jf_register.setLocation(500,300);
        jf_register.setResizable(false);
        jf_register.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == b_back ) {
            Gui_login login = new Gui_login();
            jf_register.setVisible(false);
        }
        //验证
        if ( e.getSource() == b_verification ) {
            Email = mailField.getText();
            CheckEmail checkEmail = new CheckEmail();
            boolean result = checkEmail.isEmail(Email);
            if (result) {
                try {
                    username = userField.getText();
                    confirm_data=get();
                    qqmail send = new qqmail(username,confirm_data,Email);
                    System.out.println(confirm_data);
                    flag = true;

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "邮箱地址不合法！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        }
        if ( e.getSource() == b_register ) {

                username = userField.getText();
                psw1 = String.valueOf(passwordField1.getPassword());
                psw2 = String.valueOf(passwordField2.getPassword());
                Email = mailField.getText();
                confirm = verificationField.getText();


            if ( !flag )  //
            {
                JOptionPane.showMessageDialog(null, "请先进行验证！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else if ( username.length() < 2 ) {
                JOptionPane.showMessageDialog(null, "用户名不能少于2位！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else if ( !psw1.equals(psw2) ) {
                JOptionPane.showMessageDialog(null, " 两次密码不一致！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else if ( psw1.length() < 6 || psw1.length() > 20)
            {
                JOptionPane.showMessageDialog(null, "密码不能少于6位且不能多于20位！", "提示", JOptionPane.ERROR_MESSAGE);
            }

            else if ( confirm.length() != 6 ) {
                JOptionPane.showMessageDialog(null, "请输入正确的验证码！", "提示", JOptionPane.ERROR_MESSAGE);
            }

            else if ( !confirm.equals(confirm_data) ) {
                JOptionPane.showMessageDialog(null, "验证码错误", "提示", JOptionPane.ERROR_MESSAGE);
            }

            else if (check.checkExist(username)) {
                JOptionPane.showMessageDialog(null, "该用户名已被注册", "提示", JOptionPane.ERROR_MESSAGE);
            }

            //符合注册要求
            if ( confirm.length() == 6 && flag && psw1.equals(psw2) && username.length() >= 2 && psw2.length() >= 6 && psw2.length() <= 20  && confirm.equals(confirm_data) && !check.checkExist(username) ) {
                User newuser = new User();
                newuser.setId(username);
                newuser.setPsw(psw1);
                newuser.setMail(Email);
                boolean register_msg;
                try {
                    register_msg = check.createUser(newuser);

                    if ( register_msg ) {
                        JOptionPane.showMessageDialog(null, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        jf_register.setVisible(false);
                        Gui_use use = new Gui_use(username);

                    } else {
                        JOptionPane.showMessageDialog(null, "注册失败", "提示", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ee) {
                    System.out.println("Gui_register error");
                }
            }
        }
    }

    private class CheckEmail {

        public boolean isEmail(String email) {
            //判断是否为空邮箱
            int k = 0;
            if(email == null) {
                return false;
            }
        /*
          * 单引号引的数据 是char类型的
                                    双引号引的数据 是String类型的
                                    单引号只能引一个字符
                                    而双引号可以引0个及其以上*
          */

            //判断是否有仅有一个@且不能在开头或结尾
            if(email.indexOf("@") > 0 && email.indexOf('@') == email.lastIndexOf('@') && email.indexOf('@') < email.length()-1) {
                k++;
            }

            //判断"@"之后必须有"."且不能紧跟
            if(email.indexOf('.',email.indexOf('@')) > email.indexOf('@')+1 ) {
                k++;
            }
            //判断"@"之前或之后不能紧跟"."
            if(email.indexOf('.') < email.indexOf('@')-1 || email.indexOf('.') > email.indexOf('@')+1 ) {
                k++;
            }
            //@之前要有6个字符
            if(email.indexOf('@') > 5 ) {
                k++;
            }

            if(email.endsWith("com") || email.endsWith("org") || email.endsWith("cn") ||email.endsWith("net")) {
                k++;
            }
            if(k == 5) {
                return true;
            }
            return false;

        }
    }
    private String  get(){
        Random random = new Random();
        return (String.valueOf(random.nextInt(899999) + 100000)); //生成一个六位数随机数String  confirm_data
    }

}
