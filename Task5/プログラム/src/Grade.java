import java.util.HashMap;
import java.util.Map;

public abstract class Grade {
    // クラス図から作成 - 価格
    protected int price;
    // クラス図から作成 - 空室数
    protected int vacantRoomNum;
    // クラス図から作成 - 空室ハッシュマップ
    protected Map<Integer, Room> vacantRoomsHash = new HashMap<>();

    // csvファイルのために追加
    public int getVacantRoomNum() {
        return vacantRoomNum;
    }

    // csvファイルのために追加
    public Map<Integer, Room> getVacantRoomsHash() {
        return vacantRoomsHash;
    }

    // クラス図から作成 - 空室登録
    // 指定された部屋番号を空室リストに追加し、空室数をインクリメントする
    public void registerVacantRoom(int roomNo) {
        if (!vacantRoomsHash.containsKey(roomNo)) {
            vacantRoomsHash.put(roomNo, new Room(roomNo));
            vacantRoomNum++;
        }
    }

    // クラス図から作成 - 価格と空室数取得
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 指定された条件での価格と空室数をHashMapで返却する

    // クラス図から作成 - 価格と空室数取得
    // 指定された条件での価格と空室数をHashMapで返却する
    public Map<String, Integer> getPriceAndVacantRoomNo(String checkinDate, String checkoutDate, int peopleNum) {
        Map<String, Integer> result = new HashMap<>();
        result.put("price", price);
        result.put("room_num", vacantRoomNum);
        return result;
    }

    // クラス図から作成 - 空室取得
    // 空いている部屋を1つ返す（なければnull）
    public Room getAVacantRoom() {
        for (Room room : vacantRoomsHash.values()) {
            return room;
        }
        return null;
    }

    // クラス図から作成 - 部屋予約設定
    // 部屋を予約済みにする（空室リストから削除し、空室数をデクリメント）
    public void setTheRoomAsReserved(int roomNo) {
        if (vacantRoomsHash.containsKey(roomNo)) {
            vacantRoomsHash.remove(roomNo);
            vacantRoomNum--;
        }
    }
}
