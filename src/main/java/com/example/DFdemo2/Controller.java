package com.example.DFdemo2;

import com.example.DFdemo2.model.Event;
import com.example.DFdemo2.model.FulfillmentResponse;
import com.example.DFdemo2.model.QueryText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private DialogFlowService dialogFlowService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World! This is the DF backend. Date : "+ LocalDateTime.now();
    }

    @CrossOrigin
    @PostMapping("/api/df_text_query")
    @ResponseBody
    FulfillmentResponse textQuery(@RequestBody QueryText queryText) throws IOException {
        return dialogFlowService.getTextIntentResponse(queryText.text, queryText.userId);
    }

    @CrossOrigin
    @PostMapping("/api/df_event_query")
    @ResponseBody
    FulfillmentResponse eventQuery(@RequestBody Event event) throws IOException {
        return dialogFlowService.getEventIntentResponse(event.event, event.userId);
    }
}
