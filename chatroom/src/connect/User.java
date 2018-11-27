package connect;

import jdk.nashorn.internal.scripts.JD;

import javax.jws.soap.SOAPBinding;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class User {
    private String id;
    private String psw;
    private String mail;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private  String getId(){
        return id;
    }
    private String getPsw(){
        return psw;
    }
    private String getMail(){
        return mail;
    }
    public User(){
        connection = JDBC.getConnection();
    }

    public void setId(String id){
        this.id = id;
    }

    public void setPsw(String psw){
        this.psw = psw;
    }
    public void setMail(String mail){
        this.mail = mail;
    }

    public boolean createUser(User usr) {

        String statement = "insert into information(id,psw,mail) values(?,?,?)";
        try {

            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,usr.id );
            preparedStatement.setString(2,usr.psw);
            preparedStatement.setString(3,usr.mail);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {
            }
        }
        return true;
    }

    public String getPsw(String name) {
        String statement = "select psw from information where  id = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                psw = resultSet.getString("psw");
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            closeAll();
        }
        return psw;
    }


    public String getMail(String name) {
        String statement = "select mail from information where  id = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mail = resultSet.getString("mail");
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            closeAll();
        }
        return mail;
    }

    public boolean checkExist(String name) {
        String statement = "select * from information where  id = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }finally {
            closeAll();
        }
    }


    private void closeAll() {
        try {
            if (!resultSet.isClosed()) {
                resultSet.close();
            }
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (Exception ignored) {

        }
    }
}
