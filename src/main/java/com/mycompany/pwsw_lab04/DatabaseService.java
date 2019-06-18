package com.mycompany.pwsw_lab04;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DatabaseService {

    private Connection polacz() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", "root", "");
            System.out.println("Połączono z bazą");
        } catch (Exception ex) {
            System.out.println("polacz() - " + ex.toString());
        }
        return connection;
    }

    private void rozlacz(Connection connection) {
        try {
            connection.close();
            System.out.println("Rozłączono z bazą");
        } catch (Exception ex) {
            System.out.println("Nie rozłączono z bazą");
        }
    }

    public void rejestruj(String imie, String nazwisko, String login, String haslo, String email) {
        boolean poprawnosc = true;
        if (imie.length() > 50 || nazwisko.length() > 50 || haslo.length() > 30 || email.length() > 50) {
            poprawnosc = false;
        }
        if (poprawnosc) {
            if (Pattern.matches("[a-zA-Z0-9]{1,}", login)) {
                try {
//                    Connection connection = polacz();
//                    Statement stmt = connection.createStatement();
//                    ResultSet rs = stmt.executeQuery("SELECT * FROM logowanie WHERE login='" + login + "';");
                    int ile = 0;
//                    while (rs.next()) {
//                        ile++;
//                    }
                    SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
                    Session session = sessionFactory.openSession();
                    List<Logowanie> logowanie = session.createQuery("from Logowanie").list();
                    int ile2 = (int) logowanie.stream().filter(log -> log.getLogin().equals(login)).count();
                    if (ile2 == 0) {
                        LocalDate localDate = LocalDate.now();
                        //stmt.execute("INSERT INTO logowanie (imie,nazwisko,login,haslo,email,uprawnienia,data_rejestracji) VALUES ('" + imie + "','" + nazwisko + "','" + login + "','" + haslo + "','" + email + "','user', '" + (localDate.getYear()) + "-" + (localDate.getMonthValue()) + "-" + localDate.getDayOfMonth() + "')");
                        Logowanie newLogin = new Logowanie();
                        newLogin.setLogin(login);
                        newLogin.setImie(imie);
                        newLogin.setNazwisko(nazwisko);
                        newLogin.setHaslo(haslo);
                        newLogin.setEmail(email);
                        newLogin.setDataRejestracji(new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth()));
                        Transaction tx = session.beginTransaction();
                        session.save(newLogin);
                        tx.commit();
                        //System.out.println((localDate.getYear())+"-"+(localDate.getMonthValue())+"-"+localDate.getDayOfMonth());
                        JOptionPane.showMessageDialog(null, "Zarejestrowano", "Infomracja ", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Login zajęty", "Infomracja ", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //rozlacz(connection);
                    session.close();
                    sessionFactory.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), "Błąd ", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Login może zawierać tylko znaki i cyfry", "Błąd ", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Niepoprawne dane!", "Błąd ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] zaloguj(String login, String haslo) {
        String[] dane = new String[6];
        dane[0] = "niezalogowany";
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM logowanie WHERE login='" + login + "';");
            int ile = 0;
//            while (rs.next()) {
//                ile++;
//            }
//            System.out.println(ile);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<Logowanie> logowanie = session.createQuery("from Logowanie").list();
            ile = (int) logowanie.stream().filter(log -> log.getLogin().equals(login)).count();
            if (ile == 1) {
                logowanie = logowanie.stream().filter(log -> log.getLogin().equals(login)).collect(Collectors.toList());
//                rs = stmt.executeQuery("SELECT * FROM logowanie WHERE login='" + login + "';");
//                rs.next();
//                if (haslo.equals(rs.getString("haslo"))) {
                if (logowanie.get(0).getHaslo().equals(haslo)) {
                    dane[0] = "zalogowany";
                    dane[1] = logowanie.get(0).getUprawnienia();
                    dane[2] = logowanie.get(0).getImie();
                    dane[3] = logowanie.get(0).getNazwisko();
                    dane[4] = logowanie.get(0).getDataRejestracji().toString();
                    dane[5] = logowanie.get(0).getId().toString();
                }
//                    dane[0] = "zalogowany";
//                    dane[1] = rs.getString("uprawnienia");
//                    dane[2] = rs.getString("imie");
//                    dane[3] = rs.getString("nazwisko");
//                    dane[4] = rs.getString("data_rejestracji");
//                    dane[5] = rs.getString("id");
//                }
                //dane.add(rs.getString("data_rejestracji"));
            } else {
                JOptionPane.showMessageDialog(null, "Podany login nie istnieje lub hasło jest nieprawidłowe", "Błąd ", JOptionPane.ERROR_MESSAGE);
            }
            //rozlacz(connection);
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas logowania!", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.toString());
        }
        return dane;
    }

    public List<String> wydarzenia() {
        List<String> lista = new ArrayList<>();
//
//        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM wydarzenia;");
//            System.out.println("Kwerenda");
//            while (rs.next()) {
//                lista.add(rs.getString("id") + " - " + rs.getString("nazwa"));
//                System.out.println("Wydarzenie: " + rs.getString("id") + " - " + rs.getString("nazwa"));
//            }
//            rozlacz(connection);
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "Błąd podczas pobierania danych", "Błąd ", JOptionPane.ERROR_MESSAGE);
//            System.out.println(ex.toString());
        try {
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<Wydarzenia> wydarzenia = session.createQuery("from Wydarzenia").list();
            for (Wydarzenia w : wydarzenia) {
                lista.add(w.getId() + " - " + w.getNazwa());
            }
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas pobierania danych", "Błąd ", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public List<String> info(int idWydarzenia) {
        List<String> informacje = new ArrayList<>();
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM wydarzenia WHERE id=" + idWydarzenia + ";");
//            while (rs.next()) {
//                informacje.add(rs.getString("agenda"));
//                informacje.add(rs.getString("data"));
//                informacje.add(rs.getString("godz"));
//                informacje.add(rs.getString("nazwa"));
//            }
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Wydarzenia wydarzenia = session.get(Wydarzenia.class, idWydarzenia);
            informacje.add(wydarzenia.getAgenda());
            informacje.add(wydarzenia.getData().toString());
            informacje.add(wydarzenia.getGodz().toString());
            informacje.add(wydarzenia.getNazwa());
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas pobierania danych", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.toString());
        }
        return informacje;
    }

    public void zapiszNaWydarzenie(int idUser, int idWydarzenia, String typUczestnictwa, String wyzywienie) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("INSERT INTO zapisy (id_wydarzenia,id_user,typ_uczestnictwa,wyzywienie) VALUES (" + idWydarzenia + "," + idUser + ",'" + typUczestnictwa + "','" + wyzywienie + "');");
//            rozlacz(connection);

            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Zapisy zapisy = new Zapisy();
            zapisy.setIdUser(idUser);
            zapisy.setIdWydarzenia(idWydarzenia);
            zapisy.setTypUczestnictwa(typUczestnictwa);
            zapisy.setWyzywienie(wyzywienie);
            Transaction transaction = session.beginTransaction();
            session.save(zapisy);
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie zapisano na wydarzenie", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("zapiszNaWydarzenie() - " + ex.toString() + "\nQuery: " + "INSERT INTO zapisy VALUES (''," + idWydarzenia + "," + idUser + ",'" + typUczestnictwa + "','" + wyzywienie + "');");
        }
    }

    public boolean czyZapisany(int idUser, int idWydarzenia) {
        boolean zapisany = false;
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM zapisy WHERE id_wydarzenia=" + idWydarzenia + " AND id_user=" + idUser + ";");
//            int ile = 0;
//            while (rs.next()) {
//                ile++;
//            }
//            if (ile != 1) {
//                zapisany = false;
//            } else {
//                zapisany = true;
//            }
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<Zapisy> zapisy = session.createQuery("from Zapisy").list();
            Zapisy zapis = zapisy.stream().filter(z -> z.getIdUser() == idUser).filter(z -> z.getIdWydarzenia() == idWydarzenia).findFirst().get();
            session.close();
            sessionFactory.close();
            if (zapis.isPotwierdzony()) {
                return true;
            }

        } catch (Exception ex) {
            return false;
        }
        return zapisany;
    }

    public void usunUzytkownika(String login) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("DELETE FROM logowanie WHERE login='" + login + "';");
