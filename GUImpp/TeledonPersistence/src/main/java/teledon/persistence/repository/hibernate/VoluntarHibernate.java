package teledon.persistence.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import teledon.model.Voluntar;
import teledon.persistence.repository.IVoluntarRepo;

import java.util.Date;
import java.util.List;

public class VoluntarHibernate implements IVoluntarRepo {
    private static SessionFactory sessionFactory;

    public VoluntarHibernate() {
        System.out.println("constructor");
        initialize();
    }




    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public static void main(String ... arg) {
        initialize();

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new Voluntar("gww", "aaa"));
            session.getTransaction().commit();
        }


        try(Session  session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Voluntar> result = session.createQuery("from Voluntar", Voluntar.class).list();
            for (Voluntar voluntar :  result) {
                System.out.println(voluntar);
            }
            Voluntar voluntar = (Voluntar)session.createQuery("from Voluntar where id = ?1")
                    .setParameter(1, 9L).setMaxResults(1)
                    .uniqueResult();
            System.out.println(voluntar);
            session.getTransaction().commit();
        }

        close();
    }

    @Override
    public Voluntar findOne(Long aLong) throws IllegalArgumentException{
        Voluntar voluntar = new Voluntar();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                voluntar = session.createQuery("from Voluntar where id = ?1", Voluntar.class)
                        .setParameter(1, aLong)
                        .setMaxResults(1)
                        .uniqueResult();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        return voluntar;
    }

    @Override
    public Iterable<Voluntar> findAll() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Voluntar> result = session.createQuery("from Voluntar", Voluntar.class).getResultList();
            return result;
        }

    }

    @Override
    public Voluntar save(Voluntar entity) {
        return null;
    }

    @Override
    public Voluntar delete(Long aLong) {
        return null;
    }

    @Override
    public Voluntar update(Voluntar entity) {
        return null;
    }

    @Override
    public Voluntar findByUsername(String username) throws IllegalArgumentException{
        Voluntar voluntar = new Voluntar();
        System.out.println("find by username");
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                voluntar = session.createQuery("from Voluntar where username = ?1", Voluntar.class)
                        .setParameter(1, username)
                        .setMaxResults(1)
                        .uniqueResult();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        System.out.println("hibernate gasit voluntar " + voluntar);
        return voluntar;
    }
}
