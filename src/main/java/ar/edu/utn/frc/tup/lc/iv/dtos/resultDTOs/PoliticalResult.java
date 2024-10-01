package ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliticalResult {
    @JsonProperty("orden")
    private int orderId;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("votos")
    private int votes;

    @JsonProperty("porcentaje")
    private BigDecimal percentage;
}
