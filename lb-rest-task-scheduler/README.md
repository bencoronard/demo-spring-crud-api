# API Gateway Service

REST API for scheduling opening/closing of API gateway

## Global Response Body

`ResponseBody` { respCode: ..., desc: ..., payload: ... }

HTTP 200

- `respCode` (integer):
  - `0` : operation successful
  - `non-zero` : business error occurred
- `desc` (string): payload description
- `payload` (any) : (requested data) if respCode == 0, (business error message) if respCode != 0

HTTP 4xx

- 401 : invalid access token
- 403 : unauthorized
- 400 : requested endpoint exists but request body is not valid
- etc.

HTTP 5xx

- 500 : unhandled errors
- 503 : server down
- etc.

## Endpoints

`Job` { id: string, start: integer (UNIX timestamp), end: integer (UNIX timestamp), owner: string, isRecurrent: boolean }

/----------------------------------------------------

`GET` **/gateway/status**

- _desc_ : "gateway status"
- _payload_ : { isGatewayOpen : boolean }

`POST` **/gateway/open**
- request body : none
- *response similar to /gateway/status

`POST` **/gateway/close**
- request body : { message : string }
- *response similar to /gateway/status

/----------------------------------------------------

`GET` **/jobs**

- request body : none
- _desc_ : "scheduled jobs"
- _payload_ : Job[ ]

`POST` **/jobs**

- request body : { start : string, end : string, message : string , isRecurrent : boolean }
- _desc_ : "created job"
- _payload_ : Job

`PUT` **/jobs/{id}**

- request body : { start : ... , end : ... , message : ... }
- _desc_ : "updated job"
- _payload_ : Job

`DELETE` **/jobs/{id}**

- request body : none
- _desc_ : "number of jobs deleted"
- _payload_ : 1 (integer)

## RespCode Translation Guide
RespCode : `####`

`(#)###` : SERVICE ID
- `0` : ...
- `1` : ...
- `2` : ...

`#(#)##` : SECTION ID
- `0` : ...
- `1` : ...
- `2` : ...

`##(##)` : EXCEPTION ID
- `00` : ...
- `10` : ...
- `20` : ...


## Exception handling

1. Basic:
    * Hello world
    * Hello world
