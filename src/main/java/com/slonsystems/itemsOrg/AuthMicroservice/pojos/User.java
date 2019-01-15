package com.slonsystems.itemsOrg.AuthMicroservice.pojos;

import javax.persistence.*;

@Entity
@Table(name = "\"Users\"")
public class User {

    @Column(name = "\"ID\"") @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "\"Login\"")
    private String login;
    @Column(name = "\"Password\"")
    private String password;
    @Column(name = "token")
    private String token;
    @Column(name = "lifetime")
    private Long tokenLifeTime;
    @Column(name = "tokenbirthtime")
    private Long tokenBirthTime;

    public Long getTokenLifeTime() {
        return tokenLifeTime;
    }

    public void setTokenLifeTime(Long tokenLifeTime) {
        this.tokenLifeTime = tokenLifeTime;
    }

    public Long getTokenBirthTime() {
        return tokenBirthTime;
    }

    public void setTokenBirthTime(Long tokenBirthTime) {
        this.tokenBirthTime = tokenBirthTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
