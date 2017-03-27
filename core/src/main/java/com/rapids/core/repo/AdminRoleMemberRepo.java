package com.rapids.core.repo;

import com.rapids.core.domain.AdminRole;
import com.rapids.core.domain.AdminRoleMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scott on 3/13/17.
 */
@Repository
public interface AdminRoleMemberRepo extends PagingAndSortingRepository<AdminRoleMember, Long>{
}
