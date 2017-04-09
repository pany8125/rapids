package com.rapids.core.repo;

import com.rapids.core.domain.Knowledge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface KnowledgeRepo extends PagingAndSortingRepository<Knowledge, Long> {

    @Query(value = "SELECT * FROM Knowledge " +
            "WHERE title = ?1", nativeQuery = true)
    List<Knowledge> queryKnowledgeByTitle(String title);

    List<Knowledge> findByPackId(long packId);
}
