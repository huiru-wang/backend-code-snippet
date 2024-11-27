const fs = require('fs');

// 读取文件
try {
    const data = fs.readFileSync('./文件读写/test_read.txt', 'utf8');
    console.log(data);
} catch (err) {
    console.error('读取文件出错:', err);
}
console.log('文件读取完成');


// 写入文件
const content = 'Hello Nodejs';
try {
    fs.writeFileSync('./文件读写/test_write.txt', content, 'utf8');
} catch (err) {
    console.error('写入文件出错:', err);
}
console.log('文件写入完成');