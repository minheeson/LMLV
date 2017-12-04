# Large-scale Media Loader and Viewer

대규모 미디어 목록을 위한 로더와 뷰어

## 설명

### 선정 배경

- 네이버 클라우드에서는 많은 양의 데이터를 다루고 있습니다.
- 각 유저마다 많은 양의 미디어 혹은 파일을 가지고 있기 때문에
  데이터 목록을 다루는 일은 매우 중요합니다.
- 대규모 미디어 목록을 다뤄 봄으로써 발생할 수 있는 문제와 해결 방법을
  __고민하고 경험하며 공유하기__ 위해서 선정하였습니다.

## 요구사항

### 필수 - UI

- 리스트 아이템이 아래와 같은 형식을 사용
  ![list_item_template](/images/template.png)
  - 디자인 가이드는 [머터리얼 디자인 - 컴포넌트 - 리스트](http://davidhyk.github.io/google-design-ko/components/lists.html#lists-specs)의 __세 줄 리스트__ 참고
- 화면 구성
  ![screen_flow](images/screen_flow.png)
  - 기본 화면 구성은 위와 같으나 추가로 필요에 의해 들어가는 화면은 개인 판단에 맞기도록 하겠습니다.
- `RecyclerView` 사용 + 패스트 스크롤

### 필수 - 로더

- 대규모 미디어 목록을 일정 단위로 로딩함에 있어 속도와 메모리 사용을 개선할 수 있는 방법을 구현
- 패스트 스크롤을 통해 목록의 원하는 부분을 빠르게 접근할 수 있는 방법을 구현
- `ContentProvider` 사용
- 페이징 구현

### 필수 - 뷰어

- 사진과 동영상에 대한 뷰어를 구현
- `ViewPager`를 활용한 뷰어 구현

### 선택

- 리스트: 정렬, 편집 모드 및 과업(이름 변경, 삭제 등)
- 뷰어: 동영상 재생, Animated GIF, 과업(이름 변경, 삭제 등)


## 개발 환경

- __Android Studio 3.0__
- __Java 7 or 8__

## 개발 장비

- [Samsung Galaxy S7 (Android 6.0.1)](http://www.samsung.com/sec/smartphones/galaxy-s7-g930/SM-G930SZIASKO/) x 4대

## Mentee

- [손민희](https://github.com/minheeson)
- [이채은](https://github.com/chaeeun)
- [조현웅](https://github.com/gusdnddk111)
- [전진우](https://github.com/jeonjw)


