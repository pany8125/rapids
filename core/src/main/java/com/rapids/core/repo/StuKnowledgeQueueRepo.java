package com.rapids.core.repo;

import com.rapids.core.domain.StuKnowledgeQueue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface StuKnowledgeQueueRepo extends PagingAndSortingRepository<StuKnowledgeQueue, Long> {
}
