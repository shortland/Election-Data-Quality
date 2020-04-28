export const congressionalDataLayerFillable = {
    id: 'congressionalFill',
    type: 'fill',
    paint: {
        'fill-color': 'rgba(0, 0, 0, 0.0)',
    },
};

export const congressionalLayerFillableHighlight = {
    id: 'congressionalFillHighlighted',
    type: 'fill',
    source: 'congressionalFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};

export const congressionalLayerOutline = {
    id: 'congressionalDataOutline',
    type: 'line',
    paint: {
        'line-color': '#000000',
        'line-width': 2,
    },
};