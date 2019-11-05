
坑：1 springboot版本要与gateway一致



路由规则

  routes:
        - id: MEMBER-SERVICE
          uri: lb://member-service
          predicates:
            # 路径匹配，以 api 开头，直接配置是不生效的，看 filters 配置
            - Path=/gateway/**
          filters:
            # 前缀过滤，默认配置下，我们的请求路径是 http://localhost:9000/myshop-service-consumer-item/** 这时会路由到指定的服务
            # 此处配置去掉 1 个路径前缀，再配置上面的 Path=/api/**，就能按照 http://localhost:9000/api/** 的方式访问了
            - StripPrefix=1

访问http://localhost:9000/gateway/member/weixin 会转发到 member-service 服务http://localhost:8081/member/weixin

