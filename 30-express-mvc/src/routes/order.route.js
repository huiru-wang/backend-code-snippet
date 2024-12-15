const express = require('express');
const { orderController } = require('../controller/index');

const orderRouter = express.Router();
orderRouter.get('/:userId', orderController.getUserOrders);
orderRouter.get('/:orderId', orderController.getOrderById);

module.exports = orderRouter;