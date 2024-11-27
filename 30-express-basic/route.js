const express = require('express');
const url = require('url');
const userManager = require('./data');
const userRouter = express.Router();

// 处理用户路由

// 用户基本信息管理路由
userRouter.get('/', (req, res) => {
    const userId = url.parse(req.url, true).query.id;
    if (userId) {
        const user = userManager.getUserById(parseInt(userId, 10));
        if (user) {
            res.send(user);
        } else {
            return res.status(404).send({ message: 'User not found' });
        }
        return;
    }
    res.send(users);
});

// 路径参数
userRouter.get('/:userId', (req, res) => {
    const userId = req.params.userId;
    const user = userManager.getUserById(parseInt(userId, 10));
    if (user) {
        res.send(user);
        return;
    }
    return res.status(404).send({ message: 'User not found' });
});

// 解析json，创建用户
userRouter.post('/create', (req, res) => {
    const body = req.body
    const users = userManager.getAllUsers();
    const userId = users[users.length - 1].id + 1;
    const newUser = {
        id: userId,
        name: body["name"],
        email: body["email"]
    };
    userManager.addUser(newUser)
    res.send(newUser);
});

// 删除用户
userRouter.delete('/:userId', (req, res) => {
    const userId = req.params.userId;
    userManager.removeUserById(parseInt(userId, 10));
    res.send({
        success: true,
        userId: userId
    });
});

module.exports = userRouter;