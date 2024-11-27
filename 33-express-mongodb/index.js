const express = require('express');
const app = express();

// 使用express内置的中间件来处理静态文件，这里指定public目录为静态资源目录
app.use(express.static('public'));

// 启动服务器并监听3000端口
app.listen(3000, () => {
    console.log('静态文件服务器已启动，正在监听3000端口');
});