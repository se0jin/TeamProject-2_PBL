
/**
 * Beverages Products를 상속받고 TAX를 구현하는 음료 상품 클래스 (세금 10%)
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class Beverages extends Products implements TAX 
{
    private static final double TAX_RATE = 0.10;
 
    /**
     * Beverages 생성자 (5장 - Inheritance와 Constructor)
     *
     * @param name  음료명
     * @param price 음료 가격
     */
    public Beverages(String name, int price) {
        super(name, price);
    }
 
    /**
     * 세금 계산 메소드 오버라이딩 (5장 - Method Overriding + Interface 구현)
     *
     * @return 세금액 = price × 10%
     */
    @Override
    public double CalculateTax() {
        return price * TAX_RATE; 
    }
}