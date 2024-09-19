# API Gateway Service

REST API for scheduling opening/closing of API gateway

## Global Response Body

`ResponseBody` { respCode: ... , desc: ... , payload: ... }

HTTP 200

- `respCode` (integer): 0
- `desc` (string): payload description
- `payload` (any) : requested data

HTTP 4xx, 5xx

- `respCode` (integer): ####
- `desc` (string):
    - env `dev`: error message from JVM (not safe for public)
    - env `prod`: null
- `payload` (any) : custom error message (safe for public)

## Global Request Header

- `X-API-KEY` : "loxbit888"
- `X-USER-ID` : ...

## Endpoints

`Job` { id: string, start: integer (UNIX timestamp), end: integer (UNIX timestamp), owner: string, isRecurrent: boolean }

/#######################################

`GET` **/gateway/status**

Request body:
  - none

Response body:
- _respCode_: 0
- _desc_ : "gateway status"
- _payload_ : { isGatewayOpen : boolean }

/----------------------------------------------------

`POST` **/gateway/open**
Request body:
  - none

Response body:
- _respCode_: 0
- _desc_ : null
- _payload_ : null

/----------------------------------------------------

`POST` **/gateway/close**
Request body:
  - none

Response body:
- _respCode_: 0
- _desc_ : null
- _payload_ : null

/#######################################

`GET` **/jobs**

Request body:
  - none

Response body:
- _respCode_: 0
- _desc_ : "scheduled jobs"
- _payload_ : Job[]

/----------------------------------------------------

`POST` **/jobs**

Request body:
  - { start: UNIX timestamp (seconds), end: UNIX timestamp (seconds), message: string}

Response body:
- _respCode_: 0
- _desc_ : null
- _payload_ : null

/----------------------------------------------------

`PUT` **/jobs/{id}**

Request body:
  - same as `POST` **/jobs**

Response body:
- _respCode_: 0
- _desc_ : null
- _payload_ : null

/----------------------------------------------------

`DELETE` **/jobs/{id}**

Request body:
  - none

Response body:
- _respCode_: 0
- _desc_ : null
- _payload_ : null

## RespCode Error Translation Guide
RespCode : `####`

`(#)###` : SERVICE ID
- `1` : HTTP
- `3` : Audit
- `5` : Gateway
- `7` : Scheduler

`#(#)##` : NESTED SERVICE ID
- `0` : none
- `1` : HTTP
- `3` : Audit
- `5` : Gateway
- `7` : Scheduler

`##(##)` : EXCEPTION ID
- `40` : `HTTP 502` Error at Gateway proxy server (this service)
- `50` : `HTTP 502` Error at Gateway server
- `71` : `HTTP 400` Illegal _start_ time
- `72` : `HTTP 400` Illegal _end_ time