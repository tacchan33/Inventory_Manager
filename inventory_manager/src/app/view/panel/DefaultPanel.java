package app.view.panel;

import javax.swing.*;
import java.awt.*;

import app.controller.Controller;
import app.system.Config;

public class DefaultPanel extends JPanel{

    /* コントローラ */
    private Controller controller = null;

    JLabel label1 = new JLabel("ユーザ名:");
    JLabel label2 = new JLabel("パスワード:");
    JTextField user = new JTextField();
    JTextField pass = new JTextField();
    JButton login = new JButton("ログイン");

    /* シングルトン */
    private static DefaultPanel instance = null;
    public static DefaultPanel getInstance(Controller ctrl){
        if(instance==null){
            instance = new DefaultPanel(ctrl);
        }
        return instance;
    }
    private DefaultPanel(Controller ctrl){
        System.out.println("[START]new DefaultPanel");
        controller = ctrl;
        setPanel();
        createObject();
        System.out.println("[OK]new DefaultPanel");
    }

    /* フィールド */

    /* private メソッド */
    private void setPanel(){
        this.setName("DefaultPanel");
        this.setLayout(null);
        this.setSize(Config.MainPanelWidth,Config.MainPanelHeight);
        this.setLocation(Config.MenuPanelWidth,0);
        this.setVisible(true);

        JLabel title = new JLabel();
        title.setText("ようこそ");
        title.setSize(200,40);
        title.setLocation(5,0);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title.setVisible(true);
        this.add(title);
    }

    private void createObject(){
        label1.setBounds(180,200,100,30);
        user.setBounds(280,200,120,30);
        label2.setBounds(180,250,100,30);
        pass.setBounds(280,250,120,30);
        login.setBounds(280,300,120,50);
        login.setActionCommand("login_button");
        login.addActionListener(controller.DefaultPanelAction);
        this.add(label1);
        this.add(user);
        this.add(label2);
        this.add(pass);
        this.add(login);
    }

    /* publicメソッド */
    public void changeEnable(){
        setEnabled(true);
        setVisible(true);
    }

    public void changeDisable(){
        setVisible(false);
        setEnabled(false);
    }

    public String getUser(){
        return user.getText();
    }

    public String getPassword(){
        return pass.getText();
    }

}