package com.rapids.core.repo;

import com.rapids.core.domain.Admin;
import com.rapids.core.domain.AdminRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scott on 3/13/17.
 */
@Repository
public interface AdminRoleRepo extends PagingAndSortingRepository<AdminRole, Long>{

    @Query(value = "SELECT distinct a.* FROM AdminRole a, AdminRoleMember c where a.id = c.roleid and c.adminid=?1", nativeQuery = true)
    List<AdminRole> getAdminRoleList(int adminid);

    List<AdminRole> findByStatus(String status);
}
