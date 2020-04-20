package com.electiondataquality.restservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.restservice.features.precinct.Precinct;
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
public class ErrorController {
    @GetMapping("/correctError")
    public ErrorJ setErrorAsCorrected(@RequestParam(value = "precinctId") int precinctId,
            @RequestParam(value = "errorId") int errorId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);
        if (target != null) {
            if (target.getPrecinctErrors() != null) {
                target.getPrecinctError(errorId).resolved();
                return ErrorGen.create("");
            } else {
                return ErrorGen.create("there are no errors in this precinct");
            }
        } else {
            return ErrorGen.create("unable to get precinct");
        }
    }
}
