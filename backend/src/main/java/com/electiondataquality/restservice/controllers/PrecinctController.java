package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PutMapping("/shapeOfPrecinct")
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
}
