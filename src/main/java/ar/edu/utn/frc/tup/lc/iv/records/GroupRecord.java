package ar.edu.utn.frc.tup.lc.iv.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GroupRecord(
        @JsonProperty("agrupacionId") Long id,
        @JsonProperty("agrupacionNombre") String name
) {
}
