package com.example.DFdemo2;

import com.example.DFdemo2.model.Event;
import com.example.DFdemo2.model.FulfillmentResponse;
import com.example.DFdemo2.model.QueryText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private DialogFlowService dialogFlowService;

    @Value("${df.properties.projectId}")
    private String projectId;

    @Value("${df.properties.private-key-pkcs8}")
    private String privateKeyPkcs8;


    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World! This is the DF backend. Date : "+ LocalDateTime.now();
    }

    @CrossOrigin
    @PostMapping("/api/df_text_query")
    @ResponseBody
    FulfillmentResponse textQuery(@RequestBody QueryText queryText) throws IOException {
        return dialogFlowService.getTextIntentResponse(queryText.text);
    }

    @CrossOrigin
    @PostMapping("/api/df_event_query")
    @ResponseBody
    Map<String, Object> eventQuery(@RequestBody Event event) throws IOException {

        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("response", dialogFlowService.getEventIntentResponse(event.event));
        return rtn;
    }
}
