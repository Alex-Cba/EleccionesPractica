package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs.ResultDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs.ChargesByDistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.districtDTOs.DistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.sectionDTOs.SectionDTO;

import java.util.List;

public interface IElectionService {
    List<DistrictDTO> GetAllDistricts(String fieldName);

    ChargesByDistrictDTO GetChargesByDistrictId(Long districtId);

    List<SectionDTO> GetSectionByDistrictId(Long districtId, Long sectionId);

    ResultDTO GeResultsByDistrictAndSection(Long districtId, Long sectionId);
}
