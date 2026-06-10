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
    private Scanner scanner;
 
    public POST() {
        sale = new Sale();
        saleDB = new Sale[100];
        saleSize = 0;
        scanner = new Scanner(System.in);
 
        // 상품 DB 채우기 (배열에 객체 저장 - 4장 객체 배열)
        productDB = new Products[8];
        productDB[0] = new Beverages(1, "아메리카노", 3000);
        productDB[1] = new Beverages(2, "카페라떼", 4000);
        productDB[2] = new Beverages(3, "녹차", 2500);
        productDB[3] = new Beverages(4, "오렌지주스", 3500);
        productDB[4] = new AlcoholicDrinks(5, "맥주", 5000);
        productDB[5] = new AlcoholicDrinks(6, "소주", 4500);
        productDB[6] = new AlcoholicDrinks(7, "막걸리", 3000);
        productDB[7] = new AlcoholicDrinks(8, "와인", 15000);
    }
 
    public void buyItemsWithCash() {
        // 상품은 productDB에 미리 저장돼 있음 (화면에는 보여주지 않음)
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
                    System.out.println("오류: 없는 상품입니다.");
                } else {
                    System.out.print("수량 입력: ");
                    int qty = scanner.nextInt();
                    System.out.println("상품명: " + found.getName()
                        + ", 가격: " + found.getPrice() + "원");
                    sale.addProduct(found, qty);   // Sale 정보에 추가
                }
            }
        }
 
        // 상품이 하나도 없으면 종료
        if (sale.getSize() == 0) {
            System.out.println("구매한 상품이 없습니다.");
            return;
        }
 
        // 지불할 금액 계산 및 출력
        double productTotal = sale.calculate();
        double taxTotal = sale.calculateTax();
        double payment = productTotal + taxTotal;
        System.out.println("--------------------");
        System.out.println("상품 합계: " + productTotal + "원");
        System.out.println("세금 합계: " + taxTotal + "원");
        System.out.println("지불할 금액: " + payment + "원");
        System.out.println("--------------------");
 
        // 받은 현금 입력
        System.out.print("받은 현금 입력: ");
        receivedCash = scanner.nextDouble();
 
        // 받은 현금 >= 지불할 금액 ?
        if (receivedCash < payment) {
            System.out.println("현금이 부족합니다. 판매를 취소합니다.");
            sale.reset();
            return;
        }
 
        // 거스름돈 계산
        double change = receivedCash - payment;
 
        // 영수증 출력
        System.out.println("===== 영수증 =====");
        for (int i = 0; i < sale.getSize(); i++) {
            Products p = sale.getProduct(i);
            int c = sale.getCount(i);
            System.out.println(p.getName() + " x " + c + " = " + (p.getPrice() * c) + "원");
        }
        System.out.println("지불할 금액: " + payment + "원");
        System.out.println("받은 현금: " + receivedCash + "원");
        System.out.println("거스름돈: " + change + "원");
        System.out.println("================");
 
        // Sale 정보를 SaleDB에 저장
        saleDB[saleSize] = sale;
        saleSize = saleSize + 1;
        System.out.println("판매 정보가 SaleDB에 저장되었습니다.");
    }
}