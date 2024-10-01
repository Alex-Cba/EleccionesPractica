package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.ElectionClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs.ChargesByDistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs.ChargesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.districtDTOs.DistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs.PoliticalResult;
import ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs.ResultDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.sectionDTOs.SectionDTO;
import ar.edu.utn.frc.tup.lc.iv.exceptions.AppException;
import ar.edu.utn.frc.tup.lc.iv.exceptions.NotFoundException;
import ar.edu.utn.frc.tup.lc.iv.records.ResultRecord;
import ar.edu.utn.frc.tup.lc.iv.services.IElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ElectionService implements IElectionService {

    @Autowired
    private ElectionClient electionClient;

    @Override
    public List<DistrictDTO> GetAllDistricts(String fieldName) {
        var checkAllDistricts = electionClient.getAllDistricts(fieldName);

        if (checkAllDistricts == null || checkAllDistricts.getBody() == null || checkAllDistricts.getBody().isEmpty()) {
            throw new NotFoundException("No se encontraron distritos");
        }
        var allDistricts = checkAllDistricts.getBody();

        List<DistrictDTO> response = allDistricts.stream().map(x -> new DistrictDTO(x.id(), x.name())).toList();

        return response;
    }

    @Override
    public ChargesByDistrictDTO GetChargesByDistrictId(Long districtId) {
        var checkDistrict = electionClient.getDistrictsById(districtId);

        if (checkDistrict == null || checkDistrict.getBody() == null || checkDistrict.getBody().isEmpty()) {
            throw new NotFoundException("No se encontro districto con el ID: " + districtId);
        }

        var checkCharges = electionClient.getChargesByDistrictId(districtId);

        if (checkCharges == null || checkCharges.getBody() == null || checkCharges.getBody().isEmpty()) {
            throw new NotFoundException("No se encontraron cargos con el ID de districto: " + districtId);
        }

        var district = checkDistrict.getBody().stream().findFirst().orElseThrow(() -> new NotFoundException("No se encontro districto con el ID: " + districtId));
        var charges = checkCharges.getBody();

        var chargesDTO = charges.stream().map(x -> new ChargesDTO(x.id(), x.name())).toList();
        ChargesByDistrictDTO response = new ChargesByDistrictDTO(new DistrictDTO(district.id(), district.name()), chargesDTO);

        return  response;
    }

    @Override
    public List<SectionDTO> GetSectionByDistrictId(Long districtId, Long sectionId) {
        var checkSection = electionClient.getSectionById(districtId, sectionId);

        if (checkSection == null || checkSection.getBody() == null || checkSection.getBody().isEmpty()) {
            throw new NotFoundException("No se encontraron secciones con el ID de distrito: " + districtId);
        }

        var section = checkSection.getBody();

        List<SectionDTO> response = section.stream().map(x -> new SectionDTO(x.id(), x.name())).toList();

        return response;
    }

    @Override
    public ResultDTO GeResultsByDistrictAndSection(Long districtId, Long sectionId) {

        var checkDistrict = electionClient.getDistrictsById(districtId);

        if (checkDistrict == null || checkDistrict.getBody() == null || checkDistrict.getBody().isEmpty()) {
            throw new NotFoundException("No se encontro districto con el ID: " + districtId);
        }

        var checkSection = electionClient.getSectionById(districtId, sectionId);

        if (checkSection == null || checkSection.getBody() == null || checkSection.getBody().isEmpty()) {
            throw new NotFoundException("No se encontraron secciones con el ID de districto: " + districtId);
        }

        var checkResult = electionClient.getResults(districtId);

        if (checkResult == null || checkResult.getBody() == null || checkResult.getBody().isEmpty()) {
            throw new NotFoundException("No se encontraron resultados con el ID de districto: " + districtId);
        }

        var district = checkDistrict.getBody().stream().findFirst().orElseThrow(() -> new NotFoundException("No se encontro distrito con el ID: " + districtId));
        var section = checkSection.getBody().stream().findFirst().orElseThrow(() -> new NotFoundException("No se encontro seccion con el ID: " + districtId));
        var results = checkResult.getBody().stream()
                                .filter(x -> x.sectionId().equals(sectionId))
                                .collect(Collectors.groupingBy(ResultRecord::groupId));

        List<PoliticalResult> politicalResults = new ArrayList<>();
        for (var entry : results.entrySet()) {
            var group = electionClient.getGroup(entry.getKey()).getBody();

            if (entry.getKey() == 0) {
                ResultsWithoutGroup(entry.getValue(), politicalResults);
            }
            else {

                var newPoliticalResult = entry.getValue().stream()
                        .map(x -> {
                            PoliticalResult politicalResult = new PoliticalResult();
                            politicalResult.setName(group.name());
                            politicalResult.setVotes(x.voteCount());
                            return politicalResult;
                        })
                        .findFirst()
                        .orElseThrow(() -> new AppException("Hubo un error inesperado"));

                politicalResults.add(newPoliticalResult);

            }
        }

        var response = new ResultDTO(district.name(), section.name(), politicalResults);

        response.getResults().sort(Comparator.comparing(PoliticalResult::getVotes).reversed());

        CalculatorOfPercentage(response);

        int order = 1;
        for (PoliticalResult result : response.getResults()) {
            result.setOrderId(order++);
        }

        return response;
    }

    private void CalculatorOfPercentage(ResultDTO response) {
        int totalVotes = response.getResults().stream().mapToInt(PoliticalResult::getVotes).sum();

        for (PoliticalResult result : response.getResults()) {
            result.setPercentage(BigDecimal.valueOf((double) result.getVotes() / totalVotes)
                    .setScale(5, RoundingMode.HALF_UP));
        }
    }

    private void ResultsWithoutGroup(List<ResultRecord> resultRecords, List<PoliticalResult> politicalResults) {
        Map<String, Integer> votes = new HashMap<>();
        for (var result : resultRecords) {
            votes.put(result.voteType(), votes.getOrDefault(result.voteType(), 0) + result.voteCount());
        }

        for (var entry : votes.entrySet()) {
            PoliticalResult politicalResult = new PoliticalResult();
            politicalResult.setName(entry.getKey());
            politicalResult.setVotes(entry.getValue());
            politicalResults.add(politicalResult);
        }
    }
}
