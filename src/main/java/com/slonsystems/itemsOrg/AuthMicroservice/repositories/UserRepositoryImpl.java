//package com.slonsystems.itemsOrg.AuthMicroservice.repositories;
//
//import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
//import com.slonsystems.itemsOrg.AuthMicroservice.utils.UserManagmentUtilits;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//
//import java.sql.Types;
//import java.util.Date;
//
//@org.springframework.stereotype.Repository
//public class UserRepositoryImpl implements UserRepository {
//
//    protected JdbcOperations jdbcOperations;
//
//    @Autowired
//    public UserRepositoryImpl(JdbcOperations jdbcOperations){
//        this.jdbcOperations = jdbcOperations;
//    }
//
//    public void persist(User object){
//
//        Object[] params = new Object[] { object.getLogin(), object.getPassword() };
//        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR };
//
//        jdbcOperations.update("INSERT INTO \"Users\"(\n" +
//                "            \"Login\", \"Password\")\n" +
//                "    VALUES (?, ?);", params, types);
//    }
//
//    public void delete(User object){
//        jdbcOperations.update("DELETE FROM \"Users\"\n" +
//                " WHERE \"ID\" = '" + object.getId().toString() + "';");
//    }
//
//    @Override
//    public void update(User object) {
//        Object[] params = new Object[] { object.getLogin(), object.getPassword() };
//        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR };
//
//        jdbcOperations.update("UPDATE \"Users\"\n" +
//                " SET \"Login\" = '?', \"Password\" = '?' " +
//                " WHERE \"ID\" = '" + object.getId().toString() + "';" ,params, types);
//    }
//
//    public User findByLogin(String login){
//        SqlRowSet set = jdbcOperations.queryForRowSet("SELECT * FROM \"Users\"" +
//                " WHERE \"Login\" = '" + login + "';");
//        if(set.next()) {
//            User result = new User();
//            result.setId(set.getLong("ID"));
//            result.setLogin(set.getString("Login"));
//            result.setPassword(set.getString("Password"));
//            return result;
//        }
//        return null;
//    }
//
//    @Override
//    public void appendToken(User user, String token) {
//        jdbcOperations.execute("UPDATE \"Users\" SET token='"
//                +token+"', lifetime='"+ UserManagmentUtilits.tokenLifetime
//                +"', tokenbirthtime='"+new Date().getTime() +"' WHERE \"ID\"='"+user.getId()+"'");
//    }
//
//    @Override
//    public long getUID(User user) {
//        SqlRowSet rs = jdbcOperations.queryForRowSet("SELECT \"ID\" FROM " +
//                "\"Users\" WHERE \"Login\"='"+user.getLogin()+"'");
//        rs.next();
//        return rs.getLong(1);
//    }
//
//}
