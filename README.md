# API Gateway Service

## Global Response Body

`ResponseBody` { exitCode: ..., desc: ..., payload: ... }

HTTP 200 / 4xx / 5xx

- `exitCode` (integer):
  - `0` : operation successful
  - `non-zero` : an error occurred
- `desc` (string): payload description
- `payload` (any) : requested data if exitCode == 0, error message if exitCode != 0

## Endpoints

`Job` { id: string, start: integer (UNIX timestamp), end: integer (UNIX timestamp), owner: string, isRecurrent: boolean }

`GET` **/gateway/status**

- request body : none
- desc : status
- payload : { isGatewayOpen : boolean }

`GET` **/jobs**

- request body : none
- desc : jobs
- payload : Job[ ]

`POST` **/jobs**

- request body : { start : string, end : string, message : string , isRecurrent : boolean }
- desc : scheduled job
- payload : Job

`PUT` **/jobs/{id}**

- request body : { start : ... , end : ... , message : ... }
- desc : updated job
- payload : Job

`DELETE` **/jobs/{id}**

- request body : none
- desc : number of jobs deleted
- payload : integer (1)
