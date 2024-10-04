package ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs;

import ar.edu.utn.frc.tup.lc.iv.dtos.districtDTOs.DistrictDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargesByDistrictDTO {

    @JsonProperty("distrito")
    private DistrictDTO district;

    @JsonProperty("cargos")
    private List<ChargesDTO> charges;
}
