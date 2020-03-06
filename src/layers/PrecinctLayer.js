export const precinctLayerFill = {
    id: 'precinctFill',
    type: 'fill',
    paint: {
        'fill-color': 'rgba(0, 0, 0, 0.0)',
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
        'line-color': 'rgba(66, 135, 245, 1.0)',
        'line-width': 1,
    },
};