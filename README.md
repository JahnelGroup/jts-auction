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

## Domain Design

This application is modeled after [Domain Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design) principles. The primary [Aggregate](https://martinfowler.com/bliki/DDD_Aggregate.html) is an **Auction** with it's primary Entity being a **Bid**. As we layer on security the next primary Aggregate will a **User**. 

* **Read:** [DDD-Entities-Value-Objects-Explained](http://blog.sapiensworks.com/post/2016/07/29/DDD-Entities-Value-Objects-Explained)

## Interfaces

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

## REST API

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

## OAuth2

The **2-security** branch introduces OAuth2 with the [Password Grant](https://www.oauth.com/oauth2-servers/access-tokens/password-grant/) flow. I wrote a helpful [oauth2](https://github.com/JahnelGroup/jts-auction/tree/2-security/scripts) script with a defaults file that should get you going really fast.

The first thing you need to do is **source** the oauth2 script because it will export a $OAUTH2_TOKEN variable to your enviroment that you can pass to the tool of your choice. It's important that you **source** the script and not just run it or else the variable will not be exported.

* **Read:** [What is the difference between executing a Bash script vs sourcing it?](https://superuser.com/questions/176783/what-is-the-difference-between-executing-a-bash-script-vs-sourcing-it)

```bash
$ . oauth2
The variable $OAUTH2_TOKEN now contains your token. Use like this:
  http :8080/api "$OAUTH2_TOKEN"
  curl localhost:8080/api -H "$OAUTH2_TOKEN"
```
Now you have a token for the username and password found in the defaults file (admin/admin by default). It comes in form of a header parameter named *Authorization* with a value of *Bearer $TOKEN*. Pass this to the tool of your choice.
 
```bash
[scripts]$ http -v POST :8080/api/auctions "$OAUTH2_TOKEN" name="Auction 1"
POST /api/auctions HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsianRzYXVjdGlvbmFwaSJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MjMzNTA5MDcsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sImp0aSI6IjllODg0YjA1LTZjYmMtNGE2ZC05NzNhLWM5M2VmZTEwZWY3NSIsImNsaWVudF9pZCI6Imp0c2F1Y3Rpb25jbGllbnQifQ.fD6KPw2isnA4terdv_LbgSS_2n2oAYtTDnpP-hD6pKQ
Connection: keep-alive
Content-Length: 21
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/0.9.9

{
    "name": "Auction 1"
}

HTTP/1.1 201 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Apr 2018 21:02:05 GMT
Expires: 0
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

{
    "createdBy": "admin", 
    "createdDatetime": "2018-04-09T16:02:05.406-05:00", 
    "description": null, 
    "id": 1, 
    "lastModifiedBy": "admin", 
    "lastModifiedDatetime": "2018-04-09T16:02:05.406-05:00", 
    "name": "Auction 1", 
    "version": 0
}
```
