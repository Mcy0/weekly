

##  1.全局错误码

| code | 错误解释     |
| ------ | ------------ |
| 1000   | 未登录|
| 1002   | 非管理员|
| 1003   | 参数为空		|
| 1004   | 参数格式有错|
| 2000   | 成功|
| 3000   | 账户密码错误|
| 3001 | 请求已存在 |
| 3002 | 请求不存在 |
| 3003 | 密码错误 |
| 3004 | 请求次数过多 |
| 4000 | 失败 |
## 2.全局返回参数
|参数名|类型|说明|
|:-----  |:-----|-----                           |
|success |String   |true请求成功 false请求失败  |
|code |String   |错误码  |
|msg |String   |描述(成功或者失败)的原因  |

## 3.用户相关接口



http://localhost:8080/weekly_war_exploded/  更换成

http://47.95.244.73/weekly_war/

###  a.登录

- 用户登录接口

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/login.do `

**请求方式：**

- POST 

**参数：** 

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|email |是  |string |用户名(邮箱)   |
|password |是  |string | 密码(4-18位，数字，字母) |


 **返回示例**
``` 
{
    "msg": "登录成功",
    "code": "2000",
    "success": true,
    "user": {
        "id": 1,
        "userName": "马晨阳",
        "email": "806938139@qq.com",
        "password": null,
        "professionalClass": null,
        "tel": null,
        "address": "河南省商丘市",
        "learningDirection": "BACH_END",
        "status": "ADMINISTRATOR",
        "state": null
    }
}
```
 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|id |int   |每个用户唯一编号  |
|userName |String |用户名 |
|email |String |邮箱号 |
|professionalClass |String |专业班级 |
|tel |String |手机号 |
|address |String |地址 |
|learningDirection |String |学习方向：前端(FRONT_END)，后端(BACH_END)，产品(PRODUCT)，运营(OPERATING)，视觉(VISUAL) |
|status |String |用户：管理员(ADMINISTRATOR)，非管理员(NONADMINISTRATOR) |
|state |String |状态：在校(IN_SCHOOL)，不在校(OUT_SCHOOL) |

 **备注** 

| code | 说明             |
| ---- | ---------------- |
| 1003 | 参数为空         |
| 1004 | 格式不对         |
| 3000 | 账户或者密码错误 |
| 2000 | 登录成功         |
| 5000 | 后端错误         |



- 更多返回错误代码请看首页的错误代码描述

### b.退出登录

- 用户退出登录接口

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/logout.do `

**请求方式：**

- GET

**参数：** 


 **返回示例**
``` 
{
    "msg": "退出出成功, 删除Cookie",
    "code": "2000",
    "success": true
}
```
 **返回参数说明** 

 **备注** 

| code | 说明             |
| ---- | ---------------- |
| 2000 | 成功         |
| 5000 | 后端错误         |

- 更多返回错误代码请看首页的错误代码描述

### c.获取个人资料

- 用户获取个人资料接口

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/getUser.do `

**请求方式：**

- GET

**参数：** 


 **返回示例**
``` 
{
    "msg": "获取成功",
    "code": "2000",
    "success": true,
    "user": {
        "id": 1,
        "userName": "马晨阳",
        "email": "806938139@qq.com",
        "password": null,
        "professionalClass": null,
        "tel": null,
        "address": "河南省商丘市",
        "learningDirection": "BACH_END",
        "status": "ADMINISTRATOR",
        "state": null
    }
}
```
 **返回参数说明** 

- 参考a.登录

 **备注** 

| code | 说明             |
| ---- | ---------------- |
| 2000 | 成功         |
| 5000 | 后端错误         |

- 更多返回错误代码请看首页的错误代码描述

### d.修改个人资料

- 用户获取个人资料接口

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/updateUser.do `

**请求方式：**

- GET

**参数：** 

| 参数名            | 必选 | 类型   | 说明                                                         |
| ----------------- | ---- | ------ | ------------------------------------------------------------ |
| userName          | 否   | String | 用户名(长度最大16)                                           |
| professionalClass | 否   | String | 专业班级(xxxx)，如：计科1705                                 |
| tel               | 否   | String | 手机号(格式)：                                               |
| address           | 否   | String | 地址(xx省xx市)：如：陕西省西安市                             |
| learningDirection | 否   | String | 学习方向：前端(FRONT_END)，后端(BACH_END)，产品(PRODUCT)，运营(OPERATING)，视觉(VISUAL) |
| state             | 否   | String | 状态：在校(IN_SCHOOL)，不在校(OUT_SCHOOL)                    |

 **返回示例**

