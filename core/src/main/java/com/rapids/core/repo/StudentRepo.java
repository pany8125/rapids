package com.rapids.core.repo;

import com.rapids.core.domain.Pack;
import com.rapids.core.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author David on 17/2/28.
 */
@Repository
public interface StudentRepo extends PagingAndSortingRepository<Student, Long> {

    Student findByNameAndPassword(String name, String password);
    List<Student> findByMobile(String mobile);
    List<Student> findByName(String name);
    Page<Student> findByGradeId(Long gradeId, Pageable pageable);

    @Query(value = "SELECT count(*) FROM Student " +
            "WHERE gradeId = ?1 ", nativeQuery = true)
    long countByGradeId(Long gradeId);


}
