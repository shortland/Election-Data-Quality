export const stateLayerFill = {
    id: 'stateFill',
    type: 'fill',
    paint: {
        'fill-color': '#0779e4',
        'fill-opacity': [
            'case',
            ['boolean', ['feature-state', 'hover'], false],
            .75,
            0.5
        ]
    },
};

export const stateLayerOutline = {
    id: 'stateOutline',
    type: 'line',
    paint: {
        'line-color': '#000000',
        'line-width': 2,
        //'line-dasharray': [1, 0.5, 1],
        'line-opacity': 0.4
    },
    layout: {
        'line-join': 'round'
    }
};

export const stateLayerFillHighlight = {
    id: 'stateFillHighlighted',
    type: 'fill',
    source: 'stateFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};