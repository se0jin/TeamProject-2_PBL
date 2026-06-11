/**
 * Beverages Products를 상속받고 TAX를 구현하는 음료 상품 클래스 (세율 10%)
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class Beverages extends Products implements TAX {
    private static final double TAX_RATE = 0.1;
 
    /**
     * Beverages 생성자 - 부모 클래스 생성자 호출 (5장 super)
     *
     * @param  productID  상품 고유 ID
     * @param  name       상품명
     * @param  price      판매 가격 (세금 포함)
     */
    public Beverages(int productID, String name, int price) {
        super(productID, name, price);
    }
 
    /**
     * Products의 추상메소드를 오버라이딩 - 세금 역산 계산 (5장 오버라이딩)
     * 세금 = 표시가격 - (표시가격 / (1 + 세율))
     *
     * @return 역산된 세금액 (double)
     */
    public double CalculateTax() {
        return price - (price / (1 + TAX_RATE));
    }
}
