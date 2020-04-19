package dao.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConnecterSQL {

    private static ConnecterSQL connecter = null;

    //private static final String INSTANCE_NAME = "localhost";
    private static final String DATABASE_NAME = "accountdata";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "1234";
    //private static final String PORT = "3306";

    private static final String CONNECTION_URL = "jdbc:mysql://%1$s;"
            + "databaseName=%2$s;user=%3$s;pass=%4$s;port=%5$s;";
    /*private static final String CONNECTION_STRING = String.format(CONNECTION_URL,
            INSTANCE_NAME, DATABASE_NAME, USER_NAME, PASSWORD, PORT);*/
    String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?user=" + USER_NAME + "&password="
            + PASSWORD +
            "&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"; //connection pool

    private Connection connection;
    private Statement statement;

    private ConnecterSQL() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING,
                    USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnecterSQL getConnecter() {
        if (connecter == null || connecter.connection == null) {
            connecter = new ConnecterSQL();
        }

        return connecter;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}