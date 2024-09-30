package ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    @JsonProperty("distrito")
    private String districtName;

    @JsonProperty("seccion")
    private String sectionName;

    @JsonProperty("resultados")
    private List<PoliticalResult> results;
}

