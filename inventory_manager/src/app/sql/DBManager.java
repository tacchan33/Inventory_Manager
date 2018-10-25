package app.sql;

import java.sql.*;

import app.controller.Controller;

public class DBManager{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static DBManager instance = null;
    public static DBManager getInstance(Controller ctrl){
        if(instance==null){
            instance = new DBManager(ctrl);
        }
        return instance;
    }
    private DBManager(Controller ctrl){
        System.out.println("[START]new DBManager");
        controller = ctrl;
        System.out.println("[OK]new DBManager");
    }

    /* フィールド */
    /* DB情報 */
    private final String server = "192.168.0.1";
    private final String port = "";
    private final String db = "database";
    private final String timeout = "3000";
    private final String path = "jdbc:mysql://" + server + "/" + db + "?connectTimeout=" + timeout;
    private  String user = "";
    private  String pass = "";
    private final String driver = "com.mysql.jdbc.Driver";

    /* DB接続 */
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = "";

    /* メソッド */
    public void setLogin(String u,String p){
        user = u;
        pass = p;
    }

    private void setSQL(String str){
        sql = str;
    }

    private boolean getConnection(){
        try{
            /* ドライバロード */
            Class.forName(driver);
            conn = DriverManager.getConnection(path,user,pass);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            System.out.println("getConnection");
            return true;
        }catch(SQLException ex){
            System.out.println("DBサーバに接続できない");
            controller.showDialog("error","データべースサーバに接続できません");
            return false;
        }catch(ClassNotFoundException ex){
            System.out.println("JDBCドライバがロードできません");
            controller.showDialog("error","JDBCドライバをロードできません\n"+"ダウンロードしてください\n"+"https://dev.mysql.com/downloads/connector/j/");
            return false;
        }
    }

     private boolean exeUpdate(){
        try{
            ps.executeUpdate();
            conn.commit();
            System.out.println("exeUpdate");
            return true;
        }catch(SQLException ex){
            System.out.println("SQLを実行できない");
            return false;
        }
    }

    private boolean exeQuery(){
        try{
            rs = ps.executeQuery();
            System.out.println("exeQuery");
            return true;
        }catch(SQLException ex){
            System.out.println("SQLを実行できない");
            return false;
        }
    }


