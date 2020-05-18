export const nationalParkLayerFill = {
    id: 'parkFill',
    type: 'fill',
    paint: {
        'fill-color': 'rgb(34, 139, 34)',
        'fill-opacity': [
            'case',
            ['boolean', ['feature-state', 'hover'], false],
            0.9,
            0.7
        ]
    },
};

export const nationalParkLayerOutline = {
    id: 'parkOutline',
    type: 'line',
    paint: {
        'line-color': '#000000',
        'line-width': 2,
        //'line-dasharray': [1, 0.5, 1],
        'line-opacity': 0.6
    },
    layout: {
        'line-join': 'round'
    }
};