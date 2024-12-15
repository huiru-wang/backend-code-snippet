const express = require('express');
const { orderDetailController } = require('../controller/index');

const orderDetailRouter = express.Router();
orderDetailRouter.get('/:orderId', orderDetailController.getOrderDetails)

module.exports = orderDetailRouter;