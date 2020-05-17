export const stateLayerFill = {
    id: 'stateFill',
    type: 'fill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.75)',
        'fill-opacity': [
            'case',
            ['boolean', ['feature-state', 'hover'], false],
            1,
            0.5
        ]
    },
};

export const stateLayerFillHighlight = {
    id: 'stateFillHighlighted',
    type: 'fill',
    source: 'stateFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};