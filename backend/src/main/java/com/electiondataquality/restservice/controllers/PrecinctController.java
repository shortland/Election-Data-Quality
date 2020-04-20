package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.restservice.features.precinct.Precinct;

@RestController
public class PrecinctController {
    @GetMapping("/shapeOfPrecinct")
    public Precinct getShapeOfPrecinct(@RequestParam(value = "precinctId") int precinctId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        System.out.println(target.getDemographicData().toString());
        return target;
    }

    // TODO: Change return type to ControllerError
    @PutMapping("/shapeOfPrecinct")
    public void updateShapeOfPrecicnt(@RequestParam(value = "precinctId") int precinctId,
            @RequestParam(value = "shape") double[][][] shape) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct targetPrecinct = precinctManager.getPrecicnt(precinctId);
        if (targetPrecinct != null) {
            targetPrecinct.setShape(shape);
        } else {
            // return Error
        }
    }
}