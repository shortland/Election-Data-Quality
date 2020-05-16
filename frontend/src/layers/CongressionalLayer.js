export const congressionalLayerFill = {
    id: 'congressionalFill',
    type: 'fill',
    paint: {
        'fill-color': '#27496d',
        'fill-opacity': 0.5
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
        'line-color': '#FFFFFF',
        'line-width': 4,
        'line-dasharray': [1, 0.5, 1],
    },
    layout: {
        'line-join': 'round'
    }
};