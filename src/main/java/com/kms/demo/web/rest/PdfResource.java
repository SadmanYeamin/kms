package com.kms.demo.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class PdfResource {

    @GetMapping("/butt-no/pdf")
    public String getButtPdf() {
        return "Ok";
    }


}
