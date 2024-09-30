package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs.ResultDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.sectionDTOs.SectionDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs.ChargesByDistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.districtDTOs.DistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.services.IElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ElectionController {

    @Autowired
    private IElectionService electionService;

    @GetMapping("/distritos")
    public ResponseEntity<List<DistrictDTO>> GetAllDistricts(@RequestParam(name = "distrito_nombre", required = false) String fieldName){
        List<DistrictDTO> response = electionService.GetAllDistricts(fieldName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cargos")
    public ResponseEntity<ChargesByDistrictDTO> GetChargesByDistrictId(@RequestParam(name = "distrito_id") Long districtId){
        ChargesByDistrictDTO response = electionService.GetChargesByDistrictId(districtId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/secciones")
    public ResponseEntity<List<SectionDTO>> GetSectionByDistrictId(@RequestParam(name = "distrito_id") Long districtId,
                                                                   @RequestParam(name = "section_id", required = false) Long sectionId){
        List<SectionDTO> response = electionService.GetSectionByDistrictId(districtId, sectionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resultados")
    public ResponseEntity<ResultDTO> GeResultsByDistrictAndSection(@RequestParam(name = "distrito_id") Long districtId,
                                                                         @RequestParam(name = "section_id") Long sectionId){
        ResultDTO response = electionService.GeResultsByDistrictAndSection(districtId, sectionId);
        return ResponseEntity.ok(response);
    }
}
