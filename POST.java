import java.util.Scanner;

/**
 * POST POS 단말기의 핵심 기능을 담당하는 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class POST {
    private Sale sale;            // 현재 거래
    private Products[] productDB; // 상품 DB
    private Sale[] saleDB;        // 판매 DB
    private int saleSize;         // saleDB에 저장된 거래 수
    private double receivedCash;  // 받은 현금
    private TAX tax;              // TAX 인터페이스 참조 (5장 다형성 - 다이어그램 POST→TAX 의존)
    private Scanner scanner;

    public POST() {
        sale = new Sale();
        saleDB = new Sale[100];
        saleSize = 0;
        scanner = new Scanner(System.in);

        // 상품 DB 채우기 (배열에 객체 저장 - 4장 객체 배열)
        // 다형성: 같은 AlcoholicDrinks 클래스지만 type에 따라 세율이 다름 (5장)
        productDB = new Products[12];

        // 막걸리 - 주류세 5%
        productDB[0]  = new AlcoholicDrinks(1,  "장수막걸리",    1500, AlcoholicDrinks.MAKGEOLLI);
        productDB[1]  = new AlcoholicDrinks(2,  "이동막걸리",    1800, AlcoholicDrinks.MAKGEOLLI);
        // 맥주 - 주류세 72%
        productDB[2]  = new AlcoholicDrinks(3,  "카스500ml",     2500, AlcoholicDrinks.BEER);
        productDB[3]  = new AlcoholicDrinks(4,  "테라500ml",     2600, AlcoholicDrinks.BEER);
        // 증류주(소주) - 주류세 72%
        productDB[4]  = new AlcoholicDrinks(5,  "참이슬360ml",   1800, AlcoholicDrinks.SOJU);
        productDB[5]  = new AlcoholicDrinks(6,  "처음처럼360ml", 1800, AlcoholicDrinks.SOJU);
        // 와인 - 주류세 30%
        productDB[6]  = new AlcoholicDrinks(7,  "샤토메를로",   15000, AlcoholicDrinks.WINE);
        productDB[7]  = new AlcoholicDrinks(8,  "로제와인",     12000, AlcoholicDrinks.WINE);
        // 양주 - 주류세 72%
        productDB[8]  = new AlcoholicDrinks(9,  "조니워커블랙", 45000, AlcoholicDrinks.WHISKEY);
        productDB[9]  = new AlcoholicDrinks(10, "발렌타인17년", 80000, AlcoholicDrinks.WHISKEY);
        // 일반 음료 - 부가세 10%
        productDB[10] = new Beverages(11, "코카콜라250ml", 1200);
        productDB[11] = new Beverages(12, "삼다수500ml",    900);
    }

    public void buyItemsWithCash() {
        // 매 거래마다 새 Sale 객체 생성 (이전 거래 데이터 보호 - 버그 수정)
        sale = new Sale();

        // 바코드(번호) + 수량 입력 반복 (완료: 0)
        int barcode = 0;
        boolean done = false;
        while (done == false) {
            System.out.print("상품 바코드 입력 (완료: 0): ");
            barcode = scanner.nextInt();

            if (barcode == 0) {
                done = true;
            } else {
                // 상품 DB에서 검색 : 바코드 == productID 인 상품 찾기
                Products found = null;
                for (int i = 0; i < productDB.length; i++) {
                    if (productDB[i].getProductID() == barcode) {
                        found = productDB[i];
                    }
                }

                if (found == null) {
                    // Alternative Scenario [Line 2]: 올바르지 않은 바코드 → 오류 메시지 출력
                    System.out.println("오류: 없는 상품입니다.");
                } else {
                    System.out.println("상품명: " + found.getName());
                    System.out.println("가격: " + found.getPrice() + "원");
                    System.out.print("수량 입력: ");
                    int qty = scanner.nextInt();
                    // primitives만 Sale에 저장 → Sale→Products 의존 화살표 미생성
                    sale.addItem(found.getName(), found.getPrice(), qty, found.CalculateTax());
                }
            }
        }

        // 상품이 하나도 없으면 종료
        if (sale.getSize() == 0) {
            System.out.println("구매한 상품이 없습니다.");
            return;
        }

        // 지불할 금액 계산 및 출력
        double payment      = (int)sale.calculate();    // 결제금액 (세금 포함)
        double taxTotal     = (int)sale.calculateTax(); // 역산된 세금액
        double supplyAmount = payment - taxTotal;        // 공급가액 (세금 제외)

        System.out.println("--------------------");
        System.out.println("지불할 금액: " + (int)payment + "원");
        System.out.println("--------------------");

        // Cashier가 고객으로부터 받은 현금 금액을 시스템에 입력 (Use Case Step 8)
        System.out.print("받은 현금 입력: ");
        receivedCash = scanner.nextDouble();

        System.out.println("받은 현금: " + (int)receivedCash + "원");

        // Alternative Scenario [Line 7]: 현금 부족 → 부족 메시지 출력 후 Cashier가 판매 취소
        // 시스템이 자동 취소하지 않음 — Cashier가 0 입력으로 직접 취소 처리
        if (receivedCash < payment) {
            System.out.println("현금이 부족합니다.");
            System.out.println("판매가 취소되었습니다.");
            return;
        }

        // 거스름돈 계산 (Use Case Step 9)
        double change = receivedCash - payment;

        // 영수증 출력 (Use Case Step 9)
        System.out.println("===== 영수증 =====");
        for (int i = 0; i < sale.getSize(); i++) {
            System.out.println(sale.getName(i) + " x " + sale.getCount(i) + " = " + (sale.getPrice(i) * sale.getCount(i)) + "원");
        }
        System.out.println("--------------------------------");
        System.out.println("결 제 금 액:          " + (int)payment + "원");
        System.out.println("공 급 가 액:          " + (int)supplyAmount + "원");
        System.out.println("부 가 세 액:          " + (int)taxTotal + "원");
        System.out.println("--------------------------------");
        System.out.println("받은 현금:            " + (int)receivedCash + "원");
        System.out.println("거 스 름 돈 :          " + (int)change + "원");
        System.out.println("================");

        // Sale 정보를 SaleDB에 저장 (Use Case Step 11)
        // sale은 이미 새 객체이므로 reset() 불필요 — 다음 거래 시 buyItemsWithCash() 재호출 시 new Sale()
        saleDB[saleSize] = sale;
        saleSize = saleSize + 1;
        System.out.println("판매 정보가 SaleDB에 저장되었습니다.");
    }
}