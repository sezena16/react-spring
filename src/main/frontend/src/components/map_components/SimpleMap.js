import React, { Component } from "react"
import { compose } from "recompose"
import {withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow} from "react-google-maps"
import axios from "axios";

const MapWithAMarker = compose(withScriptjs, withGoogleMap)(props => {
    return (
        <GoogleMap defaultZoom={4} defaultCenter={{ lat: 33, lng: 45 }}>
            {props.markers.map(marker => {
                const onClick = props.onClick.bind(this, marker)
                return (
                    <Marker
                        key={marker.id}
                        onClick={onClick}
                        position={{ lat: marker.latitude, lng: marker.longitude }}
                    >
                        {props.selectedMarker === marker &&
                        <InfoWindow>
                            <div>
                                {marker.activityName}
                            </div>
                        </InfoWindow>}
                        }
                    </Marker>
                )
            })}
        </GoogleMap>
    )
})

export default class SimpleMap extends Component {
    constructor(props) {
        super(props)
        this.state = {
            shelters: [],
            selectedMarker: false,
        }
    }
    componentDidMount() {
        axios.get("/users/activities/user")
            .then(response => {
                this.setState({shelters: response.data})
            })
    }
    handleClick = (marker, event) => {
        // console.log({ marker })
        this.setState({ selectedMarker: marker })
    }

    render() {

        return (
            <div>
                <MapWithAMarker
                    selectedMarker={this.state.selectedMarker}
                    markers={this.state.shelters}
                    onClick={this.handleClick}
                    googleMapURL="https://maps.googleapis.com/maps/api/js?key=YOURAPIKEY"
                    loadingElement={<div style={{ height: `100%` }} />}
                    containerElement={<div style={{ height: `400px` }} />}
                    mapElement={<div style={{ height: `100%` }} />}
                />
            </div>
        )

    }
}
