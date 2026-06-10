
/**
 * MyApp POST 객체를 생성하고 실행하는 클래스
 *
 * @author (2팀)
 * @version (2026.06.02)
 */
public class MyApp 
{
    private POST post;
 
    /**
     * 프로그램 메인 메소드 - 진입점
     */
    public static void main(String[] args) {
        MyApp app  = new MyApp();
        app.post   = new POST();

        app.post.buyItemsWithCash();
    }
}