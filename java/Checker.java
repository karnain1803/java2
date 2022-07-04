import DTO.UserQuery;
import DTO.UserResult;
import Interfaces.IChecker;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.stream.Stream;

public class Checker implements IChecker {
    @Override
    @NonNull public Stream<UserResult> checkUsers(@NonNull Stream<UserQuery> users) {
        return users.parallel().map(this::checkUser);
    }

    // TODO: REMOVE MOCKS, ADD CHECKER
    @NonNull public UserResult checkUser(@NonNull UserQuery user) {

        Proxy proxy = new Proxy();
        proxy.setHttpProxy("<HOST:PORT>");
        ChromeOptions options = new ChromeOptions();
        options.setCapability("proxy", proxy);
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://store.steampowered.com/login/?redir=&redir_ssl=1&snr=1_4_4__global-header");
        driver.findElement(By.id("input_username")).sendKeys(user.getLogin());
        driver.findElement(By.id("input_password")).sendKeys(user.getPassword());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        return new UserResult(user, user.getPassword().equals("123123"));
    }
}
