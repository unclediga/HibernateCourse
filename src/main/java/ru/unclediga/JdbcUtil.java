package ru.unclediga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
    public static void clearDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.addBatch("DELETE FROM book");
            statement.addBatch("DELETE FROM author");


            statement.addBatch("insert into Author  values (1, 'Author 1','Author 1 SCD')");
            statement.addBatch("insert into Author  values (2, 'Author 2','Author 2 SCD')");
            statement.addBatch("insert into Author  values (3, 'Author 3','Author 3 SCD')");
            statement.addBatch("insert into Author  values (4, 'Author NIL','Author NIL SCD')");

            statement.addBatch("insert into Book    values (11, 'Book 11 (Author 1)', 1)");
            statement.addBatch("insert into Book    values (12, 'Book 12 (Author 1)', 1)");
            statement.addBatch("insert into Book    values (13, 'Book 13 (Author 1)', 1)");

            statement.addBatch("insert into Book    values (21, 'Book 21 (Author 2)', 2)");
            statement.addBatch("insert into Book    values (22, 'Book 22 (Author 2)', 2)");

            statement.addBatch("insert into Book    values (33, 'Book 33 (Author 3)', 3)");

            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            statement.execute("ALTER SEQUENCE hibernate_seq RESTART WITH 100");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
