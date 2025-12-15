package com.secj3303.dao;
import com.secj3303.model.User;
public interface AuthDao {
User login(String email, String password);
void register(User user);
boolean emailExists(String email);

}
