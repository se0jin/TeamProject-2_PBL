/**
 * AlcoholicDrinks Products를 상속받고 TAX를 구현하는 주류 상품 클래스
 * 주류 종류에 따라 세율이 다르게 적용됨 (5장 다형성)
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class AlcoholicDrinks extends Products implements TAX {
    public static final int MAKGEOLLI = 0;  // 막걸리
    public static final int BEER      = 1;  // 맥주
    public static final int SOJU      = 2;  // 증류주
    public static final int WINE      = 3;  // 와인
    public static final int WHISKEY   = 4;  // 양주
 
    private double TAX_RATE;  // 종류에 따라 결정되는 세율
 
    /**
     * AlcoholicDrinks 생성자 - 주류 종류에 따라 세율을 설정한다.
     *
     * @param  productID  상품 고유 ID
     * @param  name       상품명
     * @param  price      판매 가격 (세금 포함)
     * @param  type       주류 종류 상수 (MAKGEOLLI/BEER/SOJU/WINE/WHISKEY)
     */
    public AlcoholicDrinks(int productID, String name, int price, int type) {
        super(productID, name, price);
        if (type == MAKGEOLLI) {
            TAX_RATE = 0.05;
        } else if (type == BEER || type == SOJU || type == WHISKEY) {
            TAX_RATE = 0.72;
        } else if (type == WINE) {
            TAX_RATE = 0.30;
        } else {
            TAX_RATE = 0.10;
        }
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
