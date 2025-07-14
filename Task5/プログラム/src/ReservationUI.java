import java.util.Scanner;

public class ReservationUI {
    // csvファイルのために追加 - ユーザー入力用のスキャナ（クラス図にはないが実装のため必要）
    private Scanner scanner = new Scanner(System.in);

    // クラス図から作成 - 日付と人数の入力を受け付ける
    // クラス図では戻り値voidだが、実装では文字列配列を返す
    public String[] inputDateAndPeopleNum() {
        System.out.println("=== 予約画面 ===");
        System.out.print("チェックイン日 (例: 2025-07-11): ");
        String checkinDate = scanner.next();
        System.out.print("チェックアウト日 (例: 2025-07-12): ");
        String checkoutDate = scanner.next();
        System.out.print("人数 (例: 2): ");
        while (!scanner.hasNextInt()) {
            System.out.print("数字で入力してください (例: 2): ");
            scanner.next();
        }
        int peopleNum = scanner.nextInt();
        return new String[] { checkinDate, checkoutDate, String.valueOf(peopleNum) };
    }

    // クラス図から作成 - 部屋グレードの選択を受け付ける
    // クラス図では戻り値voidだが、実装では選択値を返す
    public int selectGrade() {
        System.out.println("部屋グレードを選択してください: 0=スタンダード 1=デラックス 2=スイート");
        while (!scanner.hasNextInt()) {
            System.out.print("数字で入力してください: ");
            scanner.next();
        }
        int grade = scanner.nextInt();
        return grade;
    }

    // クラス図から作成 - プランの選択を受け付ける
    // クラス図では戻り値voidだが、実装では選択値を返す
    public int selectPlan() {
        System.out.println("プランを選択してください: 0=プランA 1=プランB 2=プランC");
        while (!scanner.hasNextInt()) {
            System.out.print("数字で入力してください: ");
            scanner.next();
        }
        int plan = scanner.nextInt();
        return plan;
    }

    // クラス図から作成 - ユーザー情報の入力を受け付ける
    // クラス図では戻り値voidだが、実装では文字列配列を返す
    public String[] inputUserInfo() {
        scanner.nextLine(); // バッファクリア
        System.out.print("名前 (例: 山田太郎): ");
        String name = scanner.nextLine();
        System.out.print("メールアドレス (例: yamada@example.com): ");
        String mail = scanner.nextLine();
        System.out.print("電話番号 (例: 090-1234-5678): ");
        String tel = scanner.nextLine();
        return new String[] { name, mail, tel };
    }

    // クラス図から作成 - ユーザー情報の正確性確認を受け付ける
    // クラス図では戻り値voidだが、実装では確認結果を返す
    public boolean inputUserInfoCorrectness() {
        System.out.print("入力内容は正しいですか？ (y/n): ");
        String ans = scanner.next();
        return ans.equalsIgnoreCase("y");
    }

    // クラス図から作成 - 各グレードの価格と空室数を表示する（privateメソッド）
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showEachGradePriceAndVacantRoomNum(int price, int roomNum) {
        System.out.println("グレードごとの価格: " + price + "円、空室数: " + roomNum);
    }

    // csvファイルのために追加 - 各グレードの情報を表示するオーバーロードメソッド（クラス図にはない）
    // コラボレーション図 1.2に従って各グレードの情報を表示するオーバーロードメソッド
    public void showEachGradePriceAndVacantRoomNum(java.util.Map<String, Integer> roomStatus) {
        System.out.println("=== 空室状況 ===");
        System.out
                .println("スタンダード: " + roomStatus.get("standard_price") + "円、空室数: " + roomStatus.get("standard_rooms"));
        System.out.println("デラックス: " + roomStatus.get("deluxe_price") + "円、空室数: " + roomStatus.get("deluxe_rooms"));
        System.out.println("スイート: " + roomStatus.get("suite_price") + "円、空室数: " + roomStatus.get("suite_rooms"));
        System.out.println("================");
    }

    // クラス図から作成 - プラン情報を表示する（privateメソッド）
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showPlan(int planAPrice, int planBPrice, int planCPrice) {
        System.out.println("プランA: " + planAPrice + "円");
        System.out.println("プランB: " + planBPrice + "円");
        System.out.println("プランC: " + planCPrice + "円");
    }

    // クラス図から作成 - 警告メッセージを表示する（privateメソッド）
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showAlert() {
        System.out.println("[警告] 入力内容に誤りがあります。もう一度ご確認ください。");
    }

    // クラス図から作成 - ユーザー情報確認を表示する（privateメソッド）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showUserinfoCheck() {
        System.out.println("入力された個人情報を確認してください。");
    }

    // csvファイルのために追加 - ユーザー情報確認を詳細表示するオーバーロードメソッド（クラス図にはない）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    public void showUserinfoCheck(String name, String mailAddress, String telephoneNo) {
        System.out.println("=== 個人情報確認 ===");
        System.out.println("名前: " + name);
        System.out.println("メールアドレス: " + mailAddress);
        System.out.println("電話番号: " + telephoneNo);
        System.out.println("上記の内容で正しいですか？");
    }

    // クラス図から作成 - 予約情報を表示する（privateメソッド）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // クラス図ではprivateだが、実装ではpublicで作成
    public void showReservationInfo(int reservationNo) {
        System.out.println("=== 予約完了 ===");
        System.out.println("予約番号: " + reservationNo);
        System.out.println("予約が完了しました。");
    }

    // csvファイルのために追加 - 予約詳細情報を表示するオーバーロードメソッド（クラス図にはない）
    // コラボレーション図に従って予約完了時に詳細情報を表示
    public void showReservationInfo(java.util.Map<String, Object> reservationInfo) {
        System.out.println("=== 予約完了 ===");
        System.out.println("予約番号: " + reservationInfo.get("reservation_no"));
        System.out.println("チェックイン日: " + reservationInfo.get("checkin_date"));
        System.out.println("チェックアウト日: " + reservationInfo.get("checkout_date"));
        System.out.println("人数: " + reservationInfo.get("people_num") + "名");
        System.out.println("部屋番号: " + reservationInfo.get("room_no"));
        System.out.println("プラン: " + reservationInfo.get("plan_name"));
        System.out.println("合計金額: " + reservationInfo.get("price") + "円");
        System.out.println("予約が完了しました。");
        System.out.println("==================");
    }

    // csvファイルのために追加 - ユーザー情報を再入力させる（コラボレーション図 5.2）
    // コラボレーション図にあるがクラス図にはないメソッド
    public String[] inputUserInfoAgain() {
        System.out.println("[再入力] 個人情報を再度入力してください。");
        return inputUserInfo(); // 既存のinputUserInfoメソッドを再利用
    }
}
