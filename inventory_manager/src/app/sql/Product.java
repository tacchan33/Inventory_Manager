package app.sql;

import java.sql.*;
import java.text.*;

public class Product{

    /* フィールド */
    private static int row = 0;
    public static String[] id = null;
    public static String[] name = null;
    public static int[] type = null;
    public static int[] maker = null;
    public static int[] stock = null;
    public static Date[] lastuse = null;

    public static void setRow(int r){
        row = r;
        id = new String[r];
        name = new String[r];
        type = new int[r];
        maker = new int[r];
        stock = new int[r];
        lastuse = new Date[r];
    }

    public static void setData(int r,String i,String n,int t,int m,int s,Date d){
        id[r] = i;
        name[r] = n;
        type[r] = t;
        maker[r] = m;
        stock[r] = s;
        lastuse[r] = d;
    }

    public static int getRow(){ return row; }

    public static String getId(int r){ return id[r]; }

    public static String getName(int r){ return name[r]; }

    public static int getType(int r){ return type[r]; }

    public static int getMaker(int r){ return maker[r]; }

    public static int getStock(int r){ return stock[r]; }
    
    public static String getLastuse(int r){
        String date = "";
        if(lastuse[r]!=null){
            date = new SimpleDateFormat("yyyy/MM/dd").format(lastuse[r]);
        }
        return date;
    }

    public static int searchRow(String i){
        int r = -1;
        for(int j=0;j<row;j++){
            if(i.equals(id[j])){
                r = j;
            }
        }
        return r;
    }

    public static boolean isOverlapId(String i){
        boolean r = false;
        for(int j=0;j<row;j++){
            if( i.equals(id[j]) ){
                r = true;
            }
        }
        return r;
    }

    public static boolean isOverlapName(String n){
        boolean r = false;
        for(int j=0;j<row;j++){
            if( n.equals(name[j]) ){
                r = true;
            }
        }
        return r;
    }

}