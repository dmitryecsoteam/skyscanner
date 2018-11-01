package su.ecso.skyscanner.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry on 08.10.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    @JsonProperty("MinPrice")
    private int minPrice;
    @JsonProperty("OutboundLeg")
    private PriceInner outboundLeg;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PriceInner {

        @JsonProperty("DepartureDate")
        private String departureDate;

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }
    }



    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public PriceInner getOutboundLeg() {
        return outboundLeg;
    }

    public void setOutboundLeg(PriceInner outboundLeg) {
        this.outboundLeg = outboundLeg;
    }

    public int getValue() {
        return minPrice;
    }

    public String getDepart_date() {
        return outboundLeg.getDepartureDate().substring(0,10);
    }
}
