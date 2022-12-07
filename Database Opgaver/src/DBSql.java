import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DBSql {
    private Connection connection;
    private Statement stmt;
    private Statement stmt1;

    DBSql() {
        connection = null;
        stmt = null;
        try {
            String url = "jdbc:sqlite:C:/sqlite/studerende.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void indsaetStud(Studerende s) {
        try {
            String sql = "INSERT INTO studerende (fnavn,enavn,adresse,postnr,mobil,klasse) VALUES(" +
                    "'" + s.getFnavn() + "','" + s.getEnavn() + "','" +
                    s.getAdresse() + "','" + s.getPostnr() + "','" + s.getMobil() + "','" + s.getKlasse() + "')";

            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void indsaetFag(Fag f) {
        String sql =  "INSERT INTO fag (fagnavn) VALUES ('"+f.getFagnavn()+"')";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Studerende soegStdnr(int stdnr) {
        String sql = "SELECT * FROM studerende where stdnr =" + stdnr + ";";
        Studerende resultat = new Studerende();
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                resultat.setStdnr(rs.getInt("stdnr"));
                resultat.setFnavn(rs.getString("fnavn"));
                resultat.setEnavn(rs.getString("enavn"));
                resultat.setAdresse(rs.getString("adresse"));
                resultat.setPostnr(rs.getString("postnr"));
                resultat.setMobil(rs.getString("mobil"));
                resultat.setKlasse(rs.getString("klasse"));

            } else
                stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultat;
    }

    public Fag soegFagnr(int fagnr) {
        String sql = "SELECT * FROM fag where fagnr =" + fagnr + ";";
        Fag resultat = new Fag();
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                resultat.setFagnr(rs.getInt("fagnr"));
                resultat.setFagnavn(rs.getString("fagnavn"));

            } else
                stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultat;
    }

    public void tilmeldFag(int studnr, int fagnr) {
        String sql = "INSERT INTO studfag(studnr,fagnr) VALUES(" + studnr + "," + fagnr + ");";
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Studerende soegAltStdnr(int stdnr) {
        String sql = "SELECT studerende.stdnr, studerende.fnavn, studerende.enavn, studerende.adresse," +
                " studerende.postnr,studerende.mobil,studerende.klasse, fag.fagnr, fag.fagnavn" +
                " FROM studerende " +
                " LEFT OUTER JOIN studfag ON studerende.stdnr = studfag.studnr " +
                " LEFT OUTER JOIN fag ON studfag.fagnr = fag.fagnr" +
                " WHERE studerende.stdnr=" + stdnr + ";";
        Studerende resultat = new Studerende();
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int antal = 0;
            while (rs.next()) {
                Fag f1 = new Fag();
                if (antal <1) {
                    resultat.setStdnr(rs.getInt("stdnr"));
                    resultat.setFnavn(rs.getString("fnavn"));
                    resultat.setEnavn(rs.getString("enavn"));
                    resultat.setAdresse(rs.getString("adresse"));
                    resultat.setPostnr(rs.getString("postnr"));
                    resultat.setMobil(rs.getString("mobil"));
                    resultat.setKlasse(rs.getString("klasse"));
                    antal++;
                }
                f1.setFagnr(rs.getInt("fagnr"));
                f1.setFagnavn(rs.getString("fagnavn"));
                resultat.tilmeldFag(f1);
            }
                stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultat;
    }

    public ArrayList<Studerende> soegefternavn(String enavn) {

        ArrayList<Studerende> a1 = new ArrayList();
        String sql = "SELECT * from studerende WHERE enavn='" + enavn + "'";
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Studerende resultat = new Studerende();
                resultat.setStdnr(rs.getInt("stdnr"));
                resultat.setFnavn(rs.getString("fnavn"));
                resultat.setEnavn(rs.getString("enavn"));
                resultat.setAdresse(rs.getString("adresse"));
                resultat.setPostnr(rs.getString("postnr"));
                resultat.setMobil(rs.getString("mobil"));
                resultat.setKlasse(rs.getString("klasse"));
                a1.add(resultat);
            } else
                stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return a1;
    }
    public ArrayList<Studerende> alleoplysninger() {
        String sql2 = "SELECT * from studerende";
        Studerende resultat = new Studerende();
        ArrayList<Studerende> a1 = new ArrayList<Studerende>();
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql2);
            while (rs.next()) {
                resultat = new Studerende();
                int studnr = rs.getInt("stdnr");
                resultat.setStdnr(studnr);
                resultat.setFnavn(rs.getString("fnavn"));
                resultat.setEnavn(rs.getString("enavn"));
                resultat.setAdresse(rs.getString("adresse"));
                resultat.setPostnr(rs.getString("postnr"));
                resultat.setMobil(rs.getString("mobil"));
                resultat.setKlasse(rs.getString("klasse"));

                String sql = "SELECT fag.fagnr, fag.fagnavn" +
                        " FROM studerende " +
                        " INNER JOIN studfag ON studerende.stdnr = studfag.studnr " +
                        " INNER JOIN fag ON studfag.fagnr = fag.fagnr" +
                        " WHERE studerende.stdnr =" + studnr;
                Statement stmt2 = connection.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql);
                while (rs2.next()) {
                    Fag f1 = new Fag();
                    f1.setFagnr(rs2.getInt("fagnr"));
                    f1.setFagnavn(rs2.getString("fagnavn"));
                    resultat.tilmeldFag(f1);
                }
                a1.add(resultat);
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return a1;
    }
    public ArrayList<Studerende> allestudmedfag(int fagnr) {
        ArrayList<Integer> ilist = new ArrayList();
        ArrayList<String> fnlist = new ArrayList();
        ArrayList<String> enlist = new ArrayList();
        ArrayList<String> alist = new ArrayList();
        ArrayList<String> plist = new ArrayList();
        ArrayList<String> mlist = new ArrayList();
        ArrayList<String> klist = new ArrayList();
        ArrayList<Studerende> a1 = new ArrayList();

        String sql = "SELECT studerende.stdnr, studerende.fnavn, studerende.enavn, studerende.adresse," +
                " studerende.postnr,studerende.mobil,studerende.mobil" +
                " FROM studerende" +
                " INNER JOIN studfag ON studerende.stdnr = studfag.studnr" +
                " INNER JOIN fag ON studfag.fagnr = fag.fagnr" +
                " WHERE fag.fagnr=" + fagnr;
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i += 7) {
                    ilist.add(rs.getInt(i));
                    fnlist.add(rs.getString(i + 1));
                    enlist.add(rs.getString(i + 2));
                    alist.add(rs.getString(i + 3));
                    plist.add(rs.getString(i + 4));
                    mlist.add(rs.getString(i + 5));
                    klist.add(rs.getString(i + 6));
                }
            }
            for (int j = 0; j < ilist.size(); j++) {
                Studerende s2 = new Studerende(ilist.get(j), fnlist.get(j), enlist.get(j), alist.get(j), plist.get(j), mlist.get(j), klist.get(j));
                a1.add(s2);
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return a1;
    }
    public void updateStudKlasse(int stdnr,String nyKlasse) {
        String sql = "UPDATE studerende set klasse="+nyKlasse+" WHERE stdnr ="+stdnr;
        try {
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void sletStuderende(int stdnr)
    {
        String sql = "DELETE From studfag where studnr="+stdnr;
        try {
            Statement stmt = connection.createStatement();
                stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql2 = "DELETE From studerende where stdnr ="+stdnr;
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql2);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}