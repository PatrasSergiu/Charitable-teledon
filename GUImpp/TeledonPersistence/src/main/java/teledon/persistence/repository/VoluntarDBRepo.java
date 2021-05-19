package teledon.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teledon.model.Voluntar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VoluntarDBRepo implements IVoluntarRepo {

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public VoluntarDBRepo(Properties properties) {
        logger.info("Initializing DonatorDBRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Voluntar findByUsername(String username) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Voluntar voluntar = new Voluntar();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Voluntari WHERE username=?")) {
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            Long id = result.getLong("id");
            String user = result.getString("username");
            String pass = result.getString("password");
            voluntar = new Voluntar(username, pass);
            voluntar.setId(id);
        }
        catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " +e);
        }
        logger.traceExit(voluntar);
        return voluntar;
    }

    @Override
    public Voluntar findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Voluntar voluntar = new Voluntar();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Voluntari WHERE id=?")) {
            preparedStatement.setLong(1, aLong);
            ResultSet result = preparedStatement.executeQuery();
            Long id = result.getLong("id");
            String username = result.getString("username");
            String password = result.getString("password");
            voluntar = new Voluntar(username, password);
            voluntar.setId(aLong);

        }
        catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " +e);
        }
        logger.traceExit(voluntar);
        return voluntar;
    }

    @Override
    public Iterable<Voluntar> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Voluntar> voluntari = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Voluntari")) {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while(result.next()) {
                    Long id = result.getLong("id");
                    String username = result.getString("username");
                    String password = result.getString("paswword");
                    Voluntar voluntar = new Voluntar(username, password);
                    voluntar.setId(id);
                    voluntari.add(voluntar);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(voluntari);
        return voluntari;
    }

    @Override
    public Voluntar save(Voluntar entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Voluntari(username, password) values (?,?)")){
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
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
    public Voluntar delete(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM Voluntari WHERE id = ?")){
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
    public Voluntar update(Voluntar entity) {
        return null;
    }
}
