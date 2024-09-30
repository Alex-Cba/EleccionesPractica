package ar.edu.utn.frc.tup.lc.iv.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChargesRecord(
        @JsonProperty("cargoId") Long id,
        @JsonProperty("cargoNombre") String name,
        @JsonProperty("distritoId") Long districtId
) {
}
