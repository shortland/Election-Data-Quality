
class FeatureData {
    constructor(properties) {
        this.elections = false;
        this.population = false;
        this.demographics = false;
        if (properties.elections) { this.elections = JSON.parse(properties.elections); }
        if (properties.population) { this.population = properties.population; }
        if (properties.demographics) { this.demographics = JSON.parse(properties.demographics); }
    }

    getElections() { return this.elections; }
    getPopulation() { return this.population; }
    getDemographics() { return this.demographics; }

}

export default FeatureData;