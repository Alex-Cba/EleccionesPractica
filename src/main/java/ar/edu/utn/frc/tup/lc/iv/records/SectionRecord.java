package ar.edu.utn.frc.tup.lc.iv.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SectionRecord (
        @JsonProperty("seccionId") Long id,
        @JsonProperty("seccionNombre") String name,
        @JsonProperty("distritoId") Long districtId
) {
}
