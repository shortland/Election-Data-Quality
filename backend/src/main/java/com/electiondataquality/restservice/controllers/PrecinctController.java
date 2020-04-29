package com.electiondataquality.restservice.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.jpa.managers.PrecinctEntityManager;
import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.ElectionDataTable;
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
     * NOTE: Tested
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
     * NOTE: Tested
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
     * NOTE: Tested
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
     * TODO: Find out how to pass in the shape value
     * 
     * @param precinctId
     * @param shape
     * @return
     */
    @GetMapping("/shapesOfPrecinct")
    public ErrorJ updateShapeOfPrecicnt(@RequestParam String precinctId,
            @RequestParam ArrayList<ArrayList<ArrayList<double[]>>> shape) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct targetPrecinct = precinctManager.getPrecinct(precinctId);

        if (targetPrecinct != null) {
            targetPrecinct.setShape(new MultiPolygon(shape));

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Delete a precinct.
     * 
     * TODO: Change return type to ControllerError
     * 
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/deletePrecinct")
    public ErrorJ deletePrecinct(@RequestParam String precinctId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        precinctManager.deletePrecinct(precinctId);

        return ErrorGen.ok();
    }

    /**
     * Define a new ghost precinct.
     * 
     * NOTE: Tested
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

            return ErrorGen.ok();
        }

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
            targetData.get().updatePrecinctFeature(info);

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
            if (targetData.get().getElectionDataSet() != null) {
                for (ElectionDataTable edt : targetData.get().getElectionDataSet()) {
                    edt.update(votingData.getElectionData(edt.getElection()), precinctId);
                }
            }

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
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
            targetData.get().getDemogrpahicTable().update(demographicData, precinctId);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Add a neighbor to a precinct's neighbor list.
     * 
     * TODO: Return ControllerError
     * 
     * NOTE: Tested
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/addPrecinctNeighbor")
    public ErrorJ addPrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecinct(precinctId1);
        Precinct target2 = precinctManager.getPrecinct(precinctId2);

        if (target1 != null && target2 != null) {
            target1.addNeighbor(precinctId2);
            target2.addNeighbor(precinctId1);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Delete a neighbor of a precinct. Removes a precinct from the specified
     * precincts neighbor list.
     * 
     * TODO: Return ControllerError
     * 
     * NOTE: Tested
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/deletePrecinctNeighbor")
    public ErrorJ deletePrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecinct(precinctId1);
        Precinct target2 = precinctManager.getPrecinct(precinctId2);

        if (target1 != null && target2 != null) {
            target1.deleteNeighbor(precinctId2);
            target2.deleteNeighbor(precinctId1);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Create a new precinct object.
     * 
     * TODO: Return ControllerError
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
     * Merge two precincts together.
     * 
     * TODO: Return ControllerError
     * 
     * TODO: Have to merge polygon also (with sam's script)
     * 
     * NOTE: Tested
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/mergePrecinct")
    public ErrorJ mergePrecincts(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct precint1 = precinctManager.getPrecinct(precinctId1);
        Precinct precint2 = precinctManager.getPrecinct(precinctId2);
        Precinct mergedPrecinct = Precinct.mergePrecinct(precint1, precint2);

        precinctManager.deletePrecinct(precinctId2);
        precinctManager.updatePrecinct(precinctId1, mergedPrecinct);

        return ErrorGen.ok();
    }

    /**
     * TODO: have script to do this part
     */
    // @GetMapping("verifyPrecinctShape")
    // public void verifyPrecinctShape(){
    // }
}
