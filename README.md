# SmartphoneGameProgram  
2019184013 박준범    
### **게임 이름** : Catch monster if you can  

### 게임 컨셉  
+ 장애물을 피하고 몬스터를 잡아서 던전을 탈출하는 게임  
+ 비슷한 장르의 게임 : Hollow Knight  
  ![image](https://github.com/Junbeomb/SmartphoneGameProgram/assets/87471961/13ddcffb-8e6f-40e9-ab93-2dd14e3707bb)
  
## Stage1  
  #### 근접 몬스터 
  + 닿으면 데미지를 입음
  + 타격을 가할 수록 빨라짐
  + 총 3단계로 구성
  <img width="540" height="300" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/1d904f40-e830-4fb0-a2f1-73a7f0f0ec4a">
  
  #### 장애물
  + 랜덤한 시간이 지나면 떨어짐
  <img width="540" height="80" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/2b10c3c8-89ca-4f72-8277-f5f34d7cbf00">
  
## Stage2
  #### 원거리 몬스터
  + 일정 거리 다가오면 공격
  + 튕겨내기 가능
  <img width="400" height="300" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/e36da472-adfa-4018-b9de-79955d28a989">
  <img width="400" height="300" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/41b5fdc3-c5a3-480d-b551-6d3e97faa385">
  
  #### 장애물
  + Stage1과 동일

## Stage3
  #### 보스 몬스터  
  + 세 가지 스킬 사용
  
  + 번개
    + 번개 공격
  <img width="400" height="300" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/2a593204-b722-4deb-b424-0a783806d5be">
  
  + 불
    + 바닥에 표시 후 일정 시간 뒤 불 소환.
    + 일정 시간이 지나면 사라짐 혹은 공격으로 제거 가능.  
  <img width="400" height="300" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/b939ff88-2b4a-48c2-a546-9ff963eaf761">
  
  + 폭발
    + 보스 몬스터 주변으로 폭발
  <img width="400" height="300" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/a31d4172-f295-4f74-9722-880742dd9459">

## Ending
  #### 종료 화면
  <img width="400" height="300" alt="image" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/883618d4-8bb7-4c26-a995-a6e5c0c2d299">
  
## 개발 일정 및 진행사항
  <img width="600" height="500" alt="개발 일정" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/13bdb384-e627-457d-9d8e-b84917ed5508">

## 주차 별 Git Commit
  <img width="600" height="500" alt="개발 일정" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/9f803404-e5f4-4cd6-99d4-432dd0560d62">  
  <img width="600" height="500" alt="개발 일정" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/909aa240-0b00-4c20-a978-d4fefeb2d684">  

## 참고, 수업내용에서 차용한 것, 개발한 것
  + MainScene, CollisionChecker, SheetSprite 등 수업 자료를 기반으로 개발
    + MainScene에 object를 추가하고 삭제하는 방식으로 stage 변경 및 몬스터 관리  
    <img width="600" height="500" alt="개발 일정" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/97e733b0-88ae-4d14-b25d-6359ba7147c3">
    
    + Sprite 이미지를 사용해 애니메이션 제작  
    <img width="800" height="300" alt="개발 일정" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/1d69bb42-a41c-410c-8687-ec0225181f76">
    
  + Boss & 몬스터 알고리즘
    + Boss 스킬 랜덤으로 골고루 사용하도록 조절
    <img width="800" height="300" alt="개발 일정" src="https://github.com/Junbeomb/Java_SmartphoneGameProgram/assets/87471961/459a6e62-fdf1-42af-80a7-e25f5e7816cb">

  + 몬스터 알고리즘
    + 맞을 수록 이동 속도 증가하는 몬스터
    + 일정 범위 안에 플레이어 들어올 시 공격하는 원거리 몬스터

## 하고 싶었지만 못한 것들
  + 맵의 크기, 위로 올라가지 못함 (사다리 등등..)

## 팔기 위해 보충할 것들
  + 캐릭터나 몬스터 등 애니메이션 자연스럽게 하기
  + 스토리 추가
  + 몬스터, 장애물 다양화
  + 맵 확장

## 느낀 점
  > 수업에서 제공되는 체계화된 코드를 사용해서 개발을 하니 굉장히 효율적이라고 느껴졌다.  
>   개발을 할 때에 있어서 좋은 지표가 될 것 같다.











