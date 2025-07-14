import java.util.Scanner;

// AIによる実装のためロジックが本来の意図と異なる可能性あり
// 予約キャンセル機能のユーザーインターフェース
public class CancelUI {
    private Scanner scanner;

    public CancelUI() {
        this.scanner = new Scanner(System.in);
    }

    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 予約番号を入力する
    public int inputReservationNo() {
        System.out.print("キャンセルする予約番号を入力してください: ");
        return scanner.nextInt();
    }

    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 警告を表示する
    public void showAlert() {
        System.out.println("該当する予約が見つかりません。");
    }

    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // キャンセル処理完了を表示する
    public void showCancelProcessDone() {
        System.out.println("予約のキャンセルが完了しました。");
    }
}
