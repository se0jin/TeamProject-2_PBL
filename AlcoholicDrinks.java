
/**
 * AlcoholicDrinks Products를 상속받고 TAX를 구현하는 주류 상품 클래스 (세금 10%)
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class AlcoholicDrinks extends Products implements TAX {
    private static final double TAX_RATE = 0.1;   // 세율 10%

    public AlcoholicDrinks(int productID, String name, int price) {
        super(productID, name, price);
    }

    public double CalculateTax() {
        return price * TAX_RATE;
    }
}