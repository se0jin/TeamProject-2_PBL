
/**
 * Beverages Products를 상속받고 TAX를 구현하는 음료 상품 클래스 (세금 10%)
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class Beverages extends Products implements TAX {
    private static final double TAX_RATE = 0.1;   // 세율 10%

    public Beverages(int productID, String name, int price) {
        super(productID, name, price);   // 부모 생성자 호출 (5장 super)
    }

    // 부모의 추상 메소드 오버라이딩 (5장)
    // 세금 = 표시가격 - (표시가격 / (1 + 세율))
    public double CalculateTax() {
        return price - (price / (1 + TAX_RATE));
    }
}