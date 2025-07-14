public class UserInfo {
    // クラス図から作成 - 名前
    private String name;
    // クラス図から作成 - メールアドレス
    private String mailAddress;
    // クラス図から作成 - 電話番号
    private String telephoneNo;

    // クラス図から作成 - コンストラクタ
    // ユーザー情報を初期化する
    public UserInfo(String name, String mailAddress, String telephoneNo) {
        this.name = name;
        this.mailAddress = mailAddress;
        this.telephoneNo = telephoneNo;
    }

    // csvファイルのために追加 - 名前取得
    // ユーザーの名前を返却する
    public String getName() {
        return name;
    }

    // csvファイルのために追加 - メールアドレス取得
    // ユーザーのメールアドレスを返却する
    public String getMailAddress() {
        return mailAddress;
    }

    // csvファイルのために追加 - 電話番号取得
    // ユーザーの電話番号を返却する
    public String getTelephoneNo() {
        return telephoneNo;
    }
}
