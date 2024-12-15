const express = require('express');
const userRouter = require('./user.route'); // 用户相关路由
const orderRouter = require('./order.route'); // 订单相关路由
const orderDetailRouter = require('./orderDetail.route'); // 订单详情相关路由

const rootRouter = express.Router();
rootRouter.use('/user', userRouter);
rootRouter.use('/order', orderRouter);
rootRouter.use('/orderDetail', orderDetailRouter);

// 全局错误处理中间件
rootRouter.use((err, req, res, next) => {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error' });
});

module.exports = rootRouter;