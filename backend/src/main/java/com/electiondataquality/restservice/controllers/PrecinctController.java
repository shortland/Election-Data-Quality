package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.restservice.features.precinct.Precinct;
import com.electiondataquality.restservice.geometry.MultiPolygon;

@RestController
public class PrecinctController {
    @GetMapping("/shapeOfPrecinct")
    public Precinct getShapeOfPrecinct(@RequestParam(value = "precinctId") int precinctId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        return target;
    }

    // TODO: Change return type to ControllerError
    // TODO: Find out how to pass in the shape value
    @PutMapping("/shapesOfPrecinct")
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
    @DeleteMapping("/deletePrecinct")
    public void deletePrecinct(@RequestParam(value = "precinctId") int precinctId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        precinctManager.deletePrecinct(precinctId);
    }

    @GetMapping("/multiplePrecinctShapes")
    public ArrayList<Precinct> getMultipleprecincts(@RequestParam(value = "precinctIdList") int[] precinctIds) {
        ArrayList<Precinct> pList = new ArrayList<Precinct>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        for (int i = 0; i < precinctIds.length; i++) {
            Precinct target = precinctManager.getPrecicnt(precinctIds[i]);
            if (target != null) {
                pList.add(target);
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

    // TODO: Return ControllerError
    @PutMapping("/defineGhostPrecinct")
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

    // + createNewPrecinct(Polygon shape) : Error
    // + updatePrecinctInfo(int precinctID, Precinct precinctInfo) : Error
    // + getPrecinctInfo(int precinctID) : Precinct
    // + getOriginalPrecinctInfo(int precinctID) : Precinct
    // + getOriginalPrecinctShape(int precinctID) : Polygon
    // + getMultipleOriginalPrecinctShapes(Array<int> precinctList) : Array<Polygon>

    // + addPrecinctAsNeighbor(int precinctID1, int precinctID2) : Error
    // + deletePrecinctAsNeighbor(int precinctID1, int precinctID2) : Error
    // + mergeTwoPrecincts(int precinctID1, int precinctID2) : Error
    // + updateVotingDataOfPrecinct(int precinctID, VotingData data) : Error
    // + updateDemogDataOfPrecinct(int precinctID, DemographicsData data) : Error
    // + verifyPrecinctShape(Polygon shape) : Error

}
