// precinct red #900c3f' = rgb(144, 12, 63)

export const precinctLayerFill = {
    id: 'precinctFill',
    type: 'fill',
    paint: {
        //'fill-color': ['case', ['boolean', ['feature-state', 'neighbor'], false], '#61366C', '#900c3f'],
        'fill-color': ['case',
            ['boolean', ['feature-state', 'neighbor'], false],
            'rgba(144, 12, 63, 0.9)',
            ['boolean', ['feature-state', 'selected'], false],
            'rgba(144, 12, 63, 1.0)',
            'rgba(144, 12, 63, 0.8)'],
        'fill-opacity':
            ['case',
                ['boolean', ['feature-state', 'selected'], false],
                0.9,
                ['boolean', ['feature-state', 'hover'], false],
                0.8,
                ['boolean', ['feature-state', 'neighbor'], false],
                0.6,
                0.4
            ]
    }
};

export const precinctLayerOutline = {
    id: 'precinctOutline',
    //source: 'precinctFill',
    type: 'line',
    paint: {
        'line-color': '#000000',
        //'line-color': ['case', ['boolean', ['feature-state', 'neighbor'], false], '#FFFFFF', '#000000'],
        'line-opacity': 0.5,
        'line-width': 2
        //'line-width': ['case', ['boolean', ['feature-state', 'neighbor'], false], 5, 2],
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