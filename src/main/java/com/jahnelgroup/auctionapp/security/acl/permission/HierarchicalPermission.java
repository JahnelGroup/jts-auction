package com.jahnelgroup.auctionapp.security.acl.permission;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.model.Permission;

/**
 * 4/16/2018 - THIS IS CURRENTLY NOT BEING USED ... Unsure if this is possible.
 *
 * Implements Hierarchical permissions based on CumulativePermission. For example someone with
 * READ can only READ. Write can READ and Write. Admin can do everything.
 *
 * This is intended to be registered in the MethodSecurityExpressionHandler bean defined in AclContext.java.
 *
 */
public class HierarchicalPermission extends CumulativePermission {

    public static final Permission READ   = new CumulativePermission()
            .set(BasePermission.READ);

    public static final Permission WRITE  = new CumulativePermission()
            .set(BasePermission.READ)
            .set(BasePermission.WRITE);

    public static final Permission CREATE = new CumulativePermission()
            .set(BasePermission.READ)
            .set(BasePermission.WRITE)
            .set(BasePermission.CREATE);

    public static final Permission DELETE = new CumulativePermission()
            .set(BasePermission.READ)
            .set(BasePermission.WRITE)
            .set(BasePermission.CREATE)
            .set(BasePermission.DELETE);

    public static final Permission ADMIN  = new CumulativePermission()
            .set(BasePermission.READ)
            .set(BasePermission.WRITE)
            .set(BasePermission.CREATE)
            .set(BasePermission.DELETE)
            .set(BasePermission.ADMINISTRATION);

}