```
{
    "msg": "资料修改成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

 **备注** 

| code | 说明     |
| ---- | -------- |
| 2000 | 成功     |
| 5000 | 后端出错 |



- 更多返回错误代码请看首页的错误代码描述

### e.获取全部用户

  - 用户同事接口

  **请求URL：** 

  - ` http://localhost:8080/weekly_war_exploded/user/getAllUser.do `

  **请求方式：**

  - GET

  **参数：** 



| 参数名            | 必选 | 类型    | 说明                                                         | 默认值 |
| ----------------- | ---- | ------- | ------------------------------------------------------------ | ------ |
| userName          | 否   | String  | 用户名(长度最大16)：模糊匹配                                 |        |
| learningDirection | 否   | String  | 学习方向：前端(FRONT_END)，后端(BACH_END)，产品(PRODUCT)，运营(OPERATING)，视觉(VISUAL) |        |
| pageParams        | 否   | Object  | 分页参数                                                     |        |
| page              | 否   | Integer | 页数                                                         | 1      |
| pageSize          | 否   | Integer | 每页个数                                                     | 10     |

 ``` 
{
	"userName":"马晨阳",
	"pageParams":{
		"page":1,
		"pageSize":"1"
	
	}
}
 ```

  **返回示例**

  ```
{
    "msg": "获取成功",
    "total": 1,
    "code": "2000",
    "success": true,
    "users": [
        {
            "id": 1,
            "userName": "马晨阳",
            "email": "806938139@qq.com",
            "password": null,
            "professionalClass": "计科1705",
            "tel": null,
            "address": "河南省商丘市",
            "learningDirection": "BACH_END",
            "status": "ADMINISTRATOR",
            "state": null
        }
    ]
}
  ```

   **返回参数说明** 

| 参数      | 说明         |
| --------- | ------------ |
| total     | 全部用户个数 |
| totalPage | 总页数       |

   **备注** 

| code |
| ---- |
| 2000 |
| 5000 |



  - 更多返回错误代码请看首页的错误代码描述

### f.修改密码

- 用户同事接口

  **请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/updateUserPassword.do `

  **请求方式：**

- GET

  **参数：**


| 参数名      | 必选 | 类型   | 说明                     | 默认值 |
| ----------- | ---- | ------ | ------------------------ | ------ |
| oldPassword | 是   | String | 密码(4-18位，数字，字母) |        |
| newPassword | 是   | String | 密码(4-18位，数字，字母) |        |

  **返回示例**

```
{
    "msg": "成功",
    "code": "2000",
    "success": true
}
```

   **返回参数说明** 

   **备注** 

| code |
| ---- |
| 1003 |
| 1004 |
| 3003 |
| 2000 |
| 5000 |



- 更多返回错误代码请看首页的错误代码描述

### g.忘记密码

  - 用户同事接口

    **请求URL：** 

  - ` http://localhost:8080/weekly_war_exploded/user/forgotPassword.do `

    **请求方式：**

  - GET

    **参数：**

| 参数名   | 必选 | 类型   | 说明                     |
| -------- | ---- | ------ | ------------------------ |
| email    | 是   | String | 邮箱                     |
| password | 是   | String | 密码(4-18位，数字，字母) |
| code     | 是   | String | 4位验证码                |

**返回示例**

  ```
{
    "msg": "成功",
    "code": "2000",
    "success": true
}
{
    "msg": "还没有请求验证码",
    "code": "4000",
    "success": false
}
  ```

**返回参数说明** 
    

**备注** 

| code |
| ---- |
| 1003 |
| 1004 |
| 3002 |
| 2000 |
| 5000 |
| 4000 |
| 3004 |
|      |



  - 更多返回错误代码请看首页的错误代码描述

    

## 4.管理员相关接口

### a.添加用户

