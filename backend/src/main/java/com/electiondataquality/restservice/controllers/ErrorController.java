package com.electiondataquality.restservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.PrecinctManager;

import java.util.Optional;
import java.util.Set;

import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.jpa.managers.PrecinctEntityManager;
import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
@CrossOrigin
public class ErrorController {

    /**
     * Mark an error as corrected.
     * 
     * NOTE: tested 4/29/2020
     * 
     * @param precinctId
     * @param errorId
     * @return
     */
    @GetMapping("/resolveError")
    public ErrorJ setErrorAsCorrected(@RequestParam String precinctId, @RequestParam int errorId) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetPrecinct = pem.findPrecinctFeatureById(precinctId);

        if (targetPrecinct.isPresent()) {
            Set<ErrorTable> errors = targetPrecinct.get().getErrors();
            if (errors != null) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        Optional<ErrorTable> targetError = pem.findErrorsByPrecinctId(errorId);
                        if (targetError.isPresent()) {
                            targetError.get().setResolved(true);
                            pem.cleanup();
                            return ErrorGen.ok();
                        } else {
                            pem.cleanup();
                            return ErrorGen.create("cannot find error with the errorId");
                        }
                    }
                }
                pem.cleanup();
                return ErrorGen.create("error don't exist in this precinct");
            }
            pem.cleanup();
            return ErrorGen.create("there are no errors in this precinct");
        }
        pem.cleanup();
        return ErrorGen.create("unable to get precinct");
    }

    @GetMapping("/unresolveError")
    public ErrorJ setErrorAsIncorrected(@RequestParam String precinctId, @RequestParam int errorId) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetPrecinct = pem.findPrecinctFeatureById(precinctId);

        if (targetPrecinct.isPresent()) {
            Set<ErrorTable> errors = targetPrecinct.get().getErrors();
            if (errors != null) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        Optional<ErrorTable> targetError = pem.findErrorsByPrecinctId(errorId);
                        if (targetError.isPresent()) {
                            targetError.get().setResolved(false);
                            pem.cleanup();
                            return ErrorGen.ok();
                        } else {
                            pem.cleanup();
                            return ErrorGen.create("cannot find error with the errorId");
                        }
                    }
                }
                pem.cleanup();
                return ErrorGen.create("error don't exist in this precinct");
            }
            pem.cleanup();
            return ErrorGen.create("there are no errors in this precinct");
        }
        pem.cleanup();
        return ErrorGen.create("unable to get precinct");
    }
}
