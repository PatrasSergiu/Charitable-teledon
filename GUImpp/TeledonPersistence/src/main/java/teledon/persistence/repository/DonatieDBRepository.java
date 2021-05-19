package teledon.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonatieDBRepository implements IDonatieRepo {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();
    private CazDBRepository cazRepo;
    private DonatorDBRepository donatorRepo;

    public DonatieDBRepository(Properties properties) {
        logger.info("Initializing DonatorDBRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
        donatorRepo = new DonatorDBRepository(properties);
        cazRepo = new CazDBRepository(properties);
    }

    @Override
    public Donatie findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Donatie donatie = new Donatie();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Donatii WHERE idDonatie=?")) {
            preparedStatement.setLong(1, aLong);
            ResultSet result = preparedStatement.executeQuery();
            int id = result.getInt("idDonatie");
            Donator donator = donatorRepo.findOne(result.getLong("idDonator"));
            CazCaritabil caz = cazRepo.findOne(result.getLong("idCaz"));
            int suma = result.getInt("suma");
            donatie = new Donatie(donator, caz, suma);
            donatie.setId(aLong);
        }
        catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " +e);
        }
        logger.traceExit(donatie);
        return donatie;
    }

    @Override
    public Iterable<Donatie> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Donatie> donatii = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Donatii")) {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while(result.next()) {
                    Long id = result.getLong("idDonatie");
                    Donator donator = donatorRepo.findOne(result.getLong("idDonator"));
                    CazCaritabil caz = cazRepo.findOne(result.getLong("idCaz"));
                    int suma = result.getInt("suma");
                    Donatie donatie = new Donatie(donator, caz, suma);
                    donatie.setId(id);
                    donatii.add(donatie);

                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(donatii);
        return donatii;
    }

    @Override
    public Donatie save(Donatie entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Donatii(idDonator, idCaz, suma) values (?,?,?)")){
            preparedStatement.setInt(1, entity.getDonator().getId().intValue());
            preparedStatement.setInt(2, entity.getCaz().getId().intValue());
            preparedStatement.setInt(3, entity.getSuma());
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
    public Donatie delete(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM Donatii WHERE idDonatie = ?")){
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
    public Donatie update(Donatie entity) {
        return null;
    }
}
