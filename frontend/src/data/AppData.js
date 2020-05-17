/**
 * A class to access all our fetch API calls through
 */
class AppData {
    constructor() {
        this.allStates = null;

        // For development
        //this.baseUrl = "//0.0.0.0:1234/";

        // For production
        this.baseUrl = "https://ElectionDataQuality.com:1234/";
    }

    asyncFetch = async (url) => {
        const response = await fetch(url);
        const json = await response.json();
        return json;
    }

    //-------- * STATES * -----------
    async fetchAllStates() {
        const data = await this.asyncFetch(this.baseUrl + 'allStates');

        if (data.status != "ok") {
            alert("server error: unable to get data");
            return;
        }

        let features = {
            type: "FeatureCollection",
            features: [],
        };

        for (let i in data.content) {
            let feature = {
                type: "Feature",
                properties: {
                    name: data.content[i].name,
                    id: data.content[i].id,
                    congressional_districts: data.content[i].congressionalDistrictIds,
                },
                id: data.content[i].id,
                geometry: data.content[i].geometry,
            };

            features.features.push(feature);
        }

        //console.log(data.content)
        console.log(features);
        return {
            featureCollection: features,
        };
    }

    //---------- * CONGRESSIONAL DISTRICTS * --------------
    async fetchCongressionalDistrictByState(stateID) {
        const data = await this.asyncFetch(this.baseUrl + 'congressionalDistrictsForState?stateId='.concat(stateID));

        if (data.status != "ok") {
            alert("server error: unable to get data");
            return;
        }

        let features = data.content.features
        for (let i = 0; i < features.length; i++) {
            let currFeature = features[i];
            let properties = {
                "id": currFeature.id,
                "name": currFeature.name,
                "parentId": currFeature.parentId,
                "childrenId": currFeature.childrenId
            }
            //delete currFeature.id;
            delete currFeature.name;
            delete currFeature.parentId;
            delete currFeature.childrenId;
            currFeature["properties"] = properties;
        }

        data.content.features = features;

        return data.content;
    }

    async fetchAllCongressionalDistricts() {
        const data = await this.asyncFetch(this.baseUrl + 'allCongressionalDistricts');

        if (data.status != "ok") {
            alert("server error: unable to get data");
            return;
        }
        //console.log(data.content)
        return data.content;
    }

    //----t---------- * COUNTIES * ------------------
    async fetchCountiesByState(stateID) {
        const data = await this.asyncFetch(this.baseUrl + 'countiesInState?stateId='.concat(stateID));

        if (data.status != "ok") {
            alert("server error: unable to get data");
            return;
        }

        let featureCollection = {
            type: "FeatureCollection",
            features: [],
        };

        for (let i in data.content) {
            let feature = {
                type: "Feature",
                properties: {
                    name: data.content[i].name,
                    id: data.content[i].countyId
                },
                id: data.content[i].countyId,
                geometry: data.content[i].geometry,
            };

            featureCollection.features.push(feature);
        }

        return featureCollection;
    }

    //------------ * PRECINCTS * ----------------
    async fetchShapeOfPrecinctsByCounty(countyID) {
        const data = await this.asyncFetch(this.baseUrl + 'shapeOfPrecinctByCounty?countyId='.concat(countyID));

        if (data.status != "ok") {
            console.log(data)
            alert("server error: unable to get precinct shapes");
            return;
        }

        let featureCollection = {
            type: "FeatureCollection",
            features: [],
        };

        for (let i in data.content) {
            let f = {
                type: "Feature",
                properties: {
                    id: data.content[i].id
                },
                geometry: data.content[i].geometry,
                id: data.content[i].id
            };
            featureCollection.features.push(f);
        }

        console.log(featureCollection)

        return featureCollection
    }

    async fetchPrecinctInfo(precinctId) {
        const data = await this.asyncFetch(this.baseUrl + 'precinctInfo?precinctId='.concat(precinctId));

        if (data.status != "ok") {
            console.log(data);
            alert("server error : unable to get precinct info");
            return
        }

        return data.content;
    }
}

export default AppData;