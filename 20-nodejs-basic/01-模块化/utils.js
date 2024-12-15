// utils.js
function add(a, b) {
    return a + b;
}
function subtract(a, b) {
    return a - b;
}

// export 来暴露函数
module.exports = {
    add: add,
    subtract: subtract
};