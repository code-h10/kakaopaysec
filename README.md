# 카카오페이증권 과제평가 - 실시간 순위 정보 제공 서비스 개발

## 목차
## 개발 환경
  - Intellij IDEA Ultimate
  - Mac OS
  - GIT
  - Java 11
  - SpringBoot 2.7.12-SNAPSHOT
  - H2 Database
  - Gradle
  - junit 5
  - Embedded Redis
---
## 프로젝트 구조
```
├── com.kakaopaysec.stock
│   ├── confing
│   │   └── exception
│   ├── controller
│   ├── models
│   ├── repository
│   ├── schedule
│   ├── service
│   ├── utils
│   │   ├── dto
│   │   └── enums
└── StockApplication.java
``` 
---
## 테이블 구조
- 주식 종목 테이블(STOCKS)

|컬럼|설명|기타|
|------|---|---|
|STOCK_CODE|종목코드| |
|NAME|종목명| |


- 일별 주식 가격 정보 테이블(STOCKS_PRICE_HISTORY)

|컬럼|설명|기타|
|------|---|---|
|STOCK_CODE|종목코드| |
|YESTERDAY_CLOSE_PRICE|어제종가| |
|OPEN_PRICE|시가| |
|CLOSE_PRICE|종가| |
|HIGH_PRICE|최고가| |
|LOW_PRICE|최저가| |
|CURRENT_PRICE|현재가| |
|TRANSACTION_VOLUME|거래량| |

- 주식 거래 테이블(TRANSACTION)

|컬럼|설명|기타|
|------|---|---|
|TYPE|매도/매수| |
|STOCK_CODE|종목코드| |
|USER_ID|사용자ID| |
|QUANTITY|거래수량| |
|PRICE|거래가격| |
|FEE|수수료| |

- 종목별 조회수 저장 테이블(VIEWS_COUNT)

|컬럼|설명|기타|
|------|---|---|
|STOCK_CODE|종목코드| |
|VIEWS_COUNT|조회수| |

- 사용자 주식 Wallet 테이블(USERS_WALLET)

|컬럼|설명|기타|
|------|---|---|
|USER_ID|사용자ID| |
|STOCK_CODE|종목코드| |
|QUANTITY|보유수량| |
---

## 해결 방법
### 1. 인기순위 조회
```
GET localhost:8080/api/v1/market/stocks/popularity
```
- 인기순위는 임베디드 레디스와 스케줄러를 사용했습니다.
- 종목 상세 조회 시 매번 DB에 조회수를 업데이트를 처리하게 되면 비효율적이므로 캐시메모리에 해당종목 코드와 조회수를 저장한 뒤 5분간격으로 해당 종목과 조회수를 DB에 업데이트하는 방식으로 처리했습니다.
- 다중 서버 환경에서 서버의 수만큼 조회수를 DB에 업데이트처리하는 스케줄러가 중복 실행되지 않도록 ShedLock 를 사용하여 처리했습니다.
- 조회수 테스트 API : GET localhost:8080/api/v1/test/stocks/{stockCode}

### 2. 상승 조회
```
GET localhost:8080/api/v1/market/stocks/rise
```
- STOCKS_PRICE_HISTORY 테이블의 어제의 종가 대비 오늘의 가격을 백분률로 구하여 구현했습니다.
- 첫 상장된 주식의 경우 오늘 시가 대비 현재가격을 백분율로 구하여 구현했습니다.

### 3. 하락 조회
```
GET localhost:8080/api/v1/market/stocks/fall
```
- STOCKS_PRICE_HISTORY 테이블의 어제의 종가 대비 오늘의 가격을 백분률로 구하여 구현했습니다.
- 첫 상장된 주식의 경우 오늘 시가 대비 현재가격을 백분율로 구하여 구현했습니다.

### 4. 거래량 조회
```
GET localhost:8080/api/v1/market/stocks/volume
```
- 거래 체결 시 해당 STOCKS_PRICE_HISTORY 테이블의 거래량 정보에 업데이트 되도록 구현했습니다. 
- 아래의 API는 TRANSACTIONS 테이블에 체결된 거래 정보를 저장 시 STOCKS_PRICE_HISTORY 테이블의 현재거래가격과 거래량에 반영되도록 처리했습니다.
```
POST localhost:8080/api/v1/test/transactions
{
	"type" : 0,
	"stockCode" : "371460",
	"userId" : 4,
	"price" : "250000",
	"quantity" : 10
}
```

### 5. 데이터 랜덤으로 변경하는 테스트용API
```
GET localhost:8080/api/v1/market/stocks/random
```



