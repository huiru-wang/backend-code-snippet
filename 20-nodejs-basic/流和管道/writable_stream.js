const fs = require('fs');

const writableStream = fs.createWriteStream('outputFile.txt');
const data = '这是要写入文件的内容';

for (let i = 0; i < 10; i++) {
    writableStream.write(data + '\n');
}

writableStream.end();

writableStream.on('finish', () => {
    console.log('写入完成');
});

writableStream.on('error', (err) => {
    console.error('写入出错:', err);
});