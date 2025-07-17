import java.util.Scanner;

public class CheckoutUI {
    // csvファイルのために追加 - ユーザー入力用のスキャナ（クラス図にはないが実装のため必要）
    private Scanner scanner = new Scanner(System.in);

    // 依存関係のため追加
    private CheckoutControl checkoutControl = new CheckoutControl();

    // クラス図から作成 - 部屋番号の入力を受け付ける
    public void inputRoomNo() {

        // 部屋番号を入力する
        System.out.println("=== チェックアウト画面 ===");
        System.out.print("部屋番号を入力してください: ");
        while (!scanner.hasNextInt()) {
            System.out.print("数字で入力してください: ");
            scanner.next();
        }
        int roomNo = scanner.nextInt();

        // 部屋番号から予約を検索する
        java.util.Map<String, Object> reservationInfo = checkoutControl.findReservationByRoomNo(roomNo);
        
        if (reservationInfo == null) {
            // [予約が存在しない]警告を表示する
            showAlert();
            // 再び部屋番号の入力を促す
            inputRoomNo();
        } else {
            // [予約が存在する]予約の内容を表示する
            int price = (int) reservationInfo.get("price");
            showReservationContents(price);
        }
    }

    // クラス図から作成 - チェックアウト完了処理
    public void completeCheckout() {
        System.out.println("チェックアウト処理を実行します。");
        checkoutControl.completeCheckOut();
        // チェックアウト完了の表示
        showCheckoutComplete();
    }

    // クラス図から作成 - 予約内容を表示する（privateメソッド）
    private void showReservationContents(int price) {
        System.out.println("宿泊料: " + price + "円");
    }

    // クラス図から作成 - チェックアウト完了を表示する（privateメソッド）
    private void showCheckoutComplete() {
        System.out.println("チェックアウトが完了しました。ご利用ありがとうございました。");
    }

    // クラス図から作成 - 警告メッセージを表示する（privateメソッド）
    private void showAlert() {
        System.out.println("[警告] 予約情報が見つかりません。部屋番号を確認してください。");
    }
}