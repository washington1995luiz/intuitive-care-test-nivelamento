package br.com.washington.intuitive_care_test.services;

import java.util.List;

public interface JdbcService {

    void updateBatch(List<Object[]> batchArgs, String sql);

}
