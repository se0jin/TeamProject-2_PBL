import java.util.Scanner;

/**
 * POST POS 단말기의 핵심 기능을 담당하는 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class POST{

    private Sale sale;
    private TAX tax;
    private Products[] productDB;
    private Scanner scan;
    private int dbSize;
    private double receivedCash;

    public POST() {
        this.sale = new Sale();
        this.scan = new Scanner(System.in);
        initProductDB();
    }

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

    public double sale_calculate() {
        return sale.sale_calculate();
    }

    public double calculate() {
        return sale_calculate() + CalculateTax();
    }

    public double event_calculate() {
        return calculate();
    }

    public double CalculateTax() {
        return sale.tax_calculate();
    }

    public void inputCash() {
        System.out.println("==========================================");
        System.out.println("  상품 합계 : " + (int)sale_calculate() + " 원");
        System.out.println("  세금 합계 : " + (int)CalculateTax() + " 원");
        System.out.println("  최종 합계 : " + (int)event_calculate() + " 원");
        System.out.println("==========================================");
        System.out.print("  받은 현금 금액 입력 (취소: 0): ");
        receivedCash = scan.nextDouble();
    }

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
        System.out.println("  받은 현금 : " + (int)receivedCash + " 원");
        System.out.println("  지불 금액 : " + (int)event_calculate() + " 원");
        System.out.println("  거스름돈  : " + (int)change + " 원");
        return true;
    }

    public void printReceipt() {
        int count = sale.getProductCount();

        System.out.println();
        System.out.println("==========================================");
        System.out.println("       [ 영 수 증  /  RECEIPT ]          ");
        System.out.println("==========================================");
        System.out.println("상품명       단가   수량    소계     세금");
        System.out.println("------------------------------------------");

        for (int i = 0; i < count; i++) {
            String name     = sale.getName(i);
            int    price    = sale.getPrice(i);
            int    qty      = sale.getQuantity(i);
            double tax      = sale.getTax(i);
            int    subtotal = price * qty;

            System.out.println(name + " " + price + "원 " + qty + "개 " + subtotal + "원 " + (int)(tax * qty) + "원");
        }

        System.out.println("------------------------------------------");
        System.out.println("  상품 합계 : " + (int)sale_calculate() + " 원");
        System.out.println("  세금 합계 : " + (int)CalculateTax() + " 원");
        System.out.println("  최종 합계 : " + (int)event_calculate() + " 원");
        System.out.println("==========================================");
        System.out.println("  받은 현금 : " + (int)receivedCash + " 원");
        System.out.println("  거스름돈  : " + (int)(receivedCash - event_calculate()) + " 원");
        System.out.println("==========================================");
        System.out.println("      감사합니다! 또 오세요 :)            ");
        System.out.println("==========================================");
    }

    public void run() {
        System.out.println("=========================================");
        System.out.println("    POST   ");
        System.out.println("=========================================");
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
                System.out.println("  [등록] " + found.getName() + " | 가격: " + found.getPrice() + "원 | 세금(10%): " + (int)found.CalculateTax() + "원");
                sale.addProduct(found.getName(), found.getPrice(), found.CalculateTax());
            }
        }

        System.out.println();
        System.out.println("  현재 등록 상품 합계: " + (int)sale_calculate() + " 원");
        inputCash();
        boolean proceed = cashReceipt();

        if (proceed) {
            printReceipt();
            System.out.println();
            System.out.println("  [시스템] Sale 정보가 저장되었습니다.");
        }
    }

    private Products findProductByName(String name) {
        for (int i = 0; i < dbSize; i++) {
            if (productDB[i].getName().equals(name)) {
                return productDB[i];
            }
        }
        return null;
    }

    private void printProductDB() {
        System.out.println("  === 등록 상품 목록 ===");
        System.out.println("  상품명           가격      종류(세율)");
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
            System.out.println("  " + p.getName() + "  " + p.getPrice() + "원  " + typeLabel);
        }
        System.out.println("  " + "-".repeat(38));
    }
}