//            JOptionPane.showMessageDialog(null, "Usunieto użytkownika", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<Logowanie> logins = session.createQuery("from Logowanie").list();
            Logowanie log = logins.stream().filter(l -> l.getLogin().equals(login)).findFirst().get();
            Transaction transaction = session.beginTransaction();
            session.delete(log);
            transaction.commit();
            session.close();
            sessionFactory.close();
            JOptionPane.showMessageDialog(null, "Usunieto użytkownika", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie usunieto użytkownika", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("usunUzytkownika - " + ex.toString());
        }
    }

    public void zmienHaslo(String login, String haslo) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("UPDATE logowanie SET haslo='" + haslo + "' WHERE login='" + login + "';");
//            JOptionPane.showMessageDialog(null, "Zresetowano hasło", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<Logowanie> logins = session.createQuery("from Logowanie").list();
            Logowanie log = logins.stream().filter(l -> l.getLogin().equals(login)).findFirst().get();
            log.setHaslo(haslo);
            Transaction transaction = session.beginTransaction();
            session.update(log);
            transaction.commit();
            session.close();
            sessionFactory.close();
            JOptionPane.showMessageDialog(null, "Zresetowano hasło", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie zmieniono hasla", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("zmienHaslo - " + ex.toString());
        }
    }

    public void dodajWydarzenie(String nazwa, String agenda, String data, String godz) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("INSERT INTO wydarzenia (nazwa,agenda,data,godz) VALUES ('" + nazwa + "','" + agenda + "','" + data + "','" + godz + "');");
