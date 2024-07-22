### 模块名

通天桥 (Tongtian Bridge)

### 模块简介

🌉 通天桥象征着连接天与地的桥梁，寓意服务之间的通信与调用，通畅无阻。通天桥是一个自定义的服务通讯中间件，旨在提供高效、稳定的服务间调用解决方案。通过通天桥，各个微服务可以实现无缝对接，确保信息传递的准确性和及时性，从而提升系统整体的运行效率和可靠性。

### 依赖

在项目的 pom.xml 文件中，添加以下依赖以引入通天桥模块：

```xml
<dependency>
   <groupId>com.virtual.luna</groupId>
   <artifactId>luna-starter-web</artifactId>
   <version>${revision}</version>
</dependency>
```

### 配置

在项目的 application.yaml 文件中，添加以下配置来设置服务注册发现和配置中心的相关参数：

```yaml
# ======== 服务注册发现 ========
service:
  # 服务名
  name: 测试Demo-A
  # 服务唯一标识
  key: test-demo-a
  # 是否启用注册
  enabled: true
  # 健康检查配置
  health_check:
    # 健康检查接口路径
    endpoint: /healthy
    # 健康检查间隔时间（毫秒）
    interval_ms: 10000
    # 地址获取模式: config（手动配置）, client（客户端自动获取）, server（服务端自动获取）
    address_mode: server
    # 健康检查地址，当 address_mode 为 config 时使用
    address: 172.17.0.231
    # 健康检查端口
    port: ${server.port}
    # 是否启用健康检查
    enabled: true
  # 注册配置
  registration:
    # 基础 url
    base: http://172.17.0.207:11000
    # 注册服务的URL
    url: ${service.registration.base}/server-register
    # 通讯 url
    forward: http://127.0.0.1:11000/remote/forward
    # 注册间隔时间（毫秒）
    interval_ms: 30000
    # 最大注册实例数
    max_instances: 5
  # 服务端获取配置
  config-addr: ${service.registration.base}/server-config/${service.key}
  config-enabled: false
```

### 使用示例

#### 1、服务B 定义Restful Controller接口

在服务B中定义Restful Controller接口，例如：

```java
@RestController
@RequestMapping("/test")
public class BaPoController {

    /**
     * 测试 Path 请求
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String testPath(@PathVariable("id") Long id)  {
        return "Hello Test";
    }

    /**
     * RequestParam 测试
     * @return
     */
    @GetMapping("/requestParam")
    public String testRequestParam(@RequestParam String a,
                                   @RequestParam String b){
        return a + b;
    }

    /**
     * RequestBody 测试
     * @return
     */
    @PostMapping("/requestBody")
    public String testRequestBody(@RequestBody HashMap<String,Object> map){
        String jsonString = JSON.toJSONString(map.get("a"));
        return jsonString;
    }
}
```

#### 2、服务A 定义接口。

在服务A中使用自定义注解 @TongtianBridge("xxx") 进行定义，其中 xxx 为B的服务名。

```java
@TongtianBridge("test-demo-b")
public interface BaPoInterfaces {

    /**
     * PathVariable 测试
     * @param id
     * @return
     */
    @GetMapping("/test/{id}")
    public String testPathVariable(@PathVariable("id") Long id);

    /**
     * RequestParam 测试
     * @return
     */
    @GetMapping("/test/requestParam")
    public String testRequestParam(@RequestParam String a,
                                   @RequestParam String b);

    /**
     * RequestBody 测试
     * @return
     */
    @PostMapping("/test/requestBody")
    public String testRequestBody(@RequestBody HashMap<String,Object> map);


}
```

#### 3、服务A调用服务B

在服务A中调用服务B的接口，可以通过注入 TongtianFactory 来创建远程服务实例，并调用其方法。例如：

```java
@SpringBootTest
public class BaPoTestController {

    @Autowired
    private TongtianFactory tongtianFactory;

    @Test
    public void testPath() {
        BaPoInterfaces remoteService = (BaPoInterfaces) tongtianFactory.create(BaPoInterfaces.class);

        // 测试 GetMapping  Path
        String result = remoteService.testPathVariable(12312L);

        System.out.println(result);

        // 测试 GetMapping RequestParam
        String s = remoteService.testRequestParam("1111", "2222");

        System.out.println(s);

        // 测试 PostMapping RequestBody
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", new HashMap<String, Object>() {
            {
                put("222", "333");
            }
        });
        String s1 = remoteService.testRequestBody(map);
        System.out.println(s1);
    }
}


```


### 核心功能

🌐 注册发现与配置

通天桥的核心功能包括服务注册、服务发现、健康检查和配置管理。通过配置 application.yaml 文件，可以灵活地管理服务的注册和发现机制。

🩺 健康检查

健康检查机制确保服务的可用性，通过定期的健康检查请求，通天桥可以监控各个服务的状态，并在发现服务不可用时进行相应的处理。

🔄 动态代理和远程调用

通天桥使用动态代理机制，通过 @TongtianBridge 注解，可以方便地定义和调用远程服务。此机制类似于Feign，实现了服务之间的无缝通信。