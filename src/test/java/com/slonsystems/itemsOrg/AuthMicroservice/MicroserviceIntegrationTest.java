package com.slonsystems.itemsOrg.AuthMicroservice;

import com.jayway.jsonpath.JsonPath;
import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Types;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {ApplicationStarter.class, TestConfiguration.class})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MicroserviceIntegrationTest {

    private final long tokenLifetime = 1000 * 60 * 60;

    @Autowired
    JdbcOperations jdbcOperations;

    @Autowired
    MockMvc mvc;

    @Test
    public void userRegistration() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders.post("/register?Login=test1&Password=1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status",equalTo("success")))
                .andDo(result -> {
                    SqlRowSet rs = jdbcOperations.queryForRowSet("SELECT COUNT(*) AS count FROM \"Users\" WHERE" +
                            " \"Login\"='"+"test1"
                            +"' AND \"Password\"='"+"1234"+"'");
                    rs.next();
                    boolean userExist = rs.getInt(1) == 1;
                    Assert.assertTrue(userExist);
                })
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    String token = JsonPath.read(json, "$.token");
                    SqlRowSet rs = jdbcOperations.queryForRowSet("SELECT COUNT(*) AS count FROM \"Users\" WHERE" +
                            " token='"+token+"'");
                    rs.next();
                    boolean uniqToken = rs.getInt(1) == 1;
                    Assert.assertTrue(uniqToken);
                });
    }

    @Test
    public void existedUserRegistration() throws Exception{
        User user = new User();
        user.setId((long) 200);
        user.setLogin("test2");
        user.setPassword("1234");
        addUser(user);

        this.mvc.perform(MockMvcRequestBuilders.post("/register?Login="+
                user.getLogin()+"&Password="+user.getPassword()))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status",equalTo("failed")))
                .andDo(result -> {
                    SqlRowSet rs = jdbcOperations.queryForRowSet("SELECT COUNT(*) AS count FROM \"Users\" WHERE" +
                            " \"Login\"='"+user.getLogin()
                            +"' AND \"Password\"='"+user.getPassword()+"'");
                    rs.next();
                    boolean userExist = rs.getInt(1) == 1;
                    Assert.assertTrue(userExist);
                });
    }

    @Test
    public void userAuthorisation() throws Exception{
        User user = new User();
        user.setId((long) 100);
        user.setLogin("AuthTest");
        user.setPassword("1234");
        addUser(user);

        this.mvc.perform(MockMvcRequestBuilders.post("/auth?Login="+
                user.getLogin()+"&Password="+user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status",equalTo("success")))
                .andExpect(jsonPath("$.token",notNullValue()))
                .andExpect(jsonPath("$.UID",equalTo(user.getId().intValue())))
                .andDo(result -> {
                    SqlRowSet rs = jdbcOperations.queryForRowSet("SELECT COUNT(*) AS count FROM \"Users\" WHERE" +
                            " \"Login\"='"+user.getLogin()
                            +"' AND \"Password\"='"+user.getPassword()+"'");
                    rs.next();
                    boolean userExist = rs.getInt(1) == 1;
                    Assert.assertTrue(userExist);
                })
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    String token = JsonPath.read(json, "$.token");
                    SqlRowSet rs = jdbcOperations.queryForRowSet("SELECT COUNT(*) AS count FROM \"Users\" WHERE" +
                            " token='"+token+"'");
                    rs.next();
                    boolean uniqToken = rs.getInt(1) == 1;
                    Assert.assertTrue(uniqToken);
                });
    }

    @Test
    public void notExistedUserAuthorisation() throws Exception{
        User user = new User();
        user.setId((long) 101);
        user.setLogin("AuthTest1");
        user.setPassword("1234");

        this.mvc.perform(MockMvcRequestBuilders.post("/auth?Login="+
                user.getLogin()+"&Password="+user.getPassword()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status",equalTo("failed")))
                .andDo(result -> {
                    SqlRowSet rs = jdbcOperations.queryForRowSet("SELECT COUNT(*) AS count FROM \"Users\" WHERE" +
                            " \"Login\"='"+user.getLogin()
                            +"' AND \"Password\"='"+user.getPassword()+"'");
                    rs.next();
                    boolean userExist = rs.getInt(1) == 1;
                    Assert.assertFalse(userExist);
                });
    }

    @Test
    public void tokenResolving() throws Exception{
        User user = new User();
        user.setId((long) 102);
        user.setLogin("TokenizedUser1");
        user.setPassword("1234");

        Object[] params = {user.getId(),user.getLogin(),user.getPassword(),new Date().getTime()};
        int[] types = {Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BIGINT};

        jdbcOperations.update("INSERT INTO \"Users\"(\"ID\",\"Login\"" +
                ",\"Password\",token,lifetime,tokenbirthtime) VALUES(?,?,?,'token1',"+tokenLifetime+",?)",params,types);

        this.mvc.perform(MockMvcRequestBuilders.get("/tokenResolve?Token=token1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.UID",equalTo(user.getId().intValue())));
    }

    @Test
    public void nonExistingTokenResolving() throws Exception{

        this.mvc.perform(MockMvcRequestBuilders.get("/tokenResolve?Token=token2"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deadTokenResolving() throws Exception{
        User user = new User();
        user.setId((long) 103);
        user.setLogin("TokenizedUser2");
        user.setPassword("1234");

        Date now = new Date();
        Object[] params = {user.getId(),user.getLogin(),user.getPassword(), now.getTime() - 2 * tokenLifetime};
        int[] types = {Types.BIGINT,Types.VARCHAR,Types.VARCHAR, Types.BIGINT};

        jdbcOperations.update("INSERT INTO \"Users\"(\"ID\",\"Login\"" +
                ",\"Password\",token,lifetime,tokenbirthtime) VALUES(?,?,?,'token3',"+tokenLifetime+",?)",params,types);

        this.mvc.perform(MockMvcRequestBuilders.get("/tokenResolve?Token=token1"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private void addUser(User user){
        Object[] params = new Object[] { user.getId(), user.getLogin(), user.getPassword() };
        int[] types = new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR };

        jdbcOperations.update("INSERT INTO \"Users\"(\n" +
                "            \"ID\", \"Login\", \"Password\")\n" +
                "    VALUES (?, ?, ?);", params, types);
    }

    private void removeUser(User object){
        jdbcOperations.update("DELETE FROM \"Users\"\n" +
                " WHERE \"ID\" = '" + object.getId().toString() + "';");
    }
}
