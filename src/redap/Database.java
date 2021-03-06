/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Vytváří instanci databáze, poskytuje metody obsluhující dotazy nad databází 
 * 
 * @author Fragolka
 */
public class Database {

    private java.sql.Connection con;
    private static Database db = null;

    private Database() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DBConnection dbconn = new DBConnection();
        con = dbconn.getConnection();
    }

    /*
     * Vrací instanci vytvořené databáze
     */
    public static Database getInstance() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    /*
     *  Přidá do databáze nového klienta
     * 
     * @return: vrací automaticky vygenerované ID nového klienta
     */
    public int insertKlient(Klient klient) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO klient (id,jmeno,ulice,psc,dic,ico,sleva) VALUES (DEFAULT,?,?,?,?,?,?) RETURNING id");
        ps.setString(1, klient.getJmeno());
        ps.setString(2, klient.getUlice());
        ps.setString(3, klient.getPSC());
        ps.setString(4, klient.getDIC());
        ps.setString(5, klient.getICO());
        ps.setDouble(6, klient.getSleva());
        ResultSet rs = ps.executeQuery();
        int i = -1;
        while (rs.next()) {
            i = rs.getInt("id");
        }
        return i;

    }

    /*
     *  Mění údaje o klientovi v databází 
     */
    public void updateKlient(Klient klient) throws Exception {
        PreparedStatement ps = con.prepareStatement("UPDATE klient SET jmeno = ? WHERE id= ?");
        ps.setString(1, klient.getJmeno());
        ps.setInt(2, klient.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE klient SET ulice = ? WHERE id= ?");
        ps.setString(1, klient.getUlice());
        ps.setInt(2, klient.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE klient SET psc = ? WHERE id= ?");
        ps.setString(1, klient.getPSC());
        ps.setInt(2, klient.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE klient SET dic = ? WHERE id= ?");
        ps.setString(1, klient.getDIC());
        ps.setInt(2, klient.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE klient SET ico = ? WHERE id= ?");
        ps.setString(1, klient.getICO());
        ps.setInt(2, klient.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE klient SET sleva = ? WHERE id= ?");
        ps.setDouble(1, klient.getSleva());
        ps.setInt(2, klient.getId());
        ps.executeUpdate();
    }

    /*
     *  Vymaže klienta z databáze
     */
    public void deleteKlient(Klient klient) throws Exception {
        PreparedStatement ps = con.prepareStatement("DELETE FROM klient WHERE id= ?");
        ps.setInt(1, klient.getId());
        ps.executeUpdate();
    }

    /*
     *  @return: Vrací list všech klientů uložených v databázi
     */    
    public List<Klient> getAllKlient() throws Exception {
        List<Klient> list = new ArrayList<Klient>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM klient");
        while (rs.next()) {
            list.add(loadKlient(rs));
        }
        return list;
    }

    /*
     *  @return: Vrací klienta s daným ID
     */
    public Klient getKlient(int id) throws Exception {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM klient WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Klient k = null;
        while (rs.next()) {
            k = loadKlient(rs);
        }
        return k;
    }

    /*
     * přijímí jeden řádek z výsledné množiny dotazy a vytváří z něj instanci klienta, kterou vrací
     * 
     * @return: vrací klienta z řádky výsledné množiny
     */
    protected Klient loadKlient(final ResultSet rs) throws Exception {
        Klient k = new Klient(rs.getString("jmeno"),
                rs.getString("ulice"),
                rs.getString("psc"),
                rs.getString("dic"),
                rs.getString("ico"),
                rs.getDouble("sleva"));
        k.setId(rs.getInt("id"));
        return k;
    }

    /*
     *  Uloží vytvořenou položku na fakturu v databázi
     */
    public void insertPolozka(Polozka polozka, Faktura faktura) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO polozkynafakture (polozkaid, fakturaid) VALUES (?,?)");
        ps.setInt(1, polozka.getId());
        ps.setInt(2, faktura.getId());
        ps.executeUpdate();
    }

    /*
     *  Přidá do databáze novou položku
     * 
     * @return: vrací automaticky vygenerované ID nové položky
     */
    public int insertPolozka(Polozka polozka) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO polozka (id,popis,cena,pocet) VALUES (default,?,?,?) returning id");
        ps.setString(1, polozka.getPopis());
        ps.setDouble(2, polozka.getJCena());
        ps.setInt(3, polozka.getPocet());
        ResultSet rs = ps.executeQuery();
        int i = -1;
        while (rs.next()) {
            i = rs.getInt("id");
        }
        return i;
    }
    
    /*
     *  Upraví položku v databázi
     */
    public void updatePolozka(Polozka polozka) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE polozka SET pocet = ? WHERE id= ?");
        ps.setInt(1, polozka.getPocet());
        ps.setInt(2, polozka.getId());
        ps.executeUpdate();
        
        ps = con.prepareStatement("UPDATE polozka SET cena = ? WHERE id= ?");
        ps.setDouble(1, polozka.getJCena());
        ps.setInt(2, polozka.getId());
        ps.executeUpdate();
        
        ps = con.prepareStatement("UPDATE polozka SET popis = ? WHERE id= ?");
        ps.setString(1, polozka.getPopis());
        ps.setInt(2, polozka.getId());
        ps.executeUpdate();        
    }

    /*
     *  Odstraní položku z databáze
     */
    public void deletePolozka(Polozka polozka) throws Exception {
        PreparedStatement ps = con.prepareStatement("DELETE FROM polozkynafakture WHERE polozkaid= ?");
        ps.setInt(1, polozka.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("DELETE FROM polozka WHERE id= ?");
        ps.setInt(1, polozka.getId());
        ps.executeUpdate();

    }

    /*
     *  @return: Vrací list všech položek na faktuře přijímané jako parametr
     */ 
    public List<Polozka> getAllPolozka(Faktura faktura) throws Exception {
        List<Polozka> list = new ArrayList<Polozka>();
        PreparedStatement ps = con.prepareStatement("SELECT p.popis, p.cena, p.pocet, p.id FROM polozka p "
                + "INNER JOIN polozkynafakture pf ON pf.polozkaid = p.id AND pf.fakturaid = ?");
        ps.setInt(1, faktura.getId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(loadPolozka(rs));
        }
        return list;
    }

    /*
     * přijímá jeden řádek z výsledné množiny dotazy a vytváří z něj instanci položky, kterou vrací
     * 
     * @return: vrací položku z řádky výsledné množiny
     */
    protected Polozka loadPolozka(final ResultSet rs) throws Exception {
        Polozka p = new Polozka(rs.getString("popis"),
                rs.getDouble("cena"),
                rs.getInt("pocet"));
        p.setId(rs.getInt("id"));
        return p;
    }

    /*
     *  Přidá do databáze novou fakturu
     * 
     * @return: vrací automaticky vygenerované ID nové faktury
     */
    public int insertFaktura(Faktura faktura) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO faktura (id,klientid,datumplneni,datumvystaveni) VALUES (default,?,?,?) returning id");
        ps.setInt(1, faktura.getKlient().getId());
        ps.setDate(2, new java.sql.Date(faktura.getDatumPlneni().getTime()));
        ps.setDate(3, new java.sql.Date(faktura.getVystaveniFaktury().getTime()));
        ResultSet rs = ps.executeQuery();
        int i = -1;
        while (rs.next()) {
            i = rs.getInt("id");
        }
        return i;
    }

    /*
     * Upraví danou fakturu
     */
    public void updateFaktura(Faktura faktura) throws Exception {
        PreparedStatement ps = con.prepareStatement("UPDATE faktura SET klientid = ? WHERE id= ?");
        ps.setInt(1, faktura.getKlient().getId());
        ps.setInt(2, faktura.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE faktura SET datumplneni = ? WHERE id= ?");
        ps.setDate(1, new java.sql.Date(faktura.getDatumPlneni().getTime()));
        ps.setInt(2, faktura.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("DELETE FROM polozka WHERE id in (SELECT polozkaid FROM polozkynafakture WHERE fakturaid = ?)");
        ps.setInt(1, faktura.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("DELETE FROM polozkynafakture WHERE fakturaid= ?");
        ps.setInt(1, faktura.getId());
        ps.executeUpdate();

        for (Polozka p : faktura.getPolozky()) {
            insertPolozka(p, faktura);
        }
    }

    /*
     * Odstraní datnou fakturu z databázi včetně položek, které na ní jsou uloženy
     */
    public void deleteFaktura(Faktura faktura) throws Exception {
        PreparedStatement ps = con.prepareStatement("DELETE FROM polozka WHERE id in (SELECT polozkaid FROM polozkynafakture WHERE fakturaid = ?)");
        ps.setInt(1, faktura.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("DELETE FROM polozkynafakture WHERE fakturaid= ?");
        ps.setInt(1, faktura.getId());
        ps.executeUpdate();

        ps = con.prepareStatement("DELETE FROM faktura WHERE id= ?");
        ps.setInt(1, faktura.getId());
        ps.executeUpdate();
    }

    /*
     *  @return: Vrací list všech faktur v databázi
     */ 
    public List<Faktura> getAllFaktura() throws Exception {
        List<Faktura> list = new ArrayList<Faktura>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM faktura");
        while (rs.next()) {
            list.add(loadFaktura(rs));
        }
        return list;
    }

    /*
     * přijímá jeden řádek z výsledné množiny dotazy a vytváří z něj instanci fakturu, kterou vrací
     * 
     * @return: vrací fakturu z řádky výsledné množiny
     */
    protected Faktura loadFaktura(final ResultSet rs) throws Exception {
        Faktura f = new Faktura();
        f.setDatumPlneni(rs.getDate("datumplneni"));
        f.setId(rs.getInt("id"));
        f.setKlient(getKlient(rs.getInt("klientid")));
        List pol = getAllPolozka(f);
        f.setPolozky(pol);
        return f;
    }
}
