package com.rapids.core.repo;

import com.rapids.core.domain.StuPackRela;
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

    List<StuPackRela> findByStudentId(long studentId);
}
