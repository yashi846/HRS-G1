import java.util.Map;

public class CheckinControl {
    // csvファイルのために追加 - 予約リスト（クラス図にはないが実装のため必要）
    private java.util.Map<Integer, Reservation> reservationMap = new java.util.HashMap<>();

    // csvファイルのために追加 - コンストラクタでCSVから予約データを読み込み
    public CheckinControl() {
        loadReservationsFromCsv();
    }

    // csvファイルのために追加 - CSVファイルから予約情報を読み込む
    private void loadReservationsFromCsv() {
        reservationMap = CsvManager.loadReservationsDetailed();
    }

    // クラス図から作成 - チェックイン処理を実行する
    // AIによる実装のためロジックが本来の意図と異なる可能性あり
    // コラボレーション図 2.1でCheckinControl->Reservationのフローを実装
    public java.util.HashMap<String, Object> checkin(int reservationNo) {
        Reservation reservation = reservationMap.get(reservationNo);
        if (reservation == null)
            return null;
        // コラボレーション図 2.1.2: setCheckinDone()を呼び出し
        reservation.setCheckinDone();

        // csvファイルのために追加 - CSVファイル全体を再読み込みして最新状態で更新
        updateReservationInCSV();

        java.util.HashMap<String, Object> result = new java.util.HashMap<>();
        result.putAll(reservation.getReservationInfo());
        return result;
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
