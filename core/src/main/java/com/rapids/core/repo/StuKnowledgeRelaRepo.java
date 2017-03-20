package com.rapids.core.repo;

import com.rapids.core.domain.StuKnowledgeRela;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface StuKnowledgeRelaRepo extends PagingAndSortingRepository<StuKnowledgeRela, String> {


    @Query(value = "SELECT * FROM StuKnowledgeRela " +
            "WHERE studentId = ?1 AND leanSeq < ?2 " +
            "AND deleted = 0 " +
            "AND enabled = 1 " +
            "AND leanSeq <> 0 " +
            "ORDER BY leanSeq LIMIT 1", nativeQuery = true)
    StuKnowledgeRela findRequireByTime(Long studentId, long timestamp);

    @Query(value = "SELECT * FROM StuKnowledgeRela " +
            "WHERE studentId = ?1 " +
            "AND deleted = 0 " +
            "AND enabled = 1 " +
            "AND viewCount = 0 " +
            "ORDER BY studentId, knowledgeId, createTime LIMIT 1", nativeQuery = true)
    StuKnowledgeRela findRequire(Long studentId);

    @Query(value = "SELECT * FROM StuKnowledgeRela " +
            "WHERE enableDate > ?1 AND enableDate < ?2", nativeQuery = true)
    StuKnowledgeRela findEnableByDay(String startDateFormat, String endDateFormat);

    @Query(value = "UPDATE StuKnowledgeRela set enable = 1 AND enableDate = ?1 " +
            "WHERE studentId = ?2 AND enable = 0 LIMIT ?3", nativeQuery = true)
    int enableKnowledgeByStudentId(Date enableDate, long studentId, int limit);

    @Query(value = "SELECT * FROM StuKnowledgeRela " +
            "WHERE studentId = ?1 AND knowledgeId = ?2", nativeQuery = true)
    StuKnowledgeRela findByStudentIdAndKnowledgeId(long studentId, long knowledgeId);


}
