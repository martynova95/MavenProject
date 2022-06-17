package lesson7_8;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInterfaceView {
    private static Controller controller = new Controller();

    public void runInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите название города:");
            String city = scanner.nextLine();

            System.out.println("Введите 1 для получения погоды на сегодня;" +
                    "2 - для загрузки погоды из базы данных;" +
                    "5 - для получения погоды на 5 дней;" +
                    "0 - для выхода.");
            String command = scanner.nextLine();

            if ("0".equals(command))
                break;

            if (!Pattern.matches("[1,2,5,0]", command)) {
                System.out.println("Введите корректную цифру...");
                continue;
            }

            try {
                    controller.getWeather(command, city);
                } catch (IOException e) {
                    System.out.println("При получении погоды произошла ошибка! Попробуйте позже.");
                } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            }
        }

    public static void main(String[] args) {
        UserInterfaceView view = new UserInterfaceView();
        view.runInterface();
    }
}

