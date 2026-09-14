# IT214_Microservice_Session07_B3

FinBank Microservice - RestTemplate + Eureka + API Gateway.

## Cấu trúc
- eureka-server: 8761
- account-service: 8082
- transaction-service: 8083
- api-gateway: 8222

## Môi trường
- JDK 21
- Spring Boot 3.5.0
- Spring Cloud 2025.0.0
- Gradle
- YAML

## Mở bằng IntelliJ IDEA
1. Giải nén file ZIP.
2. IntelliJ IDEA -> Open -> chọn thư mục `IT214_Microservice_Session07_B3`.
3. Chọn import project bằng Gradle.
4. Đặt Gradle JVM = JDK 21.
5. Chạy lần lượt:
   - `EurekaServerApplication`
   - `AccountServiceApplication`
   - `TransactionServiceApplication`
   - `ApiGatewayApplication`

## Test qua Gateway
Tạo tài khoản:

POST `http://localhost:8222/api/accounts`
```json
{"accountNumber":"1001","ownerName":"Nguyen Van A","balance":10000000}
```

POST `http://localhost:8222/api/accounts`
```json
{"accountNumber":"1002","ownerName":"Nguyen Van B","balance":5000000}
```

Chuyển tiền:

POST `http://localhost:8222/api/transactions/transfer`
```json
{"fromAccountNumber":"1001","toAccountNumber":"1002","amount":2000000,"description":"Chuyen tien"}
```

Kiểm tra:
- GET `http://localhost:8222/api/accounts/1001`
- GET `http://localhost:8222/api/accounts/1002`
- GET `http://localhost:8222/api/accounts/1001/balance`

Luồng giao tiếp:
`Transaction Service -> RestTemplate (@LoadBalanced) -> ACCOUNT-SERVICE -> Eureka`
