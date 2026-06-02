import java.util.Scanner;

/**
 * POST POS 단말기의 핵심 기능을 담당하는 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class POST implements TAX {

    private Sale sale;
    private Products[] productDB;   // 상품 데이터베이스
    private Scanner scan;
    private int dbSize;             // DB에 등록된 상품 수
    private double receivedCash;

    /**
     * Sale 객체 생성, 상품 DB 초기화, Scanner 초기화를 수행
     *
     * [4장] this 키워드로 멤버변수 명확화
     */
    public POST() {
        this.sale = new Sale();
        this.scan = new Scanner(System.in);
        initProductDB();
    }

    /**
     * 상품 데이터베이스 초기화 메소드 (private - 내부 초기화 전용)
     *
     * [5장 Upcasting]
     * Products[] 배열에 Beverages(자식), AlcoholicDrinks(자식) 객체를 저장.
     * 자식 객체 → 부모 타입으로 자동 변환(Upcasting).
     */
    private void initProductDB() {
        productDB = new Products[10];

        productDB[0] = new Beverages("아메리카노", 3000);
        productDB[1] = new Beverages("카페라떼",   4000);
        productDB[2] = new Beverages("녹차",       2500);
        productDB[3] = new Beverages("오렌지주스", 3500);

        productDB[4] = new AlcoholicDrinks("맥주",   5000);
        productDB[5] = new AlcoholicDrinks("소주",   4500);
        productDB[6] = new AlcoholicDrinks("막걸리", 3000);
        productDB[7] = new AlcoholicDrinks("와인",  15000);

        dbSize = 8;
    }

    /**
     * 판매 합계 계산 메소드 (Sale에 위임)
     *
     * @return 상품 가격 합계 (double)
     */
    public double sale_calculate() {
        return sale.sale_calculate();
    }

    /**
     * 세금 포함 최종 합계 계산 메소드
     *
     * @return 상품 합계 + 세금 합계 (double)
     */
    public double calculate() {
        return sale_calculate() + CalculateTax();
    }

    /**
     * 이벤트/할인 적용 계산 메소드
     * 현재는 기본 합계 반환 (추후 이벤트 로직 확장 가능)
     *
     * @return 이벤트 적용 후 최종 금액 (double)
     */
    public double event_calculate() {
        return calculate();
    }

    /**
     * TAX 인터페이스 구현 - 전체 판매 세금 합계 계산
     *
     * @return 전체 세금 합계 (double)
     */
    @Override
    public double CalculateTax() {
        return sale.tax_calculate();
    }

    /**
     * 현금 입력 처리 메소드
     */
    public void inputCash() {
        System.out.println("=".repeat(42));
        System.out.printf("  상품 합계 : %,10.0f 원%n", sale_calculate());
        System.out.printf("  세금 합계 : %,10.0f 원%n", CalculateTax());
        System.out.printf("  최종 합계 : %,10.0f 원%n", event_calculate());
        System.out.println("=".repeat(42));
        System.out.print("  받은 현금 금액 입력 (취소: 0): ");
        receivedCash = scan.nextDouble();
    }

    /**
     * 거스름돈 계산 및 출력 메소드
     *
     * @return true: 정상 거래 진행 / false: 판매 취소
     */
    public boolean cashReceipt() {
        if (receivedCash == 0) {
            System.out.println();
            System.out.println("  [알림] Cashier가 판매를 취소하였습니다.");
            sale.reset();
            return false;
        }

        double change = receivedCash - event_calculate();
        System.out.println();
        System.out.println("  [거스름돈 계산]");
        System.out.printf("  받은 현금 : %,10.0f 원%n", receivedCash);
        System.out.printf("  지불 금액 : %,10.0f 원%n", event_calculate());
        System.out.printf("  거스름돈  : %,10.0f 원%n", change);
        return true;
    }

    /**
     * 영수증 출력 메소드
     */
    public void printReceipt() {
        int count = sale.getProductCount();

        System.out.println();
        System.out.println("==========================================");
        System.out.println("          [ 영 수 증  /  RECEIPT ]       ");
        System.out.println("==========================================");
        System.out.printf("%-12s %5s %4s %8s %8s%n",
            "상품명", "단가", "수량", "소계", "세금");
        System.out.println("------------------------------------------");

        for (int i = 0; i < count; i++) {
            String name     = sale.getName(i);
            int    price    = sale.getPrice(i);
            int    qty      = sale.getQuantity(i);
            double tax      = sale.getTax(i);
            int    subtotal = price * qty;

            System.out.printf("%-12s %,5d %3d개 %,7d원 %,6.0f원%n",
                name, price, qty, subtotal, tax * qty);
        }

        System.out.println("------------------------------------------");
        System.out.printf("  상품 합계  : %,12.0f 원%n", sale_calculate());
        System.out.printf("  세금 합계  : %,12.0f 원%n", CalculateTax());
        System.out.printf("  최종 합계  : %,12.0f 원%n", event_calculate());
        System.out.println("==========================================");
        System.out.printf("  받은 현금  : %,12.0f 원%n", receivedCash);
        System.out.printf("  거스름돈   : %,12.0f 원%n", receivedCash - event_calculate());
        System.out.println("==========================================");
        System.out.println("      감사합니다! 또 오세요 :)            ");
        System.out.println("==========================================");
    }

    /**
     * POS 시스템 메인 실행 메소드
     */
    public void run() {
        System.out.println("==========================================");
        System.out.println("    POS (Point Of Sale Terminal) 시작    ");
        System.out.println("==========================================");
        System.out.println();

        printProductDB();

        System.out.println();
        System.out.println("  상품명 입력 (완료: 'done', 취소: 'cancel')");
        System.out.println("------------------------------------------");

        boolean scanning = true;

        while (scanning) {
            System.out.print("  상품명: ");
            String inputName = scan.next().trim();

            if (inputName.equalsIgnoreCase("done")) {
                if (sale.getProductCount() == 0) {
                    System.out.println("  [알림] 등록된 상품이 없습니다. 상품을 먼저 입력하세요.");
                    continue;
                }
                scanning = false;
                continue;
            }

            if (inputName.equalsIgnoreCase("cancel")) {
                System.out.println("  [알림] 판매를 취소합니다.");
                sale.reset();
                return;
            }

            Products found = findProductByName(inputName);

            if (found == null) {
                System.out.println("  [오류] '" + inputName + "' 은(는) 등록되지 않은 상품입니다.");
            } else {
                System.out.printf("  [등록] %-12s | 가격: %,6d원 | 세금(10%%): %.0f원%n",
                    found.getName(),
                    found.getPrice(),
                    found.CalculateTax());

                sale.addProduct(found.getName(), found.getPrice(), found.CalculateTax());
            }
        }

        System.out.println();
        System.out.printf("  현재 등록 상품 합계: %,.0f 원%n", sale_calculate());
        inputCash();
        boolean proceed = cashReceipt();

        if (proceed) {
            printReceipt();
            System.out.println();
            System.out.println("  [시스템] Sale 정보가 저장되었습니다.");
        }
    }

    /**
     * 상품명으로 DB에서 상품을 검색하는 내부 메소드 (private)
     *
     * @param name 검색할 상품명
     * @return 찾은 Products 객체, 없으면 null
     */
    private Products findProductByName(String name) {
        for (int i = 0; i < dbSize; i++) {
            if (productDB[i].getName().equals(name)) {
                return productDB[i];
            }
        }
        return null;
    }

    /**
     * 상품 데이터베이스 목록 출력 메소드 (private - 내부 사용)
     */
    private void printProductDB() {
        System.out.println("  === 등록 상품 목록 ===");
        System.out.printf("  %-14s %8s  %s%n", "상품명", "가격", "종류(세율)");
        System.out.println("  " + "-".repeat(38));

        for (int i = 0; i < dbSize; i++) {
            Products p = productDB[i];
            String typeLabel;
            if (p instanceof AlcoholicDrinks) {
                typeLabel = "주류 (세율 10%)";
            } else if (p instanceof Beverages) {
                typeLabel = "음료 (세율 10%)";
            } else {
                typeLabel = "일반";
            }

            System.out.printf("  %-14s %,7d원  %s%n",
                p.getName(), p.getPrice(), typeLabel);
        }
        System.out.println("  " + "-".repeat(38));
    }
}