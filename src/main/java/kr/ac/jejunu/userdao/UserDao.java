package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User get(Long id) throws ClassNotFoundException, SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        //리턴
        return user;

    }

    public Long add(User user) throws SQLException, ClassNotFoundException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Long id;

        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("insert into userinfo(name, password) values(?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            id = getLastInsertId(connection, preparedStatement);
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }


        return id;
    }

    public Long getLastInsertId(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = null;
        Long id;
        try {
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement("select last_insert_id()");
            resultSet = preparedStatement1.executeQuery();
            resultSet.next();

            id = resultSet.getLong(1);
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }
}
