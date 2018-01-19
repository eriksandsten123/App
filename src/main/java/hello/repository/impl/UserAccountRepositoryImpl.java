package hello.repository.impl;

import hello.domain.UserAccount;
import hello.repository.UserAccountRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public UserAccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserAccount findByUsername(final String username) {
        final String hql = "from UserAccount where username = :username";
        final Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("username", username);
        return (UserAccount)query.uniqueResult();
    }

    @Override
    public void saveOrUpdate(final UserAccount userAccount) {
        sessionFactory.getCurrentSession().saveOrUpdate(userAccount);
    }
}
