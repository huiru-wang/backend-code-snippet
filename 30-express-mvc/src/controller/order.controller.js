const orderModel = require("../model").orderModel;

const getUserOrders = async (req, res) => {
    orderModel.findAll({
        where: {
            userId: req.params.userId
        }
    }).then((orders) => {
        if (orders) {
            res.send(orders);
            return;
        }
        return res.status(200).send({ code: 200001, message: 'user has no orders' });
    }).catch(error => {
        return res.status(500).send({ code: 100000, message: 'Internal server error' });
    });
};

const getOrderById = async (req, res) => {
    orderModel.findOne({
        where: {
            orderId: req.params.orderId
        }
    }).then((order) => {
        if (order) {
            res.send(order);
            return;
        }
        return res.status(200).send({ code: 200002, message: 'order not found' });
    }).catch(error => {
        return res.status(500).send({ code: 100000, message: 'Internal server error' });
    });
};

module.exports = {
    getUserOrders,
    getOrderById
};