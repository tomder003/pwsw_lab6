package com.mycompany.pwsw_lab04;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "zapisy")
public class Zapisy {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    @Column(name="id_wydarzenia")
    private Integer idWydarzenia;
    @Column(name="id_user")
    private Integer idUser;
    @Column(name = "typ_uczestnictwa")
    private String typUczestnictwa;
    @Column(name = "wyzywienie")
    private String wyzywienie;
    @Column(name = "potwierdzony")
    private boolean potwierdzony=false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdWydarzenia() {
        return idWydarzenia;
    }

    public void setIdWydarzenia(Integer idWydarzenia) {
        this.idWydarzenia = idWydarzenia;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    

    public String getTypUczestnictwa() {
        return typUczestnictwa;
    }

    public void setTypUczestnictwa(String typUczestnictwa) {
        this.typUczestnictwa = typUczestnictwa;
    }

    public String getWyzywienie() {
        return wyzywienie;
    }

    public void setWyzywienie(String wyzywienie) {
        this.wyzywienie = wyzywienie;
    }

    public boolean isPotwierdzony() {
        return potwierdzony;
    }

    public void setPotwierdzony(boolean potwierdzony) {
        this.potwierdzony = potwierdzony;
    }

    @Override
    public String toString() {
        return "Zapisy{" + "id=" + id + ", idWydarzenia=" + idWydarzenia + ", idUser=" + idUser + ", typUczestnictwa=" + typUczestnictwa + ", wyzywienie=" + wyzywienie + ", potwierdzony=" + potwierdzony + '}';
    }

    

    
}
