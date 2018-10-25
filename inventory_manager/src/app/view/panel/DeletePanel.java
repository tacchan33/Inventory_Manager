package app.view.panel;

import javax.swing.*;
import java.awt.*;

import app.controller.Controller;
import app.sql.*;
import app.system.Config;

public class DeletePanel extends JPanel{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static DeletePanel instance = null;
    public static DeletePanel getInstance(Controller ctrl){
        if(instance==null){
            instance = new DeletePanel(ctrl);
        }
        return instance;
    }
    private DeletePanel(Controller ctrl){
        System.out.println("[START]delete NewPanel");
        controller = ctrl;
        setPanel();
        createObject();
        addObject();
        System.out.println("[OK]delete NewPanel");
    }

    /* フィールド */
    private JButton button = new JButton("削除");
    private JComboBox<String> product = new JComboBox<String>();

    /* private メソッド */
    private void setPanel(){
        this.setName("DeletePanel");
        this.setLayout(null);
        this.setSize(Config.MainPanelWidth,Config.MainPanelHeight);
        this.setLocation(Config.MenuPanelWidth,0);
        this.setVisible(false);

        JLabel title = new JLabel();
        title.setText("削除モード");
        title.setSize(200,40);
        title.setLocation(5,0);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title.setVisible(true);
        this.add(title);
    }

    private void createObject(){
        button.setBounds(250,350,200,60);
        button.setActionCommand("button");
        button.addActionListener(controller.DeletePanelAction);
        button.setVisible(true);
        product.setBounds(490,280,200,30);
        for(int i=0;i<Product.getRow();i++){
            product.addItem(Product.getName(i));
        }
    }

    private void addObject(){
        add(button);
        add(product);   
    }

    /* publicメソッド */
    public void resetText(){
    }

    public void changeEnable(){
        for(int i=0;i<Product.getRow();i++){//コンボボックス　項目再生成
            product.addItem(Product.getName(i));
        }
        setEnabled(true);
        setVisible(true);
        System.out.println("削除モード");
    }

    public void changeDisable(){
        setVisible(false);
        setEnabled(false);
        resetText();//テキストフィールド初期化
        product.removeAllItems();//コンボボックス初期化
    }
    public String getProductName(){
        return (String)product.getSelectedItem();
        //return product.getText;
    }

}