package app.view.panel;

import javax.swing.*;
import java.awt.*;

import com.phidget22.*;

import app.controller.Controller;
import app.sql.*;
import app.system.Config;

public class FlowPanel extends JPanel{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static FlowPanel instance = null;
    public static FlowPanel getInstance(Controller ctrl){
        if(instance==null){
            instance = new FlowPanel(ctrl);
        }
        return instance;
    }
    private FlowPanel(Controller ctrl){
        System.out.println("[START]new FlowPanel");
        controller = ctrl;
        setPanel();
        createObject();
        addObject();
        System.out.println("[OK]new FlowPanel");
    }

    /* フィールド */
    private JTextField productId = new JTextField();
    private JButton button = new JButton("開封");

    /* private メソッド */
    private void setPanel(){
        this.setName("FlowPanel");
        this.setLayout(null);
        this.setSize(Config.MainPanelWidth,Config.MainPanelHeight);
        this.setLocation(Config.MenuPanelWidth,0);
        this.setVisible(false);

        JLabel title = new JLabel();
        title.setText("開封モード");
        title.setSize(200,40);
        title.setLocation(5,0);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title.setVisible(true);
        this.add(title);
    }

    private void createObject(){
        productId.setBounds(200,250,200,30);
        productId.setVisible(true);
        button.setBounds(250,350,200,60);
        button.setActionCommand("button");
        button.addActionListener(controller.FlowPanelAction);
        button.setVisible(true);
    }


    private void addObject(){
        add(productId);
        add(button);
    }

    /* publicメソッド */
    public void resetText(){
        productId.setText("");
    }

    public void changeEnable(){
        setEnabled(true);
        setVisible(true);
        System.out.println("補充モード");
    }

    public void changeDisable(){
        setVisible(false);
        setEnabled(false);
        resetText();//テキストフィールド初期化
    }

    public JTextField getRFIDTextBox(){
        return productId;
    }

    public String getID(){
        return productId.getText();
    }

}