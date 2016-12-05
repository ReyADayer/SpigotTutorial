package me.reyad.SpigotTutorial;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class StatusRecord {

    private Connection connection;
    private String host, database, username, password;
    private int port;

    public StatusRecord() {
        host = "localhost";
        port = 3306;
        database = "test_database";
        username = "root";
        password = "";
    }

    public void createStatus(Player player) {
        try {
            openConnection();
            String name = player.getName();
            UUID uuid = player.getUniqueId();

            String sql = "INSERT INTO players (name, uuid, level, exp ) VALUES (?, ?, 1, 0);";

            //SQL をプリコンパイル
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //パラメーターセット
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, uuid.toString());

            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Status getStatus(Player player) {
        Status status = new Status();
        try {
            UUID uuid = player.getUniqueId();
            openConnection();

            ResultSet resultSet = getResultSet(uuid);
            convertResultSet(status, resultSet);

            if (status.uuid == null) {
                createStatus(player);
                ResultSet newResultSet = getResultSet(uuid);
                convertResultSet(status, newResultSet);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    private ResultSet getResultSet(UUID uuid) throws SQLException {
        String sql = "SELECT * FROM players WHERE uuid = ?;";

        //SQL をプリコンパイル
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //パラメーターセット
        preparedStatement.setString(1, uuid.toString());

        return preparedStatement.executeQuery();
    }

    private void convertResultSet(Status status, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            status.name = resultSet.getString("name");
            status.uuid = resultSet.getString("uuid");
            status.level = resultSet.getInt("level");
            status.exp = resultSet.getInt("exp");
            break;
        }
    }

    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }
}
