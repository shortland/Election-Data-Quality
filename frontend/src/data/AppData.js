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
            .then(result => {
                console.log(result)
                let FeatureCollection = {
                    type: "FeatureCollection",
                    features: []
                };
                for (let i in result) {
                    let feature = {
                        type: "Feature",
                        properties: {
                            name: result[i].name,
                            id: result[i].id,
                            amount_counties: result[i].countiesId ? result[i].countiesId.length : 0
                        },
                        geometry: {
                            type: "MultiPolygon",
                            coordinates: result[i].geometry.coordinates
                        }
                    };
                    FeatureCollection["features"].push(feature);
                }
                console.log(FeatureCollection);
                return FeatureCollection;
            });
    }

    fetchCongressionalDistricts(){
        return this.asyncFetch('http://67.80.171.107:1234/congressionalDistrictsForState?')
    }

    fetchPrecincts(){

    }
}

export default AppData;