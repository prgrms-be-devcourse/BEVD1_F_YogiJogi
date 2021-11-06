# YogiJogi

- F팀의 요기조기 숙박 예약 시스템(야놀자 클론코딩).
- 호텔 파트의 api를 구현했습니다.

### 구성원
Product Owner | Scrum Master | Developer | Mentor
--------------|-------------|-----------|---------
|<img src="https://avatars.githubusercontent.com/u/46310555?v=4" width="200" height="200">|![image](https://user-images.githubusercontent.com/60607880/140602752-b45da4be-3b54-46fe-a75e-9fdcd4a96ecc.png)|<img src="https://avatars.githubusercontent.com/u/56511173?v=4" width="200" height="200">|<img src="https://avatars.githubusercontent.com/u/14347593?v=4" width="200" height="200">   
[김채원](https://github.com/Chaeonida) | [신용진](https://github.com/sirin0762) | [김영현](https://github.com/eden6187) | [F](https://github.com/lleellee0)

### 🔧 기술 스택
<div style="display:flex; justify-content:center; width:100%;">
  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
  <img src="https://img.shields.io/badge/JPA-FF0000?style=for-the-badge&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
  <img src="https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">
</div>




### 구현

**지역 조회 페이지(https://www.yanolja.com/hotel)**
![image](https://user-images.githubusercontent.com/60607880/140602845-4341a8c5-77e8-4825-9d9e-f49336b313f5.png)

- 지역에 따른 호텔을 조회 할 수 있게 했습니다.


**예약 가능한 호텔 조회(https://www.yanolja.com/hotel/r-910161/?advert=AREA&hotel=1)**
![image](https://user-images.githubusercontent.com/60607880/140602867-3721271a-bc8b-47ee-898f-1f2d9b1ee884.png)

- 예약 날짜와 인원에 따라 호텔을 보여줄수 있게 했습니다.
- 추가적으로 필터 기능을 통해서(호텔 성급, 테마) 필터링 할 수 있게 했습니다. 허나 프론트엔드에서는 역량 부족으로 해당 필터기능을 제공하는 컴포넌트가 없습니다.

**호텔 세부 조회(https://www.yanolja.com/hotel/3016868)**
![image](https://user-images.githubusercontent.com/60607880/140602942-b4dac40b-433c-4459-877e-b3a840d924f3.png)

- 호텔의 세부 정보를 볼 수 있습니다.
- 호텔의 전반적인 정보 및 이미지 url 를 제공하는 api, 리뷰를 제공하는 api, 예약가능한 룸을 제공하는 api 3가지가 포함되어 있습니다.
- 날짜 변경시, 예약가능한 방이 바뀌도록 구현했습니다. 단, 각 방의 재고는 1개로 고정되어있습니다.
- 이미지 업로드 기능은 s3에 올리는 api가 존재하며, 프론트엔드에서는 구현되지 않았습니다.


**객실 상세 조회(https://www.yanolja.com/hotel/3016868/339462)**
![image](https://user-images.githubusercontent.com/60607880/140602970-b998ab70-b591-43d3-82ed-447e96a20ccc.png)

- 객실의 세부 정보를 볼 수 있습니다.
- 예약하기 버튼을 통해 예약 페이지로 이동됩니다.

**예약 기능**
![image](https://user-images.githubusercontent.com/60607880/140602985-b2dfb47c-1d2f-4b39-bf4a-f79f3916201b.png)

- 사용자 정보와 방의 정보를 토대로, 예약 기능을 구현했습니다.





