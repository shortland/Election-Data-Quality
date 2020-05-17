export const precinctLayerFill = {
    id: 'precinctFill',
    type: 'fill',
    paint: {
        'fill-color': '#C73009',
        'fill-opacity': [
            'case',
            ['boolean', ['feature-state', 'hover'], false],
            0.6,
            0.25
        ]
    },
};

export const precinctLayerFillHighlight = {
    id: 'precinctFillHighlighted',
    type: 'fill',
    source: 'precinctFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};

export const precinctLayerOutline = {
    id: 'precinctOutline',
    type: 'line',
    paint: {
        //'line-color': 'rgba(66, 135, 245, 1.0)',
        'line-color': '#FCBF1E',
        'line-opacity': 0.75,
        'line-width': 3,
    },
    layout: {
        'line-join': 'round'
    }
};