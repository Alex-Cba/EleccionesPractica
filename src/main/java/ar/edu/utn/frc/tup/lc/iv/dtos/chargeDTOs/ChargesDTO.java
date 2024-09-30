package ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargesDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre")
    private String name;

}
