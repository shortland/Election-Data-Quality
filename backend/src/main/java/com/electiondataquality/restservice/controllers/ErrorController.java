package com.electiondataquality.restservice.controllers;

import java.util.Set;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
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
            Set<Integer> errorsId = targetPrecinct.get().getErrorsId();
            if (errorsId.contains(errorId)) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        et.setResolved(true);
                    }
                }
                targetPrecinct.get().setErrors(errors);
                pem.cleanup();
                return ErrorGen.ok();
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
            Set<Integer> errorsId = targetPrecinct.get().getErrorsId();
            if (errorsId.contains(errorId)) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        et.setResolved(false);
                    }
                }
                targetPrecinct.get().setErrors(errors);
                pem.cleanup();
                return ErrorGen.ok();
            }
            pem.cleanup();
            return ErrorGen.create("there are no errors in this precinct");
        }
        pem.cleanup();
        return ErrorGen.create("unable to get precinct");
    }
}
