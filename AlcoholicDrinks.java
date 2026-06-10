/**
 * AlcoholicDrinks Products를 상속받고 TAX를 구현하는 주류 상품 클래스
 * 주류 종류에 따라 세율이 다르게 적용됨 (다형성 - 5장)
 * 표시 가격에 세금이 이미 포함되어 있음 (세금 역산 방식)
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class AlcoholicDrinks extends Products implements TAX {

    // 주류 종류 상수 정의 (2장 - final 상수)
    public static final int MAKGEOLLI = 0;   // 막걸리
    public static final int BEER      = 1;   // 맥주
    public static final int SOJU      = 2;   // 증류주
    public static final int WINE      = 3;   // 와인
    public static final int WHISKEY   = 4;   // 양주

    private double TAX_RATE;   // 세율 (종류에 따라 결정)

    public AlcoholicDrinks(int productID, String name, int price, int type) {
        super(productID, name, price);   

        // 다형성: 같은 CalculateTax() 메소드가 type에 따라 다른 결과 반환
        if (type == MAKGEOLLI) {
            TAX_RATE = 0.05;   // 막걸리 주류세 5%
        } else if (type == BEER) {
            TAX_RATE = 0.72;   // 맥주 주류세 72%
        } else if (type == SOJU) {
            TAX_RATE = 0.72;   // 증류주 주류세 72%
        } else if (type == WINE) {
            TAX_RATE = 0.30;   // 와인 주류세 30%
        } else if (type == WHISKEY) {
            TAX_RATE = 0.72;   // 양주 주류세 72%
        } else {
            TAX_RATE = 0.10;   // 기본값 10%
        }
    }

    /**
     * Products의 추상메소드를 오버라이딩하는 메소드
     * 세금 = 표시가격 - (표시가격 / (1 + 세율))
     */
    public double CalculateTax() {
        return price - (price / (1 + TAX_RATE));
    }
}