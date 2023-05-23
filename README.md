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
## 테이블 구조
- 주식 종목 테이블
|컬럼|설명|기타|
|------|---|---|
|STOCK_CODE|종목코드||
|NAME|종목명||

|제목|내용|설명|
|------|---|---|
|테스트1|테스트2|테스트3|
|테스트1|테스트2|테스트3|
|테스트1|테스트2|테스트3|

- 일별 주식 가격 정보 테이블
|컬럼|설명|기타|
|------|---|---|
|STOCK_CODE|종목코드||
|YESTERDAY_CLOSE_PRICE|어제종가||
|OPEN_PRICE|시가||
|CLOSE_PRICE|종가||
|HIGH_PRICE|최고가||
|LOW_PRICE|최저가||
|CURRENT_PRICE|현재가||
|TRANSACTION_VOLUME|거래량||

- 주식 거래 테이블
|컬럼|설명|기타|
|------|---|---|
|TYPE|매도/매수||
|STOCK_CODE|종목코드||
|USER_ID|사용자ID||
|QUANTITY|거래수량||
|PRICE|거래가격||
|FEE|수수료||

- 종목별 조회수 저장 테이블
|컬럼|설명|기타|
|------|---|---|
|STOCK_CODE|종목코드||
|VIEWS_COUNT|조회수||

- 사용자 주식 Wallet 테이블
|컬럼|설명|기타|
|------|---|---|
|USER_ID|사용자ID||
|STOCK_CODE|종목코드||
|QUANTITY|보유수량||


## 요구사항
- 실시간 순위 서비스 제공에 필요한 Restful API를 구현합니다.
- Application이 로딩될 때 기본 데이터가 DB에 적재되도록 합니다.
  1) 과제 검토를 위해 별다른 DB관련 사전작업 없이 애플리케이션만 실행하면 동작되도록 데이터가 설정되어야합니다.
  2) 주식 종목에 대한 정보는 첨부된 데이터를 참고하시면 됩니다.
- 데이터 테이블 구조는 효율적인 방식으로 스스로 설계 합니다.
- 각 기능 및 제약 사항에 대한 단위테스트를 작성합니다. 

## API 상세 기능 설명
- 아래는 과제를 위한 4가지 조회 방법
  - 인기: "사람들이 많이본"
  - 상승: "가격이 많이 오른"
  - 하락: "가격이 많이 내린"
  - 거래량: "거래량이 많은"
  - 태그에 대한 데이터 기준 시점은 API를 호출한 시점
  - 페이징은 자유롭게 구현
- 호출시 관련 데이터를 랜덤으로 변경할 수 있는 테스트 API 구현

## 해결 방법
1) 인기순위 조회
  - URL
```
GET localhost:8080/api/v1/market/stocks/popularity
```
- 인기순위는 임베디드 레디스와 스케줄러를 사용하였습니다.
- 종목 상세 조회 시 매번 DB에 조회수를 업데이트를 처리하게 되면 비효율적이므로 캐시메모리에 해당종목 코드와 조회수를 저장한 뒤 5분간격으로 해당 종목과 조회수를 DB에 업데이트하는 방식으로 처리하였습니다.
- 다중 서버 환경에서 서버의 수만큼 조회수를 DB에 업데이트 스케줄러가 중복 실행되지 않도록 ShedLock 를 사용하여 처리하였습니다.

3) 상승 조회
  - URL
```
GET localhost:8080/api/v1/market/stocks/rise
```
5) 하락 조회
  - URL
```
GET localhost:8080/api/v1/market/stocks/fall
```
7) 거래량 조회
  - URL
```
GET localhost:8080/api/v1/market/stocks/volume
```


