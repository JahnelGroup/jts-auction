package com.jahnelgroup.auctionapp.auditing.context;

import com.jahnelgroup.auctionapp.domain.user.User;

public interface UserContextService {

    User getCurrentUser();
    Long getCurrentUserId();
    String getCurrentUsername();

}
