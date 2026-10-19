package com.codegym.dao;

import com.codegym.model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    List<User> selectUsers(String country, String sort) throws SQLException;
    User findById(int id) throws SQLException;
    void insert(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(int id) throws SQLException;
    void addUserTransaction(User user, int[] permissionIds) throws SQLException;
    void insertUpdateWithoutTransaction() throws SQLException;
    void insertUpdateUseTransaction() throws SQLException;
}

