
## 1. Init
```shell
cd 30-express-basic
pnpm init
pnpm add express 
```

添加启动脚本
```json
{
  "name": "30-express-basic",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "dev": "node index.js",
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "dependencies": {
    "express": "^4.21.1"
  }
}
```

## 2. 启动服务

创建index.js：
```js
const express = require('express');
const app = express();

// 使用中间件，用于解析POST请求中的JSON数据
app.use(express.json());

// 定义一个简单的路由，处理根路径（'/'）的GET请求
app.get('/', (req, res) => {
    res.send('Hello, this is an Express project using pnpm!');
});

// 让服务器监听在3000端口上
app.listen(3000, () => {
    console.log('Server started on port 3000');
});
```

启动并访问：http://localhost:3000/
```shell
pnpm dev
```


## 3. 添加路由

```js
const express = require('express');
const app = express();
const userRouter = express.Router();

userRouter.get('/', (req, res) => {

});

userRouter.post('/create', (req, res) => {

});

// 添加路由组
app.use('/users', userRouter);

app.listen(3000, () => {
    console.log('Server started on port 3000');
});
```


## 4. 增加数据处理（模拟ORM）
创建`data.js`，并创建`UserDataManager`类，用于使用CRUD处理用户数据。
```js
const userData = [
    {
        id: 1,
        name: 'John Doe',
        email: 'john@example.com'
    },
    {
        id: 2,
        name: 'Jane Doe',
        email: 'jane@example.com'
    }
];

class UserDataManager {
    constructor(data) {
        this.data = data;
    }

    // 获取所有用户数据
    getAllUsers() {
        return this.data;
    }

    // 根据ID获取用户数据
    getUserById(id) {
        const user = this.data.find(user => user.id === id);
        if (!user) {
            return null;
        }
        return user;
    }

    // 添加用户数据
    addUser(user) {
        if (!user || !user.id || !user.name || !user.email) {
            throw new Error('Invalid user data');
        }
        this.data.push(user);
    }

    // 删除用户数据
    removeUserById(id) {
        this.data = this.data.filter(user => user.id !== id)
        console.log(this.data)
    }
}
const userManager = new UserDataManager(userData);
module.exports = userManager;
```

## 4. 中间件

引入json解析中间件
```shell
pnpm add body-parser
```

```js
const bodyParser = require('body-parser');

// ...
app.use(bodyParser.json());
```

创建一个post请求路由，处理request中的json数据
```js
const express = require('express');
const bodyParser = require('body-parser');
const url = require('url');
const userManager = require('./data');
const app = express();
const userRouter = express.Router();

// ...

// 解析json，创建用户
userRouter.post('/create', (req, res) => {
    const users = userManager.getAllUsers();
    const userId = users[users.length - 1].id + 1;

    // 解析body数据
    const body = req.body
    const newUser = {
        id: userId,
        name: body["name"],
        email: body["email"]
    };
    userManager.addUser(newUser)
    res.send(newUser);
});

// 将子路由挂载到主路由上
app.use(bodyParser.json());
app.use('/users', userRouter);

app.listen(3000, () => {
    console.log('Server started on port 3000');
});

```


## 5. 模块分离

```shell
30-express-basic
      |-- data.js
      |-- index.js
      |-- route.js
      |-- pacakage.json
```

`index.js`中引入`route.js`的`userRouter`
```js
// index.js
const express = require('express');
const bodyParser = require('body-parser');
const app = express();
// 引入路由文件
const userRouter = require('./route');

// 将子路由挂载到主路由上
app.use(bodyParser.json());
app.use('/users', userRouter);

app.listen(3000, () => {
    console.log('Server started on port 3000');
});
```