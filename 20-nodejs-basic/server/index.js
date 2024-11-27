const http = require('http');
const fs = require('fs');

// 创建服务器实例
const server = http.createServer((req, res) => {
    if (req.url === '/') {
        // 读取index.html文件
        fs.readFile('server/index.html', (err, data) => {
            if (err) {
                // 如果读取文件出错，返回500状态码及错误信息
                res.writeHead(500, { 'Content-Type': 'text/plain' });
                res.end('Internal Server Error: ' + err.message);
            } else {
                // 如果读取成功，设置响应头为HTML类型，返回200状态码并发送文件内容
                res.writeHead(200, { 'Content-Type': 'text/html' });
                res.end(data);
                console.log('index.html文件已发送')
            }
        });
    } else {
        // 读取404.html文件
        fs.readFile('server/404.html', (err, data) => {
            if (err) {
                // 如果读取文件出错，返回500状态码及错误信息
                res.writeHead(500, { 'Content-Type': 'text/plain' });
                res.end('Internal Server Error: ' + err.message);
            } else {
                // 如果读取成功，设置响应头为HTML类型，返回200状态码并发送文件内容
                res.writeHead(404, { 'Content-Type': 'text/html' });
                res.end(data);
                console.log('404.html文件已发送')
            }
        });
    }
});

// 监听3000端口
server.listen(3000, () => {
    console.log('服务器已启动，正在监听3000端口');
});