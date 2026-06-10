
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
     *
     * @param args 커맨드라인 인수 (사용 안 함)
     */
    public static void main(String[] args) {
        MyApp app  = new MyApp();
        app.post   = new POST();

        app.post.buyItemsWithCash();
    }
}