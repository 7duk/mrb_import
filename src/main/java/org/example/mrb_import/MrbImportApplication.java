package org.example.mrb_import;

import org.example.mrb_import.controller.ImportDataController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class MrbImportApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(MrbImportApplication.class, args);
        ImportDataController client = context.getBean(ImportDataController.class);
        client.process("crawl_data_13_866__size2_Date10-32-19.csv",16,"room_image");
    }

}
