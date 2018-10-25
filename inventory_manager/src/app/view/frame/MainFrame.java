package app.view.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import app.controller.Controller;
import app.system.Config;
import app.view.panel.*;

public class MainFrame extends JFrame{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static MainFrame instance = null;
    public static MainFrame getInstance(Controller ctrl){
        if(instance==null){
            instance = new MainFrame(ctrl);
        }
        return instance;
    }
    private MainFrame(Controller ctrl){
        System.out.println("[START]new MainFrame");
        controller = ctrl;
        setFrame();
        System.out.println("[OK]new MainFrame");
    }

    /* フィールド */

    /* メソッド */
    private void setFrame(){
        this.setName("MainFrame");
        this.setTitle("在庫管理ソフト");
        this.setLayout(null);
        this.setSize(Config.FrameWidth,Config.FrameHeight);
        this.setResizable(false);
        this.setLocation(0,0);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    public void closeFrame(){
        int response = JOptionPane.showConfirmDialog(this,"終了しますか？");
        if(response==0){
            System.out.println("終了");
            System.exit(0);
        }
    }

}