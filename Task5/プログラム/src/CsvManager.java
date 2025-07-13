import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

// csvファイルのために追加 - CSV管理クラス（クラス図にはないが実装のため必要）
// AIによる実装のためロジックが本来の意図と異なる可能性あり
// 予約情報、部屋情報のCSVファイル管理を一元化
public class CsvManager {

    // csvファイルのために追加 - 予約情報をCSVファイルに保存
    public static void saveReservation(Reservation reservation) {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream("reservations.csv", true), StandardCharsets.UTF_8))) {
            Map<String, Object> info = reservation.getReservationInfo();
            writer.println(
                    info.get("reservation_no") + "," +
                            info.get("price") + "," +
                            info.get("checkin_date") + "," +
                            info.get("checkout_date") + "," +
                            info.get("people_num") + "," +
                            info.get("room_no") + "," +
                            info.get("plan_name") + "," +
                            info.get("user_name") + "," +
                            info.get("user_mail_address") + "," +
                            info.get("user_telephone_no"));
        } catch (IOException e) {
            System.err.println("CSV保存エラー: " + e.getMessage());
        }
    }

    // csvファイルのために追加
    public static Map<Integer, Reservation> loadReservations() {
        Map<Integer, Reservation> reservations = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("reservations.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 10) {
                    int reservationNo = Integer.parseInt(data[0]);
                    int price = Integer.parseInt(data[1]);
                    String checkinDate = data[2];
                    String checkoutDate = data[3];
                    int peopleNum = Integer.parseInt(data[4]);
                    int roomNo = Integer.parseInt(data[5]);
                    String planType = data[6];
                    String userName = data[7];
                    String userMail = data[8];
                    String userTel = data[9];

                    Room room = new Room(roomNo);
                    Plan plan = createPlanFromType(planType, price);
                    UserInfo userInfo = new UserInfo(userName, userMail, userTel);

                    Reservation reservation = new Reservation(reservationNo, price, checkinDate, checkoutDate,
                            peopleNum, room, plan, userInfo);
                    reservations.put(reservationNo, reservation);
                }
            }
        } catch (IOException e) {
            System.err.println("CSV読み込みエラー: " + e.getMessage());
        }
        return reservations;
    }

    // csvファイルのために追加
    private static Plan createPlanFromType(String planType, int price) {
        switch (planType) {
            case "PlanA":
                return new PlanA(price);
            case "PlanB":
                return new PlanB(price);
            case "PlanC":
                return new PlanC(price);
            default:
                return new PlanA(price);
        }
    }

    // csvファイルのために追加
    public static void saveRoomStatus(String fileName, Map<Integer, Room> rooms) {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            for (Room room : rooms.values()) {
                writer.println(room.getRoomNo());
            }
        } catch (IOException e) {
            System.err.println("部屋状況保存エラー: " + e.getMessage());
        }
    }

    // csvファイルのために追加
    public static Map<Integer, Room> loadRoomStatus(String fileName) {
        Map<Integer, Room> rooms = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int roomNo = Integer.parseInt(line.trim());
                rooms.put(roomNo, new Room(roomNo));
            }
        } catch (IOException e) {
            System.err.println("部屋状況読み込みエラー: " + e.getMessage());
        }
        return rooms;
    }

    // csvファイルのために追加 - 詳細な予約情報をCSVに保存
    public static void saveReservationDetailed(Reservation reservation) {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream("reservations.csv", true), StandardCharsets.UTF_8))) {
            Room room = reservation.getRoom();
            Plan plan = reservation.getPlan();
            UserInfo userInfo = reservation.getUserInfo();

            writer.println(
                    reservation.getReservationNo() + "," +
                            reservation.getPrice() + "," +
                            reservation.getCheckinDate() + "," +
                            reservation.getCheckoutDate() + "," +
                            reservation.getPeopleNum() + "," +
                            reservation.isCheckoutDone() + "," +
                            reservation.isCheckinDone() + "," +
                            (room != null ? room.getRoomNo() : "") + "," +
                            (plan != null ? plan.getClass().getSimpleName() : "") + "," +
                            (plan != null ? plan.getPrice() : "") + "," +
                            (userInfo != null ? userInfo.getName() : "") + "," +
                            (userInfo != null ? userInfo.getMailAddress() : "") + "," +
                            (userInfo != null ? userInfo.getTelephoneNo() : ""));
        } catch (IOException e) {
            System.err.println("CSV保存エラー: " + e.getMessage());
        }
    }

    // csvファイルのために追加 - 全ての予約情報をCSVに上書き保存
    public static void saveAllReservationsDetailed(Map<Integer, Reservation> reservationMap) {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream("reservations.csv", false), StandardCharsets.UTF_8))) { // false:
                                                                                                                    // 上書き
            for (Reservation reservation : reservationMap.values()) {
                Room room = reservation.getRoom();
                Plan plan = reservation.getPlan();
                UserInfo userInfo = reservation.getUserInfo();

                writer.println(
                        reservation.getReservationNo() + "," +
                                reservation.getPrice() + "," +
                                reservation.getCheckinDate() + "," +
                                reservation.getCheckoutDate() + "," +
                                reservation.getPeopleNum() + "," +
                                reservation.isCheckoutDone() + "," +
                                reservation.isCheckinDone() + "," +
                                (room != null ? room.getRoomNo() : "") + "," +
                                (plan != null ? plan.getClass().getSimpleName() : "") + "," +
                                (plan != null ? plan.getPrice() : "") + "," +
                                (userInfo != null ? userInfo.getName() : "") + "," +
                                (userInfo != null ? userInfo.getMailAddress() : "") + "," +
                                (userInfo != null ? userInfo.getTelephoneNo() : ""));
            }
        } catch (IOException e) {
            System.err.println("CSV保存エラー: " + e.getMessage());
        }
    }

    // csvファイルのために追加 - 詳細な予約情報をCSVから読み込み
    public static Map<Integer, Reservation> loadReservationsDetailed() {
        Map<Integer, Reservation> reservations = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("reservations.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 13) {
                    int reservationNo = Integer.parseInt(data[0]);
                    int price = Integer.parseInt(data[1]);
                    String checkinDate = data[2];
                    String checkoutDate = data[3];
                    int peopleNum = Integer.parseInt(data[4]);
                    boolean checkoutDone = Boolean.parseBoolean(data[5]);
                    boolean checkinDone = Boolean.parseBoolean(data[6]);
                    int roomNo = Integer.parseInt(data[7]);
                    String planType = data[8];
                    int planPrice = Integer.parseInt(data[9]);
                    String userName = data[10];
                    String userMail = data[11];
                    String userTel = data[12];

                    Room room = new Room(roomNo);
                    Plan plan = createPlanFromType(planType, planPrice);
                    UserInfo userInfo = new UserInfo(userName, userMail, userTel);

                    Reservation reservation = new Reservation(reservationNo, price, checkinDate, checkoutDate,
                            peopleNum, room, plan, userInfo);
                    if (checkoutDone)
                        reservation.setCheckoutDone();
                    if (checkinDone)
                        reservation.setCheckinDone();

                    reservations.put(reservationNo, reservation);
                }
            }
        } catch (IOException e) {
            System.err.println("CSV読み込みエラー: " + e.getMessage());
        }
        return reservations;
    }
}
