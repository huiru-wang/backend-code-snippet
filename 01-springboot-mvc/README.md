
# SpringBoot MVC
准备MySQL、Redis容器、数据库表:

[中间件配置](https://github.com/huiru-wang/backend-code-snippet/blob/main/00-basic/middleware-config.md)

## Restful API

```shell
src
├── main
│   ├── java
│   │   └── com.example.codesnippet
│   │               ├── controller/
│   │               │     └── UserController.java
│   │               │
│   │               ├── service/
│   │               │     └── UserService.java
│   │               │ 
│   │               ├── dao/
│   │               │     └── entity/
│   │               │     │      └── UserEntity.java
│   │               │     └── mapper/
│   │               │            └── UserMapper.java
│   │               │ 
│   │               ├── Application.java
│   └── resources
│       ├── application.properties
│       └── mapper
│            └── UserMapper.xml
```
后续配置都在：`application.properties`
- `server.`相关的配置可以设置：端口、SSL、服务器连接

```properties
spring.application.name=01-springboot-mvc
server.address=localhost
server.port=8080
```

### 1. GET请求
`controller/UserController.java`处理GET请求：
```java
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 处理路径参数：/api/user/123456
     */
    @GetMapping("/{id}")
    public ServiceResult<String> getUserById(@PathVariable String id) {
        return ServiceResult.success("userTest got id: [ " + id + " ]");
    }

    /**
     * 处理查询字符串参数：/api/user/query?userId={userId}&username={username}
     */
    @GetMapping("/query")
    public ServiceResult<String> query(@RequestParam("userId") String userId, @RequestParam("username") String username) {
        String queryParam = String.format("userId is %s username is %s.", userId, username);
        return ServiceResult.success(queryParam);
    }

    /**
     * 处理对象参数，同「查询字符串参数」自动将查询参数包装为对象：/api/user/queryByParam?userId={userId}&username={username}
     */
    @GetMapping("/queryByParam")
    public ServiceResult<String> query(UserQueryParam userQueryParam) {
        return ServiceResult.success(JSON.toJSONString(userQueryParam));
    }

    /**
     * 获取HttpServletRequest对象，取参数
     */
    @GetMapping("/one")
    public ServiceResult<String> list(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String username = request.getParameter("username");
        String queryParam = String.format("userId is %s username is %s.", userId, username);
        return ServiceResult.success(queryParam);
    }
}
```

### 2. POST请求

接受POST通常有`json`和`表单`2种：
```java
...

/**
 * 接受raw格式的json请求，由@RequestBody完成反序列化
 */
@PostMapping("/create")
public ServiceResult<UserCreateResponse> createUser(@RequestBody UserCreateRequest createRequest) {
    // TODO Create
    UserCreateResponse response = new UserCreateResponse();
    response.setUserId(IdUtils.UUID());
    response.setUsername(createRequest.getUsername());
    return ServiceResult.success(response);
}

@PostMapping("/login")
public String login(@RequestParam("userId") String userId, @RequestParam("username") String username) {
    // ....
}

....
```


## MyBatis ORM

引入`mybatis`和`mysql-connector`驱动

```xml
<!--====================  MyBatis MySQL  =====================-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.4</version>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

`application.properties`配置数据库连接信息：
```properties
# Database Config
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/code_snippet?useUnicode=true&characterEncoding=utf-8&userSSL=false
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.pool-name=code-snippet-db-pool
spring.datasource.hikari.connection-timeout=1000
mybatis.mapper-locations=classpath:mapper/*.xml     # 告知MyBatis扫描的mapper文件位置
```

创建`UserEntity`和`UserMapper`接口：
```java
@Data
public class UserEntity {

    private Integer id;

    private String userId;

    private String username;

    private String password;

    private String phoneNo;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

@Mapper
public interface UserMapper {

    /**
     * 插入
     */
    int insert(@Param("userEntity") UserEntity userEntity);

    /**
     * 查询
     */
    UserEntity selectByUserId(@Param("userId") String userId);
}
```

实现对应的SQL：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.codesnippet.dao.mapper.UserMapper">

    <!-- 定义ResultMap -->
    <resultMap id="UserEntityResultMap" type="com.example.codesnippet.dao.entity.UserEntity">
        <id property="id" column="id" jdbcType="INTEGER"/>
        <result property="userId" column="user_id" jdbcType="VARCHAR"/>
        <result property="username" column="username" jdbcType="VARCHAR"/>
        <result property="phoneNo" column="phone_no" jdbcType="VARCHAR"/>
        <result property="email" column="email" jdbcType="VARCHAR"/>
        <result property="createdAt" column="created_at" jdbcType="TIMESTAMP"/>
        <result property="updatedAt" column="updated_at" jdbcType="TIMESTAMP"/>
    </resultMap>

    <!-- 插入用户信息的SQL语句 -->
    <insert id="insert" keyProperty="id" useGeneratedKeys="true">
        INSERT INTO t_user (user_id, username, phone_no, email, created_at, updated_at)
        VALUES (#{userEntity.userId}, #{userEntity.username}, #{userEntity.phoneNo}, #{userEntity.email},
                #{userEntity.createdAt}, #{userEntity.updatedAt})
    </insert>

    <!-- 根据用户ID查询用户信息的SQL语句 -->
    <select id="selectByUserId" resultMap="UserEntityResultMap">
        SELECT id, user_id, username, phone_no, email, created_at, updated_at
        FROM t_user
        WHERE user_id = #{userId}
    </select>
</mapper>
```

