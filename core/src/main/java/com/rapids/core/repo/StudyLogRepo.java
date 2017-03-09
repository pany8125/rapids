package com.rapids.core.repo;

import com.rapids.core.domain.StuPackRela;
import com.rapids.core.domain.StudyLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface StudyLogRepo extends PagingAndSortingRepository<StudyLog, String> {
}
