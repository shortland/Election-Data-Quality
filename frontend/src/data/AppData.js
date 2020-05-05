import { json } from 'd3-request';
/**
 * A js class to access all our fetch API calls through
 */
class AppData {

    constructor() {
        this.allStates = null;

        // For development
        // this.baseUrl = "http://0.0.0.0:1234/";

        // For production
        this.baseUrl = "http://67.80.171.107:1234/";
    }

    asyncFetch = async (url) => {
        const response = await fetch(url);
        const json = await response.json();

        return json;
    }

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
                geometry: data.content[i].geometry,
            };

            features.features.push(feature);
        }

        return {
            featureCollection: features,
        };
    }

    async fetchCongressionalDistrictByState(stateID) {
        const data = await this.asyncFetch(this.baseUrl + 'congressionalDistrictsForState?stateId='.concat(stateID));

        if (data.status != "ok") {
            alert("server error: unable to get data");
            return;
        }

        return data.content;
    }

    async fetchAllCongressionalDistricts() {
        const data = await this.asyncFetch(this.baseUrl + 'allCongressionalDistricts');

        if (data.status != "ok") {
            alert("server error: unable to get data");
            return;
        }

        return data.content;
    }

    // fetchCongressionalDistrictByCID(CID) {
    //     return this.asyncFetch(this.baseUrl + 'congressionalDistrict?cid='.concat(CID))
    //         .then(data => {
    //             if (data.status != "ok") {
    //                 alert("server error: unable to get data");
    //                 return;
    //             }

    //             return data.content;
    //         });
    // }

    fetchPrecincts() {

    }
}

export default AppData;