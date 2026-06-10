/**
 * Sale 한 번의 판매 거래에서 구매된 상품 목록과 합계를 관리하는 클래스
 * 표시 가격에 세금이 포함되어 있으므로 calculate()가 곧 결제 금액
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class Sale {
    private Products[] products;   // 산 상품들
    private int[] count;           // 각 상품 수량
    private int size;              // 지금 담긴 개수

    public Sale() {
        products = new Products[100];   // 최대 100개 (3장 배열 생성)
        count = new int[100];
        size = 0;
    }

    // 상품 추가
    public void addProduct(Products p, int c) {
        products[size] = p;
        count[size] = c;
        size = size + 1;
    }

    // 결제 금액 합계 (세금 포함 표시가격 x 수량) - 3장 for문
    public double calculate() {
        double total = 0;
        for (int i = 0; i < size; i++) {
            total = total + products[i].getPrice() * count[i];
        }
        return total;
    }

    // 세금 합계 - 가격에서 역산 (다형성으로 자동 계산 - 5장)
    public double calculateTax() {
        double tax = 0;
        for (int i = 0; i < size; i++) {
            tax = tax + products[i].CalculateTax() * count[i];
        }
        return tax;
    }

    public int getSize() {
        return size;
    }

    public Products getProduct(int index) {
        return products[index];
    }

    public int getCount(int index) {
        return count[index];
    }

    public void reset() {
        size = 0;
    }
}