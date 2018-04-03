package com.jahnelgroup.auctionapp.auction.bid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BidNotFoundException extends RuntimeException { }
