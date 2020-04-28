/* global setTimeout */
import React, { PureComponent } from 'react';
import styled from 'styled-components';
import { EditorModes } from 'react-map-gl-draw';


const MODES = [
    { id: EditorModes.SELECT, text: 'Edit Feature', icon: 'icon-select.svg' },
    { id: EditorModes.DRAW_POINT, text: 'Draw Point', icon: 'icon-point.svg' },
    { id: EditorModes.DRAW_PATH, text: 'Draw Polyline', icon: 'icon-path.svg' },
    { id: EditorModes.DRAW_POLYGON, text: 'Draw Polygon', icon: 'icon-polygon.svg' },
    { id: EditorModes.DRAW_RECTANGLE, text: 'Draw Rectangle', icon: 'icon-rectangle.svg' }
];

const Container = styled.div`
  position: absolute;
//   width: 48px;
  left: 260px;
  top: 240px;
  background: #fff;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.15);
  outline: none;
  display: flex;
  justify-content: center;
  flex-direction: column;
  padding: 0px;
  margin: 0px;
`;

const Row = styled.div`
  height: 34px;
  width: 34px;
  padding: 7px;
  display: flex;
  justify-content: left;
  color: ${props => (props.selected ? '#ffffff' : 'inherit')};
  background: ${props => (props.selected ? '#0071bc' : props.hovered ? '#e6e6e6' : 'inherit')};
`;

const Img = styled.img`
  max-width: 20px !important;
`;

const Tooltip = styled.div`
  position: absolute;
  left: 52px;
  padding: 4px;
  background: rgba(0, 0, 0, 0.8);
  color: #fff;
  min-width: 100px;
  max-width: 300px;
  height: 24px;
  font-size: 12px;
  z-index: 9;
  pointer-events: none;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Delete = styled(Row)`
  &:hover {
    background: ${props => (props.selected ? '#0071bc' : '#e6e6e6')};
  }
  &:active: {
    background: ${props => (props.selected ? '#0071bc' : 'inherit')};
  }
`;

export default class Toolbar extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            deleting: false,
            hoveredId: null,
        };
    }

    _onHover = evt => {
        this.setState({ hoveredId: evt && evt.target.id });
    };

    //TODO probably dont neet to pass the evt 
    _onDelete = () => {
        this.props.onDelete();
        this.setState({ deleting: true });
        setTimeout(() => this.setState({ deleting: false }), 500)
    };

    _onSaveChange = () => {
        console.log(this.props.features)
        this.props.toolBarRequest(this.props.features.data);
    }

    render() {
        const { selectedMode } = this.props;
        const { hoveredId } = this.state;
        return (
            <Container>
                {MODES.map(m => {
                    return (
                        <Row
                            onClick={this.props.onSwitchMode}
                            onMouseOver={this._onHover}
                            onMouseOut={_ => this._onHover(null)}
                            selected={m.id === selectedMode}
                            hovered={m.id === hoveredId}
                            key={m.id}
                            id={m.id}
                        >
                            <Img id={m.id} onMouseOver={this._onHover} src={m.icon} />
                            {hoveredId === m.id && <Tooltip>{m.text}</Tooltip>}
                        </Row>
                    );
                })}
                <Delete
                    selected={this.state.deleting}
                    onClick={this._onDelete}
                    onMouseOver={this._onHover}
                    onMouseOut={_ => this._onHover(null)}
                >
                    <Img
                        id={'delete'}
                        onMouseOver={this._onHover}
                        src={'icon-delete.svg'}
                    />
                    {hoveredId === 'delete' && <Tooltip>{'Delete'}</Tooltip>}
                </Delete>
                <Row
                    onClick={() => this._onSaveChange()}
                    onMouseOver={this._onHover}
                    onMouseOut={_ => this._onHover(null)}
                    id="saveDrawBtn"
                ><span style={{ "fontSize": "10px" }}> save</span></Row>
            </Container>
        );
    }
}
