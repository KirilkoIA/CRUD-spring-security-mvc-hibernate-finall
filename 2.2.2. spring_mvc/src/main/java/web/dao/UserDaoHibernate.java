package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoHibernate implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(long id) {
        User us = entityManager.find(User.class, id);
        entityManager.remove(us);
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserByName(String name) {
        return entityManager.createQuery("select u from User u where u.name=:username", User.class).setParameter("username", name).getSingleResult();
    }

    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("select u from Role u where u.name=:rolename", Role.class).setParameter("rolename", name).getSingleResult();
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<Role>(entityManager.createQuery("select u from Role u", Role.class).getResultList());
    }
}
