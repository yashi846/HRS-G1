public class Deluxe extends Grade {
    // クラス図から作成 - コンストラクタ
    // Gradeクラスの継承によりデラックスルームの価格と空室数を設定
    public Deluxe(int price, int vacantRoomNum) {
        this.price = price;
        this.vacantRoomNum = vacantRoomNum;
    }
}
