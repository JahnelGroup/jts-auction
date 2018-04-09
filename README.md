Journey Through Spring
======
![spring](https://user-images.githubusercontent.com/26745523/37191895-099bbea8-2328-11e8-8b88-657c49317c2d.png)

#### A pragmatic approach to learning the Spring Framework. 
[Journey Through Spring](https://github.com/JahnelGroup/journey-through-spring)

Auction Service 
------
Learning is best accomplished through practice. This repo is the use-case we follow as we take the Journey Through Spring.

| Branch         | Description                                               |
| -------------- | --------------------------------------------------------- |
| master         | Initial starting point.                                   |
| 1-services     | Basic REST API and service implementations.               |
| 2-security     | Added Spring Security and Users                           |
| 3-validations  | Added validations                                         |
| 4-events       | Added events                                              |
| X-ui           | Added basic UI                                            |
| X-websockets   | Added real-time updates with websockets                   |

Starting with master each branch incrementally adds another layer of complexity to the overall solution. This approach should reduce the complexity of jumping into a fully configured project right from the beginning. 

### Domain Design

This application is modeled after [Domain Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design) principles. The primary [Aggregate](https://martinfowler.com/bliki/DDD_Aggregate.html) is an **Auction** with it's primary Entity being a **Bid**. As we layer on security the next primary Aggregate will a **User**. 

* **Read:** [DDD-Entities-Value-Objects-Explained](http://blog.sapiensworks.com/post/2016/07/29/DDD-Entities-Value-Objects-Explained)

### Interfaces

The two primary interfaces you will implement are the AuctionService and BidService.

```java
public interface AuctionService {
    Iterable<Auction> findAll();
    Optional<Auction> findById(Long id);
    Auction save(Auction auction);
    Auction update(Auction incoming, Auction current);
    void delete(Auction auction);
    void deleteById(Long id);
}

public interface BidService {
    Iterable<Bid> findAll(Auction auction);
    Iterable<Bid> findAllByAuctionId(Long auctionId);
    Optional<Bid> findById(Auction auction, Long id);
    Bid save(Auction auction, Bid bid);
    Bid update(Auction auction, Bid incoming, Bid current);
    void delete(Auction auction, Bid bid);
    void deleteById(Auction auction, Long id);
}
```

### REST API

Your clients will interact with Auctions and Bids *through* the **Auction** aggregate. **Bids** Entity's only exist under an Auction and cannot be created directly - they are applied to the Auction. 

**Auction**

| Method | Path            | Description         | Branch  | 
| ------ | --------------- | ------------------- | ------- |
| POST   | /auctions       | Create one Auction  | master  |
| GET    | /auctions/{id}  | Read one Auction    | master  |
| GET    | /auctions       | Read all Auctions   | master  | 
| PUT    | /auctions/{id}  | Update one Auction  | master  | 
| DELETE | /auctions/{id}  | Delete one Auction  | master  |

**Bid**

| Method | Path                               | Description                    | Branch  | 
| ------ | ---------------------------------- | ------------------------------ | ------- |
| POST   | /auctions/{auctionId}/bids         | Adds one Bid to an Auction     | master  |
| GET    | /auctions/{auctionId}/bids/{bidId} | Read one Bid from an Auction   | master  |
| GET    | /auctions/{auctionId}/bids         | Read all Bids from an Auction  | master  | 
| PUT    | /auctions/{auctionId}/bids/{bidId} | Update one Bid from an Auction | master  | 
| DELETE | /auctions/{auctionId}/bids/{bidId} | Delete one Bid from an Auction | master  |

### Example usages of API 

Create an Auction.

```bash
$ http -v POST :8080/api/auctions name="Auction 1"
POST /api/auctions HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 21
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/0.9.9

{
    "name": "Auction 1"
}

HTTP/1.1 201 
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Apr 2018 20:50:41 GMT
Transfer-Encoding: chunked

{
    "description": null, 
    "id": 1, 
    "name": "Auction 1"
}
```

Add first Bid.

```bash
$ http -v POST :8080/api/auctions/1/bids amount=50.00
POST /api/auctions/1/bids HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 19
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/0.9.9

{
    "amount": "50.00"
}

HTTP/1.1 201 
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Apr 2018 20:50:57 GMT
Transfer-Encoding: chunked

{
    "amount": 50.0, 
    "id": 1
}
```

Add second Bid.

```bash
$ http -v POST :8080/api/auctions/1/bids amount=25.00
POST /api/auctions/1/bids HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 19
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/0.9.9

{
    "amount": "25.00"
}

HTTP/1.1 201 
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Apr 2018 20:51:03 GMT
Transfer-Encoding: chunked

{
    "amount": 25.0, 
    "id": 2
}
```

List Bids.

```bash
$ http -v GET :8080/api/auctions/1/bids
GET /api/auctions/1/bids HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/0.9.9



HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Apr 2018 20:51:21 GMT
Transfer-Encoding: chunked

[
    {
        "amount": 50.0, 
        "id": 1
    }, 
    {
        "amount": 25.0, 
        "id": 2
    }
]
```

Update second Bid.

```bash
$ http -v PUT :8080/api/auctions/1/bids/2 amount=65.00
PUT /api/auctions/1/bids/2 HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 19
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/0.9.9

{
    "amount": "65.00"
}

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Apr 2018 20:51:48 GMT
Transfer-Encoding: chunked

{
    "amount": 65.0, 
    "id": 2
}
```

List Bids again.

```bash
$ http -v GET :8080/api/auctions/1/bids
GET /api/auctions/1/bids HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/0.9.9



HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Apr 2018 20:51:51 GMT
Transfer-Encoding: chunked

[
    {
        "amount": 50.0, 
        "id": 1
    }, 
    {
        "amount": 65.0, 
        "id": 2
    }
]
```
