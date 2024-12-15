module.exports = {
  mysql: {
    host: process.env.MYSQL_HOST || '127.0.0.1',
    username: process.env.MYSQL_USERNAME || 'root',
    password: process.env.MYSQL_PASSWORD || 'root',
    database: process.env.MYSQL_DATABASE || 'code_snippet',
    port: process.env.MYSQL_PORT || 3306,
    pool: {
      max: process.env.MYSQL_POOL_MAX_CON || 5,
      min: process.env.MYSQL_POOL_MIN_CON || 0,
      acquire: 30000,
      idle: 10000
    }
  }
}