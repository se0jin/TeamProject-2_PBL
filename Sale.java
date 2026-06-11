/**
 * Sale 한 번의 판매 거래에서 구매된 상품 목록과 합계를 관리하는 클래스
 * Products 객체 대신 기본값(primitive)만 저장 → Sale→Products 의존 화살표 미생성
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class Sale {
    private String[] names;   // 상품명
    private int[] prices;  // 상품 가격
    private int[] counts;  // 수량
    private double[] taxes;   // 상품별 세금 (역산값)
    private int size;    // 담긴 항목 수
 
    /**
     * Sale 기본 생성자 - 배열을 초기화한다. (3장 배열, 4장 생성자)
     *
     */
    public Sale() {
        names = new String[100];
        prices = new int[100];
        counts = new int[100];
        taxes = new double[100];
        size = 0;
    }
 
    /**
     * 상품 항목을 Sale에 추가한다.
     *
     * @param  name   상품명
     * @param  price  상품 가격
     * @param  count  수량
     * @param  tax    역산된 세금액
     */
    public void addItem(String name, int price, int count, double tax) {
        names[size] = name;
        prices[size] = price;
        counts[size] = count;
        taxes[size] = tax;
        size = size + 1;
    }
 
    /**
     * 전체 결제 금액을 계산한다. (3장 for문)
     *
     * @return 세금 포함 총 결제 금액 (double)
     */
    public double calculate() {
        double total = 0;
        for (int i = 0; i < size; i++) {
            total = total + prices[i] * counts[i];
        }
        return total;
    }
 
    /**
     * 전체 세금 합계를 계산한다.
     *
     * @return 세금 합계 (double)
     */
    public double calculateTax() {
        double tax = 0;
        for (int i = 0; i < size; i++) {
            tax = tax + taxes[i] * counts[i];
        }
        return tax;
    }
 
    /**
     * 담긴 항목 수를 반환한다.
     *
     * @return 항목 수 (int)
     */
    public int getSize() {
        return size;
    }
 
    /**
     * 해당 인덱스의 상품명을 반환한다.
     *
     * @param  index  배열 인덱스
     * @return 상품명 (String)
     */
    public String getName(int index) {
        return names[index];
    }
 
    /**
     * 해당 인덱스의 가격을 반환한다.
     *
     * @param  index  배열 인덱스
     * @return 가격 (int)
     */
    public int getPrice(int index) {
        return prices[index];
    }
 
    /**
     * 해당 인덱스의 수량을 반환한다.
     *
     * @param  index  배열 인덱스
     * @return 수량 (int)
     */
    public int getCount(int index) {
        return counts[index];
    }
}
