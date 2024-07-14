package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController

public class MainController {
        private Logger log = LoggerFactory.getLogger(MainController.class); //для логирования запроса и ответа

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",                   //адрес по которому работает заглушка
            produces = MediaType.APPLICATION_JSON_VALUE,    //отвечает за тип входящего сообщения
            consumes = MediaType.APPLICATION_JSON_VALUE     //отвечает за тип исходящего сообщения
    )

    public Object postBalances(@RequestBody RequestDTO requestDTO) {  //метод который обрабатываем запрос, в параметры принимает request
        try{
            String clintId = requestDTO.getClientId();
            String RqUID = requestDTO.getRqUID();
            String account = requestDTO.getAccount();
            String currency;
            BigDecimal maxLimit;

            char firstDigit = clintId.charAt(0); //извлекаем первый символ Id

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                currency = "US";
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            } else {
                maxLimit = new BigDecimal(10000);
                currency = "RUB";
            }

            BigDecimal balance = BigDecimal.valueOf(Math.random() * maxLimit.intValue()).setScale(2, RoundingMode.CEILING); //рандомный баланс в пределах лимита

            ResponseDTO responseDTO = new ResponseDTO(); // создание объекта response и задание его полей
            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clintId);
            responseDTO.setAccount(account);
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("********* RequestDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO)); //логируем запросы
            log.error("********* ResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO)); //логируем ответы

            return responseDTO;

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); //вовзвращает код http и тело исключения е
        }
    }
}
