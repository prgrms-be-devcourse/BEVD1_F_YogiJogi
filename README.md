# BEVD1_F_YogiJogi

- F팀의 요기조기 숙박 예약 시스템(야놀자 클론코딩).
- 호텔 파트의 api를 구현했습니다.

### 구현

**지역 조회 페이지(https://www.yanolja.com/hotel)**

- 지역에 따른 호텔을 조회 할 수 있게 했습니다.


**예약 가능한 호텔 조회(https://www.yanolja.com/hotel/r-910161/?advert=AREA&hotel=1)**
- 예약 날짜와 인원에 따라 호텔을 보여줄수 있게 했습니다.
- 추가적으로 필터 기능을 통해서(호텔 성급, 테마) 필터링 할 수 있게 했습니다. 

**호텔 세부 조회(https://www.yanolja.com/hotel/3016868)**
- 호텔의 세부 정보를 볼 수 있습니다.
- 호텔의 전반적인 정보 및 이미지 url 를 제공하는 api, 리뷰를 제공하는 api, 예약가능한 룸을 제공하는 api 3가지가 포함되어 있습니다.
- 날짜 변경시, 예약가능한 방이 바뀌도록 구현했습니다. 단, 각 방의 재고는 1개로 고정되어있습니다.

**객실 상세 조회(https://www.yanolja.com/hotel/3016868/339462)**
- 객실의 세부 정보를 볼 수 있습니다.

**예약 기능**
- 사용자 정보와 방의 정보를 토대로, 예약 기능을 구현했습니다.



