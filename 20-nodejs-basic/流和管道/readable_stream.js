const fs = require('fs');

// 创建一个可读流, highWaterMark指定缓存区大小，即一次读取的字节数
const readableStream = fs.createReadStream('流和管道/largeFile.txt', { highWaterMark: 1024 });

// 创建回调函数监听数据事件
readableStream.on('data', (chunk) => {
    console.log(`读取到数据块: ${chunk.length} 字节`);
});


// 监听文件读取完成事件
readableStream.on('end', () => {
    console.log('读取完成');
});

// 监听错误事件
readableStream.on('error', (err) => {
    console.error('读取出错:', err);
});

console.log('读取程序执行中')