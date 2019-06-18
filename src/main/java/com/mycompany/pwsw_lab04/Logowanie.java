package com.mycompany.pwsw_lab04;

import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name = "logowanie")
public class Logowanie {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    @Column(name = "imie")
    private String imie;
    @Column(name = "nazwisko")
    private String nazwisko;
    @Column(name = "login")
    private String login;
    @Column(name = "email")
    private String email;
    @Column(name = "haslo")
    private String haslo;
    @Column(name = "uprawnienia")
    private String uprawnienia="user";
    @Column(name = "data_rejestracji")
    private Date dataRejestracji;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public Date getDataRejestracji() {
        return dataRejestracji;
    }

    public void setDataRejestracji(Date dataRejestracji) {
        this.dataRejestracji = dataRejestracji;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Logowanie{" + "id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko + ", login=" + login + ", email=" + email + ", haslo=" + haslo + ", uprawnienia=" + uprawnienia + ", dataRejestracji=" + dataRejestracji + '}';
    }

    
    
}
