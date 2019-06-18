package com.mycompany.pwsw_lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class FXMLController implements Initializable {

    public DatabaseService dbService;
    public int idUser;
    @FXML
    public Pane panelLogowanie;
    public Pane panelUzytkownika;
    public Pane panelRejestracja;
    public Pane panelAdministrator;
    public TextField imie;
    public TextField nazwisko;
    public TextField login;
    public TextField haslo;
    public TextField haslo2;
    public TextField email;
    public Label witajLabel;
    public TextField loginLog;
    public TextField hasloLog;
    public Label userLabel1;
    public Label userLabel2;
    public ComboBox combo1;
    public ComboBox combo2;
    public ComboBox combo3;
    public Label dataGodzLabel;
    public TextArea agendaArea;
    public Button zapiszNaWydButton;
    public Label czyZapisLabel;
    public TextField admImie;
    public TextField admNazwisko;
    public TextField admLogin;
    public TextField admHaslo;
    public TextField admMail;
    public TextField admUsunLogin;
    public TextField admResetLogin;
    public TextField admResetHaslo;
    public TextField admDWNazwa;
    public TextArea admDWAgenda;
    public TextField admDWData;
    public TextField admDWGodz;
    public TextField admMWId;
    public TextArea admMWAgenda;
    public TextField admMWNazwa;
    public TextField admMWData;
    public TextField admMWGodz;
    public TextField admUWId;
    public ListView<String> zapisy;
    public ComboBox combo4;

    @FXML
    private void potwierdzZapis(ActionEvent event) {
        try {
            int idZapisu = Integer.parseInt(zapisy.getSelectionModel().getSelectedItem().substring(0, zapisy.getSelectionModel().getSelectedItem().indexOf(".")));
            dbService.potwierdzZapis(idZapisu);
            String s = combo4.getSelectionModel().getSelectedItem().toString();
            int id = Integer.parseInt(s.replace(" ", "").substring(0, s.indexOf("-") - 1));
            List<String> info = dbService.zapisyNaWydarzenie(id);
            zapisy.setItems(toObsList(info));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void odrzucZapis(ActionEvent event) {
        try {
            int idZapisu = Integer.parseInt(zapisy.getSelectionModel().getSelectedItem().substring(0, zapisy.getSelectionModel().getSelectedItem().indexOf(".")));
            dbService.odrzucZapis(idZapisu);
            String s = combo4.getSelectionModel().getSelectedItem().toString();
            int id = Integer.parseInt(s.replace(" ", "").substring(0, s.indexOf("-") - 1));
            List<String> info = dbService.zapisyNaWydarzenie(id);
            zapisy.setItems(toObsList(info));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void combo4Wybor(ActionEvent event) {
        try {
            String s = combo4.getSelectionModel().getSelectedItem().toString();
            int id = Integer.parseInt(s.replace(" ", "").substring(0, s.indexOf("-") - 1));
            List<String> info = dbService.zapisyNaWydarzenie(id);
            zapisy.setItems(toObsList(info));
            System.out.println("Wybrano id: " + id);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void admUWUsun(ActionEvent event) {
        dbService.usunWydarzenie(Integer.parseInt(admUWId.getText()));
    }

    @FXML
    private void admMWMod(ActionEvent event) {
        dbService.modyfikujWydarzenie(Integer.parseInt(admMWId.getText()), admMWNazwa.getText(), admMWAgenda.getText(), admMWData.getText(), admMWGodz.getText());
    }

    @FXML
    private void admPokazWyd(ActionEvent event) {
        try {
            List<String> info = dbService.info(Integer.parseInt(admMWId.getText()));
            admMWNazwa.setText(info.get(3));
            admMWAgenda.setText(info.get(0));
            admMWData.setText(info.get(1));
            admMWGodz.setText(info.get(2));
        } catch (Exception ex) {
            admMWNazwa.setText("");
            admMWAgenda.setText("");
            admMWData.setText("");
            admMWGodz.setText("");
            JOptionPane.showMessageDialog(null, "Nie ma takiego wydarzenia", "Błąd ", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void admDWDodaj(ActionEvent event) {
        dbService.dodajWydarzenie(admDWNazwa.getText(), admDWAgenda.getText(), admDWData.getText(), admDWGodz.getText());
        combo1.setItems(toObsList(dbService.wydarzenia()));
        combo4.setItems(toObsList(dbService.wydarzenia()));
    }

    @FXML
    private void admResetujHaslo(ActionEvent event) {
        dbService.zmienHaslo(admResetLogin.getText(), admResetHaslo.getText());
    }

    @FXML
    private void admUsunUzytkownika(ActionEvent event) {
        dbService.usunUzytkownika(admUsunLogin.getText());
    }

    @FXML
    private void admDodajUser(ActionEvent event) {
        try {
            dbService.rejestruj(admImie.getText(), admNazwisko.getText(), admLogin.getText(), admHaslo.getText(), admMail.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie dodano użytkownika!", "Błąd ", JOptionPane.ERROR_MESSAGE);
            System.out.println("admDodajUser() - " + ex.toString());
        }
    }

    @FXML
    private void zapiszNaWydarzenie(ActionEvent event) {

        try {
            System.out.println(combo2.getSelectionModel().getSelectedItem().toString());
            System.out.println(combo3.getSelectionModel().getSelectedItem().toString());
            int opcja1 = combo2.getSelectionModel().getSelectedIndex();
            int opcja2 = combo3.getSelectionModel().getSelectedIndex();
            String uczestnictwo = "";
            switch (opcja1) {
                case 0:
                    uczestnictwo = "sluchacz";
                    break;
                case 1:
                    uczestnictwo = "autor";
                    break;
                case 2:
                    uczestnictwo = "sponsor";
                    break;
                case 3:
                    uczestnictwo = "organizator";
                    break;
            }
            String wyzywienie = "";
            switch (opcja2) {
                case 0:
                    wyzywienie = "bez preferencji";
                    break;
                case 1:
                    wyzywienie = "wegetarianskie";
                    break;
                case 2:
                    wyzywienie = "bez glutenu";
                    break;
            }
            String s = combo1.getSelectionModel().getSelectedItem().toString();
            int id = Integer.parseInt(s.replace(" ", "").substring(0, s.indexOf("-") - 1));
            dbService.zapiszNaWydarzenie(idUser, id, uczestnictwo, wyzywienie);
            if (dbService.czyZapisany(idUser, id)) {
                zapiszNaWydButton.setDisable(true);
                combo2.setDisable(true);
                combo3.setDisable(true);
                czyZapisLabel.setVisible(true);
            } else {
                zapiszNaWydButton.setDisable(false);
                combo2.setDisable(false);
                combo3.setDisable(false);
                czyZapisLabel.setVisible(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nie wybrałeś wymaganych opcji!", "Błąd ", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void comboAction(ActionEvent event) {
        //System.out.println(combo1.getSelectionModel().getSelectedItem());

        //System.out.println("id: " + s.replace(" ", "").substring(0, s.indexOf("-") - 1));
        try {
            String s = combo1.getSelectionModel().getSelectedItem().toString();
            int id = Integer.parseInt(s.replace(" ", "").substring(0, s.indexOf("-") - 1));
            List<String> info = dbService.info(id);
            String data, godz, agenda;
            data = info.get(1);
            godz = info.get(2);
            agenda = info.get(0);
            dataGodzLabel.setText("Data i godzina: " + data + " " + godz);
            agendaArea.setText(agenda);
            System.out.println("Zapisany: " + dbService.czyZapisany(idUser, id));
            if (dbService.czyZapisany(idUser, id)) {
                zapiszNaWydButton.setDisable(true);
                combo2.setDisable(true);
                combo3.setDisable(true);
                czyZapisLabel.setVisible(true);
            } else {
                zapiszNaWydButton.setDisable(false);
                combo2.setDisable(false);
                combo3.setDisable(false);
                czyZapisLabel.setVisible(false);
            }
        } catch (Exception ex) {
            dataGodzLabel.setText("Data i godzina: ");
            agendaArea.setText("");
        }
    }

    @FXML
    private void rejestracja1(ActionEvent event) {
        panelLogowanie.setVisible(false);
        panelRejestracja.setVisible(true);
    }

    @FXML
    private void zarejestruj(ActionEvent event) {
        if (haslo.getText().equals(haslo2.getText())) {
            dbService.rejestruj(imie.getText(), nazwisko.getText(), login.getText(), haslo.getText(), email.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Hasła się nie zgadzają!", "Błąd ", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void doLogowania(ActionEvent event) {
        panelRejestracja.setVisible(false);
        panelLogowanie.setVisible(true);
    }

    @FXML
    private void zaloguj(ActionEvent event) {
//        SessionFactory sessionFactory = DatabaseConfigurator.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        Logowanie logowanie = session.get(Logowanie.class, 1);
//        Wydarzenia wydarzenia=session.get(Wydarzenia.class, 1);
//        Zapisy zapisy=session.get(Zapisy.class,1);
//        System.out.println(logowanie.toString());
//        System.out.println(wydarzenia.toString());
//        System.out.println(zapisy.toString());
//        session.close();
//        sessionFactory.close();
        try {
            String[] dane = dbService.zaloguj(loginLog.getText(), hasloLog.getText());
            if (dane[0].equals("zalogowany")) {
                if (dane[1].equals("user")) {
                    panelLogowanie.setVisible(false);
                    panelUzytkownika.setVisible(true);
                    witajLabel.setText("Witaj " + dane[2]);
                    userLabel1.setText("Zarejestrowano: " + dane[4]);
                    userLabel2.setText("Imię i nazwisko: " + dane[2] + " " + dane[3]);
                    idUser = Integer.parseInt(dane[5]);
                }
                if (dane[1].equals("admin")) {
                    panelLogowanie.setVisible(false);
                    panelAdministrator.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Login lub haslo nieprawidłowe!", "Błąd ", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas logowania!", "Błąd ", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void wyloguj1(ActionEvent event) {
        panelUzytkownika.setVisible(false);
        panelLogowanie.setVisible(true);
    }

    @FXML
    private void wyloguj2(ActionEvent event) {
        panelAdministrator.setVisible(false);
        panelLogowanie.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dbService = new DatabaseService();
        ObservableList<String> values = FXCollections.observableArrayList("Słuchacz", "Autor", "Sponsor", "Organizator");
        combo2.setItems(values);
        values = FXCollections.observableArrayList("Bez preferencji", "Wegetariańskie", "Bez glutenu");
        combo3.setItems(values);
        values = toObsList(dbService.wydarzenia());
        combo1.setItems(values);
        combo4.setItems(values);
    }

    public ObservableList<String> toObsList(List<String> lista) {
        ObservableList<String> obsList = FXCollections.observableArrayList();
        for (String string : lista) {
            obsList.add(string);
        }
        return obsList;
    }
}
