# API Gateway Service

service สำหรับ schedule การ เปิด/ปิด Kong API gateway

## Global Response Body

`ResponseBody` { respCode: ..., desc: ..., payload: ... }

HTTP 200

- `respCode` (integer):
  - `0` : operation successful
  - `non-zero` : business error occurred
- `desc` (string): payload description
- `payload` (any) : requested data if respCode == 0, business error message if respCode != 0

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

`GET` **/status**

- request body : none
- desc : gateway status
- payload : { isGatewayOpen : boolean }

`GET` **/jobs**

- request body : none
- desc : scheduled jobs
- payload : Job[ ]

`POST` **/jobs**

- request body : { start : string, end : string, message : string , isRecurrent : boolean }
- desc : created job
- payload : Job

`PUT` **/jobs/{id}**

- request body : { start : ... , end : ... , message : ... }
- desc : updated job
- payload : Job

`DELETE` **/jobs/{id}**

- request body : none
- desc : number of jobs deleted
- payload : integer (1)
