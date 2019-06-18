package com.mycompany.pwsw_lab04;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wydarzenia")
public class Wydarzenia {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "agenda")
    private String agenda;
    @Column(name = "data")
    private Date data;
    @Column(name = "godz")
    Time godz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getGodz() {
        return godz;
    }

    public void setGodz(Time godz) {
        this.godz = godz;
    }

    @Override
    public String toString() {
        return "Wydarzenia{" + "id=" + id + ", nazwa=" + nazwa + ", agenda=" + agenda + ", dataRejestracji=" + data + ", godz=" + godz + '}';
    }
    
    
}
