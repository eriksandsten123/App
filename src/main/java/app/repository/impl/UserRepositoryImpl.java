package app.repository.impl;

import app.domain.Interest;
import app.domain.User;
import app.repository.UserRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserByName(final String name) {
        final String hql = "from User where name = :name";
        final Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("name", name);
        return (User) query.uniqueResult();
    }

    @Override
    public List<User> getOnlineUserProfiles(final int maxResults) {
        final String hql = "from User";
        final Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult(1);
        query.setMaxResults(maxResults);
        return (List<User>) query.list();
    }

    @Override
    public void saveOrUpdate(final User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public User getUserById(final Long id) {
        final String hql = "from User where id = :id";
        final Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (User) query.uniqueResult();
    }

    @Override
    public List<User> getUsersWithInterest(final Interest interest) {
        return (List<User>) sessionFactory.getCurrentSession().createQuery("from User user where :interest member of user.interests")
                .setParameter("interest", interest).list();
    }

    @Override
    public long countUsersWithInterest(final Interest interest) {
        return (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from User user where :interest member of user.interests")
                .setParameter("interest", interest).uniqueResult();
    }
}
