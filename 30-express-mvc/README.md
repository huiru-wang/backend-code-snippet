
## 1. 准备项目
```shell
cd 30-express-basic
pnpm init
pnpm install express sequelize mysql2 cors body-parser
```

```shell
pnpm install --save mysql2 # MySQL
pnpm install --save pg pg-hstore # Postgres
```

添加启动脚本
```json
{
  "name": "30-express-basic",
  "version": "1.0.0",
  "description": "",
  "main": ".dist/main.js",
  "scripts": {
    "dev": "node src/main.js",
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "dependencies": {
    "body-parser": "^1.20.3",
    "cors": "^2.8.5",
    "express": "^4.21.1",
    "mysql2": "^3.11.5",
    "sequelize": "^6.37.5"
  }
}
```

最终完整的项目工程目录结构：
```shell
├── 30-express-basic\
│   ├── src\
│   │   ├── main.ts                     # 应用入口
│   │   ├── config\                     # 配置文件相关
│   │   │    └── db.config.js           # db配置文件
│   │   │
│   │   ├── routes\                     # 用户路由文件
│   │   │    └── index.js              
│   │   │    └── user.route.js         
│   │   │    └── order.route.js       
│   │   │    └── orderDetail.route.js
│   │   │
│   │   ├── controller\                 # controller相关
│   │   │    └── index.js        
│   │   │    └── user.controller.js  
│   │   │    └── order.controller.js   
│   │   │    └── orderDetail.controller.js  
│   │   │
│   │   ├── model\                      # model数据库相关
│   │   │    └── index.js        
│   │   │    └── user.model.js  
│   │   │    └── order.model.js   
│   │   │    └── orderDetail.model.js  

│   ├── package.json         
│   ├── .gitignore           
│   └── README.md           
```

## 2. 启动服务

创建index.js：
```js
const express = require('express');
const bodyParser = require('body-parser');

const app = express();

// REST API Routes
app.get("/", (req, res) => {
    res.json({ message: "Hello Express" });
});

// Server listen
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Server is running on: http://localhost:${PORT}`);
});
```

启动并访问：http://localhost:3000/
```shell
pnpm dev
```

## 3. Sequelize ORM 配置MySQL

### 3.1 DB配置文件
`src/config/db.config.js`添加一个配置文件
```js
module.exports = {
  mysql: {
    host: process.env.MYSQL_HOST || '127.0.0.1',
    username: process.env.MYSQL_USERNAME || 'root',
    password: process.env.MYSQL_PASSWORD || 'root',
    database: process.env.MYSQL_DATABASE || 'code_snippet',
    port: process.env.MYSQL_PORT || 3306,
    pool: {
      max: process.env.MYSQL_POOL_MAX_CON || 5,
      min: process.env.MYSQL_POOL_MIN_CON || 0,
      acquire: 30000,
      idle: 10000
    }
  }
}
```

### 3.2 模型配置
以`t_user`为例，参考官方文档：
- [sequelize-模型基础](https://www.sequelize.cn/core-concepts/model-basics)
- [sequelize-数据类型](https://www.sequelize.cn/core-concepts/model-basics#%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B)
```js
module.exports = (sequelize, Sequelize) => {
    const User = sequelize.define(
        'User',
        {
            // 最好指明数据库字段名，否则默认为驼峰命名
            id: {
                type: Sequelize.INTEGER,
                primaryKey: true,
                autoIncrement: true,
                field: 'id'
            },
            userId: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'user_id'
            },
            username: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'username'
            },
            ....
        },
        {
            // 索引相关，需指明索引名字，否则自动创建索引名
            tableName: 't_user',
            indexes: [
                {
                    unique: true,
                    name: 'uk_user_id',
                    fields: ['user_id']
                },
                {
                    unique: true,
                    name: 'uk_username',
                    fields: ['username']
                }
            ],
        }
    );
    // 关联
    User.associate = function (models) { }
    return User;
}
```

### 3.4 sequelize初始化

从`src/config/db.config.js`获取配置文件，创建`Sequelize`对象，然后初始化每个`Model`，放入`db`对象中对外暴漏：
```js
const { Sequelize } = require('sequelize');
const database = require("../config/db.config.js")
const userModel = require("./user.model.js")
const orderModel = require("./order.model.js")
const orderDetailModel = require("./orderDetail.model.js")

