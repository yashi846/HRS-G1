import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Reservation {
    // クラス図から作成 - 予約番号
    private int reservationNo;
    // クラス図から作成 - 価格
    private int price;
    // クラス図から作成 - チェックイン日
    private Date checkinDate;
    // クラス図から作成 - チェックアウト日
    private Date checkoutDate;
    // クラス図から作成 - 人数
    private int peopleNum;
    // クラス図から作成 - チェックアウト完了フラグ
    private boolean checkoutDoneFlag;
    // クラス図から作成 - チェックイン完了フラグ
    private boolean checkinDoneFlag;
    // クラス図から作成 - 部屋情報
    private Room room;
    // クラス図から作成 - プラン情報
    private Plan plan;
    // クラス図から作成 - ユーザー情報
    private UserInfo userInfo;

    // クラス図から作成 - コンストラクタ
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 予約情報を初期化し、部屋番号からグレードを判断して料金を計算、チェックイン・チェックアウトフラグをfalseに設定
    public Reservation(int reservationNo, int price, String checkinDateStr, String checkoutDateStr, int peopleNum,
            Room room,
            Plan plan, UserInfo userInfo) {
        this.reservationNo = reservationNo;

        // 部屋番号からグレードの基本価格を判断
        int gradePrice = 0;
        if (room != null) {
            int roomNo = room.getRoomNo();
            if (roomNo >= 101 && roomNo <= 110) {
                gradePrice = 10000; // Standard
            } else if (roomNo >= 201 && roomNo <= 210) {
                gradePrice = 15000; // Deluxe
            } else if (roomNo >= 301 && roomNo <= 310) {
                gradePrice = 20000; // Suite
            }
        }

        // グレード価格とプラン価格を組み合わせて総料金を計算
        this.price = gradePrice + (plan != null ? plan.getPrice() : 0);
        // 文字列をDate型に変換
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.checkinDate = dateFormat.parse(checkinDateStr);
            this.checkoutDate = dateFormat.parse(checkoutDateStr);
        } catch (ParseException e) {
            // パース失敗時はnullを設定
            this.checkinDate = null;
            this.checkoutDate = null;
        }
        this.peopleNum = peopleNum;
        this.room = room;
        this.plan = plan;
        this.userInfo = userInfo;
        this.checkoutDoneFlag = false;
        this.checkinDoneFlag = false;
    }

    // クラス図から作成 - 予約情報取得
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 予約情報をHashMap形式で返却し、UI表示などで利用される

    // クラス図から作成 - 予約情報取得
    // 予約情報をHashMap形式で返却し、UI表示などで利用される
    public Map<String, Object> getReservationInfo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> info = new HashMap<>();
        info.put("reservation_no", reservationNo);
        info.put("price", price);
        info.put("checkin_date", checkinDate != null ? dateFormat.format(checkinDate) : null);
        info.put("checkout_date", checkoutDate != null ? dateFormat.format(checkoutDate) : null);
        info.put("people_num", peopleNum);
        info.put("room_no", room != null ? room.getRoomNo() : null);
        info.put("plan_name", plan != null ? plan.getClass().getSimpleName() : null);
        info.put("user_name", userInfo != null ? userInfo.getName() : null);
        info.put("user_mail_address", userInfo != null ? userInfo.getMailAddress() : null);
        info.put("user_telephone_no", userInfo != null ? userInfo.getTelephoneNo() : null);
        return info;
    }

    // クラス図から作成 - チェックアウト完了設定
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // チェックアウト完了フラグをtrueに設定する
    public void setCheckoutDone() {
        checkoutDoneFlag = true;
    }

    // クラス図から作成 - チェックイン完了設定
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // チェックイン完了フラグをtrueに設定する
    public void setCheckinDone() {
        checkinDoneFlag = true;
    }

    // csvファイルのために追加
    public int getReservationNo() {
        return reservationNo;
    }

    // csvファイルのために追加
    public int getPrice() {
        return price;
    }

    // csvファイルのために追加
    public String getCheckinDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return checkinDate != null ? dateFormat.format(checkinDate) : null;
    }

    // csvファイルのために追加
    public String getCheckoutDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return checkoutDate != null ? dateFormat.format(checkoutDate) : null;
    }

    // csvファイルのために追加
    public int getPeopleNum() {
        return peopleNum;
    }

    // csvファイルのために追加
    public boolean isCheckoutDone() {
        return checkoutDoneFlag;
    }

    // csvファイルのために追加
    public boolean isCheckinDone() {
        return checkinDoneFlag;
    }

    // csvファイルのために追加
    public Room getRoom() {
        return room;
    }

    // csvファイルのために追加
    public Plan getPlan() {
        return plan;
    }

    // csvファイルのために追加
    public UserInfo getUserInfo() {
        return userInfo;
    }

    // getter/setter省略

    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 対応する予約を削除する（キャンセル機能用）
    public void deleteTheReservation() {
        // 部屋番号とグレードを取得
        int roomNo = this.room.getRoomNo();

        // 既存の部屋状況をCSVから読み込み、グレード別にインスタンスを作成
        if (roomNo >= 101 && roomNo <= 110) {
            // Standard grade
            Standard standard = new Standard(10000, 10);
            // 既存の部屋状況を読み込み
            java.util.Map<Integer, Room> standardRooms = CsvManager.loadRoomStatus("standard_rooms.csv");
            for (Room room : standardRooms.values()) {
                standard.registerVacantRoom(room.getRoomNo());
            }
            // キャンセルした部屋を空室として登録
            standard.registerVacantRoom(roomNo);
            // CSVファイルに保存
            CsvManager.saveRoomStatus("standard_rooms.csv", standard.getVacantRoomsHash());
        } else if (roomNo >= 201 && roomNo <= 210) {
            // Deluxe grade
            Deluxe deluxe = new Deluxe(15000, 5);
            // 既存の部屋状況を読み込み
            java.util.Map<Integer, Room> deluxeRooms = CsvManager.loadRoomStatus("deluxe_rooms.csv");
            for (Room room : deluxeRooms.values()) {
                deluxe.registerVacantRoom(room.getRoomNo());
            }
            // キャンセルした部屋を空室として登録
            deluxe.registerVacantRoom(roomNo);
            // CSVファイルに保存
            CsvManager.saveRoomStatus("deluxe_rooms.csv", deluxe.getVacantRoomsHash());
        } else if (roomNo >= 301 && roomNo <= 310) {
            // Suite grade
            Suite suite = new Suite(20000, 2);
            // 既存の部屋状況を読み込み
            java.util.Map<Integer, Room> suiteRooms = CsvManager.loadRoomStatus("suite_rooms.csv");
            for (Room room : suiteRooms.values()) {
                suite.registerVacantRoom(room.getRoomNo());
            }
            // キャンセルした部屋を空室として登録
            suite.registerVacantRoom(roomNo);
            // CSVファイルに保存
            CsvManager.saveRoomStatus("suite_rooms.csv", suite.getVacantRoomsHash());
        }

        // CSVファイルから該当予約を削除
        CsvManager.deleteReservation(this.reservationNo);
    }
}
