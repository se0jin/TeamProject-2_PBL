/**
 * Products 모든 상품의 공통 속성과 동작을 정의하는 추상 클래스 (5장 추상클래스)
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public abstract class Products {
    protected long    productID;  // 상품 ID (바코드)
    protected String name;       // 상품명
    protected int    price;      // 상품 가격 (세금 포함)
 
    /**
     * Products 생성자 - 상품 ID, 이름, 가격을 초기화한다.
     *
     * @param  productID  상품 고유 ID
     * @param  name       상품명
     * @param  price      판매 가격 (세금 포함)
     */
    public Products(long productID, String name, int price) {
        this.productID = productID;
        this.name      = name;
        this.price     = price;
    }
 
    /**
     * 세금을 계산하는 추상 메소드 - 하위 클래스에서 반드시 오버라이딩 (5장)
     *
     * @return 계산된 세금액 (double)
     */
    public abstract double CalculateTax();
 
    /**
     * 상품 ID를 반환한다.
     *
     * @return 상품 ID (long)
     */
    public long getProductID() {
        return productID;
    }
 
    /**
     * 상품명을 반환한다.
     *
     * @return 상품명 (String)
     */
    public String getName() {
        return name;
    }
 
    /**
     * 상품 가격을 반환한다.
     *
     * @return 상품 가격 (int)
     */
    public int getPrice() {
        return price;
    }
}
