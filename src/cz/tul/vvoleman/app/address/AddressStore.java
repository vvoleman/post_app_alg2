package cz.tul.vvoleman.app.address;

import java.sql.*;

public class AddressStore {

    //TODO: Cache mezi databází a aplikací

    private static final String dbHostname = "localhost";
    private static final int dbPort = 3306;
    private static final String dbName = "adresy";
    private static final String dbUsername = "root";
    private static final String dbPassword = "";
    private static final String dbConnector = "mariadb";

    private Connection db = null;
    private static AddressStore instance;

    /**
     * Private constructor
     * @throws SQLException when connection can't be established
     */
    private AddressStore() throws SQLException {
        setupConnection();
    }

    /**
     * Setups connection with DB
     * @throws SQLException when connection can't be established
     */
    private void setupConnection() throws SQLException {
        try {
            String link = String.format("jdbc:%s://%s:%d/%s?user=%s&password=%s", dbConnector, dbHostname, dbPort, dbName, dbUsername, dbPassword);
            db = DriverManager.getConnection(link);
        }catch (SQLException e){
            throw new SQLException("Couldn't connect to database");
        }
    }

    /**
     * Returns Address by ID of building
     * @param id ID of building
     * @return null | Address
     * @throws SQLException when something goes wrong
     */
    public Address getAddressById(int id) throws SQLException {
        //id,street,cisladomu,psc,obec,okres
        //Where
        String query = getAddressQuery() + " WHERE d.kod = ? LIMIT 1";

        PreparedStatement ps = db.prepareStatement(query);
        ps.setInt(1,id);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return getAddressFromResult(rs);
        }
        return null;
    }

    /**
     * Returns address from passed parameters<br>
     * <b>Note:</b> It is required to pass either street name or municipality!
     * @param name streetName or municipalityName
     * @param houseNumber houseNumber
     * @param additionalNumber additionalNumber
     * @param psc psc
     * @return Address
     * @throws SQLException when something goes wrong
     */
    public Address getAddressByValues(String name, int houseNumber,int additionalNumber,int psc) throws SQLException {
        //Bořek stavitel staví query
        StringBuilder query = new StringBuilder(getAddressQuery());
        query.append(" WHERE cislo_domu = ? AND psc = ? AND (nazev_ulice = ? OR ob.nazev = ?)");
        if(additionalNumber != 0){
            query.append(" AND cislo_orientacni = ?");
        }

        //Nastavím parametry
        PreparedStatement ps = db.prepareStatement(query.toString());
        ps.setInt(1,houseNumber);
        ps.setInt(2,psc);
        ps.setString(3,name);
        ps.setString(4,name);
        if(additionalNumber != 0){
            ps.setInt(5,additionalNumber);
        }

        //Získám ResultSet, vrátím getAddressFromResult nebo null při prázdném výsledku
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return getAddressFromResult(rs);
        }
        return null;
    }

    /**
     * Returns basic query for address
     * @return query
     */
    private String getAddressQuery(){
        return "SELECT d.kod as id, nazev_ulice as street_name, cislo_domu as house_number, " +
                "cislo_orientacni as additional_number,co.psc as psc, ob.nazev as municipality_name, " +
                "ok.nazev as district_name FROM domy d " +
                "JOIN casti_obci co ON co.kod = d.kod_casti_obce JOIN obce ob ON ob.kod = co.kod_obce JOIN okresy ok ON ok.kod = ob.kod_okresu";
    }

    /**
     * Creates Address instance with ResultSet
     * @param rs ResultSet
     * @return Address
     * @throws SQLException when something goes wrong
     */
    private Address getAddressFromResult(ResultSet rs) throws SQLException {
        return new Address(
                rs.getInt("id"),
                rs.getString("street_name"),
                rs.getInt("house_number"),
                rs.getInt("additional_number"),
                rs.getInt("psc"),
                rs.getString("municipality_name"),
                rs.getString("district_name")
        );
    }

    /* -- Static */

    /**
     * Returns instance of AddressStore (Singleton)
     * @return instance of AddressStore
     * @throws SQLException when something goes wrong
     */
    public static AddressStore getInstance() throws SQLException {
        if(instance == null){
            instance = new AddressStore();
        }
        return instance;
    }
}