export const countyDataLayerFillable = {
    id: 'countyData',
    type: 'fill',
    paint: {
        'fill-color': 'rgba(20, 100, 50, 0.0)',
    },
};

export const countyDataLayerFillableHighlight = {
    id: 'countyData-highlighted',
    type: 'fill',
    source: 'countyData',
    paint: {
        'fill-color': '#6e599f',
        'fill-opacity': 0.75
    }
};

export const countyDataLayerOutline = {
    id: 'countyDataOutline',
    type: 'line',
    paint: {
        'line-color': '#000000',
        'line-width': 2,
    },
};