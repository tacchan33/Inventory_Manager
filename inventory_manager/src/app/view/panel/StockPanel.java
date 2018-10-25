package app.view.panel;

import javax.swing.*;
import java.awt.*;

import com.phidget22.*;

import app.controller.Controller;
import app.sql.*;
import app.system.Config;

public class StockPanel extends JPanel{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static StockPanel instance = null;
    public static StockPanel getInstance(Controller ctrl){
        if(instance==null){
            instance = new StockPanel(ctrl);
        }
        return instance;
    }
    private StockPanel(Controller ctrl){
        System.out.println("[START]new StockPanel");
        controller = ctrl;
        setPanel();
        createObject();
        addObject();
        System.out.println("[OK]new StockPanel");
    }

    /* フィールド */
    private JTextField productId = new JTextField();
    private String[] num = {"個数","1","2","3","4","5","6","7","8","9"};
    private JComboBox<String> number = new JComboBox<String>(num);
    private JButton button = new JButton("補充");

    /* private メソッド */
    private void setPanel(){
        this.setName("StockPanel");
        this.setLayout(null);
        this.setSize(Config.MainPanelWidth,Config.MainPanelHeight);
        this.setLocation(Config.MenuPanelWidth,0);
        this.setVisible(false);

        JLabel title = new JLabel();
        title.setText("補充モード");
        title.setSize(200,40);
        title.setLocation(5,0);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title.setVisible(true);
        this.add(title);
    }

    private void createObject(){
        productId.setBounds(200,250,200,30);
        productId.setVisible(true);
        number.setBounds(450,250,70,30);
        number.setVisible(true);
        button.setBounds(250,350,200,60);
        button.setActionCommand("button");
        button.addActionListener(controller.StockPanelAction);
        button.setVisible(true);
    }


    private void addObject(){
        add(number);
        add(productId);
        add(number);
        add(button);
    }

    /* publicメソッド */
    public void resetText(){
        productId.setText("");
        number.setSelectedIndex(0);
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

    public int getNumber(){
        return number.getSelectedIndex();
    }

}