import java.util.ArrayList;
/**
 * Sale 한 번의 판매 거래에서 구매된 상품 목록과 합계를 관리하는 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class Sale {
    private ArrayList<String> names; //판매 상품명 목록
    private ArrayList<Integer> prices; //판매 상품 단가 목록
    private ArrayList<Integer> quantities; //판매 상품 수량 목록
    private ArrayList<Double> taxes; //판매 상품 단위 세금 목록
 
    /**
     * Sale 생성자 (4장 - Constructor)
     * 각 항목의 ArrayList를 초기화한다.
     */
    public Sale() {
        names      = new ArrayList<String>();
        prices     = new ArrayList<Integer>();
        quantities = new ArrayList<Integer>();
        taxes      = new ArrayList<Double>();
    }
 
    /**
     * 판매 목록에 상품을 추가하는 메소드
     * 
     * @param name  상품명
     * @param price 상품 단가
     * @param tax   상품 단위 세금
     */
    public void addProduct(String name, int price, double tax) {
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(name)) {
                quantities.set(i, quantities.get(i) + 1);
                return;
            }
        }
        names.add(name);
        prices.add(price);
        quantities.add(1);
        taxes.add(tax);
    }
 
    /**
     * 현재 등록된 상품 종류 수 반환 getter (4장 - 캡슐화)
     *
     * @return 등록된 상품 종류 수 (int)
     */
    public int getProductCount() {
        return names.size();
    }
 
    /**
     * 상품명 반환 getter
     *
     * @param index 인덱스
     * @return 상품명 (String)
     */
    public String getName(int index) {
        return names.get(index);
    }
 
    /**
     * 단가 반환 getter
     *
     * @param index 인덱스
     * @return 단가 (int)
     */
    public int getPrice(int index) {
        return prices.get(index);
    }
 
    /**
     * 수량 반환 getter
     *
     * @param index 인덱스
     * @return 수량 (int)
     */
    public int getQuantity(int index) {
        return quantities.get(index);
    }
 
    /**
     * 단위 세금 반환 getter
     *
     * @param index 인덱스
     * @return 단위 세금 (double)
     */
    public double getTax(int index) {
        return taxes.get(index);
    }
 
    /**
     * 전체 상품 가격 합계 계산 메소드 (3장 - for 반복문)
     * 단가 × 수량을 합산
     *
     * @return 전체 상품 가격 합계
     */
    public double sale_calculate() {
        double total = 0;
        for (int i = 0; i < prices.size(); i++) {
            total += prices.get(i) * quantities.get(i);
        }
        return total;
    }
 
    /**
     * 전체 세금 합계 계산 메소드 (3장 - for 반복문)
     * 단위세금 × 수량을 합산
     *
     * @return 전체 세금 합계 (double)
     */
    public double tax_calculate() {
        double total = 0;
        for (int i = 0; i < taxes.size(); i++) {
            total += taxes.get(i) * quantities.get(i);
        }
        return total;
    }
 
    /**
     * 판매 목록 초기화(리셋) 메소드
     * 판매 취소 시 호출한다.
     */
    public void reset() {
        names      = new ArrayList<String>();
        prices     = new ArrayList<Integer>();
        quantities = new ArrayList<Integer>();
        taxes      = new ArrayList<Double>();
    }
}