//            JOptionPane.showMessageDialog(null, "Dodano wydarzenie", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Wydarzenia wydarzenia = new Wydarzenia();
            wydarzenia.setNazwa(nazwa);
            wydarzenia.setAgenda(agenda);
            java.sql.Date date = new Date(Integer.parseInt(data.substring(0, 4)) - 1900, Integer.parseInt(data.substring(5, 7)) - 1, Integer.parseInt(data.substring(8, 10)));
            wydarzenia.setData(date);
            java.sql.Time time = new Time(Integer.parseInt(godz.substring(0, 2)),Integer.parseInt(godz.substring(3, 5)),0);
            wydarzenia.setGodz(time);
            Transaction transaction = session.beginTransaction();
            session.save(wydarzenia);
            transaction.commit();
            session.close();
            sessionFactory.close();
            JOptionPane.showMessageDialog(null, "Dodano wydarzenie", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie dodano wydarzenia", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("dodajWydarzenie - " + ex.toString());
        }
    }

    public void modyfikujWydarzenie(int idWydarzenia, String nazwa, String agenda, String data, String godz) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("UPDATE wydarzenia SET nazwa='" + nazwa + "' WHERE id=" + idWydarzenia + ";");
//            stmt.execute("UPDATE wydarzenia SET agenda='" + agenda + "' WHERE id=" + idWydarzenia + ";");
//            stmt.execute("UPDATE wydarzenia SET data='" + data + "' WHERE id=" + idWydarzenia + ";");
//            stmt.execute("UPDATE wydarzenia SET godz='" + godz + "' WHERE id=" + idWydarzenia + ";");
//            JOptionPane.showMessageDialog(null, "Zmodyfikowano wydarzenie", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Wydarzenia wydarzenia = session.get(Wydarzenia.class, idWydarzenia);
            wydarzenia.setNazwa(nazwa);
            wydarzenia.setAgenda(agenda);
            java.sql.Date date = new Date(Integer.parseInt(data.substring(0, 4)) - 1900, Integer.parseInt(data.substring(5, 7)) - 1, Integer.parseInt(data.substring(8, 10)));
            wydarzenia.setData(date);
            java.sql.Time time = new Time(Integer.parseInt(godz.substring(0, 2)),Integer.parseInt(godz.substring(3, 5)),0);
            wydarzenia.setGodz(time);
            Transaction transaction = session.beginTransaction();
            session.update(wydarzenia);
            transaction.commit();
            session.close();
            sessionFactory.close();
            JOptionPane.showMessageDialog(null, "Zmodyfikowano wydarzenie", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie zmodyfikowano wydarzenia", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("modyfikujWydarzenie - " + ex.toString());
        }
    }

    public void usunWydarzenie(int idWydarzenia) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("DELETE FROM wydarzenia WHERE id=" + idWydarzenia + ";");
