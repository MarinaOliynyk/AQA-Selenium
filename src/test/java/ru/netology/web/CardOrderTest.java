package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[class='input__control'][type='text']")).sendKeys("Марина Олийнык");
        driver.findElement(cssSelector("[class='input__control'][type='tel']")).sendKeys("+79370000000");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message.trim());
    }

    @Test
    void shouldTestForm() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(cssSelector("form"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Марина Олийнык-Шпак");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79370000000");
        form.findElement(cssSelector("[data-test-id=agreement]")).click();
        form.findElement(cssSelector("[type='button']")).click();
        String text = driver.findElement(cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestFieldName() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(cssSelector("form"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Marina Oliynyk");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79370000000");
        form.findElement(cssSelector("[data-test-id=agreement]")).click();
        form.findElement(cssSelector("[type='button']")).click();
        String text = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestFieldPhone() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(cssSelector("form"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Марина Олийнык");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("89370000000");
        form.findElement(cssSelector("[data-test-id=agreement]")).click();
        form.findElement(cssSelector("[type='button']")).click();
        String text = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestFieldAgreement() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(cssSelector("form"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Марина Олийнык");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("89370000000");
        String text = driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__text")).getText();
        form.findElement(cssSelector("[type='button']")).click();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @Test
    void shouldTestFieldNonInput() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(cssSelector("form"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(cssSelector("[data-test-id=agreement]")).click();
        form.findElement(cssSelector("[type='button']")).click();
        String text = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
}

