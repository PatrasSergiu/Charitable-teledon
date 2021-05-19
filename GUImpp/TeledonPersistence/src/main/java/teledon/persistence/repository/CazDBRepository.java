package teledon.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teledon.model.CazCaritabil;
import teledon.model.Donator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
public class CazDBRepository implements ICazRepo {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public CazDBRepository(Properties properties) {
        logger.info("Initializing CazDBRepository with properties: {} ", properties);
        //dbUtils = new JdbcUtils(properties);
        try {
            properties.load(new FileInputStream("C:\\Users\\Patras Sergiu\\Desktop\\GUImpp\\l3\\TeledonServer\\src\\main\\resources\\teledonServer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Iterable<Donator> findAllDonatori(CazCaritabil caz) {
        return null;
    }

    @Override
    public CazCaritabil findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        CazCaritabil caz = new CazCaritabil();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Cazuri WHERE idCaz=?")) {
            preparedStatement.setLong(1, aLong);
            ResultSet result = preparedStatement.executeQuery();
            int id = result.getInt("idCaz");
            String descriere = result.getString("descriere");
            int suma = result.getInt("sumaAdunata");
            caz = new CazCaritabil(descriere, suma);
            caz.setId(aLong);

        }
        catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " +e);
        }
        logger.traceExit(caz);
        return caz;
    }

    @Override
    public Iterable<CazCaritabil> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<CazCaritabil> cazuri = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Cazuri")) {
            try(ResultSet result = preparedStatement.executeQuery()) {
                System.out.println("prepare");
                while(result.next()) {
                    System.out.println("result");
                    Long id = result.getLong("idCaz");
                    String descriere = result.getString("descriere");
                    int sumaAdunata = result.getInt("sumaAdunata");
                    CazCaritabil caz = new CazCaritabil(descriere, sumaAdunata);
                    caz.setId(id);
                    System.out.println(caz);
                    cazuri.add(caz);
                }
            }
        } catch(SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cazuri);
        return cazuri;
    }

    @Override
    public CazCaritabil save(CazCaritabil entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Cazuri(descriere, sumaAdunata) values (?,?)")){
            preparedStatement.setString(1, entity.getNume());
            preparedStatement.setInt(2, entity.getSumaAdunata());
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
    public CazCaritabil delete(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM Cazuri WHERE idCaz = ?")){
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
    public CazCaritabil update(CazCaritabil entity) {
        System.out.println("Repo " + entity);
            logger.traceEntry();
            Connection con = dbUtils.getConnection();
            Long id = entity.getId();
            if(findOne(id) == null) {
                throw new IllegalArgumentException("This id doesn't exist..");
            }

            String query = "UPDATE Cazuri SET sumaAdunata = ? WHERE idCaz = ?";
            try(PreparedStatement preparedStatement = con.prepareStatement(query)){
                preparedStatement.setInt(1, entity.getSumaAdunata());
                preparedStatement.setLong(2, entity.getId());
                int result = preparedStatement.executeUpdate();
                logger.trace("Updated {} instances", result);
            }
            catch(SQLException e) {
                logger.error(e);
                System.err.println("Error DB " +  e);
            }

        return findOne(id);
    }
}
