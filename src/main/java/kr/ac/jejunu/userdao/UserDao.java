package kr.ac.jejunu.userdao;

import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(Long id) throws SQLException {
        StatementStrategy statementStrategy = new GetStrategyStatement(id);
        return jdbcContext.jdbcContextforGet(statementStrategy);
    }

    public Long add(User user) throws SQLException {
        StatementStrategy statementStrategy = new AddStrategyStatement(user);
        return jdbcContext.jdbcContextforAdd(statementStrategy);
    }

    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = new UpdateStrategyStatement(user);
        jdbcContext.jdbcContextforUpdate(statementStrategy);
    }

    public void delete(User user) throws SQLException {
        StatementStrategy statementStrategy = new DeleteStrategyStatement(user);
        jdbcContext.jdbcContextforUpdate(statementStrategy);
    }

}
