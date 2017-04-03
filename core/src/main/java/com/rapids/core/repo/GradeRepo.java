package com.rapids.core.repo;

import com.rapids.core.domain.Grade;
import com.rapids.core.domain.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface GradeRepo extends PagingAndSortingRepository<Grade, Long> {

    Student findByNameAndPassword(String name, String password);
}
