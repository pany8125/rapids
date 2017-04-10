package com.rapids.core.repo;

import com.rapids.core.domain.Knowledge;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface KnowledgeRepo extends PagingAndSortingRepository<Knowledge, Long> {

    Knowledge findByNameAndEditor(String name, String editor);
}
