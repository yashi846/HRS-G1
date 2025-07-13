import java.util.Map;

public class ReservationControl {
    // クラス図から作成 - 部屋状況取得
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 指定された条件での各グレードの部屋状況を取得し、統合して返却する
    public Map<String, Integer> getRoomStatus(String checkinDate, String checkoutDate, int peopleNum) {
        // コラボレーション図 1.1.1~1.1.3に従って各グレードから情報を取得
        Map<String, Integer> standardStatus = standard.getPriceAndVacantRoomNo(checkinDate, checkoutDate, peopleNum);
        Map<String, Integer> deluxeStatus = deluxe.getPriceAndVacantRoomNo(checkinDate, checkoutDate, peopleNum);
        Map<String, Integer> suiteStatus = suite.getPriceAndVacantRoomNo(checkinDate, checkoutDate, peopleNum);

        // 予約可能な部屋がない場合はnull返却
        if ((standardStatus == null || standardStatus.get("room_num") == 0) &&
                (deluxeStatus == null || deluxeStatus.get("room_num") == 0) &&
                (suiteStatus == null || suiteStatus.get("room_num") == 0)) {
            return null;
        }

        // 各グレードの情報を統合して返す（UIで表示するため）
        Map<String, Integer> result = new java.util.HashMap<>();
        result.put("standard_price", standardStatus != null ? standardStatus.get("price") : 0);
        result.put("standard_rooms", standardStatus != null ? standardStatus.get("room_num") : 0);
        result.put("deluxe_price", deluxeStatus != null ? deluxeStatus.get("price") : 0);
        result.put("deluxe_rooms", deluxeStatus != null ? deluxeStatus.get("room_num") : 0);
        result.put("suite_price", suiteStatus != null ? suiteStatus.get("price") : 0);
        result.put("suite_rooms", suiteStatus != null ? suiteStatus.get("room_num") : 0);

        return result;
    }

    // クラス図から作成 - 部屋割り当て
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 指定されたグレードから空室を1つ割り当て、予約済みに設定する

    public Room allocateRoom(int grade) {
        // grade: 0=Standard, 1=Deluxe, 2=Suite。失敗時はnull返却
        Room room = null;
        switch (grade) {
            case 0:
                room = standard.getAVacantRoom();
                break;
            case 1:
                room = deluxe.getAVacantRoom();
                break;
            case 2:
                room = suite.getAVacantRoom();
                break;
        }
        if (room != null) {
            switch (grade) {
                case 0:
                    standard.setTheRoomAsReserved(room.getRoomNo());
                    break;
                case 1:
                    deluxe.setTheRoomAsReserved(room.getRoomNo());
                    break;
                case 2:
                    suite.setTheRoomAsReserved(room.getRoomNo());
                    break;
            }
        }
        return room;
    }

    public int[] getPlanInfo() {
        // [PlanA, PlanB, PlanCの価格]
        // 仮の価格
        return new int[] { 1000, 2000, 3000 };
    }

    public Reservation createReservation(String checkinDate, String checkoutDate, int peopleNum, Room room, Plan plan,
            UserInfo userInfo) {
        // 予約生成
        if (room == null || plan == null || userInfo == null)
            return null;
        Reservation reservation = new Reservation(reservationSeq++, 0, checkinDate, checkoutDate,
                peopleNum, room, plan,
                userInfo);

        // csvファイルのために追加 - 予約情報をCSVに保存
        CsvManager.saveReservationDetailed(reservation);

        // csvファイルのために追加 - 部屋状況をCSVに保存
        saveRoomStatusToCsv();

        return reservation;
    }

    public UserInfo createUserInfo(String name, String mailAddress, String telephoneNo) {
        // ユーザー情報生成
        return new UserInfo(name, mailAddress, telephoneNo);
    }

    // サンプル用フィールド
    private Standard standard = new Standard(10000, 10);
    private Deluxe deluxe = new Deluxe(15000, 5);
    private Suite suite = new Suite(20000, 2);
    private int reservationSeq = 1000;

    // コンストラクタで初期部屋を登録
    public ReservationControl() {
        // csvファイルのために追加 - 部屋状況をCSVから読み込み
        loadRoomStatusFromCsv();

        // CSVファイルが存在しない場合の初期部屋登録
        if (standard.getVacantRoomNum() == 0) {
            // Standard rooms (101-110)
            for (int i = 101; i <= 110; i++) {
                standard.registerVacantRoom(i);
            }
        }
        if (deluxe.getVacantRoomNum() == 0) {
            // Deluxe rooms (201-205)
            for (int i = 201; i <= 205; i++) {
                deluxe.registerVacantRoom(i);
            }
        }
        if (suite.getVacantRoomNum() == 0) {
            // Suite rooms (301-302)
            for (int i = 301; i <= 302; i++) {
                suite.registerVacantRoom(i);
            }
        }
    }

    // csvファイルのために追加
    private void loadRoomStatusFromCsv() {
        Map<Integer, Room> standardRooms = CsvManager.loadRoomStatus("standard_rooms.csv");
        Map<Integer, Room> deluxeRooms = CsvManager.loadRoomStatus("deluxe_rooms.csv");
        Map<Integer, Room> suiteRooms = CsvManager.loadRoomStatus("suite_rooms.csv");

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
}
