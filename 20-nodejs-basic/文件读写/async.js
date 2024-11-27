const fs = require('fs');

// 异步读取文件
fs.readFile('./文件读写/test_read.txt', 'utf8', (err, data) => {
    if (err) {
        console.error('读取文件出错:', err);
    } else {
        console.log(data);
    }
});
console.log('文件读取已经发起，程序继续执行其他操作');

// 异步写入文件
const content = 'Hello Nodejs';
fs.writeFile('./文件读写/test_write.txt', content, 'utf8', (err) => {
    if (err) {
        console.error('写入文件出错:', err);
    } else {
        console.log('文件写入成功');
    }
});
console.log('文件写入已经发起，程序继续执行其他操作');

