package com.electiondataquality.restservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
public class ErrorController {

    /**
     * Mark an error as corrected.
     * 
     * @param precinctId
     * @param errorId
     * @return
     */
    @CrossOrigin
    @GetMapping("/correctError")
    public ErrorJ setErrorAsCorrected(@RequestParam int precinctId, @RequestParam int errorId) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getPrecicnt(precinctId);

        if (target != null) {
            if (target.getPrecinctErrors() != null) {
                target.getPrecinctError(errorId).resolved();

                return ErrorGen.ok();
            } else {
                return ErrorGen.create("there are no errors in this precinct");
            }
        } else {
            return ErrorGen.create("unable to get precinct");
        }
    }
}
