
package az.iktex.organization.structure.testders.controller;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final Statistics statistics;

    public CacheController(EntityManagerFactory entityManagerFactory) {
        this.statistics = entityManagerFactory
                .unwrap(SessionFactory.class)
                .getStatistics();
    }

    @GetMapping("/stats")
    public Map<String, Object> getCacheStats() {
        return Map.of(
                "secondLevelCacheHitCount", statistics.getSecondLevelCacheHitCount(),
                "secondLevelCacheMissCount", statistics.getSecondLevelCacheMissCount(),
                "secondLevelCachePutCount", statistics.getSecondLevelCachePutCount(),
                "queryCacheHitCount", statistics.getQueryCacheHitCount(),
                "queryCacheMissCount", statistics.getQueryCacheMissCount()
        );
    }

    @DeleteMapping("/stats")
    public Map<String, String> clearStats() {
        statistics.clear();
        return Map.of("message", "Hibernate statistics cleared");
    }
}
