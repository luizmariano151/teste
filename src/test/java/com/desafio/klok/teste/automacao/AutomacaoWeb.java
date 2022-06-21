package com.desafio.klok.teste.automacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutomacaoWeb {

	private static WebDriver driver;
	private static Long TIMEOUT_IN_SECONDS = 2L;
	
	@BeforeAll
	public static void setup() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		
		driver.get("https://www.magazineluiza.com.br/");
		Thread.sleep(2000);
	}
	
	@AfterAll
	public static void finish() {
		driver.quit();
	}
	
	@Test
	public void testSearchProductSuccess1() throws InterruptedException {
		WebElement inputSearch = driver.findElement(By.id("input-search")); 
		inputSearch.click();
		String productName = "SMARTPHONE 9T Dual 128GB - CINZA - Global";
		inputSearch.sendKeys(productName);
		Thread.sleep(5000);
		
		inputSearch.sendKeys(Keys.ENTER);
		Thread.sleep(10000);
		
		WebElement product = driver.findElement(By.xpath("//div[@id='__next']/div/main/section[4]/div[3]/div/ul/li/a/div[3]/h2"));
		assertEquals(productName, product.getText());
		inputSearch.sendKeys(Keys.CLEAR);
		Thread.sleep(2000);
	}
	
	@Test
	public void testSearchProductFail2() throws InterruptedException {
		WebElement inputSearch = driver.findElement(By.id("input-search"));
		inputSearch.click();
		
		for(int i = 0;i < 38;i++) {
			inputSearch.sendKeys(Keys.BACK_SPACE);
		}
		
		for(int i = 0;i < 3;i++) {
			inputSearch.sendKeys(Keys.DELETE);
		}
		
		String productName = "SMARTPHONE 9T Dual 128GB";
		inputSearch.sendKeys(productName);
		Thread.sleep(5000);
		
		inputSearch.sendKeys(Keys.ENTER);
		Thread.sleep(10000);
	
		WebElement product = driver.findElement(By.xpath("//div[@id='__next']/div/main/section[4]/div[3]/div/ul/li/a/div[3]/h2"));
		assertNotEquals(productName, product.getText());
		String text = "Smartphone Xiaomi Redmi 9T Tela 6,53"+'"'+" 4GB 128GB Bateria 6000mAh Câmera Quádrupla 48+8+2+2MP Laranja";
		assertEquals(text, product.getText());
		Thread.sleep(2000);
	}
}
