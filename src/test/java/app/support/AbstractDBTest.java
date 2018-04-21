package app.support;

import app.TestConfig;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public abstract class AbstractDBTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setup() throws Exception {
        // this script will insert some useful test data in the in-memory database
        //executeSqlScript("test-init-db.sql", false);

        // verify that the script worked
        /*
        Assert.assertEquals(349, countRowsInTable("category"));
        Assert.assertEquals(338, countRowsInTable("region"));
        Assert.assertEquals(4, countRowsInTable("employment_type"));
        Assert.assertEquals(13, countRowsInTable("tags"));
        Assert.assertEquals(9, countRowsInTable("product"));
        Assert.assertEquals(54, countRowsInTable("job_ad_publication_prices"));
        */
    }
}
