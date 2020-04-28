import { json } from 'd3-request';
/**
 * A js class to access all our fetch API calls through
 */
class AppData {

    fetchDataFromAPI = (url) => {
        let obj = null;
        /* fetch(url)
            .then((response) => response.json())
            .then(data => obj = data)
            .then(() => console.log(obj))
            .catch(console.log) */

        json(
            url,
            (error, response) => {
                if (!error) {
                    obj = response;
                    console.log(response);
                }
                else {
                    console.log(error);
                }
            }
        );
        return obj;
    }

    asyncFetch = async (url) => {
        const response = await fetch(url);
        const json = await response.json();
        return json;
    }

    fetchAllStates() {
        //return this.fetchDataFromAPI('http://0.0.0.0:1234/allStates')
        return this.asyncFetch('http://67.80.171.107:1234/allStates');
    }

}

export default AppData;