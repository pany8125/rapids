package com.rapids.core.repo;

import com.rapids.core.domain.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by scott on 3/13/17.
 */
@Repository
public interface AdminRepo extends PagingAndSortingRepository<Admin, Long>{

    @Query(value = "SELECT * FROM Admin " +
            "WHERE uid = ?1 AND passowrd = ?2 LIMIT 1", nativeQuery = true)
    Admin queryByUidAndPassword(String uid, String password);
}
