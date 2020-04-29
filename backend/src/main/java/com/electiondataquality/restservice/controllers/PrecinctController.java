package com.electiondataquality.restservice.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;
import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.features.precinct.error.PrecinctError;
import com.electiondataquality.geometry.Geometry;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.jpa.managers.PrecinctEntityManager;
import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.ElectionDataTable;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
public class PrecinctController {

    /**
     * Get the boundary data/shape of a precinct.
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/shapeOfPrecinct")
    public HashMap<String, Object> getShapeOfPrecinct(@RequestParam String precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);

        Precinct target = new Precinct(targetData);
        if (target != null) {
            result.put("id", target.getId());
            result.put("geometry", target.geometry);
            pem.cleanup(true);
            return result;
        } else {
            pem.cleanup(true);
            return null;
        }
    }

    /**
     * Get the shape of multiple precincts.
     * 
     * NOTE: Tested
     * 
     * @param precinctIds
     * @return
     */
    @CrossOrigin
    @GetMapping("/multiplePrecinctShapes")
    public ArrayList<HashMap<String, Object>> getMultipleprecincts(
            @RequestParam(value = "precinctIdList") String[] precinctIds) {
        ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);

        for (int i = 0; i < precinctIds.length; i++) {
            PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctIds[i]);
            Precinct target = new Precinct(targetData);
            if (target != null) {
                HashMap<String, Object> geometry = new HashMap<String, Object>();
                geometry.put(precinctIds[i], target.geometry);
                pList.add(geometry);
            }
        }
        pem.cleanup(true);

        return pList;
    }

    /**
     * Get information of a precinct (detailed data about it - not it's shape).
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/precinctInfo")
    public HashMap<String, Object> getPrecinctInfo(@RequestParam String precinctId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);

        HashMap<String, Object> result = new HashMap<String, Object>();
        Precinct target = new Precinct(targetData);
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
            pem.cleanup(true);
            return result;
        } else {
            pem.cleanup(true);
            return null;
        }

    }

    /**
     * Get the original information of a precinct (don't show any user edits etc).
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/originalPrecinctInfo")
    public HashMap<String, Object> getOriginalPrecinctInfo(@RequestParam String precinctId) {
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
        }

        return null;
    }

    /**
     * Get the original precinct shape.
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/originalPrecinctShape")
    public HashMap<String, Object> getOriginalPrecinctShape(@RequestParam String precinctId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Precinct target = precinctManager.getOriginalPrecinct(precinctId);

        if (target != null) {
            result.put("id", target.getId());
            result.put("shape", target.getShape());

            return result;
        }

        return null;
    }

    /**
     * Get the original shape of multiple precincts.
     * 
     * TODO: Need to find out a way to get the original precicnt
     * 
     * @param precinctIds
     * @return
     */
    @CrossOrigin
    @GetMapping("/originalMultPrecinctShapes")
    public ArrayList<HashMap<String, Object>> getMultipleOriginalprecincts(
            @RequestParam(value = "precinctIdList") String[] precinctIds) {
        ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();

        for (int i = 0; i < precinctIds.length; i++) {
            Precinct target = precinctManager.getOriginalPrecinct(precinctIds[i]);

            if (target != null) {
                HashMap<String, Object> shapeMap = new HashMap<String, Object>();

                shapeMap.put(precinctIds[i], target.getShape());
                pList.add(shapeMap);
            }
        }

        return pList;
    }

    /**
     * Get the neighboring precincts of a specified precinct.
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/neighborsOfPrecinct")
    public ArrayList<String> getNeighborsOfPrecinct(@RequestParam String precinctId) {
        ArrayList<String> neighbors = new ArrayList<String>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);
        Precinct target = new Precinct(targetData);

        if (target != null) {
            for (String id : target.getNeighborsId()) {
                neighbors.add(id);
            }

            return neighbors;
        } else {
            return null;
        }
    }

    /**
     * Get the shape of a precinct.
     * 
     * 
     * TODO: Find out how to pass in the shape value
     * 
     * @param precinctId
     * @param shape
     * @return
     */
    @CrossOrigin
    @GetMapping("/shapesOfPrecinct")
    public ErrorJ updateShapeOfPrecicnt(@RequestParam String precinctId, @RequestParam Geometry geometry) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);
        if (targetData != null) {
            targetData.getFeature().update(geometry);
            pem.cleanup(true);
            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Delete a precinct.
     * 
     * NOTE: Tested 4/28/2020
     * 
     * 
     * @param precinctId
     * @return
     */
    @CrossOrigin
    @GetMapping("/deletePrecinct")
    public ErrorJ deletePrecinct(@RequestParam String precinctId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);
        if (targetData != null) {
            pem.removePrecinct(targetData);
        }
        pem.cleanup(true);
        return ErrorGen.ok();
    }

    /**
     * Define a new ghost precinct.
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @param isGhost
     * @return
     */
    @CrossOrigin
    @GetMapping("/defineGhostPrecinct")
    public ErrorJ setGhost(@RequestParam String precinctId, @RequestParam boolean isGhost) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData != null) {
            targetData.setIsGhost(isGhost);
            pem.cleanup(true);
            return ErrorGen.ok();
        }
        pem.cleanup(true);
        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Update the information of a precinct (not it's shape and it's
     * voting,demographic,error).
     * 
     * TODO: need test
     * 
     * @param precinctId
     * @param info
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/updatePrecinctInfo", method = RequestMethod.PUT)
    public ErrorJ updatePrecinctInfo(@RequestParam String precinctId, @RequestBody Precinct info) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData != null) {
            targetData.update(info);
            pem.cleanup(true);
            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to find target precinct by id");
    }

    /**
     * Update the voting data of a precinct.
     * 
     * 
     * '{"electionData": {"PRES2016": {"resultsByParty": {"REPUBLICAN":
     * 0,"DEMOCRAT": 50,"LIBRATARIAN": 0,"OTHER": 50},"majorityParty":
     * "OTHER","election": "PRES2016"}}}'
     * 
     * TODO: need test
     * 
     * @param precinctId
     * @param votingData
     * @return
     */
    @CrossOrigin
    @GetMapping("/updateVotingData")
    public ErrorJ updateVotingData(@RequestParam String precinctId, @RequestBody VotingData votingData) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData != null) {
            if (targetData.getElectionDataSet() != null) {
                Set<ELECTIONS> elections = votingData.getAllElections();
                for (ElectionDataTable edt : targetData.getElectionDataSet()) {
                    if (elections.contains(edt.getElection())) {
                        edt.update(votingData.getElectionData(edt.getElection()), precinctId);
                        elections.remove(edt.getElection());
                    }
                }
                for (ELECTIONS remainElections : elections) {
                    ElectionDataTable electionDataTable = new ElectionDataTable(
                            votingData.getElectionData(remainElections), precinctId);
                    pem.persistElectionDataTable(electionDataTable);
                }
            }
            pem.cleanup(true);
            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Update the precinctErrors of a precinct.
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
    public ErrorJ updatePreinctError(@RequestParam String precinctId, @RequestParam PrecinctError precinctError) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);
        if (targetData != null) {
            if (targetData.getErrors() != null) {
                int errorId = precinctError.getId();
                for (ErrorTable et : targetData.getErrors()) {
                    if (et.getErrorId() == errorId) {
                        et.update(precinctError, precinctId);
                    }
                }
                return ErrorGen.ok();
            } else {
                return ErrorGen.create("cannot find precinct error");
            }
        } else {
            return ErrorGen.create("cannot find precinct");
        }
    }

    /**
     * Update the demographic data of a precinct.
     * 
     * 
     * TODO: need test
     * 
     * '{"demographicByRace": {"ASIAN": 100,"BLACK":100,"HISPANIC": 100,"OTHER":
     * 100,"WHITE": 100}}'
     * 
     * @param precinctId
     * @param demographicData
     * @return
     */
    @CrossOrigin
    @GetMapping("/updateDemographicData")
    public ErrorJ updateDemographicData(@RequestParam String precinctId, @RequestBody DemographicData demographicData) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData = pem.findPrecinctFeatureById(precinctId);

        if (targetData != null) {
            targetData.getDemogrpahicTable().update(demographicData, precinctId);
            pem.cleanup(true);
            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to get precinct");
    }

    /**
     * Add a neighbor to a precinct's neighbor list.
     * 
     * 
     * TODO: need test
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @CrossOrigin
    @GetMapping("/addPrecinctNeighbor")
    public ErrorJ addPrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData1 = pem.findPrecinctFeatureById(precinctId1);
        PrecinctFeature targetData2 = pem.findPrecinctFeatureById(precinctId2);
        if (targetData1 != null && targetData2 != null) {
            targetData1.addNeighbor(precinctId2);
            targetData2.addNeighbor(precinctId1);
            pem.cleanup(true);
            return ErrorGen.ok();
        } else {
            pem.cleanup(true);
            if (targetData1 == null) {
                return ErrorGen.create("unable to get precinct1");
            } else {
                return ErrorGen.create("unable to get precinct2");
            }
        }

    }

    /**
     * Delete a neighbor of a precinct. Removes a precinct from the specified
     * precincts neighbor list.
     * 
     * 
     * TODO: need test
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @CrossOrigin
    @GetMapping("/deletePrecinctNeighbor")
    public ErrorJ deletePrecinctAsNeighbor(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData1 = pem.findPrecinctFeatureById(precinctId1);
        PrecinctFeature targetData2 = pem.findPrecinctFeatureById(precinctId2);
        if (targetData1 != null && targetData2 != null) {
            targetData1.deleteNeighbor(precinctId2);
            targetData2.deleteNeighbor(precinctId1);
            pem.cleanup(true);
            return ErrorGen.ok();
        } else {
            pem.cleanup(true);
            if (targetData1 == null) {
                return ErrorGen.create("unable to get precinct1");
            } else {
                return ErrorGen.create("unable to get precinct2");
            }

        }

    }

    /**
     * Create a new precinct object.
     * 
     * TODO: have to find a way to create a new precinct id
     * 
     * @param mp
     * @return
     */
    @CrossOrigin
    @GetMapping("/createNewPrecinct")
    public ErrorJ createNewPrecinct(@RequestParam MultiPolygon mp) {
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        BigInteger bigintId = new BigInteger(precinctManager.getLargestPrecinctId());
        bigintId.add(new BigInteger("1"));
        String newId = new String(bigintId.toByteArray());
        Precinct newPrecinct = new Precinct(newId, "", "", null, null, null, null, null, mp);
        precinctManager.addPrecinct(newPrecinct);

        return ErrorGen.ok();
    }

    /**
     * Merge two precincts together. TODO: Need to update merged errorData
     * 
     * TODO: Have to merge polygon also (with sam's script)
     * 
     * TODO: need test
     * 
     * @param precinctId1
     * @param precinctId2
     * @return
     */
    @CrossOrigin
    @GetMapping("/mergePrecinct")
    public ErrorJ mergePrecincts(@RequestParam String precinctId1, @RequestParam String precinctId2) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrecinctTable");
        PrecinctEntityManager pem = new PrecinctEntityManager(emf);
        PrecinctFeature targetData1 = pem.findPrecinctFeatureById(precinctId1);
        PrecinctFeature targetData2 = pem.findPrecinctFeatureById(precinctId2);
        if (targetData1 != null && targetData2 != null) {
            Precinct target1 = new Precinct(targetData1);
            Precinct target2 = new Precinct(targetData2);
            Precinct mergedPrecinct = Precinct.mergePrecinct(target1, target2);
            String mergedPrecinctId = mergedPrecinct.getId();
            targetData1.update(mergedPrecinct);
            if (mergedPrecinct.getDemographicData() != null) {
                targetData1.getDemogrpahicTable().update(mergedPrecinct.getDemographicData(), mergedPrecinctId);
            }
            if (mergedPrecinct.getVotingData() != null) {
                VotingData mergedVotingData = mergedPrecinct.getVotingData();
                Set<ELECTIONS> mergedElections = mergedVotingData.getAllElections();
                for (ElectionDataTable edt : targetData1.getElectionDataSet()) {
                    if (mergedElections.contains(edt.getElection())) {
                        edt.update(mergedVotingData.getElectionData(edt.getElection()), mergedPrecinctId);
                        mergedElections.remove(edt.getElection());
                    }
                }
                for (ELECTIONS remainElections : mergedElections) {
                    ElectionDataTable electionDataTable = new ElectionDataTable(
                            mergedVotingData.getElectionData(remainElections), mergedPrecinctId);
                    pem.persistElectionDataTable(electionDataTable);
                }
            }
            if (mergedPrecinct.getPrecinctErrors() != null) {
                Map<Integer, PrecinctError> mergedErrors = mergedPrecinct.getPrecinctErrors();
                Set<Integer> mergedErrorIds = mergedErrors.keySet();
                for (ErrorTable et : targetData1.getErrors()) {
                    if (mergedErrorIds.contains(et.getErrorId())) {
                        et.update(mergedErrors.get(et.getErrorId()), mergedPrecinctId);
                        mergedErrorIds.remove(et.getErrorId());
                    }
                }

                for (int remainErrorId : mergedErrorIds) {
                    ErrorTable errorTable = new ErrorTable(mergedErrors.get(remainErrorId));
                    errorTable.setPrecinctId(mergedPrecinctId);
                    pem.persistErrorTable(errorTable);
                }
            }

            pem.cleanup(true);
            return ErrorGen.ok();
        } else {
            pem.cleanup(true);
            return ErrorGen.create("can't find precinct1 or precinct2");
        }

    }

    /**
     * TODO: have script to do this part
     */
    // @GetMapping("verifyPrecinctShape")
    // public void verifyPrecinctShape(){
    // }
}
