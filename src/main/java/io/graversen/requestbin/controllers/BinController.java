package io.graversen.requestbin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BinController
{
    @RequestMapping(name = "{binIdentifier}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<Void> httpBin(@PathVariable String binIdentifier, @RequestBody(required = false) String requestBody, @RequestParam(required = false) Map<String, String> requestParams, HttpServletRequest httpServletRequest)
    {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(name = "{binIdentifier}/inspect", method = RequestMethod.GET)
    public String httpBinInspect(@PathVariable String binIdentifier, Model model)
    {
        return "";
    }
}
