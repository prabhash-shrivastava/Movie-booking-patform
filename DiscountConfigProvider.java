package com.platform.movie.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Component
class DiscountConfigProvider {

    @Qualifier("aws.appconfig-com.platform.movie.booking.service.AwsAppConfigProperties")
    @Autowired
    private AwsAppConfigProperties appConfigProps;

    private double currentDiscountPct = 30.0;
    private int ticketThreshold = 3;

    public double getCurrentDiscountPct() {
        return currentDiscountPct;
    }

    public void setCurrentDiscountPct(double currentDiscountPct) {
        this.currentDiscountPct = currentDiscountPct;
    }

    public int getTicketThreshold() {
        return ticketThreshold;
    }

    public void setTicketThreshold(int ticketThreshold) {
        this.ticketThreshold = ticketThreshold;
    }

    public void onConfigUpdate(String xmlPayload) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlPayload)));
            this.currentDiscountPct = Double.parseDouble(
                    doc.getElementsByTagName("Percentage").item(0).getTextContent());
            this.ticketThreshold = Integer.parseInt(
                    doc.getElementsByTagName("Threshold").item(0).getTextContent());
            System.out.println("AppConfig Refreshed: Discount=" + currentDiscountPct + "%");
        } catch (Exception e) {
            System.err.println("Failed to parse AppConfig XML: " + e.getMessage());
        }
    }
}
