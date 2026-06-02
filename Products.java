
/**
 * Products 모든 상품의 공통 속성(이름, 가격)을 정의하는 추상 부모 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public abstract class Products 
{
    protected String name;
    protected int price;
 
    /**
     * Products 생성자 (4장 - Constructor)
     *
     * @param name  상품명
     * @param price 상품 가격
     */
    public Products(String name, int price) {
        this.name  = name; 
        this.price = price;
    }
 
    /**
     * 상품명 반환 getter (4장 - 캡슐화/정보은닉)
     *
     * @return 상품명 (String)
     */
    public String getName() {
        return name;
    }
 
    /**
     * 상품 가격 반환 getter (4장 - 캡슐화/정보은닉)
     *
     * @return 상품 가격 (int)
     */
    public int getPrice() {
        return price;
    }
 
    /**
     * 세금 계산 추상 메소드 (5장 - Abstract Method)
     * 
     * @return 세금액 (double)
     */
    public abstract double CalculateTax();
}