package com.electiondataquality.restservice.controllers;

import java.util.Map;
import java.util.Set;
import java.util.Optional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;
import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.features.precinct.error.PrecinctError;
import com.electiondataquality.geometry.Geometry;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.jpa.managers.PrecinctEntityManager;
import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.DemographicTable;
import com.electiondataquality.jpa.tables.ElectionDataTable;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.jpa.tables.FeatureTable;
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
@CrossOrigin
public class PrecinctController {

    /**
     * Get the boundary data/shape of a precinct.
     * 
     * NOTE: Tested 4/28
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/shapeOfPrecinct")
    public HashMap<String, Object> getShapeOfPrecinct(@RequestParam String precinctId) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            HashMap<String, Object> result = new HashMap<>();
            Precinct target = new Precinct(targetData.get());

            result.put("id", target.getId());
            result.put("geometry", target.geometry);

            return result;
        }

        return null;
    }

    /**
     * Get the shape of multiple precincts.
     * 
     * NOTE: Tested 4/28
     * 
     * @param precinctIds
     * @return
     */
    @GetMapping("/multiplePrecinctShapes")
    public ArrayList<HashMap<String, Object>> getMultipleprecincts(
            @RequestParam(value = "precinctIdList") String[] precinctIds) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < precinctIds.length; i++) {
            Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctIds[i]);

            if (targetData.isPresent()) {
                HashMap<String, Object> geometry = new HashMap<>();
                Precinct target = new Precinct(targetData.get());

                geometry.put(precinctIds[i], target.geometry);
                pList.add(geometry);
            }
        }

        return pList;
    }

    /**
     * Get information of a precinct (detailed data about it - not it's shape).
     * 
     * NOTE: Tested 4/29
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/precinctInfo")
    public HashMap<String, Object> getPrecinctInfo(@RequestParam String precinctId) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            HashMap<String, Object> result = new HashMap<>();
            Precinct target = new Precinct(targetData.get());

            result.put("id", target.getId());
            result.put("canonicalName", target.getCanonicalName());
            result.put("fullName", target.getFullName());
            result.put("parentDistrictId", target.getParentDistrictId());
            result.put("neighborsId", target.getNeighborsId());
            result.put("votingData", target.getVotingData());
            result.put("demographicData", target.getDemographicData());
            result.put("precinctErrors", target.getPrecinctErrors());
            result.put("isGhost", target.getIsGhost());

            return result;
        }

        return null;
    }

    /**
     * Get the original information of a precinct (don't show any user edits etc).
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/originalPrecinctInfo")
    public HashMap<String, Object> getOriginalPrecinctInfo(@RequestParam String precinctId) {
        HashMap<String, Object> result = new HashMap<>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getOriginalPrecinct(precinctId);

        if (target != null) {
            result.put("id", target.getId());
            result.put("canonicalName", target.getCanonicalName());
            result.put("fullName", target.getFullName());
            result.put("parentDistrictId", target.getParentDistrictId());
            result.put("neighborsId", target.getNeighborsId());
            result.put("votingData", target.getVotingData());
            result.put("demographicData", target.getDemographicData());
            result.put("precinctErrors", target.getPrecinctErrors());
            result.put("isGhost", target.getIsGhost());

            return result;
        }

        return null;
    }

    /**
     * Get the original precinct shape.
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/originalPrecinctShape")
    public HashMap<String, Object> getOriginalPrecinctShape(@RequestParam String precinctId) {
        HashMap<String, Object> result = new HashMap<>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getOriginalPrecinct(precinctId);

        if (target != null) {
            result.put("id", target.getId());
            result.put("shape", target.getShape());

            return result;
        }

        return null;
    }

    /**
     * Get the original shape of multiple precincts.
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctIds
     * @return
     */
    @GetMapping("/originalMultPrecinctShapes")
    public ArrayList<HashMap<String, Object>> getMultipleOriginalprecincts(
            @RequestParam(value = "precinctIdList") String[] precinctIds) {
        ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();

        for (int i = 0; i < precinctIds.length; i++) {
            Precinct target = precinctManager.getOriginalPrecinct(precinctIds[i]);

            if (target != null) {
                HashMap<String, Object> shapeMap = new HashMap<>();

                shapeMap.put(precinctIds[i], target.getShape());
                pList.add(shapeMap);
            }
        }

        return pList;
    }

    /**
     * Get the neighboring precincts of a specified precinct.
     * 
     * NOTE: Tested 4/28
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/neighborsOfPrecinct")
    public ArrayList<String> getNeighborsOfPrecinct(@RequestParam String precinctId) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            Precinct target = new Precinct(targetData.get());
            ArrayList<String> neighbors = new ArrayList<>();

            for (String id : target.getNeighborsId()) {
                neighbors.add(id);
            }

            return neighbors;
        }

        return null;
    }

    /**
     * Get the shape of a precinct.
     * 
     * 
     * TODO: need test
     * 
     * @param precinctId
     * @param shape
     * @return
     */
    @GetMapping("/shapesOfPrecinct")
    public ErrorJ updateShapeOfPrecicnt(@RequestParam String precinctId, @RequestParam Geometry geometry) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            int featureId = targetData.get().getFeature().getFeatureId();
            Optional<FeatureTable> targetFeature = pem.findFeatureByFeatureId(featureId);

            if (targetFeature.isPresent()) {
                targetFeature.get().update(geometry);

                pem.cleanup();

                return ErrorGen.ok();
            }

            pem.cleanup();

            return ErrorGen.create("cannot find Feature with featureID");
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Delete a precinct.
     * 
     * NOTE: Tested 4/28/2020
     * 
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/deletePrecinct")
    public ErrorJ deletePrecinct(@RequestParam String precinctId) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            pem.removePrecinct(targetData.get());
        }

        pem.cleanup();

        return ErrorGen.ok();
    }

    /**
     * Define a new ghost precinct.
     * 
     * NOTE: Tested 4/28/2020
     * 
     * @param precinctId
     * @param isGhost
     * @return
     */
    @GetMapping("/defineGhostPrecinct")
    public ErrorJ setGhost(@RequestParam String precinctId, @RequestParam boolean isGhost) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            targetData.get().setIsGhost(isGhost);

            pem.cleanup();

            return ErrorGen.ok();
        }

        pem.cleanup();

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Update the information of a precinct (not it's shape and it's
     * voting,demographic,error).
     * 
     * TODO: Needs test
     * 
     * @param precinctId
     * @param info
     * @return
     */
    @RequestMapping(value = "/updatePrecinctInfo", method = RequestMethod.PUT)
    public ErrorJ updatePrecinctInfo(@RequestParam String precinctId, @RequestBody Precinct info) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            targetData.get().update(info);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to find target precinct by id");
    }

    /**
     * Update the voting data of a precinct.
     * 
     * '{"electionData": {"PRES2016": {"resultsByParty": {"REPUBLICAN":
     * 0,"DEMOCRAT": 50,"LIBRATARIAN": 0,"OTHER": 50},"majorityParty":
     * "OTHER","election": "PRES2016"}}}'
     * 
     * TODO: need test
     * 
     * @param precinctId
     * @param votingData
     * @return
     */
    @GetMapping("/updateVotingData")
    public ErrorJ updateVotingData(@RequestParam String precinctId, @RequestBody VotingData votingData) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            Set<ElectionDataTable> electionDataSet = targetData.get().getElectionDataSet();
            Set<ELECTIONS> elections = votingData.getAllElections();

            if (electionDataSet != null) {
                for (ElectionDataTable edt : targetData.get().getElectionDataSet()) {
                    ELECTIONS e = edt.getElection();

                    if (elections.contains(e)) {
                        edt.update(votingData.getElectionData(e), precinctId);
                        elections.remove(e);
                    }
                }
            }

            for (ELECTIONS remainElections : elections) {
                ElectionDataTable electionDataTable = new ElectionDataTable(votingData.getElectionData(remainElections),
                        precinctId);

                pem.persistElectionData(electionDataTable);
                electionDataSet.add(electionDataTable);
            }

            targetData.get().setElectionData(electionDataSet);
            pem.cleanup();

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Update the precinctErrors of a precinct. if not exist create a new Error.
     * 
     * 
     * TODO: need test
     * 
     * 
     * @param precinctId
     * @param precinctError
     * @return
     */
    @CrossOrigin
    @PutMapping("/updatePrecinctError")
    public ErrorJ updatePreinctError(@RequestParam String precinctId, @RequestParam PrecinctError precinctError) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            Set<ErrorTable> errors = targetData.get().getErrors();
            int errorId = precinctError.getId();

            if (targetData.get().getErrorsId().contains(errorId)) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        et.update(precinctError, precinctId);
                    }
                }

                targetData.get().setErrors(errors);
                pem.cleanup();

                return ErrorGen.ok();
            } else {
                ErrorTable newError = new ErrorTable(precinctError);
                newError.setPrecinctId(precinctId);
                errors.add(newError);
                targetData.get().setErrors(errors);
                pem.persistError(newError);
                pem.cleanup();

                return ErrorGen.ok();
            }
        } else {
            pem.cleanup();

            return ErrorGen.create("cannot find precinct");
        }
    }

    /**
     * Update the demographic data of a precinct.
     * 
     * 
     * TODO: need test
     * 
     * '{"demographicByRace": {"ASIAN": 100,"BLACK":100,"HISPANIC": 100,"OTHER":
     * 100,"WHITE": 100}}'
     * 
     * @param precinctId
     * @param demographicData
     * @return
     */
    @GetMapping("/updateDemographicData")
    public ErrorJ updateDemographicData(@RequestParam String precinctId, @RequestBody DemographicData demographicData) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            DemographicTable newDemographic = targetData.get().getDemogrpahicTable();
            newDemographic.update(demographicData, precinctId);
            targetData.get().setDemographicTable(newDemographic);
            pem.cleanup();
            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Add a neighbor to a precinct's neighbor list.
     * 
     * 
     * NOTE: tested 4/29/2020
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/addPrecinctNeighbor")
    public ErrorJ addPrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);

        Optional<PrecinctFeature> targetData1 = pem.findPrecinctFeatureById(precinctId1);
        Optional<PrecinctFeature> targetData2 = pem.findPrecinctFeatureById(precinctId2);

        if (targetData1.isPresent() && targetData2.isPresent()) {
            targetData1.get().addNeighbor(precinctId2);
            targetData2.get().addNeighbor(precinctId1);

            pem.cleanup();
            return ErrorGen.ok();
        }
        pem.cleanup();
        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Delete a neighbor of a precinct. Removes a precinct from the specified
     * precincts neighbor list.
     * 
     * 
     * NOTE: tested 4/29/2020
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/deletePrecinctNeighbor")
    public ErrorJ deletePrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);

        Optional<PrecinctFeature> targetData1 = pem.findPrecinctFeatureById(precinctId1);
        Optional<PrecinctFeature> targetData2 = pem.findPrecinctFeatureById(precinctId2);

        if (targetData1.isPresent() && targetData2.isPresent()) {
            targetData1.get().deleteNeighbor(precinctId2);
            targetData2.get().deleteNeighbor(precinctId1);

            pem.cleanup();
            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Create a new precinct object.
     * 
     * TODO: have to find a way to create a new precinct id
     * 
     * @param mp
     * @return
     */
    @GetMapping("/createNewPrecinct")
    public ErrorJ createNewPrecinct(@RequestParam MultiPolygon mp) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        BigInteger bigintId = new BigInteger(precinctManager.getLargestPrecinctId());

        String newId = new String(bigintId.add(new BigInteger("1")).toByteArray());
        Precinct newPrecinct = new Precinct(newId, "", "", null, null, null, null, null, mp);
        precinctManager.addPrecinct(newPrecinct);

        return ErrorGen.ok();
    }

    /**
     * Merge two precincts together. TODO: Need to update merged errorData
     * 
     * TODO: Have to merge polygon also (with sam's script)
     * 
     * TODO: need test
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/mergePrecinct")
    public ErrorJ mergePrecincts(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);

        Optional<PrecinctFeature> targetData1 = pem.findPrecinctFeatureById(precinctId1);
        Optional<PrecinctFeature> targetData2 = pem.findPrecinctFeatureById(precinctId2);

        if (targetData1.isPresent() && targetData2.isPresent()) {
            Precinct target1 = new Precinct(targetData1.get());
            Precinct target2 = new Precinct(targetData2.get());

            Precinct mergedPrecinct = Precinct.mergePrecinct(target1, target2);
            String mergedPrecinctId = mergedPrecinct.getId();
            targetData1.get().update(mergedPrecinct);
            if (mergedPrecinct.getDemographicData() != null) {
                DemographicTable mergedDemographic = targetData1.get().getDemogrpahicTable();
                mergedDemographic.update(mergedPrecinct.getDemographicData(), precinctId1);
                targetData1.get().setDemographicTable(mergedDemographic);
            }
            // TODO
            if (mergedPrecinct.getVotingData() != null) {
                VotingData mergedVotingData = mergedPrecinct.getVotingData();
                Set<ELECTIONS> mergedElections = mergedVotingData.getAllElections();
                for (ElectionDataTable edt : targetData1.get().getElectionDataSet()) {
                    if (mergedElections.contains(edt.getElection())) {
                        edt.update(mergedVotingData.getElectionData(edt.getElection()), mergedPrecinctId);
                        mergedElections.remove(edt.getElection());
                    }
                }
                for (ELECTIONS remainElections : mergedElections) {
                    ElectionDataTable electionDataTable = new ElectionDataTable(
                            mergedVotingData.getElectionData(remainElections), mergedPrecinctId);
                    pem.persistElectionData(electionDataTable);
                }
            }

            if (mergedPrecinct.getPrecinctErrors() != null) {
                Map<Integer, PrecinctError> mergedErrors = mergedPrecinct.getPrecinctErrors();
                Set<Integer> mergedErrorIds = mergedErrors.keySet();
                for (ErrorTable et : targetData1.get().getErrors()) {
                    if (mergedErrorIds.contains(et.getErrorId())) {
                        et.update(mergedErrors.get(et.getErrorId()), mergedPrecinctId);
                        mergedErrorIds.remove(et.getErrorId());
                    }
                }

                for (int remainErrorId : mergedErrorIds) {
                    ErrorTable errorTable = new ErrorTable(mergedErrors.get(remainErrorId));
                    errorTable.setPrecinctId(mergedPrecinctId);
                    pem.persistError(errorTable);
                }
            }

            pem.cleanup(true);
            return ErrorGen.ok();
        }

        return ErrorGen.create("can't find precinct1 or precinct2");
    }

    /**
     * TODO: have script to do this part
     */
    // @GetMapping("verifyPrecinctShape")
    // public void verifyPrecinctShape(){
    // }
}
