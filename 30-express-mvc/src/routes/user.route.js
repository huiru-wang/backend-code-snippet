const express = require('express');
const { userController } = require('../controller/index');

const userRouter = express.Router();
userRouter.get('/:userId', userController.getUserById);
userRouter.post('/add', userController.addUser);

module.exports = userRouter;