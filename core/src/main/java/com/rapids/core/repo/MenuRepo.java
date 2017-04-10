package com.rapids.core.repo;

import com.rapids.core.domain.Admin;
import com.rapids.core.domain.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scott on 3/13/17.
 */
@Repository
public interface MenuRepo extends PagingAndSortingRepository<Menu, Long>{

    @Query(value = "SELECT distinct a.id as id, a.text as text, a.url as url, a.leaf as leaf, a.orderNum as orderNum   FROM Menu a,RoleMenuMember b,AdminRoleMember c where a.parentid= ?2 and a.status='ACTIVE' " +
            " and a.id=b.menuid and c.roleid=b.roleid and c.adminid=?1 ORDER BY a.orderNum,a.id ", nativeQuery = true)
    List<Menu> getMenuListByAdminid(int adminId, int parent);
}
