package app.repository.impl;

import app.domain.UserAccount;
import app.repository.UserAccountRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public UserAccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount findByUsername(final String username) {
        final String hql = "from UserAccount where username = :username";
        final Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("username", username);
        return (UserAccount) query.uniqueResult();
    }

    @Override
    public void saveOrUpdate(final UserAccount userAccount) {
        sessionFactory.getCurrentSession().saveOrUpdate(userAccount);
    }
}
