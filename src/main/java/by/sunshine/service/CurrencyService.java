package by.sunshine.service;

import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CurrencyService {

    private static final String NationalBankWebsite = "https://www.nbrb.by/statistics/rates/ratesdaily.asp?ysclid=lewxnjhw10671551223";
    private static final String DOLLAR_XPATH = "//*[@id=\"ratesData\"]/table/tbody/tr[8]";
    private static final String EURO_XPATH = "//*[@id=\"ratesData\"]/table/tbody/tr[10]/td[3]/div";
    private static final String RUPIAH_XPATH = "//*[@id=\"ratesData\"]/table/tbody/tr[13]/td[3]/div";
    private static final String RUBLE_XPATH = "//*[@id=\"ratesData\"]/table/tbody/tr[22]/td[3]/div";
    private static final String POUND_XPATH = "//*[@id=\"ratesData\"]/table/tbody/tr[28]/td[3]/div";
    private static final String YUAN_XPATH = "//*[@id=\"ratesData\"]/table/tbody/tr[17]/td[3]/div";
    private static final double DEFAULT_HUNDRED_DIVISION = 100.0;
    private static final double DEFAULT_TEN_DIVISION = 10.0;

    private final Map<String, Double> currencies = new HashMap<>();


    @PostConstruct
    private void findCurrencies() {
        try {
            Document document = Jsoup.connect(NationalBankWebsite).get();
            Elements dollar = document.selectXpath(DOLLAR_XPATH);
            Elements euro = document.selectXpath(EURO_XPATH);
            Elements india = document.selectXpath(RUPIAH_XPATH);
            Elements rub = document.selectXpath(RUBLE_XPATH);
            Elements english = document.selectXpath(POUND_XPATH);
            Elements chine = document.selectXpath(YUAN_XPATH);
            currencies.put("by", 1D);
            currencies.put("us", Double.valueOf(dollar.get(0).text().split(" ")[4].replace(",", ".")));
            currencies.put("eu", Double.valueOf(euro.get(0).text().replace(",", ".")));
            currencies.put("ru", Double.parseDouble(rub.get(0).text().replace(",", ".")) / DEFAULT_HUNDRED_DIVISION);
            currencies.put("in", Double.parseDouble(india.get(0).text().replace(",", ".")) / DEFAULT_HUNDRED_DIVISION);
            currencies.put("gb", Double.valueOf(english.get(0).text().replace(",", ".")));
            currencies.put("cn", Double.parseDouble(chine.get(0).text().replace(",", ".")) /  DEFAULT_TEN_DIVISION);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Optional<Double> getValueOfCurrency(String currency) {
        return Optional.ofNullable(currencies.get(currency));
    }
}
