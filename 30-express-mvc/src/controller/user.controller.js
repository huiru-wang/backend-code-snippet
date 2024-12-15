const userModel = require("../model").userModel;
const getUserById = async (req, res) => {
    const userId = req.params.userId;
    userModel.findOne({ where: { userId: userId } }).then(user => {
        if (user) {
            res.send(user);
            return;
        }
        return res.status(404).send({ code: 100001, message: 'User not found' });
    }).catch(error => {
        return res.status(500).send({ code: 100000, message: 'Internal server error' });
    });
};

const addUser = async (req, res) => {
    const body = req.body;
    const timestamp = Date.now();
    const userId = `${timestamp}${Math.floor(Math.random() * 10000)}`;
    const newUser = {
        userId: userId,
        username: body["username"],
        password: body["password"],
        phoneNo: body["phoneNo"],
        email: body["email"],
        createdAt: timestamp,
        updatedAt: timestamp
    };
    userModel.create(newUser).then(result => {
        return res.status(200).json(result);
    }).catch(error => {
        return res.status(500).send({ code: 100000, message: 'Internal server error' });
    });
};

module.exports = {
    getUserById,
    addUser
};