# kotlin-convenience-store-precourse
<hr style="border: 1.5px solid white;">

## 🧑‍💻 프로젝트 정의와 주요기능

### 편의점 

- 구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템를 구현하는 프로젝트

### 주요 기능

- 재고 관리
- 프로모션 할인
- 맴버십 할인
- 영수증 출력

## 📋 기능 구현 목록

### 🙋 입력

- [X]  구매할 상품과 수량을 입력받는다.
  - [X] 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉽표로 구분한다.
- [X]  프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
  - Y: 증정 받을 수 있는 상품을 추가한다. 
  - N: 증정 받을 수 있는 상품을 추가하지 않는다.
- [X]  프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다. 
  - Y: 일부 수량에 대해 정가로 결제한다.
  - N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.
- [X]  멤버십 할인 적용 여부를 입력 받는다.
  - Y: 멤버십 할인을 적용한다.
  - N: 멤버십 할인을 적용하지 않는다.
- [X] 추가 구매 여부를 입력 받는다.
  - Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다.
  - N: 구매를 종료한다.

### 🖥 출력

- [X] 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다. 만약 재고가 0개라면 재고 없음을 출력한다.
    ```
    안녕하세요. W편의점입니다.
    현재 보유하고 있는 상품입니다.
    
   - 콜라 1,000원 10개 탄산2+1
   - 콜라 1,000원 10개
   - 사이다 1,000원 8개 탄산2+1
   - 사이다 1,000원 7개
   - 오렌지주스 1,800원 9개 MD추천상품
   - 오렌지주스 1,800원 재고 없음
   - 탄산수 1,200원 5개 탄산2+1
   - 탄산수 1,200원 재고 없음
   - 물 500원 10개
   - 비타민워터 1,500원 6개
   - 감자칩 1,500원 5개 반짝할인
   - 감자칩 1,500원 5개
   - 초코바 1,200원 5개 MD추천상품
   - 초코바 1,200원 5개
   - 에너지바 2,000원 5개
   - 정식도시락 6,400원 8개
   - 컵라면 1,700원 1개 MD추천상품
   - 컵라면 1,700원 10개
   
   구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
   ```
- [X] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메시지를 출력한다.
    ```현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)```
- [X] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.
    ```현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)```
- [X] 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력한다.
    ```멤버십 할인을 받으시겠습니까? (Y/N)```
- [X] 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
    ```
  ==============W 편의점================
  상품명		        수량	           금액
  콜라		          3 	     3,000
  에너지바 		      5 	    10,000
  ==============증    정================
  콜라		          1
  =====================================
  총구매액		      8	         13,000
  행사할인			             -1,000
  멤버십할인			             -3,000
  내실돈			                  9,000
  ```
- [X] 추가 구매 여부를 확인하기 위해 안내 문구를 출력한다.
  ```감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)```
