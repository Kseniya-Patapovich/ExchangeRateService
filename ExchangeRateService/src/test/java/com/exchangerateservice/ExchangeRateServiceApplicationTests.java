package com.exchangerateservice;

import com.exchangerateservice.controller.ExchangeRateController;
import com.exchangerateservice.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExchangeRateServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    public void testExchangeRateControllerLoaded() {
        ExchangeRateController controller = applicationContext.getBean(ExchangeRateController.class);
        assertThat(controller).isNotNull();
    }

    @Test
    public void testExchangeRateServiceLoaded() {
        ExchangeRateService service = applicationContext.getBean(ExchangeRateService.class);
        assertThat(service).isNotNull();
    }
}
