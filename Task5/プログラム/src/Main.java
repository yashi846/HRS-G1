import java.util.Scanner;

public class Main {
    // csvファイルのために追加 - メインメソッド（クラス図にはないが実装のため必要）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // ホテル予約システムのメニュー制御とユーザーインターフェース統合
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("ホテル予約システム");
            System.out.println("1.予約 2.チェックイン 3.チェックアウト 4.予約キャンセル 0.終了");
            int sel = sc.nextInt();
            if (sel == 0)
                break;
            switch (sel) {
                case 1:
                    ReservationUI reservationUI = new ReservationUI();
                    String[] datePeople = reservationUI.inputDateAndPeopleNum();
                    ReservationControl reservationControl = new ReservationControl();
                    // コラボレーション図 1.1に従ってgetRoomStatusを呼び出し
                    java.util.Map<String, Integer> status = reservationControl.getRoomStatus(datePeople[0],
                            datePeople[1], Integer.parseInt(datePeople[2]));
                    if (status == null) {
                        reservationUI.showAlert();
                        break;
                    }
                    // コラボレーション図 1.2に従ってshowEachGradePriceAndVacantRoomNumを呼び出し
                    reservationUI.showEachGradePriceAndVacantRoomNum(status);
                    int grade = reservationUI.selectGrade();
                    Room room = reservationControl.allocateRoom(grade);
                    if (room == null) {
                        reservationUI.showAlert();
                        break;
                    }
                    int[] planPrices = reservationControl.getPlanInfo();
                    reservationUI.showPlan(planPrices[0], planPrices[1], planPrices[2]);
                    int planType = reservationUI.selectPlan();
                    Plan plan = null;
                    switch (planType) {
                        case 0:
                            plan = new PlanA(planPrices[0]);
                            break;
                        case 1:
                            plan = new PlanB(planPrices[1]);
                            break;
                        case 2:
                            plan = new PlanC(planPrices[2]);
                            break;
                    }
                    // コラボレーション図5〜6: 個人情報入力と確認のループ
                    UserInfo userInfo = null;
                    while (userInfo == null) {
                        String[] userInfoArr = reservationUI.inputUserInfo();
                        reservationUI.showUserinfoCheck(userInfoArr[0], userInfoArr[1], userInfoArr[2]);
                        boolean correct = reservationUI.inputUserInfoCorrectness();
                        if (correct) {
                            userInfo = reservationControl.createUserInfo(userInfoArr[0], userInfoArr[1],
                                    userInfoArr[2]);
                        } else {
                            // コラボレーション図6: 個人情報が正しくない場合は再入力
                            reservationUI.showAlert();
                            // ループを継続して再入力
                        }
                    }
                    Reservation reservation = reservationControl.createReservation(datePeople[0], datePeople[1],
                            Integer.parseInt(datePeople[2]), room, plan, userInfo);
                    if (reservation == null) {
                        reservationUI.showAlert();
                        break;
                    }
                    // コラボレーション図に従って予約完了情報を詳細表示
                    reservationUI.showReservationInfo(reservation.getReservationInfo());
                    break;
                case 2:
                    CheckinUI checkinUI = new CheckinUI();
                    int reservationNo = checkinUI.inputReservationNum();
                    CheckinControl checkinControl = new CheckinControl();
                    java.util.Map<String, Object> info = checkinControl.checkin(reservationNo);
                    if (info == null) {
                        checkinUI.showAlert();
                    } else {
                        int roomNo = (int) info.get("room_no");
                        checkinUI.showCheckinComplete(roomNo);
                    }
                    break;
                case 3:
                    CheckoutUI checkoutUI = new CheckoutUI();
                    int roomNo = checkoutUI.inputRoomNo();
                    CheckoutControl checkoutControl = new CheckoutControl();
                    java.util.Map<String, Object> reservationInfo = checkoutControl.findReservationByRoomNo(roomNo);
                    if (reservationInfo == null) {
                        checkoutUI.showAlert();
                    } else {
                        int price = (int) reservationInfo.get("price");
                        checkoutUI.showReservationContents(price);
                        checkoutUI.completeCheckout();
                        checkoutControl.completeCheckOut(roomNo);
                        checkoutUI.showCheckoutComplete();
                    }
                    break;
                case 4:
                    // 予約キャンセル機能
                    CancelUI cancelUI = new CancelUI();
                    int reservationNoToCancel = cancelUI.inputReservationNo();
                    CancelProcess cancelProcess = new CancelProcess();
                    boolean cancelResult = cancelProcess.searchReservationInfo(reservationNoToCancel);
                    if (cancelResult) {
                        cancelUI.showCancelProcessDone();
                    } else {
                        cancelUI.showAlert();
                    }
                    break;
                default:
                    System.out.println("不正な入力です");
            }
        }
        sc.close();
    }
}
