public class Suite extends Grade {
    // クラス図から作成 - コンストラクタ
    // Gradeクラスの継承によりスイートルームの価格と空室数を設定
    public Suite(int price, int vacantRoomNum) {
        this.price = price;
        this.vacantRoomNum = vacantRoomNum;
    }
}