// 获取mysql的配置
const mysqlConfig = database.mysql;

// Connecting to a database
const sequelize = new Sequelize(
    mysqlConfig.database,
    mysqlConfig.username,
    mysqlConfig.password,
    {
        host: mysqlConfig.host,
        port: mysqlConfig.port,
        dialect: 'mysql',
        logging: (msg) => { console.log('mysql msg', msg); },
        pool: {
            max: mysqlConfig.pool.max,
            min: mysqlConfig.pool.min,
            acquire: mysqlConfig.pool.acquire,
            idle: mysqlConfig.pool.idle,
        },
        timezone: '+08:00'
    });

// Test the connection
try {
    sequelize.authenticate();
    console.log('Connection has been established successfully.');
} catch (error) {
    console.error('Unable to connect to the database:', error);
}

// 创建数据库对象
const db = {};
db.Sequelize = Sequelize;
db.sequelize = sequelize;

db.userModel = userModel(sequelize, Sequelize);
db.orderModel = orderModel(sequelize, Sequelize);
db.orderDetailModel = orderDetailModel(sequelize, Sequelize);

module.exports = db;
```

在`main.js`中执行db的同步操作：
- 如果没有表，则自动创建的对应的表、索引等；
```js
...

// 初始化数据库
db.sequelize.sync()
    .then(() => {
        console.log("Synced db.");
    })
    .catch((err) => {
        console.log("Failed to sync db: " + err.message);
    });

...
```

## 4. Controller

项目比较简单，就直接Controller层来处理业务逻辑；

在Controller中进行`ORM CRUD`操作；具体详见代码：`src/controller/user.controller.js`

CRUD参考：
- [sequelize-模型查询(基础)](https://www.sequelize.cn/core-concepts/model-querying-basics)
- [sequelize-模型查询(查找器)](https://www.sequelize.cn/core-concepts/model-querying-finders)

## 5. 模块化路由

1. 对路由进行嵌套；
2. 不同文件处理不同的路由分组；

```shell
├── 30-express-basic\
│   ├── src\
│   │   ├── main.ts                     # 应用入口
│   │   ├── config\                     # 配置文件相关
│   │   │    └── db.config.js           # db配置文件
│   │   │
│   │   ├── routes\                     # 用户路由文件
│   │   │    └── index.js              
│   │   │    └── user.route.js         
│   │   │    └── order.route.js       
│   │   │    └── orderDetail.route.js
```

以`user`为例，创建`user.route.js`，将`controller`绑定到对应的路由上：
```js
const express = require('express');
const { userController } = require('../controller/index');

const userRouter = express.Router();
userRouter.get('/:userId', userController.getUserById);
userRouter.post('/add', userController.addUser);

module.exports = userRouter;
```

`src/routes/index.js`中，将多个路由文件引入，并挂载到对应的路由上：
```js
const express = require('express');
const userRouter = require('./user.route');
const orderRouter = require('./order.route');
const orderDetailRouter = require('./orderDetail.route');

const rootRouter = express.Router();
rootRouter.use('/user', userRouter);
rootRouter.use('/order', orderRouter);
rootRouter.use('/orderDetail', orderDetailRouter);

module.exports = rootRouter;
```

最终挂在到`main.ts`中：
```js
const express = require('express');
const bodyParser = require('body-parser');
const rootRouter = require('./routes');
const db = require('./model');

const app = express();

// 初始化数据库
db.sequelize.sync()
    .then(() => {
        console.log("Synced db.");
    })
    .catch((err) => {
        console.log("Failed to sync db: " + err.message);
    });

// enable cors
app.all('*', function (req, res, next) {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', '*');
    res.header('Access-Control-Allow-Methods', '*');
    next();
});

// content-type: application/json
app.use(bodyParser.json());

// content-type: application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// 静态服务，直接访问public下静态文件: http://localhost:8080/public/girl.jpg
// app.use('/public', express.static(path.join(__dirname, 'public')));

// REST API Routes
app.get("/", (req, res) => {
    res.json({ message: "Hello Express" });
});
app.use('/api', rootRouter);

// TODO 全局异常处理

// Server listen
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Server is running on: http://localhost:${PORT}`);
});
```
