/**
 * MyApp POST 객체를 생성하고 실행하는 메인 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class MyApp {
    private POST post;
 
    /**
     * 프로그램 진입점 - POST 객체를 생성하고 현금결제 메소드를 실행한다.
     */
    public static void main(String[] args) {
        MyApp app = new MyApp();
        app.post  = new POST();
        app.post.buyItemsWithCash();
    }
}
