package com.rapids.core.repo;

import com.rapids.core.domain.Admin;
import com.rapids.core.domain.AdminRole;
import com.rapids.core.domain.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scott on 3/13/17.
 */
@Repository
public interface AdminRepo extends PagingAndSortingRepository<Admin, Long>{

    @Query(value = "SELECT * FROM Admin " +
            "WHERE uid = ?1 AND password = ?2 LIMIT 1", nativeQuery = true)
    Admin queryByUidAndPassword(String uid, String password);

    Admin findById(Integer id);
}
