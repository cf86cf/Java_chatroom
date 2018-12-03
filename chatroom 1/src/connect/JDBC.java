package connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {

    private static String IP_ADDRESS = "Localhost";
    private static String PORT_NUM = "3306";
    private static String USER_NAME = "root";
    private static String PASSWORD = "161105";
    private static String DB_NAME = "chatroom";
    String url = "jdbc:mysql://localhost:3306/chatroom";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + IP_ADDRESS + ":" + PORT_NUM + "/" + DB_NAME +"?userSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT",
                    USER_NAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