- [ ] 사용자가 잘못된 값을 입력했을 때, "[ERROR]"로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
  - 구매할 상품과 수량 형식이 올바르지 않은 경우: [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.
  - 존재하지 않는 상품을 입력한 경우: [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
  - 구매 수량이 재고 수량을 초과한 경우: [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
  - 기타 잘못된 입력의 경우: [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.
  
### 🌈 로직

- [X] 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- [ ] 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- [ ] 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.
- [ ] 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
  - 총구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.
- [ ] 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.
- [ ] 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
  Exception이 아닌 IllegalArgumentException, IllegalStateException 등과 같은 명확한 유형을 처리한다.
- [X] 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
- [X] 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- [X] 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- [X] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음 -> 고객이 원하지 않으면 정가로 결제
- [X] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨
- [X] 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- [X] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- [X] 멤버십 할인의 최대 한도는 8,000원이다.

<hr style="border: 1px solid white;">

## 🤔 Edge Case
- [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하지만, 일부 수량도 없을 경우
- [ ] 멤버십 할인이 8000원이 넘어가는 경우
- [ ] 오늘 날짜가 프로모션 기간이 아닌 경우 -> 일반 수량이랑 합쳐야하는 지 고민
- [ ] 프로모션이 끝나자마자 다음날 다른 프로모션이 있는 경우
- [ ] 프로모션이 존재하는 상품이지만, 사용자가 지정된 개수보다 적개 구매할 경우
- [ ] 고객이 구매한 상품의 수량 중 일부만 프로모션 재고에 해당되는 경우
- [ ] 연속 2번 결제했을 때, 멤버십 할인 2번 다 적용 가능한지 생각
- [ ] 고객이 구매를 원하지만 모든 상품의 재고가 0인 경우
- [ ] 프로모션이 종료되는 날의 특정 시간에 고객이 구매를 시도하는 경우 -> 0시를 기준으로 할지 생각
- [ ] 결제 금액이 너무 큰 경우
- [ ] 제품명이 너무 긴 경우
- [ ] 수량이 너무 긴 경우 -> 1,000 표시할 것인가?
- [ ] 금액이 너무 긴 경우
- [ ] 프로모션 제품이랑 프로모션 하지 않는 제품이랑 가격 차이가 나는 경우
<hr style="border: 1px solid white;">

## 🚫 예외 상황
<table>
   <tr>
      <td>대상</td>
      <td>구현 여부</td>
      <td>상황</td>
   </tr>
    <tr>
      <td rowspan="2">Y/N 입력</td>
      <td>O</td>
      <td>빈 경우</td>
    </tr>
    <tr>
      <td>O</td>
      <td>Y/N가 아닌 문자인 경우</td>
    </tr>
    <tr>
      <td rowspan="15">상품과 수량 입력</td>
      <td>O</td>
      <td>빈 경우</td>
    </tr>
    <tr>
      <td>O</td>
      <td>구분자로 쉼표(,)가 없는 경우</td>
    </tr>
    <tr>
      <td>O</td>
      <td>구분자로 쉼표(,)만 들어오는 경우</td>
    </tr>
    <tr>
      <td>O</td>
      <td>양 끝이 []이 아닌 경우</td>
    </tr>
   <tr>
      <td>O</td>
      <td>구분자 하이픈(-)이 없는 경우</td>
    </tr>
    <tr>
      <td>O</td>
      <td>구분자 하이픈(-)만 들어오는 경우</td>
    </tr>
   <tr>
      <td>O</td>
      <td>(수량) 숫자가 아닌 경우</td>
   </tr>
    <tr>
      <td><input type="checkbox"></td>
      <td>(수량) Long타입인 경우</td>
   </tr>
    <tr>
      <td>O</td>
      <td>(수량) 0인 경우</td>
   </tr>
    <tr>
      <td>O</td>
      <td>(수량) 소수인 경우</td>
   </tr>
    <tr>
      <td>O</td>
      <td>(수량) 음수인 경우</td>
   </tr>
    <tr>
        <td>O</td>
        <td>(수량) 재고 수량보다 많이 입력한 경우</td>
    </tr>
    <tr>
      <td><input type="checkbox"></td>
      <td>(제품명) ), 문자, 영어, 숫자 외 오류로 출력</td>
    </tr>
    <tr>
      <td>O</td>
      <td>(제품명) 없는 제품명을 입력한 경우</td>
    </tr>
    <tr>
      <td>O</td>
      <td>(제품명) 동일한 상품명을 2번이상 입력한 경우</td>
    </tr>
</table>


<hr style="border: 1px solid white;">

## 📌 프로그래밍 요구사항

- [ ] Kotlin 1.9.24에서 실행 가능해야 한다.
- [ ] Java 코드가 아닌 Kotlin 코드로만 구현해야 한다.
- [ ] 프로그램 실행의 시작점은 Application의 main()이다.
- [ ] build.gradle.kts 파일은 변경할 수 없으며, 제공된 라이브러리 이외의 외부 라이브러리는 사용하지 않는다.
- [ ] 프로그램 종료 시 System.exit() 또는 exitProcess()를 호출하지 않는다.
- [ ] 프로그래밍 요구 사항에서 달리 명시하지 않는 한 파일, 패키지 등의 이름을 바꾸거나 이동하지 않는다.
- [ ] 코틀린 코드 컨벤션을 지키면서 프로그래밍한다.
- [ ] 기본적으로 Kotlin Style Guide를 원칙으로 한다.
- [ ] indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용한다.
  - 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
  - 힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은 함수(또는 메서드)를 분리하면 된다.
- [ ] 함수(또는 메서드)가 한 가지 일만 하도록 최대한 작게 만들어라.
- [ ] JUnit 5와 AssertJ를 이용하여 정리한 기능 목록이 정상적으로 작동하는지 테스트 코드로 확인한다.
  - 테스트 도구 사용법이 익숙하지 않다면 아래 문서를 참고하여 학습한 후 테스트를 구현한다.
- [ ] else를 지양한다.
  - 때로는 if/else, when문을 사용하는 것이 더 깔끔해 보일 수 있다. 어느 경우에 쓰는 것이 적절할지 스스로 고민해 본다.
  - 힌트: if 조건절에서 값을 return하는 방식으로 구현하면 else를 사용하지 않아도 된다.
- [ ] Enum 클래스를 적용하여 프로그램을 구현한다. 
- [ ] 구현한 기능에 대한 단위 테스트를 작성한다. 단, UI(System.out, System.in, Scanner) 로직은 제외한다.
- [ ] 함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
- [ ] 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현한다.
- [ ] 입출력을 담당하는 클래스를 별도로 구현한다.
- [ ] 아래 InputView, OutputView 클래스를 참고하여 입출력 클래스를 구현한다.
- [ ] 클래스 이름, 메소드 반환 유형, 시그니처 등은 자유롭게 수정할 수 있다.