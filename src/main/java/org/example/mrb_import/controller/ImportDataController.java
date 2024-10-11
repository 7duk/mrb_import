package org.example.mrb_import.controller;

import org.example.mrb_import.service.ExtractService;
import org.example.mrb_import.service.ExtractServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;


@Controller
public class ImportDataController {
    //    private ExtractService extractService;
    private ExtractServiceV2 extractService;

    @Autowired
    public ImportDataController(ExtractServiceV2 extractService) {
        this.extractService = extractService;
    }

    public void process(String fileName, Integer partnerId, String outputFolder) throws Exception {
        extractService.process(fileName, partnerId, outputFolder);
    }
}
