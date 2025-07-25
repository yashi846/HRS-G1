import java.util.Map;

// 予約キャンセル処理のコントロールクラス
public class CancelProcess {

    // 予約情報を検索し、存在する場合は削除処理を行う
    public boolean searchReservationInfo(int reservationNo) {
        // CSVから予約情報を読み込み
        Map<Integer, Reservation> reservations = CsvManager.loadReservations();

        // 指定された予約番号の予約を検索
        Reservation reservation = reservations.get(reservationNo);

        if (reservation != null) {
            // 予約が存在する場合、削除処理を実行
            reservation.deleteTheReservation();
            return true;
        } else {
            // 予約が存在しない場合
            return false;
        }
    }
}
