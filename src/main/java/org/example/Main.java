package org.example;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
//        File file = findFile(args[0], args[1]);
        File file = new File("C:\\Users\\OsmanDogan\\Downloads\\Osman\\gridEndeksler.xlsx");
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = 1;

        while (rowCount < sheet.getLastRowNum()) {
            if (sheet.getRow(rowCount) == null) {
                rowCount++;
                continue;
            }
            String meterName = sheet.getRow(rowCount).getCell(0).getStringCellValue();
            String con = String.valueOf(sheet.getRow(rowCount).getCell(1).getNumericCellValue());
            String date = sheet.getRow(rowCount).getCell(6).getStringCellValue();
            System.out.println(String.format("MeterName: %s, Connection: %s, Date: %s", meterName, con, date));
            rowCount++;
        }
        executeSelenium();


    }

    public static File findFile(String path, String fileName) throws IOException {
        File directory = new File(path);
        if (directory.getCanonicalFile().isDirectory()) {
            List<File> fileList = Arrays.stream(directory.listFiles()).filter(file -> file.getName().contains(fileName)).collect(Collectors.toList());
            if (fileList != null && fileList.size() > 0) {
                fileList.sort(Comparator.comparing(File::lastModified).reversed());
                return fileList.get(0);
            } else {
                throw new RuntimeException("Dizinde dosya bulunamadı.");
            }
        } else {
            throw new RuntimeException("Belirtilen yol bir dizin değil.");
        }
    }

    public static void executeSelenium(){
        String chromeDriverPath = "C:\\Users\\OsmanDogan\\Downloads\\deneme\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://erehber.org/mobiett-neden-calismiyor-mobiett-neden-acilmiyor/");
        List<WebElement> h2s = driver.findElements(By.tagName("h2"));
        for (WebElement element: h2s){
            System.out.println(element.getText());
        }
        driver.quit();

    }

}