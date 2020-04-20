package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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
    // TESTED
    @GetMapping("/shapeOfPrecinct")
    public HashMap<String, Object> getShapeOfPrecinct(@RequestParam int precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

        if (target != null) {
            result.put("id", target.getId());
            result.put("shape", target.getShape());

            return result;
        } else {
            return null;
        }
    }

    // TESTED
    @GetMapping("/multiplePrecinctShapes")
    public ArrayList<HashMap<Integer, Object>> getMultipleprecincts(
            @RequestParam(value = "precinctIdList") int[] precinctIds) {
        ArrayList<HashMap<Integer, Object>> pList = new ArrayList<HashMap<Integer, Object>>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();

        for (int i = 0; i < precinctIds.length; i++) {
            Precinct target = precinctManager.getPrecicnt(precinctIds[i]);

            if (target != null) {
                HashMap<Integer, Object> shapeMap = new HashMap<Integer, Object>();
                shapeMap.put(precinctIds[i], target.getShape());
                pList.add(shapeMap);
            }
        }

        return pList;
    }

    // TESTED
    @GetMapping("/precinctInfo")
    public HashMap<String, Object> getPrecinctInfo(@RequestParam int precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

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
        } else {
            return null;
        }
    }

    // TESTED
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
        } else {
            System.out.println("nullllll");

            return null;
        }
    }

    // TESTED
    @GetMapping("/originalPrecinctShape")
    public HashMap<String, Object> getOriginalPrecinctShape(@RequestParam int precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getOriginalPrecinct(precinctId);

        if (target != null) {
            result.put("id", target.getId());
            result.put("shape", target.getShape());

            return result;
        } else {
            return null;
        }
    }

    // TESTED
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

    // TESTED
    @GetMapping("/neighborsOfPrecinct")
    public ArrayList<Integer> getNeighborsOfPrecinct(@RequestParam int precinctId) {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        if (target != null) {
            for (Integer id : target.getNeighborsId()) {
                neighbors.add(id);
            }
        } else {
            // return ErrorGen.create("unable to get neighbors of precinct");
        }

        return neighbors;
    }

    // TODO: Change return type to ControllerError
    // TODO: Find out how to pass in the shape value
    @GetMapping("/shapesOfPrecinct")
    public ErrorJ updateShapeOfPrecicnt(@RequestParam int precinctId,
            @RequestParam ArrayList<ArrayList<ArrayList<double[]>>> shape) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct targetPrecinct = precinctManager.getPrecicnt(precinctId);

        if (targetPrecinct != null) {
            targetPrecinct.setShape(new MultiPolygon(shape));

            return ErrorGen.ok();
        } else {
            return ErrorGen.create("unable to get precinct");
        }
    }

    // TESTED
    // TODO: Change return type to ControllerError
    @GetMapping("/deletePrecinct")
    public ErrorJ deletePrecinct(@RequestParam int precinctId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        precinctManager.deletePrecinct(precinctId);

        return ErrorGen.ok();
    }

    // TESTED
    // TODO: Return ControllerError
    @GetMapping("/defineGhostPrecinct")
    public ErrorJ setGhost(@RequestParam int precinctId, @RequestParam boolean isGhost) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

        if (target != null) {
            target.setGhost(isGhost);

            return ErrorGen.ok();
        } else {
            return ErrorGen.create("unable to get precinct");
        }
    }

    // TESTED
    @RequestMapping(value = "/updatePrecinctInfo", method = RequestMethod.PUT)
    public ErrorJ updatePrecinctInfo(@RequestParam int precinctId, @RequestBody Precinct info) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

        if (target != null) {
            target.updatePrecinct(info);

            return ErrorGen.ok();
        } else {
            return ErrorGen.create("unable to find target precinct by id");
        }
    }

    // TODO: Return ControllerError
    @GetMapping("/updateVotingData")
    public ErrorJ updateVotingData(@RequestParam int precinctId, @RequestParam VotingData votingData) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

        if (target != null) {
            target.setVotingData(votingData);

            return ErrorGen.ok();
        } else {
            return ErrorGen.create("unable to get precinct");
        }
    }

    // TODO: Return ControllerError
    @GetMapping("/updateDemographicData")
    public ErrorJ updateDemographicData(@RequestParam int precinctId, @RequestParam DemographicData demographicData) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

        if (target != null) {
            target.setDemographicData(demographicData);

            return ErrorGen.ok();
        } else {
            return ErrorGen.create("unable to get precinct");
        }
    }

    // TESTED
    // TODO: Return ControllerError
    @GetMapping("/addPrecinctNeighbor")
    public ErrorJ addPrecinctAsNeighbor(@RequestParam int precinctId1, @RequestParam int precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecicnt(precinctId1);
        Precinct target2 = precinctManager.getPrecicnt(precinctId2);

        if (target1 != null && target2 != null) {
            target1.addNeighbor(precinctId2);
            target2.addNeighbor(precinctId1);

            return ErrorGen.ok();
        } else {
            if (target1 == null) {
                return ErrorGen.create("unable to get precinct1");
            } else {
                return ErrorGen.create("unable to get precinct2");
            }

        }
    }

    // TESTED
    // TODO: Return ControllerError
    @GetMapping("/deletePrecinctNeighbor")
    public ErrorJ deletePrecinctAsNeighbor(@RequestParam int precinctId1, @RequestParam int precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecicnt(precinctId1);
        Precinct target2 = precinctManager.getPrecicnt(precinctId2);

        if (target1 != null && target2 != null) {
            target1.deleteNeighbor(precinctId2);
            target2.deleteNeighbor(precinctId1);

            return ErrorGen.ok();
        } else {
            if (target1 == null) {
                return ErrorGen.create("unable to get precinct1");
            } else {
                return ErrorGen.create("unable to get precinct2");
            }
        }
    }

    // TODO: Return ControllerError
    @GetMapping("/createNewPrecinct")
    public ErrorJ createNewPrecinct(@RequestParam MultiPolygon mp) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        int newId = precinctManager.getLargestPrecinctId() + 1;
        Precinct newPrecinct = new Precinct(newId, "", "", 0, null, null, null, null, mp);
        precinctManager.addPrecinct(newPrecinct);

        return ErrorGen.ok();
    }

    // TESTED
    // TODO: Return ControllerError
    // TODO: Have to merge polygon also
    @GetMapping("/mergePrecinct")
    public ErrorJ mergePrecincts(@RequestParam int precinctId1, @RequestParam int precinctId2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct precint1 = precinctManager.getPrecicnt(precinctId1);
        Precinct precint2 = precinctManager.getPrecicnt(precinctId2);
        Precinct mergedPrecinct = Precinct.mergePrecinct(precint1, precint2);
        precinctManager.deletePrecinct(precinctId2);
        precinctManager.updatePrecinct(precinctId1, mergedPrecinct);

        return ErrorGen.ok();
    }

    // TODO: wait for script
    // @GetMapping("verifyPrecinctShape")
    // public void verifyPrecinctShape(){

    // }
}
