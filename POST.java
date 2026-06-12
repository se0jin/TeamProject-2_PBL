import java.util.Scanner;

/**
 * POST POS 단말기의 핵심 기능을 담당하는 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class POST {
    private Sale sale;
    private Products[] productDB;
    private Sale[] saleDB;
    private int saleSize;
    private double receivedCash;
    private TAX tax;
    private Scanner scan;

    /**
     * POST 기본 생성자 - 상품DB와 판매DB를 초기화한다. (4장 객체 배열)
     *
     */
    public POST() {
        sale = new Sale();
        saleDB = new Sale[100];
        saleSize = 0;
        scan = new Scanner(System.in);
        
        productDB = new Products[17];

        productDB[0] = new AlcoholicDrinks( 8801031234567L, "장수막걸리",     1500, AlcoholicDrinks.MAKGEOLLI);
        productDB[1] = new AlcoholicDrinks( 8801031234568L , "이동막걸리",     1800, AlcoholicDrinks.MAKGEOLLI);
        productDB[2] = new AlcoholicDrinks( 8801052987654L , "카스500ml",      2500, AlcoholicDrinks.BEER);
        productDB[3] = new AlcoholicDrinks( 8801052987655L , "테라500ml",      2600, AlcoholicDrinks.BEER);
        productDB[4] = new AlcoholicDrinks( 8801093111222L, "참이슬360ml",    1800, AlcoholicDrinks.SOJU);
        productDB[5] = new AlcoholicDrinks( 8801093111223L , "처음처럼360ml",  1800, AlcoholicDrinks.SOJU);
        productDB[6] = new AlcoholicDrinks( 8801124333444L , "샤토메를로",    15000, AlcoholicDrinks.WINE);
        productDB[7] = new AlcoholicDrinks( 8801124333445L, "로제와인",      12000, AlcoholicDrinks.WINE);
        productDB[8] = new AlcoholicDrinks( 8801155555666L  , "조니워커블랙",  45000, AlcoholicDrinks.WHISKEY);
        productDB[9] = new AlcoholicDrinks( 8801155555667L , "발렌타인17년",  80000, AlcoholicDrinks.WHISKEY);
        productDB[10] = new Beverages( 8809010665009L , "코카콜라500ml", 1200); 
        productDB[11] = new Beverages( 8809010665016L, "삼다수500ml",    900);
        productDB[12] = new Beverages( 8809010665023L , "칠성사이다500ml", 1500);
        productDB[13] = new Beverages( 8809010665030L , "포카리스웨트500ml", 1800);
        productDB[14] = new Beverages( 8809010665047L , "바나나맛우유",    1700);
        productDB[15] = new Beverages( 8809010665054L , "레쓰비캔커피",     1000);
        productDB[16] = new Beverages( 8809010665061L , "아이스티355ml",   1400);
    }

    /**
     * 현금 결제를 처리하는 메소드 (Use Case: 현금으로 상품을 구매한다)
     * 바코드 입력 → 상품 조회 → Sale 추가 → 결제 → 영수증 출력 → SaleDB 저장
     *
     */
    public void buyItemsWithCash() {
        sale = new Sale();

        // [Main Scenario Step 2~3] 바코드 + 수량 입력 반복 (완료: 0)
        boolean done = false;
        while (done == false) {
            System.out.print("상품 바코드 입력 (완료: 0): ");
            int barcode = scan.nextInt();

            if (barcode == 0) {
                done = true;
            } else {
                // 상품 DB에서 바코드로 검색
                Products found = null;
                for (int i = 0; i < productDB.length; i++) {
                    if (productDB[i].getProductID() == barcode) {
                        found = productDB[i];
                    }
                }

                // [Alternative Line 2] 올바르지 않은 바코드 → 오류 메시지
                if (found == null) {
                    System.out.println("오류: 없는 상품입니다.");
                } else {
                    // [Step 3] 상품 정보 화면 출력
                    System.out.println("상품명: " + found.getName());
                    System.out.println("가격: " + found.getPrice() + "원");
                    System.out.print("수량 입력: ");
                    int qty = scan.nextInt();
                    // 5장 다형성: TAX 인터페이스 참조로 CalculateTax() 호출
                    sale.addItem(found.getName(), found.getPrice(), qty, found.CalculateTax());
                }
            }
        }

        if (sale.getSize() == 0) {
            System.out.println("구매한 상품이 없습니다.");
            return;
        }

        // [Step 5] 지불할 금액 계산 및 출력
        double payment      = sale.calculate();
        double taxTotal     = sale.calculateTax();
        double supplyAmount = payment - taxTotal;

        System.out.println("--------------------");
        System.out.println("지불할 금액: " + (int)payment + "원");
        System.out.println("--------------------");

        // [Step 8] Cashier가 받은 현금 입력
        System.out.print("받은 현금 입력: ");
        receivedCash = scan.nextDouble();
        System.out.println("받은 현금: " + (int)receivedCash + "원");

        // [Alternative Line 7] 현금 부족 → 판매 취소
        if (receivedCash < payment) {
            System.out.println("현금이 부족합니다.");
            System.out.println("판매가 취소되었습니다.");
            return;
        }

        // [Step 9] 거스름돈 계산 및 영수증 출력
        double change = receivedCash - payment;

        System.out.println("===== 영수증 =====");
        for (int i = 0; i < sale.getSize(); i++) {
            System.out.println(sale.getName(i) + " x " + sale.getCount(i)
                + " = " + (sale.getPrice(i) * sale.getCount(i)) + "원");
        }
        System.out.println("--------------------------------");
        System.out.println("결 제 금 액:   " + (int)payment + "원");
        System.out.println("공 급 가 액:   " + (int)supplyAmount + "원");
        System.out.println("부 가 세 액:   " + (int)taxTotal + "원");
        System.out.println("--------------------------------");
        System.out.println("받은 현금:     " + (int)receivedCash + "원");
        System.out.println("거 스 름 돈:   " + (int)change + "원");
        System.out.println("================");

        // [Step 11] Sale 정보를 SaleDB에 저장
        saleDB[saleSize] = sale;
        saleSize = saleSize + 1;
    }
}