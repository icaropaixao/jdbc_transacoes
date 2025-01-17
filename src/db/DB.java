package db;
import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try{
                Properties props = loadProperties(); // pegando as informações
                String url = props.getProperty("dburl"); //informações da url

                conn = DriverManager.getConnection(url,props);
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }


    // ler o  Db.properties
    private static Properties loadProperties(){
        try (FileInputStream fs = new FileInputStream("db.properties")){

            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch (IOException e){
            throw new DbException(e.getMessage());
        }
    }

    // Metodos para fechar as conexões e metodos
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if(st !=null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

    }

    public static void closeResultSet(ResultSet rs) {
        if(rs != null){
            try {
                rs.close();
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }



}