- 添加用户接口

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/addUserSim.do `

**请求方式：**

- GET

**参数：** 

| 参数名   | 必选 | 类型   | 说明                                                    | 默认值   |
| -------- | ---- | ------ | ------------------------------------------------------- | -------- |
| email    | 是   | String | 邮箱号                                                  |          |
| password | 否   | String | 密码(4-18位，数字，字母)                                | 123456   |
| status   | 否   | String | 用户：管理员(ADMINISTRATOR)，非管理员(NONADMINISTRATOR) | 非管理员 |
| state    | 否   | String | 状态：在校(IN_SCHOOL)，不在校(OUT_SCHOOL)               | 在校     |

 **返回示例**

```
{
    "msg": "资料修改成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

 **备注** 

| code |
| ---- |
| 1003 |
| 1004 |
| 3001 |
| 2000 |
| 5000 |



- 更多返回错误代码请看首页的错误代码描述

### b.修改用户

- 修改用户接口

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/updateUserSim.do `

**请求方式：**

- GET

**参数：** 

| 参数名 | 必选 | 类型    | 说明                                                    | 默认值   |
| ------ | ---- | ------- | ------------------------------------------------------- | -------- |
| email  | 是   | String  | 邮箱号                                                  |          |
| status | 否   | String  | 用户：管理员(ADMINISTRATOR)，非管理员(NONADMINISTRATOR) | 非管理员 |
| state  | 否   | String  | 状态：在校(IN_SCHOOL)，不在校(OUT_SCHOOL)               | 在校     |
| reset  | 否   | Boolean | true初始化密码(123456)                                  | false    |

 **返回示例**

```
{
    "msg": "资料修改成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

| code |
| ---- |
| 1003 |
| 1004 |
| 3002 |
| 2000 |
| 5000 |

 **备注** 

- 更多返回错误代码请看首页的错误代码描述
### c.删除用户（谨慎操作）

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/user/deleteUser.do `

**请求方式：**

- GET

**参数：** 

| 参数名 | 必选 | 类型 | 说明     |
| ------ | ---- | ---- | -------- |
| id     | 是   | int  | 用户编号 |

 **返回示例**

```
{
    "msg": "成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

 **备注** 

| code |
| ---- |
| 2000 |
| 5000 |
| 1003 |

### d.删除会议

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/meeting/deleteMeeting.do `

**请求方式：**

- GET

**参数：** 

| 参数名 | 必选 | 类型 | 说明id |
| ------ | ---- | ---- | ------ |
| id     | 是   | int  | 会议id |

 **返回示例**

```
{
    "msg": "删除成功",
    "code": "2000",
    "success": true
}
```

 **备注** 

每天最多添加一个

| code |
| ---- |
| 1003 |
| 2000 |
| 5000 |
| 3002 |

- 更多返回错误代码请看首页的错误代码描述

## 5.周报

### a.添加周报

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/task/addTask.do `

**请求方式：**

- GET

**参数：** 

| 参数名         | 必选 | 类型   | 说明                 |
| -------------- | ---- | ------ | -------------------- |
| taskDate       | 是   | Date   | 时间(格式yyyy-MM-dd) |
| taskName       | 是   | 名称   | 长度 < 40            |
| content        | 否   | 内容   | 内容 < 1024          |
| completeDegree | 否   | 完成度 | 0-100                |
| timeConsuming  | 否   | 耗时   | 长度 < 40            |

 **返回示例**

```
{
    "msg": "成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

 **备注** 

| code |
| ---- |
| 2000 |
| 5000 |
| 1003 |

### a.删除周报

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/task/deleteTask.do `

**请求方式：**

- GET

**参数：** 

| 参数名 | 必选 | 类型 | 说明   |
| ------ | ---- | ---- | ------ |
| id     | 是   | int  | 周报id |

 **返回示例**

```
{
    "msg": "成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

 **备注** 

| code |
| ---- |
| 2000 |
| 5000 |
| 1003 |

### a.更新周报

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/task/updateTask.do `

**请求方式：**

- GET

**参数：** 

| 参数名         | 必选 | 类型   | 说明                 |
| -------------- | ---- | ------ | -------------------- |
| taskDate       | 否   | Date   | 时间(格式yyyy-MM-dd) |
| taskName       | 否   | 名称   | 长度 < 40            |
| content        | 否   | 内容   | 内容 < 1024          |
| completeDegree | 否   | 完成度 | 0-100                |
| timeConsuming  | 否   | 耗时   | 长度 < 40            |

 **返回示例**

```
{
    "msg": "成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

 **备注** 

| code |
| ---- |
| 2000 |
| 5000 |
| 1003 |
| 1004 |

### a.获取全部周报

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/task/getAllTasks.do `

**请求方式：**

- GET

**参数：** 

| 参数名   | 必选 | 类型 | 说明         |
| -------- | ---- | ---- | ------------ |
| page     | 否   | int  | 页数(1)      |
| pageSize | 否   | int  | 每页大小(10) |

 **返回示例**

```
{
    "msg": "成功",
    "code": "2000",
    "success": true
}
```

 **返回参数说明** 

| 参数      | 说明                       |
| --------- | -------------------------- |
| total     | 总周报个数                 |
| totalPage | 页数                       |
| taskDate  | 日期返回【时间戳】注意转换 |

 **备注** 

| code |
| ---- |
| 2000 |
| 5000 |

### a.获取某用户三周（上，这，下）周报

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/task/getTasks.do `

**请求方式：**

- GET

**参数：** 

| 参数名 | 必选 | 类型 | 说明   |
| ------ | ---- | ---- | ------ |
| userId | 是   | int  | 用户id |

 **返回示例**

```
{
    "msg": "获取成功",
    "code": "2000",
    "success": true,
    "lastTask": [
        {
            "id": 1,
            "userId": 1,
            "taskDate": 1549728000000,
            "taskName": "测试",
            "content": "我的周报",
            "completeDegree": 0,
            "timeConsuming": null
        },
        {
            "id": 2,
            "userId": 1,
            "taskDate": 1549814400000,
            "taskName": "测试",
            "content": "我的周报",
            "completeDegree": 0,
            "timeConsuming": null
        }
    ],
    "thisTask": [],
    "nextTask": []
}
```

 **返回参数说明** 

| 参数     | 说明                       |
| -------- | -------------------------- |
| lastTask | 上                         |
| thisTask | 本                         |
| nextTask | 下                         |
| taskDate | 日期返回【时间戳】注意转换 |

 **备注** 

| code |
| ---- |
| 1003 |
| 2000 |
| 5000 |

### a.获取某用户全部周报

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/task/getAllTasksByUserId.do `

**请求方式：**

- GET

**参数：** 

| 参数名   | 必选 | 类型 | 说明         |
| -------- | ---- | ---- | ------------ |
| page     | 否   | int  | 页数(1)      |
| pageSize | 否   | int  | 每页大小(10) |
| userId   | 是   | int  | 用户id       |

 Json格式参数

```
{
	"userId":"1",
	"pageParams":{
		"page":1,
		"pageSize":"3"
	
	}
}
```

**返回示例**

```
{
    "msg": "获取成功",
    "total": 3,
    "code": "2000",
    "success": true,
    "totalPage": 1,
    "tasks": [
        {
            "id": 2,
            "userId": 1,
            "taskDate": 1549814400000,
            "taskName": "测试",
            "content": "我的周报",
            "completeDegree": 0,
            "timeConsuming": null
        },
        {
            "id": 1,
            "userId": 1,
            "taskDate": 1549728000000,
            "taskName": "测试",
            "content": "我的周报",
            "completeDegree": 0,
            "timeConsuming": null
        },
        {
            "id": 3,
            "userId": 1,
            "taskDate": 1549123200000,
            "taskName": "hello",
            "content": "我的周报",
            "completeDegree": 0,
            "timeConsuming": null
        }
    ]
}
```

 **返回参数说明** 

| 参数      | 说明                       |
| --------- | -------------------------- |
| total     | 总周报个数                 |
| totalPage | 页数                       |
| taskDate  | 日期返回【时间戳】注意转换 |

 **备注** 

| code |
| ---- |
| 2000 |
| 5000 |
| 1003 |

## 6.会议记录
### a.添加会议

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/meeting/addMeeting.do `

**请求方式：**

- GET

**参数：** 

| 参数名       | 必选 | 类型   | 说明                                                       |
| ------------ | ---- | ------ | ---------------------------------------------------------- |
| content      | 是   | String | 会议内容 length <= 800                                     |
| picture      | 否   | String | 照片链接可以自己添加（当file添加时失去作用）               |
| documentLink | 否   | String | 文档链接 length <= 255                                     |
| file         | 否   | file   | **注意form表单添加enctype="multipart/form-data"** 格式照片 |

 **返回示例**

```
{
    "msg": "上传文件成功",
    "success": true,
    "code":"2000"
}
```

 **备注** 

每天最多添加一个

| code |
| ---- |
| 1003 |
| 2000 |
| 5000 |

### a.更新会议

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/meeting/updateMeeting.do `

**请求方式：**

- GET

**参数：** 

| 参数名       | 必选 | 类型   | 说明                                                       |
| ------------ | ---- | ------ | ---------------------------------------------------------- |
| content      | 是   | String | 会议内容 length <= 800                                     |
| picture      | 否   | String | 照片链接可以自己添加（当file添加时失去作用）               |
| documentLink | 否   | String | 文档链接 length <= 255                                     |
| file         | 否   | file   | **注意form表单添加enctype="multipart/form-data"** 格式照片 |
| id           | 是   | int    | 会议id                                                     |

 **返回示例**

```
{
    "msg": "上传文件成功",
    "success": true,
    "code":"2000"
}
```

 **备注** 

每天最多添加一个

| code |
| ---- |
| 1003 |
| 2000 |
| 5000 |
| 1004 |
| 3002 |
### a.获取会议

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/meeting/getMeetings.do `

**请求方式：**

- GET

**参数：** 

| 参数名   | 必选 | 类型 | 说明     |
| -------- | ---- | ---- | -------- |
| page     | 否   | int  | 页数     |
| pageSize | 否   | int  | 每页数量 |

 **返回示例**

```
{
    "msg": "查询成功",
    "total": 2,
    "code": "2000",
    "success": true,
    "totalPage": 1,
    "meetings": [
        {
            "id": 35,
            "meetingDate": 1549814400000,
            "userId": 1,
            "picture": "http://localhost:8080/weekly_war_exploded/meeting/picture.do?picture=/home/mcy/IDEA/weekly/src/main/upload/meetingPicture/2019-02-11.jpg",
            "content": ",今天不学习",
            "documentLink": ",",
            "userName":
        },
        {
            "id": 33,
            "meetingDate": 1549728000000,
            "userId": 1,
            "picture": "http://localhost:8080/weekly_war_exploded/meeting/picture.do?picture=/home/mcy/IDEA/weekly/src/main/upload/meetingPicture/2019-02-10.jpg",
            "content": "bbb",
            "documentLink": null
        }
    ]
}
```

 **备注** 

| 参数        | 说明         |
| ----------- | ------------ |
| total       | 全部个数     |
| totalPage   | 页数         |
| picture     | 照片链接     |
| meetingDate | 注意转换     |
| userName    | 最后有谁修改 |

每天最多添加一个

| code |
| ---- |
| 2000 |
| 5000 |
### a.分享会议1

**接口说明**

发送给所有在校人员

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/meeting/sendMeetings.do `

**请求方式：**

- GET

**参数：** 

| 参数名 | 必选 | 类型 | 说明       |
| ------ | ---- | ---- | ---------- |
| id     | 是   | int  | 会议编号id |

 **返回示例**

```
{
    "msg": "发送成功",
    "code": "2000",
    "success": true
}
```

 **备注** 

每天最多添加一个

| code |
| ---- |
| 1003 |
| 2000 |
| 4000 |
| 5000 |
### a.分享会议2

发给指定人

**接口说明**

发送给所有在校人员

**请求URL：** 

- ` http://localhost:8080/weekly_war_exploded/meeting/sendMeetingsByUser.do `

**请求方式：**

- GET

**参数：** 

| 参数名    | 必选 | 类型     | 说明       |
| --------- | ---- | -------- | ---------- |
| emailList | 是   | String[] |            |
| id        | 是   | int      | 会议编号id |

```
{
	"emailList":[
	"806938139@qq.com","974690208@qq.com"],
	"id":"40"
}

```

 **返回示例**

```
{
    "msg": "发送成功",
    "code": "2000",
    "success": true
}
```

 **备注** 

每天最多添加一个

| code |
| ---- |
| 1003 |
| 2000 |
| 4000 |
| 5000 |