## log4j2日志配置

排除SpringBoot默认的logback依赖，引入Log4j2日志依赖：
```xml
<!--====================  Rest  =====================-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion><!-- springboot默认是用logback的日志框架的 -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
        <exclusion>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
        </exclusion>
        <exclusion>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!--====================  log4j2  =====================-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.4</version>
</dependency>
```

指定配置文件：
```properties
logging.config=classpath:log4j2.xml
```

见：log4j2.xml

## 全局异常处理

通常会先定义一个自己的业务异常，和项目的异常错误枚举搭配使用：

枚举见：`com.example.codesnippet.enums.ErrorConstants`
```java
@Getter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorConstants errorConstants) {
        super(errorConstants.getErrorMessage());
        this.errorCode = errorConstants.getErrorCode();
    }
}
```

创建一个全局异常处理bean：`GlobalExceptionHandler`
- 使用：`@ExceptionHandler(BusinessException.class)`指定此方法要处理的异常类型
- 通常有这样几类：处理业务异常、处理参数异常、处理系统异常等

方法抛出异常后的路径 --> aop@AfterThrowing可处理异常 --> @RestControllerAdvice --> interceptor.afterCompletion()方法处理
```java

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常，指定捕获自定义的业务异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ServiceResult<Void> handleBusinessException(BusinessException e) {
        return ServiceResult.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     * 处理参数异常，指定捕获的相关的异常类型
     *
     * @param e IllegalArgumentException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    public ServiceResult<Void> handleIllegalArgument(Exception e) {
        log.error("IllegalArgument: ", e);
        StringBuilder errorMessage = new StringBuilder(ErrorConstants.PARAM_INVALID.getErrorMessage());
        errorMessage.append(": ");
        if (e instanceof MissingServletRequestParameterException ex) {

            // lombok 校验异常
            String parameterName = ex.getParameterName();
            errorMessage.append(parameterName);

        } else if (e instanceof IllegalArgumentException ex) {

            // Assert 校验异常
            String message = ex.getMessage();
            errorMessage.append(message);

        } else if (e instanceof MethodArgumentNotValidException ex) {

            // @Validate注解异常
            BindingResult bindingResult = ex.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                errorMessage.append("[").append(field).append("]: ").append(message);
            });
        }
        return ServiceResult.fail(ErrorConstants.PARAM_INVALID.getErrorCode(), errorMessage.toString());
    }

    /**
     * 兜底其他未知异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Throwable.class)
    public ServiceResult<Void> handleException(Throwable e) {
        log.error("Internal Server Error: ", e);
        return ServiceResult.fail(ErrorConstants.INTERNAL_SERVER_ERROR);
    }
}
```


