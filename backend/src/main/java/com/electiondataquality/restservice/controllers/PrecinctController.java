package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.electiondataquality.restservice.features.precinct.Precinct;
import com.electiondataquality.restservice.features.precinct.error.PrecinctError;
import com.electiondataquality.restservice.geometry.MultiPolygon;
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
public class PrecinctController {
    @GetMapping("/shapeOfPrecinct")
    public HashMap<String, Object> getShapeOfPrecinct(@RequestParam(value = "precinctId") int precinctId) {
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

    @GetMapping("/precinctInfo")
    public HashMap<String, Object> getPrecinctInfo(@RequestParam(value = "precinctId") int precinctId) {
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

    @GetMapping("/originalPrecinctInfo")
    public HashMap<String, Object> getOriginalPrecinctInfo(@RequestParam(value = "precinctId") int precinctId) {
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

    @GetMapping("/originalPrecinctShape")
    public HashMap<String, Object> getOriginalPrecinctShape(@RequestParam(value = "precinctId") int precinctId) {
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

    @GetMapping("/neighborsOfPrecinct")
    public ArrayList<Integer> getNeighborsOfPrecinct(@RequestParam(value = "precinctId") int precinctId) {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        if (target != null) {
            for (Integer id : target.getNeighborsId()) {
                neighbors.add(id);
            }

        } else {
            // return error;
        }
        return neighbors;
    }

    // TODO: Change return type to ControllerError
    // TODO: Find out how to pass in the shape value
    @GetMapping("/shapesOfPrecinct")
    public void updateShapeOfPrecicnt(@RequestParam(value = "precinctId") int precinctId,
            @RequestParam(value = "shape") ArrayList<ArrayList<ArrayList<double[]>>> shape) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct targetPrecinct = precinctManager.getPrecicnt(precinctId);
        if (targetPrecinct != null) {
            targetPrecinct.setShape(new MultiPolygon(shape));
        } else {
            // return Error
        }
    }

    // TODO: Change return type to ControllerError
    @GetMapping("/deletePrecinct")
    public void deletePrecinct(@RequestParam(value = "precinctId") int precinctId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        precinctManager.deletePrecinct(precinctId);
    }

    // TODO: Return ControllerError
    @GetMapping("/defineGhostPrecinct")
    public void setGhost(@RequestParam(value = "precinctId") int precinctId,
            @RequestParam(value = "isGhost") boolean isGhost) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        if (target != null) {
            target.setGhost(isGhost);
        } else {
            // return error
        }
    }

    @RequestMapping(value = "/updatePrecinctInfo", method = RequestMethod.PUT)
    public ErrorJ updatePrecinctInfo(@RequestParam(value = "precinctId") int precinctId, @RequestBody Precinct info) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

        if (target != null) {
            target.updatePrecinct(info);
            return ErrorGen.ok();
        } else {
            return ErrorGen.create("unable to find target precinct by id");
        }
    }

    @PutMapping("/updateVotingData")
    public void updateVotingData(@RequestParam(value = "precinctId") int precinctId,
            @RequestParam(value = "votingData") VotingData vd) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        if (target != null) {
            target.setVotingData(vd);
        } else {
            // return error;
        }
    }

    @PutMapping("/updateDemographicData")
    public void updateDemographicData(@RequestParam(value = "precinctId") int precinctId,
            @RequestParam(value = "demographicData") DemographicData vd) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        if (target != null) {
            target.setDemographicData(vd);
        } else {
            // return error;
        }
    }

    @PutMapping("/addPrecinctNeighbor")
    public void addPrecinctAsNeighbor(@RequestParam(value = "precinctId1") int p1,
            @RequestParam(value = "precinctId2") int p2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecicnt(p1);
        Precinct target2 = precinctManager.getPrecicnt(p2);
        if (target1 != null && target2 != null) {
            target1.addNeighbor(p2);
            target2.addNeighbor(p1);
        } else {
            // return error;
        }
    }

    @DeleteMapping("/deletePrecinctNeighbor")
    public void deletePrecinctAsNeighbor(@RequestParam(value = "precinctId1") int p1,
            @RequestParam(value = "precinctId2") int p2) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target1 = precinctManager.getPrecicnt(p1);
        Precinct target2 = precinctManager.getPrecicnt(p2);
        if (target1 != null && target2 != null) {
            target1.deleteNeighbor(p2);
            target2.deleteNeighbor(p1);
        } else {
            // return error;
        }
    }

    // + createNewPrecinct(Polygon shape) : Error

    // + mergeTwoPrecincts(int precinctID1, int precinctID2) : Error
    // + updateVotingDataOfPrecinct(int precinctID, VotingData data) : Error
    // + updateDemogDataOfPrecinct(int precinctID, DemographicsData data) : Error
    // + verifyPrecinctShape(Polygon shape) : Error
}