//            JOptionPane.showMessageDialog(null, "Usunięto wydarzenie", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Wydarzenia wydarzenia = session.get(Wydarzenia.class, idWydarzenia);
            Transaction transaction = session.beginTransaction();
            session.delete(wydarzenia);
            transaction.commit();
            session.close();
            sessionFactory.close();
            JOptionPane.showMessageDialog(null, "Usunięto wydarzenie", "Informacja ", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie usunięto wydarzenia", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("usunWydarzenie - " + ex.toString());
        }
    }

    public List<String> zapisyNaWydarzenie(int idWydarzenia) {
        List<String> lista = new ArrayList<>();
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM zapisy WHERE id_wydarzenia=" + idWydarzenia + ";");
//            while (rs.next()) {
//                String element = rs.getString("id") + ". ";
//                boolean potwierdzenie = rs.getBoolean("potwierdzony");
//                if (potwierdzenie) {
//                    element = element + "POTWIERDZONY | ";
//                } else {
//                    element = element + "NIEPOTWIERDZONY | ";
//                }
//                element = element + uzytkownik(rs.getInt("id_user")) + " | ";
//                element = element + rs.getString("typ_uczestnictwa") + " | ";
//                element = element + rs.getString("wyzywienie");
//                System.out.println(element);
//                lista.add(element);
//            }
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<Zapisy> zapisy = session.createQuery("from Zapisy").list();
            System.out.println("Zapisów ogólnie: "+zapisy.size());
            zapisy = zapisy.stream().filter(z -> z.getIdWydarzenia() == idWydarzenia).collect(Collectors.toList());
            //System.out.println("W tym na wybrane wydarzenie: "+zapisy.size());
            for (Zapisy z : zapisy) {
                String element = z.getId() + ". ";
                if (z.isPotwierdzony()) {
                    element = element + "POTWIERDZONY | ";
                } else {
                    element = element + "NIEPOTWIERDZONY | ";
                }
                element = element + z.getIdUser() + " | " + z.getTypUczestnictwa() + " | " + z.getWyzywienie();
                lista.add(element);
            }
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas pobierania danych", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("zapisyNaWydarzenie - " + ex.toString());
        }
        return lista;
    }

    private String uzytkownik(int idUser) {
        String user = "";
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM logowanie WHERE id=" + idUser + ";");
//            while (rs.next()) {
//                user = rs.getString("imie") + " " + rs.getString("nazwisko");
//            }
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Logowanie log = session.get(Logowanie.class, idUser);
            user = log.getImie() + " " + log.getNazwisko();
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return user;
    }

    public void potwierdzZapis(int idZapis) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("UPDATE zapisy SET potwierdzony=true WHERE id=" + idZapis + ";");
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Zapisy zapis = session.get(Zapisy.class, idZapis);
            zapis.setPotwierdzony(true);
            Transaction transaction = session.beginTransaction();
            session.update(zapis);
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void odrzucZapis(int idZapis) {
        try {
//            Connection connection = polacz();
//            Statement stmt = connection.createStatement();
//            stmt.execute("DELETE FROM zapisy WHERE id=" + idZapis + ";");
//            rozlacz(connection);
            SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
            Session session = sessionFactory.openSession();
            Zapisy zapis = session.get(Zapisy.class, idZapis);
            Transaction transaction = session.beginTransaction();
            session.delete(zapis);
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
