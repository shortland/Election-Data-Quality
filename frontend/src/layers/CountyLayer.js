export const countyDataLayerFillable = {
    id: 'countyFill',
    type: 'fill',
    paint: {
        'fill-color': '#0779e4',
        'fill-opacity': [
            'case',
            ['boolean', ['feature-state', 'hover'], false],
            0.6,
            0.3
        ]
    },
};

export const countyDataLayerOutline = {
    id: 'countyOutline',
    type: 'line',
    paint: {
        'line-color': '#000000',
        'line-width': 2,
        'line-opacity': 0.4
    },
    layout: {
        'line-join': 'round'
    }
};

export const countyDataLayerFillableHighlight = {
    id: 'countyFillHighlighted',
    type: 'fill',
    source: 'countyFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};