    private void closeConnetion(){
        try{
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null ) conn.close();
            System.out.println("closeConnetion");
        }catch(SQLException ex){
            System.out.println("サーバから切断できません");
        }
    }

    public boolean setType(){
        System.out.println("[START]setType");
        boolean flag = false;
        
        try{
            setSQL("SELECT id,name FROM type");
            if(getConnection()){
                if(exeQuery()){
                    int cnt=0;
                    rs.last();
                    Type.setRow(rs.getRow());
                    rs.first();
                    int i = 0;
                    System.out.println("i番目→ID:NAME");
                    do{
                        Type.setData(i,rs.getInt(1),rs.getString(2));
                        System.out.println(i+"→"+Type.getId(i)+":"+Type.getName(i));
                        i++;
                    }while(rs.next());
                    closeConnetion();
                    flag = true;
                    System.out.println("[OK]setType");
                }
            }
            return flag;
        }catch(SQLException ex){
            return flag;
        }
    }

    public boolean setMaker(){
        System.out.println("[START]setMaker");
        boolean flag = false;
        try{
            setSQL("SELECT id,name FROM maker");
            if(getConnection()){
                if(exeQuery()){
                    int cnt=0;
                    rs.last();
                    Maker.setRow(rs.getRow());
                    rs.first();
                    int i = 0;
                    System.out.println("i番目→ID:NAME");
                    do{
                        Maker.setData(i,rs.getInt(1),rs.getString(2));
                        System.out.println(i+"→"+Maker.getId(i)+":"+Maker.getName(i));
                        i++;
                    }while(rs.next());
                    closeConnetion();
                    flag = true;
                    System.out.println("[OK]setMaker");
                }
            }
            return flag;
        }catch(SQLException ex){
            return flag;
        }
    }

    public boolean setProduct(){
        System.out.println("[START]setProduct");
        boolean flag = false;
        try{
            setSQL("SELECT id,name,type,maker,stock,lastuse FROM product");
            if(getConnection()){
                if(exeQuery()){
                    int cnt=0;
                    rs.last();
                    Product.setRow(rs.getRow());
                    rs.first();
                    int i = 0;
                    System.out.println("i番目→ID:NAME:TYPE:MAKER:STOCK,LASTUSE");
                    do{
                        Product.setData(i,rs.getString(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getDate(6));
                        System.out.println(i+"→"+Product.getId(i)+":"+Product.getName(i)+":"+Product.getType(i)+":"+Product.getMaker(i)+":"+Product.getStock(i)+":"+Product.getLastuse(i));
                        i++;
                    }while(rs.next());
                    closeConnetion();
                    flag = true;
                    System.out.println("[OK]setProduct");
                }
            }
            return flag;
        }catch(SQLException ex){
            return flag;
        }
    }

    public void insertType(String name) throws SQLException{
        try{
            if(!name.equals("")){
                setType();
                if(!Type.isOverlapName(name)){
                    setSQL("INSERT INTO type (`name`) value (?)");
                    if(getConnection()){
                        ps.setString(1,name);
                        if(exeUpdate()){
                            closeConnetion();
                            controller.showDialog("info","["+name+"]を登録しました");
                        }
                    }
                }else{
                    controller.showDialog("error","["+name+"]は既に登録されています");
                }
            }else{
                controller.showDialog("error","入力が不適切です");
            }
        }catch(SQLException ex){
            conn.rollback();
        }
    }

    public void insertMaker(String arg) throws SQLException{
        try{
            if(!arg.equals("")){
                setMaker();
                if(!Maker.isOverlapName(arg)){
                    setSQL("INSERT INTO maker (`name`) value (?)");
                    if(getConnection()){
                        ps.setString(1,arg);
                        if(exeUpdate()){
                            closeConnetion();
                            controller.showDialog("info","["+arg+"]を登録しました");
                        }
                    }
                }else{
                    controller.showDialog("error","["+arg+"]は既に登録されています");
                }
            }else{
                controller.showDialog("error","入力が不適切です");
            }
        }catch(SQLException ex){
            conn.rollback();
        }
    }

    public void insertProduct(String rfid,String name,int type,int maker) throws SQLException{
        try{
            if(!name.equals("")&&rfid.length()==10){
                setProduct();
                if(!Product.isOverlapId(rfid)){
                    if(!Product.isOverlapName(name)){
                        setSQL("INSERT INTO product (`id`,`name`,`type`,`maker`) value (?,?,?,?)");
                        if(getConnection()){
                            ps.setString(1,rfid);
                            ps.setString(2,name);
                            ps.setInt(3,type);
                            ps.setInt(4,maker);
                            if(exeUpdate()){
                                closeConnetion();
                                controller.showDialog("info","["+name+"]を登録しました\n"+rfid);
                            }
                        }
                    }else{
                        controller.showDialog("error","["+name+"]は既に登録されています");
                    }
                }else{
                    controller.showDialog("error","RFID["+rfid+"]は既に登録されています。");
                }
            }else{
                controller.showDialog("error","入力が不適切です");
            }
        }catch(SQLException ex){
            conn.rollback();
        }
    }

    public boolean increaseProduct(String id,int num) throws SQLException{
        try{
            if(id.length()==10&&num!=0){
                setProduct();
                if(Product.isOverlapId(id)){
                    int row = Product.searchRow(id);
                    int number = num + Product.getStock(row);
                    setSQL("UPDATE product SET stock=? WHERE id=?");
                    if(getConnection()){
                        ps.setInt(1,number);
                        ps.setString(2,id);
                        if(exeUpdate()){
                            closeConnetion();
                            controller.showDialog("info","["+id+"]を"+num+"個補充しました\n合計:"+number+"個");
                            return true;
                        }
                    }
                }else{
                    controller.showDialog("error","データベースに一致するRFIDではありません。\n新規追加モードに移ります。");
                    return false;
                }
            }else{
                controller.showDialog("error","入力が不適切です");
                return true;
            }
        }catch(SQLException ex){
            conn.rollback();
        }
        return true;
    }

    public void decreaseProduct(String id) throws SQLException{
        try{
            if(id.length()==10){
                setProduct();
                if(Product.isOverlapId(id)){
                    int number = Product.getStock( Product.searchRow(id) );
                    Date today = new Date(System.currentTimeMillis());
                    if(number>0){
                        number--;
                        setSQL("UPDATE product SET stock=?,lastuse=? WHERE id=?");
                        if(getConnection()){
                            ps.setInt(1,number);
                            ps.setDate(2,today);
                            ps.setString(3,id);
                            if(exeUpdate()){
                                closeConnetion();
                                controller.showDialog("info","["+id+"]を1個開封しました\n在庫数:"+number+"個");
                            }
                        }
                    }else{
                        controller.showDialog("error","在庫がありません");
                    }
                }else{
                    controller.showDialog("error","データベースに一致するRFIDではありません。");
                }
            }else{
                controller.showDialog("error","入力が不適切です");
            }
        }catch(SQLException ex){
            conn.rollback();
        }
    }

    public void deleteProduct(String name) throws SQLException{
        try{
            if(Product.getRow()!=0){
            setSQL("DELETE FROM product WHERE name=?");
            if(getConnection()){
                ps.setString(1,name);
                if(exeUpdate()){
                    closeConnetion();
                    controller.showDialog("info","["+name+"]を削除しました。");
                }
            }
            }else{
                controller.showDialog("error","商品がありません。");
            }
        }catch(SQLException ex){
            conn.rollback();
        }
    }

}