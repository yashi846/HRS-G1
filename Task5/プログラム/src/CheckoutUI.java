import java.util.Scanner;

public class CheckoutUI {
    // csvファイルのために追加 - ユーザー入力用のスキャナ（クラス図にはないが実装のため必要）
    private Scanner scanner = new Scanner(System.in);

    // クラス図から作成 - 部屋番号の入力を受け付ける
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図では戻り値voidだが、実装では部屋番号を返す
    public int inputRoomNo() {
        System.out.println("=== チェックアウト画面 ===");
        System.out.print("部屋番号を入力してください: ");
        while (!scanner.hasNextInt()) {
            System.out.print("数字で入力してください: ");
            scanner.next();
        }
        int roomNo = scanner.nextInt();
        return roomNo;
    }

    // クラス図から作成 - チェックアウト完了処理
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図では戻り値voidで実装
    public void completeCheckout() {
        System.out.println("チェックアウト処理を実行します。");
    }

    // クラス図から作成 - 予約内容を表示する（privateメソッド）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showReservationContents(int price) {
        System.out.println("宿泊料: " + price + "円");
    }

    // クラス図から作成 - チェックアウト完了を表示する（privateメソッド）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showCheckoutComplete() {
        System.out.println("チェックアウトが完了しました。ご利用ありがとうございました。");
    }

    // クラス図から作成 - 警告メッセージを表示する（privateメソッド）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showAlert() {
        System.out.println("[警告] 予約情報が見つかりません。部屋番号を確認してください。");
    }
}
