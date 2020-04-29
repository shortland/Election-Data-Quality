import { json } from 'd3-request';
/**
 * A js class to access all our fetch API calls through
 */
class AppData {
    constructor() {
        this.allStates = null;
    }

    asyncFetch = async (url) => {
        const response = await fetch(url);
        const json = await response.json();
        return json;
    }

    fetchAllStates() {
        //return this.fetchDataFromAPI('http://0.0.0.0:1234/allStates')
        return this.asyncFetch('http://67.80.171.107:1234/allStates')
            .then(data => {
                console.log(data)
                let FeatureCollection = {
                    type: "FeatureCollection",
                    features: []
                };
                for (let i in data) {
                    let feature = {
                        type: "Feature",
                        properties: {
                            name: data[i].name,
                            id: data[i].id,
                            amount_counties: data[i].countiesId ? data[i].countiesId.length : 0
                        },
                        geometry: {
                            type: "MultiPolygon",
                            coordinates: data[i].geometry.coordinates
                        }
                    };
                    FeatureCollection["features"].push(feature);
                }
                console.log(FeatureCollection);
                return FeatureCollection;
            });
    }

    fetchCongressionalDistrictByState(stateID) {
        return this.asyncFetch('http://67.80.171.107:1234/congressionalDistrict?cid='.concat(stateID))
            .then(data => {
                return data;
            });
    }

    fetchCongressionalDistrictByCID(CID) {
        return this.asyncFetch('http://67.80.171.107:1234/congressionalDistrict?cid='.concat(CID))
            .then(data => {
                console.log(data);
                return data;
            });
    }

    fetchPrecincts() {

    }
}

export default AppData;