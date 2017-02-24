package com.rapids.core.repo;

import com.rapids.core.domain.Sample;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David on 17/2/24.
 */
@Repository
public interface SampleRepo extends PagingAndSortingRepository<Sample, Integer> {
}
