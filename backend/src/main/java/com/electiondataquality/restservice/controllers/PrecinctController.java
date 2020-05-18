package com.electiondataquality.restservice.controllers;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Optional;
import java.util.ArrayList;
import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;
import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.features.precinct.error.PrecinctError;
import com.electiondataquality.geometry.Geometry;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.util.RawGeometryToShape;
import com.electiondataquality.jpa.managers.PrecinctEntityManager;
import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.DemographicTable;
import com.electiondataquality.jpa.tables.ElectionDataTable;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.jpa.tables.FeatureTable;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.enums.API_STATUS;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class PrecinctController {

    /**
     * Get the boundary data/shape of a precinct.
     * 
     * NOTE: Tested 5/6
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/shapeOfPrecinct")
    public ApiResponse getShapeOfPrecinct(@RequestParam String precinctId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            Map<String, Object> result = new HashMap<>();
            Precinct target = new Precinct(targetData.get());

            result.put("id", target.getId());
            result.put("geometry", target.geometry);

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, result);
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct shape");
    }

    /**
     * Get the boundary data/shape of a precinct.
     * 
     * NOTE: Tested 5/6
     * 
     * @param countyId
     * @return
     * @throws Exception
     */
    @GetMapping("/shapeOfPrecinctByCounty")
    public ApiResponse getShapeOfPrecinctByCountyId(@RequestParam String countyId) throws Exception {
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        RestServiceApplication.logger.info("Method:" + method);

        /**
         * Return the datastore cache when valid
         */
        if (RestServiceApplication.dataStore.isValid(method + "_" + countyId)) {
            RestServiceApplication.logger.info("Using datastore cache");

            return ResponseGen.create(API_STATUS.OK, (new ObjectMapper())
                    .readValue(RestServiceApplication.dataStore.readFile(method + "_" + countyId), Set.class));
        }

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        List<PrecinctFeature> targetPrecincts = pem.findAllPrecinctFeaturesByCountyId(countyId);

        if (targetPrecincts != null) {
            Set<Object> precinctsShape = new HashSet<>();

            for (PrecinctFeature pf : targetPrecincts) {
                Map<String, Object> result = new HashMap<>();
                Precinct target = new Precinct(pf);

                result.put("id", target.getId());
                result.put("geometry", target.geometry);

                precinctsShape.add(result);
            }

            pem.cleanup();

            /**
             * Save the datastore cache
             */
            RestServiceApplication.dataStore.saveFile(method + "_" + countyId, precinctsShape);

            return ResponseGen.create(API_STATUS.OK, precinctsShape);
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct shape");
    }

    /**
     * Get the shape of multiple precincts.
     * 
     * NOTE: Tested 5/6, throw error while the precinctId don't exist
     * 
     * @param precinctIds
     * @return
     */
    @GetMapping("/multiplePrecinctShapes")
    public ApiResponse getMultipleprecincts(@RequestParam(value = "precinctIdList") String[] precinctIds) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < precinctIds.length; i++) {
            Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctIds[i]);

            if (targetData.isPresent()) {
                HashMap<String, Object> geometry = new HashMap<>();
                Precinct target = new Precinct(targetData.get());

                geometry.put(precinctIds[i], target.geometry);
                pList.add(geometry);
            }
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.OK, pList);
    }

    /**
     * Get information of a precinct (detailed data about it - not it's shape).
     * 
     * NOTE: Tested 5/6
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/precinctInfo")
    public ApiResponse getPrecinctInfo(@RequestParam String precinctId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            // for (ErrorTable et : targetData.get().getFeature().getErrors()) {
            // System.out.println(et.toString());
            // et.printComments();
            // }
            Map<String, Object> result = new HashMap<>();
            Precinct target = new Precinct(targetData.get());

            result.put("id", target.getId());
            result.put("canonicalName", target.getCanonicalName());
            result.put("fullName", target.getFullName());
            result.put("parentDistrictId", target.getParentDistrictId());
            result.put("neighborsId", target.getNeighborsId());
            result.put("votingData", target.getVotingData());
            result.put("demographicData", target.getDemographicData());
            result.put("precinctErrors", target.getPrecinctErrors());
            result.put("isGhost", target.getIsGhost());

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, result);
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct info");
    }

    /**
     * Get the neighboring precincts of a specified precinct.
     * 
     * NOTE: Tested 5/6
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/neighborsOfPrecinct")
    public ApiResponse getNeighborsOfPrecinct(@RequestParam String precinctId) {
        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            Precinct target = new Precinct(targetData.get());
            List<String> neighbors = new ArrayList<>();

            for (String id : target.getNeighborsId()) {
                neighbors.add(id);
            }

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, neighbors);
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Update the shape of a precinct.
     * 
     * TODO: throws error: can't find geometry parameter. (I guess json array and
     * ArrayList are not the same so giving an array in json can't be detected as
     * geometry)
     * 
     * @param precinctId
     * @param shape
     * @return
     */
    @RequestMapping(value = "/updateShape", method = RequestMethod.PUT)
    public ApiResponse updateShapeOfPrecicnt(@RequestParam String precinctId, @RequestBody Geometry geometry) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            int featureId = targetData.get().getFeature().getFeatureId();
            Optional<FeatureTable> targetFeature = pem.findFeatureByFeatureId(featureId);

            if (targetFeature.isPresent()) {
                RestServiceApplication.logger.info("Original FeatureTable:" + targetFeature.get());

                targetFeature.get().update(geometry);

                RestServiceApplication.logger.info("Updated FeatureTable:" + targetFeature.get());

                pem.cleanup();

                return ResponseGen.create(API_STATUS.OK, "successfully updated shape of precinct");
            }

            pem.cleanup();

            return ResponseGen.create(API_STATUS.ERROR, "cannot find feature with the specified feature id");
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Delete a precinct.
     * 
     * NOTE: Tested 5/6
     * 
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/deletePrecinct")
    public ApiResponse deletePrecinct(@RequestParam String precinctId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            pem.removePrecinct(targetData.get());
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "successfully deleted specified precinct");
    }

    /**
     * Define a precinct to Ghost Precinct
     * 
     * NOTE: Tested 5/6
     * 
     * @param precinctId
     * @param isGhost
     * @return
     */
    @GetMapping("/defineGhostPrecinct")
    public ApiResponse setGhost(@RequestParam String precinctId, @RequestParam boolean isGhost) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            targetData.get().setIsGhost(isGhost);

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully updated precinct ghost property");
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Update the information of a precinct (not it's shape and it's
     * voting,demographic,error).
     * 
     * NOTE: tested 5/6 (don't allow us to update primary key, but still need
     * attribute id for creating Precinct)
     * 
     * @param precinctId
     * @param info
     * @return
     */
    @RequestMapping(value = "/updatePrecinctInfo", method = RequestMethod.PUT)
    public ApiResponse updatePrecinctInfo(@RequestParam String precinctId, @RequestBody Precinct info) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            RestServiceApplication.logger.info("Original PrecinctFeature:" + targetData.get());

            targetData.get().update(info);

            RestServiceApplication.logger.info("Updated PrecinctFeature:" + targetData.get());

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully updated precinct info");
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Update the voting data of a precinct.
     * 
     * '{"electionData": {"PRES2016": {"resultsByParty": {"REPUBLICAN":
     * 0,"DEMOCRAT": 100,"LIBRATARIAN": 0,"OTHER": 100},"election":
     * "PRES2016"},"CONG2016": {"resultsByParty": {"REPUBLICAN": 10,"DEMOCRAT":
     * 101,"LIBRATARIAN": 20,"OTHER":9},"election": "CONG2016"}}}'
     * 
     * NOTE: tested 5/6
     * 
     * @param precinctId
     * @param votingData
     * @return
     */
    @RequestMapping(value = "/updateVotingData", method = RequestMethod.PUT)
    public ApiResponse updateVotingData(@RequestParam String precinctId, @RequestBody VotingData votingData) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            Set<ElectionDataTable> electionDataSet = targetData.get().getElectionDataSet();
            Set<ELECTIONS> elections = votingData.getAllElections();

            if (electionDataSet != null) {
                for (ElectionDataTable edt : electionDataSet) {
                    ELECTIONS e = edt.getElection();

                    if (elections.contains(e)) {
                        RestServiceApplication.logger.info("Original ElectionDataTable:" + edt);

                        edt.update(votingData.getElectionData(e), precinctId);

                        RestServiceApplication.logger.info("Updated ElectionDataTable:" + edt);

                        elections.remove(e);
                    }
                }
            }

            for (ELECTIONS remainElections : elections) {
                ElectionDataTable electionDataTable = new ElectionDataTable(votingData.getElectionData(remainElections),
                        precinctId);

                pem.persistElectionData(electionDataTable);

                electionDataSet.add(electionDataTable);
            }

            targetData.get().setElectionData(electionDataSet);

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully updated voting data");
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Update the precinctErrors of a precinct. if not exist create a new Error.
     * 
     * 
     * TODO: need test
     * 
     * 
     * @param precinctId
     * @param precinctError
     * @return
     */
    @CrossOrigin
    @PutMapping("/updatePrecinctError")
    public ApiResponse updatePreinctError(@RequestParam String precinctId, @RequestParam PrecinctError precinctError) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            Set<ErrorTable> errors = targetData.get().getFeature().getErrors();
            int errorId = precinctError.getId();

            if (targetData.get().getFeature().getErrorsId().contains(errorId)) {
                for (ErrorTable et : errors) {
                    if (et.getErrorId() == errorId) {
                        RestServiceApplication.logger.info("Original ErrorTable:" + et);

                        et.update(precinctError, precinctId);

                        RestServiceApplication.logger.info("Updated ErrorTable:" + et);
                    }
                }

                targetData.get().getFeature().setErrors(errors);
                pem.cleanup();

                return ResponseGen.create(API_STATUS.OK, "successfully updated precinct error");
            }

            ErrorTable newError = new ErrorTable(precinctError, targetData.get().getFeature().getFeatureId(),
                    precinctId);

            newError.setPrecinctId(precinctId);
            newError.setFeatureId(targetData.get().getFeature().getFeatureId());
            errors.add(newError);
            targetData.get().getFeature().setErrors(errors);

            pem.persistError(newError);
            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully updated precinct error");
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Update the demographic data of a precinct.
     * 
     * 
     * NOTE: tested 5/6
     * 
     * '{"ASIAN": 100,"BLACK":100,"NATIVE_AMERICAN": 300,"NATIVE_HAWAIIAN" :
     * 600,"OTHER": 100,"WHITE": 100}'
     * 
     * @param precinctId
     * @param demographicData Map<String,Integer>
     * @return
     */
    // @GetMapping("/updateDemographicData")
    @RequestMapping(value = "/updateDemographicData", method = RequestMethod.PUT)
    public ApiResponse updateDemographicData(@RequestParam String precinctId,
            @RequestBody Map<String, Integer> demographicData) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);
        Optional<PrecinctFeature> targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData.isPresent()) {
            DemographicTable newDemographic = targetData.get().getDemogrpahicTable();

            RestServiceApplication.logger.info("Original DemographicTable:" + newDemographic);

            newDemographic.update(demographicData, precinctId);

            RestServiceApplication.logger.info("Original DemographicTable:" + newDemographic);

            targetData.get().setDemographicTable(newDemographic);

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully updated demographics data");
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Add a neighbor to a precinct's neighbor list.
     * 
     * 
     * NOTE: tested 4/29/2020
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/addPrecinctNeighbor")
    public ApiResponse addPrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);

        Optional<PrecinctFeature> targetData1 = pem.findPrecinctFeatureById(precinctId1);
        Optional<PrecinctFeature> targetData2 = pem.findPrecinctFeatureById(precinctId2);

        if (targetData1.isPresent() && targetData2.isPresent()) {
            RestServiceApplication.logger
                    .info("Original PrecinctFeature 1 Neighbors:" + targetData1.get().getNeighborsIdSet());
            RestServiceApplication.logger
                    .info("Original PrecinctFeature 2 Neighbors:" + targetData2.get().getNeighborsIdSet());

            targetData1.get().addNeighbor(precinctId2);
            targetData2.get().addNeighbor(precinctId1);

            RestServiceApplication.logger
                    .info("Updated PrecinctFeature 1 Neighbors:" + targetData1.get().getNeighborsIdSet());
            RestServiceApplication.logger
                    .info("Updated PrecinctFeature 2 Neighbors:" + targetData2.get().getNeighborsIdSet());

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully added precinct neighbor");
        }

        pem.cleanup();

        return ResponseGen.create(API_STATUS.ERROR, "unable to get one or both of the specified precincts");
    }

    /**
     * Delete a neighbor of a precinct. Removes a precinct from the specified
     * precincts neighbor list.
     * 
     * 
     * NOTE: tested 4/29/2020
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/deletePrecinctNeighbor")
    public ApiResponse deletePrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);

        Optional<PrecinctFeature> targetData1 = pem.findPrecinctFeatureById(precinctId1);
        Optional<PrecinctFeature> targetData2 = pem.findPrecinctFeatureById(precinctId2);

        if (targetData1.isPresent() && targetData2.isPresent()) {
            RestServiceApplication.logger
                    .info("Original PrecinctFeature 1 Neighbors:" + targetData1.get().getNeighborsIdSet());
            RestServiceApplication.logger
                    .info("Original PrecinctFeature 2 Neighbors:" + targetData2.get().getNeighborsIdSet());

            targetData1.get().deleteNeighbor(precinctId2);
            targetData2.get().deleteNeighbor(precinctId1);

            RestServiceApplication.logger
                    .info("Updated PrecinctFeature 1 Neighbors:" + targetData1.get().getNeighborsIdSet());
            RestServiceApplication.logger
                    .info("Updated PrecinctFeature 2 Neighbors:" + targetData2.get().getNeighborsIdSet());

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully deleted precinct neighbor");
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to get one or both of the specified precincts");
    }

    /**
     * Merge two precincts together.
     * 
     * http://0.0.0.0:1234/mergePrecinct?precinctId1=11790&precinctId2=11791
     * 
     * NOTE: tested 5/12
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @GetMapping("/mergePrecinct")
    public ApiResponse mergePrecincts(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        RestServiceApplication.logger.info("Method: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctEntityManager pem = new PrecinctEntityManager(RestServiceApplication.emFactoryPrecinct);

        Optional<PrecinctFeature> targetData1 = pem.findPrecinctFeatureById(precinctId1);
        Optional<PrecinctFeature> targetData2 = pem.findPrecinctFeatureById(precinctId2);

        if (targetData1.isPresent() && targetData2.isPresent()) {
            Precinct target1 = new Precinct(targetData1.get());
            Precinct target2 = new Precinct(targetData2.get());

            // merged the preicncts by using the mergedPrecinct method for precinct(not
            // precinct feature)
            Precinct mergedPrecinct = Precinct.mergePrecinct(target1, target2);
            String mergedPrecinctId = mergedPrecinct.getId();

            // update precinct Info
            RestServiceApplication.logger.info("Original PrecinctFeature: " + targetData1.get());

            targetData1.get().update(mergedPrecinct);

            RestServiceApplication.logger.info("Updated PrecinctFeature: " + targetData1.get());

            /**
             * Merge the precinct geo data with same technique pre-processing uses; thus via
             * python script.
             */
            Geometry geo1 = RawGeometryToShape.convertRawToGeometry(targetData1.get().getFeature().getGeometry());
            Geometry geo2 = RawGeometryToShape.convertRawToGeometry(targetData2.get().getFeature().getGeometry());

            /** Log original data */
            RestServiceApplication.logger.info("Original Geometry1: " + geo1.toString());
            RestServiceApplication.logger.info("Original Geometry2: " + geo2.toString());

            /** Begin execution of python script to merge the polygons */
            String s;
            Process p;
            BufferedReader stdInput;
            BufferedReader stdError;
            StringBuilder mergeScriptOutput = new StringBuilder();
            boolean hasError = false;

            try {
                // TODO: Not hardcode path to server scripts
                String mergeScript = "python3 ../preprocessing/serverScripts/mergePrecincts.py " + "\""
                        + geo1.rawCoordsAsString() + "\"" + " " + "\"" + geo2.rawCoordsAsString() + "\"";

                RestServiceApplication.logger.info("Executing Script: " + mergeScript);

                p = Runtime.getRuntime().exec(mergeScript);

                stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                while ((s = stdInput.readLine()) != null) {
                    mergeScriptOutput.append(s);
                }

                while ((s = stdError.readLine()) != null) {
                    hasError = true;
                    mergeScriptOutput.append(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Always log script output, even when error
            RestServiceApplication.logger.info("Merge Polygon Script output: " + mergeScriptOutput.toString());

            if (hasError) {
                pem.cleanup();

                return ResponseGen.create(API_STATUS.ERROR,
                        "running merge polygon script resulted in error: " + mergeScriptOutput.toString());
            }

            Geometry newGeo = RawGeometryToShape.convertPolygonRawCoordsToGeometry(mergeScriptOutput.toString());
            String rawNewGeo = RawGeometryToShape.convertGeometryToRaw(newGeo);

            /**
             * Update the raw new geometry
             */
            targetData1.get().getFeature().setGeometry(rawNewGeo);

            RestServiceApplication.logger.info("Updated Merged Geometry: "
                    + RawGeometryToShape.convertRawToGeometry(targetData1.get().getFeature().getGeometry()));

            // update precinct demogrphic
            if (mergedPrecinct.getDemographicData() != null) {
                DemographicTable demographic = targetData1.get().getDemogrpahicTable();
                DemographicData mergedDemogrphic = mergedPrecinct.getDemographicData();

                RestServiceApplication.logger.info("Original DemographicTable: " + demographic.toString());

                demographic.update(mergedDemogrphic, mergedPrecinctId);

                RestServiceApplication.logger.info("Updated DemographicTable: " + demographic.toString());

                targetData1.get().setDemographicTable(demographic);
            }

            // update precinct electionData
            if (mergedPrecinct.getVotingData() != null) {
                VotingData votingAfterMerged = mergedPrecinct.getVotingData();
                Set<ELECTIONS> electionsAfterMerged = votingAfterMerged.getAllElections();
                Set<ElectionDataTable> electionData = targetData1.get().getElectionDataSet();

                if (electionData != null) {
                    for (ElectionDataTable edt : electionData) {
                        ELECTIONS e = edt.getElection();

                        if (electionsAfterMerged.contains(e)) {
                            RestServiceApplication.logger.info("Original ElectionDataTable:" + edt);

                            edt.update(votingAfterMerged.getElectionData(e), mergedPrecinctId);

                            RestServiceApplication.logger.info("Updated ElectionDataTable:" + edt);

                            electionsAfterMerged.remove(e);
                        }
                    }
                }

                for (ELECTIONS remainElections : electionsAfterMerged) {
                    ElectionDataTable electionDataTable = new ElectionDataTable(
                            votingAfterMerged.getElectionData(remainElections), mergedPrecinctId);

                    pem.persistElectionData(electionDataTable);
                    electionData.add(electionDataTable);
                }

                targetData1.get().setElectionData(electionData);
            }

            // TODO: errors are under features so after mergeing the precincts make sure the
            // new feature has to have the same id as the precinct1's feature_idn
            if (mergedPrecinct.getPrecinctErrors() != null) {
                Map<Integer, PrecinctError> mergedErrors = mergedPrecinct.getPrecinctErrors();
                Set<Integer> mergedErrorIds = mergedErrors.keySet();
                Set<ErrorTable> oldErrors = targetData1.get().getFeature().getErrors();

                // update targetData1's errors
                for (ErrorTable et : oldErrors) {
                    if (mergedErrorIds.contains(et.getErrorId())) {
                        RestServiceApplication.logger.info("Original ErrorTable:" + et);

                        et.update(mergedErrors.get(et.getErrorId()), mergedPrecinctId);

                        RestServiceApplication.logger.info("Updated ErrorTable:" + et);

                        mergedErrorIds.remove(et.getErrorId());
                    }
                }

                // TODO: delete the error's under targetData2 and recreate them
                for (int remainErrorId : mergedErrorIds) {
                    int featureId = targetData1.get().getFeature().getFeatureId();
                    ErrorTable oldErrorTable = targetData2.get().getFeature().getErrorById(remainErrorId);
                    pem.removeError(oldErrorTable);

                    ErrorTable newErrorTable = new ErrorTable(mergedErrors.get(remainErrorId), featureId,
                            mergedPrecinctId);

                    newErrorTable.setFeatureId(featureId);
                    pem.persistError(newErrorTable);
                }
            }

            pem.cleanup();

            return ResponseGen.create(API_STATUS.OK, "successfully merged precincts");
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to get one or both of the specified precincts");
    }

    /* TODO: Below endpoints need discussions first */

    /**
     * Get the original information of a precinct (don't show any user edits etc).
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/originalPrecinctInfo")
    public ApiResponse getOriginalPrecinctInfo(@RequestParam String precinctId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        Map<String, Object> result = new HashMap<>();
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

            return ResponseGen.create(API_STATUS.OK, result);
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Get the original precinct shape.
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctId
     * @return
     */
    @GetMapping("/originalPrecinctShape")
    public ApiResponse getOriginalPrecinctShape(@RequestParam String precinctId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        Map<String, Object> result = new HashMap<>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getOriginalPrecinct(precinctId);

        if (target != null) {
            result.put("id", target.getId());
            result.put("shape", target.getShape());

            return ResponseGen.create(API_STATUS.OK, result);
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the specified precinct");
    }

    /**
     * Get the original shape of multiple precincts.
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctIds
     * @return
     */
    @GetMapping("/originalMultPrecinctShapes")
    public ApiResponse getMultipleOriginalprecincts(@RequestParam(value = "precinctIdList") String[] precinctIds) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();

        for (int i = 0; i < precinctIds.length; i++) {
            Precinct target = precinctManager.getOriginalPrecinct(precinctIds[i]);

            if (target != null) {
                HashMap<String, Object> shapeMap = new HashMap<>();

                shapeMap.put(precinctIds[i], target.getShape());
                pList.add(shapeMap);
            }
        }

        return ResponseGen.create(API_STATUS.OK, pList);
    }

    /**
     * Create a new precinct object.
     * 
     * TODO: have to find a way to create a new precinct id
     * 
     * @param mp
     * @return
     */
    @GetMapping("/createNewPrecinct")
    public ApiResponse createNewPrecinct(@RequestParam MultiPolygon mp) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        BigInteger bigintId = new BigInteger(precinctManager.getLargestPrecinctId());

        String newId = new String(bigintId.add(new BigInteger("1")).toByteArray());
        Precinct newPrecinct = new Precinct(newId, "", "", null, null, null, null, null, mp);

        precinctManager.addPrecinct(newPrecinct);

        return ResponseGen.create(API_STATUS.OK, "successfully created new precinct");
    }
}
