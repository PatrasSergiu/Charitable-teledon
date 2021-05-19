package teledon.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teledon.model.Donator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonatorDBRepository implements IDonatorRepo {

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public DonatorDBRepository(Properties properties) {
        logger.info("Initializing DonatorDBRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Donator findByNume(String nume, String prenume) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Donator donator = new Donator();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Donatori WHERE nume=? AND prenume=?")) {
            preparedStatement.setString(1, nume);
            preparedStatement.setString(2, prenume);
            ResultSet result = preparedStatement.executeQuery();
            Long id = result.getLong("idDonator");
            String adresa = result.getString("adresa");
            String telefon = result.getString("telefon");
            String n = result.getString("nume");
            String p = result.getString("prenume");
            donator = new Donator(adresa, telefon, nume, prenume);
            donator.setId(id);
        }
        catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " +e);
        }
        logger.traceExit(donator);
        if(donator.getId() == null)
            return null;
        return donator;
    }

    @Override
    public Donator save(Donator entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Donatori(adresa, telefon, nume, prenume) values (?,?,?,?)")){
            preparedStatement.setString(1, entity.getAdresa());
            preparedStatement.setString(2, entity.getTelefon());
            preparedStatement.setString(3, entity.getNume());
            preparedStatement.setString(4, entity.getPrenume());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);
        }
        catch(SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return entity;

    }

    @Override
    public Donator update(Donator entity) {
        return null;
    }

    @Override
    public Donator findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Donator donator = new Donator();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Donatori WHERE idDonator=?")) {
                preparedStatement.setLong(1, aLong);
                ResultSet result = preparedStatement.executeQuery();
                String adresa = result.getString("adresa");
                String telefon = result.getString("telefon");
                String nume = result.getString("nume");
                String prenume = result.getString("prenume");
                donator = new Donator(adresa, telefon, nume, prenume);
                donator.setId(aLong);
        }
        catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " +e);
        }
        logger.traceExit(donator);
        return donator;
    }

    @Override
    public Donator delete(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM Donatori WHERE idDonator = ?")){
            preparedStatement.setInt(1, aLong.intValue());
            int result = preparedStatement.executeUpdate();
            logger.trace("Deleted {} instances", result);
        }
        catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Iterable<Donator> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Donator> donatori = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Donatori")) {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while(result.next()) {
                    Long id = result.getLong("idDonator");
                    String adresa = result.getString("adresa");
                    String telefon = result.getString("telefon");
                    String nume = result.getString("nume");
                    String prenume = result.getString("prenume");
                    Donator donator = new Donator(adresa, telefon, nume, prenume);
                    donator.setId(id);
                    donatori.add(donator);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(donatori);
        return donatori;
    }
}
