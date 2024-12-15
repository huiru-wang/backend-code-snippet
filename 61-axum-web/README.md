# rust-basic
https://course.rs/about-book.html
## 创建项目

```shell
cargo new [project-name]
```

项目结构
```shell
src/
  |-- main.rs
Cargo.toml
Cargo.lock
```

使用`mod`引入同一个模块的其他文件
```rs
mod variable;

fn main() {
    variable::test_variable();
}

```

## 变量

可变性
- 变量在默认情况下是不可变的
- 可以通过使用 `mut` 关键字来创建可变变量
```rs
let x = 5;
println!("The value of x is: {}", x);

// x = 6;      // 尝试修改 x, 会报错

// 可以通过使用 `mut` 关键字来创建可变变量
let mut y = 5;
y = 6;
println!("The value of y is: {}", y);
```

常量
- 在 Rust 中常量必须使用大写字母命名
- 并且必须使用 `:` 指定类型
- 常量自始至终都不可变
```rs
const MAX_POINTS: i32 = 100_000;
println!("The value of MAX_POINTS is: {}", MAX_POINTS);
```

变量遮蔽与作用域
- 前2句：2个变量名称一样，但是内存确是不同的，是2个不同的内存地址，会释放掉之前的内存，再分配一个新的内存；
- 花括号内：作用域内的变量遮蔽了外部的变量，内外的2个z，是不同的引用，指向不同的内存，因此花括号结束后，内部z所指向的内存会释放，外部的z不受影响
```rs
let z = 5;
let z = z + 1;
{
    let z = z + 7;
    println!("The value of z is: {}, referece is {:p}", z, &z);   // 0xb9b814f414
}
println!("The value of z is: {}, referece is {:p}", z, &z);  // 0xb9b814f410
```


## 数据类型

### 数值

### 布尔

### 字符

### 切片与字符串

### 数组

### 元组

### 结构体

### 枚举

