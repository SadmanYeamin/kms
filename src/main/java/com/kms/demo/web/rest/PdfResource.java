package com.kms.demo.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kms.demo.domain.Weapon;
import com.kms.demo.web.rest.errors.BadRequestAlertException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class PdfResource {

    private final Logger log = LoggerFactory.getLogger(PdfResource.class);
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/butt-no/pdf/{id}")
    public ResponseEntity<ByteArrayResource> getButtPdf(@PathVariable String id, HttpEntity<String> httpEntity) throws IOException, JRException {
        InputStream reportStream = resourceLoader.getResource("classpath:evo-salesorder-template-report.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
        Map<String, Object> params = new HashMap<>();
        Weapon weapon = new Weapon(1L, "cjhjfhre", "cj rjfjrhw", id);
        weapon.setButtNo(id);
        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(convertObjectToJsonBytesWithDisableAnnotations(weapon));
        JRDataSource dataSource = new JsonDataSource(jsonDataStream);
        params.put("buttNo", id);
        params.put("logoPath", id);
        if (jasperReport != null) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
                JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
                ByteArrayResource resource = new ByteArrayResource(bos.toByteArray());
                return ResponseEntity.ok()
                    .headers(httpEntity.getHeaders())
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(resource);
            } catch (Exception ex) {
                log.error("Exception in generating report", ex);
                throw new BadRequestAlertException("Cannot return report", "pdf", "cannotGeneratePdf");
            }
        } else {
            log.error("Jasper report was null so was not created.");
            throw new BadRequestAlertException("Cannot return report", "pdf", "cannotGeneratePdf");
        }

    }

    public static byte[] convertObjectToJsonBytesWithDisableAnnotations(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addAbstractTypeMapping(Set.class, LinkedHashSet.class);
        mapper.registerModule(simpleModule);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        return mapper.writeValueAsBytes(object);
    }


}
