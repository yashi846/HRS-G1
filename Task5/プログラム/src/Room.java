public class Room {
    // クラス図から作成 - 部屋番号（publicフィールド）
    public int roomNo;

    // クラス図から作成 - コンストラクタ
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 部屋番号を設定してRoomオブジェクトを初期化する
    public Room(int roomNo) {
        this.roomNo = roomNo;
    }

    // csvファイルのために追加 - 部屋番号取得
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // 部屋番号を返却する
    public int getRoomNo() {
        return roomNo;
    }
}
