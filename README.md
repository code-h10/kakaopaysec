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

## 요구사항
- 실시간 순위 서비스 제공에 필요한 Restful API를 구현합니다.
- Application이 로딩될 때 기본 데이터가 DB에 적재되도록 합니다.
  1) 과제 검토를 위해 별다른 DB관련 사전작업 없이 애플리케이션만 실행하면 동작되도록 데이터가 설정되어야합니다.
  2) 주식 종목에 대한 정보는 첨부된 데이터를 참고하시면 됩니다.
- 데이터 테이블 구조는 효율적인 방식으로 스스로 설계 합니다.
- 각 기능 및 제약 사항에 대한 단위테스트를 작성합니다. 


## API 기능 설명
- 태그별 순위 조회 RestfulAPI 개발
- 인기
```
localhost:8080/api/v1/market/stocks/popularity
```
  - 상승
```
localhost:8080/api/v1/market/stocks/rise
```
  - 하락
```
localhost:8080/api/v1/market/stocks/fall
```
  - 거래량
```
localhost:8080/api/v1/market/stocks/volume
```

- 태그별 호출 시 데이터를 랜덤으로 변경 할 수 있는 테스트용 API 
 ```
localhost:8080/api/v1/market/stocks/random
```

<br>
메일로 송부된, [카카오페이증권 과제]를 확인해서 이곳 Github 에 소스를 올려주시면 됩니다.
<br>
<br>

과제 기술 제약사항에 있는 **[“내용을 작성하여 readme.md” 파일에 첨부 해주세요.]**  에서의 redeme.md 는 해당 README.md 입니다.
<br>
<br>
_**본 내용을 지우고, 여기에 기술하시면 됩니다.**_

감사합니다.
