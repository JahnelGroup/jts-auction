package com.jahnelgroup.auctionapp.security.acl;

import com.jahnelgroup.auctionapp.auditing.context.UserContextService;
import com.jahnelgroup.auctionapp.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * https://docs.spring.io/spring-security/site/docs/5.0.5.BUILD-SNAPSHOT/reference/htmlsingle/#domain-acls-getting-started
 *
 * This call will insert all base permissions for the creator of an Entity.
 */
@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class AclSecuredAspect {

    private MutableAclService aclService;
    private UserContextService userContextService;
    private PlatformTransactionManager transactionManager;

    private final static List<Permission> ALL_PERMISSIONS = Arrays.asList(
            BasePermission.READ,
            BasePermission.WRITE,
            BasePermission.CREATE,
            BasePermission.DELETE,
            BasePermission.ADMINISTRATION);

    @Pointcut("execution(* org.springframework.data.repository.CrudRepository.save(..))")
    public void save() {}

    @After("save() && args(entity)")
    public void insertAllPermissions(final JoinPoint pjp, AbstractEntity entity) {
        // Is this Entity meant to be secured by ACLs?
        if(!entity.getClass().isAnnotationPresent(AclSecured.class)){
            log.trace("ignoring entity={} not annotated with AclSecured", entity);
            return;
        }

        // Do you own this Entity?
        if( !userContextService.getCurrentUsername().equals(entity.getCreatedBy()) ){
            log.trace("ignoring entity={} because it's not yours", entity);
            return;
        }

        log.info("saving ACLs for user={} against entity={}", userContextService.getCurrentUsername(), entity);

        tx(entity, e -> {
            ObjectIdentity oi = new ObjectIdentityImpl(e.getClass(), e.getId());
            Sid sid = new PrincipalSid(userContextService.getCurrentUsername());

            // Create or update the relevant ACL
            MutableAcl acl = null;
            try {
                acl = (MutableAcl) aclService.readAclById(oi);
            } catch (NotFoundException nfe) {
                acl = aclService.createAcl(oi);
            }

            // TODO: This does not work - is it even possible?
            // Permission p = HierarchicalPermission.ADMIN;

            // Now grant some permissions via an access control entry (ACE)
            if( acl.getEntries() == null || acl.getEntries().size() == 0 ){
                for(Permission p : ALL_PERMISSIONS){
                    acl.insertAce(acl.getEntries().size(), p, sid, true);
                }
            }

            aclService.updateAcl(acl);
            return null;
        });

    }

    /**
     * TODO - This should really be tied to the existing transaction. Something needs
     * to be done to change the ordering of this Aspect to run after the Transaction Aspect.
     *
     * @param o
     * @param f
     */
    private void tx(AbstractEntity o, Function<AbstractEntity, Void> f){
        TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                f.apply(o);
            }
        });
    }

}
