package com.rapids.core.repo;

import com.rapids.core.domain.StuPackRela;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface StuPackRelaRepo extends PagingAndSortingRepository<StuPackRela, Long> {
    @Query(value = "SELECT * FROM StuPackRela WHERE studentId = ?1 AND status > 0 ORDER BY id LIMIT 1", nativeQuery = true)
    StuPackRela findLastStudyPack(long studentId);

    StuPackRela findByStudentIdAndPackId(long studentId, long packId);

    List<StuPackRela> findByPackId(long packId);

    List<StuPackRela> findByStudentId(long studentId);

    Long deleteByStudentIdAndPackId(long studentId, long packId);

    Long deleteByStudentId(long studentId);

    Long deleteByPackId(long packId);

    @Modifying
    @Query(value = "UPDATE StuPackRela SET learnedNum = learnedNum + 1 WHERE studentId = ?1 AND packId = ?2", nativeQuery = true)
    int updateLeanedCountByStuId(long studentId, long packId);
}
