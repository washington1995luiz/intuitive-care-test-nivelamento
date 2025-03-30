package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.services.JdbcService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class JdbcServiceImpl implements JdbcService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateBatch(List<Object[]> batchArgs, String sql) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            jdbcTemplate.batchUpdate(sql, batchArgs);
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
