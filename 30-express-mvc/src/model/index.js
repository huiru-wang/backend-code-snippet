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