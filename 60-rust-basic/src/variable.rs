pub fn test_variable() {
    println!("============= rust variable =============");
    /**
     1. 变量的基本能使用
    */
    // Rust 的变量在默认情况下是不可变的
    let x = 5;
    println!("The value of x is: {}", x);

    // x = 6;      // 尝试修改 x, 会报错

    // 可以通过使用 `mut` 关键字来创建可变变量
    let mut y = 5;
    println!("The value of y is: {}", y);
    y = 6;
    println!("The value of y is: {}", y);

    // 常量,
    // 1. 在 Rust 中常量必须使用大写字母命名
    // 2. 并且必须使用 `:` 指定类型
    // 3. 常量自始至终都不可变
    const MAX_POINTS: i32 = 100_000;
    println!("The value of MAX_POINTS is: {}", MAX_POINTS);

    // shadowing变量遮蔽，在同一个作用域无需再使用之前的变量，可以直接使用，仅仅是为了方便开发者，不需要定义新的名字的变量
    // 1. 前2句：2个变量名称一样，但是内存确是不同的，是2个不同的内存地址，会释放掉之前的内存，再分配一个新的内存；
    // 2. 花括号内：作用域内的变量遮蔽了外部的变量，内外的2个z，是不同的引用，指向不同的内存，因此花括号结束后，内部z所指向的内存会释放，外部的z不受影响
    let z = 5;
    let z = z + 1;
    {
        let z = z + 7;
        println!("The value of z is: {}, referece is {:p}", z, &z);
    }
    println!("The value of z is: {}, referece is {:p}", z, &z);
}
