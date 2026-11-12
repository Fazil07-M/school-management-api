package az.iktex.organization.structure.testders.cache;

import az.iktex.organization.structure.testders.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecondLevelCacheTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldLoadDepartmentFromSecondLevelCache() {

        SessionFactory sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);

        Statistics statistics = sessionFactory.getStatistics();

        statistics.clear();

        // Birinci Persistence Context yaradilir
        EntityManager firstEntityManager =
                entityManagerFactory.createEntityManager();

        firstEntityManager.getTransaction().begin();

        Department firstDepartment =
                firstEntityManager.find(Department.class, 1L);

        firstEntityManager.getTransaction().commit();
        firstEntityManager.close();

        // Ikinci Persistence Context yaradilir
        EntityManager secondEntityManager =
                entityManagerFactory.createEntityManager();

        secondEntityManager.getTransaction().begin();

        Department secondDepartment =
                secondEntityManager.find(Department.class, 1L);

        secondEntityManager.getTransaction().commit();
        secondEntityManager.close();

        System.out.println("L2 Cache Hits: "
                + statistics.getSecondLevelCacheHitCount());

        System.out.println("L2 Cache Misses: "
                + statistics.getSecondLevelCacheMissCount());

        System.out.println("L2 Cache Puts: "
                + statistics.getSecondLevelCachePutCount());

        assertNotNull(firstDepartment);
        assertNotNull(secondDepartment);

        assertEquals(
                1,
                statistics.getSecondLevelCacheHitCount(),
                "Second-Level Cache istifade edilmedi!"
        );
    }
}