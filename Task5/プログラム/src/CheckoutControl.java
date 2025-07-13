import java.util.Map;

public class CheckoutControl {
    // csvファイルのために追加 - 予約リスト（クラス図にはないが実装のため必要）
    private java.util.Map<Integer, Reservation> reservationMap = new java.util.HashMap<>();

    // csvファイルのために追加 - 部屋管理用（クラス図にはないが実装のため必要）
    private Standard standard = new Standard(10000, 10);
    private Deluxe deluxe = new Deluxe(15000, 5);
    private Suite suite = new Suite(20000, 2);

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
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 指定された部屋番号の予約情報を返却
    public Map<String, Object> findReservationByRoomNo(int roomNo) {
        // 部屋番号で予約検索。失敗時はnull返却
        for (Reservation reservation : reservationMap.values()) {
            if (reservation.getReservationInfo().get("room_no") != null &&
                    (int) reservation.getReservationInfo().get("room_no") == roomNo) {
                return reservation.getReservationInfo();
            }
        }
        return null;
    }

    // クラス図から作成 - チェックアウト完了処理
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // チェックアウト処理を実行
    public void completeCheckOut() {
        // チェックアウト処理
    }

    // csvファイルのために追加 - チェックアウト時の空室復元処理（クラス図にはない）
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    public void completeCheckOut(int roomNo) {
        // コミュニケーション図に従い、各グレードのregisterVacantRoomを呼ぶ

        // 部屋番号から判定してグレード別に空室復元
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

        // コラボレーション図 5.1.1: setCheckoutDone()を呼び出し
        for (Reservation reservation : reservationMap.values()) {
            if (reservation.getReservationInfo().get("room_no") != null &&
                    (int) reservation.getReservationInfo().get("room_no") == roomNo) {
                reservation.setCheckoutDone();
                break;
            }
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
