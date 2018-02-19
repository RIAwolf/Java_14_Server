package lt.kaunascoding.socket.server.model;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.*;

public class DBActions {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/kcs";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";

    private Connection _conn = null;
    private ObjectOutputStream _outputStream;

    public DBActions(ObjectOutputStream value) {
        _outputStream = value;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            _conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void spausdinkLentele(ResultSet values) {
        ResultSetMetaData lentelesInfo = null;
        try {
            lentelesInfo = values.getMetaData();

            while (values.next()) {
                for (int i = 1; i <= lentelesInfo.getColumnCount(); i++) {
                    _outputStream.writeUTF(values.getString(i) + " ");
                    System.out.print(values.getString(i) + " ");
                }
                _outputStream.writeUTF("\n");
                System.out.println();

            }
            _outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rodykPazymius() {
        Statement statement = null;
        try {
            statement = _conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM `marks`");
            spausdinkLentele(set);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
