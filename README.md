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

#### Create

```bash
$ http -v POST :8080/api/auctions name="Auction 1"
POST /api/auctions HTTP/1.1
Accept: application/json
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 21
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/0.9.4

{
    "name": "Auction 1"
}

HTTP/1.1 201 
Content-Type: application/json;charset=UTF-8
Date: Tue, 03 Apr 2018 15:14:19 GMT
Transfer-Encoding: chunked

{
    "description": null,
    "id": 1,
    "name": "Auction 1"
}
```

#### Read

Find all auctions.

```bash
$ http -v GET :8080/api/auctions
GET /api/auctions HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/0.9.4

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Tue, 03 Apr 2018 15:14:48 GMT
Transfer-Encoding: chunked

[
    {
        "description": null,
        "id": 1,
        "name": "Auction 1"
    },
    {
        "description": null,
        "id": 2,
        "name": "Auction 2"
    }
]
``` 

Find one auction.

```bash
$ http -v GET :8080/api/auctions/2
GET /api/auctions/2 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/0.9.4

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Tue, 03 Apr 2018 15:15:30 GMT
Transfer-Encoding: chunked

{
    "description": null,
    "id": 2,
    "name": "Auction 2"
}
```

#### Update

```bash
$ http -v PUT :8080/api/auctions/2 name="Updated Auction 2" description="Adding desc"
PUT /api/auctions/2 HTTP/1.1
Accept: application/json
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 59
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/0.9.4

{
    "description": "Adding desc",
    "name": "Updated Auction 2"
}

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Tue, 03 Apr 2018 15:17:17 GMT
Transfer-Encoding: chunked

{
    "description": "Adding desc",
    "id": 2,
    "name": "Updated Auction 2"
}
```

#### Delete

```bash
$ http -v DELETE :8080/api/auctions/1
DELETE /api/auctions/1 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 0
Host: localhost:8080
User-Agent: HTTPie/0.9.4

HTTP/1.1 204 
Date: Tue, 03 Apr 2018 15:17:56 GMT



$ http -v GET :8080/api/auctions/1
GET /api/auctions/1 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/0.9.4

HTTP/1.1 404 
Content-Length: 0
Date: Tue, 03 Apr 2018 15:18:00 GMT
```
