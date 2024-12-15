const { getUserById, addUser } = require('./user.controller');
const { getUserOrders, getOrderById } = require('./order.controller');
const { getOrderDetails } = require('./orderDetail.controller');

const controller = {
    userController: {
        getUserById,
        addUser
    },
    orderController: {
        getUserOrders,
        getOrderById
    },
    orderDetailController: {
        getOrderDetails
    },
}

module.exports = controller;