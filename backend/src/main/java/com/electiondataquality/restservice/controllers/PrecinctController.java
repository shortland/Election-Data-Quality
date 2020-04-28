package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
public class PrecinctController {

    /**
     * Get the boundary data/shape of a precinct.
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/shapeOfPrecinct")
    public HashMap<String, Object> getShapeOfPrecinct(@RequestParam int precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecinct(precinctId);

        if (target != null) {
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
    @CrossOrigin
    @GetMapping("/multiplePrecinctShapes")
    public ArrayList<HashMap<Integer, Object>> getMultipleprecincts(
            @RequestParam(value = "precinctIdList") int[] precinctIds) {
        ArrayList<HashMap<Integer, Object>> pList = new ArrayList<HashMap<Integer, Object>>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();

        for (int i = 0; i < precinctIds.length; i++) {
            Precinct target = precinctManager.getPrecinct(precinctIds[i]);

            if (target != null) {
                HashMap<Integer, Object> shapeMap = new HashMap<Integer, Object>();

                shapeMap.put(precinctIds[i], target.getShape());
                pList.add(shapeMap);
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
    @CrossOrigin
    @GetMapping("/precinctInfo")
    public HashMap<String, Object> getPrecinctInfo(@RequestParam int precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecinct(precinctId);

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
     * Get the original information of a precinct (don't show any user edits etc).
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/originalPrecinctInfo")
    public HashMap<String, Object> getOriginalPrecinctInfo(@RequestParam int precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
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
     * NOTE: Tested
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/originalPrecinctShape")
    public HashMap<String, Object> getOriginalPrecinctShape(@RequestParam int precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
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
     * NOTE: Tested
     * 
     * @param precinctIds
     * @return
     */
    @CrossOrigin
    @GetMapping("/originalMultPrecinctShapes")
    public ArrayList<HashMap<Integer, Object>> getMultipleOriginalprecincts(
            @RequestParam(value = "precinctIdList") int[] precinctIds) {
        ArrayList<HashMap<Integer, Object>> pList = new ArrayList<HashMap<Integer, Object>>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();

        for (int i = 0; i < precinctIds.length; i++) {
            Precinct target = precinctManager.getOriginalPrecinct(precinctIds[i]);

            if (target != null) {
                HashMap<Integer, Object> shapeMap = new HashMap<Integer, Object>();

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
    @CrossOrigin
    @GetMapping("/neighborsOfPrecinct")
    public ArrayList<Integer> getNeighborsOfPrecinct(@RequestParam int precinctId) {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecinct(precinctId);

        if (target != null) {
            for (Integer id : target.getNeighborsId()) {
                neighbors.add(id);
            }

            return neighbors;
        }

        // return ErrorGen.create("unable to get neighbors of precinct");
        return null;
    }

    /**
     * Get the shape of a precinct.
     * 
     * TODO: Change return type to ControllerError
     * 
     * TODO: Find out how to pass in the shape value
     * 
     * @param precinctId
     * @param shape
     * @return
     */
    @CrossOrigin
    @GetMapping("/shapesOfPrecinct")
    public ErrorJ updateShapeOfPrecicnt(@RequestParam int precinctId,
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
     * NOTE: Tested
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/deletePrecinct")
    public ErrorJ deletePrecinct(@RequestParam int precinctId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        precinctManager.deletePrecinct(precinctId);

        return ErrorGen.ok();
    }

    /**
     * Define a new ghost precinct.
     * 
     * TODO: Return ControllerError
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @param isGhost
     * @return
     */
    @CrossOrigin
    @GetMapping("/defineGhostPrecinct")
    public ErrorJ setGhost(@RequestParam int precinctId, @RequestParam boolean isGhost) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecinct(precinctId);

        if (target != null) {
            target.setGhost(isGhost);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Update the information of a precinct (not it's shape - everything but).
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @param info
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/updatePrecinctInfo", method = RequestMethod.PUT)
    public ErrorJ updatePrecinctInfo(@RequestParam int precinctId, @RequestBody Precinct info) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecinct(precinctId);

        if (target != null) {
            target.updatePrecinct(info);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to find target precinct by id");
    }

    /**
     * Update the voting data of a precinct.
     * 
     * TODO: Return ControllerError
     * 
     * '{"electionData": {"PRES2016": {"resultsByParty": {"REPUBLICAN":
     * 0,"DEMOCRAT": 50,"LIBRATARIAN": 0,"OTHER": 50},"majorityParty":
     * "OTHER","election": "PRES2016"}}}'
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @param votingData
     * @return
     */
    @CrossOrigin
    @GetMapping("/updateVotingData")
    public ErrorJ updateVotingData(@RequestParam int precinctId, @RequestBody VotingData votingData) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecinct(precinctId);

        if (target != null) {
            target.setVotingData(votingData);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Update the demographic data of a precinct.
     * 
     * TODO: Return ControllerError
     * 
     * NOTE: Tested.
     * 
     * '{"demographicByRace": {"ASIAN": 100,"BLACK":100,"HISPANIC": 100,"OTHER":
     * 100,"WHITE": 100}}'
     * 
     * @param precinctId
     * @param demographicData
     * @return
     */
    @CrossOrigin
    @GetMapping("/updateDemographicData")
    public ErrorJ updateDemographicData(@RequestParam int precinctId, @RequestBody DemographicData demographicData) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecinct(precinctId);

        demographicData.calculateTotal();
        // System.out.println(demographicData.toString());

        if (target != null) {
            target.setDemographicData(demographicData);

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
    @CrossOrigin
    @GetMapping("/addPrecinctNeighbor")
    public ErrorJ addPrecinctAsNeighbor(@RequestParam int precinctId1, @RequestParam int precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecinct(precinctId1);
        Precinct target2 = precinctManager.getPrecinct(precinctId2);

        if (target1 != null && target2 != null) {
            target1.addNeighbor(precinctId2);
            target2.addNeighbor(precinctId1);

            return ErrorGen.ok();
        }

        if (target1 == null) {
            return ErrorGen.create("unable to get precinct1");
        } else {
            return ErrorGen.create("unable to get precinct2");
        }
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
    @CrossOrigin
    @GetMapping("/deletePrecinctNeighbor")
    public ErrorJ deletePrecinctAsNeighbor(@RequestParam int precinctId1, @RequestParam int precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecinct(precinctId1);
        Precinct target2 = precinctManager.getPrecinct(precinctId2);

        if (target1 != null && target2 != null) {
            target1.deleteNeighbor(precinctId2);
            target2.deleteNeighbor(precinctId1);

            return ErrorGen.ok();
        }

        if (target1 == null) {
            return ErrorGen.create("unable to get precinct1");
        } else {
            return ErrorGen.create("unable to get precinct2");
        }
    }

    /**
     * Create a new precinct object.
     * 
     * TODO: Return ControllerError
     * 
     * @param mp
     * @return
     */
    @CrossOrigin
    @GetMapping("/createNewPrecinct")
    public ErrorJ createNewPrecinct(@RequestParam MultiPolygon mp) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        int newId = precinctManager.getLargestPrecinctId() + 1;
        Precinct newPrecinct = new Precinct(newId, "", "", 0, null, null, null, null, mp);

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
    @CrossOrigin
    @GetMapping("/mergePrecinct")
    public ErrorJ mergePrecincts(@RequestParam int precinctId1, @RequestParam int precinctId2) {
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
