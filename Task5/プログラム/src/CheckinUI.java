import java.util.Scanner;

public class CheckinUI {
    // csvファイルのために追加 - ユーザー入力用のスキャナ（クラス図にはないが実装のため必要）
    private Scanner scanner = new Scanner(System.in);

    // クラス図から作成 - 予約番号の入力を受け付ける
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図では戻り値voidだが、実装では予約番号を返す
    public int inputReservationNum() {
        System.out.println("=== チェックイン画面 ===");
        System.out.print("予約番号を入力してください: ");
        while (!scanner.hasNextInt()) {
            System.out.print("数字で入力してください: ");
            scanner.next();
        }
        int reservationNo = scanner.nextInt();
        return reservationNo;
    }

    // クラス図から作成 - チェックイン完了を表示する（privateメソッド）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showCheckinComplete(int roomNo) {
        System.out.println("チェックインが完了しました。");
        System.out.println("部屋番号: " + roomNo);
        System.out.println("ごゆっくりお過ごしください。");
    }

    // クラス図から作成 - 警告メッセージを表示する（privateメソッド）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showAlert() {
        System.out.println("[警告] 予約情報が見つかりません。予約番号を確認してください。");
    }
}
