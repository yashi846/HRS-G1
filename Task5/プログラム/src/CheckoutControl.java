import java.util.Map;

public class CheckoutControl {
    // csvファイルのために追加 - 予約リスト（クラス図にはないが実装のため必要）
    private java.util.Map<Integer, Reservation> reservationMap = new java.util.HashMap<>();

    // csvファイルのために追加 - 部屋管理用（クラス図にはないが実装のため必要）
    // コンストラクタ内で呼ばれているloadRoomStatusFromCsvで空室数が勝手に追加されるので、初めは全て0にセットしておく
    private Standard standard = new Standard(10000, 0);
    private Deluxe deluxe = new Deluxe(15000, 0);
    private Suite suite = new Suite(20000, 0);

    // 依存関係のため追加。findReservationByRoomNoが呼ばれると値がセットされる
    private Reservation reservation = null;

    // csvファイルのために追加 - コンストラクタでCSVから予約データを読み込み
    public CheckoutControl() {
        loadReservationsFromCsv();
        loadRoomStatusFromCsv(); // csvファイルのために追加
    }

    // csvファイルのために追加
    private void loadReservationsFromCsv() {
        reservationMap = CsvManager.loadReservationsDetailed();
    }

    // csvファイルのために追加
    private void loadRoomStatusFromCsv() {
        java.util.Map<Integer, Room> standardRooms = CsvManager.loadRoomStatus("standard_rooms.csv");
        java.util.Map<Integer, Room> deluxeRooms = CsvManager.loadRoomStatus("deluxe_rooms.csv");
        java.util.Map<Integer, Room> suiteRooms = CsvManager.loadRoomStatus("suite_rooms.csv");

        for (Room room : standardRooms.values()) {
            standard.registerVacantRoom(room.getRoomNo());
        }
        for (Room room : deluxeRooms.values()) {
            deluxe.registerVacantRoom(room.getRoomNo());
        }
        for (Room room : suiteRooms.values()) {
            suite.registerVacantRoom(room.getRoomNo());
        }
    }

    // csvファイルのために追加
    private void saveRoomStatusToCsv() {
        CsvManager.saveRoomStatus("standard_rooms.csv", standard.getVacantRoomsHash());
        CsvManager.saveRoomStatus("deluxe_rooms.csv", deluxe.getVacantRoomsHash());
        CsvManager.saveRoomStatus("suite_rooms.csv", suite.getVacantRoomsHash());
    }

    // クラス図から作成 - 部屋番号で予約を検索する
    // 指定された部屋番号の予約情報を返却
    public Map<String, Object> findReservationByRoomNo(int roomNo) {
        // 部屋番号で予約検索。失敗時はnull返却
        for (Reservation reservation : reservationMap.values()) {
            if (reservation.getReservationInfo().get("room_no") != null &&
                    (int) reservation.getReservationInfo().get("room_no") == roomNo &&
                    (boolean) reservation.isCheckoutDone() == false &&
                    (boolean) reservation.isCheckinDone() == true) {
                this.reservation = reservation; // 予約情報を保持
                return reservation.getReservationInfo();
            }
        }
        return null;
    }

    // クラス図から作成 - チェックアウト完了処理
    // チェックアウト処理を実行
    public void completeCheckOut() {
        // チェックアウト処理

        // 予約オブジェクトをチェックアウト済みにする
        reservation.setCheckoutDone();

        // 予約オブジェクトから部屋番号を取得
        int roomNo = (int) reservation.getReservationInfo().get("room_no");

        // コミュニケーション図に従い、各グレードのregisterVacantRoomを呼ぶ

        // 部屋番号から判定してグレード別に空室登録
        if (roomNo >= 101 && roomNo <= 110) {
            // Standard rooms
            standard.registerVacantRoom(roomNo);
        } else if (roomNo >= 201 && roomNo <= 205) {
            // Deluxe rooms
            deluxe.registerVacantRoom(roomNo);
        } else if (roomNo >= 301 && roomNo <= 302) {
            // Suite rooms
            suite.registerVacantRoom(roomNo);
        }

        // csvファイルのために追加 - 予約情報の変更をCSVに保存
        updateReservationInCSV();

        // csvファイルのために追加 - 空室状況をCSVに保存
        saveRoomStatusToCsv();
    }

    // csvファイルのために追加 - 予約情報を安全にCSVファイルに更新
    private void updateReservationInCSV() {
        // 最新のCSVファイルから全ての予約を読み込み
        Map<Integer, Reservation> allReservations = CsvManager.loadReservationsDetailed();

        // 現在のreservationMapの変更を反映
        for (Map.Entry<Integer, Reservation> entry : reservationMap.entrySet()) {
            allReservations.put(entry.getKey(), entry.getValue());
        }

        // 全ての予約をCSVに保存
        CsvManager.saveAllReservationsDetailed(allReservations);
    }
}        