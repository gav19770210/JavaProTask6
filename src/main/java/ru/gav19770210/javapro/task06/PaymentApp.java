package ru.gav19770210.javapro.task06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Перенесите ваш сервис продуктов в один проект с платежным ядром в виде отдельного модуля
- Добавить интеграцию платежного сервиса с сервисом продуктов через RestTemplate (или RestClient)
- Добавить возможность запрашивать продукты у платежного сервиса
(клиент кидает запрос в платежный сервис, платежный сервис запрашивает продукты клиента у сервиса продуктов и возвращает клиенту результат)
- Добавить в процесс исполнения платежа выбор продукта, проверку его существования и достаточности средств на нем
- Добавить возврат ошибок клиенту о проблемах как на стороне платежного сервиса, так и на стороне сервиса продуктов
 */
@SpringBootApplication(scanBasePackages = "ru.gav19770210.javapro")
public class PaymentApp {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApp.class, args);
    }
}
