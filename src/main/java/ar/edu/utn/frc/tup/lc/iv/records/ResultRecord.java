package ar.edu.utn.frc.tup.lc.iv.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResultRecord(
        @JsonProperty("id") Long id,
        @JsonProperty("distritoId") Long districtId,
        @JsonProperty("seccionId") Long sectionId,
        @JsonProperty("cargoId") Long chargeId,
        @JsonProperty("agrupacionId") Long groupId,
        @JsonProperty("votosTipo") String voteType,
        @JsonProperty("votosCantidad") int voteCount
) {
}
