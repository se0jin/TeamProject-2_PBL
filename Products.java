
/**
 * Products 모든 상품의 공통 속성(이름, 가격)을 정의하는 추상 부모 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public abstract class Products {
    protected int productID;   // 상품 번호(바코드로 입력받는 번호)
    protected String name;     // 상품명
    protected int price;       // 가격

    // 생성자 (4장)
    public Products(int productID, String name, int price) {
        this.productID = productID;
        this.name = name;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    // 추상 메소드 : 세금 계산은 자식이 구현 (5장)
    public abstract double CalculateTax();
}