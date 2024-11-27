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
