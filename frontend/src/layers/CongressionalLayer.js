export const congressionalLayerFill = {
    id: 'congressionalFill',
    type: 'fill',
    paint: {
        'fill-color': 'rgba(0, 0, 0, 0.0)',
    },
};

export const congressionalLayerFillHighlight = {
    id: 'congressionalFillHighlighted',
    type: 'fill',
    source: 'congressionalFill',
    paint: {
        'fill-color': 'rgba(66, 135, 245, 0.5)',
    },
};

export const congressionalLayerOutline = {
    id: 'congressionalOutline',
    type: 'line',
    paint: {
        'line-color': 'rgba(196, 14, 14, 1.0)',
        'line-width': 2,
    },
};