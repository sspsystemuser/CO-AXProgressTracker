package com.coax.cpt.controller;

import com.coax.cpt.model.CardCustomFieldModel;
import com.coax.cpt.service.TrelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trello")
public class TrelloController {
    Logger logger = LoggerFactory.getLogger(TrelloController.class);

    @Autowired
    private TrelloService trelloService;

    @GetMapping("/")
    public String Home() {
        return "Hello Home";
    }

      /// Endpoint to receive webhook events from Trello
    @PostMapping("/webhook")
    public String handleTrelloWebhook(@RequestBody Map<String, Object> payload) {
        logger.info("Received Trello Webhook Payload: " + payload);
        // Responding with HTTP 200 OK
        return "Webhook received";
    }


}
