const orderDetailModel = require("../model").orderDetailModel;

const getOrderDetails = async (req, res) => {
    orderDetailModel.findOne({
        where: {
            orderId: req.params.orderId
        }
    }).then((orderDetail) => {
        if (orderDetail) {
            res.send(orderDetail);
            return;
        }
        return res.status(200).send({ code: 300001, message: 'order detail not found' });
    }).catch(error => {
        return res.status(500).send({ code: 100000, message: 'Internal server error' });
    });
};

module.exports = {
    getOrderDetails
};