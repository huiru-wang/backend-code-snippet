

## 1. Installation



## Nodejs Feathers

- V8 Engine Runtime
- 单线程
- 非阻塞IO模型
- 事件驱动（回调函数）

Nodejs处理高并发请求 = 非阻塞IO + 事件循环机制

- 优点：适合处理IO密集型业务；
- 缺点：不适合CPU密集型业务；

## 2. Global Object

Console



## 3. Callback Function


## 4. 模块化

ES5及以前，是没有模块的概念的；在HTML中引入js文件，通常是单个文件，如果引入多个文件，就会涉及命名冲突的问题。通常会利用函数和闭包来模拟模块的概念，隐藏内部的变量、暴漏允许访问的部分，以此来实现封装；

模块化：一种将代码组织成独立单元（模块）的方式；将复杂工程的代码分解成若干个模块，每个模块只负责完成一个功能，这样便于维护和复用。

### Commonjs

```js
// utils.js
function add(a, b) {
    return a + b;
}
function subtract(a, b) {
    return a - b;
}

// export 来暴露函数
module.exports = {
    add: add,
    subtract: subtract
};
```

```js
// index.js
// 引入特定的模块
const { add, subtract } = require('./utils.js');

// 使用
const addResult = add(1, 2);
const subResult = subtract(1, 2);

console.log(addResult);
console.log(subResult);
```
或
```js
// main.js
// 引入特定的模块
const utils = require('./utils.js');

// 使用
const addResult = utils.add(1, 2);
const subResult = utils.subtract(1, 2);

console.log(addResult);
console.log(subResult);
```

## 5. 事件
https://nodejs.org/docs/latest/api/events.html



## 6. 文件读写




## 7. 流和管道

流可以看作是数据的管道，数据以块（chunk）的形式在管道中流动。

流有四种基本类型：
- 可读流（Readable Stream）
- 可写流（Writable Stream）
- 双工流（Duplex Stream）
- 转换流（Transform Stream）


## 8. Web Server




## 9. npm包管理

包管理工具的作用：
- 依赖管理、版本控制；
- 自动化构建；
- 项目结构和配置管理；
- 分离不同环境的依赖：开发、生产；
- 生态的促进；模块的发布、共享；

使用npm创建一个工程
```shell
npm init
```

package.json文件：
```json
{
    "name": "my-full-stack-app",
    "version": "1.0.0",
    "author": "Jane Smith <jane.smith@example.com>",
    "license": "MIT",
    "description": "A full - stack application for managing a simple to - do list",
    "main": "server/index.js",
    "scripts": {
        "start": "node server/index.js",
        "build": "webpack --config webpack.config.js",
        "test": "jest",
        "lint": "eslint. --ext.js,.jsx",
        "prettify": "prettier --write. "
    },
    "dependencies": {
        "express": "^4.18.2",
        "mongoose": "^6.7.2",
        "react": "^18.2.0",
        "react - dom": "^18.2.0"
    },
    "devDependencies": {
        "jest": "^29.6.1",
        "eslint": "^8.40.0",
        "eslint - plugin - react": "^7.32.2",
        "prettier": "^2.8.8",
        "webpack": "^5.76.1",
        "webpack - cli": "^5.0.1",
        "babel - loader": "^9.1.2",
        "@babel/core": "^7.20.12",
        "@babel/preset - react": "^7.18.6"
    },
    "repository": {
        "type": "git",
        "url": "https://github.com/jane - smith/my - full - stack - app.git"
    },
    "bugs": {
        "url": "https://github.com/jane - smith/my - full - stack - app/issues"
    }
}
```
- name：项目名称，npm仓库的唯一标识，用于区分不同的项目；
- version：版本号，用于标识项目的不同版本；
- author：项目作者，用于标识项目是谁创建的；
- license：项目许可证，用于标识项目的版权信息；
- description：项目描述，用于描述项目的功能和作用；
- main：项目的入口文件，用于启动项目；
- scripts：项目的脚本，用于执行各种任务，如启动项目、构建项目、测试项目等；
- dependencies：项目的依赖包，用于标识项目的依赖包，包括运行时依赖包和开发依赖包；
- devDependencies：项目的开发依赖包，用于标识项目的开发依赖包，包括测试框架、代码规范工具等；
- repository：项目的仓库信息，用于标识项目的仓库地址；


## 10. nodemon

nodemon是一个基于nodejs的后台进程，当检测到文件修改时，自动重启nodejs进程。

