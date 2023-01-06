# Elasticsearch_spring_api
Elasticsearch Spring Boot API Build  
Spring Boot에 Elasticsearch(Rest High Level Client) API를 이용해 REST API를 구현  

추가한 API를 이용해 Kafka 혹은 다름 데이터 수집기로부터 모은 데이터를 조회할 수 있도록 하는 것이 가장 큰 목표.  
그리고 Spring Boot를 이용해 REST API로 다른 사이트에서 REST API로 조회가 가능하도록 하거나, JAVA RestHighLevelClient API를 통해 레거시하거나 외부와의 통신이 어려운 JAVA 프로젝트에서도 사용 가능한 API를 제작하는 것은 예정

- [X] REST API 구축
- [ ] Elastic Query 조회 기능
- [ ] INDEX CURD API 제작
- [ ] Document CRUD API 제작 

(내용 추가 예정...)

## Project Setting
### Spring Boot Structure
`./src/main` 기준 디렉터리 및 파일 구조  
```bash
./src/main
├── java
│   └── com
│       └── log
│           └── logpilot
│               ├── LogpilotApplication.java
│               ├── config
│               │   └── ElasticsearchConfig.java
│               ├── controller
│               │   └── LogController.java
│               ├── model
│               │   ├── ElasticModel.java
│               │   ├── ElasticsearchConfiguration.java
│               │   └── SourceModel.java
│               └── service
│                   ├── LogService.java
│                   └── LogServiceImpl.java
└── resources
    ├── application.yml
    ├── static
    └── templates
```

### Dependency
#### `pom.xml`
Elasticsearch REST Client 의존성 추가  
```xml
<!-- Elasticsearch Dependency -->
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-high-level-client</artifactId>
    <version>7.10.2</version>
</dependency>
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>7.10.2</version>
</dependency>
<dependency>
    <groupId>org.elasticsearch.plugin</groupId>
    <artifactId>transport-netty4-client</artifactId>
    <version>7.10.2</version>
</dependency>
```
