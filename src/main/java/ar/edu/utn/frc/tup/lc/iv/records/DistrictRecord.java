package ar.edu.utn.frc.tup.lc.iv.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DistrictRecord(
        @JsonProperty("distritoId") Long id,
        @JsonProperty("distritoNombre") String name
) {
}
