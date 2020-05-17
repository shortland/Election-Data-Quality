export const precinctLayerFill = {
    id: 'precinctFill',
    type: 'fill',
    paint: {
        'fill-color': '#900c3f',
        'fill-opacity': [
            'case',
            ['boolean', ['feature-state', 'hover'], false],
            0.4,
            0.20
        ]
    },
};

export const precinctLayerOutline = {
    id: 'precinctOutline',
    type: 'line',
    paint: {
        //'line-color': 'rgba(66, 135, 245, 1.0)',
        'line-color': '#000000',
        'line-opacity': 0.5,
        'line-width': 2,
    },
    layout: {
        'line-join': 'round'
    }
};

export const precinctLayerFillHighlight = {
    id: 'precinctFillHighlighted',
    type: 'fill',
    source: 'precinctFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};