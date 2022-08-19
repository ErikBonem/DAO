package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private Session session = (new Util()).getSession();
    private final Logger LOG = Logger.getLogger("jm.task.core.jdbc.dao");
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            LOG.warning("Oshibochka vishla");
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            LOG.warning("Oshibochka vishla");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try{
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            LOG.warning("Oshibochka vishla");
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warning("Oshibochka vishla");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        users = session.createNamedQuery("getAllUsers", User.class).getResultList();
        return users;
    }

    @Override
    public void cleanUsersTable() {
    try {
        session.beginTransaction();
        session.createNamedQuery("cleanUsersTable", User.class).executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e){
        session.getTransaction().rollback();
        LOG.warning("Oshibochka vishla");
    }
    }
}
