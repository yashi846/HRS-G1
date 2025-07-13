public class Standard extends Grade {
    // クラス図から作成 - コンストラクタ
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // Gradeクラスの継承によりスタンダードルームの価格と空室数を設定
    public Standard(int price, int vacantRoomNum) {
        this.price = price;
        this.vacantRoomNum = vacantRoomNum;
    }
}
