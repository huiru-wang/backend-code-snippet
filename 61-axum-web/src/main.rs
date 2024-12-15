use axum::{
    extract::{Json, Path},
    response,
    routing::{get, post},
    Router,
};
use serde::{Deserialize, Serialize};
use std::net::SocketAddr;

// 定义一个结构体用于接收POST请求中的JSON数据
#[derive(Deserialize, Serialize)]
struct PostData {
    message: String,
}

// 定义一个结构体用于作为GET请求路径参数对应的类型
#[derive(Deserialize)]
struct GetParams {
    id: u32,
}

// 处理GET请求的函数，这里简单返回一个包含路径参数的字符串响应
async fn handle_get(Path(params): Path<GetParams>) -> String {
    format!("Received GET request with id: {}", params.id)
}

// 处理POST请求的函数，打印接收到的JSON数据，并返回一个确认消息
async fn handle_post(Json(payload): Json<PostData>) -> response::Json<PostData> {
    let message = format!("Received POST Data: {}", payload.message);
    let response = PostData { message };
    return response::Json(response);
}

#[tokio::main]
async fn main() {
    // 创建路由
    let app = Router::new()
        // 绑定GET请求的路由，路径参数为 `id`
        .route("/api/get/:id", get(handle_get))
        .route("/api/post", post(handle_post));

    // 定义服务器监听的地址和端口
    let addr = SocketAddr::from(([127, 0, 0, 1], 8080));
    println!("Listening on {}", addr);
    // 启动服务器
    axum::Server::bind(&addr)
        .serve(app.into_make_service())
        .await
        .unwrap();
}
