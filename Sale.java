/**
 * Sale 한 번의 판매 거래에서 구매된 상품 목록과 합계를 관리하는 클래스
 * Products 객체 대신 기본값(primitive)만 저장 → Sale→Products 의존 화살표 미생성
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class Sale {
    private String[] names;    // 상품명
    private int[]    prices;   // 상품 가격
    private int[]    counts;   // 수량
    private double[] taxes;    // 상품별 세금 (역산값)
    private int      size;     // 담긴 항목 수

    public Sale() {
        names  = new String[100];   // 최대 100개 (3장 배열 생성)
        prices = new int[100];
        counts = new int[100];
        taxes  = new double[100];
        size   = 0;
    }

    // 상품 추가 (primitives만 저장)
    public void addItem(String name, int price, int count, double tax) {
        names[size]  = name;
        prices[size] = price;
        counts[size] = count;
        taxes[size]  = tax;
        size = size + 1;
    }

    // 결제 금액 합계 (세금 포함 표시가격 x 수량) - 3장 for문
    public double calculate() {
        double total = 0;
        for (int i = 0; i < size; i++) {
            total = total + prices[i] * counts[i];
        }
        return total;
    }

    // 세금 합계
    public double calculateTax() {
        double tax = 0;
        for (int i = 0; i < size; i++) {
            tax = tax + taxes[i] * counts[i];
        }
        return tax;
    }

    public int getSize() {
        return size;
    }

    public String getName(int index) {
        return names[index];
    }

    public int getPrice(int index) {
        return prices[index];
    }

    public int getCount(int index) {
        return counts[index];
    }
}