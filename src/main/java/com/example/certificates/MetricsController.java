package com.example.certificates;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class MetricsController {
    @Value("${certificate.keystorePath}")
    private String keystorePath;

    @Value("${certificate.alias}")
    private String alias;

    private final CertificateMetricsService certificateService;
    private final MeterRegistry meterRegistry;

    public MetricsController(CertificateMetricsService certificateService, MeterRegistry meterRegistry) {
        this.certificateService = certificateService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/certificate_info")
    public List<String> getCertificateInfo() {
        List<String> certificateInfo = certificateService.getCertificateInfo(keystorePath, alias);
        Gauge.builder("certificate.info", certificateInfo, List::size)
                .description("Number of lines in certificate info")
                .register(meterRegistry);
        System.out.println(certificateInfo);

        return certificateInfo;
    }
}
