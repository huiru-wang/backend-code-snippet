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