## SpringBoot AOP

### 1. 正则匹配切面

使用正则表达式实现切面拦截所有controller的方法参数和返回值，记录日志：
```java
@Slf4j
@Aspect
@Component
public class HttpAccessLogAspect {

    /**
     * execution内就是一个正则匹配的方法声明
     * public：限定public方法
     * *：任意返回类型
     * com.snippet.spring.controller.*：指定此包下任意类
     * .*(..)：任意方法、任意参数
     */
    @Pointcut("execution(public * com.example.codesnippet.controller.*.*(..))")
    public void interfaceLog() {
    }

    @Around("interfaceLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Object response = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String interfaceMethod = signature.getMethod().getName();
        String uri = request.getRequestURI();
        String ip = request.getRemoteUser();
        String httpMethod = request.getMethod();
        Object requestBody = null;
        if ("GET".equals(httpMethod)) {
            requestBody = request.getParameterMap();
        } else if ("POST".equals(httpMethod)) {
            requestBody = joinPoint.getArgs();
        }
        // 接口日志的格式，通过HttpAccessLog.toString作为msg，根据log4j2.xml的配置pattern进行日志打印
        HttpAccessLog httpAccessLog = HttpAccessLog.builder()
                .ip(ip)
                .traceId(MDC.get(GlobalConstants.TRACE_ID))
                .path(uri)
                .interfaceName(interfaceMethod)
                .httpMethod(httpMethod)
                .timeCost(endTime - startTime)
                .request(requestBody)
                .response(response)
                .build();
        log.info("{}", httpAccessLog.toString());
        return response;
    }
}
```

### 2. 注解切面

TODO

定义一个注解，用于检验token
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessToken {
}
```

## Interceptor

一个请求的执行过程：
`请求到达 --> Filter --> Servlet --> Interceptor --> Controller --> Interceptor --> Filter --> 返回`

使用Filter为进入的请求添加traceId，方便后续链路追踪：
```java
@Slf4j
public class TraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String traceId = request.getHeader(GlobalConstants.X_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            MDC.put(GlobalConstants.TRACE_ID, IdUtils.UUID());
        } else {
            MDC.put(GlobalConstants.TRACE_ID, traceId);
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        String traceId = MDC.get(GlobalConstants.X_TRACE_ID);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
```

注册Interceptor
```java
@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/**");
    }
}
```

## SpringBoot Transaction

TODO

## SpringBoot Redis

### 1. Redis相关依赖

```xml
<!--====================  Redis  =====================-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```
### 2. Redis相关配置

首先配置链接相关的参数：
```properties
# Redis Config
spring.data.redis.host=127.0.0.1
spring.data.redis.port=6379
spring.data.redis.database=0
spring.data.redis.connect-timeout=1000
spring.data.redis.lettuce.pool.enabled=true
spring.data.redis.lettuce.pool.max-active=10
spring.data.redis.lettuce.pool.max-idle=10
spring.data.redis.lettuce.pool.min-idle=10
spring.data.redis.lettuce.pool.max-wait=100ms
```

配置Spring自带的RedisTempalte：`config/RedisTemplateConfig.java`
```java
@Configuration
@EnableCaching
public class RedisTemplateConfig {
    
    /**
     * 连接池工厂
     */
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 配置redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        ObjectMapper objectMapper = createObjectMapper();

        // 设置key的序列化方式
        template.setKeySerializer(createStringRedisSerializer());
        // 设置value的序列化方式
        template.setValueSerializer(createJackson2JsonRedisSerializer(objectMapper));

        template.setHashKeySerializer(createStringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        // 设置连接池
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );
        return objectMapper;
    }

    private Jackson2JsonRedisSerializer<Object> createJackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    private StringRedisSerializer createStringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * 以下的Bean声明，仅是将RedisTemplate下的各个数据结构的操作Bean取出来，方便使用
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, String> listOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, String> setOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, String> zSetOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForZSet();
    }
}
```

### 3. 各个数据类型使用方式

这部分，在Test中使用，见：`test/java/com.example.codesnippet.cache`


## MessageQueue

TODO 