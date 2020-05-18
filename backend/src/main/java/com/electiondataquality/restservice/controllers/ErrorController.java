package com.electiondataquality.restservice.controllers;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.jpa.managers.ErrorEntityManager;
import com.electiondataquality.jpa.managers.PrecinctEntityManager;
import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.enums.API_STATUS;

@RestController
@CrossOrigin
public class ErrorController {

    @GetMapping("/getAllError")
    public ApiResponse getAllError() {
        ErrorEntityManager eem = new ErrorEntityManager(RestServiceApplication.emFactoryError);
        List<ErrorTable> allErrors = eem.findAllErrors();
        eem.cleanup();
        return ResponseGen.create(API_STATUS.OK, allErrors);
    }

    /**
     * Mark an error as corrected.
     * 
     * NOTE: tested 5/5/2020
     * 
     * @param precinctId
     * @param errorId
     * @return
     */
    @GetMapping("/resolveError")
    public ApiResponse setErrorAsCorrected(@RequestParam String precinctId, @RequestParam int errorId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetPrecinct = pem.findPrecinctFeatureById(precinctId);

        if (targetPrecinct.isPresent()) {
            Set<ErrorTable> errors = targetPrecinct.get().getFeature().getErrors();
            Set<Integer> errorsId = targetPrecinct.get().getFeature().getErrorsId();

            if (errorsId.contains(errorId)) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        et.setResolved(true);
                    }
                }

                targetPrecinct.get().getFeature().setErrors(errors);

                pem.cleanup();

                return ResponseGen.create(API_STATUS.OK, "error successfully resolved");
            }

            pem.cleanup();

            return ResponseGen.create(API_STATUS.ERROR, "there are no errors in the specified precinct");
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    // NOTE: tested 5/5/2020
    @GetMapping("/unresolveError")
    public ApiResponse setErrorAsIncorrected(@RequestParam String precinctId, @RequestParam int errorId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetPrecinct = pem.findPrecinctFeatureById(precinctId);

        if (targetPrecinct.isPresent()) {
            Set<ErrorTable> errors = targetPrecinct.get().getFeature().getErrors();
            Set<Integer> errorsId = targetPrecinct.get().getFeature().getErrorsId();

            if (errorsId.contains(errorId)) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        et.setResolved(false);
                    }
                }

                targetPrecinct.get().getFeature().setErrors(errors);

                pem.cleanup();

                return ResponseGen.create(API_STATUS.OK, "error successfully unresolved");
            }

            pem.cleanup();

            return ResponseGen.create(API_STATUS.ERROR, "there are no errors in the specified precinct");
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }
}
