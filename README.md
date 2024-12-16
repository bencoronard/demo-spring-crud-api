# LB Repository (Back-end)

Repository for back-end services

## Global Response Body

`ResponseBody` { respCode: ..., desc: ..., payload: ... }

- `respCode` (integer):
  - `0` : operation successful
  - `non-zero` : business error occurred
- `desc` (string): payload description
- `payload` (any) : requested data if respCode == 0, business error message if respCode != 0

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
