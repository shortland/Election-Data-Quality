export const countyDataLayerFillable = {
    id: 'countyFill',
    type: 'fill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.3)',
    },
};

export const countyDataLayerFillableHighlight = {
    id: 'countyFillHighlighted',
    type: 'fill',
    source: 'countyFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};

export const countyDataLayerOutline = {
    id: 'countyOutline',
    type: 'line',
    paint: {
        'line-color': '#000000',
        'line-width': 2,
    },
};