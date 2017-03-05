package com.rapids.core.repo;

import com.rapids.core.domain.Pack;
import org.springframework.data.repository.PagingAndSortingRepository;

@org.springframework.stereotype.Repository
public interface PackRepo extends PagingAndSortingRepository<Pack, Long> {
}
