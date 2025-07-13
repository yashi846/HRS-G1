public abstract class Plan {
    // クラス図から作成 - 価格
    protected int price;

    // クラス図から作成 - 価格取得
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // プランの価格を返却する
    public int getPrice() {
        return price;
    }
}
